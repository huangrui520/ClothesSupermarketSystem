package com.system.services.Impl;

import com.system.bean.User;
import com.system.services.UserService;
import com.system.utils.BusinessExpection;
import com.system.utils.EmptyUtils;
import com.system.utils.UserIO;

public class UserServiceImpl implements UserService {

    @Override
    public User register(User user) throws BusinessExpection {
        UserIO userIO = new UserIO();
        userIO.add(user);
        userIO.writeUsers();
        return user;
    }

    @Override
    public User login(String username,String password) throws BusinessExpection {
        if (EmptyUtils.isEmpty(username)){
            throw new BusinessExpection("username.notnull");
        }
        if (EmptyUtils.isEmpty(password)){
            throw new BusinessExpection("password.notnull");
        }

        UserIO userIO=new UserIO();
        User user=userIO.findByUsernameAndPassword(username,password);

        return user;
    }
}
