package com.juunew.admin.dao;

import com.juunew.admin.entity.Announcements_recordsEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 此类
 * */
@Repository
public interface Announcements_recordsDao {

	//根据user_id查询send_time
	@Select("select * from ${gameDbName}.announcements_records where user_id = #{user_id}")
	Announcements_recordsEntity findByUserId(@Param("user_id") int user_id);



	//根据  announcementsID 查询  announcement_records 表中的内容
	@Select("select * from ${gameDbName}.announcement_records where announcement_id = #{announcement_id} and user_id=#{user_id}")
	Announcements_recordsEntity findDataByID(@Param("announcement_id") int announcement_id,@Param("user_id") int user_id);


	//根据  announcementsID 查询  announcement_records 表中的内容
	@Update("update ${gameDbName}.announcement_records set send_times=send_times+1 where announcement_id = #{announcement_id} and user_id=#{user_id}")
	void updateSendTimes(@Param("announcement_id") int announcement_id,@Param("user_id") int user_id);



	//如果有公告，但没有在announcements_records表中，则建立相对应的关系；（新建一条数据）
	@Insert("insert into ${gameDbName}.announcement_records(user_id,announcement_id,send_times,created_at)values(#{user_id},#{announcement_id},#{send_times},#{created_at})")
	void insertToRecords(@Param("announcement_id") int announcement_id,@Param("user_id") int user_id,@Param("send_times") int send_times,@Param("created_at") Date created_at);


}
