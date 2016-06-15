package com.dx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dx.entity.Area;
import com.dx.service.AreaService;

@Controller
public class AreaController {

	@Autowired
	AreaService areaService;

	@RequestMapping(value = "/wx/getProvice.do")
	@ResponseBody
	public List<Area> getProvice(){
		return areaService.getProvice();
	}
}
