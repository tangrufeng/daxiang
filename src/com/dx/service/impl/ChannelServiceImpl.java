package com.dx.service.impl;

import com.dx.dao.ChannelDAO;
import com.dx.entity.ChannelBean;
import com.dx.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Tom on 16/6/24.
 */
@Service("channelService")
public class ChannelServiceImpl implements ChannelService{

    @Autowired
    ChannelDAO channelDAO;
    @Override
    public int addChannel(ChannelBean bean) {
        return channelDAO.addChannel(bean);
    }

    @Override
    public List<Map<String, String>> getChannels() {
        return channelDAO.getChannels();
    }

    @Override
    public int countNameByAdd(String name) {
        return channelDAO.countNameByAdd(name);
    }

    @Override
    public int countNameByUpdate(String name, String id) {
        return channelDAO.countNameByUpdate(name,id);
    }

    @Override
    public int updateQRImage(String image, String code) {
        return channelDAO.updateQRImage(image,code);
    }
}
