package com.dx.dao;

import org.apache.ibatis.annotations.Insert;

import com.dx.entity.OrderBean;

public interface OrderDAO {

	@Insert("INSERT INTO `t_order` ( `openId`, `buy`, `sell`, `takeDate`, `cityId`,  `sum`, `changer`, `IDType`, `IDNO`, `referenceID`, `moblieNO`, `storeId`, `rate`, `status`,createtime,updatetime) "
			+ "VALUES ( #{openId}, #{buy}, #{sell}, #{takeDate}, #{cityId}, #{sum}, #{changer}, #{idType}, #{idNo}, #{referenceID}, #{moblieNo}, #{storeId}, #{rate}, 1,now(),now());")
	int addOrder(OrderBean order);

	int editOrder(OrderBean order);

	int updateStatus(int orderId,int status);

}
