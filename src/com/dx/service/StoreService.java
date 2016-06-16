package com.dx.service;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.dx.entity.StoreBean;

public interface StoreService {

	public List<StoreBean> getStores(String city,String area,String buy,String sell,String takeDate);

}
