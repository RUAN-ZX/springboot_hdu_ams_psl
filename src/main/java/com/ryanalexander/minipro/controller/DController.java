package com.ryanalexander.minipro.controller;

import com.ryanalexander.minipro.dao.DDao;
import com.ryanalexander.minipro.entries.D;
import com.ryanalexander.minipro.service.ErrorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DController {
    @Autowired
    private DDao dDao;

    
    @ApiOperation("根据ID获取部门名字")
    @PostMapping("/DgetById")
    public String DgetById(
            String Did
    ){
        // 验证token之后 当然：）
        
        return ErrorService.getCode(1,dDao.DgetById(Did)).toJSONString();

    }

    @ApiOperation("部门插入")
    @PostMapping("/Dinsert")
    public String Dinsert(
            String Did,
            String Dname
    ){

        D d = new D(Did,Dname);
        try {
            dDao.Dinsert(d);
            return ErrorService.getCode(0,dDao.DgetById(Did)).toJSONString();

        }
        catch (Exception e){

            return ErrorService.getCode(1,e.toString()).toJSONString();
        }
    }

}

