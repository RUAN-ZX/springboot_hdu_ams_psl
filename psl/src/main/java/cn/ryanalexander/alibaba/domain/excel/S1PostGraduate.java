package cn.ryanalexander.alibaba.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName: PostGraduateWorkload
 * @Description
 * @Author ryan
 * @Date 2022/3/10 20:13
 * @Version 1.0.0-Beta
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("研究生理论课工作量")
@ToString
public class S1PostGraduate{
    @ExcelProperty(value = "姓名")
    private Integer pg_t_id;

//    private Integer pg_term;
    @ExcelProperty(value = "研究生教学业绩点")
    private Double pg_gpa;

}