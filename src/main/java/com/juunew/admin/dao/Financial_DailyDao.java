package com.juunew.admin.dao;

import com.juunew.admin.entity.Financial_DailyEntity;
import com.juunew.admin.entity.GamesEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为财务日报实体类
 * */
@Repository
public interface Financial_DailyDao {

	//对financial_daily表全查，查出总条数
	@Select("select * from financial_daily order by daily_date")
	List<Financial_DailyEntity> findFinancialTotal();

	//对financial_daily表分页查询
	@Select("select * from financial_daily order by daily_date desc LIMIT #{xx},#{yy}")
	List<Financial_DailyEntity> findFinancialAll(@Param("xx") int xx,@Param("yy") int yy);



	//对financial_daily表所有数据求和
	@Select("select sum(confidential_count) confidential_count,sum(thirteen_count) thirteen_count,SUM(fivek) fivek,SUM(fish_count) fish_count,SUM(diamondflow_total) diamondflow_total,SUM(recharge_count) recharge_count,sum(recharge_num) recharge_num,sum(diamondexpend_total) diamondexpend_total from financial_daily")
	Financial_DailyEntity financialSum();


//--------------------------------------------------------------//


	//将分类查询出的数据插入到数据库中
	@Insert("insert into financial_daily(daily_date,total_revenue,apple_recharge,wechat_recharge,platform_recharge,total_recharge,diamond_expend_total,confidential_count,diamond_flow_total,thirteen_count,fivek_count,fish_count)values(#{daily_date},#{total_revenue},#{apple_recharge},#{wechat_recharge},#{platform_recharge},#{total_recharge},#{diamond_expend_total},#{confidential_count},#{diamond_flow_total},#{thirteen_count},#{fivek_count},#{fish_count})")
	void updateFinancialDaily(@Param("daily_date") String daily_date,@Param("total_revenue") int total_revenue,@Param("apple_recharge") int apple_recharge,@Param("wechat_recharge") int wechat_recharge,@Param("platform_recharge") int platform_recharge,@Param("total_recharge") int total_recharge,@Param("diamond_expend_total") int diamond_expend_total,@Param("confidential_count") int confidential_count,@Param("diamond_flow_total") int diamond_flow_total,@Param("thirteen_count") int thirteen_count,@Param("fivek_count") int fivek_count,@Param("fish_count") int fish_count);


	//统计 游戏 开房次数；
	//SELECT * FROM ${gameDbName}.games WHERE gamekinds_id = #{gamekinds_id} AND end_at BETWEEN #{start_date} and #{end_date} and  id not in (${condition})
	@Select("SELECT sum(sign_cost)sign_cost FROM ${gameDbName}.games WHERE gamekinds_id = #{gamekinds_id} AND end_at BETWEEN #{start_date} and #{end_date} and finished=1 and sign_cost > 0")
	GamesEntity countByCreateGame(@Param("gamekinds_id") int gamekinds_id,@Param("start_date") String start_date,@Param("end_date") String end_date);



	@Select("SELECT count(*) FROM ${gameDbName}.games WHERE gamekinds_id = 1 AND end_at BETWEEN #{start_date} and #{end_date} and finished=1 and sign_cost > 0")
	int countByCreateGameForYxx(@Param("start_date") String start_date,@Param("end_date") String end_date);


	//查询游戏开房次数，最新10条记录
	@Select("select * from financial_daily order by daily_date DESC limit 10")
	List<Financial_DailyEntity> findDataLimitTen();

	//上游开房次数
	@Select("SELECT sum(sign_cost)sign_cost FROM ${gameDbName}.games WHERE gamekinds_id = 2 AND end_at BETWEEN #{start_date} and #{end_date} and finished=1 and sign_cost > 0")
	GamesEntity countByCreateGameThirteen(@Param("start_date") String start_date,@Param("end_date") String end_date);


	//统计 麻将 总开房次数；
	@Select("SELECT sum(sign_cost)sign_cost FROM ${gameDbName}.games WHERE gamekinds_id = #{gamekinds_id} AND end_at BETWEEN #{start_date} and #{end_date} and finished=1 and sign_cost > 0")
	GamesEntity countMahjong(@Param("gamekinds_id") int gamekinds_id, @Param("start_date") String start_date, @Param("end_date") String end_date);

}
