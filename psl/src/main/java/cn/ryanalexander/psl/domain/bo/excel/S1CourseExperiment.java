package cn.ryanalexander.psl.domain.bo.excel;

import cn.ryanalexander.psl.domain.exceptions.AppException;
import cn.ryanalexander.psl.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.psl.domain.exceptions.code.SubjectEnum;
import cn.ryanalexander.psl.domain.po.CoursePO;
import cn.ryanalexander.psl.mapper.AccountMapper;
import cn.ryanalexander.psl.service.CourseService;
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

 * <p>2022-04-08 </p>

 * @since
 * @author RyanAlexander 2022-04-08 19:05
 * @apiNote 对于多人 且多课号的情况 与理论课的逻辑相同 记录课程信息（最下边的）+使用给定学时大小
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("工作量_实验课程")
@ToString
@ExcelIgnoreUnannotated
public class S1CourseExperiment implements ExcelEntity<S1CourseExperiment>, Cloneable{

    @ExcelProperty(value = "选课课号")
    private String courseNum;

    @ExcelProperty(value = "学期")
    private String courseTerm;

    @ExcelProperty(value = "上课时间")
    private String courseTime;

    @ExcelProperty(value = "课程名称")
    private String courseName;

    @ExcelProperty(value = "上课地点")
    private String courseAddress;

    @ExcelProperty(value = "教师姓名")
    private String courseTeacherName;

    @ExcelProperty(value = "已选人数")
    private Integer courseCapacity;

    @ExcelProperty(value = "班级规模系数1")
    private Double courseCapacityFactor1;

    @ExcelProperty(value = "班级规模系数2")
    private Double courseCapacityFactor2;

    @ExcelProperty(value = "双语")
    private String courseBilingual;

    @ExcelProperty(value = "课改")
    private String courseReform;

    @ExcelProperty(value = "类别系数")
    private Double courseFactor;

    @ExcelProperty(value = "优课优酬")
    private Double coursePrior;

    @ExcelProperty(value = "上机学时")
    private Double courseHoursOp;

    @ExcelProperty(value = "学分")
    private Double coursePoints;

    // 不能使用Character捕获！！！
    @ExcelProperty(value = "性质")
    private String courseProperties;

    @ExcelProperty(value = "教务处备注")
    private String courseNote1;

    @ExcelProperty(value = "备注")
    private String courseNote2;

    @ExcelProperty(value = "总学时")
    private Double courseHours;

    @ExcelProperty(value = "标准学时")
    private Double courseHoursStd;

//    private Double courseHoursTheory = 0.0;
    private Integer courseTeacherId;

    private Double courseHoursExp;

    private Integer courseHoursExpStd;


    @Override
    public boolean isValidated() {
        // 有名字 名字存在 有时长 这记录才有用！
//        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");
//        boolean isAccountExist = accountMapper.selectIdByName(courseTeacherName) != null;
        // TODO 挨个检查太慢了 不知道有没有啥好办法 不过这里也不需要了 找不到就让记录留下来 有朝一日update上
        //  反正也不影响在案老师的工作量计算
        return courseTeacherName != null && (courseHours != null || courseHoursExpStd != null);

    }

    @Override
    public boolean multiStart(){
        return DataUtil.multiStart(courseTeacherName);
    }
    @Override
    public boolean prevIsMultiHeadOperation(ExcelEntity mask){
        S1CourseTheory s1CourseTheory = (S1CourseTheory) mask;
        this.courseHoursStd += s1CourseTheory.courseHoursStd;
        return false; // 不存储多头
    }
    @Override // 计划 课程代码那边可能要写分配的占比！
    public boolean multiContinue(){
        return courseName == null && courseTerm == null ;
    }
    @Override
    public void fieldStandardized(){
        // 用对象前 先检测null
        if(this.courseNote1 != null && this.courseNote1.length() > 64)
            this.courseNote1 = this.courseNote1.substring(0, 64);

        if(this.courseNote2 != null && this.courseNote2.length() > 64)
            this.courseNote2 = this.courseNote2.substring(0, 64);

        if(this.courseTime != null && this.courseTime.length() > 64)
            this.courseTime = this.courseTime.substring(0, 64);

        if(this.courseAddress != null && this.courseAddress.length() > 64)
            this.courseAddress = this.courseAddress.substring(0, 64);

        if(this.courseName != null && this.courseName.length() > 24)
            this.courseName = this.courseName.substring(0, 24);
    }

