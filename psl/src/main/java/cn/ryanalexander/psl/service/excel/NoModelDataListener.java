package cn.ryanalexander.psl.service.excel;

import cn.ryanalexander.common.domain.dto.Account;
import cn.ryanalexander.psl.domain.po.AccountPO;
import cn.ryanalexander.psl.domain.po.SDetailPO;
import cn.ryanalexander.psl.mapper.AccountMapper;
import cn.ryanalexander.psl.mapper.SDetailMapper;
import cn.ryanalexander.psl.service.SDetailService;
import cn.ryanalexander.psl.service.tool.DataUtil;
import cn.ryanalexander.psl.service.tool.SpringUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */

    private static final int BATCH_COUNT = 512;
    private List<SDetailPO> cachedDataList = new ArrayList<>(BATCH_COUNT);

    private String sDetailYear = "";
    private Map<Integer, String> headMap1 = new HashMap<>();
    private Map<String, Integer> reverseHeadMap1 = new HashMap<>();
    private Map<Integer, String> headMap2 = new HashMap<>();
    private Map<String, Integer> reverseHeadMap2 = new HashMap<>();

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
//        log.error("解析失败:{}", Arrays.toString(exception.getStackTrace()));
        exception.printStackTrace();

        // 如果是某一个单元格的转换异常 能获取到具体行号

        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            log.error("第{}行，第{}列解析异常", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex());
        }
    }
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
        if(context.readRowHolder().getRowIndex() == 0){
            sDetailYear = headMap.get(0);
            headMap1 = headMap;
            headMap1.forEach((key, value) -> reverseHeadMap1.put(value, key));
        }
        if(context.readRowHolder().getRowIndex() == 1){
            headMap2 = headMap;
            headMap2.forEach((key, value) -> reverseHeadMap2.put(value, key));
        }
    }
// 常规的不需要知道 都写死了 到时候往里边填数据完事了 再扔个标题
// 最后结尾再扔两个 add 就完事了

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        // 空记录过滤！
        if(data.get(reverseHeadMap1.get("姓名")) == null) return;

        SDetailPO dataPO = new SDetailPO();
        HashMap<String, Double> s3Data = new HashMap<>();
        HashMap<String, Double> s4Data = new HashMap<>();

        dataPO.setSDetailYear(Integer.valueOf(sDetailYear));
        // reverseHeadMap1.get("姓名") 获取名字所在col index 然后data getByIndex
        dataPO.setSDetailTeacherName(data.get(reverseHeadMap1.get("姓名")));
        dataPO.setSDetailTeacherId(DataUtil.string2integer(data.get(reverseHeadMap1.get("工号")),null));
        dataPO.setS1Kpi(DataUtil.string2double(data.get(reverseHeadMap2.get("合计教学工作量")), null));
        dataPO.setS1Score(DataUtil.string2double(data.get(reverseHeadMap2.get("S1"))));


        int s2Pos = reverseHeadMap2.get("学评教平均值");
        dataPO.setS1Score(DataUtil.string2double(data.get(s2Pos)));

        dataPO.setS2Score2(DataUtil.string2double(data.get(s2Pos - 2)));
        dataPO.setS2Score1(DataUtil.string2double(data.get(s2Pos - 1)));

        dataPO.setS2ScoreAvg(DataUtil.getAvg(dataPO.getS2Score1(), dataPO.getS2Score2()));
        dataPO.setS2Rank(DataUtil.string2integer(data.get(s2Pos + 1)));
        dataPO.setS2Score(DataUtil.string2double(data.get(reverseHeadMap2.get("S2"))));

        int s3Start = reverseHeadMap2.get("S2") + 1;
        int s3End = reverseHeadMap2.get("S3");
        int s4Start = s3End + 1;
        int s4End = reverseHeadMap2.get("S4");
        dataPO.setS3Score(DataUtil.string2double(data.get(s3End)));
        dataPO.setS4Score(DataUtil.string2double(data.get(s4End)));

        for(int i = s3Start ; i < s3End ; ++i){
            String currentData = data.getOrDefault(i, null);
            String currentHead = headMap2.getOrDefault(i, null);
            if(currentData != null && currentHead != null && !currentHead.contains("S")){
                s3Data.put(currentHead, DataUtil.string2double(data.get(i)));
            }
        }
        for(int i = s4Start ; i < s4End ; ++i){
            String currentData = data.getOrDefault(i, null);
            String currentHead = headMap2.getOrDefault(i, null);
            if(currentData != null && currentHead != null && !currentHead.contains("S")){
                s4Data.put(currentHead, DataUtil.string2double(data.get(i)));
            }
        }
        // todo 一个个查有点慢
        dataPO.setS3Data(JSON.toJSONString(s3Data));
        dataPO.setS4Data(JSON.toJSONString(s4Data));
        dataPO.setSScore(DataUtil.string2double(data.get(reverseHeadMap1.get("总分"))));

        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");
        if(dataPO.getSDetailTeacherId() == null
                && dataPO.getSDetailTeacherName() != null){
            String name = DataUtil.getChineseCharacter(dataPO.getSDetailTeacherName());
            System.out.println(name);
            AccountPO accountPO = accountMapper.selectOne(
                    new QueryWrapper<AccountPO>()
                            .select("account_id")
                            .eq("account_name", name)
                            .last("limit 1"));
            if(accountPO != null){
                dataPO.setSDetailTeacherId(accountPO.getAccountId());
            }

        }

        cachedDataList.add(dataPO);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList.clear();
        }
// {0:"序号",7:"合计教学工作量",8:"S1",9:"17-18-02",10:"18-19-01",11:"学评教平均值",12:"平均\n排名",13:"S2",14:"学科竞赛",15:"其它省级比赛",16:"S31",17:"教学成果奖",18:"教学名师奖",19:"其它教学奖励",20:"教学技能奖",21:"教学事故",22:"S32",23:"S3",24:"教改项目",25:"实验教学示范中心建设项目",26:"教学团队",27:"S41",28:"专业建设",29:"课程建设",30:"教材建设",31:"S42",32:"公开发表论文",33:"S43",34:"S4"}
//        {0:"42",1:"海洋电子团队",2:"40136",3:"刘圆圆",4:"副教授",5:"专任教师",6:"副高",7:"559.00",8:"84.74",9:"92.116",10:"92.371",11:"92.2435",12:"9",13:"96.37",14:"13",16:"13",17:"2",19:"7",22:"9",23:"22",27:"0",28:"10",29:"10",31:"20",33:"0",34:"20",35:"223.111"}
//        {"实验教学示范中心建设项目":0.0,"公开发表论文":0.0,"教改项目":0.0,"专业建设":10.0,"课程建设":10.0,"教材建设":0.0,"教学团队":0.0}
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        cachedDataList.clear();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        SDetailService sDetailService = (SDetailService) SpringUtil.getBean("SDetailServiceImpl");

        sDetailService.saveBatch(cachedDataList, BATCH_COUNT);
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        log.info("存储数据库成功！");
    }
}
