package com.juunew.admin.wechat.utils;

import com.juunew.admin.wechat.utils.TextMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by juunew on 2017/8/22.
 */
public class MessageUtil {

    /**
     * text
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * music
     */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * news
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * text
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * image
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * link
     */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * location
     */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

    //voice
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

    //video
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";

    //shortvideo
    public static final String REQ_MESSAGE_TYPE_SHORTVIDEO = "shortvideo";

    //event
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    //subscribe
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    //unsubscribe
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    //CLICK
    public static final String EVENT_TYPE_CLICK = "CLICK";






    /**
     * 将xml转为map
     * */
    public static Map<String,String> xmlToMap(HttpServletRequest req)throws Exception{

        Map<String,String> map = new HashMap<>();
        SAXReader reader = new SAXReader();


        //从req中获取输入流
        InputStream ins = req.getInputStream();


        Document document = reader.read(ins);

        Element root = document.getRootElement();

        List<Element> list = root.elements();

        for (Element e: list){
            map.put(e.getName(),e.getText());
        }

        ins.close();


        return map;
    }


    /**
     * 将文本消息转为xml
     * */
    public static String textMessageToXml(TextMessage textMessage){

        XStream xStream = new XStream();

        xStream.alias("xml",textMessage.getClass());

        return xStream.toXML(textMessage);
    }

    /**
     * 将图文消息转为xml
     * */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        XStream xStream = new XStream();

        xStream.alias("xml", newsMessage.getClass());
        xStream.alias("item", new Article().getClass());
        return xStream.toXML(newsMessage);
    }

}
