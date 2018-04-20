package com.juunew.admin.dao;

import com.juunew.admin.entity.DiamondsEntity;
import com.juunew.admin.entity.GameUserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 此类为权限分类与权限的中间表的实体类
 * */
@Repository
public interface DiamondsDao {

	//定时从diamonds表中获取相应的数据select   from diamonds where (change_type=300 or change_type=100) and change_val>0 and created_at between "2017-01-26 20:44:06" and "2017-09-26 20:44:06"
	@Select("select sum(change_val) diamond from ${gameDbName}.diamonds where (change_type=301 or change_type=100 or change_type=302) and change_val>0 and created_at between #{start_date} and #{end_date}")
	DiamondsEntity findDiamondRecharge(@Param("start_date") String start_date, @Param("end_date") String end_date);



	//定时从orders表中获取相应的数据
	@Select("select sum(price) price from ${gameDbName}.orders where pay_type=#{pay_type} and created_at between #{start_date} and #{end_date}")
	DiamondsEntity findRecharge(@Param("pay_type") int pay_type, @Param("start_date") String start_date, @Param("end_date") String end_date);


//-----------------------------=--------------------------------//

	//定时从diamond表中获取相应的数据   统计开房消耗  钻石数量
	@Select("select SUM(change_val)room_consume from ${gameDbName}.diamonds where change_type=200 and created_at BETWEEN #{start_date} and #{end_date}")
	DiamondsEntity findRoomConsume(@Param("start_date") String start_date, @Param("end_date") String end_date);

	//定时从diamond表中获取相应的数据   统计开宝箱消耗  钻石数量
	@Select("select SUM(change_val)treasure_consume from ${gameDbName}.diamonds where change_type=201 and created_at BETWEEN #{start_date} and #{end_date}")
	DiamondsEntity findTreasureConsume(@Param("start_date") String start_date, @Param("end_date") String end_date);

	//定时从diamond表中获取相应的数据   统计参赛消耗   钻石数量
	@Select("select SUM(change_val)competition_consume from ${gameDbName}.diamonds where change_type=202 and created_at BETWEEN #{start_date} and #{end_date}")
	DiamondsEntity findCompetitionConsume(@Param("start_date") String start_date, @Param("end_date") String end_date);


//------------------------------------------------------------------------//


	//统计神秘宝箱开启消耗 和开启次数
	@Select("select count(*) count,SUM(diamond) consume from ${gameDbName}.treasures where type=#{type} and status>=3 and created_at BETWEEN #{start_date} and #{end_date}")
	DiamondsEntity findMysteryTreasuresConsume(@Param("type") int type,@Param("start_date") String start_date, @Param("end_date") String end_date);

//------------------------------------------------------------------------//

	//统计上游报名次数
	@Select("select count(*)thirteen_count from ${gameDbName}.competition_signs where created_at BETWEEN #{start_date} and #{end_date} and status>=3")
	DiamondsEntity findThirteenCount(@Param("start_date") String start_date, @Param("end_date") String end_date);





//----------------将数据插入orders表中------------------/
	//将充值数量与金额存入数据库中  (orders表)
	@Select("insert into ${gameDbName}.orders(product_id,user_id,price,pay_type,`desc`,created_at)values(#{product_id},#{user_id},#{price},#{pay_type},#{desc},#{created_at})")
	void insertToOrders(@Param("product_id") int product_id, @Param("user_id") int user_id, @Param("price") int price, @Param("pay_type") int pay_type, @Param("desc") String desc, @Param("created_at") String created_at);




	//如果是代理；查询该用户所有充值的钻石数量
	@Select("select SUM(change_val) diamond from ${gameDbName}.diamonds where user_id=#{user_id} and (change_type=302 or change_type=300 or  change_type=100 or  change_type=301) and change_val>0")
	DiamondsEntity findByRecharge(@Param("user_id") int user_id);

	//如果是代理；查询该用户所有充值的钻石数量
	@Select("select SUM(price) price from ${gameDbName}.orders where user_id =#{id}")
	DiamondsEntity findByOrdersPrice(@Param("id") int id);


