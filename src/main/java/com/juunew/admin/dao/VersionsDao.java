package com.juunew.admin.dao;

import com.juunew.admin.entity.UserEntity;
import com.juunew.admin.entity.VersionsEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 此类为权限分类与权限的中间表的实体类
 * */
@Repository
public interface VersionsDao {
	//将版本更新信息插入到数据库中
	@Insert("insert into ${gameDbName}.versions(`force`,down_url,ver,content,device_type,created_at,user_id)values(#{force},#{down_url},#{ver},#{content},#{device_type},#{created_at},#{user_id});")
	void insertVersions(@Param("force") int force, @Param("down_url") String down_url,@Param("ver") String ver, @Param("content") String content,@Param("device_type") int device_type, @Param("created_at") String created_at, @Param("user_id") int user_id);


	//查询版本信息
	@Select("select * from ${gameDbName}.versions")
	List<VersionsEntity> findByVersions();

	//分页查询版本信息
	@Select("select * from ${gameDbName}.versions order by created_at desc limit #{xx},#{yy}")
	List<VersionsEntity> findByVersionsPaging(@Param("xx") int xx,@Param("yy") int yy);

	//根据id删除数据信息
	@Delete("delete from ${gameDbName}.versions where id=#{version_id}")
	void delVersionsById(@Param("version_id") int version_id);
}
