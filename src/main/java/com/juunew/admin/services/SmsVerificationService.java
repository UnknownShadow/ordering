package com.juunew.admin.services;

import com.alibaba.fastjson.JSONObject;
import com.juunew.admin.utils.HttpUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by juunew on 2017/6/24.
 */
@Service
public class SmsVerificationService extends BaseService{

    /**
     * 短信验证接口
     */
    public int msgService(int code,String phone) throws Exception {



        JSONObject params = new JSONObject();
        params.put("code", code);          //表示要发送的验证码
        params.put("token", token);
        params.put("phone", phone);      //phone，手机号

        HttpUtil httpUtil = new HttpUtil();
        String s = httpUtil.post(server+"/api/sendSmsCode",
                params.toJSONString());

        return 0;
    }
}
