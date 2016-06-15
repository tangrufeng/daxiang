package com.dx.dao;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.dx.entity.Area;


public interface AreaDAO {
	@Select("SELECT id,name FROM dict_province")
	@Results({
		@Result(id = true, column = "id", property = "id"),
		@Result(column = "name", property = "name")
	})
	List<Area> getProvice();
}
