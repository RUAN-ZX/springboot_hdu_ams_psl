package cn.ryanalexander.alibaba.pojo;

import cn.ryanalexander.alibaba.enums.SexEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.junit.Test;

import java.util.List;

/**
 * Date:2022/2/12
 * Author:ybc
 * Description:
 */
@Data
//设置实体类所对应的表名
//@TableName("t_user")
public class User {

    //将属性所对应的字段指定为主键
    //@TableId注解的value属性用于指定主键的字段
    //@TableId注解的type属性设置主键生成策略
    //@TableId(value = "uid", type = IdType.AUTO)
    @TableId("t_id")
    private Long tId;

    //指定属性所对应的字段名
    @TableField("user_name")
    private String name; // userName

    private Integer age;

    private String email;

    private SexEnum sex;

    @TableLogic
    private Integer isDeleted;

}



