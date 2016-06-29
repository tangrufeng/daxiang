package com.dx.dao;

import com.dx.entity.ManagerBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Tom on 16/6/24.
 */
public interface ManagerDAO {

    @Select("select id,name,password from t_manager m where m.name=#{userName} and m.password=md5(#{password})")
    public ManagerBean login(@Param("userName") String userName,@Param("password") String password);
}
