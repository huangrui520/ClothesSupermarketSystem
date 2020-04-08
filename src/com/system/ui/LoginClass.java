package com.system.ui;

import com.system.bean.User;
import com.system.services.Impl.UserServiceImpl;
import com.system.services.UserService;
import com.system.utils.BusinessExpection;

public class LoginClass extends BaseClass {

    public void login(){
        println(getString("input.username"));
        String username=input.nextLine();
        println(getString("input.password"));
        String password=input.nextLine();
        UserService userService=new UserServiceImpl();
        User user=userService.login(username,password);

        if (user!=null){
            currentUser=user;
        }else{
            println(getString("input.error"));
            throw new BusinessExpection("login.error");
        }
    }
}
