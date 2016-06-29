package com.dx.controller;

import com.dx.common.Common;
import com.dx.entity.*;
import com.dx.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
	public ResultListBean getStoreList(@RequestParam("sid") int sid,HttpServletRequest request){
		ResultListBean rb=new ResultListBean();
		ManagerBean managerBean = (ManagerBean) request.getSession().getAttribute(Common.MANAGER_SESSIOIN_BEAN);
		if (managerBean == null) {
			rb.setErrCode(Common.ERR_CODE_NOLOGIN_MP);
			rb.setErrMsg(Common.ERR_MSG_NOLOGIN);
			return rb;
		}
	 	List<Map<String,String>> list= storeService.getStoreList(sid);
		rb.getList().addAll(list);
		rb.setCnt(list.size());
		return rb;
	}

	@RequestMapping("sp/getStoreList")
	@ResponseBody
	public ResultListBean getStoreList(HttpServletRequest request){
		ResultListBean rb=new ResultListBean();

		SupplerBean bean=(SupplerBean)request.getSession().getAttribute(Common.SUPPLER_SESSIOIN_BEAN);
		if(bean==null){
			rb.setErrCode(Common.ERR_CODE_NOLOGIN_SP);
			rb.setErrMsg(Common.ERR_MSG_NOLOGIN);
			return rb;
		}
		List<Map<String,String>> list= storeService.getStoreList(Integer.parseInt(bean.getId()));
		rb.getList().addAll(list);
		rb.setCnt(list.size());
		return rb;
	}



	@RequestMapping("mp/addStore")
	@ResponseBody
	public ResultBean addStore(@RequestBody StoreBean bean,HttpServletRequest request){
		ResultBean rb = new ResultBean();
		ManagerBean managerBean = (ManagerBean) request.getSession().getAttribute(Common.MANAGER_SESSIOIN_BEAN);
		if (managerBean == null) {
			rb.setErrCode(Common.ERR_CODE_NOLOGIN_MP);
			rb.setErrMsg(Common.ERR_MSG_NOLOGIN);
			return rb;
		}
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
