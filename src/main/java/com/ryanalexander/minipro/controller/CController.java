package com.ryanalexander.minipro.controller;

import com.alibaba.fastjson.JSONObject;
import com.ryanalexander.minipro.dao.AccountDao;
import com.ryanalexander.minipro.dao.CDao;
import com.ryanalexander.minipro.dao.EDao;
import com.ryanalexander.minipro.service.CService;
import com.ryanalexander.minipro.service.TService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CController {
    @Autowired
    private CService cService;
    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TService tService;
    @ApiOperation("获取当前老师最新的课程学评教信息C 总评数据")
    @PostMapping("/CGetLast")
    public String CGetLast(String CTid, String access){
        return tService.verifyAccess(cService.CGetLast(CTid),CTid,access).toJSONString();
    }

//    @ApiOperation("获取当前老师最新的课程学评教信息C 所有的年份 ")
//    @PostMapping("/CGetAllYears")
//    public String CGetAllYears(String CTid, String access){
//        return tService.verifyAccess(cService.CGetAllYears(CTid),CTid,access).toJSONString();
//    }
    @ApiOperation("获取当前老师一年的课程学评教信息C ")
    @PostMapping("/CGetOne")
    public String CGetOne(String CTid, String year, String access){
        return tService.verifyAccess(cService.CGetOne(CTid, year),CTid,access).toJSONString();
    }

}



