package com.dx.dao;

import com.dx.entity.OrderBean;
import com.dx.entity.UserBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * Created by Tom on 16/6/28.
 */
public interface UserDAO {

    @Insert("INSERT INTO t_users(openId,source,createtime,updatetime,event) VALUES (#{openId},#{source},DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'),DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'),#{event})")
    public int insert(UserBean userBean);


    @UpdateProvider(type = UserSQL.class, method = "update")
    public int update(UserBean userBean);


    public class UserSQL {

        public String update(final UserBean bean) {

            return new SQL() {
                {
                    UPDATE("t_users");

                    if (!StringUtils.isEmpty(bean.getEvent())) {
                        SET("event = #{event}");
                    }
                    SET("updateTime = DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s') ");

                    WHERE("openId = #{openId}");

                }
            }.toString();
        }
    }
}
