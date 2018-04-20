package com.juunew.admin.dao;

import com.juunew.admin.entity.GamesEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类
 * */
@Repository
public interface GamesDao {

	//根据liveCode查询competitionId  (lives表)
	@Select("select status from ${gameDbName}.games where lives_id = (select id from ${gameDbName}.lives where code=#{liveCode})")
	GamesEntity findByLiveCode(@Param("liveCode") String liveCode);


	//上游  根据 lives_id 查询 games表，将开房没打解散的排除
	@Select("select count(*) from ${gameDbName}.games where lives_id = #{id} and finished=1 and sign_cost > 0")
	int findThirteenTotalByLivesId(@Param("id") int id);

	//根据liveCode查询competitionId  (lives表)
	@Select("SELECT count(*) FROM ${gameDbName}.games WHERE lives_id = #{id} and finished=1 and sign_cost > 0")
	int findGKTotalByLivesId(@Param("id") int id);

	//根据liveCode查询competitionId  (lives表)
	@Select("SELECT sum(sign_cost)sign_cost FROM ${gameDbName}.games WHERE lives_id = #{id} and finished=1 and sign_cost > 0")
	GamesEntity findByLivesId(@Param("id") int id);



	//根据日期查询开房数据详情
	@Select("select * from ${gameDbName}.games where end_at between #{start_time} and #{end_time} and finished=1 and sign_cost > 0 order by end_at desc limit #{xx},#{yy}")
	List<GamesEntity> queryByTime(@Param("start_time") String start_time,@Param("end_time") String end_time,
								  @Param("xx") int xx,@Param("yy") int yy);

	//根据日期查询开房数据详情
	@Select("select count(*) from ${gameDbName}.games where end_at between #{start_time} and #{end_time} and finished=1 and sign_cost > 0")
	int queryTotalByTime(@Param("start_time") String start_time,@Param("end_time") String end_time);



	//根据日期和UserId查询开房数据详情
	@Select("select * from ${gameDbName}.games where end_at between #{start_time} and #{end_time} " +
			"and anchor_id=#{user_id} and finished=1 and sign_cost > 0")
	List<GamesEntity> totalGamesToday(@Param("start_time") String start_time,@Param("end_time") String end_time,
									   @Param("user_id") int user_id);



	//根据日期和UserId查询常玩的游戏
	@Select("select count(gamekinds_id)times,gamekinds_id from ${gameDbName}.games where " +
			"created_at between #{start_time} and #{end_time} and anchor_id=#{user_id} and finished=1 and sign_cost > 0 group by gamekinds_id")
	List<GamesEntity> queryOftenPlayGames(@Param("start_time") String start_time,@Param("end_time") String end_time,
										  @Param("user_id") int user_id);


	//查询总开房数量，包括私房、俱乐部开房（激K、上游、麻将）
	@Select("SELECT id,anchor_id FROM ${gameDbName}.games WHERE end_at BETWEEN #{start_time} and #{end_time} and finished=1 and sign_cost > 0")
	List<GamesEntity> queryAllOpening(@Param("start_time") String start_time,@Param("end_time") String end_time);


	/*//查询总开房数量，包括私房、俱乐部开房（激K、上游、麻将）
	@Select("SELECT count(*) FROM ${gameDbName}.games WHERE anchor_id in(${users}) AND end_at BETWEEN #{start_time} and #{end_time} " +
			"and finished=1 and id not in(${gamesId})")
	int queryAllOpening(@Param("start_time") String start_time,@Param("end_time") String end_time,
						@Param("users") String users,@Param("gamesId") String gamesId);
*/


	//根据时间查询私房
	@Select("select a.id,a.anchor_id,a.sign_cost from ${gameDbName}.games a left JOIN ${gameDbName}.lives b on a.lives_id = b.id " +
			"where b.club_id=0 and a.finished = 1 and a.sign_cost > 0 and a.end_at BETWEEN #{start_time} and #{end_time}")
	List<GamesEntity> queryByDateTime(@Param("start_time") String start_time,@Param("end_time") String end_time);


	//查询新增用户的当天游戏局数
	@Select("SELECT count(gamekinds_id)times,gamekinds_id FROM ${gameDbName}.games WHERE created_at BETWEEN #{start_time} " +
			"and #{end_time} and `anchor_id`=#{user_id} and finished = 1 and `cost_id`>0 group by gamekinds_id")
	List<GamesEntity> queryAddUserGameTimes(@Param("start_time") String start_time,@Param("end_time") String end_time,
							@Param("user_id") int user_id);

}
