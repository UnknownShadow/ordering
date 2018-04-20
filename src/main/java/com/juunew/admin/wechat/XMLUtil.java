package com.juunew.admin.wechat;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * xml管理
 * @author Mark
 *
 */
public class XMLUtil {


    /**
     * 将xml转为map
     * */
    public static Map<String,String> xmlToMap(String strxml)throws Exception{
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");


        Map<String,String> map = new HashMap<>();
        SAXReader reader = new SAXReader();


        InputStream ins = new ByteArrayInputStream(strxml.getBytes("UTF-8"));

        if(null == strxml || "".equals(strxml)) {
            return null;
        }

        Map m = new HashMap();


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
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     * @param strxml
     * @return
     */




    /**
     * 获取子结点的xml
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if(!children.isEmpty()) {
            Iterator it = children.iterator();
            while(it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextTrim();     //.getTextNormalize();
                List list = (List) e.getData();        //getChildren();
                sb.append("<" + name + ">");
                if(!list.isEmpty()) {
                    sb.append(XMLUtil.getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }
}