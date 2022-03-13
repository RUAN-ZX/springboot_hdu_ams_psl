package cn.ryanalexander.alibaba.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName: EC
 * @Description
 * @Author Ryan
 * @Date 2021.3.14 21:22
 * @Version 1.0.0-Beta
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("成果记录")
@ToString
// 未来 教师可以自己申请对应的项目 国家级 还是论文等等 然后系统知道这些值多少分
// 另外可以输入占比 比如一个团队占比多少 然后经过审核就能通过了
// 如果政策改了 那就系统改 反正是教师 而不是教科办天天盯着公示来算。
public class Achievement {
    // 成果必须记录 记录完怎么加点是另外一回事 这个主要用于查询
    private Integer a_id;
    private Integer a_t_id;
    private String a_term;
    private String a_type;

    private String a_category;
    private Integer a_level;
    private Double a_score;
    private String a_note;
}
