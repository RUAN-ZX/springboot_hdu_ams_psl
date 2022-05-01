package cn.ryanalexander.psl.domain.exceptions.code;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum SubjectEnum implements EnumDescribable{
    OK(0, "什么错误都没有"),
    USER(1, "用户"),
    INTERNAL(2, "系统内部"),
    THIRD_PARTY(3, "第三方"),
    FAIL(4, "待定的错误");

    private final int code;
    private final String description;
}
