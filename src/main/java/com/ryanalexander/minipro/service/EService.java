package com.ryanalexander.minipro.service;

import com.alibaba.fastjson.JSONObject;
import com.ryanalexander.minipro.dao.EDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EService {
    @Autowired
    EDao eDao;

    public JSONObject EGetLast(String Eid){
        return EGetOne(Eid, Collections.max(eDao.EShowAllTime(Eid)));
    }
    public JSONObject EGetAllYears(String Eid){
        return ErrorService.getCode(0,eDao.EShowAllTime(Eid));
    }
    public JSONObject EGetOne(String Eid, String Year){
        try{
            return ErrorService.getCode(0,eDao.EShow(Eid, Year));
        }
        catch (Exception e){
            return ErrorService.getCode(-1,"ETid 不存在");
        }

    }


}
