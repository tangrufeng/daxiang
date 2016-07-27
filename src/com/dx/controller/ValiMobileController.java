package com.dx.controller;

import com.dx.common.Common;
import com.dx.entity.ResultBean;
import com.dx.service.OrderService;
import com.dx.utils.SMSUtils;
import com.dx.utils.ValiCodeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Tom on 16/6/18.
 */
@Controller
public class ValiMobileController extends BaseController{

    @Autowired
    ValiCodeCache cache;

    @Autowired
    SMSUtils smsUtils;

    @Autowired
    OrderService orderService;

    @RequestMapping("/wx/isExistMobile")
    @ResponseBody
    public ResultBean isExistMobile(@RequestParam("mobileNo") String mobileNo,HttpServletRequest request){
        ResultBean rst =new ResultBean();
        if(request.getSession().getAttribute(Common.SESSION_OPENID)!=null) {
            String isExist=orderService.isExistMobileNo(mobileNo);
            if(StringUtils.isEmpty(isExist)){
                rst.setIsExist("0");
            }else{
                rst.setIsExist("1");
            }
        }else{
            rst.setErrCode(1);
            rst.setErrMsg("请先关注【大象汇率】公众号");
        }

        return rst;
    }

    @RequestMapping("/wx/sendValiCode")
    @ResponseBody
    public ResultBean sendValiCode(@RequestParam String mobileNo, HttpServletRequest request){
        ResultBean rst =new ResultBean();
        if(request.getSession().getAttribute(Common.SESSION_OPENID)!=null) {
            int valiCode = (int) ((Math.random() * 9 + 1) * 100000);
            if(smsUtils.sendSMS(mobileNo,Common.SMS_CONTENT_VALICODE.replace("#CODE#",String.valueOf(valiCode)))){
                cache.put(mobileNo,String.valueOf(valiCode));
            }else {
                rst.setErrCode(1);
                rst.setErrMsg("验证短信发送失败,请重试!");
            }
        }else{
            rst.setErrCode(1);
            rst.setErrMsg("请先关注【大象汇率】公众号");
        }

        rst.setErrCode(0);
        return rst;
    }

    @RequestMapping("/wx/valiMobile")
    @ResponseBody
    public ResultBean sendValiCode(@RequestParam String mobileNo,@RequestParam String valiCode,HttpServletRequest request){
        ResultBean rst =new ResultBean();
        if(request.getSession().getAttribute(Common.SESSION_OPENID)!=null) {
            int i=cache.valiCode(mobileNo,valiCode);
            switch (i){
                case 0:
                    rst.setErrCode(0);
                    rst.setErrMsg("手机验证成功");
                    break;
                case 1:
                    rst.setErrCode(1);
                    rst.setErrMsg("验证码错误");
                    break;
                case 2:
                    rst.setErrCode(2);
                    rst.setErrMsg("验证码已过期");
                    break;
            }
        }
        return rst;
    }


}
