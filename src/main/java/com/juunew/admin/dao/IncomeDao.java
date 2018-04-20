package com.juunew.admin.dao;

import com.juunew.admin.entity.DiamondsEntity;
import com.juunew.admin.entity.IncomeEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为收入的具体支出查询
 * */
@Repository
public interface IncomeDao {
	//将钻石的发送信息记录
	@Select("INSERT into income(users_id,streaming_name,target_name,type,fatherproxy_id,number,send_date,status,username) VALUES (#{user_id},'发送',#{nickname},#{type},#{self_id},#{diamond},#{sysDate},1,#{username})")
	void IncomeUpdate_out( @Param("user_id") int user_id,@Param("nickname") String nickname,@Param("type") String type,@Param("self_id") int self_id,@Param("diamond") int diamond,@Param("sysDate") String sysDate,@Param("username") String username);

	//将钻石的收入信息记录
	@Select("INSERT into income(users_id,streaming_name,target_name,type,fatherproxy_id,number,send_date,status,username) VALUES (#{self_id},'收入',#{nickname},#{type},#{user_id},#{diamond},#{sysDate},1,#{username})")
	void IncomeUpdate_in( @Param("user_id") int user_id,@Param("nickname") String nickname,@Param("type") String type,@Param("self_id") int self_id,@Param("diamond") int diamond,@Param("sysDate") String sysDate,@Param("username") String username);

	//INSERT into chaowan_admin.income(users_id,streaming_name,target_name,type,fatherproxy_id,number,send_date,status) VALUES (#{self_id},'收入',#{nickname},#{type},#{user_id},#{diamond},#{sysDate},1)


	//select * from chaowan_admin.user where username LIKE'%11%' LIMIT 0,3 and fatherproxy_id=#{proxy_id} @Param("proxy_id") int proxy_id,
	//查询子代理收发钻石的数据
	@Select("select * from income where fatherproxy_id=#{self_id} and send_date>=#{proxy_date} and send_date<=#{proxy_date_end} and users_id LIKE \"%${user_id}%\" order by case when 1=#{composit} then users_id end,case when 2=#{composit} then send_date end,case when 3=#{composit} then number end desc,case when 4=#{composit} then number end asc LIMIT #{xx},#{yy}")
	List<IncomeEntity> findIncome( @Param("user_id") String user_id,@Param("proxy_date") String proxy_date,@Param("proxy_date_end") String proxy_date_end,@Param("self_id") int self_id,@Param("composit") int composit,@Param("xx") int xx,@Param("yy") int yy);

	//查询子代理收发钻石的数据;;查询总共有多少条记录，用模糊查询即可；and fatherproxy_id=#{self_id}
	@Select("select * from income where fatherproxy_id=#{self_id} and send_date>=#{proxy_date} and send_date<=#{proxy_date_end} and users_id LIKE \"%${user_id}%\" ")
	List<IncomeEntity> findIncomeTotal( @Param("user_id") String user_id,@Param("proxy_date") String proxy_date,@Param("proxy_date_end") String proxy_date_end,@Param("self_id") int self_id);
	//select * from chaowan_admin.income where users_id LIKE'%${user_id}%' and send_date LIKE'%${send_date}%' and fatherproxy_id=#{self_id} order by #{ordered}


	//查询子代理收发钻石的数据;;查询总共有多少条记录，用模糊查询即可；and fatherproxy_id=#{self_id}
	@Select("select * from income where fatherproxy_id=#{self_id} and users_id LIKE '%${user_id}%'")
	List<IncomeEntity> findIncomeTotalOther( @Param("user_id") String user_id,@Param("self_id") int self_id);


	//查询子代理收发钻石的数据;;查询总共有多少条记录，用模糊查询即可；and fatherproxy_id=#{self_id}
	@Select("select * from income where fatherproxy_id=#{self_id} and users_id LIKE '%${user_id}%' " +
			"order by case when 1=#{composit} then users_id end,case when 2=#{composit} then send_date end desc," +
			"case when 3=#{composit} then number end desc,case when 4=#{composit} then number end asc LIMIT #{offset},#{limit}")
	List<IncomeEntity> findIncomeOther( @Param("user_id") String user_id,@Param("self_id") int self_id,
										@Param("composit") int composit,@Param("offset") int offset,
										@Param("limit") int limit);


/*
	//微信中查询子代理收发钻石的数据;;查询总共有多少条记录，
	@Select("select * from income where fatherproxy_id=#{self_id} and users_id LIKE '%${user_id}%' order by case when 1=#{composit} then users_id end,case when 2=#{composit} then send_date end,case when 3=#{composit} then number end desc,case when 4=#{composit} then number end asc LIMIT #{xx},#{yy}")
	List<IncomeEntity> findIncomeOther( @Param("user_id") String user_id,@Param("self_id") int self_id,@Param("composit") int composit,@Param("xx") int xx,@Param("yy") int yy);
*/

