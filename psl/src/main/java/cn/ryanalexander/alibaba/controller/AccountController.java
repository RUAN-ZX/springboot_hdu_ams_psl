package cn.ryanalexander.alibaba.controller;


import cn.ryanalexander.alibaba.domain.exceptions.UnKnownException;
import cn.ryanalexander.alibaba.mapper.AccountDao;
import cn.ryanalexander.alibaba.domain.po.AccountPO;
import cn.ryanalexander.alibaba.service.*;
import cn.ryanalexander.alibaba.service.tool.EmailService;
import cn.ryanalexander.alibaba.service.tool.ErrorService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

@RestController
public class AccountController {
    // 如果该Service有多个实现类，设置注入哪个ServiceImpl类
    // @Qualifier("womanService") 而serviceImpl要制定好名字！@Service("")
    @Resource
    private AccountService accountService;

    @Resource
    private EmailService emailservice;

    @Resource
    private AccountDao accountDao;


    @ApiOperation("通过token验证")
    @PostMapping("/loginByAccess")
    public String loginByaccess(String Tid, String access){
        JSONObject result = accountDao.TverifyAccess(Tid,access);
        if(result.getIntValue("code")==0){
            JSONObject jsonObject = accountService.refreshBothToken(Tid);
            jsonObject.put("Tname", accountService.getById(Tid).getAccountName());
            return ErrorService.getCode(0, jsonObject).toJSONString();
        }
        return result.toJSONString();
    }

    @ApiOperation("通过refresh token更新access token")
    @PostMapping("/refresh")
    public String refresh(String Tid, String refresh){
        JSONObject result = accountDao.TverifyRefresh(Tid,refresh);
        if(result.getIntValue("code")==0){
            JSONObject jsonObject = accountService.refreshBothToken(Tid);
            jsonObject.put("Tname", accountService.getById(Tid).getAccountName());
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
    public String loginByPwd(String Tid, String Tpwd) throws Exception {
//        String Tid = JSONObject.parseObject(AesService.decrypt(temp))
//                .getString("Tid");
//
//        String pwd = JSONObject.parseObject(AesService.decrypt(temp))
//                .getString("Tpwd");

        try{
            AccountPO t =  accountService.getById(Tid);
            // 密码不允许纯数字！！ 8位以上
            if(AccountService.isCaptcha(Tpwd)){
                JSONObject jsonObject = accountDao.TverifyCaptcha(Tid,Tpwd);
                if(jsonObject.getIntValue("code")!=0) return jsonObject.toJSONString();
            }// 普通密码
            else{
                if(t.getAccountPwd()==null){
                    return ErrorService.getCode(2,"请设置自己的密码 否则均需要验证码登录").toJSONString();
                }
                else if(!t.getAccountPwd().equals(Tpwd)){
                    return ErrorService.getCode(5,"密码错误").toJSONString();
                }
            }

            JSONObject jsonObject = accountService.refreshBothToken(Tid);
            jsonObject.put("Tname", accountService.getById(Tid).getAccountName());
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
        try{
            Optional<AccountPO> accountNullable = Optional.ofNullable(accountService.getById(Tid));
            AccountPO accountPO = accountNullable.orElseThrow(() -> new UnKnownException(AccountPO.class));
            String Tname = accountPO.getAccountName();
            String Temail = accountPO.getAccountMail();

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
            accountService.updatePwdById(Tid,Tpwd);
            return ErrorService.getCode(0,"修改完成").toJSONString();
        }
        else return jsonObject.toJSONString();
    }
    @ApiOperation("getEmailById")
    @PostMapping("/getEmailById")
    public String getEmailById(String Tid, String access){
        return accountService.verifyAccess(accountService.getEmailById(Tid),Tid,access).toJSONString();
    }
}



