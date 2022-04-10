package cn.ryanalexander.alibaba.domain.bo.excel;

import cn.ryanalexander.alibaba.domain.exceptions.AppException;
import cn.ryanalexander.alibaba.domain.po.EvaluationPO;
import cn.ryanalexander.alibaba.domain.po.TeacherTitlePO;
import cn.ryanalexander.alibaba.mapper.AccountMapper;
import cn.ryanalexander.alibaba.service.EvaluationService;
import cn.ryanalexander.alibaba.service.TeacherTitleService;
import cn.ryanalexander.alibaba.service.tool.ExcelDataProcessUtil;
import cn.ryanalexander.alibaba.service.tool.SpringUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TitleInfo
 * @Description
 * @Author ryan
 * @Date 2022/3/10 20:00
 * @Version 1.0.0-Beta
 **/
@Data
@ToString
@AllArgsConstructor
@ApiModel("职称信息表")
public class TitleInfo implements ExcelEntity<TitleInfo>{
    @ExcelProperty(value = "团队")
    private String titleTeam;

    @ExcelProperty(value = "姓名")
    private String titleTeacherName;

    @ExcelProperty(value = "职工号")
    private Integer titleTeacherId;

    @ExcelProperty(value = "职称")
    private String titleName;

    @ExcelProperty(value = "系列")
    private String titleType;

    @ExcelProperty(value = "职称级别")
    private String titleLevel;

    private Integer titleYear;

    @Override
    public boolean isValidated() {
        // 有名字 有百分占比 正常情况！
        return titleTeacherName != null;
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
        if(this.titleTeacherName.length() > 24)
            this.titleTeacherName = this.titleTeacherName.substring(0, 24);
    }
    @Override
    public void stdCalculator(List<Map<Integer, String>> headInfoMap){
        try{
            if(headInfoMap != null) // null的含义就是 我不想更新这个时间
                this.titleYear = Integer.valueOf(
                        ExcelDataProcessUtil.getTermFromHead(headInfoMap.get(0).get(0)));
        }
        catch (Exception e){
            System.out.println("Invalid currentHeadInfo, a integer for current date(year) Expected");
            e.printStackTrace();
        }
    }

    @Override
    public void transformAndSave(ArrayList<TitleInfo> list, int size) {
        TeacherTitleService teacherTitleService =
                (TeacherTitleService) SpringUtil.getBean("teacherTitleServiceImpl");
        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");

        ArrayList<AccountIdAndEmail> accountIdAndEmails = new ArrayList<>(size);
        ArrayList<TeacherTitlePO> teacherTitlePOS = new ArrayList<>(size);

        // accountId 注入到CourseTheory
        TitleInfo titleInfo = null;
        for(int i = 0 ; i < list.size() ; i++){
            try{
                titleInfo = list.get(i);
                titleInfo.fieldStandardized();
                // 内置转换函数 能够将CourseTheory转换为Course 然后save！
                teacherTitlePOS.add(new TeacherTitlePO(titleInfo));
                accountIdAndEmails.add(new AccountIdAndEmail(titleInfo));
            }
            catch (Exception e){
                e.printStackTrace();
                throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
            }
        }
        try{
            teacherTitleService.saveBatch(teacherTitlePOS);
            accountMapper.saveOrIgnoreBatchByNameAndId(accountIdAndEmails);

        }
        catch (Exception e){
            e.printStackTrace();
            throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
        }
        finally {
            teacherTitlePOS.clear();
            accountIdAndEmails.clear();
        }
    }
}
