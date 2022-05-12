package cn.ryanalexander.psl.domain.bo.excel;

import cn.ryanalexander.psl.domain.exceptions.AppException;
import cn.ryanalexander.psl.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.psl.domain.exceptions.code.SubjectEnum;
import cn.ryanalexander.psl.domain.po.StudentPO;
import cn.ryanalexander.psl.domain.po.ThesisDesignPO;
import cn.ryanalexander.psl.mapper.AccountMapper;
import cn.ryanalexander.psl.service.StudentService;
import cn.ryanalexander.psl.service.ThesisDesignService;
import cn.ryanalexander.psl.service.tool.DataUtil;
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
 * @ClassName: CourseThesisDesign
 * @Description
 * @Author ryan
 * @Date 2022/3/10 19:50
 * @Version 1.0.0-Beta
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("毕业设计")
@ExcelIgnoreUnannotated
public class S1ThesisDesign implements ExcelEntity<S1ThesisDesign>, Cloneable{

    @ExcelProperty(value = "备注")
    private String thesisDesignNote;

    @ExcelProperty(value = "教师姓名")
    private String thesisDesignTeacherName;

    @ExcelProperty(value = "姓名")
    private String thesisDesignStudentName;

    @ExcelProperty(value = "学号")
    private String studentId;

    @ExcelProperty(value = "专业名称")
    private String studentMajor;

    @ExcelProperty(value = "成绩")
    private String thesisDesignGrade;

    @ExcelProperty(value = "系数1")
    private Double thesisDesignFactor1;

    @ExcelProperty(value = "优秀系数")
    private Double thesisDesignFactor2;

    @ExcelProperty(value = "T1")
    private Double thesisDesignT1;

    @ExcelProperty(value = "标准学时")
    private Integer thesisDesignStd;

    @ExcelProperty(value = "毕业设计题目")
    private String thesisDesignName;

    // 表头捕获
    private Integer thesisDesignYear;
    // 获取
    private Integer thesisDesignTeacherId;


    @Override
    public boolean isValidated() {
        // 有名字 有百分占比 正常情况！
//        return thesisDesignFactor1 != null || thesisDesignTeacherName != null;
        return thesisDesignStudentName != null || studentId != null;
    }

    @Override
    public boolean multiStart(){
        return thesisDesignStudentName != null;
    }
    // 这里 之前也是multiStart 应该怎么处理
    @Override
    public boolean prevIsMultiHeadOperation(ExcelEntity mask){
        return true;
    }

    @Override // 下一行的补充数据
    public boolean multiContinue(){
        return thesisDesignStudentName == null && studentId == null;
    }
    @Override
    public void fieldStandardized(){

        this.thesisDesignGrade = DataUtil.getChineseCharacter(thesisDesignGrade);
        // 处理老师名字
        this.thesisDesignTeacherName =
                DataUtil.getChineseCharacter(this.thesisDesignTeacherName);

        // 用对象前 先检测null
        if(this.thesisDesignNote != null && this.thesisDesignNote.length() > 24)
            this.thesisDesignNote = this.thesisDesignNote.substring(0, 24);

        if(this.thesisDesignTeacherName != null && this.thesisDesignTeacherName.length() > 3)
            this.thesisDesignTeacherName = this.thesisDesignTeacherName.substring(0, 3);

        if(this.thesisDesignStudentName != null && this.thesisDesignStudentName.length() > 3)
            this.thesisDesignStudentName = this.thesisDesignStudentName.substring(0, 3);

    }
    @Override
    public void stdCalculator(List<Map<Integer, String>> headInfoMap){
        try{
            if(headInfoMap != null) // null的含义就是 我不想更新这个时间
                this.thesisDesignYear = Integer.valueOf(headInfoMap.get(0).get(0));
        }
        catch (Exception e){
            System.out.println("Invalid currentHeadInfo, a integer for current date(year) Expected");
            e.printStackTrace();
        }

        if(this.thesisDesignFactor1 == null){
            return; // 直接使用指定的std了。别的都为0
        }
        double factor1 = this.thesisDesignFactor1;
        // 有些记录不规范 没有写 默认为0咯
        double factor2 = this.thesisDesignFactor2 == null ? 0.0 : this.thesisDesignFactor2;
        double t1 = this.thesisDesignT1;

        if(this.thesisDesignStd == null)
            this.thesisDesignStd = (int) Math.round((factor1 + factor2) * t1);
    }
    // 只有多人才会调用这个！
    @Override
    public ExcelEntity copyFromMasterMask(ExcelEntity data) {
        S1ThesisDesign result = null;
        S1ThesisDesign share = (S1ThesisDesign) data;
        try {
            // 因为是自己clone自己 所以类型一定是对的
            result = (S1ThesisDesign) this.clone();
            share.stdCalculator(null);
            // 合并！
            // 头已经计算过了 在multiStart那边
            result.thesisDesignStd += Math.round(share.getThesisDesignStd());
            result.thesisDesignNote = share.thesisDesignNote; // 补充的note也带进来为好！

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new AppException(new ErrorCode(SubjectEnum.INTERNAL));
        } catch (Exception e){
            e.printStackTrace();
        }
        // 两个合并std 其他信息克隆
        return result;
    }

    @Override
    public void transformAndSave(ArrayList<S1ThesisDesign> list, int size) {
        ThesisDesignService thesisDesignService = (ThesisDesignService) SpringUtil.getBean("thesisDesignServiceImpl");
//        StudentService studentService = (StudentService) SpringUtil.getBean("studentServiceImpl");
        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");

        ArrayList<String> accountNameList = new ArrayList<>(size);

        // 这里 因为标准课时 前边累加了 都是指定系数1 没有直接指定标准课时的 所以不calculation
        for (S1ThesisDesign s1ThesisDesign : list) {
            accountNameList.add(s1ThesisDesign.thesisDesignTeacherName);
        }
        ArrayList<Integer> accountIdList = accountMapper.selectBatchIdByName(accountNameList);
        ArrayList<ThesisDesignPO> thesisDesignPOS = new ArrayList<>(size);
//        ArrayList<StudentPO> studentPOS = new ArrayList<>(size);

        // accountId 注入到CourseTheory
        S1ThesisDesign s1ThesisDesign = null;
        for(int i = 0 ; i < list.size() ; i++){
            try{
                s1ThesisDesign = list.get(i);
                s1ThesisDesign.setThesisDesignTeacherId(accountIdList.get(i));
                // 有些字段实在太长 删减点 别太过了
                s1ThesisDesign.fieldStandardized();
                // 内置转换函数 能够将CourseTheory转换为Course 然后save！
                thesisDesignPOS.add(new ThesisDesignPO(s1ThesisDesign));
//                studentPOS.add(new StudentPO(s1ThesisDesign));
            }
            catch (Exception e){
                e.printStackTrace();
                throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
            }
        }
        try{
            thesisDesignService.saveBatch(thesisDesignPOS);
//            studentService.saveBatch(studentPOS);

        }
        catch (Exception e){
            e.printStackTrace();
            throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
        }
        finally {
//            studentPOS.clear();
            thesisDesignPOS.clear();
            accountIdList.clear();
            accountNameList.clear();
        }
    }
}