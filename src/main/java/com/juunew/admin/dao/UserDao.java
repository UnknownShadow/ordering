package com.juunew.admin.dao;

import com.juunew.admin.entity.GameUserEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
	//根据用户名和密码查询登陆
	@Select("select * from user where  username = #{username} and password = #{password}")
	List<UserEntity> all(@Param("username") String username,@Param("password") String password);


	//根据用户名查询users表中所对应的ID，users_id
	@Select("select users_id from user where username=#{username}")
	UserEntity findUsersId(@Param("username") String username);


	//根据users_id查询出该登录的用户钻石数量
	@Select("select diamond,money from ${gameDbName}.user_banks where id=#{users_id}")
	GameUserEntity checkDiamond(@Param("users_id") int users_id);


	//根据users_id查询出所有代理ID
	@Select("select nickname,id from ${gameDbName}.users where id in(select users_id from user where fatherproxy_id=#{users_id})")
	List<GameUserEntity> findByProxyId(@Param("users_id") int users_id);


	@Select("select * from user where  username = #{username} and password = #{password} and login_status!=0")
	UserEntity findByUserNameAndPassword(@Param("username") String username, @Param("password") String password);

	//新增
	@Insert("insert into log_check(content,users_id,create_date,ip_address,nickname)values(#{content},#{users_id},now(),#{ip_address},#{nickname})")
	void insertLogCheck(@Param("content") String content,@Param("users_id") int users_id,@Param("ip_address") String ip_address,@Param("nickname") String nickname);
	//查询
	@Select("select * from log_check")
	List<UserEntity> findAll();
	//分页查询
	@Select("select * from log_check limit #{xx},#{yy}")
	List<UserEntity> findAllPaging(@Param("xx") int xx,@Param("yy") int yy);

	//关键词查询
	@Select("select * from keywords_filtration order by id desc")
	List<UserEntity> findKeywords();
	//关键词查询
	@Select("insert into keywords_filtration(keywords)values(#{keywords})")
	void insertKeywords(@Param("keywords") String keywords );
}
