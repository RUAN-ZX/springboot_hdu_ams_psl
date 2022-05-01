package cn.ryanalexander.auth.processor.annotationIntercept;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    USER(0),
    EXPIRED(1); // 所有用户只要过期了 要刷新access都这么处理 反正拿到access你再操作

    public final int id;
}
