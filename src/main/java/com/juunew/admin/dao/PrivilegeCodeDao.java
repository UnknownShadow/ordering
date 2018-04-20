package com.juunew.admin.dao;

import com.juunew.admin.entity.PrivilegeCodeEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PrivilegeCodeDao {

	//根据user_id 查询激活码（特权码）
	@Select("select * from ${gameDbName}.privilege_code where userId=#{user_id} limit 1")
	PrivilegeCodeEntity findPrivilegeCodeByUserId(@Param("user_id") int user_id);

}
