package com.dx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dx.dao.OrderDAO;
import com.dx.entity.OrderBean;
import com.dx.service.OrderService;

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
	public int updateStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
