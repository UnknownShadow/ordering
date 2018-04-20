package com.juunew.admin.dao;

import com.juunew.admin.entity.SendDiamondsEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SendDiamondsDao {

	//数据新增
	@Insert("insert into send_diamonds(send_user_id,receive_user_id,diamond,created_time)" +
			"values(#{send_user_id},#{receive_user_id},#{diamond},now())")
	void insertToSendDiamonds(@Param("send_user_id") int send_user_id, @Param("receive_user_id") int receive_user_id,
						 @Param("diamond") int diamond);



	//查询发送记录
	@Select("select * from send_diamonds where send_user_id=#{user_id} order by created_time desc limit #{offset},#{limit}")
	List<SendDiamondsEntity> querySendRecord(@Param("user_id") int user_id,@Param("offset") int offset,
											 @Param("limit") int limit);

	//根据 user_id 查询发送记录总条数
	@Select("select count(send_user_id) from send_diamonds where send_user_id like '%${user_id}%' or " +
			"receive_user_id like '%${user_id}%'")
	int totalCount(@Param("user_id") String user_id);

	//分页联表查询数据
	@Select("select a.send_user_id,a.receive_user_id,a.diamond,a.created_time,b.nickname send_nickname," +
			"c.nickname receive_nickname from send_diamonds a,${gameDbName}.users b,${gameDbName}.users c " +
			"where a.send_user_id=b.id and a.receive_user_id = c.id and (a.send_user_id like '%${user_id}%' or " +
			"a.receive_user_id like '%${user_id}%') order by created_time desc limit #{offset},#{limit}")
	List<SendDiamondsEntity> queryPagingByUserId(@Param("user_id") String user_id,@Param("offset") int offset,
								@Param("limit") int limit);
}
