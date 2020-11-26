package com.ryanalexander.minipro.service;

import java.text.DecimalFormat;

public class MathService {

    public static String rounded(String str) {
        Double d = Double.parseDouble(str);
        DecimalFormat df = new DecimalFormat("#.0");
        return df.format(d);
    }
}
