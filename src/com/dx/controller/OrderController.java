package com.dx.controller;

import com.dx.entity.ResultListBean;
import org.apache.commons.collections4.MapUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.DateTimeDateFormat;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.dx.entity.OrderBean;
import com.dx.entity.ResultBean;
import com.dx.service.OrderService;

import javax.servlet.http.HttpServletRequest;
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
    public ResultBean editOrder(@RequestBody OrderBean order, HttpServletRequest request) {
        logger.info("Get order info====>" + order);
        Object openId = request.getSession().getAttribute("session_openid");
        if (openId == null) {
            throw new RuntimeException("The openId is null and the order is ===>" + order);
        }
        ResultBean rb = new ResultBean();
        order.setOpenId(String.valueOf(openId));
        if (order == null) {
            rb.setErrCode(-1);
            rb.setErrMsg("订单信息为空");
        } else if (StringUtils.isEmpty(order.getId())) {        //新增订单
            orderService.addOrder(order);
        } else {    //修改订单
            orderService.editOrder(order);
        }
        return rb;
    }

    @RequestMapping("/wx/getMyOrderList")
    @ResponseBody
    public ResultListBean getUserByOrder(HttpServletRequest request) {
        Object openId = request.getSession().getAttribute("session_openid");
        if (openId == null) {
            throw new RuntimeException("The openId is null ");
        }
        ResultListBean rst = new ResultListBean();
        List<Map<String, String>> list = orderService.getOrderByUser(String.valueOf(openId));
        rst.getList().addAll(list);
        rst.setCnt(list.size());
        return rst;
    }


//    @RequestMapping("/sp/queryOrderByPage")
//    @ResponseBody
//    public ResultListBean queryOrderByPage(@RequestBody OrderBean order,@RequestParam int page,@RequestParam int pageSize) {
//        ResultListBean rst = new ResultListBean();
//        List<Map<String, String>> list = orderService.queryOrderByPage(order,page,pageSize);
//        rst.getList().addAll(list);
//        rst.setCnt(list.size());
//        return rst;
//    }


    @RequestMapping("/sp/queryOrderByPage")
    @ResponseBody
    public ResultListBean queryOrderByPage(HttpServletRequest request) {

        Enumeration<String> paramNames = request.getParameterNames();
        Map<String, String> params = new HashMap<String, String>();
        while (paramNames.hasMoreElements()) {
            String pName = paramNames.nextElement();
            String pValue = request.getParameter(pName);
            params.put(pName, pValue);
        }
        ResultListBean rst = new ResultListBean();
        List<Map<String, String>> list = orderService.queryOrderByPage(params);
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


    @RequestMapping("/sp/reviewedOrder")
    @ResponseBody
    public ResultBean reviewedOrder(@RequestParam int orderId, @RequestParam String openId, @RequestParam int status) {
        ResultBean rst = new ResultBean();
        orderService.updateStatus(orderId, openId, status);
        return rst;
    }


}
