package com.juunew.admin.dao;

import com.juunew.admin.entity.FeedbackInfoEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FeedbackInfoDao {

	//数据新增
	@Insert("insert into feedback_info(user_id,`desc`,phone,type,created_time)" +
			"values(#{user_id},#{desc},#{phone},#{type},now())")
	void insertToFeedbackInfo(@Param("user_id") int user_id,@Param("desc") String desc,
								 @Param("phone") String phone,@Param("type") int type);


	//查询总数
	@Select("select count(*) from feedback_info where type = #{type}")
	int queryTotalCountByType(@Param("type") int type);

	//分页查询
	@Select("select * from feedback_info where type = #{type} order by created_time desc limit #{offset},#{limit}")
	List<FeedbackInfoEntity> findPaging(@Param("type") int type,@Param("offset") int offset,@Param("limit") int limit);

	//将未联系改成已联系
	@Update("update feedback_info set connection_status=1 where id=#{id}")
	void contacted(@Param("id") int id);

	//根据 type 查询是否有小红点
	@Select("select count(*) from feedback_info where type=#{type} and track_point=#{track_point}")
	int queryTrackPointByType(@Param("type") int type,@Param("track_point") int track_point);

	//改为已读状态
	@Update("update feedback_info set track_point = 0 where type= #{type}")
	void read(@Param("type") int type);
}
