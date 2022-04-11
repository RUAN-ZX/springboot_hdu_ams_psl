package cn.ryanalexander.alibaba.domain.bo.excel.out;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p><b></b></p>
 *
 * <p>2022/4/11 </p>
 *
 * @author ryan 2022/4/11 16:16
 * @since 1.0.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("S1工作量表")
@ToString
@ExcelIgnoreUnannotated
public class SFinal {
    // List index就完事了
    @ExcelProperty(value = "序号")
    private String id;

    @ExcelProperty(value = "工号")
    private String teacherId;

    @ExcelProperty(value = "姓名")
    private String teacherName;

    @ExcelProperty(value = "职称")
    private String teacherLevel; // 不是名字 是等级！

    // 理论+实验 纯课时 没有标准！
    // todo 前边对总课时的统计要确保了！
    @ExcelProperty(value = "承担主讲课程学时数是否不低于64学时")
    private String teacherHours;

    @ExcelProperty(value = "考核分数\n")
    private String sScore; // postGraduateKpi * 100

    @ExcelProperty(value = "考核等级\n")
    private String sGrade; // postGraduateKpi * 100

    @ExcelProperty(value = "备注\n")
    private String sNote; // postGraduateKpi * 100
}
