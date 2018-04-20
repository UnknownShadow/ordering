package com.juunew.admin.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;

public class WechatPayUtil {

    private static Logger logger = LoggerFactory.getLogger(WechatPayUtil.class);

    // private static Logger logger = Logger.getLogger(Background.class);
    public static void main(String[] args) throws Exception {
        //运行main方法，可以测试配置是否正确。配置信息写在同级目录的 weinxin.config.xml里
        String openid = "o1-buw7NA2tTez8f1jdAvAeGP5Hw";//这是个人的微信openID,欢迎大家给我付款。哈哈。。。。。
        int amount = 100;  //最低付款金额为100分。付款金额以分为单位
        String desc = "节日快乐";
        String partner_trade_no = "1234567890008"; //同一单号不可以重复提交，如果重复提交同一单号，微信以为是同一次付款，只要成功一次，以后都会无视。
        Map<String, String> map = transfer("", "", "", "",
                openid, amount, desc, partner_trade_no);
        if (StringUtils.equals(map.get("state"), "SUCCESS")) {
            System.out.println(map.get("payment_no"));
            System.out.println(map.get("payment_time"));
        } else {
            System.out.println(map.get("err_code"));
            System.out.println(map.get("err_code_des"));
        }
    }

    /**
     * @param openid           收款人的openID(微信的openID)
     * @param amount           付款金额
     * @param desc             付款描述
     * @param partner_trade_no 订单号(系统业务逻辑用到的订单号)
     * @return map{state:SUCCESS/FAIL}{payment_no:
     * '支付成功后，微信返回的订单号'}{payment_time:'支付成功的时间'}{err_code:'支付失败后，返回的错误代码'}{err_code_des:'支付失败后，返回的错误描
     * 述 ' }
     * @author 吴桂生
     * @date 2016-7-12下午2:29:34
     * @Description：微信支付，企业向个人付款
     */
    public static Map<String, String> transfer(String appid, String mchid, String certPath,
                                               String ip,
                                               String openid, int amount, String desc, String partner_trade_no) throws Exception {
        Map<String, String> map = new HashMap<>(); // 定义一个返回MAP
        // 读取配置文件信息，包括微信支付的APPID，商户ID和证书路径

        String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

        String uuid = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");// 随机获取UUID
        // 设置支付参数
        SortedMap<Object, Object> signParams = new TreeMap<Object, Object>();

        signParams.put("mch_appid", appid); // 微信分配的公众账号ID（企业号corpid即为此appId）
        signParams.put("mchid", mchid);// 微信支付分配的商户号
        signParams.put("nonce_str", uuid); // 随机字符串，不长于32位
        signParams.put("partner_trade_no", partner_trade_no); // 商户订单号，需保持唯一性
        signParams.put("openid", openid); // 商户appid下，某用户的openid
        signParams.put("check_name", "NO_CHECK"); // NO_CHECK：不校验真实姓名
        // FORCE_CHECK：强校验真实姓名（未实名认证的用户会校验失败，无法转账）
        // OPTION_CHECK：针对已实名认证的用户才校验真实姓名（未实名认证用户不校验，可以转账成功）
        signParams.put("amount", amount); // 企业付款金额，单位为分
        signParams.put("desc", desc); // 企业付款操作说明信息。必填。
        signParams.put("spbill_create_ip", ip); // 调用接口的机器Ip地址

        // 生成支付签名，要采用URLENCODER的原始值进行MD5算法！

        String sign = "";
        sign = createSign("UTF-8", signParams);
        // System.out.println(sign);
        String data = "<xml><mch_appid>";
        data += appid + "</mch_appid><mchid>"; // APPID
        data += mchid + "</mchid><nonce_str>"; // 商户ID
        data += uuid + "</nonce_str><partner_trade_no>"; // 随机字符串
        data += partner_trade_no + "</partner_trade_no><openid>"; // 订单号
        data += openid + "</openid><check_name>NO_CHECK</check_name><amount>"; // 是否强制实名验证
        data += amount + "</amount><desc>"; // 企业付款金额，单位为分
        data += desc + "</desc><spbill_create_ip>"; // 企业付款操作说明信息。必填。
        data += ip + "</spbill_create_ip><sign>";// 调用接口的机器Ip地址
        data += sign + "</sign></xml>";// 签名
        System.out.println(data);
        // 获取证书，发送POST请求；
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(certPath)); // 从配置文件里读取证书的路径信息
        keyStore.load(instream, mchid.toCharArray());// 证书密码是商户ID
        instream.close();
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchid.toCharArray()).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        HttpPost httpost = new HttpPost(url); //
        httpost.addHeader("Connection", "keep-alive");
        httpost.addHeader("Accept", "*/*");
        httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpost.addHeader("Host", "api.mch.weixin.qq.com");
        httpost.addHeader("X-Requested-With", "XMLHttpRequest");
        httpost.addHeader("Cache-Control", "max-age=0");
        httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        httpost.setEntity(new StringEntity(data, "UTF-8"));
        CloseableHttpResponse response = httpclient.execute(httpost);
        HttpEntity entity = response.getEntity();

        String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        EntityUtils.consume(entity);
        // 把返回的字符串解释成DOM节点
        Document dom = DocumentHelper.parseText(jsonStr);
        Element root = dom.getRootElement();
        String returnCode = root.element("result_code").getText(); // 获取返回代码
        if (StringUtils.equals(returnCode, "SUCCESS")) { // 判断返回码为成功还是失败
            String payment_no = root.element("payment_no").getText(); // 获取支付流水号
            String payment_time = root.element("payment_time").getText(); // 获取支付时间
            map.put("state", returnCode);
            map.put("payment_no", payment_no);
            map.put("payment_time", payment_time);
            return map;
        } else {
            String err_code = root.element("err_code").getText(); // 获取错误代码
            String err_code_des = root.element("err_code_des").getText();// 获取错误描述
            map.put("state", returnCode);// state
            map.put("err_code", err_code);// err_code
            map.put("err_code_des", err_code_des);// err_code_des
            return map;
        }

    }

    /**
     * @param characterEncoding 编码格式
     * @param parameters        请求参数
     * @return
     * @author 吴桂生
     * @date 2016-7-5下午2:29:34
     * @Description：sign签名
     */
    private static String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set<Entry<Object, Object>> es = parameters.entrySet();
        Iterator<Entry<Object, Object>> it = es.iterator();
        while (it.hasNext()) {
            Entry<Object, Object> entry = it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }

        //公众号
        sb.append("key=" + "e11af80fb7dc71d06dd310e00db5958f");
        //APP
//        sb.append("key=" + "142685a65491707063ee8ff9f8fca762");
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

}