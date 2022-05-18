package cn.ryanalexander.psl.domain.bo.excel;

import cn.ryanalexander.common.domain.exceptions.AppException;
import cn.ryanalexander.psl.domain.po.CourseUnionPO;
import cn.ryanalexander.psl.mapper.AccountMapper;
import cn.ryanalexander.psl.service.CourseUnionService;
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
 * <p>2022/5/10 </p>
 *
 * @author ryan 2022/5/10 9:48
 * @since 1.0.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("课程 多人情况导入")
@ToString
@ExcelIgnoreUnannotated
public class CourseMulti implements ExcelEntity<CourseMulti>, Cloneable {
    @ExcelProperty("教师姓名")
    private String courseTeacherName;

    @ExcelProperty("分成")
    private Double courseShare;

    @ExcelProperty("课号")
    private String courseNum;

    @Override
    public boolean isValidated() {
        // 有名字 有百分占比 正常情况！
        return courseNum != null || courseShare != null || courseTeacherName != null;
    }

    @Override
    public boolean multiStart(){
        return courseNum != null && courseShare == null && courseTeacherName == null;
    }
    // 这里 之前也是multiStart 应该怎么处理
    @Override
    public boolean prevIsMultiHeadOperation(ExcelEntity mask){
        return false;
    }

    @Override // 下一行的补充数据
    public boolean multiContinue(){
        return !this.multiStart();
    }


    @Override
    public void fieldStandardized(){
    }
    @Override
    public void stdCalculator(List<Map<Integer, String>> headInfoMap){
        // todo 工作量计算后边再说吧 这里随便先不实现 选一个作为表头即可
//        if(this.multiStart()){ // 开头的时候
//            CourseUnionMapper courseUnionMapper =
//                    (CourseUnionMapper) SpringUtil.getBean("courseUnionMapper");
//
//            CourseUnionPO course = courseUnionMapper.selectOne(new QueryWrapper<CourseUnionPO>()
//                            .eq("course_num", this.courseNum).last("limit 1"));
//
//            this.courseUnionPO = course;
//            // 表格只提供一个课号即可 剩下系统会找到所有同名 同学期 同老师的
//            List<CourseUnionPO> courses =
//            courseUnionMapper.selectList(new QueryWrapper<CourseUnionPO>()
//                    .eq("course_name", course.getCourseName())
//                    .eq("course_term", course.getCourseTerm())
//                    .eq("course_teacher_name", course.getCourseTeacherName()));
//            // 执行完以后会存到mask里边！
//        }

    }

    @Override
    public ExcelEntity copyFromMasterMask(ExcelEntity data){
        CourseMulti result = (CourseMulti) data;
        result.setCourseNum(this.courseNum); // data吧mask的courseNum提取一下就完事了。。
        return result;
    }

    @Override
    public void transformAndSave(ArrayList<CourseMulti> list, int size) {
        CourseUnionService courseUnionService =
                (CourseUnionService) SpringUtil.getBean("courseUnionServiceImpl");

        AccountMapper accountMapper = (AccountMapper) SpringUtil.getBean("accountMapper");

        ArrayList<String> accountNames = new ArrayList<>(size);
        ArrayList<String> courseNums = new ArrayList<>(size);

        for (CourseMulti courseMulti : list) {
            accountNames.add(courseMulti.getCourseTeacherName());
            courseNums.add(courseMulti.getCourseNum());
        }
        // num不可重复 否则gg
        List<CourseUnionPO> courseUnionPOS =
                courseUnionService.selectBatchByNums(courseNums);

        ArrayList<Integer> accountIds =
                accountMapper.selectBatchIdByName(accountNames);

        size = courseUnionPOS.size();


        ArrayList<CourseUnionPO> updatePos = new ArrayList<>();
        ArrayList<CourseUnionPO> insertPos = new ArrayList<>(size);

        for(int i = 0 ; i < size ; ++i){
            CourseUnionPO current = courseUnionPOS.get(i);
            if(!current.getCourseTeacherName().equals(accountNames.get(i))){
                current.setCourseTeacherId(accountIds.get(i));
                current.setCourseTeacherName(accountNames.get(i));
                current.setCourseId(null); // 这样让mysql生成新的id！
                insertPos.add(current);
            } else{
                current.setCourseHours(0);
                updatePos.add(current);
            }
            //  todo  相同的应该 要改分成的具体工作量大小 做个list然后批量更新
        }

        try{
            // todo saveOrUpdateBatch update 会更新表头老师的工作量
            courseUnionService.saveBatch(insertPos);
//            courseUnionService.updateBatchById(updatePos);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new AppException(e, "courseUnion", "transformAndSave CourseShortTerm.saveBatch(courses)");
        }
        finally {
            courseUnionPOS.clear();
        }
    }
}

