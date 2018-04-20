package com.juunew.admin.services;

import com.juunew.admin.dao.ClubsDao;
import com.juunew.admin.dao.GameUserDao;
import com.juunew.admin.dao.GamesDao;
import com.juunew.admin.entity.ClubsEntity;
import com.juunew.admin.entity.GameUserEntity;
import com.juunew.admin.entity.GamesEntity;
import com.juunew.admin.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by juunew on 2017/11/17.
 */
@Service
public class GetUserInfoService {

    @Autowired
    GameUserDao gameUserDao;
    @Autowired
    GamesDao gamesDao;
    @Autowired
    ClubsDao clubsDao;


    /**
     * 根据user_id查询用户信息
     *
     * @param id 需要查询的用户ID
     * @return
     * @throws Exception
     */
    public GameUserEntity getUserInfo(int id) throws Exception {

        //查询用户信息
        GameUserEntity userInfo = gameUserDao.findusers(id);

        if (userInfo != null) {
            /**
             * 查询钻石
             */
            GameUserEntity diamByUserId = gameUserDao.findDiamByUserId(id);
            int diamond = 0;
            if (diamByUserId != null) diamond = diamByUserId.getDiamond();
            userInfo.setDiamond(diamond);


            /**
             * 用户权限
             */
            int role_id = userInfo.getRole_id();
            String perm = "无";
            if (role_id == GameUserEntity.SeniorAdministratorRole) {
                perm = "高级管理员";
            } else if (role_id == GameUserEntity.AdministratorsRole) {
                perm = "管理员";
            } else if (role_id == GameUserEntity.OperateRole) {
                perm = "运营";
            } else if (role_id == GameUserEntity.FinanceRole) {
                perm = "财务";
            } else if (role_id == GameUserEntity.AgentRole) {
                perm = "代理";
            } else if (role_id == GameUserEntity.CustomerServiceRole) {
                perm = "客服";
            }
            userInfo.setPerm(perm);


            /**
             * 查询所有的下级用户
             */
            List<GameUserEntity> childProxy = new ArrayList<>();
            int user_status = userInfo.getUser_status();

            if (user_status == GameUserEntity.Partner) {
                childProxy = gameUserDao.queryPartnerSubordinate(id);    //自己所有的下级（总代理、代理、绑定的玩家）
            } else if (user_status == GameUserEntity.First_Level_Agent) {
                childProxy = gameUserDao.queryAllSubordinate(id);    //自己的所有下级（及下级的下级、绑定的玩家）
            } else if (user_status == GameUserEntity.Two_Level_Agent) {
                childProxy = gameUserDao.findAllProxy(id);    //所有绑定的玩家
            }
            userInfo.setChildproxytotal(childProxy.size());


            /**
             * 查询上级代理昵称
             */
            GameUserEntity nickName = gameUserDao.queryMySuperior(id);
            if (nickName != null) userInfo.setFatherproxy_nickname(nickName.getNickname());
        }
        return userInfo;
    }


    /**
     * 得到查询后的userInfon（user表） 看是否有数据，
     * 如果没有，再查看users表中是否有数据，如果有则在user表中建立关系
     *
     * @param id 需要查询的用户ID
     * @return
     */
    public ModelAndView buildRelationships(int id) throws Exception {

        GameUserEntity userInfo = null;
        ModelAndView mv = new ModelAndView();

        //查询users表中是否有该ID的数据，如果有，则在user表中建立，如果没有，则返回无此数据
        GameUserEntity userID = gameUserDao.findDataByusersId(id);

        if (userID != null) {

            String pass = MD5Util.MD5(id + "123456");

            //在user表中建立，如果为null，则返回无此数据
            gameUserDao.insertData(id + "", pass, id);

            userInfo = getUserInfo(id);     //user表 与 users表 建立关系后，重新查询
        } else {
            mv.addObject("err", "对不起，没有查询到您输入的ID，请正确输入！");
        }

        mv.addObject("user", userInfo);
        return mv;
    }


