package cn.ryanalexander.alibaba.entity.excel;

/**
 * @ClassName: CourseTheory
 * @Description
 * @Author ryan
 * @Date 2022/3/10 19:08
 * @Version 1.0.0-Beta
 **/
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("工作量_理论课程")
@ToString
public class CourseTheory implements ExcelEntity{

    @ExcelProperty(value = "学期")
    private String c_term;

    @ExcelProperty(value = "选课课号")
    private String c_cid;

    @ExcelProperty(value = "课程名称")
    private String c_name;

    @ExcelProperty(value = "教师姓名")
    private String c_t_name; // 全部老师

    @ExcelProperty(value = "人数")
    private Integer c_capacity; // < 1000

    @ExcelProperty(value = "教务处备注")
    private String c_note_1;

    @ExcelProperty(value = "双语")
    private String c_bilingual;

    @ExcelProperty(value = "课改")
    private String c_reform;

    @ExcelProperty(value = "优课优酬")
    private Double c_prior;

    @ExcelProperty(value = "类别系数")
    private Double c_factor;

    @ExcelProperty(value = "班级规模系数")
    private Double c_cap_factor_1;

//    @ExcelProperty(value = "学分")
//    private Double c_points;

    @ExcelProperty(value = "上课时间")
    private String c_time;

    @ExcelProperty(value = "上课地点")
    private String c_addr;

    @ExcelProperty(value = "总学时")
    private Double c_hours;

    @ExcelProperty(value = "讲课学时")
    private Double c_hours_theory;

    @ExcelProperty(value = "理论课标准课时")
    private Double c_hours_theory_std;

    @ExcelProperty(value = "实验学时")
    private Double c_hours_exp;

    @ExcelProperty(value = "实验标准课时")
    private Double c_hours_exp_std;

    @ExcelProperty(value = "标准课时")
    private Double c_hours_std;

    @ExcelProperty(value = "备注")
    private String c_note_2;


    @Override
    public Object transformAndSave(ArrayList<?> list) {
        return null;
    }
}
