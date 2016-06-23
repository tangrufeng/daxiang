package com.dx.service.impl;

import com.dx.dao.SupplerDAO;
import com.dx.entity.SupplerBean;
import com.dx.service.SupplerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Tom on 16/6/22.
 */
@Service("supplerService")
public class SupplerServiceImpl implements SupplerService{

    @Autowired
    private SupplerDAO supplerDAO;

    @Override
    public int countAccount(String account) {
        return supplerDAO.countAccountByAdd(account);
    }

    @Override
    public int countAccount(String account, String id) {
        return supplerDAO.countAccountByUpdate(account,id);
    }

    @Override
    public int addSuppler(SupplerBean bean) {
        return supplerDAO.addSuppler(bean);
    }

    @Override
    public int editSuppler(SupplerBean bean) {
        return supplerDAO.editSuppler(bean);
    }

    @Override
    public int resetPWD(int id,String oldPassword,String newPassword) {
        return supplerDAO.resetPWD(id,oldPassword,newPassword);
    }

    @Override
    public SupplerBean login(String userName, String password) {
        return supplerDAO.login(userName,password);
    }

    @Override
    public List<Map<String, String>> getSupplerList() {
        return supplerDAO.getSupplerList();
    }
}
