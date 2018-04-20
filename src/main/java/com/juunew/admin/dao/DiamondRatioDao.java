package com.juunew.admin.dao;

import com.juunew.admin.entity.DiamondRatioEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public interface DiamondRatioDao {
	@Select("select * from diamond_ratio order by id")
	List<DiamondRatioEntity> findAll();

	//根据id查询 数据
	@Select("select * from diamond_ratio where id=#{id}")
	DiamondRatioEntity findById(@Param("id") int id);
}
