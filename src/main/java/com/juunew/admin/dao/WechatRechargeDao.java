package com.juunew.admin.dao;

import com.juunew.admin.entity.UserEntity;
import com.juunew.admin.entity.WechatRechargeEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 此类为权限分类与权限的中间表的实体类
 * */
@Repository
public interface WechatRechargeDao {

	//将下单的数据存入到数据库中
	@Insert("insert into wechat_recharge(user_id,order_number,recharge_number,recharge_money,created_date,data_voucher,payment_status)values(#{user_id},#{order_number},#{recharge_number},#{recharge_money},now(),#{dataVoucher},#{payment_status})")
	void insertToWechatRecharge(@Param("user_id") int user_id, @Param("order_number") String order_number, @Param("recharge_number") int recharge_number, @Param("recharge_money") int recharge_money, @Param("dataVoucher") String dataVoucher, @Param("payment_status") int payment_status);


	//根据订单号查询user_id 和 充值钻石数量；
	@Select("select * from wechat_recharge where order_number = #{out_trade_no}")
	WechatRechargeEntity findByOrderNum(@Param("out_trade_no") String out_trade_no);


	//根据订单号修改支付状态
	@Update("update wechat_recharge set payment_status = #{payment_status},transaction_id=#{transaction_id} where order_number = #{out_trade_no}")
	void updatePaymentStatus(@Param("out_trade_no") String out_trade_no,@Param("payment_status") int payment_status,@Param("transaction_id") String transaction_id);


	//根据openid查询成功的订单
	@Select("select * from wechat_recharge where data_voucher=#{dataVoucher} and payment_status=1")
	WechatRechargeEntity findByOrder(@Param("dataVoucher") String dataVoucher);


	//根据userId 查询 最新成功的订单
	@Select("SELECT * FROM wechat_recharge WHERE user_id=#{userId} and payment_status=1 ORDER BY created_date DESC LIMIT 1")
	WechatRechargeEntity findByUserId(@Param("userId") int userId);


	//将 （app支付） 下单的数据存入到数据库中
	@Insert("insert into wechat_recharge(user_id,order_number,recharge_number,recharge_money,created_date,data_voucher,payment_status,payment_method,products_id)" +
			"values(#{user_id},#{order_number},#{recharge_number},#{recharge_money},now(),#{dataVoucher},#{payment_status},#{payment_method},#{products_id})")
	void toWechatRechargeFromAppPay(@Param("user_id") int user_id, @Param("order_number") String order_number,
									@Param("recharge_number") int recharge_number, @Param("recharge_money") int recharge_money,
									@Param("dataVoucher") String dataVoucher, @Param("payment_status") int payment_status,
									@Param("payment_method") int payment_method,
									@Param("products_id") int products_id);


	//根据时间查询付费笔数
	@Select("select * from wechat_recharge where payment_status =1 and created_date between #{start_time} and #{end_time}")
	List<WechatRechargeEntity> findPayCountByDate(@Param("start_time") String start_time,@Param("end_time") String end_time);


	//根据user_id查询成功的订单
	@Select("select sum(recharge_money)recharge_money from wechat_recharge where payment_status =1 and user_id = #{user_id}")
	WechatRechargeEntity totalRechargeByUserId(@Param("user_id") int user_id);



	//根据时间查询所有付费用户总条数
	@Select("select count(*) from wechat_recharge where payment_status =1 and created_date between #{start_time} and #{end_time}")
	int queryTotalPayByDate(@Param("start_time") String start_time,@Param("end_time") String end_time);

	//根据时间查询所有付费用户
	@Select("select * from wechat_recharge where payment_status =1 and created_date between #{start_time} and #{end_time} limit #{xx},#{yy}")
	List<WechatRechargeEntity> queryPayByDate(@Param("start_time") String start_time,@Param("end_time") String end_time,
											  @Param("xx") int xx,@Param("yy") int yy);


	//根据时间和UserId查询总付费金额
	@Select("select sum(recharge_money)recharge_money from wechat_recharge where payment_status =1 and created_date " +
			"between #{start_time} and #{end_time} and user_id=#{user_id}")
	WechatRechargeEntity queryTotalRechargeMoneyByDate(@Param("start_time") String start_time,@Param("end_time") String end_time,
											 @Param("user_id") int user_id);


	//根据UserId查询总付费金额
	@Select("select sum(recharge_money)recharge_money from wechat_recharge where payment_status =1 and user_id=#{user_id}")
	WechatRechargeEntity totalRechargeMoneyByUserId(@Param("user_id") int user_id);


	//分页查询付费用户
	@Select("select * from wechat_recharge where payment_status = 1 and user_id in(${allSubAgents}) order by created_date desc limit #{offset},#{limit}")
	List<WechatRechargeEntity> queryPaymentUser(@Param("allSubAgents") String allSubAgents, @Param("offset") int offset, @Param("limit") int limit);


	//分页查询付费用户
	@Select("select * from wechat_recharge where payment_status = 1 and created_date between #{start_time} and #{end_time}")
	List<WechatRechargeEntity> queryPaymentByTime(@Param("start_time") String start_time, @Param("end_time") String end_time);


	//查询我所有下级的充值金额和充值钻石数量
	@Select("select sum(recharge_money)recharge_money,sum(recharge_number)recharge_number from wechat_recharge where " +
			"payment_status = 1 and user_id in(${allSubAgent}) and created_date between #{start_time} and #{end_time} limit 1")
	WechatRechargeEntity queryMySubRecharge(@Param("start_time") String start_time, @Param("end_time") String end_time,
											@Param("allSubAgent") String allSubAgent);


	//查询我所有下级的充值金额和充值钻石数量 （重构）
	@Select("select sum(recharge_number)recharge_number,sum(recharge_money)recharge_money,b.couplet_date from " +
			"wechat_recharge a,couplet_date b where a.payment_status = 1 and a.user_id in(${allSubAgent}) and " +
			"TO_DAYS(a.created_date) = TO_DAYS(b.couplet_date) group by b.couplet_date")
	List<WechatRechargeEntity> mySubRecharge(@Param("allSubAgent") String allSubAgent);
}
