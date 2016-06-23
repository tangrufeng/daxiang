package com.dx.controller;

import java.util.List;
import java.util.Map;

import com.dx.common.Common;
import com.dx.entity.ResultBean;
import com.dx.entity.SupplerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dx.entity.ResultListBean;
import com.dx.entity.StoreBean;
import com.dx.service.StoreService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class StoreController extends BaseController{

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

	@RequestMapping("mp/getStoreList")
	@ResponseBody
	public ResultListBean getStoreList(@RequestParam("sid") int sid){
		ResultListBean rlb=new ResultListBean();
	 	List<Map<String,String>> list= storeService.getStoreList(sid);
		rlb.getList().addAll(list);
		rlb.setCnt(list.size());
		return rlb;
	}

	@RequestMapping("sp/getStoreList")
	@ResponseBody
	public ResultListBean getStoreList(HttpServletRequest request){
		ResultListBean rlb=new ResultListBean();
		SupplerBean bean=(SupplerBean)request.getSession().getAttribute(Common.SUPPLER_SESSIOIN_BEAN);
		if(bean==null){
//			rlb.setErrCode(1);
//			rlb.setErrMsg("请先登录");
			bean=new SupplerBean();
			bean.setId("1");
		}
		List<Map<String,String>> list= storeService.getStoreList(Integer.parseInt(bean.getId()));
		rlb.getList().addAll(list);
		rlb.setCnt(list.size());
		return rlb;
	}



	@RequestMapping("mp/addStore")
	@ResponseBody
	public ResultBean addStore(@RequestParam StoreBean bean){
		ResultBean rb = new ResultBean();
		if (StringUtils.isEmpty(bean.getName())) {
			rb.setErrCode(1);
			rb.setErrMsg("名称不能为空");
			return rb;
		}
		if (StringUtils.isEmpty(bean.getAddress())) {
			rb.setErrCode(1);
			rb.setErrMsg("地址不能为空");
			return rb;
		}


		if (StringUtils.isEmpty(bean.getPhone())) {
			rb.setErrCode(1);
			rb.setErrMsg("联系电话不能为空");
			return rb;
		}

		int i = storeService.addStore(bean);
		return rb;
	}
}
