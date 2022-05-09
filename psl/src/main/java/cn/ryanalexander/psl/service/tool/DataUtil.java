package cn.ryanalexander.psl.service.tool;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.message.ReusableMessage;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p><b></b></p>
 *
 * <p>2022/3/24 </p>
 *
 * @author ryan 2022/3/24 20:34
 * @since 1.0.0
 **/
public class DataUtil {
    public final static String CH_REGEX = "([\u4e00-\u9fa5]+)";

    public static String getChineseCharacter(String raw){
        Matcher matcher = Pattern.compile(DataUtil.CH_REGEX).matcher(raw);
        if(matcher.find()) return matcher.group(1);
        else return null;
    }


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
    static {
        ThesisDesignGradeMap.put("优秀", 0);
        ThesisDesignGradeMap.put("良好", 1);
        ThesisDesignGradeMap.put("中等", 2);
        ThesisDesignGradeMap.put("及格", 3);
        ThesisDesignGradeMap.put("不及格", 4);
    }
    private static final HashMap<String, Integer> TitleGradeMap = new HashMap<>();
    static {
        TitleGradeMap.put("正高", 0);
        TitleGradeMap.put("副高", 1);
        TitleGradeMap.put("中级", 2);
        TitleGradeMap.put("初级", 3);
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


    public static double string2double(String doubleNum){
        return string2double(doubleNum, 0.0);
    }
    public static double string2double(String doubleNum, double defaultDouble){
        if(doubleNum == null || doubleNum.equals("null") || doubleNum.equals("#N/A"))
            return defaultDouble;

        double result = defaultDouble;
        try{
            result = Double.parseDouble(doubleNum);
            return result;
        }catch (Exception e){
            return result;
        }
    }
    public static int string2integer(String integerNum){
        return string2integer(integerNum, 0);
    }
    public static int string2integer(String integerNum, int defaultInt){
        if(integerNum == null || integerNum.equals("null") || integerNum.equals("#N/A"))
            return defaultInt;
        int result = defaultInt;
        try{
            result = Integer.parseInt(integerNum);
            return result;
        }catch (Exception e){
            return result;
        }
    }

    public static double getAvg(double data1, double data2){
        boolean data1Absence = data1 > 0.00001;
        boolean data2Absence = data2 > 0.00001;
        if(data1Absence && data2Absence) return (data1 + data2) / 2;
        else if(data1Absence) return data1;
        else if(data2Absence) return data2;
        else return 0.0;
    }
}
