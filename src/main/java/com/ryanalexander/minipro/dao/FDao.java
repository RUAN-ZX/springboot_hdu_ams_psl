package com.ryanalexander.minipro.dao;

import com.alibaba.fastjson.JSONObject;
import com.ryanalexander.minipro.entries.F;
import jdk.nashorn.api.scripting.JSObject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FDao {
    void f_insert(String f_t_id, String f_title, String f_path, String f_content);
    void f_update(String f_id);
    List<JSONObject> f_select();

    String f_select_content(String f_id);
//#{f_t_id}, #{f_title}, #{f_path}, #{f_content}
}