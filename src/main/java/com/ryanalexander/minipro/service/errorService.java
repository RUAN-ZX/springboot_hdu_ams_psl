package com.ryanalexander.minipro.service;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;

public class errorService{


    public static String getCode(int code, String info){
        String code_ = "code:"+code;
        System.out.println(info);
        return JSON.toJSONString(code_);
    }




}