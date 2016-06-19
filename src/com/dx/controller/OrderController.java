package com.dx.controller;

import com.dx.entity.ResultListBean;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.DateTimeDateFormat;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.dx.entity.OrderBean;
import com.dx.entity.ResultBean;
import com.dx.service.OrderService;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class OrderController extends BaseController {

    private final static Logger logger = Logger.getLogger(OrderController.class);
    private final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");

    @Autowired
    OrderService orderService;

    @RequestMapping(
            value = "/wx/editOrder.do",
            method = RequestMethod.POST,
            consumes = "application/json")
    @ResponseBody
    public ResultBean editOrder(@RequestBody OrderBean order) {
        logger.info("Get order info====>" + order);
        ResultBean rb = new ResultBean();
        if (order == null) {
            rb.setErrCode(-1);
            rb.setErrMsg("订单信息为空");
        } else if (order.getId() == 0) {        //新增订单
            orderService.addOrder(order);
        } else {    //修改订单
            orderService.editOrder(order);
        }
        return rb;
    }

    @RequestMapping("/wx/getMyOrderList")
    @ResponseBody
    public ResultListBean getUserByOrder(@RequestParam String openId) {
        ResultListBean rst = new ResultListBean();
        List<Map<String, String>> list = orderService.getOrderByUser(openId);
        rst.getList().addAll(list);
        rst.setCnt(list.size());
        return rst;
    }

    @RequestMapping("/wx/cancelOrder")
    @ResponseBody
    public ResultBean updateOrderStatus(@RequestParam int orderId, @RequestParam String openId) {
        ResultBean rst = new ResultBean();
        rst.setErrCode(orderService.updateStatus(orderId, openId, 4));
        return rst;
    }


    @RequestMapping("/wx/getOrderDate")
    @ResponseBody
    public ResultListBean getOrderDate() {
        ResultListBean rst = new ResultListBean();
        List<String> list = new ArrayList<String>();
        Date date = new Date();
        for (int i = 1; i < 6; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, i);
            list.add(sdf.format(calendar.getTime()));
        }
        rst.getList().addAll(list);
        rst.setCnt(list.size());
        return rst;
    }





}
