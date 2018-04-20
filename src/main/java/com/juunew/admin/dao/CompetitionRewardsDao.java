package com.juunew.admin.dao;

import com.juunew.admin.entity.CompetitionRewardsEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为权限分类与权限的中间表的实体类
 * */
@Repository
public interface CompetitionRewardsDao {
	//比赛新增与编辑中的奖励数据的插入
	@Insert("INSERT INTO ${gameDbName}.competition_rewards(rank,reward_id,title,count,get_type,seq,created_at,competition_template_id,gift_title,gift_url)VALUES(#{rank},#{reward_id},#{title},#{count},#{get_type},#{seq},#{created_at},#{competition_template_id},#{gift_title},#{gift_url})")
	void insertRewards(@Param("rank") String rank,@Param("reward_id") int reward_id,@Param("title") String title, @Param("count") int count,@Param("get_type") int get_type, @Param("seq") int seq,@Param("created_at") String created_at,@Param("competition_template_id") int competition_template_id,@Param("gift_title") String gift_title, @Param("gift_url") String gift_url);


	//根据competition_template表ID查询CompetitionRewards表中的奖品名称
	@Select("SELECT title,count,rank,gift_title,gift_url FROM ${gameDbName}.competition_rewards WHERE competition_template_id=#{id}")
	List<CompetitionRewardsEntity> findByRewards(@Param("id") int id);



	//根据competition_reward_record表ID查询CompetitionRewards表中的奖品名称
	@Select("SELECT get_type,gift_title FROM ${gameDbName}.competition_rewards WHERE id=(SELECT competition_reward_id FROM ${gameDbName}.competition_reward_records WHERE id=#{id})")
	CompetitionRewardsEntity findByRewardsGift(@Param("id") int id);


	//根据条件删除元素
	@Delete("DELETE FROM ${gameDbName}.competition_rewards WHERE competition_template_id=#{id}")
	void change(@Param("id") int id);


	//比赛新增与编辑中的  实物图片的修改     1:先查出有实物奖励的图片url地址 的id
	@Select("select id from ${gameDbName}.competition_rewards where competition_template_id = #{competition_template_id} and gift_url!=''")
	List<CompetitionRewardsEntity> findIdByTemplateId(@Param("competition_template_id") int competition_template_id);



	//比赛新增与编辑中的  实物图片的修改     2:先查出有实物奖励的图片url地址 的id
	@Update("update ${gameDbName}.competition_rewards set gift_url=#{url} where id = #{id}")
	void updateByGiftUrl(@Param("url") String url,@Param("id") int id);

	//比赛新增与编辑中的  修改奖励名称
	@Update("update ${gameDbName}.competition_rewards set title=#{title} where id = #{id}")
	void updateTitle(@Param("title") String title,@Param("id") int id);


	//比赛新增与编辑中的  比赛奖励名称修改
	@Select("select id from ${gameDbName}.competition_rewards where competition_template_id = #{competition_template_id}")
	List<CompetitionRewardsEntity> findIdByTemplateIdData(@Param("competition_template_id") int competition_template_id);

}
