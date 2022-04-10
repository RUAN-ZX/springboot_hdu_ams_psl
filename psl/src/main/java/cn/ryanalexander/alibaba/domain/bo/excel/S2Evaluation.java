package cn.ryanalexander.alibaba.domain.bo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @ClassName: Evaluation_
 * @Description
 * @Author ryan
 * @Date 2022/3/10 20:19
 * @Version 1.0.0-Beta
 **/
@Data
@ToString
@AllArgsConstructor
@ApiModel("学评教")
public class S2 {
    @ExcelProperty(value = "教师职工号")
    private Integer e_t_id;

//    private String e_term; 表名

    @ExcelProperty(value = "参评人次")
    private Integer e_participate;

    @ExcelProperty(value = "总得分")
    private Double e_score;

    @ExcelProperty(value = "全校排名")
    private Integer e_srank;

    @ExcelProperty(value = "学院排名")
    private Integer e_arank;

}
