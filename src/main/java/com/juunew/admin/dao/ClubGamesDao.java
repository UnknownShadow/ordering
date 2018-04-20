package com.juunew.admin.dao;

import com.juunew.admin.entity.ClubGamesEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClubGamesDao {

	//根据UserId查询当天进度
	@Select("select count(*) from ${gameDbName}.club_games cg,${gameDbName}.games g ,${gameDbName}.clubs c " +
			"where cg.game_id = g.id and g.finished = 1 and c.creator_id = #{user_id} and c.id = cg.club_id " +
			"and Date(g.end_at) = #{time}")
	int queryProgressByUserId(@Param("user_id")int user_id,@Param("time")String time);


	//根据俱乐部id查询游戏id
	@Select("select game_id from ${gameDbName}.club_games where club_id = #{club_id} limit 1")
	ClubGamesEntity queryGameIdByClubId(@Param("club_id") int club_id);

	//根据游戏id查询俱乐部id
	@Select("select club_id from ${gameDbName}.club_games where game_id = #{game_id} limit 1")
	ClubGamesEntity queryClubIdByGameId(@Param("game_id") int game_id);

}
