package com.dx.dao;

import com.dx.entity.SupplerBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by Tom on 16/6/22.
 */
public interface SupplerDAO {

    @Insert("INSERT INTO t_suppliers (name,status,createtime,updatetime,fullname,account,password) VALUES (#{name}, #{status}, DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'),DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'),#{fullname},#{account},md5('#{password}'))")
    public int addSuppler(SupplerBean bean);

    @Update("update t_suppliers set name=#{name},fullname=#{fullname},account=#{account},updatetime=DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s') where id=#{id} ")
    public int editSuppler(SupplerBean bean);

    @Update("update t_suppliers set password=md5(#{newPassword}),updatetime=DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s') where id=#{id} and password=md5(#{oldPassword})")
    public int resetPWD(@Param("id") int id,@Param("oldPassword") String oldPassword,@Param("newPassword") String newPassword);

    @Select("select sp.id,sp.name,sp.status,sp.createtime,sp.fullname,sp.account,count(st.id) storeCnt from t_suppliers sp left join t_stores st on sp.id=st.s_id group by sp.id,sp.name,sp.status,sp.createtime,sp.fullname,sp.account order by id desc")
    public List<Map<String,String>> getSupplerList();

    @Select("select sp.id,sp.name,sp.status,sp.createtime,sp.fullname,sp.account from t_suppliers sp where sp.account = #{userName} and sp.password=md5(#{password})")
    public SupplerBean login(@Param("userName") String userName,@Param("password") String password);

    @Select("select count(1) from t_suppliers where account =#{account}")
    public int countAccountByAdd(@Param("account") String account);

    @Select("select count(1) from t_suppliers where account =#{account} and id <> #{id}")
    public int countAccountByUpdate(@Param("account") String account, @Param("id") String id);
}
