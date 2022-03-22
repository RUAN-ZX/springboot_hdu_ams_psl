package cn.ryanalexander.alibaba.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName: CourseSemester
 * @Description
 * @Author ryan
 * @Date 2022/3/10 19:41
 * @Version 1.0.0-Beta
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("工作量_短学期")
@ToString
public class CourseShortTerm{
    @ExcelProperty(value = "学期")
    private String c_term;

    @ExcelProperty(value = "已选人数")
    private Integer c_capacity; // < 1000

    @ExcelProperty(value = "教师姓名")
    private String c_t_name; // 全部老师

    @ExcelProperty(value = "课程代码")
    private String c_cid;

    @ExcelProperty(value = "课程名称")
    private String c_name;

    @ExcelProperty(value = "起始时间")
    private String c_time;

    @ExcelProperty(value = "上课地点")
    private String c_addr;

    @ExcelProperty(value = "教务处备注")
    private String c_note_1;

    @ExcelProperty(value = "性质")
    private String c_properties;

    @ExcelProperty(value = "类别系数")
    private Double c_factor;

    @ExcelProperty(value = "教改")
    private Double c_reform_2;

    @ExcelProperty(value = "班级规模系数")
    private Double c_cap_factor;

    @ExcelProperty(value = "学时")
    private Double c_hours;

    @ExcelProperty(value = "标准课时")
    private Double c_hours_std;

    @ExcelProperty(value = "备注")
    private String c_note_2;

}
