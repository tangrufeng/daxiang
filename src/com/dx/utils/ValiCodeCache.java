package com.dx.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Tom on 16/6/24.
 */
@Component
public class ValiCodeCache {
    private ConcurrentMap<String,String> cache = new ConcurrentHashMap<String,String>();

    private static final  int TIMEOUT=120000;

    public void put(String phone,String valiCode){
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(valiCode)){
            return;
        }
        long cuerrent=System.currentTimeMillis();
        String value=valiCode+"_"+cuerrent;
        cache.put(phone,value);
    }

    /**
     *
     * @param phone
     * @param valiCode
     * @return 0--成功  1--验证码错误  2--过期
     */
    public int valiCode(String phone,String valiCode){
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(valiCode)){
            return 1;
        }

        String value=cache.remove(phone);
        if(StringUtils.isEmpty(value)){
            return 1;
        }

        String[] strArr=value.split("_");

        if(System.currentTimeMillis()-Long.parseLong(strArr[1])>TIMEOUT){
            return 2;
        }

        if(valiCode.equals(strArr[0])){
            return 0;
        }else{
            return 1;
        }
    }


    @Scheduled(fixedRate = TIMEOUT)
    public void clearCache(){
        Set<String> values=cache.keySet();
        Iterator<String> iter=values.iterator();
        while (iter.hasNext()){
            String key=iter.next();
            String value=cache.get(key);
            String[] strArr=value.split("_");
            if(System.currentTimeMillis()-Long.parseLong(strArr[1])>TIMEOUT){
                cache.remove(key);
            }
        }
    }
}
