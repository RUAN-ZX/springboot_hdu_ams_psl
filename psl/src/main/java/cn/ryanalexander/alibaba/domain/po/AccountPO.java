package cn.ryanalexander.alibaba.domain.po;

import cn.ryanalexander.alibaba.domain.bo.excel.TitleInfo;
import cn.ryanalexander.alibaba.service.tool.DataUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

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
    @TableId
    private Integer accountId;

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
    private String accountPhone;

    /**
     * 
     */
    private String accountPwd;

    public AccountPO(TitleInfo titleInfo){
        accountId = DataUtil.string2integer(titleInfo.getTeacherId());
        accountName = titleInfo.getTeacherName();

    }
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
            && (this.getAccountName() == null ? other.getAccountName() == null : this.getAccountName().equals(other.getAccountName()))
            && (this.getAccountMail() == null ? other.getAccountMail() == null : this.getAccountMail().equals(other.getAccountMail()))
            && (this.getAccountPhone() == null ? other.getAccountPhone() == null : this.getAccountPhone().equals(other.getAccountPhone()))
            && (this.getAccountPwd() == null ? other.getAccountPwd() == null : this.getAccountPwd().equals(other.getAccountPwd()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAccountId() == null) ? 0 : getAccountId().hashCode());
        result = prime * result + ((getAccountName() == null) ? 0 : getAccountName().hashCode());
        result = prime * result + ((getAccountMail() == null) ? 0 : getAccountMail().hashCode());
        result = prime * result + ((getAccountPhone() == null) ? 0 : getAccountPhone().hashCode());
        result = prime * result + ((getAccountPwd() == null) ? 0 : getAccountPwd().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", accountId=").append(accountId);
        sb.append(", accountName=").append(accountName);
        sb.append(", accountMail=").append(accountMail);
        sb.append(", accountPhone=").append(accountPhone);
        sb.append(", accountPwd=").append(accountPwd);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}