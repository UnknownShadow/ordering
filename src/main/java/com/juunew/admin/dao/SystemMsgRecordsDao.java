package com.juunew.admin.dao;

import com.juunew.admin.entity.SystemMsgRecordsEntity;
import com.juunew.admin.entity.SystemMsgsEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为 系统消息 记录表 实体类
 * */
@Repository
public interface SystemMsgRecordsDao {

	//根据消息ID和用户ID查询该消息是否未读
	@Select("select id,read_status from ${gameDbName}.system_msg_records where user_id =#{user_id} and system_msgs_id=#{system_msgs_id} limit 1")
	SystemMsgRecordsEntity queryIsRead(@Param("user_id") int user_id,@Param("system_msgs_id") int system_msgs_id);


	//根据user_id 和 msg_id 查询数据
	@Select("select * from ${gameDbName}.system_msg_records where user_id = #{user_id} and system_msgs_id=#{system_msgs_id} order by id desc limit 1")
	SystemMsgRecordsEntity findByUserIdAndMsgId(@Param("user_id") int user_id,@Param("system_msgs_id") int system_msgs_id);

	//将system_msg 内容 添加到record表中
	@Insert("insert into ${gameDbName}.system_msg_records(user_id,system_msgs_id,read_status,created_at)values(#{user_id},#{system_msgs_id},#{read_status},now())")
	@Options(useGeneratedKeys=true, keyProperty = "api.id")
	void insertToMsgRecords(@Param("user_id") int user_id,@Param("system_msgs_id") int system_msgs_id,@Param("read_status") int read_status, @Param("api") SystemMsgRecordsEntity api);


	//根据system_msgs_record_id 和 user_id修改读取状态
	@Update("update ${gameDbName}.system_msg_records set read_status=1 where user_id=#{user_id} and id=#{msg_record_id}")
	void updateReadStatus(@Param("user_id") int user_id,@Param("msg_record_id") int msg_record_id);


	//根据system_msgs_record_id 和 user_id修改读取状态
	@Update("update ${gameDbName}.system_msg_records set show_times=#{times} where user_id=#{user_id} and id=#{msg_record_id}")
	void updateTimes(@Param("user_id") int user_id,@Param("msg_record_id") int msg_record_id,@Param("times") int times);


	//根据system_msgs_record_id 和 user_id修改读取状态
	@Update("update ${gameDbName}.system_msg_records set read_status=2 where user_id=#{user_id} and id=#{msg_record_id}")
	void updateReadStatusTwo(@Param("user_id") int user_id,@Param("msg_record_id") int msg_record_id);


	//根据record_id 和user_id 查询read_status
	@Select("select id,read_status from ${gameDbName}.system_msg_records where user_id=#{user_id} and id=#{reward_record_id}")
	SystemMsgRecordsEntity findReadStatus(@Param("user_id") int user_id,@Param("reward_record_id") int reward_record_id);

	//根据user_id 和 msg_id 查询数据
	@Select("select * from ${gameDbName}.system_msg_records where user_id = #{user_id} and id=#{reward_record_id} ")
	SystemMsgRecordsEntity findData(@Param("user_id") int user_id,@Param("reward_record_id") int reward_record_id);


	//根据user_id 和 system_msgs_id 查询数据
	@Select("select * from ${gameDbName}.system_msg_records where user_id = #{user_id} and system_msgs_id=#{system_msgs_id} ")
	SystemMsgRecordsEntity findByUserIdSysMsgId(@Param("user_id") int user_id,@Param("system_msgs_id") int system_msgs_id);

	@Insert("insert into  ${gameDbName}.system_msg_records (user_id, system_msgs_id, read_status) values (" +
			"#{a.user_id}, #{a.system_msgs_id}, #{a.read_status})")
	@Options(useGeneratedKeys=true, keyProperty = "a.id")
	void save(@Param("a") SystemMsgRecordsEntity entity);

	@Update("update ${gameDbName}.system_msg_records set read_status=1 where id = #{id} and read_status = 0")
    void read(@Param("id") int id);

	//根据消息Id 和用户ID修改已读状态
	@Update("update ${gameDbName}.system_msg_records set read_status=1 where system_msgs_id = #{msg_id} and user_id=#{user_id} and read_status = 0")
	void readed(@Param("msg_id") int msg_id,@Param("user_id") int user_id);

	//根据消息Id 和用户ID修改已读状态
	@Select("select id from ${gameDbName}.system_msg_records where system_msgs_id=#{msg_id} and user_id=#{user_id} limit 1")
	SystemMsgRecordsEntity queryByMsgId(@Param("msg_id") int msg_id,@Param("user_id") int user_id);


	@Update("update ${gameDbName}.system_msg_records set show_times = show_times+1 where id = #{id}")
	void addShowTime(@Param("id") int id);

	//根据system_msgs_id 删除记录
	@Delete("delete from ${gameDbName}.system_msg_records where system_msgs_id=#{system_msgs_id}")
    void delById(@Param("system_msgs_id") int system_msgs_id);

	//根据user_id 和 msg_id 查询数据
	@Select("select * from ${gameDbName}.system_msg_records where system_msgs_id=#{system_msgs_id}")
	List<SystemMsgRecordsEntity> findRecordByMsgId(@Param("system_msgs_id") int system_msgs_id);


	//根据msg_id 查询展示的次数
	@Select("select SUM(show_times)show_times,SUM(hit_count)hit_count from ${gameDbName}.system_msg_records where system_msgs_id = #{msgs_id}")
	SystemMsgRecordsEntity findShowTimesByMsgId(@Param("msgs_id") int msgs_id);


	//根据msgId 和 user_id修改点击消息次数
	@Update("update ${gameDbName}.system_msg_records set hit_count=hit_count+1 where user_id=#{user_id} and system_msgs_id=#{msg_id}")
	void updateHitCount(@Param("user_id") int user_id,@Param("msg_id") int msg_id);


	//将system_msg 内容 添加到record表中
	@Insert("insert into ${gameDbName}.system_msg_records(user_id,system_msgs_id,read_status,created_at)values(#{user_id},#{system_msgs_id},#{read_status},now())")
	void insertToMsg(@Param("user_id") int user_id,@Param("system_msgs_id") int system_msgs_id,@Param("read_status") int read_status);

}
