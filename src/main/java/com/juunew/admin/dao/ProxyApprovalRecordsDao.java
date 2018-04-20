package com.juunew.admin.dao;

import com.juunew.admin.entity.ProxyApprovalRecordsEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 此类为权限分类与权限的中间表的实体类
 * */
@Repository
public interface ProxyApprovalRecordsDao {

	//将记录插入到数据库中
	@Insert("insert into proxy_approval_records(user_id,nickname,name,phone,source,proxy_hierarchy,address,approve_date)" +
			"values(#{user_id},#{nickname},#{name},#{phone},#{source},#{proxy_hierarchy},#{address},now())")
	void insertToProxyApproval(@Param("user_id") int user_id, @Param("nickname") String nickname,@Param("name") String name,
							   @Param("phone") String phone,@Param("source") int source, @Param("proxy_hierarchy") int proxy_hierarchy,
							   @Param("address") String address);


	//查询是否   是审核中
	@Select("select * from proxy_approval_records where user_id=#{user_id} and dispose_result='' order by approve_date desc limit 1")
	ProxyApprovalRecordsEntity findByUserId(@Param("user_id") int user_id);


	//查询所有需要审核的记录
	@Select("select * from proxy_approval_records where dispose_result=3")
	List<ProxyApprovalRecordsEntity> findApprovalDate();
	//分页查询所有需要审核的记录
	@Select("select * from proxy_approval_records where dispose_result=3 limit #{offset},#{limit}")
	List<ProxyApprovalRecordsEntity> findApprovalDatePaging(@Param("offset") int offset,@Param("limit") int limit);


	//将
	@Update("update proxy_approval_records set dispose_date=#{dispose_date}, dispose_result=#{dispose_result},dispose_name=#{dispose_name} where id=#{id}")
	void updateApprovalRecords(@Param("dispose_date") Date dispose_date,@Param("dispose_result") int dispose_result,@Param("dispose_name") String dispose_name,@Param("id") int id);


	//查询表中所有数据
	@Select("select * from proxy_approval_records")
	List<ProxyApprovalRecordsEntity> findAllDate();
	//分页查询表中所有数据
	@Select("select * from proxy_approval_records order by id desc limit #{offset},#{limit}")
	List<ProxyApprovalRecordsEntity> findAllDatePaging(@Param("offset") int offset,@Param("limit") int limit);


	//根据ID 查 user_id
	@Select("select user_id from proxy_approval_records where id=#{id}")
	ProxyApprovalRecordsEntity findUserIdById(@Param("id") int id);

	//新增一条数据
	@Insert("insert into proxy_approval_records(user_id,nickname,name,phone,source,proxy_hierarchy,address,dispose_result,dispose_name,approve_date,dispose_date)values(#{user_id},#{nickname},#{name},#{phone},#{source},#{proxy_hierarchy},#{address},#{dispose_result},#{dispose_name},#{approve_date},#{dispose_date})")
	void insertToRecords(@Param("user_id") int user_id,@Param("nickname") String nickname,@Param("name") String name,@Param("phone") String phone,@Param("source") int source,@Param("proxy_hierarchy") int proxy_hierarchy,@Param("address") String address,@Param("dispose_result") int dispose_result,@Param("dispose_name") String dispose_name,@Param("approve_date") Date approve_date,@Param("dispose_date") Date dispose_date);

	//查询是否   是审核中
	@Select("select * from proxy_approval_records where user_id=#{userId} order by approve_date desc limit 1")
	ProxyApprovalRecordsEntity findDisposeResult(@Param("userId") int userId);

	//根据user_id 修改dispose_result值
	@Select("update proxy_approval_records set dispose_result=0 where user_id=#{users_id}")
	void updateDisposeResult(@Param("users_id") int users_id);

	//根据user_id 修改dispose_result值
	@Select("delete from proxy_approval_records where user_id=#{users_id} and dispose_result=1")
	void delByUserId(@Param("users_id") int users_id);
}
