package com.system.utils;

public class EmptyUtils {

    public static boolean isEmpty(String s){
        if (s==null||"".equals(s)){
            return true;
        }else{
            return false;
        }
    }
}
