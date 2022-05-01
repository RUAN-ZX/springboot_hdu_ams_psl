package cn.ryanalexander.psl.domain.bo.excel;

import cn.ryanalexander.psl.domain.exceptions.AppException;
import cn.ryanalexander.psl.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.psl.domain.exceptions.code.SubjectEnum;
import cn.ryanalexander.psl.domain.po.AchievementPO;
import cn.ryanalexander.psl.mapper.AccountMapper;
import cn.ryanalexander.psl.mapper.AchievementMapper;
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
 * <p><b></b></p>
 *
 * <p>2022/4/10 </p>
 *
 * @author ryan 2022/4/10 12:32
 * @since 1.0.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("实践成果")
@ToString
@ExcelIgnoreUnannotated
public class S1Achievement implements ExcelEntity<S1Achievement>, Cloneable{

    @ExcelProperty("姓名")
    private String achievementTeacherName;

    @ExcelProperty("成果名称")
    private String achievementName;

    @ExcelProperty("考核项")
    private String achievementLevel;

    @ExcelProperty("类别")
    private String achievementCategory;

    @ExcelProperty("获奖\n等级")
    private Integer achievementRank;

    @ExcelProperty("任务考核\n指标分值")
    private Double achievementKpi;

    @ExcelProperty("备注")
    private String achievementNote;

    @ExcelProperty("分值")
    private Double achievementKpiS34;
    @ExcelProperty("计分项")
    private String achievementKpiCategory;

//    private String achievementEvidence1;
//    private String achievementEvidence2;
//    private String achievementEvidence3;

    private Integer achievementYear;

//    @ExcelProperty("工号")
    private Integer achievementTeacherId;

    private Integer achievementType;

    @Override
    public boolean isValidated() {
        // todo 根据multiStart的机制 对于 陈龙、马学条 但是下边又不写分成的情况 会自动略过
        // 不写分成也对 因为kpi=0 没必要统计 唯一不好的就是老师查不到自己有这个项目 但应该问题不大。。
        return achievementKpi != null && achievementTeacherName != null;

    }

    @Override
    public boolean multiStart(){
        return DataUtil.multiStart(achievementTeacherName);
    }

    // 多行 多人头出现
    @Override
    public boolean prevIsMultiHeadOperation(ExcelEntity mask){
        return false;
    }
    @Override // 计划 课程代码那边可能要写分配的占比！
    public boolean multiContinue(){
        return achievementName == null && achievementCategory == null;
    }
    @Override
    public void fieldStandardized(){
        // 用对象前 先检测null
        if(this.achievementNote != null && this.achievementNote.length() > 32)
            this.achievementNote = this.achievementNote.substring(0, 32);

        if(this.achievementCategory != null && this.achievementCategory.length() > 24)
            this.achievementCategory = this.achievementCategory.substring(0, 24);

        if(this.achievementLevel != null && this.achievementLevel.length() > 48)
            this.achievementLevel = this.achievementLevel.substring(0, 48);

        if(this.achievementName != null && this.achievementName.length() > 150)
            this.achievementName = this.achievementName.substring(0, 150);

        if(this.achievementTeacherName != null && this.achievementTeacherName.length() > 3)
            this.achievementTeacherName = this.achievementTeacherName.substring(0, 3);
    }

    @Override
    public ExcelEntity copyFromMasterMask(ExcelEntity data) {
        S1Achievement result;
        S1Achievement share = (S1Achievement) data;
        try {
            // 此时this就是多人的模板 result就是每个老师的分成 对象实例
            // share就是excel读取的分成 数据
            result = (S1Achievement) this.clone();
            result.achievementTeacherName = share.getAchievementTeacherName();
            result.achievementKpi = share.getAchievementKpi(); // 直接取出KPI
        } catch (CloneNotSupportedException e) {
            throw new AppException(new ErrorCode(SubjectEnum.INTERNAL));
        }
        return result;
    }
    @Override
    public void stdCalculator(List<Map<Integer, String>> headMapList){
        try{
            // null的含义就是 我不想更新这个时间
            if(headMapList != null){
                this.achievementYear = Integer.valueOf(headMapList.get(0).get(0));
                this.achievementType =
                        DataUtil.transformAchievementType(headMapList.get(0).get(100));
            }
        }
        catch (Exception e){
            System.out.println("Invalid currentHeadInfo, a integer for current date(year) Expected");
            e.printStackTrace();
        }
    }
    @Override
    public void transformAndSave(ArrayList<S1Achievement> list, int size) {
        AchievementMapper achievementMapper =
                (AchievementMapper) SpringUtil.getBean("achievementMapper");

        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");

        ArrayList<String> accountNameList = new ArrayList<>(size);

        for (S1Achievement s1Achievement : list) {
            accountNameList.add(s1Achievement.getAchievementTeacherName());
        }
        ArrayList<Integer> accountIdList = accountMapper.selectBatchIdByName(accountNameList);
        ArrayList<AchievementPO> achievementPOS = new ArrayList<>(size);

        // accountId 注入到CourseTheory
        S1Achievement s1Achievement;
        for(int i = 0 ; i < list.size() ; i++){
            try{
                s1Achievement = list.get(i);
                s1Achievement.setAchievementTeacherId(accountIdList.get(i));
                s1Achievement.fieldStandardized();
                achievementPOS.add(new AchievementPO(s1Achievement));
            }
            catch (Exception e){
                e.printStackTrace();
//                throw new AppException(e, "CourseTheory", "transformAndSave courseService.saveBatch(courses)");
            }
        }
        try{
            achievementMapper.saveOrUpdateBatch(achievementPOS);

        }
        catch (Exception e){
            e.printStackTrace();
//            throw new AppException(e, "CourseTheory", "transformAndSave courseService.saveBatch(courses)");
        }
        finally {
            achievementPOS.clear();
            accountIdList.clear();
            accountNameList.clear();
        }
    }
}
