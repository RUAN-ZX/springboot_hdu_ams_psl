package cn.ryanalexander.alibaba.service.tool;

import java.nio.channels.MulticastChannel;

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

    public static boolean batchStart(String courseTeacherName){
        boolean contains1 = courseTeacherName.contains("（多人）");
        boolean contains2 = courseTeacherName.contains("/");
        // 两者有一个符合就炸
        return contains1 || contains2;
    }
}
