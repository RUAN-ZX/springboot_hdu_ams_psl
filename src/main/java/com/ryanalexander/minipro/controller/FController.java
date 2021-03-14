package com.ryanalexander.minipro.controller;

import com.ryanalexander.minipro.dao.FDao;
import com.ryanalexander.minipro.dao.TDao;
import com.ryanalexander.minipro.entries.T;
import com.ryanalexander.minipro.service.EmailService;
import com.ryanalexander.minipro.service.ErrorService;
import com.ryanalexander.minipro.service.FService;
import com.ryanalexander.minipro.service.TService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FController {
    @Autowired
    private FDao fDao;

    @Autowired
    private TDao tDao;


    @Autowired
    private FService fService;


    @ApiOperation("发送反馈")
    @PostMapping("/feedback")
    public String feedback(String Tid, String f_title, String f_path, String f_content) {
        try {
            String Temail = tDao.TgetById(Tid).getTmail();

            return fService.feedback(Temail, Tid, f_title, f_path, f_content).toJSONString();
        } catch (Exception e) {
            return ErrorService.getCode(-1, "您的教工号可能输错了 系统暂时没有收录此职工号").toJSONString();
        }
    }



    @ApiOperation("通过申请 更改邮箱")
    @PostMapping("/approveEmailChange")
    public String approveEmailChange(String Tid, String f_title, String f_path, String f_content) {
        try {
            String Temail = tDao.TgetById(Tid).getTmail();

            return fService.feedback(Temail, Tid, f_title, f_path, f_content).toJSONString();
        } catch (Exception e) {
            return ErrorService.getCode(-1, "您的教工号可能输错了 系统暂时没有收录此职工号").toJSONString();
        }

    }

    @ApiOperation("通过申请 更改邮箱")
    @PostMapping("/EmailChange")
    public String EmailChange(String Tid, String f_title, String f_path, String f_content) {
        try {
            String Temail = tDao.TgetById(Tid).getTmail();

            return fService.feedback(Temail, Tid, f_title, f_path, f_content).toJSONString();
        } catch (Exception e) {
            return ErrorService.getCode(-1, "您的教工号可能输错了 系统暂时没有收录此职工号").toJSONString();
        }

    }

    @ApiOperation("通过申请 更改邮箱")
    @PostMapping("/rejectEmailChange")
    public String rejectEmailChange(String f_id, String Tid) {
        try {

            return fService.rejectEmailChange(f_id, Tid).toJSONString();

        } catch (Exception e) {
            return ErrorService.getCode(-1, "您的教工号可能输错了 系统暂时没有收录此职工号").toJSONString();
        }

    }



}