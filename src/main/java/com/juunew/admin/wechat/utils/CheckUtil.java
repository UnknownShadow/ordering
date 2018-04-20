package com.juunew.admin.wechat.utils;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Created by juunew on 2017/8/22.
 */
public class CheckUtil {
    private static final String token = "wang123456";

    public static boolean checkSignature(String signature, String timestamp,String nonce){

        String[] arr = new String[]{token,timestamp,nonce};

        //先排序
        Arrays.sort(arr);

        //在将其生成字符串
        StringBuffer content = new StringBuffer();
        for (int i=0;i<arr.length;i++){
            content.append(arr[i]);
        }

        //进行sha1加密
        String temp= getSha1(content.toString());

        return temp.equals(signature);
    }


    /**
     * sha1加密处理
     * */
    public static String getSha1(String str){
        if(str == null || str.length() == 0 ){return null;}

        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

        try{
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;

            for (int i=0;i<j;i++){
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);

        }catch (Exception e){
            return null;
        }

    }
}
