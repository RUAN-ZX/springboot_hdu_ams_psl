package cn.ryanalexander.alibaba.domain.bo.excel;

/**
 * @ClassName: CourseTheory
 * @Description
 * @Author ryan
 * @Date 2022/3/10 19:08
 * @Version 1.0.0-Beta
 * @apiNote 对于多人 且多课号的情况 理论课的逻辑是 记录课程信息（最下边的）+使用给定学时大小
 * 如果多人情况 如果有标准学时 使用给定标准学时完事（特别安排相当于）
 **/
import cn.ryanalexander.alibaba.domain.exceptions.AppException;
import cn.ryanalexander.alibaba.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.alibaba.domain.exceptions.code.SubjectEnum;
import cn.ryanalexander.alibaba.domain.po.CoursePO;
import cn.ryanalexander.alibaba.mapper.AccountMapper;
import cn.ryanalexander.alibaba.mapper.CourseMapper;
import cn.ryanalexander.alibaba.service.CourseService;
import cn.ryanalexander.alibaba.service.tool.ExcelDataProcessUtil;
import cn.ryanalexander.alibaba.service.tool.MathService;
import cn.ryanalexander.alibaba.service.tool.SpringUtil;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.annotation.Resource;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("工作量_理论课程")
@ToString
@ExcelIgnoreUnannotated
public class CourseTheory implements ExcelEntity<CourseTheory>, Cloneable{

    @ExcelProperty(value = "学期")
    public String courseTerm;

    @ExcelProperty(value = "选课课号")
    public String courseNum;

    @ExcelProperty(value = "课程名称")
    public String courseName;

    @ExcelProperty(value = "教师姓名")
    public String courseTeacherName; // 全部老师

    @ExcelProperty(value = "教务处备注")
    public String courseNote1;

    @ExcelProperty(value = "双语")
    public String courseBilingual;

    @ExcelProperty(value = "课改")
    public String courseReform;

    @ExcelProperty(value = "上课时间")
    public String courseTime;

    @ExcelProperty(value = "上课地点")
    public String courseAddress;

    @ExcelProperty(value = "备注")
    public String courseNote2;

    @ExcelProperty(value = "人数")
    public Integer courseCapacity; // < 1000

    @ExcelProperty(value = "优课优酬")
    public Double coursePrior;

    @ExcelProperty(value = "类别系数")
    public Double courseFactor;

    @ExcelProperty(value = "总学时")
    public Double courseHours;

    @ExcelProperty(value = "讲课学时")
    public Double courseHoursTheory;


    // ---------------------- 派生数据
    //    @ExcelProperty(value = "班级规模系数")

    public Double courseCapacityFactor1;

//    @ExcelProperty(value = "理论课标准课时")
    public Integer courseHoursTheoryStd;

//    @ExcelProperty(value = "实验学时")
    public Double courseHoursExp;

//    @ExcelProperty(value = "实验标准课时")
    public Integer courseHoursExpStd;

    @ExcelProperty(value = "标准课时")
    public Double courseHoursStd;

    public Integer courseType = 1; // Theory

