package com.dx.service.impl;

import com.dx.dao.StoreRateDAO;
import com.dx.entity.StoreRateBean;
import com.dx.service.StoreRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Tom on 16/6/23.
 */
@Service("rateService")
public class StoreRateServiceImpl implements StoreRateService {

    @Autowired
    private StoreRateDAO storeRateDAO;

    @Override
    public boolean addRate(int supplerId, List<StoreRateBean> rates) {
        storeRateDAO.copeRateToHistory(supplerId);
        storeRateDAO.removeRate(supplerId);
        storeRateDAO.addRateList(rates);
        return true;
    }
}
