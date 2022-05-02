package cn.ryanalexander.common.domain.dto;

import java.io.Serializable;

import cn.ryanalexander.common.enums.AccountStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {

    private String accountMail;
    private String accountPwd;
    private String accountName;
    private String accountPhone;

    // 默认
    private int accountStatus = AccountStatusEnum.NORMAL.key;

    private int accountUserId;
    private int accountApp;

}