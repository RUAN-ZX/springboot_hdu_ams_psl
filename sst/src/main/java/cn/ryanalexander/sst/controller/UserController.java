package cn.ryanalexander.sst.controller;

import cn.ryanalexander.common.domain.dto.Account;
import cn.ryanalexander.common.domain.dto.MailInfo;
import cn.ryanalexander.common.domain.dto.Result;
import cn.ryanalexander.common.domain.exceptions.AppException;
import cn.ryanalexander.common.domain.exceptions.code.ErrorCode;
import cn.ryanalexander.common.domain.exceptions.code.ErrorCodeEnum;
import cn.ryanalexander.common.domain.exceptions.code.SubjectEnum;
import cn.ryanalexander.common.enums.AccountStatusEnum;
import cn.ryanalexander.common.enums.AppKeyEnum;
import cn.ryanalexander.sst.domain.po.UserPO;
import cn.ryanalexander.sst.mapper.UserMapper;
import cn.ryanalexander.sst.processor.annotationIntercept.Require;
import cn.ryanalexander.sst.processor.annotationIntercept.RoleEnum;
import cn.ryanalexander.sst.service.AccountFeignService;
import cn.ryanalexander.sst.service.UserService;
import cn.ryanalexander.sst.service.tool.StaticConfiguration;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.cloud.openfeign.support.FeignUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.service.ParameterType;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Api(tags = "用户 账户操作")
@RestController
public class UserController {
    // 如果该Service有多个实现类，设置注入哪个ServiceImpl类
    // @Qualifier("womanService") 而serviceImpl要制定好名字！@Service("")

    @Resource
    private StaticConfiguration staticConfiguration;
    @Resource
    private UserService userService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private AccountFeignService accountFeignService;

    @Transactional
    @ApiOperation("邮箱注册")
    @PostMapping("/registerByMail")
    public Object registerByMail(String userName,
                                 int userRole,
                                 String userAlias,
                                 String accountMail,
                                 String captcha,
                                 String accountPwd){

        try{
            accountFeignService.verifyCaptcha(accountMail, AppKeyEnum.SST.key, captcha);
        } catch (Exception e){
//            e.printStackTrace();
            throw new AppException(new ErrorCode(SubjectEnum.INTERNAL),"邮箱验证码过期了");
        }

        UserPO userPO = new UserPO();
        userPO.setUserName(userName);
        userPO.setUserAlias(userAlias);
        userPO.setUserRole(userRole);
        userMapper.insert(userPO); // Mapper会采用自增的方式 而Service的save很可能采用雪花算法 不知道为啥？
        int accountUserId = userPO.getUserId();

        Account account = new Account();
        account.setAccountMail(accountMail);
        account.setAccountName(userName); // 用户name 真名 设置为用户名！
        account.setAccountPwd(accountPwd);
        account.setAccountApp(AppKeyEnum.SST.key); // 客户端无需 也无权提供！
        account.setAccountUserId(accountUserId);
        System.out.println(account);
        Result result = accountFeignService.registerAccountInfo(account);

        // Result里边的data包装的JSON会被转换为LinkedHashMap
        // 如果传键值对 直接用JSONObject 或者Map都行了 当然接收一定要Map或者HashMap
        // 如果是对象 那就只能自个儿序列化 JSONString 然后后边parse了
        // 可能可以直接使用LinkedHashMap？ 因为保持顺序！
        if(result.getCode() == 0){
            Map<String, String> dataMap = (Map<String, String>) result.getData();
            dataMap.put("userId", String.valueOf(accountUserId));
            return dataMap;
        } else throw new AppException(new ErrorCode(SubjectEnum.INTERNAL),"RPC Failed");

    }

//    @ApiOperation("手机号注册")
//    @PostMapping("/registerByPhone")
//    public Object registerByPhone(String accountPhone, String accountPwd){
//        Account account = new Account();
//        account.setAccountPhone(accountPhone);
//        account.setAccountPwd(accountPwd);
//        account.setAccountApp(AppKeyEnum.SST.key); // 客户端无需 也无权提供！
//        return accountFeignService.registerAccountInfo(account);
//    }

//    @Require
    @ApiOperation("已经注册的邮箱 获取验证码")
    @GetMapping("/getCaptcha")
    public Object getCaptcha(
            @RequestHeader String access,
            int userId) {
        UserPO userPO = userMapper.selectOne(new QueryWrapper<UserPO>()
                .eq("user_id", userId).last("limit 1"));

        MailInfo mailInfo = new MailInfo();
        mailInfo.setAccountApp(AppKeyEnum.SST.key);
        mailInfo.setCaptchaExpire(staticConfiguration.getCaptchaExpire());
        mailInfo.setMailHtmlUrl(staticConfiguration.getMailUrl());

        mailInfo.setCallName(userPO.getUserName()); // 阮智祥同学
        mailInfo.setRoleName(RoleEnum.getEnumMap().get(userPO.getUserRole()));

        return accountFeignService.getCaptcha(userId, mailInfo);
    }

