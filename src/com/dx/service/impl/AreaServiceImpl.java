package com.dx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dx.dao.AreaDAO;
import com.dx.entity.Area;
import com.dx.service.AreaService;

@Service("areaService")
public class AreaServiceImpl implements AreaService {

	@Autowired
	AreaDAO areaDao;
	
	@Override
	public List<Area> getProvice() {
		return areaDao.getProvice();
	}

}
