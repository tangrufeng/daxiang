package com.dx.controller;

import com.dx.entity.ResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Tom on 16/6/18.
 */
@Controller
public class ValiMobileController extends BaseController{

    @RequestMapping("/wx/sendValiCode")
    @ResponseBody
    public ResultBean sendValiCode(@RequestParam String openId,@RequestParam String mobileNo){
        ResultBean rst =new ResultBean();
        rst.setErrCode(0);
        return rst;
    }

    @RequestMapping("/wx/valiMobile")
    @ResponseBody
    public ResultBean sendValiCode(@RequestParam String openId,@RequestParam String mobileNo,@RequestParam String valiCode){
        ResultBean rst =new ResultBean();
        rst.setErrCode(0);
        return rst;
    }


}
