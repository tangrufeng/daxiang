package com.dx.controller;

import com.dx.entity.ResultListBean;
import com.dx.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AreaController extends BaseController{

	@Autowired
	AreaService areaService;


	@RequestMapping(value = "/common/getCities.do")
	@ResponseBody
	public ResultListBean getCities(){
		ResultListBean bean =new ResultListBean();
		bean.getList().addAll(areaService.getCities());
		bean.setCnt(bean.getList().size());
		return bean;
	}

	@RequestMapping(value = "/common/getAreas.do")
	@ResponseBody
	public ResultListBean getAreas(){
		ResultListBean bean =new ResultListBean();
		bean.getList().addAll(areaService.getAreas());
		bean.setCnt(bean.getList().size());
		return bean;
	}
	
	
}
