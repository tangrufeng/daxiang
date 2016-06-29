package com.dx.utils;

import com.dx.common.Common;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tom on 16/6/24.
 */
@Component
public class SMSUtils {
    private static final Logger logger = Logger.getLogger(SMSUtils.class);

    private final static String SMS_URL = "http://182.236.163.92:8084/WebAPI/SmsAPI.asmx/SendSms?user=" + Common.SMS_USERNAME + "&pwd=" + Common.SMS_PASSWORD + "&mobiles=#mobile#&contents=#content#";

    private final static String REG_STR = "<Code>([^</]+)</Code>";

    public boolean sendSMS(String mobile, String content) {
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(content)){
            return false;
        }
        String url = SMS_URL.replace("#mobile#", mobile).replace("#content#", URLEncoder.encode(content));
        HttpGet post = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpResponse resp = httpClient.execute(post);
            if (resp != null) {
                HttpEntity entity = resp.getEntity();
                if (entity != null) {
                    String rst = EntityUtils.toString(entity, "utf-8");
                    logger.info("==mobile =====>" + mobile + "==" + content + "=发送短信返回==>" + rst);
                    Pattern p = Pattern.compile(REG_STR);
                    Matcher m = p.matcher(rst);
                    String rstCode = "0";
                    if (m.find()) {
                        rstCode = m.group(1);
                    }

                    int intCode = Integer.parseInt(rstCode);
                    return intCode > 0;
                }
            }
        } catch (IOException e) {
            logger.error("==mobile =====>" + mobile + "==" + content + "=发送短信返回==>",e);
        }
        return false;
    }

    public static void main(String args[]) {
        SMSUtils s = new SMSUtils();
        s.sendSMS("18688953119", "【大象汇率】验证码186868 120秒内有效 回T退订");
    }
}
