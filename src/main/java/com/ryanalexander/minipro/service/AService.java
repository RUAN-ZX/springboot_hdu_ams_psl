package com.ryanalexander.minipro.service;

import com.alibaba.fastjson.JSONObject;
import com.ryanalexander.minipro.dao.ADao;
import com.ryanalexander.minipro.dao.EDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AService {
    @Autowired
    ADao aDao;

    public JSONObject AGetLast(String Aid){
        return AGetOne(Aid, Collections.max(aDao.AShowAllTime(Aid)));

    }
    public JSONObject AGetAllYears(String Aid){
        return ErrorService.getCode(0,aDao.AShowAllTime(Aid));
    }

    public JSONObject AGetOne(String Aid, String Year){
        try{
            return ErrorService.getCode(0,aDao.AShow(Aid, Year));
        }
        catch (Exception e){
            return ErrorService.getCode(-1,"ATid 不存在");
        }

    }

    public JSONObject AGetAll(String Aid){
        try{
            return ErrorService.getCode(0,aDao.AShowAll(Aid));
        }
        catch (Exception e){
            return ErrorService.getCode(-1,"ATid 不存在");
        }

    }


}