    //随机生成邀请码
    public int getInviteCode() {
        int invite_code = (int) ((Math.random() * 9 + 1) * 100000);

        //根据邀请码查询是否有重复的，如果有，则重新生成邀请码
        boolean flag = true;
        while (flag) {
            GameUserEntity queryInviteCode = gameUserDao.findByInviteCode(invite_code);
            if (queryInviteCode != null) {
                invite_code = (int) ((Math.random() * 9 + 1) * 100000);
            } else {
                flag = false;
            }
        }
        return invite_code;
    }


    /**
     * 总代理
     *
     * 查询 user_id 的所有子代理ID（包括子代理的子代理）
     */
    /*public List<Integer> getSubAgentUser(int user_id) {
        List<Integer> subAgentList = new ArrayList();

        List<GameUserEntity> gameUserEntities = gameUserDao.allSubAgent(user_id);

        if (gameUserEntities.size() != 0) {
            for (GameUserEntity gameUserEntity : gameUserEntities) {

                int users_id = gameUserEntity.getUsers_id();

                subAgentList.add(users_id);

                //根据id查询子代理的子代理
                List<GameUserEntity> totalSubAgent = gameUserDao.allSubAgent(users_id);
                if (totalSubAgent.size() != 0) {
                    for (GameUserEntity userEntity : totalSubAgent) {
                        subAgentList.add(userEntity.getUsers_id());
                    }
                }
            }
        }
        return subAgentList;
    }*/


    /**
     * 合伙人 （下级）
     *
     * @param user_id
     * @return
     */
    public List<Integer> getSubAgentUsers(int user_id) {
        List<Integer> subAgentList = new ArrayList();

        List<GameUserEntity> gameUserEntities = gameUserDao.allSubAgent(user_id);

        if (gameUserEntities.size() != 0) {
            for (GameUserEntity gameUserEntity : gameUserEntities) {

                //一级代理id
                int users_id = gameUserEntity.getUsers_id();

                subAgentList.add(users_id);

                //根据id查询子代理的子代理
                List<GameUserEntity> totalSubAgent = gameUserDao.allSubAgent(users_id);
                if (totalSubAgent.size() != 0) {
                    for (GameUserEntity userEntity : totalSubAgent) {

                        //二级代理id
                        int two_agent_user_id = userEntity.getUsers_id();

                        subAgentList.add(two_agent_user_id);

                        //根据id查询子代理的子代理
                        List<GameUserEntity> subAgent = gameUserDao.allSubAgent(two_agent_user_id);
                        if (subAgent.size() != 0) {
                            for (GameUserEntity entity : subAgent) {

                                //绑定玩家ID
                                subAgentList.add(entity.getUsers_id());
                            }
                        }
                    }
                }
            }
        }
        return subAgentList;
    }


    /**
     * 查询总代理有多少绑定玩家
     */
    /*public List<Integer> getAllBindingPlayer(int user_id) {
        List<Integer> bindingPlayerList = new ArrayList();

        List<GameUserEntity> gameUserEntities = gameUserDao.allSubAgent(user_id);

        if (gameUserEntities.size() != 0) {
            for (GameUserEntity gameUserEntity : gameUserEntities) {

                int users_id = gameUserEntity.getUsers_id();
                int user_status = gameUserEntity.getUser_status();

                if(user_status == GameUserEntity.Player){
                    bindingPlayerList.add(users_id);
                }else{  //是代理，则查询代理下绑定的玩家

                    //根据id查询子代理的子代理
                    List<GameUserEntity> totalSubAgent = gameUserDao.allSubAgent(users_id);
                    if (totalSubAgent.size() != 0) {
                        for (GameUserEntity userEntity : totalSubAgent) {
                            if(userEntity.getUser_status() == GameUserEntity.Player) {
                                bindingPlayerList.add(userEntity.getUsers_id());
                            }
                        }
                    }
                }
            }
        }
        return bindingPlayerList;
    }*/


