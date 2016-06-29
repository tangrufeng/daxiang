package com.dx.service.impl;

import com.dx.dao.UserDAO;
import com.dx.entity.UserBean;
import com.dx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Tom on 16/6/29.
 */
@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    UserDAO userDAO;

    @Override
    public int syncUser(UserBean userBean) {
        int rst=userDAO.update(userBean);
        if(rst==0){
            rst=userDAO.insert(userBean);
        }
        return rst;
    }
}
