package com.dx.dao;

import com.dx.entity.StoreRateBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

import static org.apache.ibatis.mapping.SqlCommandType.INSERT;

/**
 * Created by Tom on 16/6/23.
 */
public interface StoreRateDAO {


    @InsertProvider(type = StoreRateSQL.class,method = "insert")
    public int addRateList(@Param("rates") List<StoreRateBean> rates);

    @Delete("delete from t_store_rate where supplierId=#{supplierId}")
    public int removeRate(int supplierId);

    @Insert("Insert into t_store_rate_history select * from t_store_rate rate where rate.supplierId=#{supplierId};")
    public int copeRateToHistory(int supplierId);


    public class StoreRateSQL{
        public String insert(final Map<String,Object> params){
            List<StoreRateBean> rates = (List<StoreRateBean>) params.get("rates");
            StringBuilder sb=new StringBuilder("INSERT INTO t_store_rate(`buy`,`sell`,`rate`,`s_id`,`supplierId`,`createtime`) VALUES");
            for(StoreRateBean bean:rates){
                sb.append("(");
                sb.append("'"+bean.getBuy()+"',");
                sb.append("'"+bean.getSell()+"',");
                sb.append("'"+bean.getRate()+"',");
                sb.append("'"+bean.getsId()+"',");
                sb.append("'"+bean.getSupplierId()+"',");
                sb.append("DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s')),");
            }
            return sb.deleteCharAt(sb.length()-1).toString();
        }
    }

}
