package cn.ryanalexander.alibaba.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("学评教结果表")
@ToString
public class Evaluation {

    private Integer e_id;

    private Integer e_t_id;

    private String e_term;

    private Double e_score;

    private Integer e_participate;

    private Integer e_srank;
    private Integer e_arank;

    private Double e_result; // 这是排名占比
    // 当时就直接计算即可 然后存着 因为这个数据很少变更 如果变更就用新excel更新就好了

}