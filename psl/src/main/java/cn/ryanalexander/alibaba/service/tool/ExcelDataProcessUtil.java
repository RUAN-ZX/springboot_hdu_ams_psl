package cn.ryanalexander.alibaba.service.tool;

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

    private static final HashMap<String, Integer> ThesisDesignGradeMap = new HashMap<>();
    {
        ThesisDesignGradeMap.put("优秀", 0);
        ThesisDesignGradeMap.put("良好", 1);
        ThesisDesignGradeMap.put("中等", 2);
        ThesisDesignGradeMap.put("及格", 3);
        ThesisDesignGradeMap.put("不及格", 4);
    }
    private static final HashMap<String, Integer> TitleGradeMap = new HashMap<>();
    {
        TitleGradeMap.put("优秀", 0);
        TitleGradeMap.put("良好", 1);
        TitleGradeMap.put("中等", 2);
        TitleGradeMap.put("及格", 3);
        TitleGradeMap.put("不及格", 4);
    }
    public static int transformThesisDesignGrade(String grade){
        return ThesisDesignGradeMap.getOrDefault(grade, 1);
    }

    public static int transformAchievementType(String type){
        if(type.contains("非")) return 1;
        else return 0;
    }

    public static String getTermFromHead(String head){
        return head.substring(0,12);
    }

    public static int transformTitleGrade(String grade){
        return TitleGradeMap.getOrDefault(grade, 1);
    }

}
