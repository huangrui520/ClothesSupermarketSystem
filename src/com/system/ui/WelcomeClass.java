package com.system.ui;

import com.system.utils.BusinessExpection;
import com.system.utils.UserIO;

public class WelcomeClass extends BaseClass {

    public void start() {
        println(getString("info.welcome"));
        UserIO userIO = new UserIO();
        userIO.readUsers();
        boolean flag = true;
        while (flag) {
            println(getString("info.login.reg"));
            println(getString("info.select"));
            String select = input.nextLine();
            switch (select) {
                case "1":
                    flag = false;
                    try {
                        new LoginClass().login();
                        flag = false;
                        println(getString("login.success"));
                        println("welcome, "+currentUser.getUsername());
                        new HomeClass().show();
                    } catch (BusinessExpection e) {
                        println(getString(e.getMessage()));
                    }
                    break;
                case "2":
                    try {
                        if (new RegisterClass().register()) {
                            println("reg.success");
                            flag = false;
                            new HomeClass().show();
                        } else {
                            flag = true;
                        }
                    } catch (BusinessExpection e) {
                        println(getString("reg.error"));
                    }
                    break;
                default:
                    println(getString("info.error"));
                    break;
            }
        }

    }
}
