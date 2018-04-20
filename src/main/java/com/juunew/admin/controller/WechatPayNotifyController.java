package com.juunew.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONObject;
import com.juunew.admin.dao.*;
import com.juunew.admin.entity.*;
import com.juunew.admin.services.BankService;
import com.juunew.admin.services.RechargeService;
import com.juunew.admin.services.WechatPayService;
import com.juunew.admin.utils.HttpUtil;
import com.juunew.admin.utils.HttpUtil;
import com.juunew.admin.wechat.ResultState;
import com.juunew.admin.wechat.XMLUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 微信支付结果通知(统一下单参数的notify_url)
 * 微信充值成功回调 做业务逻辑的处理
 */


@Controller
public class WechatPayNotifyController {
    @Autowired
    BankService bankService;

    @Autowired
    WechatRechargeDao wechatRechargeDao;

    @Autowired
    DiamondsDao diamondsDao;

    @Autowired
    IntegralRebateDao integralRebateDao;

    @Autowired
    GameUserDao gameUserDao;

    @Autowired
    IntegralProductDao integralProductDao;

    @Autowired
    DiamondGiftDao diamondGiftDao;

    @Autowired
    RechargeService rechargeService;

    @Autowired
    ProductsDao productsDao;

    @Autowired
    IntegralsDao integralsDao;

    @Autowired
    RebateRatioDao rebateRatioDao;


    Logger logger = LoggerFactory.getLogger(WechatPayNotifyController.class);

    @ResponseBody
    @RequestMapping("notify")
    public ResultState notify(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sysDate = df.format(new Date());// new Date()为获取当前系统时间

        ResultState resultState = new ResultState();

        String resXml = "";

        logger.info("开始处理支付返回的请求：\n");


        BufferedReader reader = req.getReader();

        String line = "";
        String xmlString = null;
        StringBuffer inputString = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            inputString.append(line);
        }
        xmlString = inputString.toString();
        req.getReader().close();

        logger.debug("----接收到的数据如下：---" + xmlString);


        Map<String, String> map = new HashMap<String, String>();
        String result_code = "";
        String return_code = "";
        String out_trade_no = "";
        String transaction_id = "";

        //将接受到的xml数据转换为map形式
        map = XMLUtil.xmlToMap(xmlString);
        logger.debug("数据转为map形式：" + map);


        result_code = map.get("result_code");
        out_trade_no = map.get("out_trade_no");     //获取微信支付返回的订单号
        return_code = map.get("return_code");
        transaction_id = map.get("transaction_id");

        int num = 0;
        int user_id = 0;
        int money = 0;
        int products_id = 0;

