package com.dx.service;

import org.apache.ibatis.annotations.Insert;

import com.dx.entity.OrderBean;

public interface OrderService {

	int addOrder(OrderBean order);

	int editOrder(OrderBean order);

	int updateStatus();

}
