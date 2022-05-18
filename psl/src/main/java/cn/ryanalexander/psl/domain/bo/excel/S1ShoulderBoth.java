package cn.ryanalexander.psl.domain.bo.excel;

import cn.ryanalexander.common.domain.exceptions.AppException;
import cn.ryanalexander.psl.domain.po.ShoulderBothPO;
import cn.ryanalexander.psl.mapper.AccountMapper;
import cn.ryanalexander.psl.service.ShoulderBothService;
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
 * @ClassName: WordloadPractical
 * @Description
 * @Author ryan
 * @Date 2022/3/11 22:35
 * @Version 1.0.0-Beta
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("双肩挑")
@ToString
@ExcelIgnoreUnannotated
public class S1ShoulderBoth implements ExcelEntity<S1ShoulderBoth>{
    //    姓名	学校标志性成果业绩分（本科）	学校非标志性成果业绩分（本科）	学院专项（本科）	双肩挑
    @ExcelProperty(value = "姓名")
    private String shoulderBothTeacherName;

    @ExcelProperty(value = "双肩挑")
    private Integer shoulderBothHours;

    private Integer shoulderBothYear;
    private Integer shoulderBothTeacherId;

    @Override
    public boolean isValidated() {
        // 有名字 有百分占比 正常情况！
        return shoulderBothTeacherName != null;
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
        if(this.shoulderBothTeacherName.length() > 3)
            this.shoulderBothTeacherName = this.shoulderBothTeacherName.substring(0, 3);
    }
    @Override
    public void stdCalculator(List<Map<Integer, String>> headInfoMap){
        try{
            if(headInfoMap != null) // null的含义就是 我不想更新这个时间
                this.shoulderBothYear = Integer.valueOf(headInfoMap.get(0).get(0));
        }
        catch (Exception e){
            System.out.println("Invalid currentHeadInfo, a integer for current date(year) Expected");
            e.printStackTrace();
        }
    }

    @Override
    public void transformAndSave(ArrayList<S1ShoulderBoth> list, int size) {
        ShoulderBothService shoulderBothService = (ShoulderBothService) SpringUtil.getBean("shoulderBothServiceImpl");
        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");

        ArrayList<String> accountNameList = new ArrayList<>(size);

        // 这里 因为标准课时 前边累加了 都是指定系数1 没有直接指定标准课时的 所以不calculation
        for (S1ShoulderBoth s1ShoulderBoth : list) {
            accountNameList.add(s1ShoulderBoth.shoulderBothTeacherName);
        }
        ArrayList<Integer> accountIdList = accountMapper.selectBatchIdByName(accountNameList);
        ArrayList<ShoulderBothPO> shoulderBothPOS = new ArrayList<>(size);

        // accountId 注入到CourseTheory
        S1ShoulderBoth s1ShoulderBoth = null;
        for(int i = 0 ; i < list.size() ; i++){
            try{
                s1ShoulderBoth = list.get(i);
                s1ShoulderBoth.setShoulderBothTeacherId(accountIdList.get(i));
                // 有些字段实在太长 删减点 别太过了
                s1ShoulderBoth.fieldStandardized();
                // 内置转换函数 能够将CourseTheory转换为Course 然后save！
                shoulderBothPOS.add(new ShoulderBothPO(s1ShoulderBoth));
            }
            catch (Exception e){
                e.printStackTrace();
                throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
            }
        }
        try{
            shoulderBothService.saveBatch(shoulderBothPOS);

        }
        catch (Exception e){
            e.printStackTrace();
            throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
        }
        finally {
            shoulderBothPOS.clear();
            accountIdList.clear();
            accountNameList.clear();
        }
    }
}
