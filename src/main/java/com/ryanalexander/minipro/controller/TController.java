package com.ryanalexander.minipro.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSONObject;
import com.ryanalexander.minipro.dao.AccountDao;
import com.ryanalexander.minipro.dao.TDao;
import com.ryanalexander.minipro.entries.T;
import com.ryanalexander.minipro.service.*;
import com.ryanalexander.minipro.service.excel_ali.DataListener_T;
import com.ryanalexander.minipro.service.excel_ali.EasyExcelService;
import com.ryanalexander.minipro.service.excel_ali.entity.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TController {
    @Autowired
    private TDao tDao;
    @Resource
    private EmailService emailservice;

    @Autowired
    private TService tService;

    @Resource
    private StaticConfiguration StaticConfiguration;

    @Resource
    private AccountDao accountDao;
    @Autowired
    private EasyExcelService easyExcelService;




    @ApiOperation("SST Shiro hashcode")
    @PostMapping("/md5")
    public String md5Encryption(String name,String password){
        return MathService.md5Encryption(name,password);
    }


    @ApiOperation("通过token验证")
    @PostMapping("/loginByAccess")
    public String loginByaccess(String Tid, String access){
        JSONObject result = accountDao.TverifyAccess(Tid,access);
        if(result.getIntValue("code")==0){
            JSONObject jsonObject = tService.refreshBothToken(Tid);
            jsonObject.put("Tname",tDao.TgetById(Tid).getTname());
            return ErrorService.getCode(0, jsonObject).toJSONString();
        }

        return result.toJSONString();

    }

    @ApiOperation("通过refresh token更新access token")
    @PostMapping("/refresh")
    public String refresh(String Tid, String refresh){
        JSONObject result = accountDao.TverifyRefresh(Tid,refresh);
        if(result.getIntValue("code")==0){
            JSONObject jsonObject = tService.refreshBothToken(Tid);
            jsonObject.put("Tname",tDao.TgetById(Tid).getTname());
            return ErrorService.getCode(0, jsonObject).toJSONString();
        }

        return result.toJSONString();

    }

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

    /**
     * 1 用户登录 传输使用jwt生成的info token [token(Tid Tpwd)]
     * 2 服务器生成access token与refresh token 【token(Tid)】
     *
     */
    @ApiOperation("登录 或者说注册 反正验证一波")
    @PostMapping("/loginByPwd")
    public String loginBypwd(String Tid, String Tpwd) throws Exception {
//        String Tid = JSONObject.parseObject(AesService.decrypt(temp))
//                .getString("Tid");
//
//        String pwd = JSONObject.parseObject(AesService.decrypt(temp))
//                .getString("Tpwd");

        try{
            T t =  tDao.TgetById(Tid);
            // 密码不允许纯数字！！ 8位以上
            if(TService.iscaptcha(Tpwd)){
                JSONObject jsonObject = accountDao.TverifyCaptcha(Tid,Tpwd);
                if(jsonObject.getIntValue("code")!=0) return jsonObject.toJSONString();
            }// 普通密码
            else{
                if(t.getTPwd()==null){
                    return ErrorService.getCode(2,"请设置自己的密码 否则均需要验证码登录").toJSONString();
                }
                else if(!t.getTPwd().equals(Tpwd)){
                    return ErrorService.getCode(5,"密码错误").toJSONString();
                }
            }

            JSONObject jsonObject = tService.refreshBothToken(Tid);
            jsonObject.put("Tname",tDao.TgetById(Tid).getTname());
            return ErrorService.getCode(0, jsonObject).toJSONString();

        }
        catch (Exception e){
            System.out.println(e);
            //用户名找不到
            return ErrorService.getCode(1,"您的教工号可能输错了").toJSONString();
        }


    }






    @ApiOperation("获取验证码")
    @PostMapping("/getCaptcha")
    public String getCaptcha(String Tid) throws Exception {
//        String Tid = JSONObject.parseObject(AesService.decrypt(temp))
//                .getString("Tid");

        try{
            String Tname = tDao.TgetById(Tid).getTname();
            String Temail = tDao.TgetById(Tid).getTmail();

            accountDao.getCaptcha_async(Tid, Tname, Temail);
            return ErrorService.getCode(0,"验证码已发送到您的邮箱"+Temail+" 10分钟内有效").toJSONString();
        }
        catch (Exception e){
            return ErrorService.getCode(-1,"您的教工号可能输错了").toJSONString();
        }

    }



    @ApiOperation("更改密码")
    @PostMapping("/updatePwd")
    public String updatePwd(
            String Tid,
            String Tpwd,
            String access
        ){
        JSONObject jsonObject = accountDao.TverifyAccess(Tid,access);
        if(jsonObject.getIntValue("code")==0){
            tDao.TupdatePwd(Tid,Tpwd);
            return ErrorService.getCode(0,"修改完成").toJSONString();
        }
        else return jsonObject.toJSONString();



    }


    @ApiOperation("更新Excel")
    @GetMapping("/updateExcel")
    public String updateExcel(){
        String url = StaticConfiguration.getExcelUrl();
//      // 人员信息
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
                    .registerReadListener(new DataListener_T<TeacherEntity>(easyExcelService)).build();

            excelReader_1.read(readSheet1);
        } finally {
            if (excelReader_1 != null) excelReader_1.finish();
        }

        try {
            ReadSheet readSheet2 =
                    EasyExcel.readSheet("学评教总表")
                            .headRowNumber(2)
                            .head(EvaluationEntity.class)
                            .registerReadListener(new DataListener_T<EvaluationEntity>(easyExcelService)).build();

            excelReader_2.read(readSheet2);

        } finally {
            if (excelReader_2 != null) excelReader_2.finish();
        }

        try {
            ReadSheet readSheet3 =
                    EasyExcel.readSheet("学评教明细表")
                            .headRowNumber(2)
                            .head(CourseEntity.class)
                            .registerReadListener(new DataListener_T<CourseEntity>(easyExcelService)).build();

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
                            .registerReadListener(new DataListener_T<AchievementEntity>(easyExcelService)).build();

            excelReader_4.read(readSheet4);
//            return errorService.getCode(0,"good");

        } finally {
            if (excelReader_4 != null) excelReader_4.finish();
        }

        try {
            ReadSheet readSheet5 =
                EasyExcel.readSheet("工号和邮箱")
                    .headRowNumber(1)
                    .head(EmailEntity.class)
                    .registerReadListener(new DataListener_T<EmailEntity>(easyExcelService)).build();

            excelReader_5.read(readSheet5);
        } finally {
            if (excelReader_5 != null) excelReader_5.finish();
        }

        return ErrorService.getCode(0,"good").toJSONString();
    }

    @ApiOperation("getEmailById")
    @PostMapping("/getEmailById")
    public String getEmailById(String Tid, String access){
        return tService.verifyAccess(tService.getEmailById(Tid),Tid,access).toJSONString();
    }
}



