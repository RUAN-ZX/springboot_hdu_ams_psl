package cn.ryanalexander.alibaba.domain.bo.excel;

import cn.ryanalexander.alibaba.domain.exceptions.AppException;
import cn.ryanalexander.alibaba.domain.po.EvaluationPO;
import cn.ryanalexander.alibaba.domain.po.ShoulderBothPO;
import cn.ryanalexander.alibaba.mapper.AccountMapper;
import cn.ryanalexander.alibaba.service.EvaluationService;
import cn.ryanalexander.alibaba.service.ShoulderBothService;
import cn.ryanalexander.alibaba.service.tool.ExcelDataProcessUtil;
import cn.ryanalexander.alibaba.service.tool.SpringUtil;
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
 * @ClassName: Evaluation_
 * @Description
 * @Author ryan
 * @Date 2022/3/10 20:19
 * @Version 1.0.0-Beta
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("学评教")
@ToString
@ExcelIgnoreUnannotated
public class S2Evaluation implements ExcelEntity<S2Evaluation>, Cloneable {
    @ExcelProperty("姓名")
    private String evaluationTeacherName;

    @ExcelProperty("参评人数")
    private Integer evaluationParticipate;

    @ExcelProperty("总得分")
    private Double evaluationScore;

    @ExcelProperty("全校排名")
    private Integer evaluationSchoolRank;

    @ExcelProperty("学院排名")
    private Integer evaluationAcademyRank;

    private String evaluationTerm;

    private Integer evaluationTeacherId;

    @Override
    public boolean isValidated() {
        // 有名字 有百分占比 正常情况！
        return evaluationTeacherName != null;
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
        if(this.evaluationTeacherName.length() > 3)
            this.evaluationTeacherName = this.evaluationTeacherName.substring(0, 3);
    }
    @Override
    public void stdCalculator(List<Map<Integer, String>> headInfoMap){
        try{
            if(headInfoMap != null) // null的含义就是 我不想更新这个时间
                this.evaluationTerm =
                        ExcelDataProcessUtil.getTermFromHead(headInfoMap.get(0).get(0));
        }
        catch (Exception e){
            System.out.println("Invalid currentHeadInfo, a integer for current date(year) Expected");
            e.printStackTrace();
        }
    }

    @Override
    public void transformAndSave(ArrayList<S2Evaluation> list, int size) {
        EvaluationService evaluationService =
                (EvaluationService) SpringUtil.getBean("evaluationServiceImpl");
        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");

        ArrayList<String> accountNameList = new ArrayList<>(size);

        // 这里 因为标准课时 前边累加了 都是指定系数1 没有直接指定标准课时的 所以不calculation
        for (S2Evaluation s2Evaluation : list) {
            accountNameList.add(s2Evaluation.evaluationTeacherName);
        }
        ArrayList<Integer> accountIdList = accountMapper.selectBatchIdByName(accountNameList);
        ArrayList<EvaluationPO> evaluationPOS = new ArrayList<>(size);

        // accountId 注入到CourseTheory
        S2Evaluation s2Evaluation = null;
        for(int i = 0 ; i < list.size() ; i++){
            try{
                s2Evaluation = list.get(i);
                s2Evaluation.setEvaluationTeacherId(accountIdList.get(i));
                // 有些字段实在太长 删减点 别太过了
                s2Evaluation.fieldStandardized();
                // 内置转换函数 能够将CourseTheory转换为Course 然后save！
                evaluationPOS.add(new EvaluationPO(s2Evaluation));
            }
            catch (Exception e){
                e.printStackTrace();
                throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
            }
        }
        try{
            evaluationService.saveBatch(evaluationPOS);

        }
        catch (Exception e){
            e.printStackTrace();
            throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
        }
        finally {
            evaluationPOS.clear();
            accountIdList.clear();
            accountNameList.clear();
        }
    }
}
