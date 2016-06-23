package com.dx.dao;

import org.apache.ibatis.annotations.*;

import com.dx.entity.OrderBean;
import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public interface OrderDAO {

	@Insert("INSERT INTO `t_order` ( `openId`, `buy`, `sell`, `takeDate`, `cityId`,  `sum`, `changer`, `IDType`, `IDNO`, `referenceID`, `mobileNO`, `storeId`, `rate`, `status`,createtime,updatetime) "
			+ "VALUES ( #{openId}, #{buy}, #{sell}, #{takeDate}, #{cityId}, #{sum}, #{changer}, #{idType}, #{idNo}, #{referenceID}, #{moblieNo}, #{storeId}, #{rate}, 1,DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'),DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'));")
	int addOrder(OrderBean order);

	@UpdateProvider(type = OrderSQL.class,method = "update")
	int editOrder(OrderBean order);

	@Select("SELECT  o.id, o.buy, o.sell,o.rate, o.takeDate, o.cityId, o.sum, o.changer, o.idType, o.idNo, o.referenceID, o.mobileNo, o.status orderStatus, s.name, s.id storeId, s.max, s.min, s.address, s.opentime openingHours,a.name as area,c.name as city,DATE_FORMAT(o.createTime,'%Y-%m-%d %H:%i:%s') createTime " +
			"FROM t_order o, t_stores s, t_areas a, dict_city c " +
			"WHERE o.storeId = s.id and s.city=c.id and s.area=a.id and openId=#{openId}")
	List<Map<String,String>> getOrderByUser(String openId);


	@Update("update t_order set status=#{status} where id=#{orderId} and openId=#{openId}")
	int updateStatus(@Param("orderId") int orderId,@Param("openId") String openId,@Param("status") int status);

	@SelectProvider(type=OrderSQL.class,method = "query")
	List<Map<String,String>> queryOrderByPage(Map<String,String> map);


	public class OrderSQL {

		public String query(final Map params){
			SQL sql= new SQL(){
				{
					SELECT("o.id, openId, buy, sell, takeDate, cityId, sum, changer, idType, idNo, ifnull(referenceID,'') referenceID, ifnull(mobileNo,'') mobileNo, storeId, rate, o.status, DATE_FORMAT(o.createTime,'%Y-%m-%d%H:%i:%s') createTime, DATE_FORMAT(o.updateTime,'%Y-%m-%d%H:%i:%s') updateTime, st.name as store, c.name as city, sp.name suppler, ifnull(ch.name,'') as channel");
					FROM(" t_order o ");
                    LEFT_OUTER_JOIN("dict_city c ON o.cityId = c.id");
                    LEFT_OUTER_JOIN("t_channels ch ON ch.code = o.referenceID");
                    LEFT_OUTER_JOIN("t_stores st ON o.storeId = st.id");
                    LEFT_OUTER_JOIN("t_suppliers sp ON st.s_id = sp.id");

					//通过条件 判断是否需要更新该字段
					if (params.containsKey("buy")){
						WHERE("buy = #{buy}");
					}
					if (params.containsKey("sell")){
						WHERE("sell = #{sell}");
					}
					if (params.containsKey("takeDate")){
						WHERE("takeDate = #{takeDate}");
					}
					if (params.containsKey("cityId")){
						WHERE("cityId = #{cityId}");
					}
					if (params.containsKey("changer")){
						WHERE("changer = '%#{changer}%'");
					}
					if (params.containsKey("idNo")){
						WHERE("IDNO like '%#{idNo}%'");
					}
					if (params.containsKey("referenceID")){
						WHERE("referenceID = #{referenceID}");
					}
					if (params.containsKey("moblieNo")){
						WHERE("moblieNO = #{moblieNo}");
					}
					if (params.containsKey("storeId")){
						WHERE("storeId = #{storeId}");
					}
					if (params.containsKey("createTime")){
						WHERE("DATE_FORMAT(createTime,'%Y-%m-%d') = #{createTime}");
					}
					if(params.containsKey("status")){
						WHERE("o.status = #{status}");
					}
					if(params.containsKey("supplierId")){
						WHERE("sp.id = #{supplierId}");
					}
					if(params.containsKey("orderId")){
						WHERE("o.id = #{orderId}");
					}

                    ORDER_BY(" o.createTime desc");
				}

			};

			int page=1;
			int pageSize=10;
			try {
				page = Integer.parseInt(String.valueOf(params.get("page")));
				pageSize = Integer.parseInt(String.valueOf(params.get("pageSize")));
			}catch (Exception e){

			}
			int start=(page-1)*pageSize;
			StringBuilder sb=new StringBuilder(sql.toString());
			sb.append(" LIMIT "+start+","+pageSize);
			return sb.toString();
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
							SET("updateTime = DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s') ");

						WHERE("id = #{id}");

					}
			}.toString();
		}
	}
}
