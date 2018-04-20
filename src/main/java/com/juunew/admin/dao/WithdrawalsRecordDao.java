package com.juunew.admin.dao;

import com.juunew.admin.entity.WithdrawalsRecordEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为 提现记录实体类
 * */
@Repository
public interface WithdrawalsRecordDao {

	//根据user_id 查询是否有提现记录；
	@Select("select * from withdrawals_record where user_id = #{user_id} limit 1")
	WithdrawalsRecordEntity findWithdrawsRecord(@Param("user_id") int user_id);

	//将记录新增到数据表中
	@Insert("insert into withdrawals_record(user_id,wechat_number,order_number,withdrawal_limit,verification_status,phone,total_integral," +
			"withdrawals_cash,created_time)values(#{user_id},#{wechat_number},#{order_number},#{withdrawal_limit},#{verification_status},#{phone},#{total_integral},#{withdrawals_cash},now())")
	void insertToWR(@Param("user_id") int user_id,@Param("wechat_number") String wechat_number,@Param("order_number") String orderNum,
					@Param("withdrawal_limit") int withdrawal_limit,@Param("verification_status") int verification_status,
					@Param("phone") String phone,@Param("total_integral") int total_integral,@Param("withdrawals_cash") int withdrawals_cash);

	//查询total值
	@Select("select count(*) from withdrawals_record order by id desc")
	int getTotal();

	//分页查询数据
	@Select("select * from withdrawals_record where verification_status=#{verification_status} order by id desc limit #{offset},#{limit}")
	List<WithdrawalsRecordEntity> findWRPaging(@Param("offset") int offset,@Param("limit") int limit,@Param("verification_status") int verification_status);

	//分页查询数据所有审核数据（运营）
	@Select("select * from withdrawals_record order by id desc limit #{offset},#{limit}")
	List<WithdrawalsRecordEntity> findWRAllPaging(@Param("offset") int offset,@Param("limit") int limit);


	//根据 表中主键id 修改 审核状态（VerificationStatus）
	@Update("update withdrawals_record set verification_status=#{verification_status} where id=#{id}")
	void updateVerificationStatus(@Param("id") int id,@Param("verification_status") int verification_status);

	//根据主键id 查询积分
	@Select("select * from withdrawals_record where id = #{id}")
	WithdrawalsRecordEntity findIntegralById(@Param("id") int id);


	//根据主键id 查询积分
	@Select("select * from withdrawals_record where verification_status!=1 and verification_status!=0 and user_id=#{user_id} ORDER BY id desc limit 1")
	WithdrawalsRecordEntity queryAutingIntegral(@Param("user_id") int user_id);

	//查询所有返利记录
	@Select("select * from withdrawals_record where user_id=#{user_id} order by id desc limit #{offset},#{limit}")
	List<WithdrawalsRecordEntity> findWithdrawalsRecordPaging(@Param("user_id") int user_id, @Param("offset") int offset, @Param("limit") int limit);


	//根据 表中主键id 更改 为已入账
	@Update("update withdrawals_record set arrival=#{arrival} where id=#{id}")
	void updateArrival(@Param("id") int id,@Param("arrival") int arrival);

	//根据 表中主键id 更改 图片上传后的地址
	@Update("update withdrawals_record set img_url=#{img_url} where id=#{id}")
	void updateImg(@Param("id") int id,@Param("img_url") String img);


	//提现积分
	@Select("select SUM(withdrawal_limit) withdrawal_limit from withdrawals_record where user_id=#{user_id} and verification_status=1")
	WithdrawalsRecordEntity findWithdrawalRebate(@Param("user_id") int user_id);


	//根据user_id查询 当前是否有正在审核的积分提现
	@Select("select * from withdrawals_record where user_id=#{user_id} and verification_status!=1 and verification_status!=0 limit 1")
	WithdrawalsRecordEntity findVerStatusByUserId(@Param("user_id")int user_id);


}
