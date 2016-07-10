package com.dx.wx;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Created by Tom on 16/7/10.
 */
@Component
public class WXMessageManager {

    private static final Logger logger = Logger.getLogger(WXMessageManager.class);
    private final static String URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
    private final static String MSG_CONTENT = "{ \"touser\":\"#{OPENID}\", \"msgtype\":\"mpnews\", \"mpnews\": { " +
            "\"media_id\":\"-8idR3PAvmFohOCh6LLMZa4pKVHZ6BAK4wZIoGtxPQU\" }}";
    @Autowired
    AccessTokenManager accessTokenManager;

    public void sendWelcomeMsg(String openId) {
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(URL.replace("ACCESS_TOKEN", accessTokenManager.getAccessToken()));
        String content = MSG_CONTENT.replace("#{OPENID}", openId);
        logger.info("content===>" + content);
        StringEntity entity = new StringEntity(content, "utf-8");
        entity.setContentType("application/json");
        post.setEntity(entity);
        try {
            HttpResponse resp = client.execute(post);
            if (resp.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                {
                    HttpEntity resEntity = resp.getEntity();
                    if (resEntity != null) {
                        String result = EntityUtils.toString(resEntity, "utf-8");
                        logger.info("openId===>" + result);
                    }

                }
            }
        } catch (Exception e) {
            logger.error("openId===>", e);
        }
    }


}
