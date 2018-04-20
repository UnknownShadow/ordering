package com.juunew.admin.controller;

import com.juunew.admin.dao.*;
import com.juunew.admin.services.WechatApiService;
import com.juunew.admin.wechat.utils.MessageUtil;
import com.juunew.admin.wechat.utils.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;


@Controller
public class WeChatController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    WechatApiService weixinUtil;


    /**
     *  微信公众号
     */
    @RequestMapping("/wechat")
    public void weChat(HttpServletRequest req, HttpServletResponse resp) throws Exception, ServletException {

        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");


        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");


        PrintWriter out = resp.getWriter();

        if(CheckUtil.checkSignature(signature,timestamp,nonce)){
            out.print(echostr);
            wech(req,resp);
        }

    }


    public void wech(HttpServletRequest req,HttpServletResponse resp){

        PrintWriter out = null;
        try{
            out= resp.getWriter();
            Map<String,String> map = MessageUtil.xmlToMap(req);

            String fromUserName = map.get("FromUserName");  // 发送方帐号（open_id）
            String  toUserName = map.get("ToUserName");     // 公众帐号
            String  msgType = map.get("MsgType");           // 消息类型
            String  content = map.get("Content");
            String  eventKey = map.get("EventKey");
            String  eventType = map.get("Event");           //event内容


            String message ="";

            TextMessage textMessage = new TextMessage();
            textMessage.setFromUserName(toUserName);
            textMessage.setToUserName(fromUserName);
            textMessage.setCreateTime(new Date().getTime()+"");


            if(MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgType)){

                //菜单按钮click点击后，返回的消息
                if(MessageUtil.EVENT_TYPE_CLICK.equals(eventType)){
                    if (eventKey.equals("31")) {
                        textMessage.setMsgType("text");
                        textMessage.setContent("如果您有任何问题，可在微信直接与我们联系\n也可以拨打客服热线：\n0663-8268168\n或发送邮件至\nchaoshangwan@juunew.com");
                    } else if (eventKey.equals("32")) {
                        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                        // 创建图文消息
                        NewsMessage newsMessage = new NewsMessage();
                        newsMessage.setToUserName(fromUserName);
                        newsMessage.setFromUserName(toUserName);
                        newsMessage.setCreateTime(new Date().getTime());
                        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);


                        //用于 设置多条图文消息
                        List<Article> articleList = new ArrayList<Article>();

                        //单图文消息
                        Article article = new Article();
                        article.setTitle("我是一条单图文消息");
                        article.setDescription("我是描述信息，哈哈哈哈哈哈哈。。。");
                        article.setPicUrl("http://www.iteye.com/upload/logo/user/603624/2dc5ec35-073c-35e7-9b88-274d6b39d560.jpg");
                        article.setUrl("http://tuposky.iteye.com");

                        articleList.add(article);


                        // 设置图文消息个数
                        newsMessage.setArticleCount(articleList.size());
                        // 设置图文消息包含的图文集合
                        newsMessage.setArticles(articleList);
                        // 将图文消息对象转换成xml字符串
                        message = MessageUtil.newsMessageToXml(newsMessage);

                        textMessage.setContent(message);

                    } /*else if (eventKey.equals("13")) {
                        content = "周边搜索菜单项被点击！";
                    } else if (eventKey.equals("14")) {
                        content = "历史上的今天菜单项被点击！";
                    } else if (eventKey.equals("21")) {
                        content = "歌曲点播菜单项被点击！";
                    }*/
                }else  if (MessageUtil.EVENT_TYPE_SUBSCRIBE.equals(eventType)) {

                    // 订阅时 返回消息
                    textMessage.setMsgType("text");
                    textMessage.setContent("好开心，你也来啦。我是人见人爱的小潮，感谢您关注潮尚玩的官方微信！\n" +
                            "点击下方菜单栏的『潮尚玩APP』下载潮尚玩，马上进入潮汕人尚好玩的文化娱乐平台，甲本地美女主播一起干上游，搏鱼虾蟹(๑‾ ꇴ ‾๑)");
                    //respXmlMsg = responseXml.respTextXml(fromUserName, toUserName, "输入1、2、3...试试看/::D\n或者发个图片/::D");
                }
            }else if(MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(msgType)){

                //用户发送消息后，返回的消息
                textMessage.setMsgType("text");
                textMessage.setContent("您好，小潮已收到您的信息，将会在48小时内给您回复哦O(∩_∩)O~如着急咨询的，请致电0663-8268168");
                //textMessage.setContent("你发送的消息是：" + content);
            }

            if(message.equals("")){
                //对message消息进行封装 // 将消息对象转换成xml字符串
                message = MessageUtil.textMessageToXml(textMessage);
            }




            //获取accessToken
            AccessToken accessToken = weixinUtil.getAccessToken();

            logger.info("- - - - access_token - - - - "+accessToken.getToken());
            logger.info("- - - - expires_in - - - - "+accessToken.getExpiresIn());



            //自定义菜单
            String menu = JSONObject.fromObject(weixinUtil.initMeun()).toString();

            int result = weixinUtil.createMenu(accessToken.getToken(),menu);
            if(result==0){
                logger.info("成功创建自定义菜单");
            }else{
                logger.info("错误码："+result);
            }

            out.print(message);
        }catch (Exception e){
           logger.error("错误信息：{}",e);
        }finally {
            if (out!=null){
                out.close();
            }
        }

    }



}
