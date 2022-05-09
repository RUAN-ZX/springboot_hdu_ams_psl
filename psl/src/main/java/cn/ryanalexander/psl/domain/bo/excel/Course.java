package cn.ryanalexander.psl.domain.bo.excel;

import cn.ryanalexander.psl.domain.exceptions.AppException;
import cn.ryanalexander.psl.domain.po.CourseUnionPO;
import cn.ryanalexander.psl.domain.po.EvaluationPO;
import cn.ryanalexander.psl.mapper.AccountMapper;
import cn.ryanalexander.psl.mapper.CourseUnionMapper;
import cn.ryanalexander.psl.service.CourseUnionService;
import cn.ryanalexander.psl.service.EvaluationService;
import cn.ryanalexander.psl.service.tool.DataUtil;
import cn.ryanalexander.psl.service.tool.SpringUtil;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p><b></b></p>
 *
 * <p>2022/5/7 </p>
 *
 * @author ryan 2022/5/7 22:32
 * @since 1.0.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("课程总表")
@ToString
@ExcelIgnoreUnannotated
public class Course implements ExcelEntity<Course>, Cloneable {
    @ExcelProperty("学年")
    private String year;

    @ExcelProperty("学期")
    private String courseTerm;

    @ExcelProperty("课程名称")
    private String courseName;

    @ExcelProperty("学分")
    private Double coursePoints;

    @ExcelProperty("教学班")
    private String courseNum;

    @ExcelProperty("教学班组成")
    private String courseClass;

    @ExcelProperty("选课人数")
    private Integer courseCapacity;

    @ExcelProperty("教工号")
    private String courseTeacherId;

    @ExcelProperty("教师名称")
    private String courseTeacherName;

    @ExcelProperty("学时")
    private Integer courseHours;

    @ExcelProperty("上课时间")
    private String courseTime;

    @ExcelProperty("教学地点")
    private String courseAddress;

    @ExcelProperty("专业组成")
    private String courseMajor;

    private Integer courseType;

    @Override
    public boolean isValidated() {
        // 有名字 有百分占比 正常情况！
        return courseTeacherId != null
                && !courseTeacherId.equals("无")
                && courseTeacherId.contains("lsd"); // lsd的教工号也不要！
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
        // 老师名字的处理 如果需要添加另一个 就和多人的情况一样 额外添加！所有双人课都需要处理！
        String finalName = this.courseTeacherName.split(",")[0];
        this.courseTeacherId = this.courseTeacherId.split(",")[0];

        Matcher matcher = Pattern.compile(DataUtil.CH_REGEX).matcher(finalName);
        if(matcher.find()) this.courseTeacherName = matcher.group(1);

        // 分流 三类课
        String[] numTemp = this.courseNum.split("-");
        // (2018-2019-2)-20160418-S0403700-1
        // (2018-2019-1)-S0406080-40191-2
        char character = numTemp[1].charAt(0);
        if(character <= 9) this.courseType = 2;
        else if(character == 'S') this.courseType = 1;
        else this.courseType = 0;

        // 学期拼接
        this.courseTerm = this.year + this.courseTerm;

    }
    @Override
    public void stdCalculator(List<Map<Integer, String>> headInfoMap){

    }

    @Override
    public void transformAndSave(ArrayList<Course> list, int size) {
        CourseUnionService courseUnionService =
                (CourseUnionService) SpringUtil.getBean("courseUnionServiceImpl");

        ArrayList<CourseUnionPO> courseUnionPOS = new ArrayList<>(size);

        // accountId 注入到CourseTheory
        Course course = null;
        for(int i = 0 ; i < list.size() ; i++){
            try{
                course = list.get(i);
                // 这里做了分类
                course.fieldStandardized();
                courseUnionPOS.add(new CourseUnionPO(course));
            }
            catch (Exception e){
                e.printStackTrace();
                throw new AppException(e, "courseUnion", "transformAndSave CourseShortTerm.saveBatch(courses)");
            }
        }
        try{
            // todo saveOrUpdateBatch
            courseUnionService.saveBatch(courseUnionPOS);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new AppException(e, "courseUnion", "transformAndSave CourseShortTerm.saveBatch(courses)");
        }
        finally {
            courseUnionPOS.clear();
//            accountIdList.clear();
//            accountNameList.clear();
        }
    }
}

