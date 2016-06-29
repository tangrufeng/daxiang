package com.dx.service;

import com.dx.entity.SupplerBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Tom on 16/6/22.
 */
public interface SupplerService {

    public int addSuppler(SupplerBean bean);

    public int editSuppler(SupplerBean bean);

    public int resetPWD(int id,String oldPassword,String newPassword);

    public List<Map<String,String>> getSupplerList();

    public SupplerBean login(String userName,String password);

    public int countAccount(String account);

    public int countAccount(String account,String id);
}
