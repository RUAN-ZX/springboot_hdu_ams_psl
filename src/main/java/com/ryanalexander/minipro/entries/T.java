package com.ryanalexander.minipro.entries;

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
public class T {
//一定要和数据表中的名字完全一样！ 用别名非常麻烦！！！
	private String Tid;

	private String TDid;

	private String Tname;


	private String Tcaptcha;

    //1 male, 0 female
    private String Tmail;//默认为Tid@hdu.edu.cn
    private String TPwd;
}

