package com.dx.service;

import com.dx.entity.ChannelBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Tom on 16/6/24.
 */
public interface ChannelService {

    public int addChannel(ChannelBean bean);

    public List<Map<String,String>> getChannels();

    public int updateQRImage(String image,String code);

    public int countNameByAdd(String name);

    public int countNameByUpdate(String name, String id);
}
