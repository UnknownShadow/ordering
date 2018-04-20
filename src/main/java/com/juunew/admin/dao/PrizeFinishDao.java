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
public interface PrizeFinishDao {

	//将所有数据逐条的插入到prize_finish数据表中
	@Select("INSERT INTO prize_finish(created_at,round,gift_title,get_type,nickname,users_id,name,address,phone,status,confirm_name)VALUES(#{created_at},#{round},#{gift_title},#{get_type},#{nickname},#{users_id},#{name},#{address},#{phone},#{status},#{confirm_name})")
	void insertPrizeFinish(@Param("created_at") String created_at, @Param("round") int round,@Param("gift_title") String gift_title, @Param("get_type") int get_type,@Param("nickname") String nickname, @Param("users_id") int users_id,@Param("name") String name, @Param("address") String address,@Param("phone") String phone, @Param("status") int status, @Param("confirm_name") String confirm_name);
}
