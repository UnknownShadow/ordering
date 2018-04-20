package com.juunew.admin.dao;

import com.juunew.admin.entity.ClubUsersEntity;
import com.juunew.admin.entity.ClubsEntity;
import com.juunew.admin.entity.GamesEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为俱乐部成员实体类
 * */
@Repository
public interface ClubsDao {

	//根据 俱乐部Code查询 该俱乐部的创建人ID
	@Select("select * from ${gameDbName}.clubs where code=#{clubCode}")
	ClubsEntity findByCode(@Param("clubCode") String clubCode);

	//查询所有俱乐部
	@Select("select a.id,a.name,a.announcement,a.gamekind,a.creator_id,a.code,a.game_times,a.created_at,b.nickname from " +
			"${gameDbName}.clubs a,${gameDbName}.users b where a.creator_id=b.id order by a.game_times desc " +
			"limit #{offset},#{limit}")
	List<ClubsEntity> findClubPaging(@Param("offset") int offset,@Param("limit") int limit);
	//根据UserID查询俱乐部
	@Select("select a.id,a.name,a.announcement,a.gamekind,a.creator_id,a.code,a.game_times,a.created_at,b.nickname from " +
			"${gameDbName}.clubs a,${gameDbName}.users b where a.creator_id=b.id and a.creator_id=#{query_id} " +
			"order by a.game_times desc limit #{offset},#{limit}")
	List<ClubsEntity> findClubByUserId(@Param("query_id") int user_id,@Param("offset") int offset,@Param("limit") int limit);
	//根据俱乐部ID查询俱乐部
	@Select("select a.id,a.name,a.announcement,a.gamekind,a.creator_id,a.code,a.game_times,a.created_at,b.nickname from " +
			"${gameDbName}.clubs a,${gameDbName}.users b where a.creator_id=b.id and a.`code`=#{query_id} " +
			"order by a.game_times desc  limit #{offset},#{limit}")
	List<ClubsEntity> findClubByClubId(@Param("query_id") int user_id,@Param("offset") int offset,@Param("limit") int limit);



	//查询所有俱乐部
	@Select("select count(*) from ${gameDbName}.clubs")
	int findTotal();

	//根据UserId查询所有俱乐部总条数
	@Select("select count(*) from ${gameDbName}.clubs where creator_id=#{id}")
	int findTotalById(@Param("id") int id);
	//根据俱乐部ID查询所有俱乐部总条数
	@Select("select count(*) from ${gameDbName}.clubs where `code`=#{id}")
	int findTotalByClubId(@Param("id") int id);



	//根据 俱乐部ID查询数据
	@Select("select a.id,a.name,a.announcement,a.gamekind,a.creator_id,a.code,a.game_times,a.created_at,a.status,b.nickname from " +
			"${gameDbName}.clubs a,${gameDbName}.users b where a.creator_id=b.id and a.id=#{query_id}")
	ClubsEntity findById(@Param("query_id") int query_id);


	//修改俱乐部公告
	@Update("update ${gameDbName}.clubs set announcement=#{announcement} where id=#{id}")
	void updateAnnouncement(@Param("id") int id,@Param("announcement") String announcement);

	//修改俱乐部名称
	@Update("update ${gameDbName}.clubs set name=#{name} where id=#{id}")
	void updateName(@Param("id") int id,@Param("name") String name);

	//修改俱乐部状态
	@Update("update ${gameDbName}.clubs set status=#{status} where id=#{id}")
	void updateStatus(@Param("id") int id,@Param("status") int status);


	//根据 游戏类型查询俱乐部
	@Select("select * from ${gameDbName}.clubs where gamekind=#{gamekind}")
	List<ClubsEntity> findByGameKind(@Param("gamekind") int gamekind);


	//根据 创建者查询俱乐部
	@Select("select * from ${gameDbName}.clubs where creator_id= #{user_id}")
	List<ClubsEntity> queryClubByCreator(@Param("user_id") int user_id);
	//根据 创建者查询俱乐部
	@Select("select id from ${gameDbName}.clubs where creator_id= #{user_id}")
	List<ClubsEntity> getClubIdByCreator(@Param("user_id") int user_id);

	//根据 创建者查询俱乐部
	@Select("select id from ${gameDbName}.clubs where creator_id in(${user_id})")
	List<ClubsEntity> getClubIdByCreators(@Param("user_id") String user_id);


	//根据 游戏id查询是否为俱乐部开房
	@Select("select a.creator_id from ${gameDbName}.clubs a,${gameDbName}.club_games b where a.id = b.club_id " +
			"and b.game_id=#{game_id} limit 1")
	ClubsEntity findByGameId(@Param("game_id") int game_id);



	//根据时间查询俱乐部开房
	@Select("select c.id,c.name,c.gamekind,c.creator_id,a.sign_cost from ${gameDbName}.clubs c,${gameDbName}.games a,${gameDbName}.lives b " +
			"where a.lives_id = b.id and b.club_id=c.id and a.finished = 1 and a.sign_cost > 0 and a.end_at BETWEEN #{start_time} and #{end_time}")
	List<ClubsEntity> queryClubsByDateTime(@Param("start_time") String start_time, @Param("end_time") String end_time);

}
