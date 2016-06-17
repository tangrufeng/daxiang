package com.dx.service;

import java.util.List;

import com.dx.entity.AreaBean;

public interface AreaService {

	List<AreaBean> getProvice();
	
	List<AreaBean> getCities(int pId);

	List<AreaBean> getAreas();
	
}
