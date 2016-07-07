package com.dx.controller;

import com.dx.common.Common;
import com.dx.entity.*;
import com.dx.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
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
        Object openId = request.getSession().getAttribute(Common.SESSION_OPENID);
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
        HttpSession session = request.getSession();
        Object openId = session.getAttribute(Common.SESSION_OPENID);
        if (openId == null) {
            throw new RuntimeException("The openId is null ");
        }
        ResultListBean rst = new ResultListBean();
        List<Map<String, String>> list = orderService.getOrderByUser(String.valueOf(openId));
        rst.getList().addAll(list);
        rst.setCnt(list.size());
        return rst;
    }


    @RequestMapping(value = {"/mp/queryOrderByPage"})
    @ResponseBody
    public PageResultListBean queryOrderByMP(HttpServletRequest request) {

        ManagerBean bean = (ManagerBean) request.getSession().getAttribute(Common.MANAGER_SESSIOIN_BEAN);
        if (bean == null) {
            PageResultListBean rst = new PageResultListBean();
            rst.setErrCode(Common.ERR_CODE_NOLOGIN_MP);
            rst.setErrMsg(Common.ERR_MSG_NOLOGIN);
            return rst;
        }
        return queryOrderByPage(request,null);
    }

    @RequestMapping(value = {"/sp/queryOrderByPage"})
    @ResponseBody
    public PageResultListBean queryOrderBySP(HttpServletRequest request) {

        SupplerBean bean = (SupplerBean) request.getSession().getAttribute(Common.SUPPLER_SESSIOIN_BEAN);
        if (bean == null) {
            PageResultListBean rst = new PageResultListBean();
            rst.setErrCode(Common.ERR_CODE_NOLOGIN_SP);
            rst.setErrMsg(Common.ERR_MSG_NOLOGIN);
            return rst;
        }

        return queryOrderByPage(request,bean.getId());
    }

    private PageResultListBean queryOrderByPage(HttpServletRequest request,String spId) {
        Enumeration<String> paramNames = request.getParameterNames();
        Map<String, String> params = new HashMap<String, String>();
        while (paramNames.hasMoreElements()) {
            String pName = paramNames.nextElement();
            String pValue = request.getParameter(pName);
            params.put(pName, pValue);
        }
        if(!StringUtils.isEmpty(spId)){
            params.put("supplierId",spId);
        }
        PageResultListBean rst = new PageResultListBean();
        List<Map<String, String>> list = orderService.queryOrderByPage(params);
        int count = orderService.queryCount(params);
        int page = 1;
        int pageSize = 10;
        try {
            page = Integer.parseInt(String.valueOf(params.get("page")));
            pageSize = Integer.parseInt(String.valueOf(params.get("pageSize")));
        } catch (Exception e) {

        }
        rst.setCount(count);
        rst.setPageCount(new BigDecimal((float) count / pageSize).setScale(0, BigDecimal.ROUND_UP).intValue());
        rst.getList().addAll(list);
        rst.setCnt(list.size());
        return rst;
    }


    @RequestMapping("/wx/cancelOrder")
    @ResponseBody
    public ResultBean updateOrderStatus(@RequestParam int orderId,HttpServletRequest request) {
        Object openId = request.getSession().getAttribute(Common.SESSION_OPENID);
        if (openId == null) {
            throw new RuntimeException("The openId is null and the order is ===>" + orderId);
        }
        ResultBean rst = new ResultBean();
        rst.setErrCode(orderService.updateStatus(orderId, String.valueOf(openId), 4));
        return rst;
    }


    @RequestMapping("/wx/getOrderDate")
    @ResponseBody
    public ResultListBean getOrderDate() {
        ResultListBean rst = new ResultListBean();
        List<String> list = new ArrayList<String>();
        Date date = new Date();
        for (int i = 1; i < 14; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, i+1);
            list.add(sdf.format(calendar.getTime()));
        }
        rst.getList().addAll(list);
        rst.setCnt(list.size());
        return rst;
    }


    @RequestMapping("/sp/reviewedOrder")
    @ResponseBody
    public ResultBean reviewedOrder(@RequestParam int orderId, @RequestParam String openId, @RequestParam int status, HttpServletRequest request) {
        ResultBean rst = new ResultBean();
        SupplerBean bean = (SupplerBean) request.getSession().getAttribute(Common.SUPPLER_SESSIOIN_BEAN);
        if (bean == null) {
            rst.setErrCode(Common.ERR_CODE_NOLOGIN_SP);
            rst.setErrMsg(Common.ERR_MSG_NOLOGIN);
        }
        orderService.updateStatus(orderId, openId, status);
        return rst;
    }


}
