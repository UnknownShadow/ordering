package com.juunew.admin.dao;

import com.juunew.admin.entity.SystemMsgRecordsEntity;
import com.juunew.admin.entity.SystemMsgsEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为 系统消息 实体类
 * */
@Repository
public interface SystemMsgsDao {
	@Select("select * from ${gameDbName}.system_msgs as a  where cmd=5  and (send_all = 1 or id in " +
			" (select system_msgs_id from ${gameDbName}.system_msg_records where " +
			"user_id = #{userId} ) ) and (`version`=0 or `version` IS NULL OR `version`=#{build}) order by id DESC")
	List<SystemMsgsEntity> findAllData(@Param("userId") int id,@Param("build") int build);

	@Select("select * from ${gameDbName}.system_msgs where id=(select system_msgs_id from " +
			"${gameDbName}.system_msg_records where id = #{reward_record_id}) and now() between start_time and end_time")
	SystemMsgsEntity findMsgById(@Param("reward_record_id") int reward_record_id);

	@Select("select * from ${gameDbName}.system_msgs where need_save=1  and (send_all = 1 or id in " +
			"(select system_msgs_id from ${gameDbName}.system_msg_records where user_id = #{userId} order by read_status) ) " +
			"and (`version`=0 or `version` IS NULL OR `version`=#{build}) order by start_time DESC limit #{offset},#{limit} ")
	List<SystemMsgsEntity> getUserMsgs(@Param("build") int build, @Param("userId") int id, @Param("limit") int limit, @Param("offset") int offset);
	//join


	/*
	"select * from ${gameDbName}.system_msgs a left join ${gameDbName}.system_msg_records b on a.id=b.system_msgs_id where a.need_save=1 and b.user_id=#{user_id} " +
			"and (`version`=0 or `version` IS NULL OR `version`=#{build}) order by start_time DESC limit #{offset},#{limit}
	 */
	//不查询邀请和再来一局的消息
	@Select("select * from ${gameDbName}.system_msgs a,${gameDbName}.system_msg_records b where a.id=b.system_msgs_id " +
			"and a.need_save=1 and b.user_id=#{user_id} and (a.`version`=0 or a.`version` IS NULL OR a.`version`=#{build}) " +
			"and a.cmd!=2 and a.cmd!=1 order by start_time DESC limit #{offset},#{limit}")
	List<SystemMsgsEntity> queryMessages(@Param("user_id") int user_id,@Param("build") int build, @Param("offset") int offset, @Param("limit") int limit);

	//只查询邀请和再来一局的消息
	@Select("select * from ${gameDbName}.system_msgs a,${gameDbName}.system_msg_records b where a.id=b.system_msgs_id " +
			"and a.need_save=1 and b.user_id=#{user_id} and (a.`version`=0 or a.`version` IS NULL OR a.`version`=#{build}) and " +
			"(a.cmd=2 or a.cmd=1) order by start_time DESC limit #{offset},#{limit}")
	List<SystemMsgsEntity> queryInviteAndAgain(@Param("user_id") int user_id,@Param("build") int build,
								  @Param("offset") int offset, @Param("limit") int limit);


	//先查询未读消息
	@Select("select * from ${gameDbName}.system_msgs as s left join ${gameDbName}.system_msg_records as r " +
			"on s.id = r.system_msgs_id and r.user_id = #{user_id} where s.end_time > now() and now()>=s.start_time " +
			" and s.send_all=1 and (r.read_status is null or r.read_status=0)")
	List<SystemMsgsEntity> getUnread(@Param("user_id") int user_id);
	/**
	 select * from ${gameDbName}.system_msgs as s left join ${gameDbName}.system_msg_records as r " +
	 "on s.id = r.system_msgs_id and r.user_id = #{user_id} where s.end_time > now() and now()>=s.start_time " +
	 "and s.send_all=1 and (r.read_status is null or r.read_status=0)
	 */

	@Select("select * from ${gameDbName}.system_msgs as s left join ${gameDbName}.system_msg_records as r on " +
			"s.id = r.system_msgs_id and r.user_id = #{userId} where s.end_time > now() and now()>=s.start_time " +
			"and s.send_all=1 and (r.read_status is null or r.read_status=0)limit #{offset},#{limit} ")
	List<SystemMsgsEntity> findMsgsByUserId(@Param("userId") int userId, @Param("limit") int limit, @Param("offset") int offset);



	@Select("select id from ${gameDbName}.system_msgs where need_save=1  and (send_all = 1 or id in " +
			"(select system_msgs_id from ${gameDbName}.system_msg_records where user_id = #{userId} order by read_status) ) " +
			"and (`version`=0 or `version` IS NULL OR `version`=#{build})")
	List<SystemMsgsEntity> queryUserMsgs(@Param("build") int build, @Param("userId") int userId);

	//重构
	@Select("select * from ${gameDbName}.system_msgs where id in(select system_msgs_id from ${gameDbName}.system_msg_records where user_id = #{userId} order by read_status) " +
			"limit #{offset},#{limit}")
	List<SystemMsgsEntity> queryMsgsByUserId(@Param("userId") int userId,@Param("offset") int offset,@Param("limit") int limit);

	//

//order by start_time DESC

	//将 前台传入的数据 insert 到system_msgs 表中
	@Insert("insert into ${gameDbName}.system_msgs(title,content,pic_url,pic_cmd,pic_to_page,pic_to_url,btn_show,btn_cmd,btn_title," +
			"btn_to_page,btn_to_url,reward,need_exit,need_scroller,show_place,cmd,immediateness,need_save,show_times,platfrom," +
			"version,start_time,end_time,show_again,created_at,updated_at,msg_type,send_all)values" +
			"(#{a.title},#{a.content},#{a.pic_url},#{a.pic_cmd},#{a.pic_to_page},#{a.pic_to_url},#{a.btn_show},#{a.btn_cmd},#{a.btn_title}," +
			"#{a.btn_to_page},#{a.btn_to_url},#{a.reward},#{a.need_exit},#{a.need_scroller},#{a.show_place},#{a.cmd},#{a.immediateness}," +
			"#{a.need_save},#{a.show_times},#{a.platfrom},#{a.version},#{a.start_time},#{a.end_time},#{a.show_again},now()," +
			"now(),#{a.msg_type},#{a.send_all})")
	@Options(useGeneratedKeys=true, keyProperty = "a.id")
	void insertToSysMsg(@Param("a") SystemMsgsEntity entity);



	//查询所有消息
	@Select("select count(*) from ${gameDbName}.system_msgs where cmd=5")
	int findAllMsgTotal();

	//分页查询所有数据
	@Select("select * from ${gameDbName}.system_msgs where cmd=5 order by id DESC limit #{offset},#{limit}")
	List<SystemMsgsEntity> findPaging(@Param("offset") int offset,@Param("limit") int limit);

	//根据id删除消息
	@Delete("delete from ${gameDbName}.system_msgs where id = #{id}")
	void delById(@Param("id") int id);


	//根据 Msg_id 查询数据
	@Select("select * from ${gameDbName}.system_msgs where id = #{id}")
	SystemMsgsEntity findById(@Param("id") int id);
}
