package com.juunew.admin.dao;

import com.juunew.admin.entity.AuthenticationEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AuthenticationDao {

	//数据新增
	@Insert("insert into authentication(user_id,name,id_card,created_time)values(#{user_id},#{name},#{id_card},now())")
	void insertToAuthentication(@Param("user_id") int user_id,@Param("name") String name,@Param("id_card") String id_card);

	//先查询是否已经认证过了
	@Select("select id from authentication where user_id = #{user_id} limit 1")
	AuthenticationEntity queryByUserId(@Param("user_id") int user_id);

	//查询总条数
	@Select("select count(*) from authentication")
	int totalCount();

	//分页查询
	@Select("select * from authentication order by created_time desc limit #{offset},#{limit}")
	List<AuthenticationEntity> queryByPaging(@Param("offset") int offset,@Param("limit")int limit);

}
