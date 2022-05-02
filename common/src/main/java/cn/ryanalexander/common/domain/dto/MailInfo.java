package cn.ryanalexander.common.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p><b>keyName mailTo注意auth需要加工 其他的固定！</b></p>
 *
 * <p>2022/5/2 </p>
 *
 * @author ryan 2022/5/2 15:41
 * @since 1.0.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailInfo {
    String keyName;
    String mailTo;

    int accountApp;
    String callName;
    String roleName;

    int captchaExpire;
    String mailHtmlUrl;
}
