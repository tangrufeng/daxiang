package com.dx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dx.entity.ResultListBean;
import com.dx.entity.StoreBean;
import com.dx.service.StoreService;

@Controller
public class StoreController {

	@Autowired
	StoreService storeService;

	@RequestMapping("/wx/getStoreList.do")
	@ResponseBody
	public ResultListBean getStoreList(@RequestParam("cityId") String city,
			@RequestParam("areaId") String area,
			@RequestParam("buy") String buy,
			@RequestParam("sell") String sell,
			@RequestParam("takeDate") String takeDate) {
		ResultListBean bean = new ResultListBean();
		bean.getList().addAll(
				storeService.getStores(city, area, buy, sell, takeDate));
		bean.setCnt(bean.getList().size());
		return bean;
	}
}
