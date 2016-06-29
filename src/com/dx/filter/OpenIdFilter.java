package com.dx.filter;

import com.alibaba.fastjson.JSONObject;
import com.dx.common.Common;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.net.ssl.*;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by Tom on 16/6/21.
 */
@Component
public class OpenIdFilter implements Filter {
    private static final Logger logger = Logger.getLogger(OpenIdFilter.class);

    private String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ Common.WX_APPID+"&secret="+ Common.WX_SERCERT+"&code=#CODE#&grant_type=authorization_code";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String code = httpServletRequest.getParameter("code");
        Object sOpenId = httpServletRequest.getSession().getAttribute(Common.SESSION_OPENID);
        logger.info("Common.SESSION_OPENID==========>"+sOpenId+"====code====>"+code);
        if (sOpenId == null && !StringUtils.isEmpty(code)) {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet get = new HttpGet(url.replace("#CODE#", code));
            HttpResponse response = httpClient.execute(get);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity, "utf-8");
                    logger.debug("openId========>" + result);
                    JSONObject json=JSONObject.parseObject(result);
                    if(json.containsKey("openid")){
                        String openId=json.getString("openid");
                        httpServletRequest.getSession().setAttribute(Common.SESSION_OPENID,openId);
                    }
                }
            }

        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
