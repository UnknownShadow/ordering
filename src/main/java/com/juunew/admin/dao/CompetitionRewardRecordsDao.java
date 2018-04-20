package com.juunew.admin.dao;

import com.juunew.admin.entity.CompetitionRewardRecordsEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 此类为权限分类与权限的中间表的实体类
 * */
@Repository
public interface CompetitionRewardRecordsDao {

	//查询出competition_reward_records表中的所有数据；
	/*@Select("SELECT * FROM ${gameDbName}.competition_reward_records")
	List<CompetitionRewardRecordsEntity> findRewardRecordAll();*/

	//查询出competition_reward_records表中待处理的发货信息
	@Select("SELECT * FROM ${gameDbName}.competition_reward_records WHERE STATUS=#{status}")
	List<CompetitionRewardRecordsEntity> findRewardRecordByStatus(@Param("status") int status);


	//查询出competition_reward_records表中待处理的发货信息
	@Select("SELECT * FROM ${gameDbName}.competition_reward_records WHERE competition_id IN(SELECT id FROM ${gameDbName}.competitions WHERE competition_template_id IN(SELECT id FROM ${gameDbName}.competition_templates WHERE type=#{type}))")
	List<CompetitionRewardRecordsEntity> findRewardRecordByType(@Param("type") int type);
//SELECT * FROM chaowan_gamedb.competition_reward_records WHERE competition_id IN(SELECT id FROM chaowan_gamedb.competitions WHERE competition_template_id IN(SELECT id FROM chaowan_gamedb.competition_templates WHERE TYPE=1))


	//查询实物赛信息SELECT * FROM ${gameDbName}.competition_reward_records WHERE address_info IS NOT NULL
	@Select("SELECT * FROM ${gameDbName}.competition_reward_records WHERE (STATUS=3 OR STATUS=0) AND (created_at BETWEEN #{start_date} AND #{end_date}) AND user_id LIKE '%${user_id}%' ORDER BY created_at DESC ")
	List<CompetitionRewardRecordsEntity> findRewardRecordByAddressInfo(Map map);



	//分页查询competition_reward_records表中的所有数据；
	@Select("SELECT * FROM ${gameDbName}.competition_reward_records WHERE (STATUS=3 OR STATUS=0) AND (created_at BETWEEN #{start_date} AND #{end_date}) AND user_id LIKE '%${user_id}%' ORDER BY created_at DESC limit #{xx},#{yy}")
	List<CompetitionRewardRecordsEntity> findRewardRecordPaging(Map map);


	//分页查询competition_reward_records表中待发货数据；
	/*@Select("SELECT * FROM ${gameDbName}.competition_reward_records where status=#{status}  ORDER BY created_at DESC limit #{xx},#{yy}")
	List<CompetitionRewardRecordsEntity> findRewardRecordPagingByStatus(@Param("status") int status,@Param("xx") int xx,@Param("yy") int yy);
*/

	//根据ID查询address_info中的name,phone,address
	@Select("SELECT JSON_EXTRACT(address_info,'$.name') name,JSON_EXTRACT(address_info,'$.phone') phone,JSON_EXTRACT(address_info,'$.address') address FROM ${gameDbName}.competition_reward_records WHERE id=#{reward_id}")
	CompetitionRewardRecordsEntity findAddresses(@Param("reward_id") int reward_id);


	//根据ID修改competition_reward_records表中的状态
	@Select("UPDATE ${gameDbName}.competition_reward_records SET STATUS=#{status},confirm_content=#{confirm_content} WHERE id=#{id}")
	void updateRewardRecords(@Param("id") int id,@Param("status") int status,@Param("confirm_content") String confirm_content);


	//分页查询competition_reward_records表中所有已经发货（实物）的数据；
	@Select("SELECT * FROM ${gameDbName}.competition_reward_records WHERE (STATUS=1 or status=4) AND address_info IS NOT NULL limit #{xx},#{yy}")
	List<CompetitionRewardRecordsEntity> findByAddressIsNotNull(@Param("xx") int xx,@Param("yy") int yy);



	//查询competition_reward_records表中所有已经发货（实物）的数据；
	@Select("SELECT * FROM ${gameDbName}.competition_reward_records WHERE (status=1 or status=4) and (created_at between #{start_date} and #{end_date}) and user_id like '%${user_id}%'")
	List<CompetitionRewardRecordsEntity> findByRewardRecords(Map map);
	//查询competition_reward_records表中所有已经发货（实物）的数据；
	@Select("SELECT * FROM ${gameDbName}.competition_reward_records WHERE (status=1 or status=4) and (created_at between #{start_date} and #{end_date}) and user_id like '%${user_id}%' AND address_info IS NOT NULL")
	List<CompetitionRewardRecordsEntity> findByAddressNotNull(Map map);
	//查询competition_reward_records表中所有已经发货（实物）的数据；
	@Select("SELECT * FROM ${gameDbName}.competition_reward_records WHERE (status=1 or status=4) and (created_at between #{start_date} and #{end_date}) and user_id like '%${user_id}%' AND address_info is null")
	List<CompetitionRewardRecordsEntity> findByAddressNull(Map map);

	//分页查询competition_reward_records表中所有已经发货（实物）的数据；
	@Select("SELECT * FROM ${gameDbName}.competition_reward_records WHERE (status=1 or status=4) and (created_at between #{start_date} and #{end_date}) and user_id like '%${user_id}%' ORDER BY created_at desc limit #{xx},#{yy}")
	List<CompetitionRewardRecordsEntity> findByAddressPaging(Map map);
	//分页查询competition_reward_records表中所有已经发货（实物）的数据；
	@Select("SELECT * FROM ${gameDbName}.competition_reward_records WHERE (status=1 or status=4) and (created_at between #{start_date} and #{end_date}) and user_id like '%${user_id}%' AND address_info IS NOT NULL ORDER BY created_at desc limit #{xx},#{yy}")
	List<CompetitionRewardRecordsEntity> findByAddressNotNullPaging(Map map);
	//分页查询competition_reward_records表中所有已经发货（实物）的数据；
	@Select("SELECT * FROM ${gameDbName}.competition_reward_records WHERE (status=1 or status=4) and (created_at between #{start_date} and #{end_date}) and user_id like '%${user_id}%' AND address_info IS NULL ORDER BY created_at desc limit #{xx},#{yy}")
	List<CompetitionRewardRecordsEntity> findByAddressNullPaging(Map map);




	//如果有日期查询，走此方法,
	@Select("SELECT * FROM ${gameDbName}.competition_reward_records WHERE STATUS=1 AND address_info IS NOT NULL AND id IN (SELECT id FROM ${gameDbName}.competition_templates WHERE end_time>=#{proxy_date} AND end_time<=#{proxy_date_end})")
	List<CompetitionRewardRecordsEntity> findByAddressPagingDate(@Param("proxy_date") String proxy_date,@Param("proxy_date_end") String proxy_date_end);

	//如果有日期查询，走此方法, 分页查询competition_reward_records表中所有已经发货（实物）的数据；
	@Select("SELECT * FROM ${gameDbName}.competition_reward_records WHERE STATUS=1 AND address_info IS NOT NULL AND id IN (SELECT id FROM ${gameDbName}.competition_templates WHERE end_time>=#{proxy_date} AND end_time<=#{proxy_date_end}) limit #{xx},#{yy}")
	List<CompetitionRewardRecordsEntity> findByAddressIsNotNullDate(@Param("xx") int xx,@Param("yy") int yy,@Param("proxy_date") String proxy_date,@Param("proxy_date_end") String proxy_date_end);


}
