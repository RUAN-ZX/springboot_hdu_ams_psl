package cn.ryanalexander.alibaba.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("老师信息")
@ToString
public class Teacher {
//一定要和数据表中的名字完全一样！ 用别名非常麻烦！！！
	private Integer t_id;
	private Integer t_d_id;
	private String t_name;
	private String t_mail; // 默认为Tid@hdu.edu.cn

	private String t_team;
	private String t_type;
	private String t_title;
	private String t_title_level;

	private Integer t_captcha;

    private String t_pwd;
}

