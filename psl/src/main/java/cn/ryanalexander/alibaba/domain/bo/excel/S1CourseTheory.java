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
import java.util.List;
import java.util.Map;

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

    @ExcelProperty(value = "标准\n课时")
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
        // 有名字 名字存在 有时长 这记录才有用！ 如果只有老师+标准学时 也认了。。
        return courseTeacherName != null && (courseHours != null || courseHoursStd != null);

    }

    @Override
    public boolean multiStart(){
        return ExcelDataProcessUtil.multiStart(courseTeacherName);
    }

    // 多行 多人头出现
    @Override
    public boolean prevIsMultiHeadOperation(ExcelEntity mask){
        CourseTheory lastMultiHead = (CourseTheory) mask; // 上一个模板
//        两个都要计算Std 然后叠加Std 不过每次都已经算好了！ 只有multiContinued不用算 直接分成！
        this.courseHoursStd += lastMultiHead.courseHoursStd;
        this.courseHoursExpStd += lastMultiHead.courseHoursExpStd;
        this.courseCapacity += lastMultiHead.courseCapacity;
        return false;
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

        if(share.courseHoursStd == null && share.courseNum != null){
            // todo 这个地方写分成比例！
            double ratio = ExcelDataProcessUtil.getRatio(share.courseNum);
            result.courseHoursStd *= ratio; // 分成
            // 总课时什么的没必要分成 就是作为一个课程信息的展示 多好啊
        }
        result.courseHoursStd = share.getCourseHoursStd(); // 有标准课时可用就拿来咯
        // 课程总课时 等等其他参数 全部放进来即可 不需要分成。。
        return result;
    }
    @Override
    public void stdCalculator(List<Map<Integer, String>> headInfoMap){
        if(this.courseHours == null){
            this.courseHoursExpStd = 0;
            this.courseHoursTheory = 0.0;
            this.courseHoursExp = 0.0;
            this.courseHoursTheoryStd = 0;
            return; // 直接使用指定的std了。别的都为0
        }
        double hours = this.courseHours;
        double factor = this.courseFactor;
        double prior = this.coursePrior;
        double hoursTheory = this.courseHoursTheory;
        double hoursExp = hours - hoursTheory;
        if(hoursExp < 0) hoursExp = 0.0; // 竟然存在这种情况。。

        this.courseHoursExp = hoursExp;

        double capacity_factor = ExcelDataProcessUtil.getCapacityFactorByProperty(
                "I",
                this.courseCapacity
        );
        factor *= (capacity_factor * prior); // 归在一起了 优课×规模×容量
        // 3位小数 而且
        this.courseCapacityFactor1 = capacity_factor;
        // 虽然excel可能获取到double 不管 直接round
        // 如果有 就用已有的 认为是特殊指定的！
        if(this.courseHoursStd == null)
            this.courseHoursStd = hours * factor;

        this.courseHoursTheoryStd = (int) Math.round(hoursTheory * factor);
        this.courseHoursExpStd = (int) Math.round(hoursExp * factor);
    }
    @Override
    public void transformAndSave(ArrayList<CourseTheory> list, int size) {
        CourseService courseService = (CourseService) SpringUtil.getBean("courseServiceImpl");
        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");

        ArrayList<String> accountNameList = new ArrayList<>(size);

        // 通过accountName 获取accountId
        // 另外顺便做些处理 处理多人课程 必须放在那里 因为是共性问题 但这里就是个性问题
        for (CourseTheory courseTheory : list) {
            accountNameList.add(courseTheory.getCourseTeacherName());
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