        if ("SUCCESS".equals(result_code)) {

            //根据订单号查询user_id 和 充值钻石数量；
            WechatRechargeEntity getOrderNum = wechatRechargeDao.findByOrderNum(out_trade_no);


            String desc = "微信公众号充值";

            if (getOrderNum != null) {

                int payment_method = getOrderNum.getPayment_method();       //支付方式，1：公众号支付，2：APP支付
                if (payment_method == 2) desc = "微信APP支付";

                user_id = getOrderNum.getUser_id();         //充值的user_id
                num = getOrderNum.getRecharge_number();     //充值的钻石数量
                money = getOrderNum.getRecharge_money();    //充值的钱数 （分）
                products_id = getOrderNum.getProducts_id();


                //方便测试，实际支付价格1分，回调后从产品中得到 实际产品应该支付的钱数
                if (user_id == 781 || user_id == 33 || user_id == 154 || user_id == 9791 || user_id == 8723) {
                    IntegralProductEntity products = integralProductDao.queryById(products_id);
                    money = products.getMoney();
                }

                logger.info("user_id(" + user_id + ")的充值金额为：{}", money);


                try {
                    logger.info("微信充值成功回调，调用添加钻石接口：");
                    //钻石充值
                    int diamond_s = bankService.addDiamond(1, user_id, num, 1, "微信钻石充值", 302);

                    logger.info("user_id(" + user_id + ")的充值钻石数量为：{}", num);


                    /**
                     * 查询该充值的用户是玩家还是绑定用户，如果是绑定用户，则给上级  <积分返利>
                     *  玩家充值不返利
                     *  如果是绑定玩家充值，则给二级返利（50%），给一级返利（20%）
                     *  如果是二级充值，则给自己返利（50%），给一级返利（20%）
                     *  如果是一级充值，则给自己返利（70%）
                     */
                    GameUserEntity querySuperior = gameUserDao.findFatherProxyId(user_id);

                    Boolean flag = false;

                    //不为空表示有上级
                    if (querySuperior != null) flag = true;

                    if (flag) {
                        int user_status = querySuperior.getUser_status();

                        //得到上级代理id  （返利积分给上级代理用户）
                        int superior_id = querySuperior.getFatherproxy_id();

                        if (user_status == GameUserEntity.Player) {

                            //查询上级的上级（代理的上级：总代理）
                            //如果是 1 表示是系统代理
                            if (superior_id != 1) {
                                GameUserEntity userStatus = gameUserDao.findUserStatus(superior_id);
                                if (userStatus != null) {
                                    int superUserStatus = userStatus.getUser_status();

                                    if (superUserStatus == GameUserEntity.Two_Level_Agent) {

                                        //返积分给代理
                                        rechargeService.integralRebate(num, user_id, money, superior_id, RebateRatioEntity.TwoRatio);

                                        GameUserEntity firstAgent = gameUserDao.findFatherProxyId(superior_id);
                                        if (firstAgent != null && firstAgent.getFatherproxy_id() != 1) {

                                            //返积分给总代理
                                            rechargeService.integralRebate(num, user_id, money, firstAgent.getFatherproxy_id(), RebateRatioEntity.FirstRatio);
                                        }
                                    } else if (superUserStatus == GameUserEntity.First_Level_Agent) {   //总代理直接绑定玩家返利给一级
                                        //返积分给总代理
                                        rechargeService.integralRebate(num, user_id, money, superior_id, RebateRatioEntity.FirstSelfRatio);
                                    }
                                } else {
                                    logger.info("充值用户的上级ID没有在表中，没有返利");
                                }
                            }
                        } else if (user_status == GameUserEntity.Two_Level_Agent) {

                            //返利给自己
                            rechargeService.integralRebate(num, user_id, money, user_id, RebateRatioEntity.TwoRatio);

                            //查询上级（代理的上级：总代理）
                            GameUserEntity firstAgent = gameUserDao.findFatherProxyId(user_id);
                            if (firstAgent != null && firstAgent.getFatherproxy_id() != 1) {
                                //返积分给总代理
                                rechargeService.integralRebate(num, user_id, money, firstAgent.getFatherproxy_id(), RebateRatioEntity.FirstRatio);
                            }

                        } else if (user_status == GameUserEntity.First_Level_Agent) {

                            //返利给自己
                            rechargeService.integralRebate(num, user_id, money, user_id, RebateRatioEntity.FirstSelfRatio);
                        }
                    }
                } catch (Exception e) {
                    logger.error("微信充值钻石错误信息：{}", e);
                }
            } else {
                logger.info("- - - 数据库中查不到此订单号 - - - ");
            }


            //根据订单号修改支付状态
            wechatRechargeDao.updatePaymentStatus(out_trade_no, 1, transaction_id);

            //将充值数量与金额存入数据库中
            diamondsDao.insertToOrders(products_id, user_id, money, 2, desc, sysDate);

            if (user_id != 781 && user_id != 33) {
                notifyDingding(user_id, money);     //调用钉钉机器人智能提示
            }


            resultState.setErrcode(0);// 表示成功,可以不写，int默认是0
            resultState.setErrmsg("success");

            // 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了
            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
        } else {
            resultState.setErrcode(-1);// 支付失败
            resultState.setErrmsg(map.get("err_code_des"));
            logger.info("支付失败,错误信息：" + map.get("err_code_des"));
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[" + map.get("err_code_des") + "]]></return_msg>" + "</xml> ";
        }


        BufferedOutputStream out = new BufferedOutputStream(resp.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();

        return resultState;
    }



    /**
     * 充值提醒到钉钉
     *
     * @param user_id
     * @param money
     */
    private void notifyDingding(int user_id, int money) {
        String url = "https://oapi.dingtalk.com/robot/send?access_token=309178d09a19ed28dc993995346557625640be728552bb2c3992d29232b1c2cc";

//        {
//            "msgtype": "text",
//                "text": {
//            "content": "我就是我, 是不一样的烟火"
//        },
//            "at": {
//            "atMobiles": [
//            "156xxxx8827",
//                    "189xxxx8325"
//        ],
//            "isAtAll": false
//        }
//        }

        JSONObject object = new JSONObject();
        object.put("msgtype", "text");
        JSONObject textObject = new JSONObject();
        textObject.put("content", "" + user_id + " 充值: " + money);
        object.put("text", textObject);

        HttpUtil httpUtil = new HttpUtil();
        try {
            httpUtil.post(url, object.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String returnXML(String return_code) {

        return "<xml><return_code><![CDATA["

                + return_code

                + "]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }


    private boolean checkSign(String xmlString) {

        Map<String, String> map = null;

        try {

            map = XMLUtil.xmlToMap(xmlString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String signFromAPIResponse = map.get("sign");

        if (signFromAPIResponse == "" || signFromAPIResponse == null) {

            logger.debug("API返回的数据签名数据不存在，有可能被第三方篡改!!!");

            return false;

        }
        logger.debug("服务器回包里面的签名是：" + signFromAPIResponse);

        //清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名

        map.put("sign", "");

        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较

        String signForAPIResponse = getSign(map);

        if (!signForAPIResponse.equals(signFromAPIResponse)) {

            //签名验不过，表示这个API返回的数据有可能已经被篡改了

            System.out.println("API返回的数据签名验证不通过，有可能被第三方篡改!!! signForAPIResponse生成的签名为" + signForAPIResponse);

            return false;

        }

        System.out.println("恭喜，API返回的数据签名验证通过!!!");

        return true;
    }


    //签名验证
    public String getSign(Map<String, String> map) {
        SortedMap<Object, Object> signParams = new TreeMap<Object, Object>();
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            signParams.put(stringStringEntry.getKey(), stringStringEntry.getValue());
        }
        signParams.remove("sign");
        String sign = WechatPayService.createSign(signParams);
        return sign;
    }


    public static Map<String, String> parseXml(HttpServletRequest request)
            throws Exception {
        // 解析结果存储在HashMap
        Map<String, String> map = new HashMap<String, String>();
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        // 释放资源
        inputStream.close();
        inputStream = null;

        return map;
    }

}


    /*import com.juunew.admin.services.WechatPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


*//**
 * Created by juunew on 2017/9/5.
 *//*
@Controller
public class WechatPayNotifyController {

    Logger logger = LoggerFactory.getLogger(Test.class);


    @RequestMapping("notify")
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

    }
}*/



