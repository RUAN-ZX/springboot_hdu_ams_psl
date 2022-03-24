package cn.ryanalexander.alibaba.domain.exceptions.code;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PropertyEnum implements EnumDescribable{
    OK(0,""),
    ACCESS(1,"ACCESS令牌"),
    REFRESH(2,"REFRESH令牌"),
    CAPTCHA(3,"邮箱验证码"),
    TEXT(4,"短信验证码");

    private final int code;
    private final String description;
}
