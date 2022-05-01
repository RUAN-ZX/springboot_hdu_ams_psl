package cn.ryanalexander.common.domain.exceptions.code;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum ResultEnum {
    OK(0,""),
    NOT_Found(1, "找不到"),
    INVALID(2, "错误");

    private final int code;
    private final String category;
}
