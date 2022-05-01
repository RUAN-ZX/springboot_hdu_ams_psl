package cn.ryanalexander.auth.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName account
 */
@TableName(value ="account")
@Data
public class AccountPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer accountId;

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
    private String accountMail;

    /**
     * 
     */
    private Integer accountStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
            && (this.getAccountPwd() == null ? other.getAccountPwd() == null : this.getAccountPwd().equals(other.getAccountPwd()))
            && (this.getAccountName() == null ? other.getAccountName() == null : this.getAccountName().equals(other.getAccountName()))
            && (this.getAccountMail() == null ? other.getAccountMail() == null : this.getAccountMail().equals(other.getAccountMail()))
            && (this.getAccountStatus() == null ? other.getAccountStatus() == null : this.getAccountStatus().equals(other.getAccountStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAccountId() == null) ? 0 : getAccountId().hashCode());
        result = prime * result + ((getAccountPwd() == null) ? 0 : getAccountPwd().hashCode());
        result = prime * result + ((getAccountName() == null) ? 0 : getAccountName().hashCode());
        result = prime * result + ((getAccountMail() == null) ? 0 : getAccountMail().hashCode());
        result = prime * result + ((getAccountStatus() == null) ? 0 : getAccountStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", accountId=").append(accountId);
        sb.append(", accountPwd=").append(accountPwd);
        sb.append(", accountName=").append(accountName);
        sb.append(", accountMail=").append(accountMail);
        sb.append(", accountStatus=").append(accountStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}