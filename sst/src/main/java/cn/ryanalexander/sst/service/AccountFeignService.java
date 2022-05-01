package cn.ryanalexander.sst.service;

import cn.ryanalexander.common.domain.dto.Account;
import cn.ryanalexander.common.domain.dto.Result;
import cn.ryanalexander.common.enums.AppKeyEnum;
import feign.Headers;
import feign.RequestLine;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@FeignClient(name = "nacos-provide",fallback = RemoteHystrix.class)
// hystrix 降级

// Bean 'AccountFeignService' of type [org.springframework.cloud.openfeign.FeignClientFactoryBean]
// is not eligible for getting processed by all BeanPostProcessors
// (for example: not eligible for auto-proxying)
@FeignClient(name = "auth")
public interface AccountFeignService {

//    @RequestLine("GET /verifyRefresh")
//    @Headers({"refresh: {token}"})
//    void verifyRefresh(String accountId, String refresh);

    @GetMapping("/registerAccountInfo")
    Result registerAccountInfo(Account account);

    @GetMapping("/changeAccountInfo")
    Result changeAccountInfo(Account account);

    // ==================================================================================

    @GetMapping("/verifyRefresh")
    Result verifyRefresh(@RequestParam("accountId")String accountId, AppKeyEnum appKeyEnum, @RequestParam("refresh")String refresh);

    @GetMapping("/verifyAccess")
    Result verifyAccess(@RequestParam("accountId")String accountId, AppKeyEnum appKeyEnum, @RequestParam("access")String access);

    @GetMapping("/verifyCaptcha")
    Result verifyCaptcha(@RequestParam("accountId")String accountId, AppKeyEnum appKeyEnum, @RequestParam("captcha")String captcha);

    // ==================================================================================

    @GetMapping("/getCaptcha")
    Result getCaptcha(@RequestParam("accountId")String accountId, AppKeyEnum appKeyEnum, @RequestParam("roleName")String roleName);

    @GetMapping("/sendCaptcha")
    Result sendCaptcha(@RequestParam("accountMail")String accountMail, AppKeyEnum appKeyEnum);

    // ==================================================================================

    @GetMapping("/refreshBothToken")
    Result refreshBothToken(String accountId, AppKeyEnum accountApp);

    @GetMapping("/refreshAccess")
    Result refreshAccess(String accountId, AppKeyEnum accountApp);

    // ==================================================================================

    @GetMapping("/loginByPwd")
    Result loginByPwd(String accountId, AppKeyEnum accountApp, String accountPwd);

    @GetMapping("/updatePwd")
   Result updatePwd(String accountId, String accountPwd);
}
