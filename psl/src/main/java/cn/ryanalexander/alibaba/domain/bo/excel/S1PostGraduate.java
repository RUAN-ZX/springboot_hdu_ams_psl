package cn.ryanalexander.alibaba.domain.bo.excel;

import cn.ryanalexander.alibaba.domain.exceptions.AppException;
import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.alibaba.domain.exceptions.code.SubjectEnum;
import cn.ryanalexander.alibaba.domain.po.S1PO;
import cn.ryanalexander.alibaba.domain.po.StudentPO;
import cn.ryanalexander.alibaba.domain.po.ThesisDesignPO;
import cn.ryanalexander.alibaba.mapper.AccountMapper;
import cn.ryanalexander.alibaba.service.S1Service;
import cn.ryanalexander.alibaba.service.StudentService;
import cn.ryanalexander.alibaba.service.ThesisDesignService;
import cn.ryanalexander.alibaba.service.tool.SpringUtil;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: PostGraduateWorkload
 * @Description
 * @Author ryan
 * @Date 2022/3/10 20:13
 * @Version 1.0.0-Beta
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("研究生理论课工作量")
@ToString
@ExcelIgnoreUnannotated
public class S1PostGraduate implements ExcelEntity<S1PostGraduate>{
    @ExcelProperty(value = "姓名")
    private String s1TeacherName;

    @ExcelProperty(value = "研究生教学业绩点")
    private Double s1KpiPostgraduate;

    private Integer s1TeacherId;

    private Integer s1Year;

    @Override
    public boolean isValidated() {
        // 有名字 有百分占比 正常情况！
        return s1TeacherName != null && s1KpiPostgraduate != null;
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
        if(this.s1TeacherName.length() > 24)
            this.s1TeacherName = this.s1TeacherName.substring(0, 24);
    }
    @Override
    public void stdCalculator(List<Map<Integer, String>> headInfoMap){
        try{
            if(headInfoMap != null) // null的含义就是 我不想更新这个时间
                this.s1Year = Integer.valueOf(headInfoMap.get(0).get(0));
        }
        catch (Exception e){
            System.out.println("Invalid currentHeadInfo, a integer for current date(year) Expected");
            e.printStackTrace();
        }
    }

    @Override
    public void transformAndSave(ArrayList<S1PostGraduate> list, int size) {
        S1Service s1Service = (S1Service) SpringUtil.getBean("s1ServiceImpl");
        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");

        ArrayList<String> accountNameList = new ArrayList<>(size);

        // 这里 因为标准课时 前边累加了 都是指定系数1 没有直接指定标准课时的 所以不calculation
        for (S1PostGraduate s1PostGraduate : list) {
            accountNameList.add(s1PostGraduate.s1TeacherName);
        }
        ArrayList<Integer> accountIdList = accountMapper.selectBatchIdByName(accountNameList);
        ArrayList<S1PO> s1POS = new ArrayList<>(size);

        // accountId 注入到CourseTheory
        S1PostGraduate s1PostGraduate = null;
        for(int i = 0 ; i < list.size() ; i++){
            try{
                s1PostGraduate = list.get(i);
                s1PostGraduate.setS1TeacherId(accountIdList.get(i));
                // 有些字段实在太长 删减点 别太过了
                s1PostGraduate.fieldStandardized();
                // 内置转换函数 能够将CourseTheory转换为Course 然后save！
                s1POS.add(new S1PO(s1PostGraduate));
            }
            catch (Exception e){
                e.printStackTrace();
                throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
            }
        }
        try{
            s1Service.saveBatch(s1POS);

        }
        catch (Exception e){
            e.printStackTrace();
            throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
        }
        finally {
            s1POS.clear();
            accountIdList.clear();
            accountNameList.clear();
        }
    }

}