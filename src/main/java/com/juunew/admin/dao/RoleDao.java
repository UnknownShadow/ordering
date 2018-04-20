package com.juunew.admin.dao;

import com.juunew.admin.entity.RoleEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为权限分类的实体类
 * */
@Repository
public interface RoleDao {
	//根据用户名和密码查询登陆
	@Select("select * from role where id = (select role_id from user where username = #{username})")
	RoleEntity findByRole(@Param("username") String username);


}
