package com.juunew.admin.dao;

import com.juunew.admin.entity.TemporaryEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为权限分类与权限的中间表的实体类
 * */
@Repository
public interface TemporaryDao {
	//将查出数据存入数据库中的临时表中；
	@Select("insert into temporary(insert_date,users_id,nickname,diamond,out_count,in_count,out_num,avg,in_num)values(#{insert_date},#{users_id},#{nickname},#{diamond},#{out_count},#{in_count},#{out_num},#{avg},#{in_num})")
	void insertToTemp(@Param("insert_date") String insert_date, @Param("users_id") int users_id, @Param("nickname") String nickname,@Param("diamond") int diamond, @Param("out_count") int out_count,@Param("in_count") int in_count, @Param("out_num") int out_num,@Param("avg") double avg, @Param("in_num") int in_num);


	//将临时表temporary中的数据根据日期在进行排序查询出来；
	@Select("select * from temporary where insert_date=#{insert_date} order by case when 1=#{ordered} then id end,case when 2=#{ordered} then in_num end,case when 3=#{ordered} then out_num end,case when 4=#{ordered} then nickname end,case when 5=#{ordered} then users_id end desc ")
	List<TemporaryEntity> findTempByDate(@Param("insert_date") String insert_date, @Param("ordered") int ordered);


	//将临时表temporary中的数据根据日期删除
	@Select("DELETE from temporary where insert_date=#{insert_date}")
	void delTempByDate(@Param("insert_date") String insert_date);

}
