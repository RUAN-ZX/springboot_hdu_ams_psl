package cn.ryanalexander.psl.processor.annotationIntercept;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    ROOT(0, "先生"),
    MANAGER(1, "老师"),
    TEACHER(2, "老师"),
    EXPIRED(3, ""); // 所有用户只要过期了 要刷新access都这么处理 反正拿到access你再操作


    public final int id;
    public final String name;
}
