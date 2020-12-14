package com.ryanalexander.minipro.controller;

import com.alibaba.fastjson.JSONObject;
import com.ryanalexander.minipro.dao.AccountDao;
import com.ryanalexander.minipro.dao.EDao;
import com.ryanalexander.minipro.service.AService;
import com.ryanalexander.minipro.service.EService;
import com.ryanalexander.minipro.service.ErrorService;
import com.ryanalexander.minipro.service.TService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EController {
    @Autowired
    private EService eService;
    @Autowired
    private TService tService;
    @Autowired
    private AccountDao accountDao;
    @ApiOperation("获取当前老师最新的学评教E 总评数据")
    @PostMapping("/EgetLast")
    public String EGetLast(String ETid, String access){
        return tService.verifyAccess(eService.EGetLast(ETid),ETid,access).toJSONString();
    }

    @ApiOperation("获取当前老师最新学评教总评E 所有的年份 ")
    @PostMapping("/EGetAllYears")
    public String EGetAllYears(String ETid, String access){
        return tService.verifyAccess(eService.EGetAllYears(ETid),ETid,access).toJSONString();
    }
    @ApiOperation("获取当前老师一年最新总评A ")
    @PostMapping("/EGetOne")
    public String EGetOne(String ETid, String year, String access){
        return tService.verifyAccess(eService.EGetOne(ETid, year),ETid,access).toJSONString();
    }

}

