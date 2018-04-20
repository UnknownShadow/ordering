package com.juunew.admin.dao;

import com.juunew.admin.entity.ControlTimesEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为权限分类与权限的中间表的实体类
 * */
@Repository
public interface ControlTimesDao {

	//将公告信息存入数据库
	@Select("insert into control_times(user_id,announcements_id,times)values(#{user_id},#{announcements_id},#{times})")
	void insertControlTimes(@Param("user_id") int user_id, @Param("announcements_id") int announcements_id, @Param("times") int times);


	//
	@Select("select * from control_times where user_id=#{user_id}")
	List<ControlTimesEntity> findByTimes(@Param("user_id") int user_id);


	//处理times次数
	@Select("update control_times set times=times-1 where user_id=#{user_id} and times>0")
	void updateByTimes(@Param("user_id") int user_id);





}
