package cn.ryanalexander.alibaba.service.tool;


import java.text.DecimalFormat;

public class MathService {

    public static String rounded(String str) {
        Double d = Double.parseDouble(str);
        DecimalFormat df = new DecimalFormat("#.0");
        return df.format(d);
    }

    public static Double ratioFormatter(Double data, Double ratio, String pattern){
        Double d = data * ratio;
        DecimalFormat df = new DecimalFormat(pattern);
        return Double.valueOf(df.format(d));
    }
    public static Double formatter(Double data, String pattern){
        DecimalFormat df = new DecimalFormat(pattern);
        return Double.valueOf(df.format(data));
    }
}
