package com.juunew.admin.dao;

import com.juunew.admin.entity.RebateRatioEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RebateRatioDao {

    @Select("select * from rebate_ratio where type = #{type} limit 1")
    RebateRatioEntity queryByType(@Param("type") int type);


}
