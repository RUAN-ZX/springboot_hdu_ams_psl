package cn.ryanalexander.auth.service;

import cn.ryanalexander.auth.domain.po.AccountPO;
import cn.ryanalexander.common.enums.AppKeyEnum;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ryan
* @description 针对表【teacher】的数据库操作Service
* @createDate 2022-03-22 16:11:53
*/
public interface AccountService extends IService<AccountPO> {

    void updatePwdById(String accountId, String accountPwd);

    JSONObject refreshBothToken(String accountId, AppKeyEnum accountApp);
    JSONObject refreshAccess(String accountId, AppKeyEnum accountApp);

    String getEmailById(String Tid);

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

    void verifyRefresh(String accountId, AppKeyEnum accountApp, String refresh);
    void verifyAccess(String accountId, AppKeyEnum accountApp, String refresh);
    void verifyCaptcha(String accountId, AppKeyEnum accountApp, String captcha);
    void getCaptcha(String keyName, AppKeyEnum accountApp, String callName, String roleName, String mailTo);
}
