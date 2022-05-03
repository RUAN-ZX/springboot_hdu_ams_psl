package cn.ryanalexander.auth.service;

import cn.ryanalexander.auth.domain.po.AccountPO;

import cn.ryanalexander.common.domain.dto.MailInfo;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author ryan
* @description 针对表【teacher】的数据库操作Service
* @createDate 2022-03-22 16:11:53
*/
public interface AccountService extends IService<AccountPO> {

    void updatePwdById(int accountId, String accountPwd);

    Map<String, String> refreshBothToken(int accountId, int accountApp);
    Map<String, String> refreshAccess(int accountId, int accountApp);



    static boolean isCaptcha(String str) {
        if(str.length()!=6) return false;
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    void verifyRefresh(int accountId, int accountApp, String refresh);
    void verifyAccess(int accountId, int accountApp, String refresh);
    void verifyCaptcha(String keyName, int accountApp, String captcha);
    void verifyMail(String mail);

    AccountPO existMail(String mail);

    void getCaptcha(MailInfo mailInfo);


}
