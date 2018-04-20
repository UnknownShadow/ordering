package com.juunew.admin.dao;

import com.juunew.admin.entity.IntegralsEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为积分记录详情
 * */
@Repository
public interface IntegralsDao {

	//新增数据
	@Insert("insert into integrals(user_id,old_val,new_val,change_val,change_type,purpose,created_at)" +
			"values(#{user_id},#{old_val},#{new_val},#{change_val},#{change_type},#{purpose},now())")
	void insertToIntegrals(@Param("user_id") int user_id,@Param("old_val") int old_val,
						   @Param("new_val") int new_val,@Param("change_val") int change_val,
						   @Param("change_type") int change_type,@Param("purpose") String purpose);


	/*@Select("")
	List<IntegralsEntity> findAll(@Param("username") String username);*/
}
