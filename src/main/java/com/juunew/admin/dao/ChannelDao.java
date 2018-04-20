package com.juunew.admin.dao;

import com.juunew.admin.entity.Channel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelDao {

    @Select("select * from ${gameDbName}.channels where id = #{id}")
    Channel find(@Param("id")int id);
}
