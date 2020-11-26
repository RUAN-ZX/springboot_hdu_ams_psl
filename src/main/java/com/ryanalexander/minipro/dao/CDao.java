package com.ryanalexander.minipro.dao;

import com.alibaba.fastjson.JSONObject;
import com.ryanalexander.minipro.entries.C;
import com.ryanalexander.minipro.service.excel_ali.entity.TeacherEntity_;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CDao {
    void Cinsert(List<C> list);

    C CGetById(String Cid);

    List<JSONObject> CShow(String CTid, String Ctime);
    List<String> CShowAllTime(String CTid);
}