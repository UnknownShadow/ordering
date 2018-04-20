package com.juunew.admin.dao;

import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface AllotDao {

	//根据用户名和密码查询登陆
	@Select("select * from user where  username = #{username} and password = #{password}")
	List<UserEntity> all(@Param("username") String username, @Param("password") String password);

	//@Transactional
	@Select("update allot set role_id=#{role} where id=#{id}")
	void updateToAllot(@Param("role") int role, @Param("id") int id);

}
