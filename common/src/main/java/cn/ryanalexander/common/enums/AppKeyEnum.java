package cn.ryanalexander.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * <p><b>整个微服务体系 存在的所有APP应用 目前存在：</b></p>
 * 0 为HDU查教务 | 1为 组队啦 | 2为 SST
 * <p>2022-05-01 </p>

 * @since
 * @author RyanAlexander 2022-05-01 18:58
 */
@Getter
@AllArgsConstructor
public enum AppKeyEnum{
    PSL(0,"教务查系统"),
    TEAM(1,"组队啦"),
    SST(2,"学习系统");

    public final int key;
    public final String name;

    private static final Map<Integer, AppKeyEnum> SEARCH_MAP = new HashMap<>();
    static {
        for(AppKeyEnum appKeyEnum : AppKeyEnum.values()){
            SEARCH_MAP.put(appKeyEnum.getKey(), appKeyEnum);
        }
    }
    public static AppKeyEnum getByKey(int key) {
        return SEARCH_MAP.get(key);
    }
}
