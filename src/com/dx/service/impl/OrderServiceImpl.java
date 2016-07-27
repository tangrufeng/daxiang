package com.dx.service.impl;

import com.dx.dao.OrderDAO;
import com.dx.entity.OrderBean;
import com.dx.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDAO orderDAO;


    @Override
    public int addOrder(OrderBean order) {
        return orderDAO.addOrder(order);
    }

    @Override
    public int editOrder(OrderBean order) {
        return orderDAO.editOrder(order);
    }

    @Override
    public int updateStatus(int orderId, String openId, int status) {
        return orderDAO.updateStatus(orderId, openId, status);
    }

    @Override
    public List<Map<String, String>> getOrderByUser(String openId, String key) {
        return StringUtils.isEmpty(key) ? orderDAO.getOrderByUser(openId) : orderDAO.getOrderByUserSearch(openId,
                key);
    }

    @Override
    public List<Map<String, String>> queryOrderByPage(Map<String, String> params) {
        return orderDAO.queryOrderByPage(params);
    }

    @Override
    public int queryCount(Map<String, String> params) {
        return orderDAO.queryCount(params);
    }

    @Override
    public Map<String, Object> getOrderById(String id) {
        return orderDAO.getOrderById(id);
    }

    @Override
    public String isExistMobileNo(String mobileNo) {
        return orderDAO.isExistMobileNo(mobileNo);
    }
}
