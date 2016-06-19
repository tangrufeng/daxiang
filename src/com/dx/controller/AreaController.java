package com.dx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dx.entity.AreaBean;
import com.dx.entity.ResultBean;
import com.dx.entity.ResultListBean;
import com.dx.service.AreaService;

@Controller
public class AreaController extends BaseController{

	@Autowired
	AreaService areaService;


	@RequestMapping(value = "/wx/getCities.do")
	@ResponseBody
	public ResultListBean getCities(){
		ResultListBean bean =new ResultListBean();
		bean.getList().addAll(areaService.getCities());
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
