package com.ryanalexander.minipro.controller;

import com.alibaba.fastjson.JSONObject;
import com.ryanalexander.minipro.dao.AccountDao;
import com.ryanalexander.minipro.service.AService;
import com.ryanalexander.minipro.service.ErrorService;
import com.ryanalexander.minipro.service.TService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AController {
    @Autowired
    private AService aService;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private TService tService;
    @ApiOperation("获取最近一年的成就")
    @PostMapping("/AGetLast")
    public String AGetLast(String ATid,String access){
        return tService.verifyAccess(aService.AGetLast(ATid),ATid,access).toJSONString();
    }

    @ApiOperation("获取所有的年份 ")
    @PostMapping("/AGetAllYears")
    public String AGetAllYears(String ATid, String access){
        return tService.verifyAccess(aService.AGetAllYears(ATid),ATid,access).toJSONString();
    }

    @ApiOperation("获取所有的年份的结果")
    @PostMapping("/AGetAll")
    public String AGetAll(String ATid, String access){
        return tService.verifyAccess(aService.AGetAll(ATid),ATid,access).toJSONString();
    }

    @ApiOperation("获取当前老师一年最新总评A ")
    @PostMapping("/AGetOne")
    public String AGetOne(String ATid, String year, String access){
        return tService.verifyAccess(aService.AGetOne(ATid, year),ATid,access).toJSONString();
    }
}