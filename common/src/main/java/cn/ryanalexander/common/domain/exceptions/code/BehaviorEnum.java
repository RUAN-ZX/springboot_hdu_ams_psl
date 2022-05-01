package cn.ryanalexander.common.domain.exceptions.code;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum BehaviorEnum implements EnumDescribable {
    OK(0, "ok"),
    REG(1, "注册"),
    LOGIN(2, "登录"),
    AUTH(3, "未授权 unauthorized"),
    REQ(4, "访问 request"), // 用户层面的访问失败
    SQL_REQ(5, "MySQL执行命令"),
    UPLOAD(6, "RPC接口请求失败"),
    WEB_SOCKET(7,"websocket 即时通讯访问异常"),

    RESOURCE(8,"资源"), // 系统资源 or 用户资源
    RPC_REQ(9,"RPC调用"),
    OP(8,"执行超时"),
    DISASTER(10, "容灾功能触发");

    private final int code;
    private final String description;
}
