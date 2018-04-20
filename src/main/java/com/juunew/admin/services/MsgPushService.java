package com.juunew.admin.services;

import com.alibaba.fastjson.JSONObject;
import com.juunew.admin.utils.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by juunew on 2017/6/24.
 */
@Service
public class MsgPushService extends BaseService{


    /**
     *  消息推送
     */
    public int msgPush(JSONObject msg,List userIds,boolean flag,long sendTime,String version,int platform) throws Exception {

        JSONObject params = new JSONObject();
        params.put("platform", platform);
        params.put("rawContent", msg);
        params.put("token", token);
        params.put("pushAll", flag);
        params.put("sendTime", sendTime);
        if(!version.equals("0")){
            params.put("version", version);
        }
        if(!flag){
            params.put("userIds", userIds);
        }

        logger.info("<推送> 给服务端数据：{}",params);

        HttpUtil httpUtil = new HttpUtil();
        String s = httpUtil.post(server+"/api/newPushMsg",
                params.toJSONString());

        return 0;
    }




    /**
     *  自定义cmd消息推送
     */
    public int newPushSysMsg(int cmd,JSONObject content,int userId) throws Exception {


        JSONObject params = new JSONObject();
        params.put("cmd",cmd);
        params.put("content", content);
        params.put("token", token);
        params.put("userId", userId);


        logger.info("<推送> 给服务端数据：{}",params);

        HttpUtil httpUtil = new HttpUtil();
        String s = httpUtil.post(server+"/api/newPushSysMsg",
                params.toJSONString());

        return 0;
    }



}
