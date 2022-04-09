package cn.ryanalexander.alibaba.service.tool;

import io.swagger.models.auth.In;

import java.util.HashMap;

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
        else if(properties.equals("I") && capacity > 80) return Math.min(1 + (capacity - 80) / 200.0, 1.2);
        else if(properties.equals(("J"))) return capacity <= 30 ? 1.2 : 1.2 * capacity / 30.0 ;
        else if(properties.equals("B") && capacity > 40) return capacity / 40.0;
        else return 1.0;
    }

    public static double getRatio(String ratio){
        return Double.parseDouble(ratio.split("%")[0]) / 100.0;
    }

    private static HashMap<String, Integer> gradeMap = new HashMap<>();
    {
        gradeMap.put("优秀", 0);
        gradeMap.put("良好", 1);
        gradeMap.put("中等", 2);
        gradeMap.put("及格", 3);
        gradeMap.put("不及格", 4);
    }

    public static Integer transformGrade(String grade){
        return gradeMap.getOrDefault(grade, 1);
    }
}