	//根据users_id查询出此用户发送的出去的所有钻石数量
	//select SUM(number) from chaowan_admin.income where fatherproxy_id=42
	@Select("select SUM(number) from income where streaming_name='发送' and type='钻石' and fatherproxy_id=#{users_id}")
	Integer findProxyNumTotal( @Param("users_id") int users_id);

	//根据users_id和fatherproxy_id查询我为该子代理所发送的所有钻石数量
	@Select("select SUM(number) from income where users_id=#{users_id} and fatherproxy_id=#{fatherproxy_id}")
	Integer findFatherProxyNumTotal( @Param("users_id") int users_id,@Param("fatherproxy_id") int fatherproxy_id);


	//根据子代理ID查询income表中的数据,select SUM(number) from chaowan_admin.income where fatherproxy_id=#{users_id}
	@Select("select * from income where fatherproxy_id=#{users_id} and streaming_name=#{streaming_name}")
	List<IncomeEntity> findIncomeData( @Param("users_id") int users_id, @Param("streaming_name") String streaming_name);

	//	select * from chaowan_admin.income where fatherproxy_id=42 and streaming_name="收入"
	//根据子代理ID查询income表中的数据,select SUM(number) from chaowan_admin.income where fatherproxy_id=#{users_id}
	@Select("select * from income where fatherproxy_id=#{users_id} and streaming_name=#{streaming_name}")
	List<IncomeEntity> findoutcomeData( @Param("users_id") int users_id, @Param("streaming_name") String streaming_name);

	//根据子代理ID查询income表中的数据,有日期的情况下
	@Select("select * from income where fatherproxy_id=#{users_id} and streaming_name=#{streaming_name} and send_date>=#{proxy_date} and send_date<=#{proxy_date_end}")
	List<IncomeEntity> findIncomeData_date( @Param("users_id") int users_id, @Param("streaming_name") String streaming_name, @Param("proxy_date") String proxy_date, @Param("proxy_date_end") String proxy_date_end);

	//根据子代理ID查询income表中的数据,有日期的情况下
	@Select("select * from income where fatherproxy_id=#{users_id} and streaming_name=#{streaming_name} and send_date>=#{proxy_date} and send_date<=#{proxy_date_end}")
	List<IncomeEntity> findoutcomeData_date( @Param("users_id") int users_id, @Param("streaming_name") String streaming_name, @Param("proxy_date") String proxy_date, @Param("proxy_date_end") String proxy_date_end);

	//select fatherproxy_id from chaowan_admin.income where number = (select MAX(number) from chaowan_admin.income where send_date BETWEEN "2017-07-01 00:00:00" and "2017-07-01 23:59:59")

	//根据日期查询所有发送出去的钻石数量，用于财务日报中的当日钻石流动总数的统计
	@Select("select sum(number) number from income where type=#{type} and  streaming_name=#{streaming_name} and send_date between #{start_date} and #{end_date}")
	IncomeEntity findByDateForNumber( @Param("type") String type, @Param("streaming_name") String streaming_name, @Param("start_date") String start_date, @Param("end_date") String end_date);


	//根据user_id 查询 钻石发送，收入记录select  from income where fatherproxy_id=#{userId} or users_id=#{userId}
	@Select("SELECT count(*) from income where (fatherproxy_id = #{userId} and streaming_name='发送') or (fatherproxy_id=#{userId} and streaming_name='收入')")
	int findTotal(@Param("userId") int userId);

	//根据 user_id 分页查询 谁给他发了钻石，或他给谁发了钻石
	@Select("SELECT * from income where (fatherproxy_id = #{userId} and streaming_name='发送') or (fatherproxy_id=#{userId} and streaming_name='收入') " +
			"or (fatherproxy_id=#{userId} and streaming_name='返利') order by send_date desc limit #{offset},#{limit}")
	List<IncomeEntity> findIncomePaging(@Param("userId") int userId,@Param("offset") int offset,@Param("limit") int limit);


	//将钻石返利记录到数据表中
	@Update("INSERT into income(users_id,streaming_name,target_name,`type`,fatherproxy_id,number,send_date,status,username) VALUES (#{self_id},'返利',#{nickname},#{type},#{user_id},#{diamond},now(),1,#{username})")
	void updateIncome( @Param("user_id") int user_id,@Param("nickname") String nickname,@Param("type") String type,@Param("self_id") int self_id,@Param("diamond") int diamond,@Param("username") String username);


}
