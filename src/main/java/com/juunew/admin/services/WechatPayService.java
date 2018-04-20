package com.juunew.admin.services;


import com.juunew.admin.utils.MD5Util;
import com.juunew.admin.utils.WechatPayUtil;
import com.juunew.admin.wechat.ConfigUtil;
import com.juunew.admin.wechat.PayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 微信支付签名算法sign
 */
@Service
public class WechatPayService {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Value("${wechat.appid}")
    private String appId;

    @Value("${wechat.mch_id}")
    private String mchId;

    @Value("${wechat.certPath}")
    private String certPath;


    @SuppressWarnings("unchecked")
    public static String createSign(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + ConfigUtil.API_KEY);
        String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
        return sign;
    }


    /**
     * @param parameters 请求参数
     * @return
     * @author Mark
     * @Description：将请求参数转换为xml格式的string
     */
    public static String getRequestXml(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }


    /**
     * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     *
     * @returnboolean
     */
    public static boolean isTenpaySign(String characterEncoding, SortedMap packageParams, String API_KEY) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }

        sb.append("key=" + API_KEY);

//算出摘要
        String mysign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toLowerCase();
        String tenpaySign = ((String) packageParams.get("sign")).toLowerCase();

//System.out.println(tenpaySign+""+mysign);
        return tenpaySign.equals(mysign);
    }


    /**
     * 企业向个人打款
     *
     * @param openid 需要打款的玩家openid
     * @param amount 金额，单位分
     * @param ip     本机ip地址
     * @param desc   入账详情，会显示在收款人的界面上
     * @return {"payment_time":"2018-02-02 09:57:32","payment_no":"1000018301201802023762126751","state":"SUCCESS"}
     */
    public Map transferPay(String openid, int amount, String desc, String out_trade_no, String ip) throws Exception {
        logger.info("[/transfer/pay]");
        Map<String, String> result = WechatPayUtil.transfer(appId, mchId, certPath, ip,
                openid, amount, desc, out_trade_no);
        logger.info("支付结果: {}", result);

        return result;
    }

}
