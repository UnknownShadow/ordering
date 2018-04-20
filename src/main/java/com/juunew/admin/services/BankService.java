package com.juunew.admin.services;

import com.alibaba.fastjson.JSONObject;
import com.juunew.admin.utils.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by juunew on 2017/6/24.
 */
@Service
public class BankService extends BaseService{

    /**
     * 添加钻石和金币API
     * @param userId
     * @param num
     */
    public int addService(int userId, int receiveUserId,int num,int type,String reason,int chargeType) throws Exception {

        JSONObject params = new JSONObject();
        params.put("number", num);
        params.put("reason", reason);
        params.put("token", token);
        params.put("userId", userId);                   //表示要扣钻石的ID
        params.put("receiveUserId", receiveUserId);        //表示要增加钻石的ID
        params.put("type", type);      //type:1 表示增加钻石，2 表示增加金币；
        params.put("chargeType", chargeType);      //type:1 表示增加钻石，2 表示增加金币；


        HttpUtil httpUtil = new HttpUtil();
        String s = httpUtil.post(server+"/api/bank/transDiamond",
                params.toJSONString());

        if (StringUtils.isNotEmpty(s)){
            logger.debug("transDiamond: {}", s);
            JSONObject o = (JSONObject) JSONObject.parse(s);
            if (o!=null){
                if(o.containsKey("code") && o.getInteger("code")==0){
                    JSONObject data = o.getJSONObject("data");
                    if (data!=null){
                        return data.getIntValue("newValue");
                    }
                } else {
                    String errMsg = o.getString("errMsg");
                    throw new Exception(errMsg);
                }
            }
        } else {
            throw new Exception("transDiamond error: server error!!!");
        }
        return 0;
    }


    //添加金币操作
    public int addMoney(int userId, int receiveUserId,int num,int type) throws Exception {
        int i = addService(userId, receiveUserId, num, type,"",0);
        return i;
    }

    //添加钻石操作
    public int addDiamond(int userId, int receiveUserId, int num,int type,String reason,int chargeType) throws Exception {
        int i = addService(userId,receiveUserId,num, type,reason,chargeType);
        return i;
    }
}
