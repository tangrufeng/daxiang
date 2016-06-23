package com.dx.controller;

import com.dx.entity.ResultBean;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Tom on 16/6/18.
 */
public class BaseController {
    private static Logger logger = Logger.getLogger(BaseController.class);

    @ExceptionHandler
    @ResponseBody
    public ResultBean exp(Exception ex){
        logger.error("",ex);
        ResultBean rst=new ResultBean();
        rst.setErrCode(-1);
        rst.setErrMsg("网络异常,联通用户请拨打10010,移动用户请拨打10086,电信用户请拨打10000进行咨询或投诉,我们将为您提供详细的服务");
        return rst;
    }

}
