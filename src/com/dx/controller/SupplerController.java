package com.dx.controller;

import com.dx.common.Common;
import com.dx.entity.ResultBean;
import com.dx.entity.ResultListBean;
import com.dx.entity.SupplerBean;
import com.dx.service.SupplerService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Tom on 16/6/22.
 */
@Controller
public class SupplerController {

    @Autowired
    SupplerService supplerService;

    @RequestMapping("/mp/addSuppler")
    @ResponseBody
    public ResultBean addSuppler(@RequestBody SupplerBean bean) {
        ResultBean rb = new ResultBean();
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

        int i = supplerService.addSuppler(bean);
        return rb;
    }

    @RequestMapping("/mp/editSuppler")
    @ResponseBody
    public ResultBean editSuppler(@RequestBody SupplerBean bean) {
        ResultBean rb = new ResultBean();
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


    @RequestMapping("/mp/resetPWD")
    @ResponseBody
    public ResultBean resetPWD(@RequestParam("id") int id, @RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword) {
        ResultBean rb = new ResultBean();
        int i = supplerService.resetPWD(id, oldPassword,newPassword);
        if(i==0){
            rb.setErrCode(1);
            rb.setErrMsg("密码重置失败");
        }
        return rb;
    }


    @RequestMapping("/mp/getSupplerList")
    @ResponseBody
    public ResultListBean getSupplerList() {
        ResultListBean rstBean = new ResultListBean();
        List<Map<String, String>> list = supplerService.getSupplerList();
        rstBean.getList().addAll(list);
        rstBean.setCnt(list.size());
        return rstBean;
    }

    @RequestMapping("/mp/login")
    @ResponseBody
    public ResultBean login(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpServletRequest request) {
        ResultBean rb = new ResultBean();
        SupplerBean bean = supplerService.login(userName, password);
        if (bean == null || (StringUtils.isEmpty(bean.getAccount()) && StringUtils.isEmpty(bean.getId()))) {
            rb.setErrCode(1);
            rb.setErrMsg("用户名或密码错误");
            return rb;
        } else {
            request.getSession().setAttribute(Common.SUPPLER_SESSIOIN_BEAN, bean);
            rb.setErrCode(0);
            rb.setErrMsg("登录成功");
            return rb;
        }
    }
}
