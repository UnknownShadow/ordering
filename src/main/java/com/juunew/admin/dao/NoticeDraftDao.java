package com.juunew.admin.dao;

import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为权限分类与权限的中间表的实体类
 * */
@Repository
public interface NoticeDraftDao {

	//自定义
	@Select("select * from user where  username = #{username} and password = #{password}")
	List<UserEntity> all(@Param("username") String username, @Param("password") String password);
}
