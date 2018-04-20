package com.juunew.admin.dao;

import com.juunew.admin.entity.Diamond_consumeEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为权限分类与权限的中间表的实体类
 * */
@Repository
public interface Diamond_consumeDao {
	//分页查询数据
	@Select("select * from diamond_consume order by consume_date  desc LIMIT #{xx},#{yy}")
	List<Diamond_consumeEntity> findPaging(@Param("xx") int xx, @Param("yy") int yy);
	//分页查询数据 ；有日期的情况下
	@Select("select * from diamond_consume where consume_date between #{start_date} and #{end_date} order by consume_date  desc LIMIT #{xx},#{yy}")
	List<Diamond_consumeEntity> findPagingBydate(@Param("start_date") String start_date,@Param("end_date") String end_date,@Param("xx") int xx, @Param("yy") int yy);



	//根据日期进行分组函数将数据累加,统计数据总条数；第一种情况
	@Select("select * from diamond_consume")
	List<Diamond_consumeEntity> findBydateTotal();
	//有日期查询的情况
	@Select("select * from diamond_consume where consume_date between #{start_date} and #{end_date}")
	List<Diamond_consumeEntity> findBydateTotalByDate(@Param("start_date") String start_date,@Param("end_date") String end_date);


//----------------------------------------------------------------------------/

	//将统计出来的数据记录在diamond_consume表中
	@Insert("insert into diamond_consume(consume_date,consume_total,room_consume,treasure_consume,competition_consume,mystery_treasures_count,mystery_treasures_consume,gold_count,gold_consume,silver_count,silver_consume,thirteen_count,fivek_count,fish_count,competition_count)values(#{consume_date},#{consume_total},#{room_consume},#{treasure_consume},#{competition_consume},#{mystery_treasures_count},#{mystery_treasures_consume},#{gold_count},#{gold_consume},#{silver_count},#{silver_consume},#{thirteen_count},#{fivek_count},#{fish_count},#{competition_count})")
	void insertConsumeTotal(@Param("consume_date") String consume_date, @Param("consume_total") int consume_total, @Param("room_consume") int room_consume,@Param("treasure_consume") int treasure_consume, @Param("competition_consume") int competition_consume,@Param("mystery_treasures_count") int mystery_treasures_count, @Param("mystery_treasures_consume") int mystery_treasures_consume, @Param("gold_count") int gold_count,@Param("gold_consume") int gold_consume, @Param("silver_count") int silver_count,@Param("silver_consume") int silver_consume, @Param("thirteen_count") int thirteen_count, @Param("fivek_count") int fivek_count, @Param("fish_count") int fish_count, @Param("competition_count") int competition_count);



}
