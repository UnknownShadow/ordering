package com.juunew.admin.dao;

import com.juunew.admin.entity.LogFilesEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 此类为权限分类与权限的中间表的实体类
 * */
@Repository
public interface LogFilesDao {

	//根据 user_id 查询日志文件
	@Select("select * from log_files where  user_id=#{user_id}")
	List<LogFilesEntity> findByUserId(@Param("user_id") int user_id);

	//根据 user_id 分页查询日志文件
	@Select("select * from log_files where  user_id=#{user_id} limit #{xx},#{yy}")
	List<LogFilesEntity> findByUserIdPaging(@Param("user_id") int user_id,@Param("xx") int xx,@Param("yy") int yy);


	//将修改密码日志记录到数据库中
	@Select("insert into log_files(user_id,created_date,event,content,target,diamond_changed)values(#{user_id},now(),#{event},#{content},#{target},#{diamond_changed})")
	void insertToLog(@Param("user_id") int user_id,@Param("event") String event, @Param("content") String content, @Param("target") String target, @Param("diamond_changed") int diamond_changed);

}
