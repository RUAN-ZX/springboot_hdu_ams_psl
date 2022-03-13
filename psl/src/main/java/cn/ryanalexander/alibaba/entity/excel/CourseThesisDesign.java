package cn.ryanalexander.alibaba.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName: CourseThesisDesign
 * @Description
 * @Author ryan
 * @Date 2022/3/10 19:50
 * @Version 1.0.0-Beta
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("毕业设计")
public class CourseThesisDesign extends ExcelEntity{
    @ExcelProperty(value = "教师姓名")
    private String td_name;

    @ExcelProperty(value = "学号")
    private String td_stu_id;

    @ExcelProperty(value = "姓名")
    private String td_stu_name;

    @ExcelProperty(value = "专业名称")
    private String td_stu_major;

    @ExcelProperty(value = "成绩")
    private String td_grade; // 0 ~ 4 确定好了基本 优秀系数

    @ExcelProperty(value = "系数1")
    private Double td_factor_1;
    @ExcelProperty(value = "优秀系数")
    private Double td_factor_2;

    @ExcelProperty(value = "T1")
    private Double td_t1; // t1

    @ExcelProperty(value = "标准学时")
    private Double td_std; // 标准学时

    @ExcelProperty(value = "备注")
    private String td_note;

    @Override
    public void transformAndSave() {
        super.transformAndSave();
    }
}