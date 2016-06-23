package com.dx.service.impl;

import java.util.List;
import java.util.Map;

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

	@Override
	public int addStore(StoreBean bean) {
		return storeDAO.addStore(bean);
	}

	@Override
	public List<Map<String, String>> getStoreList(int sid) {
		return storeDAO.getStoreList(sid);
	}

	@Override
	public int isExistStore(String id, String name, String supplerId) {
		return storeDAO.isExistStore(id,name,supplerId);
	}
}
