package cn.ryanalexander.alibaba.service.tool;

/**
 * <p><b></b></p>
 *
 * <p>2022/3/24 </p>
 *
 * @author ryan 2022/3/24 20:34
 * @since 1.0.0
 **/
public class ExcelDataProcessUtil {
    // 多个老师的情况匹配
//    private final Character[] multiTeacherRegex = new Character[];
    private static final String[] MULTI_FLAG={"多人","/","、"};
    public static boolean multiStart(String courseTeacherName){
        // 但凡有一个符合就GG
        for(String mark : MULTI_FLAG){
            if(courseTeacherName.contains(mark)) return true;
        }
        return false;
    }

    public static double getCapacityFactorByProperty(String properties, Integer capacity){
        if(properties.equals("A") && capacity > 20) return capacity / 20.0;
        else if(properties.equals("I")) return Math.min(1 + (capacity - 80) / 200.0, 1.2);
        else if(properties.equals(("J"))) return capacity <= 30 ? 1.2 : 1.2 * capacity / 30.0 ;
        else if(properties.equals("B") && capacity > 40) return capacity / 40.0;
        else return 1.0;
    }
}
