package com.system.ui;

import com.system.bean.User;

import java.util.ResourceBundle;
import java.util.Scanner;

public abstract class BaseClass {
    protected static Scanner input=new Scanner(System.in);
    protected static User currentUser;//当作用户对象
    private static ResourceBundle r=ResourceBundle.getBundle("com/system/resource/r");
    public static String getString(String key){
        return r.getString(key);
    }
    public static void println(String s){
        System.out.println(s);
    }
 

}
