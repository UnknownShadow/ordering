package com.juunew.admin.dao;

import com.juunew.admin.entity.TimeConfigEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为活动时间配置类
 * */
@Repository
public interface TimeConfigDao {

	//根据type查询活动时间
	@Select("select * from time_config where type=#{type} order by id desc LIMIT 1")
	TimeConfigEntity queryActionTimeByType(@Param("type") int type);
}
