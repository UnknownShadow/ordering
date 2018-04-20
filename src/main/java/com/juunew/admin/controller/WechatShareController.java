package com.juunew.admin.controller;

/**
 * Created by juunew on 2017/9/14.
 */
import java.util.Map;


import com.juunew.admin.wechat.share.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*import wrt.book.Json.TicketJson;
import wrt.book.Json.TokenJson;*/

/**
 *
 */
@Controller
public class WechatShareController {

    Logger logger = LoggerFactory.getLogger(WechatShareController.class);

    //此处的appid与wx.apiEntity 参数appId一致   微信公众账号提供给开发者的信息，以下同理
    public static String APPID = "wxecac91c5fbd2b158";

    //同上
    public static String SECRET = "d0b765a3016c204fc4c502972db97740";

    private static TokenJson getAccess_token(){

        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+SECRET;
        try {
            String result = HttpGetRequest.doGet(url);

            System.out.println("微信服务器获取token:"+result);
            JSONObject rqJsonObject = JSONObject.fromObject(result);
            TokenJson tokenJson = (TokenJson) JSONObject.toBean(rqJsonObject,TokenJson.class);
            return tokenJson;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


    private static TicketJson getTicket(String token){
        //https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token+"&type=wx_card

        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token+"&type=jsapi";
        try {
            String result = HttpGetRequest.doGet(url);
            System.out.println("微信服务器获取Ticket:"+result);

            JSONObject rqJsonObject = JSONObject.fromObject(result);
            TicketJson ticket = (TicketJson) JSONObject.toBean(rqJsonObject,TicketJson.class);
            return ticket;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


    /**
     *微信分享
     */
    @RequestMapping("share")
    public ModelAndView getSign(String user_id, HttpServletRequest req, HttpServletResponse resp)throws Exception{

        String url="https://admin.juunew.com/share?user_id="+user_id;

        ModelAndView md = new ModelAndView();

      /*  System.out.println("WxParams.tic ketTime:"+WxParams.ticketTime);
        System.out.println("WxParams.ticketExpires:"+WxParams.ticketExpires);*/

        //处理token失效的问题
       try {
            long tokenTimeLong = 0;
            long tokenExpiresLong = 0;

            if(WxParams.tokenTime!=null){
                tokenTimeLong = Long.parseLong(WxParams.tokenTime);
            }
            if(WxParams.tokenExpires!=null){
                tokenExpiresLong = Long.parseLong(WxParams.tokenExpires);
            }


            //时间差
            long differ = (System.currentTimeMillis() - tokenTimeLong) /1000;
            if (WxParams.token == null ||  differ > (tokenExpiresLong - 1800)) {
                System.out.println("token为null，或者超时，重新获取");
                TokenJson tokenJson = getAccess_token();
                if (tokenJson != null) {
                    WxParams.token = tokenJson.getAccess_token();
                    WxParams.tokenTime = System.currentTimeMillis()+"";
                    WxParams.tokenExpires = tokenJson.getExpires_in()+"";
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            TokenJson tokenJson = getAccess_token();
            if (tokenJson != null) {
                WxParams.token = tokenJson.getAccess_token();
                WxParams.tokenTime = System.currentTimeMillis()+"";
                WxParams.tokenExpires = tokenJson.getExpires_in()+"";
            }
        }

        //处理ticket失效的问题
        try {
            long ticketTimeLong =0;
            long ticketExpiresLong =0;

            if(WxParams.ticketTime!=null){
                ticketTimeLong = Long.parseLong(WxParams.ticketTime);
            }
            if(WxParams.ticketExpires!=null){
                ticketExpiresLong = Long.parseLong(WxParams.ticketExpires);
            }


            //时间差
            long differ = (System.currentTimeMillis() - ticketTimeLong) /1000;
            if (WxParams.ticket == null ||  differ > (ticketExpiresLong - 1800)) {
                System.out.println("ticket为null，或者超时，重新获取");
                TicketJson ticketJson = getTicket(WxParams.token);
                if (ticketJson != null) {
                    WxParams.ticket = ticketJson.getTicket();
                    WxParams.ticketTime = System.currentTimeMillis()+"";
                    WxParams.ticketExpires = ticketJson.getExpires_in()+"";
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            TicketJson ticketJson = getTicket(WxParams.token);
            if (ticketJson != null) {
                WxParams.ticket = ticketJson.getTicket();
                WxParams.ticketTime = System.currentTimeMillis()+"";
                WxParams.ticketExpires = ticketJson.getExpires_in()+"";
            }
        }

        Map<String, String> ret = Sign.sign(WxParams.ticket,url);
        System.out.println("计算出的签名-----------------------");
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
        System.out.println("-----------------------");


        md.addObject("user_id",user_id);
        md.addObject("timestamp",ret.get("timestamp"));
        md.addObject("nonceStr",ret.get("nonceStr"));
        md.addObject("jsapi_ticket",ret.get("jsapi_ticket"));
        md.addObject("signature",ret.get("signature"));
        md.addObject("APPID",APPID);
        md.setViewName("share");
        return md;
    }

}