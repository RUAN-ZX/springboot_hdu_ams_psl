package cn.ryanalexander.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p><b>整个微服务体系 存在的所有APP应用 目前存在：</b></p>
 * 0 为HDU查教务 | 1为 组队啦 | 2为 SST
 * <p>2022-05-01 </p>

 * @since
 * @author RyanAlexander 2022-05-01 18:58
 */
@Getter
@AllArgsConstructor
public enum AppKeyEnum {
    PSL(0),
    TEAM(1),
    SST(2);

    public final int key;
}
