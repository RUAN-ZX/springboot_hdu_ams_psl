package cn.ryanalexander.psl.domain.bo.excel.out;

import cn.ryanalexander.common.domain.exceptions.AppException;
import cn.ryanalexander.psl.domain.bo.excel.ExcelEntity;
import cn.ryanalexander.psl.domain.po.AccountPO;
import cn.ryanalexander.psl.domain.po.SDetailPO;
import cn.ryanalexander.psl.domain.po.SFinalPO;
import cn.ryanalexander.psl.mapper.AccountMapper;
import cn.ryanalexander.psl.mapper.SFinalMapper;
import cn.ryanalexander.psl.service.SFinalService;
import cn.ryanalexander.psl.service.tool.DataUtil;
import cn.ryanalexander.psl.service.tool.SpringUtil;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
 * @author ryan 2022/4/11 16:16
 * @since 1.0.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("成绩汇总表")
@ToString
@ExcelIgnoreUnannotated
public class SFinal implements ExcelEntity<SFinal> {
    // List index就完事了
    @ExcelProperty(value = "序号")
    private String id;

    @ExcelProperty(value = "工号")
    private String sFinalTeacherId;

    @ExcelProperty(value = "姓名")
    private String sFinalTeacherName;

    @ExcelProperty(value = "职称")
    private String sFinalTitleLevel; // 不是名字 是等级！

    // 理论+实验 纯课时 没有标准！
    // 这个标准还是个问题
    @ExcelProperty(value = "承担主讲课程学时数是否不低于64学时")
    private String sFinalCourseMain;

    @ExcelProperty(value = "考核分数")
    private String sFinalScore; // postGraduateKpi * 100

    @ExcelProperty(value = "考核等级")
    private String sFinalGrade; // postGraduateKpi * 100

    @ExcelProperty(value = "备注")
    private String sNote; // postGraduateKpi * 100

    private Integer sFinalYear;
    // 用于PO转ExcelEntity 实现表输出的
    public SFinal(SDetailPO sDetailPO){

    }
    @Override
    public boolean isValidated() {
        // 有名字 有百分占比 正常情况！
        return sFinalTeacherName != null;
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
        this.sFinalTeacherName = DataUtil.getChineseCharacter(this.sFinalTeacherName);

        if(this.sNote != null && this.sNote.length() > 32)
            this.sNote = this.sNote.substring(0,32);
    }
    @Override
    public void stdCalculator(List<Map<Integer, String>> headInfoMap){
//        System.out.println(headInfoMap);
        try{
            if(headInfoMap != null) // null的含义就是 我不想更新这个时间
                this.sFinalYear = DataUtil.string2integer(headInfoMap.get(0).get(0).substring(0,4));
        }
        catch (Exception e){
            System.out.println("Invalid currentHeadInfo, a integer for current date(year) Expected");
            e.printStackTrace();
        }
    }

    @Override
    public void transformAndSave(ArrayList<SFinal> list, int size) {
        SFinalMapper sFinalMapper =
                (SFinalMapper) SpringUtil.getBean("SFinalMapper");
        SFinalService sFinalService =
                (SFinalService) SpringUtil.getBean("SFinalServiceImpl");
//        SFinalService sFinalService =
//                (SFinalService) SpringUtil.getBean("sFinalServiceImpl");

        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");
//
//        ArrayList<String> accountNameList = new ArrayList<>(size);
//        // 这里 因为标准课时 前边累加了 都是指定系数1 没有直接指定标准课时的 所以不calculation
//        for (SFinal sFinal : list)
//            accountNameList.add(sFinal.sFinalTeacherName);
//
//        ArrayList<Integer> accountIdList = accountMapper.selectBatchIdByName(accountNameList);

        ArrayList<SFinalPO> sFinalPOS = new ArrayList<>(size);

        // accountId 注入到CourseTheory
//        SFinal sFinal = null;
        for (SFinal sFinal : list) {
            try {
//                sFinal = aFinal;
                // 作为读表的时候是不看表原有teacherId的！
//                sFinal.setSFinalTeacherId(String.valueOf(accountIdList.get(i)));
                // 有些字段实在太长 删减点 别太过了
                sFinal.fieldStandardized();
                // 13 - 15年 表没有工号！
                if(sFinal.getSFinalTeacherId() == null){
                    AccountPO accountPO = accountMapper.selectOne(new QueryWrapper<AccountPO>()
                            .select("account_id")
                            .eq("account_name", sFinal.getSFinalTeacherName())
                            .last("limit 1"));
                    if(accountPO == null) continue;
                    sFinal.setSFinalTeacherId(String.valueOf(accountPO.getAccountId()));
                }

//                System.out.println(sFinal);
                sFinalPOS.add(new SFinalPO(sFinal));
            } catch (Exception e) {
                e.printStackTrace();
                throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
            }
        }
        try{
            sFinalService.saveBatch(sFinalPOS);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
        }
        finally {
            sFinalPOS.clear();
//            accountIdList.clear();
//            accountNameList.clear();
        }
    }
}
