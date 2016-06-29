package com.dx.service.impl;

import com.dx.dao.OrderDAO;
import com.dx.entity.OrderBean;
import com.dx.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("orderService")
public class OrderServiceImpl implements OrderService{

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
	public int updateStatus(int orderId,String openId,int status) {
		return orderDAO.updateStatus(orderId,openId,status);
	}

	@Override
	public List<Map<String, String>> getOrderByUser(String openId) {
		return orderDAO.getOrderByUser(openId);
	}

	@Override
	public List<Map<String, String>> queryOrderByPage(Map<String,String> params) {
		return orderDAO.queryOrderByPage(params);
	}

	@Override
	public int queryCount(Map<String, String> params) {
		return orderDAO.queryCount(params);
	}


}
