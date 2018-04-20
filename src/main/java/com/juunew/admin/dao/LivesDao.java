package com.juunew.admin.dao;

import com.juunew.admin.entity.LivesEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 */
@Repository
public interface LivesDao {
	//根据日期和clubsID查询开房数量（俱乐部开房）
	@Select("select count(*) from ${gameDbName}.games where lives_id in (select id from ${gameDbName}.lives where club_id=#{id}  " +
			"and created_at BETWEEN #{start_time} and #{end_time}) and finished=1 and sign_cost > 0")
	int findTotal(@Param("id") int id,@Param("start_time") String start_time,@Param("end_time") String end_time);

	/*@Select("select count(*) from ${gameDbName}.lives where club_id = #{id} and created_at BETWEEN #{start_time} and #{end_time}")
	int findTotal(@Param("id") int id,@Param("start_time") String start_time,@Param("end_time") String end_time);
*/

	//根据日期和clubsID查询开房数量
	@Select("select * from ${gameDbName}.lives where club_id = #{id} and created_at BETWEEN #{start_time} and #{end_time}")
	List<LivesEntity> findId(@Param("id") int id, @Param("start_time") String start_time, @Param("end_time") String end_time);

	//根据日期和clubsID查询开房数量
	@Select("select * from ${gameDbName}.lives where id = #{id}")
	LivesEntity findById(@Param("id") int id);

}
