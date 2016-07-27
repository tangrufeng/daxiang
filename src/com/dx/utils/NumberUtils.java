package com.dx.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tom on 16/7/3.
 */
public class NumberUtils {


    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isInteger("500"));
    }

}
