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
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.support.FeignUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Optional;

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
        }catch (Exception e){
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
        account.setAccountPwd(accountPwd);
        account.setAccountApp(AppKeyEnum.SST.key); // 客户端无需 也无权提供！
        account.setAccountUserId(accountUserId);
        System.out.println(account);
        Result result = accountFeignService.registerAccountInfo(account);

        // Result里边的data包装的JSON会被转换为LinkedHashMap
        // // TODO: 2022/5/2  考虑清楚 传参的时候应该用什么数据结构？没必要JSON解析 但是有没有别的合适的数据结构？
        // 可能可以直接使用LinkedHashMap？ 因为保持顺序！
        if(result.getCode() == 0){
            LinkedHashMap linkedHashMap = (LinkedHashMap) result.getData();
            linkedHashMap.put("userId", accountUserId);
            return linkedHashMap;
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
    public Object getCaptcha(int userId) {
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

    @Require
    @ApiOperation("通过access登录 刷新refresh token")
    @GetMapping("/loginByAccess")
    public Object loginByAccess(int userId){

        return new Object();
    }

    // 无所谓 是个用户都应该享有这种权利！
    @Require
    @ApiOperation("客户端定时刷新 or loginByAccess都行 类似续杯1小时")
    @GetMapping("/refreshAccess")
    public Object refreshAccess(int userId){
        return new Object();
    }

    // 用过一次就失效！
    @Require(RoleEnum.EXPIRED)
    @ApiOperation("通过refresh token更新access token 适用于中间1分钟客户端没跑 但是没超过15天")
    @GetMapping("/refresh")
    public Object refresh(String userId){
        return new Object();
    }

    @ApiOperation("登录 或者说注册 反正验证一波")
    @GetMapping("/loginByPwd")
    public Object loginByPwd(String userId, String accountPwd){

        return new Object();
    }


    @Require
    @ApiOperation("更改密码")
    @GetMapping("/updatePwd")
    public Object updatePwd(String userId, String accountPwd){
        return new Object();
    }
}



