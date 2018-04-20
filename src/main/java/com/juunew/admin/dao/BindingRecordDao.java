package com.juunew.admin.dao;

import com.juunew.admin.entity.BindingRecordEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BindingRecordDao {

	//根据user_id查询邀请的用户
	@Select("select * from binding_record where user_id = #{user_id} and binding_status = 1")
	List<BindingRecordEntity> queryByUserId(@Param("user_id") int user_id);

	//数据新增
	@Insert("insert into binding_record(user_id,binding_user_id,`desc`,created_time)" +
			"values(#{user_id},#{binding_user_id},#{desc},now())")
	void insertToBindingRecord(@Param("user_id")int user_id,@Param("binding_user_id")int binding_user_id,
							   @Param("desc")String desc);



}
