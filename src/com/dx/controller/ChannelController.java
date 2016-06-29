package com.dx.controller;

import com.dx.common.Common;
import com.dx.entity.ChannelBean;
import com.dx.entity.ManagerBean;
import com.dx.entity.ResultBean;
import com.dx.entity.ResultListBean;
import com.dx.service.ChannelService;
import com.dx.wx.QRManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Tom on 16/6/24.
 */
@Controller
public class ChannelController {

    private static final Logger logger=Logger.getLogger(ChannelController.class);

    @Autowired
    ChannelService channelService;

    @Autowired
    QRManager qrManager;

    @RequestMapping("/mp/addChannel.do")
    @ResponseBody
    public ResultBean addChannel(@RequestBody ChannelBean bean,HttpServletRequest request){
        ResultBean rb=new ResultBean();
        ManagerBean managerBean = (ManagerBean) request.getSession().getAttribute(Common.MANAGER_SESSIOIN_BEAN);
        if (managerBean == null) {
            rb.setErrCode(Common.ERR_CODE_NOLOGIN_MP);
            rb.setErrMsg(Common.ERR_MSG_NOLOGIN);
            return rb;
        }
        if(StringUtils.isEmpty(bean.getName())){
            rb.setErrCode(1);
            rb.setErrMsg("渠道商名称不能为空");
            return rb;
        }

        if(channelService.countNameByAdd(bean.getName())>0){
            rb.setErrCode(1);
            rb.setErrMsg("渠道商名称不能重复");
            return rb;
        }

        String uuid= UUID.randomUUID().toString().replace("-","");
        logger.info(bean.getName()+"===UUID==>"+uuid);
        bean.setQrcode(uuid);
        if(channelService.addChannel(bean)!=1){
            rb.setErrCode(1);
            rb.setErrMsg("渠道商添加失败");
            return rb;
        }
        String image=qrManager.getQR(uuid);
        logger.debug("image=====>"+image);
        if(channelService.updateQRImage(image,uuid)!=1){
            rb.setErrCode(1);
            rb.setErrMsg("渠道商添加失败");
            return rb;
        }else{
            rb.setErrMsg(image);
            return rb;
        }
    }

    @RequestMapping("/mp/getChannels")
    @ResponseBody
    public ResultListBean getChannels(HttpServletRequest request){
        ResultListBean rlb=new ResultListBean();
        ManagerBean managerBean = (ManagerBean) request.getSession().getAttribute(Common.MANAGER_SESSIOIN_BEAN);
        if (managerBean == null) {
            rlb.setErrCode(Common.ERR_CODE_NOLOGIN_MP);
            rlb.setErrMsg(Common.ERR_MSG_NOLOGIN);
            return rlb;
        }
        List<Map<String,String>> list=channelService.getChannels();
        rlb.getList().addAll(list);
        rlb.setCnt(list.size());
        return rlb;
    }
}
