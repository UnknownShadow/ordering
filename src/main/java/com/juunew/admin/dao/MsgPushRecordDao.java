package com.juunew.admin.dao;

import com.juunew.admin.entity.MsgPushRecordEntity;
import com.juunew.admin.entity.NoticeEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为权限分类与权限的中间表的实体类
 * */
@Repository
public interface MsgPushRecordDao {
	//将推送的数据插入到msg_push_record表中
	@Insert("insert into ${gameDbName}.push_msg_records(user_id,read_status,notice_id)values(#{user_id},#{read_status},#{notice_id})")
	void insertMsgPushRecord(@Param("user_id") int user_id,@Param("read_status") int read_status,@Param("notice_id") int notice_id);
//	void insertMsgPushRecord(@Param("users_id") int users_id, @Param("title") String title,@Param("content") String content, @Param("display_position") String display_position,@Param("sign_out") int sign_out, @Param("reward_type") String reward_type,@Param("reward_number") int reward_number, @Param("start_date") String start_date,@Param("end_date") String end_date, @Param("platform") String platform,@Param("version") String version,@Param("read_status") int read_status,@Param("notice_id") int notice_id);



	// 根据notice_id修改所有数据//用于修改notices表数据
	@Update("update ${gameDbName}.notices set title=#{title},content=#{content},display_position=#{display_posit},sign_out=#{sign_out},reward_type=#{reward_type},reward_number=#{reward_number},start_date=#{start_date},end_date=#{end_date},platform=#{platforms},version=#{version},cmd=#{cmd} where id=#{notice_id}")
	void updateNotices(@Param("title") String title,@Param("content") String content, @Param("display_posit") int display_posit,@Param("sign_out") int sign_out, @Param("reward_type") int reward_type,@Param("reward_number") int reward_number, @Param("start_date") String start_date,@Param("end_date") String end_date, @Param("platforms") int platforms,@Param("version") String version,@Param("cmd") int cmd,@Param("notice_id") int notice_id);



	////先根据notice_id 删除msg_push_record表中的数据
	@Delete("delete from ${gameDbName}.push_msg_records where notice_id=#{notice_id}")
	void deleteByIdMsgPushRecord(@Param("notice_id") int notice_id);


	//根据notiec_id 查询数据 user_id(特定用户)
	@Select("select user_id from ${gameDbName}.push_msg_records where notice_id=#{notice_id}")
	List<MsgPushRecordEntity> findUserId(@Param("notice_id") int notice_id);

	//根据user_id 查询数据select notice_id from ${gameDbName}.push_msg_records where user_id=#{user_id}
	@Select("SELECT * FROM ${gameDbName}.push_msg_records a LEFT JOIN ${gameDbName}.notices b ON a.notice_id=b.id WHERE user_id = #{user_id} ORDER BY start_date DESC limit #{xx},#{limits}")
	List<NoticeEntity> findPushMsgByUserId(@Param("user_id") int user_id,@Param("xx") int xx,@Param("limits") int limits);

	//根据user_id 和notice_id 查询read_status
	@Select("select id,read_status from ${gameDbName}.push_msg_records where user_id=#{user_id} and notice_id=#{notice_id}")
	MsgPushRecordEntity findReadStatus(@Param("user_id") int user_id,@Param("notice_id") int notice_id);


	//将read_status的值改为  1
	@Update("update ${gameDbName}.push_msg_records set read_status=#{read_status} where notice_id=#{notice_id} and user_id=#{user_id}")
	void updateReadStatus(@Param("user_id") int user_id,@Param("notice_id") int notice_id,@Param("read_status") int read_status);

	//根据user_id 和notice_id 查询read_status
	@Select("SELECT * from ${gameDbName}.push_msg_records a LEFT JOIN ${gameDbName}.notices b ON a.notice_id=b.id WHERE user_id =#{user_id} and notice_id=#{notice_id} and #{sysDate} BETWEEN  start_date and end_date")
	MsgPushRecordEntity findMsgExpired(@Param("user_id") int user_id,@Param("notice_id") int notice_id,@Param("sysDate") String sysDate);

}
