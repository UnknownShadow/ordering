package com.juunew.admin.dao;

import com.juunew.admin.entity.AuditEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为权限分类与权限的中间表的实体类
 * */
@Repository
public interface AuditDao {
	//查询审核表中要审核的数据
	@Select("select * from financial_audit where audit_status=2")
	List<AuditEntity> findAllAudit();


	//对审核表中的数据进行模分查询
	//select * from chaowan_admin.financial_audit where audit_status=2 LIMIT #{xx},#{yy}
	@Select("select * from financial_audit where audit_status=2 LIMIT #{xx},#{yy}")
	List<AuditEntity> AuditPaging(@Param("xx") int xx,@Param("yy") int yy);


	//修改审核的数据
	@Select("update financial_audit set audit_status=#{audit_status},auditor=#{auditor} where id=#{id}")
	void updateAudit(@Param("audit_status") int audit_status,@Param("id") int id,@Param("auditor") String auditor);

	//根据ID查询Audit表中单一的数据
	@Select("select * from financial_audit where id=#{id}")
	AuditEntity findAuditById(@Param("id") int id);


	//查询审核表中所有数据，第一种情况
	@Select("select * from financial_audit where send_id like'%${send_id}%' order by id desc")
	List<AuditEntity> AllAuditData(@Param("send_id") String send_id);

	//查询审核表中所有数据，第二种情况
	@Select("select * from financial_audit where audit_date  BETWEEN #{proxy_date} and #{proxy_date_end} and send_id like'%${send_id}%' order by id desc")
	List<AuditEntity> AllAuditDataOther(@Param("send_id") String send_id,@Param("proxy_date") String proxy_date,@Param("proxy_date_end") String proxy_date_end);


	//对审核表中的数据进行模分查询
	@Select("select * from financial_audit where send_id like '%${send_id}%' order by id desc LIMIT #{xx},#{yy}")
	List<AuditEntity> AuditDataPaging(@Param("xx") int xx,@Param("yy") int yy,@Param("send_id") String send_id);

	//对审核表中的数据进行模分查询,第二种情况
	@Select("select * from financial_audit where audit_date  BETWEEN #{proxy_date} and #{proxy_date_end} and send_id like'%${send_id}%' order by id desc LIMIT #{xx},#{yy}")
	List<AuditEntity> AuditDataPagingOther(@Param("send_id") String send_id,@Param("proxy_date") String proxy_date,@Param("proxy_date_end") String proxy_date_end,@Param("xx") int xx,@Param("yy") int yy);
}
