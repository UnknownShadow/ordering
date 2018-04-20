package com.juunew.admin.dao;

import com.juunew.admin.entity.UserEntity;
import com.juunew.admin.entity.WechatInfoEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 此类
 * */
@Repository
public interface WechatInfoDao {

	//记录到数据库中
	@Insert("insert into wechat_info(user_id,openid,unionid,headimgurl,created_date)values(#{user_id},#{openid},#{unionid},#{headimgurl},now())")
	void insertWechatInfo(@Param("user_id") int user_id,@Param("openid") String openid, @Param("unionid") String unionid,@Param("headimgurl") String headimgurl);


	//根据条件查询数据
	@Select("select * from wechat_info where user_id=#{user_id} order by id desc limit 1")
	WechatInfoEntity findByData(@Param("user_id") int user_id);

	//根据条件
	@Update("update wechat_info set flag=2 where user_id=#{user_id}")
	void updateFlag(@Param("user_id") int user_id);

	//根据条件
	@Update("update wechat_info set flag=1 where user_id=#{user_id}")
	void updateFlagOther(@Param("user_id") int user_id);


	@Delete("delete from wechat_info where unionid = #{unionid}")
	void deleteAllByUnionId(@Param("unionid") String unionid);

	//根据条件查询数据
	@Select("select * from wechat_info where unionid=#{unionid} order by id desc limit 1")
	WechatInfoEntity findByUnionid(@Param("unionid") String unionid);
}
