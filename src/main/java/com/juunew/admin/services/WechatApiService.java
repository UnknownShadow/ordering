package com.juunew.admin.services;



import com.juunew.admin.entity.menu.Button;
import com.juunew.admin.entity.menu.ClickButton;
import com.juunew.admin.entity.menu.Menu;
import com.juunew.admin.entity.menu.ViewButton;
import com.juunew.admin.wechat.utils.AccessToken;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * Created by juunew on 2017/8/22.
 */
@Service
public class WechatApiService {

    private static Logger logger = LoggerFactory.getLogger(WechatApiService.class);

    @Value("${wechat.appid}")
    String APPID;

    @Value("${wechat.appsecret}")
    String APPSECRET;

    @Value("${wechat.backUrl}")
    String backUrl;


    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static final String OPENID_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
    private static final String CREATE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    private static final String GET_OPENID_URL = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";




    /**
     * get请求
     * */
    public JSONObject doGetStr(String url) {

        logger.info("doGetStr："+url);
        /*HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();*/

        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;

        try {
            HttpResponse response = httpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");

                logger.info("返回结果："+result);
                jsonObject = JSONObject.fromObject(result);

            }
        } catch (Exception e) {
            System.out.println("出错信息："+e);
        }
        return jsonObject;
    }


    /**
     * post请求
     * */
    public JSONObject doPostStr(String url,String outStr){

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;

        logger.debug("doPost: {}", url);

        try {
            httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);

            String result = EntityUtils.toString(response.getEntity(),"UTF-8");

            logger.debug("doPost result: {}", result);

            jsonObject = JSONObject.fromObject(result);
        }catch (Exception e){
            System.out.println("出错信息："+e);
        }

        return jsonObject;
    }





    /**
     * 获取Access_token
     */
    public AccessToken getAccessToken() {
        AccessToken token = new AccessToken();
        String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);

        JSONObject jsonObject = doGetStr(url);

        if (jsonObject != null) {
            try{
                token.setToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getInt("expires_in"));
            }catch (Exception e){
                // 获取token失败
                //logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
                logger.error("获取token失败：{}",e);
            }

        }

        return token;
    }



    /**
     * 组装自定义菜单
     * */
    public Menu initMeun(){
        Menu menu = new Menu();

        /*ClickButton button11 = new ClickButton();
        button11.setName("个人中心");
        button11.setType("click");
        button11.setKey("11");*/

        ViewButton button11 = new ViewButton();
        button11.setName("个人中心");
        button11.setType("view");
        button11.setUrl(backUrl+"/wxLogin");

        ViewButton button21 = new ViewButton();
        button21.setName("下载游戏");
        button21.setType("view");
        button21.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxeb5244b9728bab00&redirect_uri=http://e947d9a3.ngrok.io/login&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");

       //子菜单
        ClickButton button31 = new ClickButton();
        button31.setName("客服");
        button31.setType("click");
        button31.setKey("31");
         //子菜单
        ClickButton button32 = new ClickButton();
        button32.setName("地理位置");
        button32.setType("click");
        button32.setKey("32");

        //组装子菜单
        Button button = new Button();
        button.setName("菜单");
        button.setSub_button(new Button[]{button31,button32});

        menu.setButton(new Button[]{button11,button21,button});

        return menu;
    }

    public int createMenu(String token,String menu){
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN",token);
        JSONObject jsonObject = doPostStr(url,menu);

        int result = 0;
        if(jsonObject != null){
            result = jsonObject.getInt("errcode");
        }
        return result;
    }




    /**
     * 用于获取用户的基本信息
     * */
   /* public static WeixinUserInfo getUserInfo(String accessToken, String openId) {
        WeixinUserInfo weixinUserInfo = null;
        // 拼接请求地址
        String url = OPENID_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 获取用户信息
        JSONObject jsonObject = doGetStr(url);

        if (null != jsonObject) {
            try {
                weixinUserInfo = new WeixinUserInfo();
                // 用户的标识
                weixinUserInfo.setOpenId(jsonObject.getString("openid"));
                // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
                weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
                // 用户关注时间
                weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
                // 昵称
                weixinUserInfo.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                weixinUserInfo.setSex(jsonObject.getInt("sex"));
                // 用户所在国家
                weixinUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                weixinUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                weixinUserInfo.setCity(jsonObject.getString("city"));
                // 用户的语言，简体中文为zh_CN
                weixinUserInfo.setLanguage(jsonObject.getString("language"));
                // 用户头像
                weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                //unionid
                //weixinUserInfo.setUnionid(jsonObject.getString("unionid"));
            } catch (Exception e) {
                if (0 == weixinUserInfo.getSubscribe()) {
                    System.out.println("用户"+weixinUserInfo.getOpenId()+"已取消关注");
                    //logger.error("用户{}已取消关注", weixinUserInfo.getOpenId());
                } else {
                    int errorCode = jsonObject.getInt("errcode");
                    String errorMsg = jsonObject.getString("errmsg");
                    System.out.println("获取用户信息失败 errcode:"+errorCode);
                    //log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
                }
            }
        }
        return weixinUserInfo;
    }*/






//-----------------------------------------------------------



    /*    public static String doGetStr(String url, String params) throws IOException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        try {

            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("charset", "utf-8");
            httppost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");

            byte[] bytes = params.getBytes(Charset.forName("utf-8"));
            ByteArrayEntity se = new ByteArrayEntity(bytes, ContentType.APPLICATION_JSON);

            httppost.setEntity(se);

            CloseableHttpResponse execute = closeableHttpClient.execute(httppost);
            HttpEntity httpEntity = execute.getEntity();
            String result = "";
            if (httpEntity != null) {
                result = EntityUtils.toString(httpEntity, "UTF-8").replace("\"{", "{").replace("}\"", "}").replaceAll("\\\\", "");
            }

            System.out.println();
            return result;
        } finally {
            closeableHttpClient.close();
        }
    }*/

}