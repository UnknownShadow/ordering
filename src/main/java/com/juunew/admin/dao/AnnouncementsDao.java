package com.juunew.admin.dao;

import com.juunew.admin.entity.AnnouncementsEntity;
import com.juunew.admin.entity.NoticeDraftEntity;
import com.juunew.admin.entity.api.NoticeDropDown;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为发布公告实体类
 * */
@Repository
public interface AnnouncementsDao {

	//将发布公告信息插入数据库中
	@Insert("insert into ${gameDbName}.announcements(type,status,content,start_time,end_time,created_at,`exit`,title,times,version,platform,display_position,jump_url,imgurl,msg_type,page_type)VALUES(#{type},#{status},#{content},#{start_time},#{end_time},#{created_at},#{exit},#{title},#{times},#{version},#{platforms},#{display_position},#{jump_url},#{imgurl},#{msg_type},#{page_type})")
	@Options(useGeneratedKeys=true, keyProperty = "api.id")
	void insertAnnouncements(@Param("type") int type, @Param("status") int status,@Param("content") String content, @Param("start_time") long start_time,@Param("end_time") long end_time, @Param("created_at") String created_at,@Param("exit") int exit, @Param("title") String title,@Param("times") int times, @Param("version") String version,@Param("platforms") int platforms, @Param("display_position") int display_position,@Param("jump_url") String jump_url,@Param("imgurl") String imgurl,@Param("msg_type") int msg_type,@Param("page_type") int page_type,@Param("api") AnnouncementsEntity api);

	//将发布公告信息的草稿插入数据库中
	@Insert("insert into notice_draft(content,start_time,end_time,created_at,`exit`,title,times,version,platform,display_position,jump_url,imgurl)VALUES(#{content},#{start_time},#{end_time},#{created_at},#{exit},#{title},#{times},#{version},#{platforms},#{display_position},#{jump_url},#{imgurl})")
	@Options(useGeneratedKeys=true, keyProperty = "api.id")
	void insertNoticeDraft(@Param("content") String content, @Param("start_time") String start_time, @Param("end_time") String end_time, @Param("created_at") String created_at, @Param("exit") int exit, @Param("title") String title, @Param("times") int times, @Param("version") String version, @Param("platforms") int platforms, @Param("display_position") int display_position, @Param("jump_url") String jump_url, @Param("imgurl") String imgurl,@Param("api") NoticeDraftEntity api);




	//查询公告信息
	@Select("select * from ${gameDbName}.announcements")
	List<AnnouncementsEntity> findAnnouncements();

	//分页查询公告信息
	@Select("select * from ${gameDbName}.announcements limit #{xx},#{yy}")
	List<AnnouncementsEntity> findAnnouncementsPaging(@Param("xx") int xx, @Param("yy") int yy);

	//分页查询公告信息
	@Delete("delete from ${gameDbName}.announcements where id=#{id}")
	void deleteById(@Param("id") int id);


	//根据id 查询数据
	@Select("select * from ${gameDbName}.announcements where id=#{id}")
	AnnouncementsEntity findById(@Param("id") int id);


	//根据id 修改数据
	@Select("update ${gameDbName}.announcements set type=#{type},status=#{status},content=#{content},start_time=#{start_time},end_time=#{end_time},created_at=#{created_at},`exit`=#{exit},title=#{title},times=#{times},version=#{version},platform=#{platforms},display_position=#{display_position},jump_url=#{jump_url} where id=#{id}")
	void updateById(@Param("id") int id,@Param("type") int type, @Param("status") int status,@Param("content") String content, @Param("start_time") long start_time,@Param("end_time") long end_time, @Param("created_at") String created_at,@Param("exit") int exit, @Param("title") String title,@Param("times") int times, @Param("version") String version,@Param("platforms") int platforms, @Param("display_position") int display_position,@Param("jump_url") String jump_url);




	//查询公告信息
	@Select("select id,start_time,content,title,display_position,jump_url,times,`exit` from ${gameDbName}.announcements where end_time>=#{now_date} and id=#{id}")
	NoticeDropDown findAnnouncementsByEndTime(@Param("now_date") long now_date,@Param("id") int id);


	//select * from ${gameDbName}.announcements where end_time>=#{now_date} and status=1 and msg_type=0 or msg_type=2
	//查询所有平台发送的公告信息select * from announcements where BETWEEN #{now_date} between start_time and end_time and status=1 and msg_type=0 or msg_type=2
	@Select("select * from ${gameDbName}.announcements where (#{now_date} between start_time and end_time) and status=1 and (msg_type=0 or msg_type=2)")
	List<AnnouncementsEntity> findAnnouncementsByMsType(@Param("now_date") long now_date);

	//查  Announcements 表 中的 msg_type=1 的记录
	@Select("select * from ${gameDbName}.announcements where end_time>=#{now_date} and status=1 and msg_type=1 order by created_at desc limit 5")
	List<AnnouncementsEntity> findAnnouncementsByCondition(@Param("now_date") long now_date);

}
