package com.dx.controller;

import com.dx.common.Common;
import com.dx.entity.OrderBean;
import com.dx.entity.UserBean;
import com.dx.service.UserService;
import com.dx.wx.WXMessageManager;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.util.XMLHelper;
import org.apache.xmlbeans.impl.common.IOUtil;
import org.apache.xmlbeans.impl.xsd2inst.SampleXmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tom on 16/6/28.
 */
@Controller
public class WxReceiverController {


    private final static Logger logger = Logger.getLogger(WxReceiverController.class);
    private final static String REG_TOUSERNAME = "<ToUserName><!\\[CDATA\\[(.*?)\\]\\]></ToUserName>";
    private final static String REG_FROMUSERNAME = "<FromUserName><!\\[CDATA\\[(.*?)\\]\\]></FromUserName>";
    private final static String REG_CREATETIME = "<CreateTime><!\\[CDATA\\[(.*?)\\]\\]></CreateTime>";
    private final static String REG_MSGTYPE = "<MsgType><!\\[CDATA\\[(.*?)\\]\\]></MsgType>";
    private final static String REG_EVENT = "<Event><!\\[CDATA\\[(.*?)\\]\\]></Event>";
    private final static String REG_EVENTKEY = "<EventKey><!\\[CDATA\\[(.*?)\\]\\]></EventKey>";
    private final static String REG_TICKET = "<Ticket><!\\[CDATA\\[(.*?)\\]\\]></Ticket>";
    /**
     * 微信关注
     */
    private final static String WX_EVENT_SUBSCRIBE = "subscribe";
    /**
     * 取消微信关注
     */
    private final static String WX_EVENT_UNSUBSCRIBE = "unsubscribe";
    /**
     * 消息类型 事件
     */
    private final static String WX_EVENT_MSGTYPE_EVENT = "event";

    @Autowired
    private UserService userService;

    @Autowired
    private WXMessageManager wxMessageManager;

    @RequestMapping("/cgi/wxMsgReceive")
    @ResponseBody
    public String receiveWxMsg(@RequestBody String xml, @RequestParam("signature") String signature,
                               @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce,
                               @RequestParam(value = "echostr", required = false, defaultValue = "") String echostr,
                               HttpServletRequest request) throws IOException {

        logger.debug("come from wx==signature=>" + signature + "==timestamp==>" + timestamp + "==nonce==>" + nonce);
        //开始鉴权,确定是微信来的
        List<String> list = new ArrayList<String>();

        list.add(Common.WX_TOKEN);
        list.add(timestamp);
        list.add(nonce);
        Collections.sort(list);
        StringBuffer sb = new StringBuffer();
        for (String str : list) {
            sb.append(str);
        }


        String sha1 = DigestUtils.sha1Hex(sb.toString());

        logger.debug("come from wx==sha1=>" + sha1 + "==sb==>" + sb.toString());
        if (!signature.equals(sha1)) {
            return "";
        }
        //鉴权结束~~

        logger.info("come from wx======>" + xml);


        if (StringUtils.isEmpty(xml)) {
            return echostr;
        }
        String msgType = "", event = "", eventKey = "", fromUserName = "",toUserName="";

        Pattern p = Pattern.compile(REG_MSGTYPE);
        Matcher m = p.matcher(xml);
        if (m.find()) {
            msgType = m.group(1);
        }

        p = Pattern.compile(REG_EVENT);
        m = p.matcher(xml);
        if (m.find()) {
            event = m.group(1);
        }

        p = Pattern.compile(REG_FROMUSERNAME);
        m = p.matcher(xml);
        if (m.find()) {
            fromUserName = m.group(1);
        }

        p = Pattern.compile(REG_TOUSERNAME);
        m = p.matcher(xml);
        if (m.find()) {
            toUserName = m.group(1);
        }

        p = Pattern.compile(REG_EVENTKEY);
        m = p.matcher(xml);
        if (m.find()) {
            eventKey = m.group(1);
        }

        logger.info(
                "parse from xml===msgType==>" + msgType + "===event====>" + event + "====eventKey====>" + eventKey +
                        "==fromUserName===>" + fromUserName);

        if (WX_EVENT_SUBSCRIBE.equals(event) || WX_EVENT_UNSUBSCRIBE.equals(event)) {
            UserBean bean = new UserBean();
            bean.setOpenId(fromUserName);
            bean.setEvent(event);
            if (!StringUtils.isEmpty(eventKey)) {
                bean.setSource(eventKey.substring("qrscene_".length()));
            }
            userService.syncUser(bean);
        }

        if (WX_EVENT_SUBSCRIBE.equals(event)) {
//            String fileURL=request.getSession().getServletContext().getRealPath("/wx_welcome.xml");
//
//            String xmlMsg=FileUtils.readFileToString(new File(fileURL));
//            xmlMsg=xmlMsg.replace("#{toUser}]",fromUserName);
//            xmlMsg=xmlMsg.replace("#{fromUser}]",toUserName);
//            xmlMsg=xmlMsg.replace("#{createTime}",System.currentTimeMillis()+"");
//            logger.info(xmlMsg);
//            return xmlMsg;
            wxMessageManager.sendWelcomeMsg(fromUserName);
        }

        return echostr;

    }

}
