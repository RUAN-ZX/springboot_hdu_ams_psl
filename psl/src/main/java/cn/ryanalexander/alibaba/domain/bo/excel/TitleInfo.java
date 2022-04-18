package cn.ryanalexander.alibaba.domain.bo.excel;

import cn.ryanalexander.alibaba.domain.po.AccountPO;
import cn.ryanalexander.alibaba.domain.po.TeacherPO;
import cn.ryanalexander.alibaba.mapper.AccountMapper;
import cn.ryanalexander.alibaba.mapper.TeacherMapper;
import cn.ryanalexander.alibaba.service.AccountService;
import cn.ryanalexander.alibaba.service.TeacherService;
import cn.ryanalexander.alibaba.service.tool.SpringUtil;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@ApiModel("职称信息表")
@NoArgsConstructor
@AllArgsConstructor
@ExcelIgnoreUnannotated
public class TitleInfo implements ExcelEntity<TitleInfo>{
    @ExcelProperty(value = "团队")
    private String teacherTeam;

    @ExcelProperty(value = "姓名")
    private String teacherName;

    @ExcelProperty(value = "职工号")
    private String teacherId;

    @ExcelProperty(value = "职称")
    private String teacherTitleName;

    @ExcelProperty(value = "系列")
    private String teacherType;

    @ExcelProperty(value = "职称级别")
    private String teacherLevel;

    private String teacherTitleYear;

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
        if(this.teacherName.length() > 24)
            this.teacherName = this.teacherName.substring(0, 24);
    }
    @Override
    public void stdCalculator(List<Map<Integer, String>> headInfoMap){
        try{
            if(headInfoMap != null) // null的含义就是 我不想更新这个时间 不过每年都有职称表！
                this.teacherTitleYear = headInfoMap.get(0).get(0);
        }
        catch (Exception e){
            System.out.println("Invalid currentHeadInfo, a integer for current date(year) Expected");
            e.printStackTrace();
        }
    }

    @Override
    public void transformAndSave(ArrayList<TitleInfo> list, int size) {
        TeacherMapper teacherMapper =
                (TeacherMapper) SpringUtil.getBean("teacherMapper");
        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");

//        TeacherService teacherService =
//                (TeacherService) SpringUtil.getBean("teacherServiceImpl");
//        AccountService accountService =
//                (AccountService) SpringUtil.getBean("accountServiceImpl");
//        ArrayList<AccountIdAndEmail> accountIdAndEmails = new ArrayList<>(size);
        ArrayList<AccountPO> accountPOS = new ArrayList<>(size);
        ArrayList<TeacherPO> teacherPOS = new ArrayList<>(size);

        // accountId 注入到CourseTheory
        TitleInfo titleInfo = null;
        for(int i = 0 ; i < list.size() ; i++){
            try{
                titleInfo = list.get(i);
                titleInfo.fieldStandardized();
                // 内置转换函数 能够将CourseTheory转换为Course 然后save！
                teacherPOS.add(new TeacherPO(titleInfo));
                accountPOS.add(new AccountPO(titleInfo));
            }
            catch (Exception e){
                e.printStackTrace();
//                throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
            }
        }
        try{
            teacherMapper.saveOrUpdateBatch(teacherPOS);
            // 职称表更新职工号+id的 有就没必要管了
            accountMapper.saveOrIgnoreBatchByNameAndId(accountPOS);

        }
        catch (Exception e){
            e.printStackTrace();
//            throw new AppException(e, "CourseShortTerm", "transformAndSave CourseShortTerm.saveBatch(courses)");
        }
        finally {
            teacherPOS.clear();
            accountPOS.clear();
        }
    }
}
