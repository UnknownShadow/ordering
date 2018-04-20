package com.juunew.admin.dao;

import com.juunew.admin.entity.IntegralProductEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IntegralProductDao {

    //查询所有积分换钻石的商品
    @Select("select * from integral_product where type=#{type} order by id limit 8")
    List<IntegralProductEntity> queryAllProduct(@Param("type") int type);

    //根据商品ID查询数据
    @Select("select * from integral_product where id = #{id}")
    IntegralProductEntity queryById(@Param("id") int id);

    //根据商品ID 和type（防止玩家拿到绑定用户的充值价格）查询数据
    @Select("select * from integral_product where id = #{id} and type=#{type}")
    IntegralProductEntity queryProductById(@Param("id") int id,@Param("type") int type);

}
