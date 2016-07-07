package com.dx.dao;

import com.dx.entity.ChannelBean;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.List;
import java.util.Map;

/**
 * Created by Tom on 16/6/24.
 */
public interface ChannelDAO {

    @Insert("insert into t_channels (name,linkman,phone,qrcode,status,createtime,updatetime,sId) values(#{name},#{linkman},#{phone},#{qrcode},1,DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'),DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'),#{sId})")
    public int addChannel(ChannelBean bean);

    @Select("select id,name,linkman,phone,qrimage,status,DATE_FORMAT(createTime,'%Y-%m-%d %H:%i:%s') createTime, DATE_FORMAT(updateTime,'%Y-%m-%d %H:%i:%s') updateTime,sId from t_channels")
    @Results({ @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "linkman", property = "linkman"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "qrimage", property = "qrimage",typeHandler = com.dx.utils.BlobTypeHandler.class),
            @Result(column = "status", property = "status"),
            @Result(column = "createTime", property = "createtime"),
            @Result(column = "updateTime", property = "updatetime"),
            @Result(column = "sId", property = "sId")})
    public List<Map<String,String>> getChannels();

    @Update("update t_channels set QRImage=#{image} where QRCode=#{code}")
    public int updateQRImage(@Param("image")String image,@Param("code") String code);


    @Select("select count(1) from t_channels where name =#{name}")
    public int countNameByAdd(@Param("name") String name);

    @Select("select count(1) from t_channels where name =#{name} and id <> #{id}")
    public int countNameByUpdate(@Param("name") String name, @Param("id") String id);
}