	////按月查询总充值钱数
	//
	@Select("select year(created_at) years,month(created_at) months,SUM(price)price from ${gameDbName}.orders where user_id = #{id} group by year(created_at),month(created_at)")
	List <DiamondsEntity> findByOrdersMonthPrice(@Param("id") int id);


	//select sum(price) price from orders where pay_type=1 or pay_type=2 or pay_type=3
	@Select("select sum(price) price from ${gameDbName}.orders where user_id=#{userId} and (pay_type=1 or pay_type=2 or pay_type=3)")
	DiamondsEntity findPayTotal(@Param("userId") int userId);

	//查询（orders）表 充值玩家信息
	@Select("select * from ${gameDbName}.orders where (pay_type=1 or pay_type=2 or pay_type=3) and created_at BETWEEN #{start_date} and #{end_date} and user_id like '%${user_id}%'")
	List<DiamondsEntity> findOrders(Map map);

	//分页查询（orders）表  充值玩家信息
	@Select("select * from ${gameDbName}.orders where (pay_type=1 or pay_type=2 or pay_type=3) and user_id!=33 and user_id!=781 and " +
			"created_at BETWEEN #{start_date} and #{end_date} and user_id like '%${user_id}%' ${order} limit #{xx},#{yy}")
	List<DiamondsEntity> findOrdersPaging(Map map);

	//查询product 钻石数
	@Select("select product_num diamond from ${gameDbName}.products where id=#{product_id}")
	DiamondsEntity findForProducts(@Param("product_id") int product_id);

	//查询product 钻石数
	@Select("select diamond from products where id=#{product_id}")
	DiamondsEntity findForProductsOther(@Param("product_id") int product_id);


	//根据user_id查询 昵称
	@Select("select nickname from ${gameDbName}.users where id=#{userId}")
	GameUserEntity findNickname(@Param("userId") int userId);


	//查询（orders）表 当日充值人数
	@Select("select distinct user_id from ${gameDbName}.orders where (pay_type=1 or pay_type=2 or pay_type=3) and created_at BETWEEN #{start_date} and #{end_date}")
	List<DiamondsEntity> findPayNumByDate(@Param("start_date") String start_date,@Param("end_date") String end_date);

	//查询（orders）表 当日充值笔数
	@Select("select * from ${gameDbName}.orders where (pay_type=1 or pay_type=2 or pay_type=3) and created_at BETWEEN #{start_date} and #{end_date}")
	List<DiamondsEntity> findPayCountByDate(@Param("start_date") String start_date,@Param("end_date") String end_date);

	//查询（orders）表 当日充值总金额
	@Select("select SUM(price) price from ${gameDbName}.orders where (pay_type=1 or pay_type=2 or pay_type=3) and user_id!=781 and user_id!=33 and created_at BETWEEN #{start_date} and #{end_date}")
	DiamondsEntity findPayTotalByDate(@Param("start_date") String start_date,@Param("end_date") String end_date);



	@Select("select * from ${gameDbName}.diamonds where created_at BETWEEN #{start_date} and #{end_date} and change_type=107")
	List<DiamondsEntity> findPurposeByDate(@Param("start_date") String date_start,@Param("end_date") String end_date);


	//消息总产出钻石
	@Select("select sum(change_val) change_val from ${gameDbName}.diamonds where change_type=#{change_type} and change_val>0 and created_at BETWEEN #{date_start} and #{date_end}")
	DiamondsEntity totalOutput(@Param("change_type") int change_type,@Param("date_start") String date_start, @Param("date_end") String date_end);


	//查询七天内是否有充值记录
	@Select("select sum(price) price from ${gameDbName}.orders where user_id=#{userId} and (pay_type=1 or pay_type=2 or pay_type=3) " +
			"and created_at between #{sevenDaysAgo} and #{createdTime}")
	DiamondsEntity withinSevenDaysRecharge(@Param("userId") int userId,@Param("sevenDaysAgo") String sevenDaysAgo,
										   @Param("createdTime") String createdTime);


}
