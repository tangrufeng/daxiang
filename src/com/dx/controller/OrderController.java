package com.dx.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dx.entity.OrderBean;
import com.dx.entity.ResultBean;
import com.dx.service.OrderService;

@Controller
public class OrderController {
	
	private final static Logger logger=Logger.getLogger(OrderController.class);
	
	@Autowired
	OrderService orderService;
	
	@RequestMapping(
		    value="/wx/editOrder.do", 
		    method=RequestMethod.POST, 
		    consumes="application/json")
	public ResultBean editOrder(@RequestBody OrderBean order){
		logger.info("Get order info====>"+order);
		ResultBean rb=new ResultBean();
		if(order==null){
			rb.setErrCode(-1);
			rb.setErrMsg("订单信息为空");
		}else if(order.getId()==0){		//新增订单
			orderService.addOrder(order);
		}else{	//修改订单
			orderService.editOrder(order);
		}
		return rb;
	}
}
