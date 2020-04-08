package com.system.ui;

import com.system.bean.User;
import com.system.services.Impl.UserServiceImpl;
import com.system.services.UserService;
import com.system.utils.BusinessExpection;
import com.system.utils.UserIO;

public class RegisterClass extends BaseClass {

    public boolean register() throws BusinessExpection {
        UserIO userIO = new UserIO();
        boolean flag = true;
        while (flag) {
            println(getString("input.username"));
            String username = input.nextLine();
            println(getString("input.password"));
            String password = input.nextLine();
            if ("".equals(username) && "".equals(password)) {
                println(getString("username.notnull"));
                println(getString("password.notnull"));
            } else if ("".equals(username)) {
                println(getString("username.notnull"));
            } else if ("".equals(password)) {
                println(getString("password.notnull"));
            } else {
                if (userIO.findByUsernameAndPassword(username, password) != null) {
                    println(getString("reg.repeat"));
                    return false;
                }
                User user = new User(username, password);
                UserService userService = new UserServiceImpl();
                userService.register(user);
                flag = false;
            }
        }
        return true;

    }
}
