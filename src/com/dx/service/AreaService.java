package com.dx.service;

import java.util.List;

import com.dx.entity.Area;

public interface AreaService {

	List<Area> getProvice();
	
	List<Area> getCities(int pId);

	List<Area> getAreas();
	
}
