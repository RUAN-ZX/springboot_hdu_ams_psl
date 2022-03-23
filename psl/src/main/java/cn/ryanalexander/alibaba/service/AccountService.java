package cn.ryanalexander.alibaba.service;

import cn.ryanalexander.alibaba.domain.po.AccountPO;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ryan
* @description 针对表【teacher】的数据库操作Service
* @createDate 2022-03-22 16:11:53
*/
public interface AccountService extends IService<AccountPO> {

    void updatePwdById(String t_id, String t_pwd);

    JSONObject refreshBothToken(String tid);

    JSONObject verifyAccess(JSONObject originalResult, String id, String access);

    JSONObject getEmailById(String Tid);

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
}
