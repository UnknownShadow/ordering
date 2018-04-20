package com.juunew.admin.dao;

import com.juunew.admin.entity.HalfPriceEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类半价活动实体类
 * */
@Repository
public interface HalfPriceDao {

	//查询活动状态，是否半价
	@Select("select * from half_price where user_id=#{user_id} and products_id=#{products_id}")
	HalfPriceEntity queryActionStatus(@Param("user_id") int user_id,@Param("products_id") int products_id);


	//新增关联数据
	@Insert("insert into half_price(user_id,products_id,created_at)" +
			"values(#{user_id},#{products_id},now())")
	void insertToHalfPrice(@Param("user_id") int user_id,@Param("products_id") int products_id);


	//修改 活动状态
	@Update("update half_price set `status`= #{status} where user_id=#{user_id} and products_id=#{products_id}")
	void updateHalfPrice(@Param("user_id") int user_id,@Param("products_id") int products_id,
						 @Param("status") int status);

	//修改 活动状态
	@Update("update half_price set flag= #{flag} where user_id=#{user_id} and products_id=#{products_id}")
	void updateFlag(@Param("user_id") int user_id,@Param("products_id") int products_id,
						 @Param("flag") int flag);

	//将第二次充值的金额累加
	@Update("update half_price set second_total_money=second_total_money+#{money} where user_id=#{user_id} and products_id=#{products_id}")
	void updateSecondTotalMoney(@Param("user_id") int user_id,@Param("products_id") int products_id,
					@Param("money") int money);


	//累加第二次充值数量
	@Update("update half_price set second_total_times=second_total_times+1 where user_id=#{user_id} and products_id=#{products_id}")
	void secondTotalTimes(@Param("user_id") int user_id,@Param("products_id") int products_id);


}
