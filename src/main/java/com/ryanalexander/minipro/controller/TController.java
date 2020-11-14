package com.ryanalexander.minipro.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.ryanalexander.minipro.dao.TDao;
import com.ryanalexander.minipro.entries.T;
import com.ryanalexander.minipro.service.emailService;
import com.ryanalexander.minipro.service.errorService;
import com.ryanalexander.minipro.service.excel_ali.DataListener_T;
import com.ryanalexander.minipro.service.excel_ali.entity.*;
import com.ryanalexander.minipro.service.jwtService;
import com.ryanalexander.minipro.service.verifyService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.javassist.bytecode.stackmap.TypeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TController {
    @Autowired
    private TDao tDao;


    @Autowired
    private JavaMailSender mailSender;


    List temp = new ArrayList();
    /**
     *
     * return token!!! or error code!!!!
     *
     *
     * 检测到输入为6位数字 -> 且验证码字段为空 则验证码过期了
     *  *              验证码字段不为空 但是错了 那就是验证码错误
     *      *      检测到其他输入 却是第一次使用本系统 没有密码 -> 还没有注册过密码 请先使用验证码登录 并注册自己的密码 以为未来登录时使用
     *      *      检测到其他输入 密码错误 ——>密码错误！
     */
    @ApiOperation("登录 或者说注册 反正验证一波")
    @PostMapping("/login")
    public String verifyByMailPwd(String Tid, String pwd){

        try{
            T t =  tDao.TgetById(Tid);
            // 第一次使用的用户 要求后面设置密码 无论验证码对不对！ ? 要不让前端判断得了 ？
            // 怎么结合spring security验证啊 感觉直接jwt也行啊
            if(verifyService.iscaptcha(pwd)){
                if(t.getTcaptcha().equals("")) return errorService.getCode(3,"验证码过期啦");
                else if(!t.getTcaptcha().equals(pwd)) return errorService.getCode(4,"验证码错误");

            }// 普通密码
            else{

                if(t.getTPwd().equals("")){
                    errorService.getCode(2,"请设置自己的密码 否则均需要验证码登录");
                }
                else if(!t.getTPwd().equals(pwd)){
                    return errorService.getCode(5,"密码错误");
                }
            }

        }
        catch (Exception e){
            System.out.println(e);
            //用户名找不到
            return errorService.getCode(1,"教工号不存在");
        }

        // 无论是验证码还是密码都是ok的 不过怎么验证token时 不把密码加入token！！！ 这样也不需要验证密码和验证码
        return errorService.getCode(0,jwtService.getToken(Tid));
    }

    /**
     *
     * @param Tid
     * @return
     * 到时候问候语句加上老师名称 职工号等信息 更加可信
     * 定时任务timertask还没有实现 到时候先写入captcha然后定时清除 所以
     *
     */
    @ApiOperation("获取验证码")
    @PostMapping("/getCaptcha")
    public boolean getCaptcha(String Tid) throws MessagingException {
        emailService emailservice = new emailService(mailSender);
        emailservice.sendMails(Tid);
        //
        return true;
    }


    @ApiOperation("更改密码")
    @PostMapping("/updatePwd")
    public String updatePwd(
            String Tid,
            String Tpwd
        ){
        // 验证token之后 当然：）
        tDao.TupdatePwd(Tid,Tpwd);
        return errorService.getCode(1,"修改完成");

    }


    @ApiOperation("更新Excel")
    @GetMapping("/updateExcel")
    public String updateExcel(){
        String url = "D:\\Users\\Ryan\\IdeaProjects\\psl\\src\\main\\java\\com\\ryanalexander\\minipro\\service\\excel_ali\\教务查询系统数据.xlsx";
        // 人员信息
        ExcelReader excelReader_1 = EasyExcel.read(url).build();

        //学评教总表
        ExcelReader excelReader_2 = EasyExcel.read(url).build();

        //学评教明细
        ExcelReader excelReader_3 = EasyExcel.read(url).build();

        //业绩考核明细
        ExcelReader excelReader_4 = EasyExcel.read(url).build();

        //邮箱
        ExcelReader excelReader_5 = EasyExcel.read(url).build();
        try {
            ReadSheet readSheet1 = EasyExcel.readSheet("人员信息")
                    .head(TeacherEntity.class)
                    .registerReadListener(new DataListener_T<TeacherEntity>()).build();

            excelReader_1.read(readSheet1);
        } finally {
            if (excelReader_1 != null) excelReader_1.finish();
        }

        try {
            ReadSheet readSheet2 =
                    EasyExcel.readSheet("学评教总表")
                            .headRowNumber(2)
                            .head(EvaluationEntity.class)
                            .registerReadListener(new DataListener_T<EvaluationEntity>()).build();

            excelReader_2.read(readSheet2);

        } finally {
            if (excelReader_2 != null) excelReader_2.finish();
        }

        try {
            ReadSheet readSheet3 =
                    EasyExcel.readSheet("学评教明细表")
                            .headRowNumber(2)
                            .head(CourseEntity.class)
                            .registerReadListener(new DataListener_T<CourseEntity>()).build();

            excelReader_3.read(readSheet3);
//            return errorService.getCode(0,"good");

        } finally {
            if (excelReader_3 != null) excelReader_3.finish();
        }


        try {
            ReadSheet readSheet4 =
                    EasyExcel.readSheet("业绩考核明细表")
                            .headRowNumber(3)
                            .head(AchievementEntity.class)
                            .registerReadListener(new DataListener_T<AchievementEntity>()).build();

            excelReader_4.read(readSheet4);
//            return errorService.getCode(0,"good");

        } finally {
            if (excelReader_4 != null) excelReader_4.finish();
        }

        try {
//            System.out.println("fuxk");
            ReadSheet readSheet5 =
                EasyExcel.readSheet("工号和邮箱")
                    .headRowNumber(1)
                    .head(EmailEntity.class)
                    .registerReadListener(new DataListener_T<EmailEntity>()).build();

            excelReader_5.read(readSheet5);
        } finally {
            if (excelReader_5 != null) excelReader_5.finish();
        }

        return errorService.getCode(0,"good");
    }
}



