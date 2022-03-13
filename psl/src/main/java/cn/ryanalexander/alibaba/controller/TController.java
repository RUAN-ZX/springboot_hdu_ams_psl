package cn.ryanalexander.alibaba.controller;


import cn.ryanalexander.alibaba.dao.AccountDao;
import cn.ryanalexander.alibaba.dao.TeacherDao;
import cn.ryanalexander.alibaba.entity.Teacher;
import cn.ryanalexander.alibaba.entity.TidAndEmail;
import cn.ryanalexander.alibaba.entity.excel.*;
import cn.ryanalexander.alibaba.service.*;
import cn.ryanalexander.alibaba.service.excel_ali.DataListener_T;
import cn.ryanalexander.alibaba.service.excel_ali.EasyExcelService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class TController {
    @Resource
    private TeacherDao tDao;

    @Resource
    private EmailService emailservice;

    @Autowired
    private TService tService;

    @Resource
    private StaticConfiguration StaticConfiguration;

    @Resource
    private AccountDao accountDao;

    @Resource
    private EasyExcelService easyExcelService;

    @ApiOperation("通过token验证")
    @PostMapping("/loginByAccess")
    public String loginByaccess(String Tid, String access){
        JSONObject result = accountDao.TverifyAccess(Tid,access);
        if(result.getIntValue("code")==0){
            JSONObject jsonObject = tService.refreshBothToken(Tid);
            jsonObject.put("Tname",tDao.TgetById(Tid).getT_name());
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
            jsonObject.put("Tname",tDao.TgetById(Tid).getT_name());
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
            Teacher t =  tDao.TgetById(Tid);
            // 密码不允许纯数字！！ 8位以上
            if(TService.iscaptcha(Tpwd)){
                JSONObject jsonObject = accountDao.TverifyCaptcha(Tid,Tpwd);
                if(jsonObject.getIntValue("code")!=0) return jsonObject.toJSONString();
            }// 普通密码
            else{
                if(t.getT_pwd()==null){
                    return ErrorService.getCode(2,"请设置自己的密码 否则均需要验证码登录").toJSONString();
                }
                else if(!t.getT_pwd().equals(Tpwd)){
                    return ErrorService.getCode(5,"密码错误").toJSONString();
                }
            }

            JSONObject jsonObject = tService.refreshBothToken(Tid);
            jsonObject.put("Tname",tDao.TgetById(Tid).getT_name());
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
            String Tname = tDao.TgetById(Tid).getT_name();
            String Temail = tDao.TgetById(Tid).getT_mail();

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

    private static HashMap<String, Class<?>> sheetAndExcelEntity = new HashMap<>();
    static{
        sheetAndExcelEntity.put("理论", CourseTheory.class);
        sheetAndExcelEntity.put("工号和邮箱", TidAndEmail.class);
    }
    @ApiOperation("更新Excel")
    @GetMapping("/updateExcel")
    public String updateExcel(){
        String url = StaticConfiguration.getExcelUrl();
        // 理论
        ExcelReader excelReader = EasyExcel.read(url).build();

        List<ReadSheet> sheets = excelReader.excelExecutor().sheetList();
        String regex = "([\u4e00-\u9fa5]+)";
        // 多年的表 故需要匹配名字 然后执行对应的套路
        for (ReadSheet sheet : sheets) {
            Matcher matcher = Pattern.compile(regex).matcher(sheet.getSheetName());
            String matcherResult = null;
            Class<?> hashResult = null;
            if(matcher.find()) {
                matcherResult = matcher.group(1);
                hashResult = sheetAndExcelEntity.get(matcherResult);
            }

            if(hashResult != null){
                ReadSheet readSheet1 = EasyExcel.readSheet(matcherResult)
                        .headRowNumber(2)
                        .head(hashResult)
                        .registerReadListener(new DataListener_T<CourseTheory>(easyExcelService)).build();

                excelReader.read(readSheet1);
            }

        }
        try {
            ReadSheet readSheet1 = EasyExcel.readSheet("理论")
                    .headRowNumber(2)
                    .head(CourseTheory.class)
                    .registerReadListener(new DataListener_T<CourseTheory>(easyExcelService)).build();

//            excelReader.read(readSheet1);


            ReadSheet readSheet2 =
                    EasyExcel.readSheet("实验")
                            .headRowNumber(2)
                            .head(CourseExperiment.class)
                            .registerReadListener(new DataListener_T<CourseExperiment>(easyExcelService)).build();

//            excelReader.read(readSheet2);


            ReadSheet readSheet3 =
                    EasyExcel.readSheet("短学期")
                            .headRowNumber(2)
                            .head(CourseShortTerm.class)
                            .registerReadListener(new DataListener_T<CourseShortTerm>(easyExcelService)).build();

//            excelReader.read(readSheet3);

            ReadSheet readSheet4 =
                    EasyExcel.readSheet("毕业设计")
                            .headRowNumber(2)
                            .head(CourseThesisDesign.class)
                            .registerReadListener(new DataListener_T<CourseThesisDesign>(easyExcelService)).build();

//            excelReader.read(readSheet4);


            ReadSheet readSheet5 =
                    EasyExcel.readSheet("工号和邮箱")
                            .headRowNumber(1)
                            .head(TidAndEmail.class)
                            .registerReadListener(new DataListener_T<TidAndEmail>(easyExcelService)).build();

//            excelReader.read(readSheet5);

        } finally {
            if (excelReader != null) excelReader.finish();
        }
        return ErrorService.getCode(0,"good").toJSONString();
    }

    @ApiOperation("getEmailById")
    @PostMapping("/getEmailById")
    public String getEmailById(String Tid, String access){
        return tService.verifyAccess(tService.getEmailById(Tid),Tid,access).toJSONString();
    }
}



