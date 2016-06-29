package com.dx.dao;

import com.dx.entity.OrderBean;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public interface OrderDAO {

	@Insert("INSERT INTO `t_order` ( `openId`, `buy`, `sell`, `takeDate`, `cityId`,  `sum`, `changer`, `IDType`, `IDNO`, `referenceID`, `mobileNO`, `storeId`, `rate`, `status`,createtime,updatetime) "
			+ "VALUES ( #{openId}, #{buy}, #{sell}, #{takeDate}, #{cityId}, #{sum}, #{changer}, #{idType}, #{idNo}, #{referenceID}, #{mobileNo}, #{storeId}, #{rate}, 1,DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'),DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'));")
	int addOrder(OrderBean order);

	@UpdateProvider(type = OrderSQL.class,method = "update")
	int editOrder(OrderBean order);

	@Select("SELECT  o.id, o.buy, o.sell,o.rate, o.takeDate, o.cityId, o.sum, o.changer, o.idType, o.idNo, o.referenceID, o.mobileNo, o.status orderStatus, s.name, s.id storeId, s.max, s.min, s.address, s.opentime,a.name as area,c.name as city,DATE_FORMAT(o.createTime,'%Y-%m-%d %H:%i:%s') createTime " +
			"FROM t_order o, t_stores s, t_areas a, dict_city c " +
			"WHERE o.storeId = s.id and s.city=c.id and s.area=a.id and openId=#{openId} order by createtime desc")
	List<Map<String,String>> getOrderByUser(String openId);


	@Update("update t_order set status=#{status} where id=#{orderId} and openId=#{openId}")
	int updateStatus(@Param("orderId") int orderId,@Param("openId") String openId,@Param("status") int status);

	@SelectProvider(type=OrderSQL.class,method = "queryByPage")
	List<Map<String,String>> queryOrderByPage(Map<String,String> map);


	@SelectProvider(type=OrderSQL.class,method = "queryCount")
	int queryCount(Map<String,String> map);

	public class OrderSQL {

		public String queryByPage(final Map params){
			SQL sql = getSql(params);

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

		public String queryCount(final Map params){
			SQL sql=getSql(params);
			StringBuilder sb=new StringBuilder("select count(1) from (");
			sb.append(sql.toString());
			sb.append(") a");
			return sb.toString();
		}

		private SQL getSql(final Map params) {
			return new SQL(){
                        {
                            SELECT("o.id, openId, buy, sell, takeDate, cityId, sum, changer, idType, idNo, ifnull(referenceID,'') referenceID, ifnull(mobileNo,'') mobileNo, storeId, rate, o.status, DATE_FORMAT(o.createTime,'%Y-%m-%d %H:%i:%s') createTime, DATE_FORMAT(o.updateTime,'%Y-%m-%d %H:%i:%s') updateTime, st.name as store, c.name as city, sp.name suppler, ifnull(ch.name,'') as channel");
                            FROM(" t_order o ");
                            LEFT_OUTER_JOIN("dict_city c ON o.cityId = c.id");
                            LEFT_OUTER_JOIN("t_channels ch ON ch.code = o.referenceID");
                            LEFT_OUTER_JOIN("t_stores st ON o.storeId = st.id");
                            LEFT_OUTER_JOIN("t_suppliers sp ON st.s_id = sp.id");

                            //通过条件 判断是否需要更新该字段
                            if (!StringUtils.isEmpty(params.getOrDefault("buy",""))){
                                WHERE("o.buy = #{buy}");
                            }
                            if (!StringUtils.isEmpty(params.getOrDefault("sell",""))){
                                WHERE("o.sell = #{sell}");
                            }
                            if (!StringUtils.isEmpty(params.getOrDefault("takeDate",""))){
                                WHERE("o.takeDate = #{takeDate}");
                            }
                            if (!StringUtils.isEmpty(params.getOrDefault("cityId",""))){
                                WHERE("o.cityId = #{cityId}");
                            }
                            if (!StringUtils.isEmpty(params.getOrDefault("changer",""))){
                                WHERE("changer like CONCAT('%',#{changer},'%' )");
                            }
                            if (!StringUtils.isEmpty(params.getOrDefault("idNo",""))){
                                WHERE("IDNO like  CONCAT('%',#{idNo},'%' )");
                            }
                            if (!StringUtils.isEmpty(params.getOrDefault("referenceID",""))){
                                WHERE("o.referenceID = #{referenceID}");
                            }
                            if (!StringUtils.isEmpty(params.getOrDefault("mobileNo",""))){
                                WHERE("o.mobileNo = #{mobileNo}");
                            }
                            if (!StringUtils.isEmpty(params.getOrDefault("storeId",""))){
                                WHERE("o.storeId = #{storeId}");
                            }
                            if (!StringUtils.isEmpty(params.getOrDefault("createTime",""))){
                                WHERE("DATE_FORMAT(o.createTime,'%Y-%m-%d') = #{createTime}");
                            }
                            if(!StringUtils.isEmpty(params.getOrDefault("status",""))){
                                WHERE("o.status = #{status}");
                            }
                            if(!StringUtils.isEmpty(params.getOrDefault("supplierId",""))){
                                WHERE("sp.id = #{supplierId}");
                            }
                            if(!StringUtils.isEmpty(params.getOrDefault("orderId",""))){
                                WHERE("o.id = #{orderId}");
                            }

                            ORDER_BY(" o.createTime desc");
                        }

                    };
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
						if (!StringUtils.isEmpty(order.getMobileNo())){
							SET("mobileNo = #{mobileNo}");
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
