package com.ryanalexander.minipro.dao;

import com.alibaba.fastjson.JSONObject;
import com.ryanalexander.minipro.entries.A;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ADao {
    void Ainsert(List<A> list);
    List<String> AShowAllTime(String ATid);
    JSONObject AShow(String ATid, String Atime);
    List<JSONObject> AShowAll(String ATid);

}

