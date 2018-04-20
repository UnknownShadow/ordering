package com.juunew.admin.dao;

import com.juunew.admin.entity.UserEntity;
import com.juunew.admin.entity.UserPrivilegeEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserPrivilegeDao {

    //查询总激活人数
    @Select("select count(*) from ${gameDbName}.user_privilege")
    int totalCount();

    //查询总条数
    @Select("select count(*) from ${gameDbName}.user_privilege ${totalCondition}")
    int queryTotalCount(@Param("totalCondition")String totalCondition);

    //分页查询数据
    @Select("select a.userId,a.currentDay,a.created_at,a.lashFinishTime,a.finishDay,b.nickname from ${gameDbName}.user_privilege a," +
            "${gameDbName}.users b where a.userId = b.id ${condition} order by a.lashFinishTime desc limit #{offset},#{limit}")
    List<UserPrivilegeEntity> queryPaging(@Param("offset") int offset,@Param("limit") int limit,
                                          @Param("condition") String condition);


    //查询特权码绑定时间
    @Select("select * from ${gameDbName}.user_privilege where userId=#{user_id} limit 1")
    UserPrivilegeEntity queryBindingTime(@Param("user_id") int user_id);

    //查询当天进度
    @Select("select * from ${gameDbName}.user_privilege where userId =#{user_id} and lashFinishTime is not null limit 1")
    UserPrivilegeEntity queryTodayProgress(@Param("user_id") int user_id);


    //根据 UserId 查询数据
    @Select("select a.userId,a.currentDay,a.created_at,a.lashFinishTime,a.finishDay,b.nickname from ${gameDbName}.user_privilege a," +
            "${gameDbName}.users b where a.userId = b.id and a.userId = #{user_id} and lashFinishTime is not null limit 1")
    UserPrivilegeEntity queryByUserId(@Param("user_id") int user_id);
}