    @ApiOperation("未注册 未登记的邮箱 获取验证码 如果已经登记的邮箱直接报错不发验证码 否则浪费大家时间")
    @GetMapping("/sendCaptcha")
    public Object sendCaptcha(String mailTo){
        MailInfo mailInfo = new MailInfo();
        mailInfo.setAccountApp(AppKeyEnum.SST.key);
        mailInfo.setCaptchaExpire(staticConfiguration.getCaptchaExpire());
        mailInfo.setMailHtmlUrl(staticConfiguration.getMailUrl());

        mailInfo.setCallName("您好");
        mailInfo.setRoleName("");
        mailInfo.setMailTo(mailTo);

        return accountFeignService.sendCaptcha(mailInfo);
    }

//    @Require
//    @ApiOperation("通过access登录")
//    @GetMapping("/loginByAccess")
//    public Object loginByAccess(int userId){
//        return new Object();
//    }

    // 无所谓 是个用户都应该享有这种权利！
    @Require
    @ApiOperation("客户端定时刷新 or loginByAccess都行 类似续杯10分钟")
    @GetMapping("/refreshAccess")
    public Object refreshAccess(int userId){
        return accountFeignService.refreshAccess(userId, AppKeyEnum.SST.key);
    }

    // 前端没必要使用access自动登录了 10分钟真不太可能 10分钟以内 微信也会保留页面的！10分钟差不多缓存清理了 那就refresh认证咯
    // 也不太可能抓到这个机会吧。。
    // 所以这接口直接作为自动登录的接口完事了 反正refreshToken也只会使用一次！

//    @Require(RoleEnum.EXPIRED)
    @ApiOperation("通过refresh token更新access token 适用于中间十多分钟客户端没跑 但是没超过15天")
    @GetMapping("/refresh")
    public Object refresh(
            @RequestHeader String refresh,
            int userId){
        return accountFeignService.refreshBothToken(userId, AppKeyEnum.SST.key).getData();
    }

    // @Param keyName 用户名 | 邮箱 | 手机号
    @ApiResponses({
        @ApiResponse(responseCode = "0", description = "返回Map<String, String> 包含access:accessToken | refresh:refreshToken | account:userId")
    })
    @ApiOperation("邮箱登录")
    @GetMapping("/loginByMailPwd")
    public Object loginByMailPwd(String accountMail, String accountPwd){
        Account account = new Account();
        account.setAccountMail(accountMail);
        account.setAccountPwd(accountPwd);
        account.setAccountApp(AppKeyEnum.SST.key);

        return accountFeignService.loginByPwd(account);
    }

    // @Param keyName 用户名 | 邮箱 | 手机号
    @GetMapping("/loginByNamePwd")
    public Object loginByNamePwd(String accountName, String accountPwd){
        Account account = new Account();
        account.setAccountName(accountName);
        account.setAccountPwd(accountPwd);
        account.setAccountApp(AppKeyEnum.SST.key);

        return accountFeignService.loginByPwd(account);
    }

//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "accountPwd", value = "新的密码", required = true, dataType = "String")
//            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String")
//    })
//    @Require
    @ApiOperation("更改密码")
    @GetMapping("/updatePwd")
    public Object updatePwd(
            @RequestHeader String access,
            @Parameter(description = "用户ID", required = true) int userId,
            @Parameter(description = "新的密码", required = true) String accountPwd){
        // 不用担心 userId穿透到别的用户 因为Require会检查的! 不匹配直接拒绝
        return accountFeignService.updatePwd(userId, AppKeyEnum.SST.key, accountPwd);
    }

    //    @Require
    @ApiOperation("获取自己的个人信息")
    @GetMapping("/getPersonalInfo")
    public UserPO getPersonalInfo(
            @RequestHeader String access,
            int userId){
        return userMapper.selectOne(new QueryWrapper<UserPO>()
                .eq("user_id", userId).last("limit 1"));
    }
}