    public Integer courseTeacherId;
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
        return courseTeacherName != null && courseHours != null;

    }

    @Override
    public boolean multiStart(){
        return ExcelDataProcessUtil.multiStart(courseTeacherName);
    }
    @Override
    public void stdAccumulate(ExcelEntity mask){
        CourseTheory courseTheory = (CourseTheory) mask;
        this.courseHoursStd += courseTheory.courseHoursStd;
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

        if(this.courseAddress != null && this.courseAddress.length() > 24)
            this.courseAddress = this.courseAddress.substring(0, 24);

        if(this.courseName != null && this.courseName.length() > 24)
            this.courseName = this.courseName.substring(0, 24);
    }

    @Override
    public ExcelEntity copyFromMasterMask(ExcelEntity data) {
        CourseTheory result = null;
        CourseTheory share = (CourseTheory) data;
        try {
            // 此时this就是多人的模板 result就是每个老师的分成 对象实例
            // share就是excel读取的分成 数据
            result = (CourseTheory) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new AppException(new ErrorCode(SubjectEnum.INTERNAL));
        }

        // 老师名字必须改
        result.courseTeacherName = share.getCourseTeacherName();
        if(share.courseNum != null){
            // 获取比例 原来是通过给定学时 但是这不适用于多个多人课头 所以 这里直接设计成 默认通过课号写的百分比来搞定！
//        double ratio = share.getCourseHours() / result.courseHours;
            double ratio = Double.parseDouble(share.courseNum.split("%")[0]) / 100.0;
            result.courseHoursTheory = MathService.ratioFormatter(result.courseHoursTheory,
                    ratio, "##.#");
        }

        // 课程总课时
        result.courseHours = share.getCourseHours(); // 总课时也就放进来
        result.courseHoursStd = share.getCourseHoursStd(); // 有标准课时可用就拿来咯
        // 模板 也就是总理论课时 乘上比例 得出share


        return result;
    }

    private static void stdCalculator(CourseTheory courseTheory){
        int capacity = courseTheory.courseCapacity;
        double hours = courseTheory.courseHours;
        double factor = courseTheory.courseFactor;
        double prior = courseTheory.coursePrior;
        double hoursTheory = courseTheory.courseHoursTheory;
        double hoursExp = hours - hoursTheory;
        if(hoursExp < 0) hoursExp = 0.0; // 竟然存在这种情况。。

        courseTheory.courseHoursExp = hoursExp;

        double capacity_factor = 1.0;
        if(capacity > 80){
            capacity_factor = Math.min(1 + (capacity - 80) / 200.0, 1.2);
        }
        factor *= (capacity_factor * prior); // 归在一起了 优课×规模×容量
        // 3位小数 而且
        courseTheory.courseCapacityFactor1 = capacity_factor;
        // 虽然excel可能获取到double 不管 直接round
        // 如果有 就用已有的 认为是特殊指定的！
        if(courseTheory.courseHoursStd == null)
            courseTheory.courseHoursStd = (double) (int) Math.round(hours * factor);

        courseTheory.courseHoursTheoryStd = (int) Math.round(hoursTheory * factor);
        courseTheory.courseHoursExpStd = (int) Math.round(hoursExp * factor);
//        courseTheory.courseHoursStd = 1.0;
//        courseTheory.courseHoursTheoryStd = 1.0;
//        courseTheory.courseHoursExpStd = 1.0;
    }
    @Override
    public void transformAndSave(ArrayList<CourseTheory> list, int size) {
        CourseService courseService = (CourseService) SpringUtil.getBean("courseServiceImpl");
        CourseMapper courseMapper = (CourseMapper) SpringUtil.getBean("courseMapper");
        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");

        ArrayList<String> accountNameList = new ArrayList<>(size);

        // 通过accountName 获取accountId
        // 另外顺便做些处理 处理多人课程 必须放在那里 因为是共性问题 但这里就是个性问题
        for (CourseTheory courseTheory : list) {
            accountNameList.add(courseTheory.getCourseTeacherName());
            // todo std 问题 | capacity factor 问题 | exp
            if(courseHoursStd == null)
                stdCalculator(courseTheory);
        }
        // todo 这里存在问题 如果这个老师不存在 找到的id为null 应当怎么处理为好？
        // 目前是计划 我先导入 虽然id为一个值 比如null 到时候再补充 全库批量找null 然后 update还是快的
        ArrayList<Integer> accountIdList = accountMapper.selectBatchIdByName(accountNameList);
        ArrayList<CoursePO> cours = new ArrayList<>(size);

        // accountId 注入到CourseTheory
        CourseTheory courseTheory = null;
        for(int i = 0 ; i < list.size() ; i++){
            try{
                courseTheory = list.get(i);
                courseTheory.setCourseTeacherId(accountIdList.get(i));
                // 有些字段实在太长 删减点 别太过了
                courseTheory.fieldStandardized();
                // 内置转换函数 能够将CourseTheory转换为Course 然后save！
                cours.add(new CoursePO(courseTheory));
            }
            catch (Exception e){
                e.printStackTrace();
//                throw new AppException(e, "CourseTheory", "transformAndSave courseService.saveBatch(courses)");
            }
        }
        try{
            courseService.saveBatch(cours);

        }
        catch (Exception e){
            e.printStackTrace();
            throw new AppException(e, "CourseTheory", "transformAndSave courseService.saveBatch(courses)");
        }
        finally {
            cours.clear();
            accountIdList.clear();
            accountNameList.clear();
        }
    }
}
