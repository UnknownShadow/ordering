package com.juunew.admin.dao;

import com.alibaba.fastjson.JSONObject;
import com.juunew.admin.entity.CompetitionTemplatsEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 比赛新增接口
 * */
@Repository
public interface CompetitionTemplatsDao {

	//将比赛新增的数据插入到数据库中
	@Insert("INSERT INTO ${gameDbName}.competition_templates(type,name,title,user_limit,cost,rule,sign_start_time,start_time,end_time,seq,show_time,pic_url,password)VALUES(#{type},#{name},#{title},#{user_limit},#{cost},#{rule},#{sign_start_time},#{start_time},#{end_time},#{seq},#{showTime},#{compPicUrl},#{password})")
	@Options(useGeneratedKeys=true, keyProperty = "api.id")
	void insertCompetitionTemp(@Param("type") int type, @Param("name") String name, @Param("title") String title, @Param("user_limit") int user_limit, @Param("cost") String cost, @Param("rule") String rule, @Param("sign_start_time") String sign_start_time, @Param("start_time") String start_time, @Param("end_time") String end_time, @Param("seq") int seq, @Param("showTime") Date showTime,@Param("compPicUrl") String compPicUrl,@Param("password") String password, @Param("api") CompetitionTemplatsEntity api);


	//根据template_id更新比赛新增的数据
	@Update("update ${gameDbName}.competition_templates set name=#{name},title=#{title},rule=#{rule},pic_url=#{pic_url} where id=#{id}")
	void updateCompetitionTemp(@Param("name") String name, @Param("title") String title, @Param("rule") String rule,@Param("pic_url") String pic_url, @Param("id") int id);



	//查询Competition_template表中的所有数据
	@Select("select * from ${gameDbName}.competition_templates ORDER BY end_time DESC")
	List<CompetitionTemplatsEntity> findAll();

	//根据时间段查询Competition_template表中的所有数据
	@Select("SELECT * FROM ${gameDbName}.competition_templates WHERE end_time>=#{proxy_date} AND end_time<=#{proxy_date_end} ORDER BY end_time DESC")
	List<CompetitionTemplatsEntity> findByEndTime(@Param("proxy_date") String proxy_date,@Param("proxy_date_end") String proxy_date_end);



	//对Competition_template表中的所有数据进行分页查询
	@Select("select * from ${gameDbName}.competition_templates ORDER BY end_time DESC limit #{offset},#{limit} ")
	List<CompetitionTemplatsEntity> findByPaging(@Param("offset") int offset,@Param("limit") int limit);

	//根据时间段对Competition_template表中的所有数据进行分页查询
	@Select("select * from ${gameDbName}.competition_templates WHERE end_time>=#{proxy_date} " +
			"and end_time<=#{proxy_date_end} ORDER BY end_time DESC limit #{offset},#{limit}")
	List<CompetitionTemplatsEntity> findPagingByEndTime(@Param("proxy_date") String proxy_date,@Param("proxy_date_end") String proxy_date_end,
														@Param("offset") int offset,@Param("limit") int limit);




	//根据ID查询cost中的num
	@Select("SELECT JSON_EXTRACT(cost,'$.num') num,JSON_EXTRACT(rule,'$.title') rules,JSON_EXTRACT(JSON_EXTRACT(rule,'$.rule'),'$.totalPeriod') totalPeriod,JSON_EXTRACT(JSON_EXTRACT(rule,'$.rule'),'$.settleType') settleType,id FROM ${gameDbName}.competition_templates where id=#{id}")
	CompetitionTemplatsEntity findByCostNum(@Param("id") int id);


	//根据ID查询单一数据，进行再次编辑；
	@Select("select * from ${gameDbName}.competition_templates where id=#{id}")
	CompetitionTemplatsEntity findById(@Param("id") int id);




	/*//根据ID号修改比赛新增单挑数据
	@Update("update ${gameDbName}.competition_templates set type=#{type},name=#{name},title=#{title},user_limit=#{user_limit},cost=#{cost},rule=#{rule},sign_start_time=#{sign_start_time},start_time=#{start_time},end_time=#{end_time} where id=#{id}")
	void updateCompetitionTemp(@Param("type") int type, @Param("name") String name, @Param("title") String title, @Param("user_limit") int user_limit, @Param("cost") String cost, @Param("rule") String rule, @Param("sign_start_time") String sign_start_time, @Param("start_time") String start_time, @Param("end_time") String end_time,@Param("id") int id);
*/

	//根据ID号立即终止比赛
	@Update("update ${gameDbName}.competition_templates set enable=#{enable} where id=#{id}")
	void updateEnable(@Param("id") int id,@Param("enable") int enable);

}
