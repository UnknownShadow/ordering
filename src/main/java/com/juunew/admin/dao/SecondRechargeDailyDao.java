package com.juunew.admin.dao;

import com.juunew.admin.entity.SecondRechargeDailyEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 二次充值半价统计类
 * */
@Repository
public interface SecondRechargeDailyDao {

	//数据新增
	@Insert("insert into second_recharge_daily(total,second_total_money,second_total_times,daily_date,created_at)" +
			"values(#{total},#{second_total_money},#{second_total_times},#{daily_date},now())")
	void insertToSecondRecharge(@Param("total")int total,@Param("second_total_money")int second_total_money,
								@Param("second_total_times")int second_total_times,@Param("daily_date")String daily_date);


	@Select("select * from second_recharge_daily where created_at BETWEEN #{start_date} and #{end_date}")
	SecondRechargeDailyEntity queryYesterdayInfo(@Param("start_date") String start_date,@Param("end_date") String end_date);


}
