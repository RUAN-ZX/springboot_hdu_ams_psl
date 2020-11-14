package com.ryanalexander.minipro.service;

public class verifyService {

    public static boolean iscaptcha(String str) {
        if(str.length()!=6) return false;
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
