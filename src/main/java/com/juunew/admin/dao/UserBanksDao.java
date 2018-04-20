package com.juunew.admin.dao;

import com.juunew.admin.entity.UserBanksEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserBanksDao {

	//根据ID查询用户总钻石数量
	@Select("select * from ${gameDbName}.user_banks where user_id = #{id} limit 1")
	UserBanksEntity queryTotalDiamond(@Param("id") int id);

}
