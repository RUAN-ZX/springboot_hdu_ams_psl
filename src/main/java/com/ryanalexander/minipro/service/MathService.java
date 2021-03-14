package com.ryanalexander.minipro.service;

import java.text.DecimalFormat;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

public class MathService {

    public static String rounded(String str) {
        Double d = Double.parseDouble(str);
        DecimalFormat df = new DecimalFormat("#.0");
        return df.format(d);
    }
    public static String md5Encryption(String name, String password){
        String algorithmName= Md5Hash.ALGORITHM_NAME;
        int hashIterations=20;
        SimpleHash simpleHash=new SimpleHash(algorithmName, password, name,hashIterations);
        return simpleHash.toString();
    }

}
