package com.juunew.admin.dao;

import com.juunew.admin.entity.GameConfigsEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * */
@Repository
public interface GameConfigsDao {

	//根据game_kind=99 查询App Store审核的版本号
	@Select("select * from ${gameDbName}.game_configs where game_kind = 99 limit 1")
	GameConfigsEntity findHiddenBuildversByGameKind();


	//根据game_kind 查询数据
	@Select("select * from ${gameDbName}.game_configs where game_kind = #{game_kind} limit 1")
	GameConfigsEntity queryByGameConfigs(@Param("game_kind") int game_kind);
}
