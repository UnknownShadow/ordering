package com.juunew.admin.dao;

import com.juunew.admin.entity.OperationDailyEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 此类为运营日报实体类
 * */
@Repository
public interface OperationDailyDao {

	//查询所有数据
	@Select("select * from operation_daily order by daily_date desc")
	List<OperationDailyEntity> findAllData();

	//分页查询所有数据
	@Select("SELECT * from operation_daily ${order} limit #{xx},#{yy}")
	List<OperationDailyEntity> findAllDataPaging(Map map);


	//新增数据
	@Insert("insert into operation_daily(daily_date,newly_added_num,activity_num,pay_num,pay_count,pay_total,three_days," +
			"five_days,thirteen_count,fivek_count,fish_count,ios_new_size,android_new_size,ios_activity_size,android_activity_size," +
			"thirteen_club_count,fivek_club_count,mahjong_club_count,mahjong_count)values(" +
			"#{daily_date},#{newly_added_num},#{activity_num},#{pay_num},#{pay_count},#{pay_total},#{three_days},#{five_days}," +
			"#{thirteen_count},#{fivek_count},#{fish_count},#{ios_new_size},#{android_new_size},#{ios_activity_size}," +
			"#{android_activity_size},#{thirteen_club_count},#{fivek_club_count},#{mahjong_club_count},#{mahjong_count})")
	void insertToOperationDaily(@Param("daily_date") String daily_date,@Param("newly_added_num") int newly_added_num,
								@Param("activity_num") int activity_num,@Param("pay_num") int pay_num,
								@Param("pay_count") int pay_count,@Param("pay_total") int pay_total,
								@Param("three_days") int three_days,@Param("five_days") int five_days,
								@Param("thirteen_count") int thirteen_count,@Param("fivek_count") int fivek_count,
								@Param("fish_count") int fish_count, @Param("ios_new_size")int ios_new_size,
								@Param("android_new_size")int android_new_size, @Param("ios_activity_size")int ios_activity_size,
								@Param("android_activity_size")int android_activity_size,@Param("thirteen_club_count")int thirteen_club_count,
								@Param("fivek_club_count")int fivek_club_count, @Param("mahjong_club_count")int mahjong_club_count,
								@Param("mahjong_count")int mahjong_count
								);

	@Delete("delete from operation_daily where daily_date = #{daily_date}")
	void deleteByDate(@Param("daily_date")String date);
}
