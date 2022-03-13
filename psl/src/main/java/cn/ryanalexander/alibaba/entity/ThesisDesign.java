package cn.ryanalexander.alibaba.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ApiModel("毕设")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ThesisDesign {
    private Integer td_id;
    private Integer td_year;
    private String td_note;
    private String td_t_id;
    private Integer td_grade; // 0 ~ 4 确定好了基本 优秀系数

    private Double td_t1; // t1
    private Double td_std; // 标准学时

    private Double td_factor_1;
    private Double td_factor_2;


}