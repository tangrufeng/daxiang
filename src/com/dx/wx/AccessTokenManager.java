package com.dx.wx;

import com.alibaba.fastjson.JSONObject;
import com.dx.common.Common;
import com.dx.filter.OpenIdFilter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Tom on 16/6/24.
 */
@Component
public class AccessTokenManager {

    private static final Logger logger = Logger.getLogger(AccessTokenManager.class);
    private String accessToken="";

    private final static String URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ Common.WX_APPID+"&secret="+ Common.WX_SERCERT;

    public String getAccessToken() {
        return accessToken;
    }

    @Scheduled(fixedRate = 7000*1000)
    private void flushAccessToken(){
        HttpClient httpClient = HttpClients.createDefault();
        logger.info("accessToken====URL====>" + URL);
        HttpGet get = new HttpGet(URL);
        try {
            HttpResponse response = httpClient.execute(get);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity, "utf-8");
                    logger.info("accessToken========>" + result);
                    JSONObject json = JSONObject.parseObject(result);
                    if (json.containsKey("access_token")) {
                        synchronized (accessToken) {
                            accessToken = json.getString("access_token");
                        }
                    }
                }
            }
        }catch (Exception e){
            logger.error("",e);
        }

    }
}
