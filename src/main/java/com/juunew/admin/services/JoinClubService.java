package com.juunew.admin.services;

import com.alibaba.fastjson.JSONObject;
import com.juunew.admin.utils.HttpUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by juunew on 2017/6/24.
 */
@Service
public class JoinClubService extends BaseService{


    /**
     *  加入俱乐部API调用
     */
    public int joinClub(String token,String code) throws Exception {

        JSONObject params = new JSONObject();
        params.put("token", token);
        params.put("code", code);

        logger.info("<推送> 给服务端数据（/api/club/join）：{}",params);

        HttpUtil httpUtil = new HttpUtil();
        String s = httpUtil.post(server+"/api/club/join",
                params.toJSONString());

        return 0;
    }

}
