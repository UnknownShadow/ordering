package com.juunew.admin.dao;

import com.juunew.admin.entity.ShopEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopDao {

	@Insert("insert into shop(id,name,created_at)values(#{id},#{name},now())")
	void insertToShop(@Param("id") int id,@Param("name") String name);
}
