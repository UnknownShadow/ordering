package com.juunew.admin.dao;

import com.juunew.admin.entity.IntentProxyEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为 意向申请代理
 * */
@Repository
public interface IntentProxyDao {

	@Select("select * from intent_proxy order by id desc limit #{offset},#{limit}")
	List<IntentProxyEntity> findIntentProxyPaging(@Param("offset") int offset,@Param("limit") int limit);

	//查询total值
	@Select("select count(*) from intent_proxy order by id desc")
	int getTotal();

	//修改备注
	@Update("update intent_proxy set remark = #{remark} where id = #{id}")
	void updateRemark(@Param("id") int id,@Param("remark") String remark);

	//修改为已联系
	@Update("update intent_proxy set connection_status = 1 where id = #{id}")
	void updateConnection(@Param("id") int id);

	@Insert("insert into intent_proxy(name,phone,created_time,connection_status)values(#{name},#{phone},now(),0)")
	void insertToIntentProxy(@Param("name") String name,@Param("phone") String phone);
}
