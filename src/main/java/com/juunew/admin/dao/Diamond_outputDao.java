package com.juunew.admin.dao;

import com.juunew.admin.entity.Diamond_outputEntity;
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
public interface Diamond_outputDao {

	//查询diamond_output表中的数据条数；
	@Select("select * from diamond_output order by output_date desc")
	List<Diamond_outputEntity> diamondOutputTotal();
	//查询diamond_output表中的数据条数；有日期查询
	@Select("select * from diamond_output where output_date between #{start_date} and #{end_date} order by output_date desc")
	List<Diamond_outputEntity> diamondOutputTotalByDate(@Param("start_date") String start_date,@Param("end_date") String end_date);


	//分页查询数据
	@Select("select * from diamond_output order by output_date desc limit #{xx},#{yy}")
	List<Diamond_outputEntity> diamondOutputTotalPaging(@Param("xx") int xx,@Param("yy") int yy);
	//分页查询数据
	@Select("select * from diamond_output where output_date between #{start_date} and #{end_date} order by output_date desc limit #{xx},#{yy}")
	List<Diamond_outputEntity> diamondOutputTotalPagingByDate(@Param("start_date") String start_date,@Param("end_date") String end_date,@Param("xx") int xx,@Param("yy") int yy);




	//从客户端获取钻石产生详情数据
	@Select("INSERT into diamond_output(create_date,daily_output,signin_output,treasure_output,msg_reward,signin_count,opentreasure_count,msgreceive_count)values(#{create_date},#{daily_output},#{signin_output},#{treasure_output},#{msg_reward},#{signin_count},#{opentreasure_count},#{msgreceive_count})")
	void insertDiamOutput(@Param("create_date") String create_date, @Param("daily_output") int daily_output, @Param("signin_output") int signin_output,@Param("treasure_output") int treasure_output, @Param("msg_reward") int msg_reward,@Param("signin_count") int signin_count, @Param("opentreasure_count") int opentreasure_count, @Param("msgreceive_count") int msgreceive_count);




	//查询diamond_output表中的数据；
	@Select("select SUM(count)daily_output from ${gameDbName}.competition_rewards where created_at between #{start_date} and #{end_date} and get_type=1")
	Diamond_outputEntity dailyOutput(@Param("start_date") String start_date, @Param("end_date") String end_date);

	////签到产出  和 签到次数；
	@Select("select sum(gift_num)sign_output,count(*)sign_count  from ${gameDbName}.signs where created_at between #{start_date} and #{end_date}")
	Diamond_outputEntity signOutput(@Param("start_date") String start_date, @Param("end_date") String end_date);

	//宝箱产出
	@Select("select SUM(gift_num)treasure_output ,count(*)treasure_count from ${gameDbName}.treasures where created_at between #{start_date} and #{end_date} and gift_num>0")
	Diamond_outputEntity treasureOutput(@Param("start_date") String start_date, @Param("end_date") String end_date);

	//消息奖励
	@Select("select * from ${gameDbName}.push_msg_records where read_status=2 and created_at BETWEEN #{start_date} and #{end_date}")
	List<Diamond_outputEntity> msgOutput(@Param("start_date") String start_date, @Param("end_date") String end_date);

	//消息奖励领取次数
	@Select("select count(*) msg_count from ${gameDbName}.push_msg_records where read_status=2 and created_at BETWEEN #{start_date} and #{end_date}")
	Diamond_outputEntity msgCount(@Param("start_date") String start_date, @Param("end_date") String end_date);


	//将统计过后的数据插入数据库中
	@Insert("insert into diamond_output(output_date,daily_output,sign_count,sign_output,treasure_output,treasure_count,msg_count,msg_output,output_total,novice_boot)values(#{output_date},#{daily_output},#{sign_count},#{sign_output},#{treasure_output},#{treasure_count},#{msg_count},#{msg_output},#{output_total},#{novice_boot})")
	void insertDiamondOutput(@Param("output_date") String output_date, @Param("daily_output") int daily_output, @Param("sign_count") int sign_count, @Param("sign_output") int sign_output, @Param("treasure_output") int treasure_output, @Param("treasure_count") int treasure_count, @Param("msg_count") int msg_count, @Param("msg_output") int msg_output, @Param("output_total") int output_total, @Param("novice_boot") int novice_boot);

}
