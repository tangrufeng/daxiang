package com.dx.dao;

import com.dx.entity.AreaBean;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface AreaDAO {
	

	@Select("SELECT id,name FROM dict_city where status=1")
	@Results({
		@Result(id = true, column = "id", property = "id"),
		@Result(column = "name", property = "name")
	})
	List<AreaBean> getCities();


	@Select("SELECT distinct dc.id,dc.name FROM dict_city dc,t_stores s where dc.id=s.city and dc.status=1")
	@Results({
			@Result(id = true, column = "id", property = "id"),
			@Result(column = "name", property = "name")
	})
	List<AreaBean> getUsefullCities();

	@Select("SELECT id,name FROM t_areas where status=1")
	@Results({
		@Result(id = true, column = "id", property = "id"),
		@Result(column = "name", property = "name")
	})
	List<AreaBean> getAreas();
}
