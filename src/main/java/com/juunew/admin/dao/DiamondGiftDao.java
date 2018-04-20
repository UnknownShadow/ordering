package com.juunew.admin.dao;

import com.juunew.admin.entity.DiamondGiftEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public interface DiamondGiftDao {
	//
	@Select("select * from diamond_gift")
	List<DiamondGiftEntity> findAll();

	//新增充值钻石 送钻石的比例
	@Insert("insert into diamond_gift(user_id,diamond_ratio_id,recharge_diamond,give_diamond,ratio)" +
			"values(#{user_id},#{diamond_ratio_id},#{recharge_diamond},#{give_diamond},#{ratio})")
	void insertToDiamondGift(@Param("user_id") int user_id,@Param("diamond_ratio_id") int diamond_ratio_id,
							 @Param("recharge_diamond") int recharge_diamond,
							 @Param("give_diamond") int give_diamond,@Param("ratio") String ratio);


	//根据user_id查询调整后的赠送钻石数量
	@Select("select * from diamond_gift where user_id=#{user_id} and recharge_diamond=#{diamond} order by id desc limit 1")
	DiamondGiftEntity findAdjustDiamond(@Param("user_id") int user_id,@Param("diamond") int diamond);


	//根据user_id查询调整后的赠送钻石数量
	@Select("select * from diamond_gift where user_id=#{users_id} and recharge_diamond=#{diamond} order by id desc limit 1")
	DiamondGiftEntity findByUserIdAndDiamond(@Param("users_id") int users_id,@Param("diamond") int diamond);

}
