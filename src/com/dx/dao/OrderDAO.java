package com.dx.dao;

import org.apache.ibatis.annotations.*;

import com.dx.entity.OrderBean;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public interface OrderDAO {

	@Insert("INSERT INTO `t_order` ( `openId`, `buy`, `sell`, `takeDate`, `cityId`,  `sum`, `changer`, `IDType`, `IDNO`, `referenceID`, `moblieNO`, `storeId`, `rate`, `status`,createtime,updatetime) "
			+ "VALUES ( #{openId}, #{buy}, #{sell}, #{takeDate}, #{cityId}, #{sum}, #{changer}, #{idType}, #{idNo}, #{referenceID}, #{moblieNo}, #{storeId}, #{rate}, 1,now(),now());")
	int addOrder(OrderBean order);

	@UpdateProvider(type = OrderSQL.class,method = "update")
	int editOrder(OrderBean order);

	@Select("SELECT  o.id, o.buy, o.sell, o.takeDate, o.cityId, o.sum, o.changer, o.IDType, o.IDNO, o.referenceID, o.moblieNO, o.status orderStatus, s.name, s.id storeId, s.max, s.min, s.address, s.opentime openningHours,a.name as area,c.name as city " +
			"FROM t_order o, t_stores s, t_areas a, dict_city c " +
			"WHERE o.storeId = s.id and s.city=c.id and s.area=a.id and openId=#{openId}")
	List<Map<String,String>> getOrderByUser(String openId);


	@Update("update t_order set status=#{status} where id=#{orderId} and openId=#{openId}")
	int updateStatus(@Param("orderId") int orderId,@Param("openId") String openId,@Param("status") int status);

	@SelectProvider(type=OrderSQL.class,method = "query")
	List<Map<String,String>> queryOrderByPage(OrderBean order,int page,int pageSize);

	public class OrderSQL {

		public String query(final OrderBean order,int page,int pageSize){

			return new SQL(){
				{
					SELECT("SELECT  id, openId, buy, sell, takeDate, cityId, sum, changer, IDType, IDNO, referenceID, moblieNO, storeId, rate, status, createTime, updateTime, st.name as store, c.name as city, sp.name suppler, ch.name");
					FROM("t_order o, dict_city c, t_suppliers sp, t_channels ch, t_stores st ");
					WHERE("o.cityId=c.id and o.storeId=st.id and st.s_id=sp.id and ch.code=o.referenceID ");

					//通过条件 判断是否需要更新该字段
					if (!StringUtils.isEmpty(order.getBuy())){
						WHERE("buy = #{buy}");
					}
					if (!StringUtils.isEmpty(order.getSell())){
						WHERE("sell = #{sell}");
					}
					if (!StringUtils.isEmpty(order.getTakeDate())){
						WHERE("takeDate = #{takeDate}");
					}
					if (!StringUtils.isEmpty(order.getCityId())){
						WHERE("cityId = #{cityId}");
					}
					if (!StringUtils.isEmpty(order.getChanger())){
						WHERE("changer = '%#{changer}%'");
					}
					if (!StringUtils.isEmpty(order.getIdNo())){
						WHERE("IDNO like '%#{idNo}%'");
					}
					if (!StringUtils.isEmpty(order.getReferenceID())){
						WHERE("referenceID = #{referenceID}");
					}
					if (!StringUtils.isEmpty(order.getMoblieNo())){
						WHERE("moblieNO = #{moblieNO}");
					}
					if (!StringUtils.isEmpty(order.getStoreId())){
						WHERE("storeId = #{storeId}");
					}
					if (!StringUtils.isEmpty(order.getCreateTime())){
						WHERE("DATE_FORMAT(createTime,'%Y-%m-%d') = #{createTime}");
					}
					int start=(page-1)*pageSize;
					WHERE("LIMIT ");

				}
			}.toString();
		}

		public String update(final OrderBean order) {
				return new SQL(){
					{
						UPDATE("t_order");

						//通过条件 判断是否需要更新该字段
						if (!StringUtils.isEmpty(order.getBuy())){
							SET("buy = #{buy}");
						}
						if (!StringUtils.isEmpty(order.getSell())){
							SET("sell = #{sell}");
						}
						if (!StringUtils.isEmpty(order.getTakeDate())){
							SET("takeDate = #{takeDate}");
						}
						if (!StringUtils.isEmpty(order.getCityId())){
							SET("cityId = #{cityId}");
						}
						if (!StringUtils.isEmpty(order.getSum())){
							SET("sum = #{sum}");
						}
						if (!StringUtils.isEmpty(order.getChanger())){
							SET("changer = #{changer}");
						}
						if (!StringUtils.isEmpty(order.getIdType())){
							SET("IDType = #{idType}");
						}
						if (!StringUtils.isEmpty(order.getIdNo())){
							SET("IDNO = #{idNo}");
						}
						if (!StringUtils.isEmpty(order.getReferenceID())){
							SET("referenceID = #{referenceID}");
						}
						if (!StringUtils.isEmpty(order.getMoblieNo())){
							SET("moblieNO = #{moblieNO}");
						}
						if (!StringUtils.isEmpty(order.getStoreId())){
							SET("storeId = #{storeId}");
						}
							SET("updateTime = now()");

						WHERE("id = #{id}");

					}
			}.toString();
		}
	}
}
