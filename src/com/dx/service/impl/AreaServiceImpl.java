package com.dx.service.impl;

import com.dx.dao.AreaDAO;
import com.dx.entity.AreaBean;
import com.dx.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("areaService")
public class AreaServiceImpl implements AreaService {

	@Autowired
	AreaDAO areaDao;
	

	@Override
	public List<AreaBean> getCities() {
		return areaDao.getCities();
	}

	@Override
	public List<AreaBean> getAreas() {
		return areaDao.getAreas();
	}

	@Override
	public List<AreaBean> getUsefullCities() {
		return areaDao.getUsefullCities();
	}
}
