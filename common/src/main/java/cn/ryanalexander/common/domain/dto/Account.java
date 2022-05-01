package cn.ryanalexander.common.domain.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName account
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {

    private String accountMail;
    private String accountPwd;
    private String accountName;
    private String accountPhone;
    private Integer accountStatus;

    private Integer accountUserId;
    private Integer accountApp;

}