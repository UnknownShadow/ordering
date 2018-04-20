package com.juunew.admin.dao;

import com.juunew.admin.entity.IntegralRebateEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为 钻石返利实体类
 * */
@Repository
public interface IntegralRebateDao {

	//查询所有返利记录
	@Select("select * from integral_rebate where user_id=#{user_id} order by id desc limit #{offset},#{limit}")
	List<IntegralRebateEntity> findRebatePaging(@Param("user_id") int user_id, @Param("offset") int offset, @Param("limit") int limit);

	//查询所有返利记录
	@Select("select sum(rebate_number) rebate_number from integral_rebate where user_id=#{user_id} ")
	IntegralRebateEntity findTotalRebateNumByUserId(@Param("user_id") int user_id);

	//返利新增
	@Insert("insert into integral_rebate(user_id,child_user_id,recharge_number,rebate_number,created_time,frozen_flag,recharge_money)" +
			"values(#{user_id},#{child_user_id},#{recharge_number},#{rebate_number},now(),#{frozen_flag},#{recharge_money})")
	void insertToRebate(@Param("user_id") int user_id, @Param("child_user_id") int child_user_id,
                        @Param("recharge_number") int recharge_number, @Param("rebate_number") int rebate_number,
						@Param("frozen_flag") int frozen_flag,@Param("recharge_money") int recharge_money);

	//返利新增
	@Insert("insert into integral_rebate(user_id,child_user_id,recharge_number,rebate_number,created_time,frozen_flag,integral_source)" +
			"values(#{user_id},#{child_user_id},#{recharge_number},#{rebate_number},now(),#{frozen_flag},#{integral_source})")
	void insertToRebateTwo(@Param("user_id") int user_id, @Param("child_user_id") int child_user_id,
						   @Param("recharge_number") int recharge_number, @Param("rebate_number") int rebate_number,
						   @Param("frozen_flag") int frozen_flag,
						   @Param("integral_source") int integral_source);



	//查询总记录
	@Select("select count(*) from integral_rebate where user_id like'%${user_id}%'")
	int findTotal(@Param("user_id") String user_id);

	//模糊查询所有返利记录
	@Select("select * from integral_rebate where user_id like'%${user_id}%' order by id desc limit #{offset},#{limit}")
	List<IntegralRebateEntity> findRebatePagingVague(@Param("user_id") String user_id, @Param("offset") int offset, @Param("limit") int limit);
	//模糊查询所有返利记录
	@Select("select * from integral_rebate order by id desc")
	List<IntegralRebateEntity> findRebatePagingAll();



	//查询所有返利记录 的 user_id
	@Select("select distinct user_id from integral_rebate")
	List<IntegralRebateEntity> findAll();

	//查询返利子代理个数
	@Select("select child_user_id from integral_rebate where user_id =#{user_id} group by child_user_id")
	List<IntegralRebateEntity> findRebateChildTotal(@Param("user_id") int user_id);

	//子代理返利笔数
	@Select("select COUNT(*) from integral_rebate where user_id=#{user_id}")
	int findChildTotalCount(@Param("user_id") int user_id);

	//总返利积分
	@Select("select SUM(rebate_number)rebate_number from integral_rebate where user_id=#{user_id}")
	IntegralRebateEntity findTotalRebate(@Param("user_id") int user_id);


	//根据userId查询冻结积分，是否达到7天，达到后解冻，并将积分增加到总可用积分中（user表）
	@Select("select * from integral_rebate where user_id=#{user_id} and frozen_flag=#{flag}")
	List<IntegralRebateEntity> findFrozenIntegralByFlag(@Param("user_id") int user_id,@Param("flag") int flag);

	//根据userID将已解冻积分flag 改为 0
	@Update("update integral_rebate set frozen_flag=#{flag} where user_id=#{user_id} and created_time=#{created_time}")
	void updateFrozenFlag(@Param("user_id") int user_id,@Param("created_time") String created_time,@Param("flag") int flag);


	//根据userId查询冻结总积分，
	@Select("select sum(rebate_number)rebate_number from integral_rebate where user_id=#{user_id} and frozen_flag=#{flag}")
	IntegralRebateEntity findTotalFrozenIntegral(@Param("user_id") int user_id,@Param("flag") int flag);


	//返利新增
	@Insert("insert into integral_rebate(user_id,child_user_id,recharge_number,rebate_number,created_time,updated_time,frozen_flag)" +
			"values(#{user_id},#{child_user_id},#{recharge_number},#{rebate_number},#{created_time},now(),#{frozen_flag})")
	void insertToRebateHours(@Param("user_id") int user_id, @Param("child_user_id") int child_user_id,
							 @Param("recharge_number") int recharge_number, @Param("rebate_number") int rebate_number,
							 @Param("created_time") String created_time,@Param("frozen_flag") int frozen_flag);


	//查询所有冻结积分
	@Select("select * from integral_rebate where user_id=#{user_id} and frozen_flag=1 order by id desc")
	List<IntegralRebateEntity> queryFrozenIntegral(@Param("user_id") int user_id);


	//根据主键ID 查询数据
	@Select("select * from integral_rebate where id=#{id} and updated_time is not null")
	IntegralRebateEntity findById(@Param("id") int id);


	//查询所有子代理返利的积分
	@Select("select * from integral_rebate where user_id=#{user_id} order by created_time desc limit #{offset},#{limit}")
	List<IntegralRebateEntity> queryRebateByUserId(@Param("user_id") int user_id,@Param("offset") int offset,
								   @Param("limit") int limit);


	//查询所有代理特权活动获得的积分
	@Select("select * from integral_rebate where integral_source=#{integral_source} ${condition} order by created_time desc " +
			"limit #{offset},#{limit}")
	List<IntegralRebateEntity> queryByIntegralSource(@Param("integral_source") int integral_source,
													 @Param("offset") int offset, @Param("limit") int limit,
													 @Param("condition") String condition);

	//查询所有代理特权活动获得的积分
	@Select("select count(*) from integral_rebate where integral_source=#{integral_source} ${condition} order by created_time desc")
	int queryTotalCount(@Param("integral_source") int integral_source,@Param("condition") String condition);


	//根据userId查询特权活动获得的总积分
	@Select("select SUM(rebate_number)rebate_number from integral_rebate where integral_source=#{integral_source} and user_id=#{user_id}")
	IntegralRebateEntity queryTotalByIntegralSource(@Param("integral_source") int integral_source, @Param("user_id") int user_id);
}
