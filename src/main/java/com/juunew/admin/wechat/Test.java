package com.juunew.admin.wechat;

import com.juunew.admin.services.WechatPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


/**
 * Created by juunew on 2017/9/5.
 */
public class Test {

   /* Logger logger = LoggerFactory.getLogger(Test.class);


    public void weixin_notify(HttpServletRequest req, HttpServletResponse resp)throws Exception{

//读取参数
        InputStream inputStream;
        StringBuffer sb=new StringBuffer();
        inputStream=req.getInputStream();
        String s;
        BufferedReader in=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        while((s=in.readLine())!=null){
            sb.append(s);
        }
        in.close();
        inputStream.close();

//解析xml成map
        Map m=new HashMap();
        m=XMLUtil.xmlToMap(sb.toString());

//过滤空设置TreeMap
        SortedMap packageParams=new TreeMap();
        Iterator it=m.keySet().iterator();
        while(it.hasNext()){
            String parameter=(String)it.next();
            String parameterValue=(String)m.get(parameter);

            String v="";
            if(null!=parameterValue){
                v=parameterValue.trim();
            }
            packageParams.put(parameter,v);
        }

//账号信息
        String key=ConfigUtil.API_KEY;//key

        logger.debug("信息："+packageParams);
//判断签名是否正确
        if(WechatPayService.isTenpaySign("UTF-8",packageParams,key)){
//------------------------------
//处理业务开始
//------------------------------
            String resXml="";
            if("SUCCESS".equals((String)packageParams.get("result_code"))){
//这里是支付成功
//////////执行自己的业务逻辑////////////////
                String mch_id=(String)packageParams.get("mch_id");
                String openid=(String)packageParams.get("openid");
                String is_subscribe=(String)packageParams.get("is_subscribe");
                String out_trade_no=(String)packageParams.get("out_trade_no");

                String total_fee=(String)packageParams.get("total_fee");

                logger.info("mch_id:"+mch_id);
                logger.info("openid:"+openid);
                logger.info("is_subscribe:"+is_subscribe);
                logger.info("out_trade_no:"+out_trade_no);
                logger.info("total_fee:"+total_fee);

//////////执行自己的业务逻辑////////////////

                logger.info("支付成功");
//通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                resXml=""+""
                        +""+"";

            }else{
                logger.info("支付失败,错误信息："+packageParams.get("err_code"));
                resXml=""+""
                        +""+"";
            }
//------------------------------
//处理业务完毕
//------------------------------
            BufferedOutputStream out=new BufferedOutputStream(
                    resp.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        }else{
            logger.info("通知签名验证失败");
        }

    }*/
}
