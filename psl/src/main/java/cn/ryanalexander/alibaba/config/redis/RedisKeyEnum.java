package cn.ryanalexander.alibaba.config.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p><b>一些Key的对应名称</b></p>
 * 比如redis的Key名 返回json字符串对应的key名称等等
 * <p>2022/3/23 </p>
 *
 * @author ryan 2022/3/23 21:30
 * @since 1.0.0
 **/

@Getter
@AllArgsConstructor
public enum RedisKeyEnum {
    ACCESS("access"),
    REFRESH("refresh"),
    CAPTCHA("captcha"),
    ACCOUNT("account");

    public final String key;
}
