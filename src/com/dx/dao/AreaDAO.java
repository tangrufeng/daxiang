package com.dx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.dx.entity.AreaBean;


public interface AreaDAO {
	

	@Select("SELECT id,name FROM dict_city where status=1")
	@Results({
		@Result(id = true, column = "id", property = "id"),
		@Result(column = "name", property = "name")
	})
	List<AreaBean> getCities();
	


	@Select("SELECT id,name FROM t_areas where status=1")
	@Results({
		@Result(id = true, column = "id", property = "id"),
		@Result(column = "name", property = "name")
	})
	List<AreaBean> getAreas();
}
