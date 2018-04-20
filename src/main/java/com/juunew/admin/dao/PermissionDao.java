package com.juunew.admin.dao;

import com.juunew.admin.entity.PermissionEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为权限实体类
 * */
@Repository
public interface PermissionDao {
	//根据用户名和密码查询登陆
	@Select("(select * from permission where  id in (select permission_id from allot where role_id = (select role_id from user where username = #{username})))")
	List<PermissionEntity> all(@Param("username") String username);

}
