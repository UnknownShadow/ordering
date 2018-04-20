package com.juunew.admin.services;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by juunew on 2018/3/26.
 */
@Service
public class MsgSendingService {


    Logger logger = LoggerFactory.getLogger(MsgSendingService.class);

    @Autowired
    MsgPushService msgPush;


    public void diposeMsg(String title, String content, String btnShow, String btnUrl, String btn_title,
                          String version, String platform, String reward_type, String picUrl, int picCmd,
                          int picPage, String picToUrl, int showPlace, int msgType, int reward, boolean btn_show,
                          int btnToPage, int btnCmd, boolean needScroller, Date startTime, boolean sendAll, List userIds,
                          int msgId, int msgRecodId, JSONObject jsonObject) throws Exception {

        JSONObject contentObject = new JSONObject();
        jsonObject.put("content", contentObject);

        if (btnShow.equals("1")) {
            //page定义:  1：消息；2：任务；3：比赛列表 4：宝箱；5：充值界面；6：好友界面
            //btnAction中Cmd值：0. 关闭窗口 1. 跳转到网页 2. 跳转到页面  3. 领取奖励  4. 退出应用

            btn_show = true;
            JSONObject actionObject = getActionObject(btnUrl, btnToPage, btnCmd);
            contentObject.put("btnAction", actionObject);
        }
        contentObject.put("btnShow", btn_show);

        if (StringUtils.isNotEmpty(picUrl)) {
            JSONObject actionObject = getActionObject(picToUrl, picPage, picCmd);
            JSONObject picContentObject = new JSONObject();
            picContentObject.put("picUrl", picUrl);
            picContentObject.put("picAction", actionObject);

            contentObject.put("picContent", picContentObject);
        }
        jsonObject.put("cmd", 5);
        contentObject.put("msgType", msgType);
        contentObject.put("msgId", msgId);
        contentObject.put("rewardRecordId", msgRecodId);
        contentObject.put("title", title);
        contentObject.put("content", content);
        contentObject.put("showPlace", showPlace);
        contentObject.put("rewardType", reward_type);
        contentObject.put("rewardNum", reward);
        contentObject.put("btnShow", btn_show);
        contentObject.put("btnTitle", btn_title);
        contentObject.put("needScroller", needScroller);
        if (needScroller) {
            contentObject.put("scrollerTime", 3);
        }

        boolean flag = sendAll;

        msgPush.msgPush(jsonObject, userIds, flag, startTime.getTime() / 1000, version, Integer.parseInt(platform));
        logger.debug("消息推送成功！ ");
    }

    private JSONObject getActionObject(String url, int page, int cmd) {
        JSONObject actionObject = new JSONObject();
        actionObject.put("cmd", cmd);
        actionObject.put("page", page);
        actionObject.put("url", url);
        return actionObject;
    }

}
