package com.juunew.admin.dao;

import com.juunew.admin.entity.Financial_AuditEntity;
import com.juunew.admin.entity.Financial_DailyEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为权限分类与权限的中间表的实体类
 * */
@Repository
public interface Financial_AuditDao {

	//将需要审批的数据记录下来
	@Insert("insert into financial_audit(send_id,send_name,receive_id,receive_name,diamond,money,audit_status," +
			"audit_date,pay_status)values(#{send_id},#{send_name},#{receive_id},#{receive_name},#{diamond}," +
			"#{money},#{audit_status},#{audit_date},#{pay_status})")
	@Options(useGeneratedKeys=true, keyProperty = "api.id")
	void insertFinancial_Audit(@Param("send_id") int send_id, @Param("send_name") String send_name,
							   @Param("receive_id") int receive_id, @Param("receive_name") String receive_name,
							   @Param("diamond") int diamond, @Param("money") int money,
							   @Param("audit_status") int audit_status, @Param("audit_date") String audit_date,
							   @Param("pay_status") int pay_status, @Param("api") Financial_DailyEntity api);



	//根据刚刚新增的ID 进行流水号的插入
	//@Param("audit_id") String audit_id,
	@Update("update financial_audit set audit_id=#{audit_id} where id=#{id}")
	void updateFinancial_Audit(@Param("audit_id") String audit_id,@Param("id") int id);


	//根据product_id 查询 financial_audit 表
	@Select("select * from financial_audit where id = #{product_id}")
	Financial_AuditEntity findById(@Param("product_id") int product_id);
}
