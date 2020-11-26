package com.ryanalexander.minipro.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;

public class ErrorService {


    public static JSONObject getCode(int code, Object info){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("info",info);
        // Object 兼容JSONObject
        return jsonObject;
    }




}