package cn.ryanalexander.alibaba.domain.bo.excel;

/**
 * @ClassName: CourseTheory
 * @Description
 * @Author ryan
 * @Date 2022/3/10 19:08
 * @Version 1.0.0-Beta
 **/
import cn.ryanalexander.alibaba.domain.exceptions.AppException;
import cn.ryanalexander.alibaba.domain.exceptions.ExceptionInfo;
import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.alibaba.domain.exceptions.code.SubjectEnum;
import cn.ryanalexander.alibaba.domain.po.Course;
import cn.ryanalexander.alibaba.mapper.AccountMapper;
import cn.ryanalexander.alibaba.mapper.CourseMapper;
import cn.ryanalexander.alibaba.service.CourseService;
import cn.ryanalexander.alibaba.service.tool.ExcelDataProcessUtil;
import cn.ryanalexander.alibaba.service.tool.MathService;
import cn.ryanalexander.alibaba.service.tool.SpringUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.annotation.Resource;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("工作量_理论课程")
@ToString
public class CourseTheory implements ExcelEntity<CourseTheory>, Cloneable{

    @ExcelProperty(value = "学期")
    private String courseTerm;

    @ExcelProperty(value = "选课课号")
    private String courseNum;

    @ExcelProperty(value = "课程名称")
    private String courseName;

    @ExcelProperty(value = "教师姓名")
    private String courseTeacherName; // 全部老师

    @ExcelProperty(value = "人数")
    private Integer courseCapacity; // < 1000

    @ExcelProperty(value = "教务处备注")
    private String courseNote1;

    @ExcelProperty(value = "双语")
    private String courseBilingual;

    @ExcelProperty(value = "课改")
    private String courseReform;

    @ExcelProperty(value = "优课优酬")
    private Double coursePrior;

    @ExcelProperty(value = "类别系数")
    private Double courseFactor;

    @ExcelProperty(value = "班级规模系数")
    private Double courseCapacityFactor1;

    @ExcelProperty(value = "上课时间")
    private String courseTime;

    @ExcelProperty(value = "上课地点")
    private String courseAddress;

    @ExcelProperty(value = "总学时")
    private Double courseHours;

    @ExcelProperty(value = "讲课学时")
    private Double courseHoursTheory;

    @ExcelProperty(value = "理论课标准课时")
    private Double courseHoursTheoryStd;

    @ExcelProperty(value = "实验学时")
    private Double courseHoursExp;

    @ExcelProperty(value = "实验标准课时")
    private Double courseHoursExpStd;

    @ExcelProperty(value = "标准课时")
    private Double courseHoursStd;

    @ExcelProperty(value = "备注")
    private String courseNote2;

    private Integer courseType = 1; // Theory

    private Integer courseTeacherId;
//    private Double courseCapacityFactor2;

//    @ExcelProperty(value = "学分")
//    private Double coursePoints;
//    private Double courseHoursOp;
//    private Double coursePoints;
//    private String courseProperties;
    @Override
    public boolean isValidated() {
        // 有名字 名字存在 有时长 这记录才有用！
//        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");
//        boolean isAccountExist = accountMapper.selectIdByName(courseTeacherName) != null;
        // TODO 挨个检查太慢了 不知道有没有啥好办法 不过这里也不需要了 找不到就让记录留下来 有朝一日update上
        //  反正也不影响在案老师的工作量计算
        return courseName != null && courseHours != null;

    }
    @Resource
    private CourseService courseService;

    @Override
    public boolean multiStart(){
        return ExcelDataProcessUtil.batchStart(courseTeacherName);
    }

    @Override
    public boolean multiContinue(){
        return courseNum == null;
    }

    @Override
    public ExcelEntity copyFromMasterMask(ExcelEntity data) {
        CourseTheory result = null;
        CourseTheory share = (CourseTheory) data;
        try {
            // 因为是自己clone自己 所以类型一定是对的
            result = (CourseTheory) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new AppException(new ErrorCode(SubjectEnum.INTERNAL));
        }

        // 老师名字必须改
        result.courseTeacherName = share.getCourseTeacherName();
        // 获取比例
        double ratio = share.getCourseHours() / result.courseHours;
        // 课程总课时
        result.courseHours = share.getCourseHours(); // 总课时是关键！

        result.courseHoursTheory = MathService.ratioFormatter(share.getCourseHoursTheory(),
                ratio, "##.#");
        // 实验学时直接倒减推出来就行！
        result.courseHoursExp = result.courseHours - result.courseHoursTheory;
        return result;
    }

    @Override
    public void transformAndSave(ArrayList<CourseTheory> list, int size) {
//        CourseService courseService = (CourseService) SpringUtil.getBean("courseServiceImpl");
        CourseMapper courseMapper = (CourseMapper) SpringUtil.getBean("courseMapper");
        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");

        ArrayList<String> accountNameList = new ArrayList<>(size);

        // 通过accountName 获取accountId
        for (CourseTheory courseTheory : list) {
            accountNameList.add(courseTheory.getCourseTeacherName());
        }
        // todo 这里存在问题 如果这个老师不存在 找到的id为null 应当怎么处理为好？
        // 目前是计划 我先导入 虽然id为一个值 比如null 到时候再补充 全库批量找null 然后 update还是快的
        ArrayList<Integer> accountIdList = accountMapper.selectBatchIdByName(accountNameList);
        ArrayList<Course> courses = new ArrayList<>(size);

        // accountId 注入到CourseTheory
        for(int i = 0 ; i < list.size() ; i++){
            list.get(i).setCourseTeacherId(accountIdList.get(i));
            // 内置转换函数 能够将CourseTheory转换为Course 然后save！
            courses.add(new Course(list.get(i)));
        }

        try{
            courseMapper.saveBatch(courses);
        }
        catch (Exception e){
            throw new AppException(e, "CourseTheory", "transformAndSave courseService.saveBatch(courses)");
        }
    }
}
