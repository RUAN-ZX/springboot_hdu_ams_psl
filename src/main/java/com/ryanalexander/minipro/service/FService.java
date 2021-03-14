package com.ryanalexander.minipro.service;

import com.alibaba.fastjson.JSONObject;
import com.ryanalexander.minipro.dao.FDao;
import com.ryanalexander.minipro.dao.TDao;
import com.ryanalexander.minipro.entries.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FService {
    @Autowired
    FDao fDao;

    @Resource
    TDao tDao;

    @Autowired
    private EmailService emailService;


    public JSONObject feedback(String t_mail, String f_t_id, String f_title, String f_path, String f_content){
        try{
            fDao.f_insert(f_t_id, f_title, f_path, f_content);

        }
        catch (Exception e){
            // 发邮件？？？ 用自己的邮箱！ 别用企业邮箱 有限额
            return ErrorService.getCode(-2,"实在不好意思 发送失败 数据库似乎出了问题 技术人员正在解决");


        }
        return ErrorService.getCode(0,"发送成功 工作人员会在一天之内向您的邮箱"+t_mail+"反馈审核结果");
    }

    public JSONObject rejectEmailChange(String f_id, String Tid){
        try{
            T temp = tDao.TgetById(Tid);
            String Temail = temp.getTmail();
            String Tname = temp.getTname();
            String Fcontent = fDao.f_select_content(f_id);

            emailService.sendFeedbackMails(Tid,Tname,Temail,Fcontent);
        }
        catch (Exception e){
            // 发邮件？？？ 用自己的邮箱！ 别用企业邮箱 有限额
            return ErrorService.getCode(-2,"");


        }
        return ErrorService.getCode(0,"");


    }
}