package com.dx.wx;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.dx.common.Common;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.common.IOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by Tom on 16/6/24.
 */
@Component
public class QRManager {

    private static final Logger logger = Logger.getLogger(QRManager.class);

    @Autowired
    private AccessTokenManager atm;

    private final static String URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=#TOKENPOST#";

    private final static String SHOWQRCODE_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";

    private final static String POST_JSON = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"#SCENE_STR#\"}}}";

    public String getQR(String uuid) {

        String token = atm.getAccessToken();
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL.replace("#TOKENPOST#", token));
        StringEntity entity = new StringEntity(POST_JSON.replace("#SCENE_STR#", uuid), "utf-8");
        entity.setContentType("application/json");
        post.setEntity(entity);
        try {
            HttpResponse resp = client.execute(post);
            if (resp.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                {
                    HttpEntity resEntity = resp.getEntity();
                    if (resEntity != null) {
                        String result = EntityUtils.toString(resEntity, "utf-8");
                        logger.debug("QRCode========>" + result);
                        JSONObject json = JSONObject.parseObject(result);
                        if (json.containsKey("ticket")) {
                            String ticket = json.getString("ticket");
                            HttpGet get = new HttpGet(SHOWQRCODE_URL + ticket);
                            HttpResponse respQR = client.execute(get);
                            HttpEntity httpEntity = respQR.getEntity();
                            if (httpEntity != null) {
                                InputStream inputStream = httpEntity.getContent();
                                byte[] bytes = new byte[(int) httpEntity.getContentLength()];
                                inputStream.read(bytes);
                                inputStream.close();
                                return Base64.encodeBase64String(bytes);
                            }
                        }
                    }

                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return "";

    }

    public static void main(String args[]) {
        QRManager qr = new QRManager();
        System.out.println(qr.getQR("ba379fd5f7bb41fc93485c22bbbdd5e4"));
    }
}
