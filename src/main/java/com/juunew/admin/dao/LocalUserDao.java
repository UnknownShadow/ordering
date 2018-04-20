package com.juunew.admin.dao;

import com.juunew.admin.entity.GameUserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalUserDao {
	//根据游戏用户名id查询数据
	@Select("SELECT users_id,user_status,diamond,username,nickname,money from user a left JOIN ${gameDbName}.users b on a.users_id=b.id where a.username=#{username}")
	List<GameUserEntity> findByUsername(@Param("Username") String Username);

	//根据users_id修改用户登录密码
	@Select("UPDATE user set password=#{password} where users_id=#{users_id}")
	void modifyPassword(@Param("users_id") int users_id,@Param("password") String password);

}
