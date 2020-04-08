package com.system.services;

import com.system.bean.User;
import com.system.utils.BusinessExpection;

public interface UserService {

    public User register(User user) throws BusinessExpection;

    public User login(String username,String password) throws BusinessExpection;

}
