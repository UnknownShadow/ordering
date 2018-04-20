package com.juunew.admin.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by juunew on 2017/6/24.
 */
public class HttpUtilTest {

    private static final String token = "6df7eefb2ad9340354fcb58c8446961267a1747a9bafc24c02e90adbc5f11d41";

   /* @Test
    public void post() throws Exception {

        JSONObject params = new JSONObject();
        params.put("number", 1);
        params.put("reason", "test");
        params.put("token", token);
        params.put("userId", 23);
        params.put("type", 1);


        HttpUtil httpUtil = new HttpUtil();
        String s = httpUtil.post("http://server.qq.hugeox.cn:9000/api/bank/addDiamond",
                params.toJSONString());

        if (StringUtils.isNotEmpty(s)){
            JSONObject o = (JSONObject) JSONObject.parse(s);
            System.out.println(o);
        }
    }*/

}