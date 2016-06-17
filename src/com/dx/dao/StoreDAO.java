package com.dx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.dx.entity.StoreBean;

public interface StoreDAO {

	@Select("select  s.id, s.s_id, s.name, s.address,"
			+ "s.opentime, s.max, s.min, sr.rate, s.opentime,"
			+ "sp.name as spname, sp.fullname from  t_stores s,"
			+ "t_store_rate sr, dict_city dc, t_suppliers sp"
			+ " where  s.city = #{city} and s.area = #{area}  and sr.buy = #{buy} "
			+ "and sr.sell = #{sell}  and s.ahead >= datediff(#{takeDate}, "
			+ "date_format(now(), '%Y-%m-%d')) and dc.id = s.city "
			+ "and sp.id = s.s_id;")
	@Results({ @Result(id = true, column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "spname", property = "supplier"),
			@Result(column = "address", property = "address"),
			@Result(column = "opentime", property = "openingHours"),
			@Result(column = "max", property = "max"),
			@Result(column = "min", property = "min"),
			@Result(column = "rate", property = "rate"),
			@Result(column = "opentime", property = "name") })
	public List<StoreBean> getStores(@Param("city") String city,
			@Param("area") String area, @Param("buy") String buy,
			@Param("sell") String sell, @Param("takeDate") String takeDate);

}
