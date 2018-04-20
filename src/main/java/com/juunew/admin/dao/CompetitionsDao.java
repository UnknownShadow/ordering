package com.juunew.admin.dao;

import com.juunew.admin.entity.CompetitionRewardRecordsEntity;
import com.juunew.admin.entity.CompetitionsEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为权限分类与权限的中间表的实体类
 * */
@Repository
public interface CompetitionsDao {

	//根据ID查询：和比赛轮次
	@Select("select COUNT(*) round from ${gameDbName}.competitions where competition_template_id=#{id} and status>2")
	CompetitionsEntity findByRound(@Param("id") int id);


	//根据ID查询：比赛轮次 当前第几轮
	@Select("select * from ${gameDbName}.competitions where competition_template_id=#{id} and id=#{competition_id}")
	CompetitionsEntity findByThisRound(@Param("id") int id,@Param("competition_id") int competition_id);



	//SELECT COUNT(*)total_enrollment FROM competition_signs WHERE competition_id IN (SELECT id FROM competitions WHERE competition_template_id = 87) AND  `status` NOT IN (0,1,2)
	//根据ID查询：已经报名人数，
	@Select("SELECT COUNT(*)total_enrollment FROM ${gameDbName}.competition_signs WHERE competition_id IN (SELECT id FROM ${gameDbName}.competitions WHERE competition_template_id = #{id}) AND  `status` NOT IN (0,1,2)")
	CompetitionsEntity findByTotalEnrollment(@Param("id") int id);




	//根据competition_template_id 在competitions表中查询比赛状态，
	@Select("SELECT DISTINCT `status`  FROM ${gameDbName}.competitions WHERE competition_template_id = #{competition_template_id}")
	CompetitionsEntity findByCompetitionStatus(@Param("competition_template_id") int competition_template_id);



	//查找比赛标题和比赛轮次
	@Select("SELECT * FROM ${gameDbName}.competition_templates WHERE id = (SELECT competition_template_id FROM ${gameDbName}.competitions WHERE id = #{competition_id})")
	CompetitionRewardRecordsEntity findTileAndRound(@Param("competition_id") int competition_id);



	//根据liveCode查询competitionId  (lives表)
	@Select("select competition_id from ${gameDbName}.lives where code=#{liveCode}")
	CompetitionRewardRecordsEntity findCompetitionIdByLiveCode(@Param("liveCode") String liveCode);


	//根据liveCode查询competitionId  (lives表)
	@Select("select * from ${gameDbName}.competitions where id=#{competitionId}")
	CompetitionRewardRecordsEntity findByCompetitionId(@Param("competitionId") int competitionId);


}
