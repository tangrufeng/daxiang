package com.dx.service;

import com.dx.entity.StoreRateBean;

import java.util.List;

/**
 * Created by Tom on 16/6/23.
 */
public interface StoreRateService {

    public boolean addRate(int supplerId, List<StoreRateBean> rates);
}
