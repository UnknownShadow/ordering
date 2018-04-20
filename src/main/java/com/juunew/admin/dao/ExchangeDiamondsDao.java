package com.juunew.admin.dao;

import com.juunew.admin.entity.ExchangeDiamondsEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExchangeDiamondsDao {


	@Select("select * from exchange_diamonds where user_id=#{user_id} order by created_time desc")
	List<ExchangeDiamondsEntity> findByUserId(@Param("user_id") int user_id);


	//数据新增
	@Insert("insert into exchange_diamonds(user_id,integral_product_id,consume_integral,diamond,created_time)" +
			"values(#{user_id},#{integral_product_id},#{consume_integral},#{diamond},now())")
	void insertToExchangeDiamond(@Param("user_id") int user_id,@Param("integral_product_id") int integral_product_id,
								 @Param("consume_integral") int consume_integral,@Param("diamond") int diamond);


}
