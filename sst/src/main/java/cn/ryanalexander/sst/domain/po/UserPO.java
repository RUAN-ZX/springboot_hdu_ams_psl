package cn.ryanalexander.sst.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class UserPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer userId;

    /**
     * 
     */
    private String userAlias;

    /**
     * 
     */
    private String userPwd;

    /**
     * 
     */
    private String userName;

    /**
     * 
     */
    private String userPhone;

    /**
     * 
     */
    private Integer userRole;

    /**
     * 
     */
    private Integer userStatus;

    /**
     * 
     */
    private Object userPresentClassId;

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
        UserPO other = (UserPO) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getUserAlias() == null ? other.getUserAlias() == null : this.getUserAlias().equals(other.getUserAlias()))
            && (this.getUserPwd() == null ? other.getUserPwd() == null : this.getUserPwd().equals(other.getUserPwd()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getUserPhone() == null ? other.getUserPhone() == null : this.getUserPhone().equals(other.getUserPhone()))
            && (this.getUserRole() == null ? other.getUserRole() == null : this.getUserRole().equals(other.getUserRole()))
            && (this.getUserStatus() == null ? other.getUserStatus() == null : this.getUserStatus().equals(other.getUserStatus()))
            && (this.getUserPresentClassId() == null ? other.getUserPresentClassId() == null : this.getUserPresentClassId().equals(other.getUserPresentClassId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getUserAlias() == null) ? 0 : getUserAlias().hashCode());
        result = prime * result + ((getUserPwd() == null) ? 0 : getUserPwd().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getUserPhone() == null) ? 0 : getUserPhone().hashCode());
        result = prime * result + ((getUserRole() == null) ? 0 : getUserRole().hashCode());
        result = prime * result + ((getUserStatus() == null) ? 0 : getUserStatus().hashCode());
        result = prime * result + ((getUserPresentClassId() == null) ? 0 : getUserPresentClassId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", userAlias=").append(userAlias);
        sb.append(", userPwd=").append(userPwd);
        sb.append(", userName=").append(userName);
        sb.append(", userPhone=").append(userPhone);
        sb.append(", userRole=").append(userRole);
        sb.append(", userStatus=").append(userStatus);
        sb.append(", userPresentClassId=").append(userPresentClassId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}