package com.dx.controller;

import com.dx.common.Common;
import com.dx.entity.ManagerBean;
import com.dx.entity.ResultListBean;
import com.dx.entity.SupplerBean;
import com.dx.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Tom on 16/6/24.
 */
@Controller
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @RequestMapping("/mp/login")
    @ResponseBody
    public ResultListBean login(@RequestBody Map<String,String> loginMap, HttpServletRequest request) {
        ResultListBean rb = new ResultListBean();
        String userName=loginMap.getOrDefault("userName","");
        String password=loginMap.getOrDefault("password","");
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
            rb.setErrCode(1);
            rb.setErrMsg("请输入用户名和密码");
        }
        ManagerBean bean = managerService.login(userName, password);
        if (bean == null || (StringUtils.isEmpty(bean.getName()) && StringUtils.isEmpty(bean.getId()))) {
            rb.setErrCode(1);
            rb.setErrMsg("用户名或密码错误");
            return rb;
        } else {
            HttpSession session=request.getSession();
            session.setAttribute(Common.MANAGER_SESSIOIN_BEAN, bean);
            rb.setErrCode(0);
            rb.getList().add(bean);
            rb.setErrMsg("登录成功");
            return rb;
        }
    }
}
