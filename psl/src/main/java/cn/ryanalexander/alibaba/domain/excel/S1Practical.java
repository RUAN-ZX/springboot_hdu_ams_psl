package cn.ryanalexander.alibaba.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName: WordloadPractical
 * @Description
 * @Author ryan
 * @Date 2022/3/11 22:35
 * @Version 1.0.0-Beta
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("实践教学工作量")
@ToString
public class S1Practical {
//    姓名	学校标志性成果业绩分（本科）	学校非标志性成果业绩分（本科）	学院专项（本科）	双肩挑
    @ExcelProperty(value = "姓名")
    private String s1_t_name;

    @ExcelProperty(value = "学校标志性成果业绩分（本科）")
    private Double s1_kpi_practical_1;

    @ExcelProperty(value = "学校非标志性成果业绩分（本科）")
    private Double s1_kpi_practical_2;

    @ExcelProperty(value = "学院专项（本科）")
    private Double s1_kpi_practical_3;

    @ExcelProperty(value = "双肩挑")
    private Double s1_kpi_practical_4;
}
