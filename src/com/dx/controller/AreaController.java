package com.dx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dx.entity.Area;
import com.dx.entity.ResultBean;
import com.dx.entity.ResultListBean;
import com.dx.service.AreaService;

@Controller
public class AreaController {

	@Autowired
	AreaService areaService;

	@RequestMapping(value = "/wx/getPrivice.do")
	@ResponseBody
	public ResultBean getProvice(){
		ResultListBean bean =new ResultListBean();
		bean.getList().addAll(areaService.getProvice());
		bean.setCnt(bean.getList().size());
		return bean;
	}
	

	@RequestMapping(value = "/wx/getCities.do")
	@ResponseBody
	public ResultListBean getCities(@RequestParam("pId") int id){
		ResultListBean bean =new ResultListBean();
		bean.getList().addAll(areaService.getCities(id));
		bean.setCnt(bean.getList().size());
		return bean;
	}

	@RequestMapping(value = "/wx/getAreas.do")
	@ResponseBody
	public ResultListBean getAreas(){
		ResultListBean bean =new ResultListBean();
		bean.getList().addAll(areaService.getAreas());
		bean.setCnt(bean.getList().size());
		return bean;
	}
	
	
}
