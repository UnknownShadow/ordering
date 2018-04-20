package com.juunew.admin.dao;

import com.juunew.admin.entity.NoticeEntity;
import com.juunew.admin.entity.api.MsgAcquisition;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 *
 * */
@Repository
public interface NoticeDao {
	//将公告消息内容存入数据库中
	@Insert("insert into ${gameDbName}.notices(title,content,start_date,end_date,platform,version,reward_type,reward_number,display_position,sign_out,is_all,cmd)values(#{title},#{content},#{start_date},#{end_date},#{platforms},#{version},#{typed},#{reward_number},#{display_position},#{sign_out},#{is_all},#{cmd})")
	@Options(useGeneratedKeys=true, keyProperty = "api.id")
	void saveAsNotice(@Param("title") String title,@Param("content") String content,@Param("start_date") String start_date,@Param("end_date") String end_date,@Param("platforms") int platforms,@Param("version") String version,@Param("typed") int typed,@Param("reward_number") int reward_number,@Param("display_position") int display_position,@Param("sign_out") int sign_out,@Param("is_all") int is_all,@Param("cmd") int cmd,@Param("api") NoticeEntity api);



	//根据ID查找notice所有数据
	@Select("select * from ${gameDbName}.notices where id = (select notice_id from status_editor where  id=#{id})")
	NoticeEntity findById(@Param("id") int id);



	//根据ID删除notice表中该数据
	@Delete("DELETE FROM ${gameDbName}.notices WHERE id=(SELECT notice_id FROM status_editor WHERE id=#{id})")
	void deleteFromNoticeById(@Param("id") int id);



	//将公告消息内容修改后存入数据库
	@Select("update ${gameDbName}.notices set title=#{title},content=#{content},start_date=#{start_date},end_date=#{end_date},platform=#{platforms},version=#{version},reward_type=#{reward_type},reward_number=#{reward_number},display_position=#{display_position},sign_out=#{sign_out} where id=#{id}")
	void saveAsNoticeChange(@Param("title") String title,@Param("content") String content,@Param("start_date") String start_date,@Param("end_date") String end_date,@Param("platforms") int platforms,@Param("version") String version,@Param("reward_type") int reward_type,@Param("reward_number") int reward_number,@Param("display_position") int display_position,@Param("sign_out") int sign_out,@Param("id") int id);

	//@Param("title") String title, @Param("content") String content, @Param("start_date") String start_date, @Param("end_date") String end_date, @Param("platform") String platform, @Param("version") String version, @Param("specific_user") String specific_user, @Param("type") String type, @Param("number") String number


	//date_sub(curdate(), INTERVAL 1 DAY) < date(created_at);
	//select * from ${gameDbName}.notices where id=#{id} and #{now} between start_date and end_date		////select * from ${gameDbName}.notices where id=#{id} and date_sub(curdate(), INTERVAL 7 DAY) < date(end_date)
	@Select("select * from ${gameDbName}.notices where id=#{id} and #{now} between start_date and end_date")
	MsgAcquisition findNoticeById(@Param("id") int id,@Param("now") Date now);



	//根据notice_id查询数据；
	@Select("select * from ${gameDbName}.notices where id=#{notice_id}")
	NoticeEntity findBy(@Param("notice_id") int notice_id);
}
