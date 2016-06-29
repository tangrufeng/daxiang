package com.dx.controller;

import com.dx.common.Common;
import com.dx.entity.ManagerBean;
import com.dx.entity.ResultBean;
import com.dx.entity.ResultListBean;
import com.dx.entity.SupplerBean;
import com.dx.service.SupplerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by Tom on 16/6/22.
 */
@Controller
public class SupplerController {

    @Autowired
    SupplerService supplerService;

    @RequestMapping(
            value = "/mp/addSuppler",
            method = RequestMethod.POST,
            consumes = "application/json")
    @ResponseBody
    public ResultBean addSuppler(@RequestBody SupplerBean bean,HttpServletRequest request) {
        ResultBean rb = new ResultBean();
        ManagerBean managerBean = (ManagerBean) request.getSession().getAttribute(Common.MANAGER_SESSIOIN_BEAN);
        if (managerBean == null) {
            rb.setErrCode(Common.ERR_CODE_NOLOGIN_MP);
            rb.setErrMsg(Common.ERR_MSG_NOLOGIN);
            return rb;
        }
        if (StringUtils.isEmpty(bean.getName())) {
            rb.setErrCode(1);
            rb.setErrMsg("供应商名称不能为空");
            return rb;
        }
        if (StringUtils.isEmpty(bean.getFullname())) {
            rb.setErrCode(1);
            rb.setErrMsg("供应商全称不能为空");
            return rb;
        }
        if (StringUtils.isEmpty(bean.getAccount())) {
            rb.setErrCode(1);
            rb.setErrMsg("供应商账号不能为空");
            return rb;
        }

        if (supplerService.countAccount(bean.getAccount()) > 0) {
            rb.setErrCode(1);
            rb.setErrMsg("供应商账号不能重复");
            return rb;
        }


        if (StringUtils.isEmpty(bean.getPassword())) {
            rb.setErrCode(1);
            rb.setErrMsg("供应商密码不能为空");
            return rb;
        }
        bean.setStatus("1");

        int i = supplerService.addSuppler(bean);
        return rb;
    }

    @RequestMapping("/mp/editSuppler")
    @ResponseBody
    public ResultBean editSuppler(@RequestBody SupplerBean bean,HttpServletRequest request) {
        ResultBean rb = new ResultBean();
        ManagerBean managerBean = (ManagerBean) request.getSession().getAttribute(Common.MANAGER_SESSIOIN_BEAN);
        if (managerBean == null) {
            rb.setErrCode(Common.ERR_CODE_NOLOGIN_MP);
            rb.setErrMsg(Common.ERR_MSG_NOLOGIN);
            return rb;
        }
        if (StringUtils.isEmpty(bean.getName())) {
            rb.setErrCode(1);
            rb.setErrMsg("供应商名称不能为空");
            return rb;
        }
        if (StringUtils.isEmpty(bean.getFullname())) {
            rb.setErrCode(1);
            rb.setErrMsg("供应商全称不能为空");
            return rb;
        }
        if (StringUtils.isEmpty(bean.getAccount())) {
            rb.setErrCode(1);
            rb.setErrMsg("供应商账号不能为空");
            return rb;
        }

        if (supplerService.countAccount(bean.getAccount(), bean.getId()) > 0) {
            rb.setErrCode(1);
            rb.setErrMsg("供应商账号不能重复");
            return rb;
        }

        int i = supplerService.editSuppler(bean);
        return rb;
    }


    @RequestMapping("/sp/resetPWD")
    @ResponseBody
    public ResultBean resetPWD(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, HttpServletRequest request) {
        ResultBean rb = new ResultBean();
        SupplerBean bean = (SupplerBean) request.getSession().getAttribute(Common.SUPPLER_SESSIOIN_BEAN);
        if (bean == null) {
            rb.setErrCode(Common.ERR_CODE_NOLOGIN_MP);
            rb.setErrMsg(Common.ERR_MSG_NOLOGIN);
            return rb;
        }
        int i = supplerService.resetPWD(Integer.parseInt(bean.getId()), oldPassword, newPassword);
        if (i == 0) {
            rb.setErrCode(1);
            rb.setErrMsg("密码重置失败");
        }
        return rb;
    }


    @RequestMapping("/mp/getSupplerList")
    @ResponseBody
    public ResultListBean getSupplerList(HttpServletRequest request) {
        ResultListBean rstBean = new ResultListBean();
        ManagerBean bean = (ManagerBean) request.getSession().getAttribute(Common.MANAGER_SESSIOIN_BEAN);
        if (bean == null) {
            rstBean.setErrCode(Common.ERR_CODE_NOLOGIN_MP);
            rstBean.setErrMsg(Common.ERR_MSG_NOLOGIN);
            return rstBean;
        }
        List<Map<String, String>> list = supplerService.getSupplerList();
        rstBean.getList().addAll(list);
        rstBean.setCnt(list.size());
        return rstBean;
    }

    @RequestMapping("/sp/login")
    @ResponseBody
    public ResultListBean login(@RequestBody Map<String, String> loginMap, HttpServletRequest request) {
        ResultListBean rb = new ResultListBean();
        String userName = loginMap.getOrDefault("userName", "");
        String password = loginMap.getOrDefault("password", "");
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            rb.setErrCode(1);
            rb.setErrMsg("请输入用户名和密码");
        }
        SupplerBean bean = supplerService.login(userName, password);
        if (bean == null || (StringUtils.isEmpty(bean.getAccount()) && StringUtils.isEmpty(bean.getId()))) {
            rb.setErrCode(1);
            rb.setErrMsg("用户名或密码错误");
            return rb;
        } else {
            HttpSession session=request.getSession();

            session.setAttribute(Common.SUPPLER_SESSIOIN_BEAN, bean);
            rb.setErrCode(0);
            rb.getList().add(bean);
            rb.setErrMsg("登录成功");
            return rb;
        }
    }
}
