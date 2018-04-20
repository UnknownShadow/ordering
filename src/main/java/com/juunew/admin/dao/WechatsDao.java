package com.juunew.admin.dao;

import com.juunew.admin.entity.UserEntity;
import com.juunew.admin.entity.WechatsEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为微信信息对应实体类
 * */
@Repository
public interface WechatsDao {

	//根据得到的unionid 查询数据库中相对应的 users_id; (wechats表)
	// SELECT * FROM wechats WHERE unionid="oFmRVwsGorunhidXWoZkcqyZmQZE" LIMIT 1

	@Select("select user_id from ${gameDbName}.wechats where unionid = #{unionid} LIMIT 1")
	WechatsEntity findByUnionId(@Param("unionid") String unionid);


	@Select("select unionid from ${gameDbName}.wechats where  user_id = #{user_id} LIMIT 1")
	WechatsEntity findByUserId(@Param("user_id") int user_id);
}
