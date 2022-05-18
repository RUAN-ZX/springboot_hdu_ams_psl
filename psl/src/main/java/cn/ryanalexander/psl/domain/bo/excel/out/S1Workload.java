package cn.ryanalexander.psl.domain.bo.excel.out;

import cn.ryanalexander.common.domain.exceptions.AppException;
import cn.ryanalexander.psl.domain.bo.excel.ExcelEntity;
import cn.ryanalexander.psl.domain.po.PostGraduatePO;
import cn.ryanalexander.psl.domain.po.SDetailPO;
import cn.ryanalexander.psl.domain.po.ShoulderBothPO;
import cn.ryanalexander.psl.mapper.AccountMapper;
import cn.ryanalexander.psl.mapper.PostGraduateMapper;
import cn.ryanalexander.psl.mapper.ShoulderBothMapper;
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
 * @author ryan 2022/4/11 14:21
 * @since 1.0.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("S1工作量表")
@ToString
@ExcelIgnoreUnannotated
public class S1Workload implements ExcelEntity<S1Workload> {
    // List index就完事了
    @ExcelProperty(value = "序号")
    private String id;

    @ExcelProperty(value = "团队")
    private String teacherTeam;

    @ExcelProperty(value = "姓名")
    private String teacherName;

    @ExcelProperty(value = "职工号")
    private String teacherId;

    // 除了研究生 双肩挑的所有。。。
    @ExcelProperty(value = "本科")
    private String teacherHoursStd;

    @ExcelProperty(value = "研究生")
    private String postGraduateHours; // postGraduateKpi * 100

    @ExcelProperty(value = "双肩挑")
    private String shoulderBothHours;

    @ExcelProperty(value = "合计")
    private String s1Hours;

    @ExcelProperty(value = "S1")
    private String s1Score_;

    @ExcelProperty(value = "S1封顶")
    private String s1Score; // 100最多拉

    @ExcelProperty(value = "备注")
    private String note; // 没数据的 让老师后边自个儿往excel里边加得了

    private String sYear;

    // 用于PO转ExcelEntity 实现表输出的
    public S1Workload(SDetailPO sDetailPO){

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
//        System.out.println(headInfoMap);
        try{
            if(headInfoMap != null) // null的含义就是 我不想更新这个时间
                this.sYear = headInfoMap.get(0).get(0);
        }
        catch (Exception e){
            System.out.println("Invalid currentHeadInfo, a integer for current date(year) Expected");
            e.printStackTrace();
        }
    }

    @Override
    public void transformAndSave(ArrayList<S1Workload> list, int size) {
//        ShoulderBothService shoulderBothService = (ShoulderBothService) SpringUtil.getBean("shoulderBothServiceImpl");
//        PostGraduateService postGraduateService =
//                (PostGraduateService) SpringUtil.getBean("postGraduateServiceImpl");

        ShoulderBothMapper shoulderBothMapper =
                (ShoulderBothMapper) SpringUtil.getBean("shoulderBothMapper");
        PostGraduateMapper postGraduateMapper =
                (PostGraduateMapper) SpringUtil.getBean("postGraduateMapper");


        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");

        ArrayList<String> accountNameList = new ArrayList<>(size);

        // 这里 因为标准课时 前边累加了 都是指定系数1 没有直接指定标准课时的 所以不calculation
        for (S1Workload s1Workload : list) {
            accountNameList.add(s1Workload.teacherName);
        }
        ArrayList<Integer> accountIdList = accountMapper.selectBatchIdByName(accountNameList);
        ArrayList<ShoulderBothPO> shoulderBothPOS = new ArrayList<>(size);
        ArrayList<PostGraduatePO> postGraduatePOS = new ArrayList<>(size);

        // accountId 注入到CourseTheory
        S1Workload s1Workload = null;
        for(int i = 0 ; i < list.size() ; i++){
            try{
                s1Workload = list.get(i);
                // 作为读表的时候是不看表原有teacherId的！
                s1Workload.setTeacherId(String.valueOf(accountIdList.get(i)));
                // 有些字段实在太长 删减点 别太过了
                s1Workload.fieldStandardized();

                shoulderBothPOS.add(new ShoulderBothPO(s1Workload));
                postGraduatePOS.add(new PostGraduatePO(s1Workload));

            }
            catch (Exception e){
                e.printStackTrace();
                throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
            }
        }
        try{
            shoulderBothMapper.saveOrUpdateBatch(shoulderBothPOS);
            postGraduateMapper.saveOrUpdateBatch(postGraduatePOS);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
        }
        finally {
            shoulderBothPOS.clear();
            postGraduatePOS.clear();
            accountIdList.clear();
            accountNameList.clear();
        }
    }
}
