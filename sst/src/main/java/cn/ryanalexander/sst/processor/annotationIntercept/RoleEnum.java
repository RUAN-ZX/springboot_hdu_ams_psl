package cn.ryanalexander.sst.processor.annotationIntercept;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    STUDENT(0, "同学"),
    TEACHER(1, "老师"),
    ROOT(2, "先生"),
    EXPIRED(3, ""); // 所有用户只要过期了 要刷新access都这么处理 反正拿到access你再操作


    public final int id;
    public final String name;
}