    @Override
    public ExcelEntity copyFromMasterMask(ExcelEntity data) {
        S1CourseExperiment result = null;
        S1CourseExperiment share = (S1CourseExperiment) data;
        try {
            // 此时this就是多人的模板 result就是每个老师的分成 对象实例
            // share就是excel读取的分成 数据
            result = (S1CourseExperiment) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new AppException(new ErrorCode(SubjectEnum.INTERNAL));
        }

        // 老师名字必须改
        result.courseTeacherName = share.getCourseTeacherName();
        // 需要计算 且 可以计算的时候
        // todo 这里有个致命点 就是如果即没有标准学时 也不知道分成具体情况 会导致hoursStd=null！
        if(share.courseHoursStd == null && share.courseNum != null){
            // 获取比例 原来是通过给定学时 但是这不适用于多个多人课头 所以 这里直接设计成 默认通过课号写的百分比来搞定！
//        double ratio = share.getCourseHours() / result.courseHours;
            double ratio = DataUtil.getRatio(share.courseNum);
            result.courseHoursStd *= ratio; // 分成
            // 总课时什么的没必要分成 就是作为一个课程信息的展示 多好啊
        }
        result.courseHoursStd = share.getCourseHoursStd(); // 有标准课时可用就拿来咯


        return result;
    }

    @Override
    public void stdCalculator(List<Map<Integer, String>> headInfoMap){
        if(this.courseHours == null){
            this.courseHoursExpStd = (int) Math.round(this.courseHoursStd);
            this.courseHoursExp = 0.0;
            return; // 直接使用指定的std了。别的都为0
        }
        double hours = this.courseHours;
        double factor = this.courseFactor;
        double prior = this.coursePrior;
        this.courseHoursExp = hours;

        double capacity_factor_1 = DataUtil.getCapacityFactorByProperty(
                this.courseProperties,
                this.courseCapacity
        );
        double capacity_factor_2 = this.courseCapacityFactor2;

        factor *= (capacity_factor_1 * capacity_factor_2 * prior); // 归在一起了 优课×规模×容量
        // 3位小数 而且
        this.courseCapacityFactor1 = capacity_factor_1;

        // 虽然excel可能获取到double 不管 直接round
        // 如果没有就计算
        if(this.courseHoursStd == null)
            this.courseHoursStd = hours * factor;

        // 其他的就算制定了 你也算算。。
        this.courseHoursExpStd = (int) Math.round(hours * factor);
    }
    @Override
    public void transformAndSave(ArrayList<S1CourseExperiment> list, int size) {
        CourseService courseService = (CourseService) SpringUtil.getBean("courseServiceImpl");
        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");

        ArrayList<String> accountNameList = new ArrayList<>(size);

        // 通过accountName 获取accountId
        // 另外顺便做些处理 处理多人课程 必须放在那里 因为是共性问题 但这里就是个性问题
        for (S1CourseExperiment s1CourseExperiment : list) {
            accountNameList.add(s1CourseExperiment.getCourseTeacherName());
        }
        ArrayList<Integer> accountIdList = accountMapper.selectBatchIdByName(accountNameList);
        ArrayList<CoursePO> courses = new ArrayList<>(size);

        // accountId 注入到CourseTheory
        S1CourseExperiment s1CourseExperiment = null;
        for(int i = 0 ; i < list.size() ; i++){
            try{
                s1CourseExperiment = list.get(i);
                s1CourseExperiment.setCourseTeacherId(accountIdList.get(i));
                // 有些字段实在太长 删减点 别太过了
                s1CourseExperiment.fieldStandardized();
                // 内置转换函数 能够将CourseTheory转换为Course 然后save！
                courses.add(new CoursePO(s1CourseExperiment));
            }
            catch (Exception e){
                e.printStackTrace();
//                throw new AppException(e, "CourseTheory", "transformAndSave courseService.saveBatch(courses)");
            }
        }
        try{
            courseService.saveBatch(courses);

        }
        catch (Exception e){
            e.printStackTrace();
            throw new AppException(e, "CourseExperiment", "transformAndSave courseService.saveBatch(courses)");
        }
        finally {
            courses.clear();
            accountIdList.clear();
            accountNameList.clear();
        }
    }

}