package com.juunew.admin.dao;

import com.juunew.admin.entity.ControlMsgsEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为权限分类与权限的中间表的实体类
 * */
@Repository
public interface ControlMsgsDao {

	//根据用户名和密码查询登陆
	@Select("select * from user where  username = #{username} and password = #{password}")
	List<ControlMsgsEntity> all(@Param("username") String username, @Param("password") String password);



	//将信息存入数据库
	@Select("insert into control_msgs(user_id,system_msgs_id,times,created_at)values(#{user_id},#{system_msgs_id},#{times},now())")
	void insertControlMsgs(@Param("user_id") int user_id, @Param("system_msgs_id") int system_msgs_id, @Param("times") int times);


	//
	@Select("select * from control_msgs where user_id=#{user_id} and system_msgs_id=#{system_msgs_id}")
	ControlMsgsEntity findTimes(@Param("user_id") int user_id,@Param("system_msgs_id") int system_msgs_id);


	//处理times次数
	@Select("update control_msgs set times=times-1 where user_id=#{user_id} and system_msgs_id=#{system_msgs_id} and times>0")
	void updateTimes(@Param("user_id") int user_id,@Param("system_msgs_id") int system_msgs_id);
}
