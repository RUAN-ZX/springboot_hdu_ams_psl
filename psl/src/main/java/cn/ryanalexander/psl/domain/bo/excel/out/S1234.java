package cn.ryanalexander.psl.domain.bo.excel.out;

import cn.ryanalexander.psl.domain.bo.excel.ExcelEntity;
import cn.ryanalexander.psl.domain.exceptions.AppException;
import cn.ryanalexander.psl.domain.po.SDetailPO;
import cn.ryanalexander.psl.mapper.AccountMapper;
import cn.ryanalexander.psl.service.SDetailService;
import cn.ryanalexander.psl.service.tool.SpringUtil;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p><b></b></p>
 *
 * <p>2022/4/11 </p>
 *
 * @author ryan 2022/4/11 14:37
 * @since 1.0.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("汇总表")
@ToString
@ExcelIgnoreUnannotated
public class S1234 implements ExcelEntity<S1234>{
    @ExcelProperty(value = "部门")
    private String teacherTeam;

    @ExcelProperty(value = "工号")
    private Integer teacherId;

    @ExcelProperty(value = "姓名")
    private String teacherName;

    @ExcelProperty(value = "职称")
    private String teacherTitleName;

    @ExcelProperty(value = "系列")
    private String teacherType;

    @ExcelProperty(value = "职称等级")
    private Integer teacherLevel;

    // ------------------------------------------------------------------------

    @ExcelProperty(value = {"工作量S1","合计教学工作量"})
    private Double s1HoursStd;

    @ExcelProperty(value = {"工作量S1","S1"})
    private Double s1Score;

    // ------------------------------------------------------------------------
    // todo 表头需要变量？
    @ExcelProperty(value = {"学评教S2", "第一学期"})
    private Double s2Score1;

    @ExcelProperty(value = {"学评教S2", "第二学期"})
    private Double s2Score2;

    @ExcelProperty(value = {"学评教S2", "平均值"})
    private Double s2ScoreAvg;

    @ExcelProperty(value = {"学评教S2", "平均\n排名"})
    private Integer s2Rank;

    @ExcelProperty(value = {"学评教S2", "S2"})
    private Double s2Score;

    // ------------------------------------------------------------------------

//    @ExcelProperty(value = {"S3", "学科竞赛"})
//    private Double s31First;
//
//    @ExcelProperty(value = {"S3", "其它省级比赛"})
//    private Double s31Second;
//
//    @ExcelProperty(value = {"S3", "S31"})
//    private Double s31;
//
//    @ExcelProperty(value = {"S3", "教学成果奖\n"})
//    private Double s32First;
//
//    @ExcelProperty(value = {"S3", "教学名师奖\n"})
//    private Double s32Second;
//
//    @ExcelProperty(value = {"S3", "其它教学奖励\n"})
//    private Double s32Third;
//
//    @ExcelProperty(value = {"S3", "教学技能奖\n"})
//    private Double s32Fourth;
//
//    @ExcelProperty(value = {"S3", "教学事故\n"})
//    private Double s32Fifth;
//
//    @ExcelProperty(value = {"S3", "S32\n"})
//    private Double s32;
//
//    @ExcelProperty(value = {"S3", "S3"})
//    private Double s3;
//
//    // ------------------------------------------------------------------------
//
//    @ExcelProperty(value = {"S4", "教改项目"})
//    private Double s41First;
//
//    @ExcelProperty(value = {"S4", "实验教学示范中心建设项目\n"})
//    private Double s41Second;
//
//    @ExcelProperty(value = {"S4", "教学团队"})
//    private Double s41Third;
//
//    @ExcelProperty(value = {"S4", "S41"})
//    private Double s41;
//
//    @ExcelProperty(value = {"S4", "专业建设"})
//    private Double s42First;
//
//    @ExcelProperty(value = {"S4", "课程建设\n"})
//    private Double s42Second;
//
//    @ExcelProperty(value = {"S4", "教材建设\n"})
//    private Double s42Third;
//
//    @ExcelProperty(value = {"S4", "S42\n"})
//    private Double s42;
//
//    @ExcelProperty(value = {"S4", "公开发表论文\n"})
//    private Double s43First;
//
//    @ExcelProperty(value = {"S4", "S43\n"})
//    private Double s43;
//
//    @ExcelProperty(value = {"S4", "S4\n"})
//    private Double s4;

    // ------------------------------------------------------------------------

    @ExcelProperty(value = "总分")
    private Double sScore;

    // 历史数据也把这个导入了！
    @ExcelProperty(value = "备注1（本人提出不参与）\n", index = 20)
    private String sNote;

    private Integer sDetailYear;

    public S1234(SDetailPO s){
        // 根据key=311 312 等统计即可！
//        s31 = s.getS31First() + s.getS31First();
//        s32 = s.getS32First() + s.getS32Second() + s.getS32Third() + s.getS32Fourth() + s.getS32Fifth();
//        s41 = s.getS41First() + s.getS42Second() + s.getS43First();
//        s42 = s.getS42First() + s.getS42Second() + s.getS42Third();
//        s43 = s.getS43First();
    }

    @Override
    public boolean isValidated() {
        // 有名字 有百分占比 正常情况！
        return teacherName != null;
    }

    @Override
    public boolean multiStart(){
        return false;
    }
    // 这里 之前也是multiStart 应该怎么处理
    @Override
    public boolean prevIsMultiHeadOperation(ExcelEntity mask){
        return false;
    }

    @Override // 下一行的补充数据
    public boolean multiContinue(){
        return false;
    }
    @Override
    public void fieldStandardized(){
        // 用对象前 先检测null teacherName一定有的。。
        if(this.teacherName.length() > 3)
            this.teacherName = this.teacherName.substring(0, 3);
    }
    @Override
    public void stdCalculator(List<Map<Integer, String>> headInfoMap){
        try{
            if(headInfoMap != null) // null的含义就是 我不想更新这个时间
                this.sDetailYear = Integer.valueOf(headInfoMap.get(0).get(0));
        }
        catch (Exception e){
            System.out.println("Invalid currentHeadInfo, a integer for current date(year) Expected");
            e.printStackTrace();
        }
    }

    @Override
    public void transformAndSave(ArrayList<S1234> list, int size) {
        SDetailService sDetailService =
                (SDetailService) SpringUtil.getBean("sDetailServiceImpl");
        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");

        ArrayList<String> accountNameList = new ArrayList<>(size);

        // 这里 因为标准课时 前边累加了 都是指定系数1 没有直接指定标准课时的 所以不calculation
        for (S1234 s1234 : list) {
            accountNameList.add(s1234.teacherName);
        }
        ArrayList<Integer> accountIdList = accountMapper.selectBatchIdByName(accountNameList);
        ArrayList<SDetailPO> sDetailPOS = new ArrayList<>(size);


        // accountId 注入到CourseTheory
        S1234 s1234 = null;
        for(int i = 0 ; i < list.size() ; i++){
            try{
                s1234 = list.get(i);
                // 作为读表的时候是不看表原有teacherId的！
                s1234.setTeacherId(accountIdList.get(i));
                // 有些字段实在太长 删减点 别太过了
                s1234.fieldStandardized();

                sDetailPOS.add(new SDetailPO(s1234));

            }
            catch (Exception e){
                e.printStackTrace();
                throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
            }
        }
        try{
            sDetailService.saveBatch(sDetailPOS);

        }
        catch (Exception e){
            e.printStackTrace();
            throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
        }
        finally {
            sDetailPOS.clear();
            accountIdList.clear();
            accountNameList.clear();
        }
    }
}
