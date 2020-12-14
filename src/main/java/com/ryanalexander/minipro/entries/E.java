package com.ryanalexander.minipro.entries;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("菜品信息")
@ToString
public class E {

    private String Eid;

    private String ETid;

    private String Etime;

    private String Escore;

    private String Eparticipate;

    private String Esrank;
    private String Eprank;

}