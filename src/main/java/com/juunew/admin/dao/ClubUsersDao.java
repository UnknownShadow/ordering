package com.juunew.admin.dao;

import com.juunew.admin.entity.ClubUsersEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为俱乐部成员实体类
 * */
@Repository
public interface ClubUsersDao {


	//根据 俱乐部Code 和 当前登录的用户 user_id查询 该用户在该俱乐部的状态
	@Select("select * from ${gameDbName}.club_users where club_id = (select id from ${gameDbName}.clubs " +
			"where code=#{clubCode}) and user_id=#{user_id}")
	ClubUsersEntity findByCodeAndUserId(@Param("clubCode") String clubCode,@Param("user_id") int user_id);

	//根据俱乐部ID查询该俱乐部所有成员数量select COUNT(*) from club_users where club_id=20 and `status` =1
	@Select("select COUNT(*) from ${gameDbName}.club_users where club_id=#{id} and `status`=1")
	int findAllMembersByClubId(@Param("id") int id);

	//根据俱乐部ID查询该俱乐部所有成员信息
	@Select("select * from ${gameDbName}.club_users where club_id=#{id} order by id desc limit #{offset},#{limit}")
	List<ClubUsersEntity> findAllMembers(@Param("id") int id,@Param("offset") int offset,@Param("limit") int limit);

	//根据ID 删除指定数据
	@Delete("delete from ${gameDbName}.club_users where id=#{id}")
	void delById(@Param("id") int id);

}
