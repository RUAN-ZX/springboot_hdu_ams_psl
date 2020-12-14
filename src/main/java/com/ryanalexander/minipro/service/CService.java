package com.ryanalexander.minipro.service;

import com.alibaba.fastjson.JSONObject;
import com.ryanalexander.minipro.dao.ADao;
import com.ryanalexander.minipro.dao.CDao;
import com.ryanalexander.minipro.entries.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CService {

    @Autowired
    CDao cDao;


    public JSONObject CGetLast(String Cid){
        return CGetOne(Cid, Collections.max(cDao.CShowAllTime(Cid)));
    }
//    public JSONObject CGetAllYears(String Cid){
//        return ErrorService.getCode(0,cDao.CShowAllTime(Cid));
//    }
    public JSONObject CGetOne(String Cid, String Year){
        try{
            return ErrorService.getCode(0,cDao.CShow(Cid, Year));
        }
        catch (Exception e){
            return ErrorService.getCode(-1,"CTid 不存在");
        }

    }



}
