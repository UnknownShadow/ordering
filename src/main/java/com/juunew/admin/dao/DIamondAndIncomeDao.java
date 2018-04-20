package com.juunew.admin.dao;

import com.juunew.admin.entity.DiamondAndIcomeEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类 为玩家 钻石消耗 获取 日志
 * */
@Repository
public interface DIamondAndIncomeDao {

	//根据user_id 分页查询数据
	@Select("select * from ${gameDbName}.diamonds where user_id=#{userId} and (change_type=102 or change_type=103 or  change_type=104 or " +
			"change_type=105 or  change_type=200 or  change_type=201 or  change_type=202 or  change_type=107 or change_type=303 or change_type=304) and " +
			"change_val>0 order by created_at desc limit #{offset},#{limit}")
	List<DiamondAndIcomeEntity> findByUserId(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);


	//根据user_id查询总条数
	@Select("select count(*) from ${gameDbName}.diamonds where user_id=#{userId} and (change_type=102 or change_type=103 or change_type=104 or " +
			"change_type=105 or  change_type=200 or  change_type=201 or  change_type=202 or  change_type=107) and " +
			"change_val>0")
	int findTotal(@Param("userId") int userId);
}
