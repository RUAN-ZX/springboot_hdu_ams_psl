package cn.ryanalexander.sst.service;

import cn.ryanalexander.common.domain.dto.Account;
import cn.ryanalexander.common.domain.dto.MailInfo;
import cn.ryanalexander.common.domain.dto.Result;
import cn.ryanalexander.common.enums.AppKeyEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/registerAccountInfo")
    Result registerAccountInfo(@RequestBody Account account);

    @PostMapping("/changeAccountInfo")
    Result changeAccountInfo(@RequestBody Account account);

    // ==================================================================================

//    @PostMapping("/loginByPwd")
//    Result loginByPwd(@RequestBody Account account);

    @PostMapping("/loginByPwd")
    Result loginByPwd(@RequestBody Account account);
    // 填充需要的部分 int accountApp, String accountPwd 如果是邮箱那就带上email 如果是手机号就带上phone！
    // 如果用用户名就是accountName PSL那个其实是accountName

    @PostMapping("/updatePwd")
    Result updatePwd(@RequestParam int userId, @RequestParam int accountApp, @RequestParam String accountPwd);

    // ==================================================================================

    @GetMapping("/verifyRefresh")
    Result verifyRefresh(@RequestParam int userId, @RequestParam int accountApp, @RequestParam String refresh);

    @GetMapping("/verifyAccess")
    Result verifyAccess(@RequestParam int userId, @RequestParam int accountApp, @RequestParam String access);

    @GetMapping("/verifyCaptcha")
    Result verifyCaptcha(@RequestParam String keyName, @RequestParam int accountApp, @RequestParam String captcha);

    // ==================================================================================

    @GetMapping("/getCaptcha")
    Result getCaptcha(@RequestParam int userId, @SpringQueryMap MailInfo mailInfo);

    @GetMapping("/sendCaptcha")
    Result sendCaptcha(@SpringQueryMap MailInfo mailInfo);

    // ==================================================================================

    @GetMapping("/refreshBothToken")
    Result refreshBothToken(@RequestParam int userId, @RequestParam int accountApp);

    @GetMapping("/refreshAccess")
    Result refreshAccess(@RequestParam int userId, @RequestParam int accountApp);

    // ==================================================================================

}
