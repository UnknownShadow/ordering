package com.juunew.admin.dao;

import com.juunew.admin.entity.UserEntity;
import com.juunew.admin.entity.WithdrawsEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为积分提现实体类
 * */
@Repository
public interface WithdrawsDao {

	//积分提取申请入库
	@Insert("insert into withdraws(user_id,score,amount,created_at, trade_no, openid)" +
			"values(#{user_id},#{score},#{amount},now(), #{trade_no}, #{openid})")
	void insertToWithdraws(@Param("user_id") int user_id,@Param("score") int score,
						   @Param("amount") int amount, @Param("trade_no")String trade_no,
						   @Param("openid")String openId);


	//根据user_id查询数据
	@Select("select * from withdraws where user_id=#{user_id} order by created_at desc limit #{offset},#{limit}")
	List<WithdrawsEntity> findByUserId(@Param("user_id") int user_id,@Param("offset") int offset,@Param("limit") int limit);


	//查询是否有审核中的数据
	@Select("select * from withdraws where user_id=#{user_id} and audit=2 and pay_status=0 order by id desc limit 1")
	WithdrawsEntity findAuditByUserId(@Param("user_id") int user_id);

	//打款成功后入库
	@Update("update withdraws set trade_no=#{trade_no},pay_status=#{pay_status},pay_desc=#{pay_desc}," +
			"pay_time=now() where id=#{id}")
	void updateWithdraws(@Param("id")int id,@Param("trade_no")String trade_no,
						 @Param("pay_desc")String pay_desc,
						 @Param("pay_status")int pay_status);


	//查询是否有审核中的数据
	@Select("select * from withdraws where user_id=#{user_id} and (pay_status = 0 or (audit = 2 and pay_status=0)) limit 1")
	WithdrawsEntity findAuditInfo(@Param("user_id") int user_id);

	@Select("select * from withdraws where user_id = #{userId} order by id desc limit 1")
	WithdrawsEntity getLastWithDraw(@Param("userId") int user_id);

	@Select("select * from withdraws where id = #{id}")
	WithdrawsEntity find(@Param("id") int withDrawId);

	//修改审核状态
	@Update("update withdraws set audit = #{audit} where user_id=#{user_id} and trade_no=#{trade_no}")
	void updateAudit(@Param("user_id") int user_id,@Param("audit") int audit,@Param("trade_no") String trade_no);

	//修改流程审核状态
	@Update("update withdraws set process_audit = #{process_audit} where user_id=#{user_id} and trade_no=#{trade_no}")
	void updateProcessAudit(@Param("user_id") int user_id,@Param("process_audit") int process_audit,
							@Param("trade_no") String trade_no);


	//根据user_id查询 最新拒绝的提现交易
	@Select("select * from withdraws where user_id=#{user_id} and audit=#{audit} and trade_no=#{trade_no} order by created_at desc limit 1")
	WithdrawsEntity findRefuseWithdraws(@Param("user_id") int user_id,@Param("audit") int audit,
										@Param("trade_no") String trade_no);


	//查询total值
	@Select("select count(*) from withdraws order by id desc")
	int getTotal();

	//分页查询数据所有审核数据（运营）
	@Select("select * from withdraws order by id desc limit #{offset},#{limit}")
	List<WithdrawsEntity> findWRAllPaging(@Param("offset") int offset,@Param("limit") int limit);


	//查询正在审核的数据
	@Select("select * from withdraws where user_id=#{user_id} and audit=#{audit} order by created_at desc limit 1")
	WithdrawsEntity findAuditInfor(@Param("user_id") int user_id,@Param("audit") int audit);



	//根据user_id查询 审核通过或拒绝的数据
	@Select("select * from withdraws where user_id=#{user_id} and audit !=1 and red_dot!=0 order by created_at desc")
	List<WithdrawsEntity> queryWithdraws(@Param("user_id") int user_id);


	//修改小红点状态
	@Update("update withdraws set red_dot=#{red_dot} where user_id=#{user_id} and trade_no = #{trade_no}")
	void updateRedDot(@Param("red_dot") int red_dot,@Param("user_id") int user_id,@Param("trade_no") String trade_no);


	//查询正在审核的数据
	@Select("select * from withdraws where user_id=#{user_id} and audit!=#{audit} and red_dot=1 ORDER BY id desc limit 1")
	WithdrawsEntity queryAuditStatus(@Param("user_id") int user_id,@Param("audit") int audit);


	//查询审核通过的数据
	@Select("select sum(score)score from withdraws where user_id=#{user_id} and audit=#{audit} and pay_status=0")
	WithdrawsEntity queryAuditPass(@Param("user_id") int user_id,@Param("audit") int audit);


	//查询审核通过的数据
	@Select("select * from withdraws where user_id=#{user_id} and audit=#{audit} and pay_status=0")
	List<WithdrawsEntity> queryAuditPassByUserId(@Param("user_id") int user_id,@Param("audit") int audit);


}
