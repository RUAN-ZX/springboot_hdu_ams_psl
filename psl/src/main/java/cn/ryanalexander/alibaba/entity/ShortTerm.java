package cn.ryanalexander.alibaba.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName: ShortTerm
 * @Description
 * @Author ryan
 * @Date 2022/3/12 14:17
 * @Version 1.0.0-Beta
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("短学期")
@ToString
public class ShortTerm{
    private Integer c_id;
    private String c_cid;
    private String c_term;
    private String c_time;
    private String c_name;
    private String c_addr;

    private String c_t_id;
    private String c_t_name; // 全部老师

    private Integer c_type; // 1 2 3 4

    private Integer c_capacity; // < 1000
    private Double c_cap_factor_1;
    private Double c_cap_factor_2;

    private Double c_hours;
    private Double c_hours_std;
    private Double c_hours_theory;
    private Double c_hours_theory_std;
    private Double c_hours_exp;
    private Double c_hours_exp_std;

    private String c_bilingual;
    private String c_reform_1;
    private Double c_reform_2;

    private Double c_factor;
    private Double c_prior;

    private Double c_hours_op;
    private Double c_points;
    private String c_properties;
    private String c_note_1;
    private String c_note_2;

}