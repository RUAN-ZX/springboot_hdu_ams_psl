package cn.ryanalexander.alibaba.service.tool;

import com.alibaba.fastjson.JSONObject;

public class ErrorService {


    public static JSONObject getCode(int code, Object info){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("info",info);
        // Object 兼容JSONObject
        return jsonObject;
    }




}