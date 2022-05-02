package cn.ryanalexander.sst.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.*;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPO implements Serializable {
    /**
     * 
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;
    /**
     * 
     */
    private String userAlias;

    /**
     * 
     */
    private String userName;

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


}