    /**
     * 根据时间查询开房次数    俱乐部和私房 开房 分开统计
     *
     * @param splitSubAgent 我的所有子代理
     * @param startDate    开始时间
     * @param endDate      结束时间
     * @return
     */
    public Map getGamesNum(String[] splitSubAgent, String startDate, String endDate) {

        int gamesNum = 0;   //开房次数
        int gameConsumeDiam = 0;    //游戏开房用钻

        if (splitSubAgent.length > 0) {
            //先根据时间查询私房
            List<GamesEntity> gamesEntities = gamesDao.queryByDateTime(startDate, endDate);
            if (gamesEntities.size() != 0) {
                for (GamesEntity gamesEntity : gamesEntities) {
                    //私房创建者ID
                    int anchor_id = gamesEntity.getAnchor_id();

                    for (String s : splitSubAgent) {
                        int users_id = Integer.parseInt(s);
                        if (anchor_id == users_id) {
                            gamesNum++;
                            gameConsumeDiam = gameConsumeDiam + gamesEntity.getSign_cost();
                        }
                    }
                }
            }

            //根据时间查询俱乐部开房
            List<ClubsEntity> clubsEntities = clubsDao.queryClubsByDateTime(startDate, endDate);
            if (clubsEntities.size() != 0) {
                for (ClubsEntity clubsEntity : clubsEntities) {
                    //俱乐部开房记录（俱乐部的创建者）
                    int creator_id = clubsEntity.getCreator_id();

                    for (String s : splitSubAgent) {
                        int users_id = Integer.parseInt(s);
                        if (creator_id == users_id) {
                            gamesNum++;
                            gameConsumeDiam = gameConsumeDiam + clubsEntity.getSign_cost();
                        }
                    }
                }
            }
        }


        Map map = new HashMap();
        map.put("gamesNum", gamesNum);
        map.put("gameConsumeDiam", gameConsumeDiam);

        return map;
    }



    /**
     * 遍历跟 user_id 相等的id有几个
     *
     * @param allSubordinate 我所有的子代理实体类
     * @param user_id        需要比较的 user_id
     * @return
     */
    public int countEqualsUserId(List<GameUserEntity> allSubordinate, int user_id) {
        int num = 0;

        if (allSubordinate.size() != 0) {
            for (GameUserEntity userEntity : allSubordinate) {
                int userId = userEntity.getUsers_id();
                if (userId == user_id) num++;
            }
        }
        return num;
    }


    /**
     * 查询 user_id 所有下级用户  用String类型表示，如：78,77,89
     * @param user_id
     * @return
     */
    public String getStringAllSubAgent(int user_id) throws Exception {

        //用于存储我的所有下级ID
        List<GameUserEntity> subAgentLists = new ArrayList<>();

        //查询请求用户的身份
        GameUserEntity userStatus = gameUserDao.findUserStatus(user_id);
        int user_status = 3;
        if (userStatus != null) user_status = userStatus.getUser_status();

        //查询自己所有的下级（包括绑定的玩家）
        if (user_status == GameUserEntity.Partner) {
            subAgentLists = gameUserDao.queryPartnerSubordinate(user_id);
        } else if (user_status == GameUserEntity.First_Level_Agent) {
            subAgentLists = gameUserDao.queryAllSubordinate(user_id);
        } else if (user_status == GameUserEntity.Two_Level_Agent) {
            subAgentLists = gameUserDao.findAllProxy(user_id);
        } else {
            throw new Exception("玩家身份无此查看权限");
        }

        //我的所有下级用户ID    （将自己包含在内）
        String subAgentUserIds = user_id + ",";

        if (subAgentLists.size() != 0) {
            for (GameUserEntity subAgentList : subAgentLists) {
                subAgentUserIds = subAgentUserIds + subAgentList.getUsers_id() + ",";
            }
        }
        subAgentUserIds = subAgentUserIds.substring(0, subAgentUserIds.length() - 1);

        return subAgentUserIds;
    }


}
