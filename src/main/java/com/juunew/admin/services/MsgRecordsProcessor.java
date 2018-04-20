package com.juunew.admin.services;

import com.juunew.admin.dao.GameUserDao;
import com.juunew.admin.dao.SystemMsgRecordsDao;
import com.juunew.admin.entity.GameUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 全员推送 记录消息 入库
 */
@Service
public class MsgRecordsProcessor implements Runnable {

    @Autowired
    GameUserDao gameUserDao;
    @Autowired
    SystemMsgRecordsDao systemMsgRecordsDao;


    private int msgId;

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }


    @Override
    public void run() {

        //获取所有用户的ID
        List<GameUserEntity> gameUserEntities = gameUserDao.queryAllUserId();
        for (GameUserEntity gameUserEntity : gameUserEntities) {
            //将消息记录到 表中
            systemMsgRecordsDao.insertToMsg(gameUserEntity.getId(), msgId, 0);
        }
    }
}
