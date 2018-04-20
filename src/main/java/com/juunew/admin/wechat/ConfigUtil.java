package com.juunew.admin.wechat;

/**
 * 微信支付配置文件 2017/8/31.
 */
public class ConfigUtil {
    /**
     * 服务号相关信息
     */
    public final static String APPID = "wxecac91c5fbd2b158";//服务号的appid
    public final static String APP_SECRECT = "d0b765a3016c204fc4c502972db97740";//服务号的appSecrect
    public final static String TOKEN = "wang123456";//服务号的配置token
    public final static String MCH_ID = "1486334762";//开通微信支付分配的商户号
    public final static String API_KEY = "e11af80fb7dc71d06dd310e00db5958f";//商户API密钥 自行去商户平台设置
    public final static String SIGN_TYPE = "MD5";//签名加密方式
    //微信支付统一接口的回调action
    public final static String NOTIFY_URL = "https://admin.juunew.com/notify"; //用于告知微信服务器 调用成功

    //app支付所需参数
    public final static String APP_PAY_NOTIFY_URL = "http://192.168.199.126:8080/notify"; //用于告知微信服务器 调用成功
    public final static String API_KEY_OTHER = "142685a65491707063ee8ff9f8fca762";//商户API密钥 自行去商户平台设置


    /**
     * 微信基础接口地址
     */
    //获取token接口(GET)
    public final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    //oauth2授权接口(GET)
    public final static String OAUTH2_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    //刷新access_token接口（GET）
    public final static String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
    // 菜单创建接口（POST）
    public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    // 菜单查询（GET）
    public final static String MENU_GET_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    // 菜单删除（GET）
    public final static String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
    /**
     * 微信支付接口地址
     */
//微信支付统一接口(POST)
    public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //微信退款接口(POST)
    public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    //订单查询接口(POST)
    public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
    //关闭订单接口(POST)
    public final static String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
    //退款查询接口(POST)
    public final static String CHECK_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
    //对账单接口(POST)
    public final static String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
    //短链接转换接口(POST)
    public final static String SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
    //接口调用上报接口(POST)
    public final static String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";
}
