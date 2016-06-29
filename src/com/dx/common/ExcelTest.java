package com.dx.common;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import sun.misc.BASE64Encoder;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tom on 16/6/22.
 */
public class ExcelTest {
    private static final String[] CURRENCY={
            "USD", /*美元 USD*/
            "JPY", /*日元 JPY*/
            "AUD", /*澳大利亚元 AUD*/
            "EUR", /*欧元 EUR*/
            "HKD", /*港币 HKD*/
            "AED", /*阿联酋迪拉姆 AED*/
            "DKK", /*丹麦克朗 DKK*/
            "CHF", /*瑞士法郎 CHF*/
            "CAD", /*加拿大元 CAD*/
            "BRL", /*巴西里亚尔 BRL*/
            "GBP", /*英镑 GBP*/
            "INR", /*印度卢比 INR*/
            "IDR", /*印尼卢比 IDR*/
            "ZAR", /*南非兰特 ZAR*/
            "TWD", /*新台币 TWD*/
            "THB", /*泰国铢 THB*/
            "SGD", /*新加坡元 SGD*/
            "SEK", /*瑞典克朗 SEK*/
            "RUB", /*卢布 RUB*/
            "PHP", /*菲律宾比索 PHP*/
            "NZD", /*新西兰元 NZD*/
    };

    private final static String REG_TOUSERNAME = "<ToUserName><!\\[CDATA\\[(.*?)\\]\\]></ToUserName>";

    private final static String REG_FROMUSERNAME = "<FromUserName><!\\[CDATA\\[(.*?)\\]\\]></FromUserName>";

    private final static String REG_CREATETIME = "<CreateTime><!\\[CDATA\\[(.*?)\\]\\]></CreateTime>";

    private final static String REG_MSGTYPE = "<MsgType><!\\[CDATA\\[(.*?)\\]\\]></MsgType>";

    private final static String REG_EVENT = "<Event><!\\[CDATA\\[(.*?)\\]\\]></Event>";

    private final static String REG_EVENTKEY = "<EventKey><!\\[CDATA\\[(.*?)\\]\\]></EventKey>";

    private final static String REG_TICKET = "<Ticket><!\\[CDATA\\[(.*?)\\]\\]></Ticket>";
    
    public static String xml="<xml><ToUserName><![CDATA[gh_b4d94807c9f7]]></ToUserName>"+
            "<FromUserName><![CDATA[oWLn9slpAibc66VvRboiOubgZTHw]]></FromUserName>"+
            "<CreateTime>1467161225</CreateTime>"+
            "<MsgType><![CDATA[event]]></MsgType>"+
            "<Event><![CDATA[subscribe]]></Event>"+
            "<EventKey><![CDATA[qrscene_ba379fd5f7bb41fc93485c22bbbdd5e4]]></EventKey>"+
            "<Ticket><![CDATA[gQFq7zoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL3Qwa0d1RGJsbVhyZ095RlpsR0djAAIEKvNvVwMEAAAAAA==]]></Ticket>"+
            "</xml>";
    
    public static void main(String args[]) {


        Pattern p = Pattern.compile(REG_MSGTYPE);
        Matcher m = p.matcher(xml);
        if (m.find()) {
            System.out.println(m.group(1));
        }

        p = Pattern.compile(REG_EVENT);
        m = p.matcher(xml);
        if (m.find()) {
            System.out.println(m.group(1));
        }

        p = Pattern.compile(REG_FROMUSERNAME);
        m = p.matcher(xml);
        if (m.find()) {
            System.out.println(m.group(1));
        }
        p = Pattern.compile(REG_EVENTKEY);
        m = p.matcher(xml);
        if (m.find()) {
            System.out.println(m.group(1));
        }
        System.out.println(URLEncoder.encode("#"));

    }

}
