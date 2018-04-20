package com.juunew.admin.dao;

import com.juunew.admin.entity.Status_EditorEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为权限分类与权限的中间表的实体类
 * */
@Repository
public interface Status_EditorDao {
	//将数据存入数据库status_editor表中，
	@Select("insert into status_editor(title,status,create_date,start_date,end_date,notice_id,announcements_id)values(#{title},#{status},#{create_date},#{start_date},#{end_date},#{notice_id},#{announcements_id})")
	void insertEditor(@Param("title") String title, @Param("status") String status, @Param("create_date") String create_date, @Param("start_date") String start_date,@Param("end_date") String end_date, @Param("notice_id") int notice_id, @Param("announcements_id") int announcements_id);


	//对Status_editor表中消息奖励查询
	@Select("select * from status_editor where notice_id in(select id from ${gameDbName}.notices where cmd=5 or cmd=8) order by create_date desc")
	List<Status_EditorEntity> findByMsgReward();

	//对Status_editor表中消息奖励进行分页查询
	@Select("select * from status_editor where notice_id in(select id from ${gameDbName}.notices where cmd=5 or cmd=8) order by create_date desc limit #{xx},#{yy}")
	List<Status_EditorEntity> findByMsgRewardPaging(@Param("xx") int xx,@Param("yy") int yy);


	//对Status_editor表进行模分查询
	@Select("select * from status_editor where announcements_id!=0 order by create_date desc")
	List<Status_EditorEntity> findByMsgNotice();


	//对Status_editor表进行模分查询
	@Select("select * from status_editor where announcements_id!=0 order by create_date desc limit #{xx},#{yy}")
	List<Status_EditorEntity> findByMsgNoticePaging(@Param("xx") int xx,@Param("yy") int yy);


	//修改数据后存入数据库status_editor表中，
	@Select("update status_editor set title=#{title},status=#{status},create_date=#{create_date},start_date=#{start_date},end_date=#{end_date} where notice_id=#{notice_id}")
	void updateEditor(@Param("title") String title, @Param("status") String status, @Param("create_date") String create_date, @Param("start_date") String start_date,@Param("end_date") String end_date, @Param("notice_id") int notice_id);


	//根据ID删除Status_editor表中该数据
	@Delete("DELETE FROM status_editor WHERE id=#{id}")
	void deleteFromStatus_editorById(@Param("id") int id);



	//对Status_editor表中消息奖励查询
	@Select("select * from status_editor where announcements_id in(select id from notice where web_jumping='' and location='') order by create_date desc")
	List<Status_EditorEntity> findByAnnouncements();



	//对Status_editor表中消息奖励查询
	@Select("select * from status_editor where id=#{id}")
	Status_EditorEntity findByAnnouncementsId(@Param("id") int id);


	//修改数据后存入数据库status_editor表中，
	@Select("update status_editor set title=#{title},status=#{status},create_date=#{create_date},start_date=#{start_date},end_date=#{end_date} where announcements_id=#{id}")
	void updateEditorOther(@Param("title") String title, @Param("status") String status, @Param("create_date") String create_date, @Param("start_date") String start_date,@Param("end_date") String end_date, @Param("id") int id);

}
