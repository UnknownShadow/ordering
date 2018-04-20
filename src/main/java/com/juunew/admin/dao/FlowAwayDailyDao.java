package com.juunew.admin.dao;

import com.juunew.admin.entity.FlowAwayDailyEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FlowAwayDailyDao {

	//数据新增
	@Insert("insert into flow_away_daily(user_id,user_source,recharge_money," +
			"diamond,integral,user_status,created_time,flow_away_time)" +
			"values(#{user_id},#{user_source},#{recharge_money}," +
			"#{diamond},#{integral},#{user_status},now(),#{flow_away_time})")
	void insertToFlowAwayDaily(@Param("user_id")int user_id, @Param("user_source")int user_source,
							   @Param("recharge_money")int recharge_money, @Param("diamond")int diamond,
							   @Param("integral")int integral, @Param("user_status")int user_status,
							   @Param("flow_away_time")String flow_away_time);


	//总条数
	@Select("select count(*) from flow_away_daily where flow_away_time=#{date}")
	int queryTotal(@Param("date") String date);

	//按日期查询数据
	@Select("select * from flow_away_daily where flow_away_time=#{date} limit #{xx},#{yy}")
	List<FlowAwayDailyEntity> findByDate(@Param("date") String date,@Param("xx") int xx,@Param("yy") int yy);



	@Select("select user_id from flow_away_daily")
	List<FlowAwayDailyEntity> findUserId();

	@Insert("insert into flow_away_daily(user_id,user_source,recharge_money," +
			"diamond,integral,user_status,created_time,flow_away_time)values ${sql}")
	void insertTo(@Param("sql")String sql);

}
