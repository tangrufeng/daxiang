package com.dx.dao;

import com.dx.entity.StoreBean;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface StoreDAO {

	@Select("select  LPAD(s.id,5,0) as id, s.s_id, s.name, s.address,"
			+ "s.opentime, s.max, s.min, sr.rate, s.opentime,"
			+ "sp.name as spname, sp.fullname,s.ahead,dc.name as city  from  t_stores s,"
			+ "t_store_rate sr, dict_city dc, t_suppliers sp"
			+ " where  s.city = #{city} and s.area = #{area}  and sr.buy = #{buy} "
			+ "and sr.sell = #{sell}  and s.ahead >= datediff(#{takeDate}, "
			+ "date_format(now(), '%Y-%m-%d')) and dc.id = s.city "
			+ "and sp.id = s.s_id;")
	@Results({ @Result(id = true, column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "spname", property = "supplier"),
			@Result(column = "address", property = "address"),
			@Result(column = "opentime", property = "opentime"),
			@Result(column = "max", property = "max"),
			@Result(column = "min", property = "min"),
			@Result(column = "rate", property = "rate"),
			@Result(column = "city", property = "city"),
			@Result(column = "ahead", property = "ahead")})
	public List<StoreBean> getStores(@Param("city") String city,
			@Param("area") String area, @Param("buy") String buy,
			@Param("sell") String sell, @Param("takeDate") String takeDate);


	@Insert("INSERT INTO t_stores (s_id,status,createtime,updatetime,name,address,opentime,max,min,linkman,phone,city,area,ahead) VALUES (#{sid}, 1, DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'),DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'),#{name},#{address},#{opentime},#{max},#{min},#{linkman},#{phone},#{city},#{area},#{ahead})")
	public int addStore(StoreBean bean);

//	@Update("update t_suppliers set name=#{name},fullname=#{fullname},account=#{account},updatetime=DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s') where id=#{id} ")
//	public int editStore(SupplerBean bean);

	@Select("select LPAD(id,5,0) as id,s_id sid,status,createtime,updatetime,name,address,opentime,max,min,linkman,phone,city,area,ahead from t_stores where s_id=#{sid} order by createtime")
	public List<Map<String,String>> getStoreList(int sid);


	@Select("select count(1) from t_stores where LPAD(id,5,0)=#{id} and s_id=#{sid} and name=#{name}")
	public int isExistStore(@Param("id") String id,@Param("name") String name,@Param("sid") String supplerId);

}
