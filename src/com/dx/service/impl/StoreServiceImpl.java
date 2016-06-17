package com.dx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dx.dao.AreaDAO;
import com.dx.dao.StoreDAO;
import com.dx.entity.AreaBean;
import com.dx.entity.StoreBean;
import com.dx.service.AreaService;
import com.dx.service.StoreService;

@Service("storeService")
public class StoreServiceImpl implements StoreService {

	@Autowired
	StoreDAO storeDAO;

	@Override
	public List<StoreBean> getStores(String city, String area, String buy,
			String sell, String takeDate) {
		return storeDAO.getStores(city, area, buy, sell, takeDate);
	}
	

}
