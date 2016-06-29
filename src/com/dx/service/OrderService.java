package com.dx.service;

import com.dx.entity.OrderBean;

import java.util.List;
import java.util.Map;

public interface OrderService {

	int addOrder(OrderBean order);

	int editOrder(OrderBean order);

	int updateStatus(int orderId,String openId,int status);

	List<Map<String,String>> getOrderByUser(String openId);

	List<Map<String,String>> queryOrderByPage(Map<String,String> params);

	int queryCount(Map<String,String> params);
}
