package com.juunew.admin.dao;

import com.juunew.admin.entity.ClubsActionEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 俱乐部活动类
 * */
@Repository
public interface ClubsActionDao {

	//
	@Delete("delete from clubs_action where created_at between #{start_time} and #{end_time}")
	void delByCreateTime(@Param("start_time") String start_time,@Param("end_time") String end_time);


	//新增满足俱乐部活动条件数据
	@Insert("insert into clubs_action(club_id,club_code,creator_id,`level`,reward_integral,audit,created_at)" +
			"values(#{club_id},#{club_code},#{creator_id},#{level},#{reward_integral},#{audit},now())")
	void insertToClubsAction(@Param("club_id") int club_id,@Param("club_code") String club_code,
							 @Param("creator_id") int creator_id, @Param("level") int level,
							 @Param("reward_integral") int reward_integral, @Param("audit") int audit);


	//查询总条数
	@Select("select COUNT(*) from clubs_action")
	int queryTotal();

	//分页查询
	@Select("select * from clubs_action limit #{offset},#{limit}")
	List<ClubsActionEntity> findPaging(@Param("offset") int offset,@Param("limit") int limit);


	//根据创建者ID 查询最高活动 获得的积分值
	@Select("select * from clubs_action where creator_id = #{user_id} ORDER BY reward_integral desc limit 1")
	ClubsActionEntity queryHighestRewardIntegral(@Param("user_id") int user_id);

	//查询出所有 满足 俱乐部活动条件的 创建者ID（去重）
	@Select("select DISTINCT(creator_id) from clubs_action")
	List<ClubsActionEntity> queryCreatorId();

	//修改审核状态
	@Update("update clubs_action set audit=#{status} where id=#{id}")
	void changeAudit(@Param("status") int status,@Param("id") int id);


	//根据ID 查询
	@Select("select * from clubs_action where id = #{id}")
	ClubsActionEntity findById(@Param("id") int id);


	//字段二：当日购买了第二次，算当日的活跃用户，
	@Select("select count(DISTINCT user_id) from  half_price where flag =1 and created_at BETWEEN #{start_date} and #{end_date}")
	int queryHalfPriceActive(@Param("start_date") String start_date,@Param("end_date") String end_date);

	//当日的第二次半价总支付成功次数
	@Select("select sum(second_total_times)second_total_times from half_price")
	ClubsActionEntity querySecTotalTimes(@Param("start_date") String start_date,@Param("end_date") String end_date);


	//当日第二次充值的总金额
	@Select("select sum(second_total_money)second_total_money from half_price")
	ClubsActionEntity querySecTotalMoney(@Param("start_date") String start_date,@Param("end_date") String end_date);


	//更新表
	@Update("update clubs_action set club_id=#{club_id},club_code=#{club_code},creator_id=#{creator_id}," +
			"`level`=#{level},reward_integral=#{reward_integral},updated_at=NOW() where id = #{id}")
	void updateClubsAction(@Param("id") int id,@Param("club_id") int club_id,
						   @Param("club_code") String club_code,@Param("creator_id") int creator_id,
						   @Param("level") int level,@Param("reward_integral") int reward_integral);


}
