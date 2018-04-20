package com.juunew.admin.dao;

import com.juunew.admin.entity.UserEntity;
import com.juunew.admin.entity.UserSpreadEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为推广者积分返利
 * */
@Repository
public interface UserSpreadDao {

	//数据新增
	@Insert("insert into user_spread(spreader_user_id,downloader_unionid,integral_proportion,created_time)" +
			"values(#{spreader_user_id},#{downloader_unionid},#{integral_proportion},now())")
	void insertToUserSpread(@Param("spreader_user_id") int spreader_user_id,
							@Param("downloader_unionid") String downloader_unionid,
							@Param("integral_proportion") double integral_proportion);

	//删除 扫码者（用户推广）重复的扫码的unionid
	@Delete("DELETE from user_spread where downloader_unionid=#{unionid}")
	void delSameDownloader(@Param("unionid") String unionid);

	//根据unionid 修改数据
	@Update("update user_spread set downloader_user_id = #{userId},spread_status=1,updated_time=now() where downloader_unionid=#{unionid}")
	void updateUserSpread(@Param("unionid") String unionid,@Param("userId")int userId);


	//根据unionid 修改数据
	@Select("select * from user_spread where downloader_unionid=#{unionid} and spread_status=0")
	UserSpreadEntity queryUserSpread(@Param("unionid") String unionid);


	//根据 user_id 查询数据
	@Select("select * from user_spread where downloader_user_id=#{user_id} and spread_status=1 limit 1")
	UserSpreadEntity findByUserId(@Param("user_id") int user_id);


	//根据 user_id 分页查询数据
	@Select("select * from user_spread where spreader_user_id=#{user_id} and spread_status=1 order by updated_time desc limit #{offset},#{limit}")
	List<UserSpreadEntity> findPagingByUserId(@Param("user_id") int user_id, @Param("offset") int offset,@Param("limit") int limit);
}
