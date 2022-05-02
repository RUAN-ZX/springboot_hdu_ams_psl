package cn.ryanalexander.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum AccountStatusEnum {
    NORMAL(0),
    FREEZING(1),
    CANCELLATION(2);

    public final int key;
}
