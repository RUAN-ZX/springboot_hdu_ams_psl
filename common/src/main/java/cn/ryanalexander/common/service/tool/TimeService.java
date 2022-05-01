package cn.ryanalexander.common.service.tool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author ryan
 * @description TODO
 * @date 2022/3/22 21:33
 * @Version 1.0.0-Beta
 */
public class TimeService {
    private final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public long getTimeStamp(){
        return calendar.getTimeInMillis();
    }
    public String getTime(){
        return simpleDateFormat.format(calendar.getTime()) + ", GMT+08:00";
    }
}
