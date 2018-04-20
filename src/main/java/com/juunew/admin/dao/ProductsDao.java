package com.juunew.admin.dao;

import com.juunew.admin.entity.ProductsEntity;
import com.juunew.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * */
@Repository
public interface ProductsDao {

	//查询表中（products） type=0 为玩家充值
	@Select("select * from products where id=#{id} and type=#{type}")
	ProductsEntity findById(@Param("id") int id,@Param("type") int type);

	//查询表中（products） type=0 为玩家充值
	@Select("select * from products where type=0 order by id desc LIMIT 6")
	List<ProductsEntity> findAll();


	//查询表中（products）
	@Select("select * from products where type=1 order by id desc LIMIT 7")
	List<ProductsEntity> findTypeOne();


	//查询表中（products）type=1 为代理充值
	@Select("select * from products where type=1 order by id desc LIMIT 6")
	List<ProductsEntity> findTypeOneAndLimitSix();


	//根据钻石数量查询数据
	@Select("select * from products where pay_price=#{pay_price} ORDER BY id desc limit 1")
	ProductsEntity findRatioByPrice(@Param("pay_price") int pay_price);


	//查询表中（products）
	@Select("select * from products where id=#{id}")
	ProductsEntity find(@Param("id") int id);

}
