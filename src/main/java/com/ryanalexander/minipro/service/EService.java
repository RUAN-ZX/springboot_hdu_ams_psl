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
        List<String> str = new ArrayList<String>();
        try{
            str = eDao.EShowAllTime(Eid);

            return ErrorService.getCode(0,eDao.EShow(Eid, Collections.max(str)));
        }
        catch (Exception e){
            return ErrorService.getCode(-1,"ETid 不存在");
        }

    }

}
