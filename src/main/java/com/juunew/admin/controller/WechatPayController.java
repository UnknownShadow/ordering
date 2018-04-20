package com.juunew.admin.controller;

import com.juunew.admin.dao.WechatRechargeDao;
import com.juunew.admin.services.WechatPayService;
import com.juunew.admin.services.WechatService;
import com.juunew.admin.utils.WechatPayUtil;
import com.juunew.admin.wechat.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by juunew on 2017/9/1.
 */
@Controller
public class WechatPayController {
    /**
     * 微信内H5调起支付
     *
     * @param request
     * @param response
     * @param openId
     * @return
     * @throws Exception
     */

    @Autowired
    WechatRechargeDao wechatRechargeDao;

    @Autowired
    WechatService wechatService;

    @Autowired WechatPayService payService;


    Logger logger = LoggerFactory.getLogger(WechatPayController.class);


    /**
     * 微信公众号支付
     * @param openId
     * @param total_number  支付金额
     * @param diamonds  支付后获得的钻石数
     * @param dataVoucher   随机码
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("jspay")
    public JsPayResult jsPay(HttpServletRequest request, HttpServletResponse response, HttpSession session, String openId, String total_number, String diamonds, String dataVoucher) throws Exception {
        int total_money = Integer.parseInt(total_number);
        int diamond = Integer.parseInt(diamonds);
        int userId = wechatService.auth(session);


        JsPayResult results = null;

        logger.info("- - - - - - 正在支付的openId - - - - - - " + openId);

        // 统一下单
        String out_trade_no = PayUtil.createOutTradeNo();

        String total_fee = total_money+""; // 产品价格1分钱,用于测试

        logger.info("支付钱数：{}",total_fee);

        String spbill_create_ip = HttpReqUtil.getRemortIP(request);

        logger.info("客户端支付IP：" + spbill_create_ip);

        String nonce_str = UUID.randomUUID().toString().replaceAll("-", ""); // 随机数据

        //为了测试，将33号充值金额归为0.01
        if (userId==33 || userId==781){
            total_fee = "1";
        }

        //参数组装
        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
        parameters.put("appid", ConfigUtil.APPID);      //公众账号ID

        parameters.put("mch_id", ConfigUtil.MCH_ID);        //商户号
        parameters.put("nonce_str", nonce_str);        //随机字符串
        parameters.put("body", "钻石充值");                   //商品描述
        parameters.put("out_trade_no", out_trade_no);           //商户订单号
        parameters.put("total_fee", total_fee);                       //标价金额
        parameters.put("spbill_create_ip",spbill_create_ip);     //终端IP
        parameters.put("notify_url", ConfigUtil.NOTIFY_URL);        //通知地址
        parameters.put("trade_type", "JSAPI");      //交易类型
        parameters.put("openid", openId);



        //获取当前时间，录入，
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间
        Date nowDate = df.parse(sysDate);

        //将订单信息存入数据库中：out_trade_no 订单号；total_fee：金额；user_id
        wechatRechargeDao.insertToWechatRecharge(userId,out_trade_no,diamond,total_money,dataVoucher,0);

        String sign = WechatPayService.createSign(parameters);   //签名
        parameters.put("sign", sign);          //将签名put到对象中

        // 统一下单 请求的Xml(正常的xml格式)
        String requestXML = WechatPayService.getRequestXml(parameters);     ////签名并入service



        // 返回<![CDATA[SUCCESS]]>格式的XML
        String result = CommonUtil.httpsRequest(ConfigUtil.UNIFIED_ORDER_URL, "POST", requestXML);

        logger.debug("返回<![CDATA[SUCCESS]]>格式的XML： "+result.toString());

        Map<String, String> map=new HashMap<String, String>();

        map = XMLUtil.xmlToMap(result);     //将xml转为map



        //获取时间戳
        String timeStamp = PayUtil.createTimeStamp();

        nonce_str = UUID.randomUUID().toString().replaceAll("-", "");

        results = new JsPayResult();
        results.setAppId(ConfigUtil.APPID);
        results.setTimeStamp(timeStamp);
        results.setNonceStr(nonce_str);//直接用返回的
        results.setSignType(ConfigUtil.SIGN_TYPE);

        /**** prepay_id 2小时内都有效，再次支付方法自己重写 ****/
        results.setPackageStr("prepay_id=" + map.get("prepay_id"));

        /**** 用对象进行签名 ****/
        results.setResultCode("SUCCESS");

        //再次签名
        SortedMap<Object, Object> payMap = new TreeMap<Object,Object>();
        payMap.put("appId", results.getAppId());
        payMap.put("nonceStr", results.getNonceStr());
        payMap.put("package", results.getPackageStr());
        payMap.put("signType", results.getSignType());
        payMap.put("timeStamp", results.getTimeStamp());
        String paySign = WechatPayService.createSign(payMap);

        logger.debug("second sign = {}", paySign);

        results.setPaySign(paySign);

        /**** 返回对象给页面 ****/
        return results;
    }


    @ResponseBody
    @RequestMapping("transferPay")
    public Map transferPay(@RequestParam("amount")int amount,
                           @RequestParam("openid")String openId,
                           HttpServletRequest request ) throws Exception {

//        String ip = HttpReqUtil.getRemortIP(request);
//        Map result = payService.transferPay(openId, amount, "积分提现", ip);
//        return result;
        return null;
    }
}
