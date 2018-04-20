package com.juunew.admin.dao;

import com.juunew.admin.entity.GameUserEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface GameUserDao {

    //根据游戏用户名id查询数据
    @Select("SELECT * from ${gameDbName}.users where id = #{id}")
    GameUserEntity findByUserId(@Param("id") int id);

    //查询所有用户ID
    @Select("select id from ${gameDbName}.users")
    List<GameUserEntity> queryAllUserId();


    //根据游戏用户名id查询数据
    @Select("SELECT * FROM user a LEFT JOIN ${gameDbName}.users b ON a.users_id = b.id LEFT JOIN ${gameDbName}.user_banks c ON b.id = c.user_id where a.users_id = #{id}")
    List<GameUserEntity> findById(@Param("id") int id);


    //根据游戏用户名id查询相对应的权限操作
    @Select("select * from role where id in (select role_id from allot where role_id = (select role_id from user where users_id = #{id}))")
    GameUserEntity findPermisssion(@Param("id") int id);


    //SELECT * FROM ${gameDbName}.users a LEFT JOIN ${gameDbName}.user_banks b ON a.id=b.user_id WHERE a.id = #{id}
    //在users表中根据id查询用户信息//
    @Select("SELECT * FROM user a LEFT JOIN ${gameDbName}.users b ON a.users_id = b.id WHERE a.users_id = #{id}")
    GameUserEntity findusers(@Param("id") int id);

    //根据userID查询diamond (users_bank表)
    @Select("select * from ${gameDbName}.user_banks where user_id= #{id}")
    GameUserEntity findDiamByUserId(@Param("id") int id);


    //查询users表中是否有该ID的数据，如果有，则在user表中建立，如果没有，则返回无此数据SELECT * from user a left JOIN ${gameDbName}.users b on a.users_id=b.id where b.id=#{userId}
    //;SELECT * FROM ${gameDbName}.users WHERE id=#{id}
    @Select("SELECT * from ${gameDbName}.users a LEFT JOIN user b on a.id=b.users_id where a.id=#{userId}")
    GameUserEntity findDataByusersId(@Param("userId") int userId);


    //在chaowan_admin.user表中建立users表的对应数据关系INSERT INTO users (#{user})
    @Select("insert into user(username,password,role_id,user_status,users_id)values(#{username},#{pass},6,3,#{id})")
    void insertData(@Param("username") String username, @Param("pass") String pass, @Param("id") int id);


    //根据users_id修改数据1 ,role_id=5
    @Update("UPDATE user set login_status=1,user_status = #{user_status},fatherproxy_id=1,fatherproxy_date=now() where users_id = #{user_id}")
    void revise(@Param("user_id") int user_id, @Param("user_status") int user_status);

    //根据users_id修改数据1 ,role_id=5
    @Update("UPDATE user set role_id=5,login_status=1,user_status = #{user_status},fatherproxy_id=1,fatherproxy_date=now() where users_id = #{users_id}")
    void updateRoleStatus(@Param("users_id") int users_id, @Param("user_status") int user_status);


    //status为3（玩家）时；根据users_id修改数据1
    @Update("UPDATE user set login_status=1,user_status = #{user_status},fatherproxy_id=0,fatherproxy_date='',invite_code=0 where users_id = #{user_id}")
    void cleanProxy(@Param("user_id") int user_id, @Param("user_status") int user_status);


    //根据users_id修改数据2
    @Select("UPDATE user set role_id=#{role_id},login_status=1 where users_id = #{users_id}")
    GameUserEntity reviseOther(@Param("users_id") int users_id, @Param("role_id") int role_id);


    //根据游戏用户名查询数据
    @Select("SELECT fatherproxy_id,users_id,user_status,username,nickname from user a left JOIN ${gameDbName}.users b on a.users_id=b.id where a.username=#{username}")
    List<GameUserEntity> findByUsername(@Param("username") String username);


    //根据 User_id 查询diamond,money
    @Select("SELECT * FROM ${gameDbName}.user_banks WHERE user_id=#{users_id}")
    GameUserEntity findByDiamondAndMoney(@Param("users_id") int users_id);


    //根据游戏用户名查询数据
    @Select("SELECT * from user a where a.fatherproxy_id=#{id}")
    List<GameUserEntity> findchildproxy(@Param("id") int id);


    //根据用户ID查询所有的子代理
    @Select("select * from user where fatherproxy_id=#{users_id}")
    List<GameUserEntity> findAllProxy(@Param("users_id") int users_id);

    //对所有子代理分页查询
    @Select("SELECT fatherproxy_date,users_id,user_status,username,nickname from user a left JOIN ${gameDbName}.users b " +
            "on a.users_id=b.id where fatherproxy_id=#{id} and superior_proxy_status=1 limit #{CurrentPage},#{limit}")
    List<GameUserEntity> proxyPaging(@Param("id") int id, @Param("CurrentPage") int CurrentPage, @Param("limit") int limit);

    //对所有子代理分页查询<查询diamond和money>
    @Select("SELECT * FROM ${gameDbName}.user_banks WHERE user_id=#{users_id}")
    GameUserEntity proxyPagingDiamondAndMoney(@Param("users_id") int users_id);


    //添加子代理接口
    @Select("update user set login_status=1,role_id=#{role_id},fatherproxy_id=#{users_id},fatherproxy_date=#{fatherproxy_date},user_status=#{user_status},superior_proxy_status=#{superior_proxy_status} where users_id=#{proxy_id}")
    void QRAddProxy(@Param("role_id") int role_id, @Param("users_id") int users_id, @Param("proxy_id") int proxy_id, @Param("fatherproxy_date") String fatherproxy_date, @Param("user_status") int user_status, @Param("superior_proxy_status") int superior_proxy_status);


    //添加子代理接口
    @Select("update user set login_status=1,role_id=#{role_id},fatherproxy_id=#{users_id}," +
            "fatherproxy_date=#{fatherproxy_date},user_status=#{user_status} where users_id=#{proxy_id}")
    void addProxy(@Param("role_id") int role_id, @Param("users_id") int users_id, @Param("proxy_id") int proxy_id,
                  @Param("fatherproxy_date") String fatherproxy_date, @Param("user_status") int user_status);


    // 查找用户身份；
    @Select("select user_status from user where users_id=#{users_id}")
    GameUserEntity findByID(@Param("users_id") int users_id);

    //根据ID查询users表中的nicKname
    @Select("select nickname from ${gameDbName}.users where id=#{users_id}")
    GameUserEntity findNickName(@Param("users_id") int users_id);


    //根据子代理ID查出该子代理有多少钻石
    @Select("select diamond from ${gameDbName}.user_banks where user_id=#{users_id}")
    Integer diamond(@Param("users_id") int users_id);

    //根据当前用户id查询nickname
    @Select("select nickname from ${gameDbName}.users where id=#{users_id}")
    GameUserEntity findNickname(@Param("users_id") int users_id);

    @Select("INSERT INTO users (id,nickname,identity,permission,diamond,money)VALUES(112,'3123','31','13',13,13)where id = #{id}")
    List<GameUserEntity> find(@Param("id") int id);


    //根据登录用户ID查询手机号码，用于修改密码；
    @Select("SELECT phone FROM ${gameDbName}.users WHERE id=#{id}")
    GameUserEntity findByPhone(@Param("id") int id);

    //根据手机号码查询记录
    @Select("SELECT * FROM ${gameDbName}.users WHERE phone=#{phone} limit 1")
    GameUserEntity qureyPhone(@Param("phone") String phone);

    //根据登录用户ID查询手机号码，用于修改密码；
    @Select("select id from ${gameDbName}.users")
    List<GameUserEntity> findAllUsersID();


    //根据登录用户ID查询手机号码，用于修改密码；
    @Select("select * from ${gameDbName}.users where token=#{token} limit 1")
    GameUserEntity findUsersIDByToken(@Param("token") String token);

    //根据登录用户ID查询token
    @Select("select * from ${gameDbName}.users where id=#{id}")
    GameUserEntity findToken(@Param("id") int id);


    //查询是否已经成为代理了；select * from user where users_id=34 and fatherproxy_id is not null
    @Select("select * from user where users_id=#{u_id} and fatherproxy_id is not null and fatherproxy_id!=0")
    GameUserEntity findFatherProxyId(@Param("u_id") int u_id);


    //根据user_id查询数据
    @Select("select username from user where users_id=#{user_id}")
    GameUserEntity findUsername(@Param("user_id") int user_id);


    //查询所有拥有后台权限的用户
    @Select("select * from user where role_id!=6")
    List<GameUserEntity> findAllRoleUser();

    //查询所有拥有后台权限的用户，并进行分页查询
    @Select("select * from user where role_id!=6 order BY login_time desc limit #{xx},#{yy}")
    List<GameUserEntity> findAllRoleUserPaging(@Param("xx") int xx, @Param("yy") int yy);


    //根据users_id将role_id修改为6
    @Update("UPDATE user set role_id=6 where users_id = #{users_id}")
    void updateRole(@Param("users_id") int users_id);


    //登录成功后将登录时间记录下来
    @Update("UPDATE user set login_time=now() where users_id = #{users_id}")
    void updateLoginTime(@Param("users_id") int users_id);


    //根据users_id查询出该登录的用户是什么游戏用户身份权限(user_status)
    @Select("select * from user where users_id=#{users_id} limit 1")
    GameUserEntity findUserStatus(@Param("users_id") int users_id);
    //根据users_id查询出该登录的用户是什么游戏用户身份权限(user_status)
    @Select("select user_status,users_id from user where users_id=#{user_id} limit 1")
    GameUserEntity queryUserStatus(@Param("user_id") int user_id);

    //根据users_id查询出该登录的用户是什么游戏用户身份权限(user_status)
    @Select("select * from user where users_id=#{users_id} and fatherproxy_id!=1 and fatherproxy_id is not null")
    GameUserEntity findProxy(@Param("users_id") int users_id);

    //根据代理关系状态
    @Select("update user set superior_proxy_status=#{status},user_status=#{user_status} where user_id=#{user_id}")
    void updateSuperiorProxyStatus(@Param("users_id") int users_id, @Param("status") int status, @Param("user_status") int user_status);


    //根据users_id查询 该user_id是否已经成为代理，，，
    @Select("select * from user where users_id=#{user_id}")
    GameUserEntity findFatherProxyByUserId(@Param("user_id") int user_id);


    //修改user_status=2,role_id=5,fatherproxy_id=1  根据user_id，，，
    @Update("update user set user_status=2,role_id=5,fatherproxy_id=1,fatherproxy_date=#{fatherproxy_date},login_status=1 where users_id=#{user_id}")
    void updateProxy(@Param("fatherproxy_date") String fatherproxy_date, @Param("user_id") int user_id);


    //user 表中数据为空时，新增一条数据；
    @Insert("insert into user(username,password,role_id,user_status,users_id,fatherproxy_id,fatherproxy_date,login_status)values(#{username},#{password},#{role_id},#{user_status},#{user_id},1,#{fatherproxy_date},1)")
    void insertProxy(@Param("username") String username, @Param("password") String password, @Param("role_id") int role_id, @Param("user_status") int user_status, @Param("user_id") int user_id, @Param("fatherproxy_date") String fatherproxy_date);

    //根据 user_id 将代理升级为至尊5
    @Update("update user set user_status=#{user_status},superior_proxy_status=1 where users_id=#{user_id}")
    void updateUserStatus(@Param("user_status") int user_status, @Param("user_id") int user_id);


    //根据 user_id 修改 登录状态 为 1
    @Update("update user set login_status=1 where users_id=#{user_id}")
    void updateLoginStatus(@Param("user_id") int user_id);

    //根据 user_id 修改 密码
    @Update("update user set password=#{password} where users_id=#{user_id}")
    void updatePassword(@Param("user_id") int user_id, @Param("password") String password);


    //根据 user_id 增加 积分
    @Update("update user set integral=integral+#{integral} where users_id=#{user_id}")
    void addIntegral(@Param("user_id") int user_id, @Param("integral") int integral);


    //根据 当前时间查询当日新增人数
    @Select("select count(*) from ${gameDbName}.users where created_at BETWEEN #{start_date} and #{end_date}")
    int countNewUsers(@Param("start_date") String start_date, @Param("end_date") String end_date);

    @Select("select count(*) from ${gameDbName}.users where created_at BETWEEN #{start_date} and #{end_date} and (clientinfo->'$.device_type'=3 or clientinfo->'$.device_type'=1 or clientinfo->'$.device_type'=4 or clientinfo->'$.device_type'=5)")
    int countNewUsersForIos(@Param("start_date") String start_date, @Param("end_date") String end_date);

    @Select("select count(*) from ${gameDbName}.users where created_at BETWEEN #{start_date} and #{end_date} and (clientinfo->'$.device_type'=2 or clientinfo->'$.device_type' is null) ")
    int countNewUsersForAndroid(@Param("start_date") String start_date, @Param("end_date") String end_date);

    //将所以代理查询出来
    @Select("select * from user a LEFT JOIN ${gameDbName}.users b on a.users_id=b.id where user_status!=3 or (fatherproxy_id is not null and fatherproxy_id!=0)")
    List<GameUserEntity> findProxyTotal();

    //将所以代理查询出来
    @Select("select * from user a LEFT JOIN ${gameDbName}.users b on a.users_id=b.id where (users_id like '%${user_id}%' or fatherproxy_id like '%${user_id}%') and user_status!=3 and (fatherproxy_id is not null and fatherproxy_id!=0)")
    List<GameUserEntity> findProxyList(Map map);
    //将所以代理查询出来
    @Select("select * from user a LEFT JOIN ${gameDbName}.users b on a.users_id=b.id where " +
            "user_status!=3 or (fatherproxy_id is not null and fatherproxy_id!=0)")
    List<GameUserEntity> queryProxyList();


    //将所以代理查询出来 （代理总览页面）条数
    @Select("select count(*) from user where (users_id like '%${user_id}%' or fatherproxy_id like '%${user_id}%') and " +
            "user_status!=3 and fatherproxy_id is not null and fatherproxy_id!=0")
    int queryProxyTotalCount(Map map);
    //分页查询所以代理
    @Select("select * from user where (users_id like '%${user_id}%' or fatherproxy_id like '%${user_id}%') and " +
            "user_status!=3 and fatherproxy_id is not null and fatherproxy_id!=0 ${order} limit #{offset},#{limit}")
    List<GameUserEntity> queryProxyListPaging(Map map);



    //将所以代理 分页查询出来
    @Select("select * from user a LEFT JOIN ${gameDbName}.users b on a.users_id=b.id where (users_id like '%${user_id}%' " +
            "or fatherproxy_id like '%${user_id}%') and (fatherproxy_id is not null and fatherproxy_id!=0) and user_status!=3 limit #{xx},#{yy}")
    List<GameUserEntity> findProxyListPaging(Map map);


    //查询总积分
    @Select("select integral from user where users_id = #{user_id}")
    GameUserEntity findIntegralByUserId(@Param("user_id") int user_id);


    //总积分根据User_id 和积分提现值 扣除积分
    @Update("update user set integral=integral-#{integral} where users_id = #{user_id}")
    void deductionIntegral(@Param("integral") int integral, @Param("user_id") int user_id);


    //（调整赠送比例者）
    @Update("update user set adjuster=#{adjuster} where users_id = #{user_id}")
    void updateAdjuster(@Param("adjuster") String adjuster, @Param("user_id") int user_id);


    //根据昵称或手机号查询userID
    @Select("select * from ${gameDbName}.users where nickname=#{pn} or phone=#{pn} or id=#{pn} limit 1")
    GameUserEntity findByPhoneOrNickname(@Param("pn") String pn);


    //根据日期查询所有新增的用户<折线统计图用>
    @Select("select * from ${gameDbName}.users where created_at BETWEEN #{start_time} and #{end_time}")
    List<GameUserEntity> findAddUserByDate(@Param("start_time") String start_time, @Param("end_time") String end_time);

    //根据日期查询所有新增的用户
    @Select("select id,created_at,nickname from ${gameDbName}.users where created_at BETWEEN #{start_time} and #{end_time} limit #{xx},#{yy}")
    List<GameUserEntity> addUserByDatePaging(@Param("start_time") String start_time, @Param("end_time") String end_time,
                                             @Param("xx") int xx, @Param("yy") int yy);

    //查询总量
    @Select("select count(*) from ${gameDbName}.users where created_at BETWEEN #{start_time} and #{end_time}")
    int queryTotalByTime(@Param("start_time") String start_time, @Param("end_time") String end_time);


    //根据userId查询user表中是否存在数据
    @Select("select * from user where users_id=#{user_id}")
    GameUserEntity findUserByUserId(@Param("user_id") int user_id);

    //根据userId查询user表中是否存在数据
    @Select("select * from ${gameDbName}.users where id=#{user_id}")
    GameUserEntity findUsersInfo(@Param("user_id") int user_id);


    //查询积分大于0的所有用户
    @Select("select * from user where integral>0 and users_id like '%${user_id}%' ${order} limit #{xx},#{yy}")
    List<GameUserEntity> allEffectiveIntegral(Map map);

    //查询积分大于0的所有用户
    @Select("select count(*) from user where integral>0 and users_id like '%${user_id}%'")
    int totalEffectiveIntegral(@Param("user_id") String user_id);


    //根据时间查询流失用户
    /*@Select("select id from ${gameDbName}.users where id not in" +
            "(select DISTINCT user_id from ${gameDbName}.realtime_online_histories where created_at between #{start_time} and #{end_time})" +
            " and id not in(select user_id from flow_away_daily) and id not in(1)")
    List<GameUserEntity> queryFlowAwayByDate(@Param("start_time") String start_time,@Param("end_time") String end_time);
  */

    //查询总条数
    @Select("select count(id) from ${gameDbName}.users where id not in(${id})")
    int queryFlowAwayTotal(@Param("id") String id);

    //根据时间查询流失用户
    @Select("select id from ${gameDbName}.users where id not in(${id})")
    List<GameUserEntity> queryFlowAwayByDate(@Param("id") String id);

    //根据时间查询流失用户
    @Select("select id from ${gameDbName}.users where id not in(${id}) limit #{limit},#{offset}")
    List<GameUserEntity> queryFlowAwayByDatePaging(@Param("id") String id, @Param("offset") int offset, @Param("limit") int limit);



    //根据邀请码查询是否有相同的邀请码
    @Select("select * from user where invite_code=#{invite_code} limit 1")
    GameUserEntity findByInviteCode(@Param("invite_code") int invite_code);

    //根据邀请码查询是否有相同的邀请码
    @Update("update user set invite_code=#{invite_code} where users_id = #{user_id}")
    void updateInviteCode(@Param("invite_code") int invite_code,@Param("user_id") int user_id);


    //查询所有邀请的玩家
    @Select("select * from user where fatherproxy_id=#{user_id} and user_status = 3")
    List<GameUserEntity> queryAllInvite(@Param("user_id") int user_id);
    //查询所有子代理除邀请玩家外
    @Select("select * from user where fatherproxy_id=#{user_id} and user_status!=3")
    List<GameUserEntity> queryAllProxy(@Param("user_id") int user_id);

    @Select("select * from user where fatherproxy_id=#{id} and users_id = #{user_id} limit 1")
    GameUserEntity queryMyProxy(@Param("id") int id,@Param("user_id") int user_id);

    //查询所有子代理（不包括子代理的子代理）
    @Select("select id,users_id,user_status,fatherproxy_id from user where fatherproxy_id=#{id}")
    List<GameUserEntity> allSubAgent(@Param("id") int id);

    //查询二级代理所有的绑定的玩家
    @Select("select count(users_id) fatherproxy_id from user where fatherproxy_id=#{id} and user_status=3")
    int queryBindingUser(@Param("id") int id);

    //修改上级代理 代理时间
    @Update("update user set fatherproxy_date=now() where users_id=#{user_id}")
    void updateSuperDate(@Param("user_id")int user_id);


    //根据用户身份查询数据
    @Select("select a.users_id,b.nickname from user a,${gameDbName}.users b where a.users_id = b.id and a.user_status = #{user_status}")
    List<GameUserEntity> queryByUserStatus(@Param("user_status") int user_status);

    //查询一级代理总人数
    @Select("select count(users_id) from user where fatherproxy_id=#{user_id} and user_status =1")
    int querySuperAgentTotal(@Param("user_id") int user_id);
    //查询二级代理总人数
    @Select("select count(users_id) from user where fatherproxy_id in(select users_id from user where fatherproxy_id=#{user_id} " +
            "and user_status =1) and user_status=2")
    int querySubAgentTotal(@Param("user_id") int user_id);



    //查询合伙人下的所有总代理
    @Select("select users_id from user where fatherproxy_id=#{user_id} and user_status =#{user_status}")
    List<GameUserEntity> findByFatherProxyId(@Param("user_id") int user_id,@Param("user_status") int user_status);

    //查询合伙人下的所有总代理（总条数）
    @Select("select count(*) from user where fatherproxy_id=#{user_id} and user_status =#{user_status}")
    int queryTotalCount(@Param("user_id") int user_id,@Param("user_status") int user_status);




    /**
     * 总代理查询自己所有绑定的玩家（包括下级的下级绑定的玩家）
     * @param user_id   总代理ID
     */
    @Select("select users_id from user where fatherproxy_id=#{user_id} and user_status = 3 union all " +
            "select users_id from user where user_status = 3 and fatherproxy_id in" +
            "(select users_id from user where fatherproxy_id=#{user_id} and user_status = 2)")
    List<GameUserEntity> queryAllBindingPlayer(@Param("user_id") int user_id);


    /**
     * 总代理查询自己的所有下级（及下级的下级），包括绑定玩家（不包括自己在内）
     * @param user_id   总代理的ID
     */
    @Select("select users_id from user where fatherproxy_id=#{user_id} and user_status =3 union all " +
            "select users_id from user where fatherproxy_id=#{user_id} and user_status = 2 union all " +
            "select users_id from user where fatherproxy_id in" +
            "(select users_id from user where fatherproxy_id=#{user_id} and user_status = 2)")
    List<GameUserEntity> queryAllSubordinate(@Param("user_id")int user_id);


    /**
     *  合伙人查询自己所有的下级（总代理、代理、绑定的玩家）不包括合伙人自己
     * @param user_id   合伙人ID
     */
    @Select("select users_id from user where fatherproxy_id=#{user_id} and user_status =1 union all " +
            "select users_id from user where fatherproxy_id in" +
            "(select users_id from user where fatherproxy_id=#{user_id} and user_status =1) and user_status=3 union all " +
            "select users_id from user where fatherproxy_id in" +
            "(select users_id from user where fatherproxy_id=#{user_id} and user_status =1) and user_status=2 union all " +
            "select users_id from user where fatherproxy_id in (select users_id from user where fatherproxy_id in" +
            "(select users_id from user where fatherproxy_id=#{user_id} and user_status =1) and user_status=2)")
    List<GameUserEntity> queryPartnerSubordinate(@Param("user_id")int user_id);
    /**
     //查询总代理下的一级代理
     select * from user where fatherproxy_id=781 and user_status =1

     //查询总代理下的一级代理下的 一级代理绑定的玩家
     select * from user where fatherproxy_id in(select users_id from user where fatherproxy_id=781 and user_status =1) and user_status=3

     //查询总代理下的一级代理下的 二级代理
     select * from user where fatherproxy_id in(select users_id from user where fatherproxy_id=781 and user_status =1) and user_status=2

     //查询二级代理绑定的玩家
     select * from user where fatherproxy_id in (select users_id from user where fatherproxy_id in(select users_id from user where fatherproxy_id=781 and user_status =1) and user_status=2)
     */



    /**
     * 查询userId 上级用户的id昵称 为空则表示没有上级
     * @param user_id  需要查询上级的用户ID
     */
    @Select("select b.id,b.nickname from user a,${gameDbName}.users b where a.fatherproxy_id=b.id and a.users_id=#{user_id} limit 1")
    GameUserEntity queryMySuperior(@Param("user_id") int user_id);


}
