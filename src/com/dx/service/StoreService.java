package com.dx.service;

import com.dx.entity.StoreBean;

import java.util.List;
import java.util.Map;

public interface StoreService {

	public List<StoreBean> getStores(String city,String area,String buy,String sell,String takeDate);

	public int addStore(StoreBean bean);

	public List<Map<String,String>> getStoreList(int sid);

	public int isExistStore(String id, String name, String supplerId);

}
