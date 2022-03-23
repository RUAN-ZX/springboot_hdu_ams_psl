package cn.ryanalexander.alibaba.domain.bo.excel;

import cn.ryanalexander.alibaba.domain.enumable.ErrorCodeEnum;
import cn.ryanalexander.alibaba.domain.exceptions.AppException;
import cn.ryanalexander.alibaba.domain.po.Teacher;
import cn.ryanalexander.alibaba.mapper.TeacherMapper;
import cn.ryanalexander.alibaba.service.tool.SpringUtil;
import cn.ryanalexander.alibaba.service.TeacherService;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

/**
 * <p><b></b></p>

 * <p>2022-03-23 </p>

 * @since 1.0.0
 * @see com.alibaba.excel.converters.Converter
 * @author RyanAlexander 2022-03-23 11:20
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
//@ApiModel("工号和邮箱")
public class TidAndEmail implements ExcelEntity<TidAndEmail> {
    @ExcelProperty(value = "职工号")
    public Integer teacherId;

    @ExcelProperty(value = "姓名")
    public String teacherName;

    @ExcelProperty(value = "邮箱")
    public String teacherMail;

    @Override
    public boolean isValidated() {
        return teacherId != null && teacherMail != null;
    }

    @Override
    public void transformAndSave(ArrayList<TidAndEmail> list, int size) {
        TeacherService teacherService = (TeacherService) SpringUtil.getBean("teacherServiceImpl");
        TeacherMapper teacherMapper = (TeacherMapper) SpringUtil.getBean("teacherMapper");
        ArrayList<Teacher> afterTransform = new ArrayList<>(size);

//        for (TidAndEmail item : list) {
//            Teacher transformItem = new Teacher();
//            if(item.teacherId == null) continue;
//            Integer teacherId = Integer.valueOf(item.teacherId);
//            if(teacherId == null) continue;
//            transformItem.setTeacherId(teacherId);
//            transformItem.setTeacherName(item.teacherName);
//            transformItem.setTeacherMail(item.teacherMail);
//            afterTransform.add(transformItem);
//        }

        try{

//            afterTransform.forEach(System.out::println);
            // 不必要传size 因为easyexcel的batch明显远小于默认的1000！
            teacherMapper.saveOrUpdateBatchByMail(list);
//            teacherService.saveBatch(afterTransform);
        }
        catch (Exception e){
            throw new AppException(ErrorCodeEnum.FAIL, e.getMessage());
        }
    }
}
