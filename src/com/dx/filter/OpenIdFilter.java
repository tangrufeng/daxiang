package com.dx.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Tom on 16/6/21.
 */

public class OpenIdFilter implements Filter{
    private static final Logger logger=Logger.getLogger(OpenIdFilter.class);

    private String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxa6ef537b46131748&secret=d1a1007c9b2d9393f17e49276a38280e&code=#CODE#&grant_type=authorization_code";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest)servletRequest;
        logger.debug("querypath======>" + httpServletRequest.getQueryString() + "====" + httpServletRequest.getParameter("code"));
        String code = httpServletRequest.getParameter("code");
        Object sOpenId = httpServletRequest.getSession().getAttribute("session_openid");
        if (sOpenId==null && !StringUtils.isEmpty(code)) {
            url = url.replace("#CODE#", code);
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet get = new HttpGet(url);
            HttpResponse response = httpClient.execute(get);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity, "utf-8");
                    logger.debug("openId========>" + result);
                    JSONObject json=JSONObject.parseObject(result);
                    if(json.containsKey("openid")){
                        String openId=json.getString("openid");
                        httpServletRequest.getSession().setAttribute("session_openid",openId);
                    }
                }
            }

        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
