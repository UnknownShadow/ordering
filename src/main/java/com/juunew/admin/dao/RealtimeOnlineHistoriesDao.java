package com.juunew.admin.dao;

import com.juunew.admin.entity.RealtimeOnlineHistoriesEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  在线用户与活跃用户类
 * */
@Repository
public interface RealtimeOnlineHistoriesDao {

    //查询当日活跃人数
    @Select("select count(distinct user_id) from ${gameDbName}.realtime_online_histories  join ${gameDbName}.users on users.id = realtime_online_histories.user_id where " +
            " realtime_online_histories.created_at between #{start_date} and #{end_date}")
    int countActivity(@Param("start_date") String start_date,@Param("end_date") String end_date);

    @Select("select count(distinct user_id) from ${gameDbName}.realtime_online_histories  join ${gameDbName}.users on users.id = realtime_online_histories.user_id where " +
            " realtime_online_histories.created_at between #{start_date} and #{end_date} and (clientinfo->'$.device_type'=3 or clientinfo->'$.device_type'=1 or clientinfo->'$.device_type'=4 or clientinfo->'$.device_type'=5)")
    int countActivityByIos(@Param("start_date") String start_date,@Param("end_date") String end_date);

    @Select("select count(distinct user_id) from ${gameDbName}.realtime_online_histories  join ${gameDbName}.users on users.id = realtime_online_histories.user_id where " +
            " realtime_online_histories.created_at between #{start_date} and #{end_date} and (clientinfo->'$.device_type'=2 or clientinfo->'$.device_type' is null) ")
    int countActivityByAndroid(@Param("start_date") String start_date,@Param("end_date") String end_date);


    //查询 三天 活跃用户
    @Select("select count(DISTINCT user_id) from ${gameDbName}.realtime_online_histories where created_at BETWEEN #{start_date} and #{end_date}  " +
            "and user_id in (select user_id from ${gameDbName}.realtime_online_histories where created_at BETWEEN #{twoDaysAgo_start} and #{twoDaysAgo_end}) " +
            "and user_id in (select user_id from ${gameDbName}.realtime_online_histories where created_at BETWEEN #{threeDaysAgo_start} and #{threeDaysAgo_end}) "
            )
    int  findThreeDaysByDate(@Param("start_date") String start_date,@Param("end_date") String end_date,
                            @Param("twoDaysAgo_start") String twoDaysAgo_start,@Param("twoDaysAgo_end") String twoDaysAgo_end,
                            @Param("threeDaysAgo_start") String threeDaysAgo_start,@Param("threeDaysAgo_end") String threeDaysAgo_end
                            );

    //查询 五天 活跃用户
    @Select("select count(DISTINCT user_id) from ${gameDbName}.realtime_online_histories where created_at BETWEEN #{start_date} and #{end_date}  " +
            "and user_id in (select user_id from ${gameDbName}.realtime_online_histories where created_at BETWEEN #{twoDaysAgo_start} and #{twoDaysAgo_end}) " +
            "and user_id in (select user_id from ${gameDbName}.realtime_online_histories where created_at BETWEEN #{threeDaysAgo_start} and #{threeDaysAgo_end}) " +
            "and user_id in (select user_id from ${gameDbName}.realtime_online_histories where created_at BETWEEN #{fourDaysAgo_start} and #{fourDaysAgo_end}) " +
            "and user_id in (select user_id from ${gameDbName}.realtime_online_histories where created_at BETWEEN #{fiveDaysAgo_start} and #{fiveDaysAgo_end}) ")
    int  findFiveDaysByDate(@Param("start_date") String start_date,@Param("end_date") String end_date,
                            @Param("twoDaysAgo_start") String twoDaysAgo_start,@Param("twoDaysAgo_end") String twoDaysAgo_end,
                            @Param("threeDaysAgo_start") String threeDaysAgo_start,@Param("threeDaysAgo_end") String threeDaysAgo_end,
                            @Param("fourDaysAgo_start") String fourDaysAgo_start,@Param("fourDaysAgo_end") String fourDaysAgo_end,
                            @Param("fiveDaysAgo_start") String fiveDaysAgo_start,@Param("fiveDaysAgo_end") String fiveDaysAgo_end);

    //根据时间查询数据
    @Select("select DISTINCT user_id from ${gameDbName}.realtime_online_histories where created_at BETWEEN #{start_time} and #{end_time}")
    List<RealtimeOnlineHistoriesEntity> findActiveUserByDate(@Param("start_time") String start_time,@Param("end_time") String end_time);

    //根据UserID 查询数据
    @Select("select * from ${gameDbName}.realtime_online_histories where created_at BETWEEN #{start_time} and #{end_time} " +
            "and user_id=#{user_id} order by created_at desc limit 1")
    RealtimeOnlineHistoriesEntity findActiveUserByUserId(@Param("start_time") String start_time,
                                                               @Param("end_time") String end_time,
                                                               @Param("user_id") int user_id);




    //查询 五天 活跃用户
    @Select("select * from ${gameDbName}.realtime_online_histories where created_at BETWEEN #{start_date} and #{end_date}  " +
            "and user_id=#{user_id} limit 1")
    RealtimeOnlineHistoriesEntity  queryTodayOnline(@Param("start_date") String start_date,
                                                                 @Param("end_date") String end_date,
                                                                 @Param("user_id") int user_id);




    //根据日期查询活跃用户
    @Select("select count(distinct user_id) from ${gameDbName}.realtime_online_histories where created_at " +
            "between #{start_date} and #{end_date}")
    int queryOnlineCountByTime(@Param("start_date") String start_date,
                               @Param("end_date") String end_date);

    //根据日期查询活跃用户
    @Select("select distinct user_id from ${gameDbName}.realtime_online_histories where created_at " +
            "between #{start_date} and #{end_date} limit #{offset},#{limit}")
    List<RealtimeOnlineHistoriesEntity>  queryOnlineByTime(@Param("start_date") String start_date,
                                                           @Param("end_date") String end_date,
                                                           @Param("offset") int offset, @Param("limit") int limit);

    //根据日期查询活跃用户
    @Select("select count(*) from ${gameDbName}.realtime_online_histories where created_at " +
            "between #{start_date} and #{end_date} and user_id=#{user_id}")
    int queryOnlineCountByUserId(@Param("start_date") String start_date,
                          @Param("end_date") String end_date,
                          @Param("user_id") int user_id);


    //查询出七天内在线的用户
    @Select("select DISTINCT user_id from ${gameDbName}.realtime_online_histories where created_at between #{start_time} and #{end_time}")
    List<RealtimeOnlineHistoriesEntity> queryRealtimeUser(@Param("start_time") String start_time,@Param("end_time") String end_time);


    //根据时间和用户ID 查询活跃个数
    @Select("select count(DISTINCT user_id) from ${gameDbName}.realtime_online_histories where user_id in(${allSubAgent}) " +
            "and created_at between #{start_time} and #{end_time}")
    int queryMyActiveUserCount(@Param("start_time") String start_time,@Param("end_time") String end_time,
           @Param("allSubAgent") String allSubAgent);
}
