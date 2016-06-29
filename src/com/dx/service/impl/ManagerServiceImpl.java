package com.dx.service.impl;

import com.dx.dao.ManagerDAO;
import com.dx.entity.ManagerBean;
import com.dx.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Tom on 16/6/24.
 */
@Service("managerService")
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    ManagerDAO managerDAO;
    @Override
    public ManagerBean login(String userName, String password) {
        return managerDAO.login(userName,password);
    }
}

