package cn.ryanalexander.auth.domain.po;

import cn.ryanalexander.common.domain.dto.Account;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName account
 */
@TableName(value ="account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer accountId;

    /**
     * 
     */
    private String accountMail;

    /**
     * 
     */
    private String accountPwd;

    /**
     * 
     */
    private String accountName;

    /**
     * 
     */
    private String accountPhone;

    /**
     * 
     */
    private Integer accountStatus;

    /**
     * 
     */
    private Integer accountUserId;

    /**
     * 
     */
    private Integer accountApp;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public AccountPO(Account account){
        this.accountMail = account.getAccountMail();
        this.accountName = account.getAccountName();
        this.accountPwd = account.getAccountPwd();
        this.accountPhone = account.getAccountPhone();

        this.accountApp = account.getAccountApp();
        this.accountUserId = account.getAccountUserId();
        this.accountStatus = account.getAccountStatus();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AccountPO other = (AccountPO) that;
        return (this.getAccountId() == null ? other.getAccountId() == null : this.getAccountId().equals(other.getAccountId()))
            && (this.getAccountMail() == null ? other.getAccountMail() == null : this.getAccountMail().equals(other.getAccountMail()))
            && (this.getAccountPwd() == null ? other.getAccountPwd() == null : this.getAccountPwd().equals(other.getAccountPwd()))
            && (this.getAccountName() == null ? other.getAccountName() == null : this.getAccountName().equals(other.getAccountName()))
            && (this.getAccountPhone() == null ? other.getAccountPhone() == null : this.getAccountPhone().equals(other.getAccountPhone()))
            && (this.getAccountStatus() == null ? other.getAccountStatus() == null : this.getAccountStatus().equals(other.getAccountStatus()))
            && (this.getAccountUserId() == null ? other.getAccountUserId() == null : this.getAccountUserId().equals(other.getAccountUserId()))
            && (this.getAccountApp() == null ? other.getAccountApp() == null : this.getAccountApp().equals(other.getAccountApp()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAccountId() == null) ? 0 : getAccountId().hashCode());
        result = prime * result + ((getAccountMail() == null) ? 0 : getAccountMail().hashCode());
        result = prime * result + ((getAccountPwd() == null) ? 0 : getAccountPwd().hashCode());
        result = prime * result + ((getAccountName() == null) ? 0 : getAccountName().hashCode());
        result = prime * result + ((getAccountPhone() == null) ? 0 : getAccountPhone().hashCode());
        result = prime * result + ((getAccountStatus() == null) ? 0 : getAccountStatus().hashCode());
        result = prime * result + ((getAccountUserId() == null) ? 0 : getAccountUserId().hashCode());
        result = prime * result + ((getAccountApp() == null) ? 0 : getAccountApp().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", accountId=").append(accountId);
        sb.append(", accountMail=").append(accountMail);
        sb.append(", accountPwd=").append(accountPwd);
        sb.append(", accountName=").append(accountName);
        sb.append(", accountPhone=").append(accountPhone);
        sb.append(", accountStatus=").append(accountStatus);
        sb.append(", accountUserId=").append(accountUserId);
        sb.append(", accountApp=").append(accountApp);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}