package com.juunew.admin.loginInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler)
            throws Exception {
        HttpSession session = req.getSession();
        Object userName = session.getAttribute("username");
        logger.debug("userName : {}", userName);
        String url = req.getRequestURI();
        logger.debug("url : {}", url);


        /**
         * 以下的URL要经过微信认证才能访问
         */
        String[] wechatUrls = new String[]{
                "/wechat_player_index","/wechat_player_recharge", "/wechat_proxy_apply","/weChatVerification",
                "/wechat_recharge_success","/wechat_proxy_recharge", "/proxy_apply", "/jspay", "/success",
                "/success_back","/wechat_send_diamonds","/wechat_diamond_rebate","/wechat_rebate_func",
                "/wechat_my_rebate",
                "/application_withdrawals","/withdrawals_cash_record","/getPhone","/phone_verification",
                "/wechat_add_proxy","/wechat_send_records","/wechat_proxy_func","/withdrawals_audit","/withdrawals_success",
                "/confirmation_submission"
        };
        for (String wechatUrl : wechatUrls) {
            if (StringUtils.endsWith(url, wechatUrl)){
                if (session.getAttribute("wechat.user.id") != null) {
                    int userId = (Integer) session.getAttribute("wechat.user.id");
                    if (userId > 0) {
                        return true;
                    }
                }
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return false;
            }
        }


        if (userName == null) {
            /**
             * 以下的页，不需要认证，可以直接访问
             */
            String[]  urls = new String[]{
                    "/login", "/user/check", "/user/consume","/user/diamond/output","/fileUpload", "/zhi","/testCase",
                    "/user/api/noticeDropDown","/user/api/msgAcquisition","/user/api/getRewards","/user/api/getReward",
                    "/user/api/getRewardStatus","/user/api/getMsg","/user/api/getMsgs","/user/api/clientMsg",
                    "/user/api/getProductList","/user/api/appPay","/wechat", "/wxLogin",
                    "/callBack","/game_download","/download","/share","/notify",
                    "/gamerFinduser","/change","/addproxy","/user/api/customer_service","/user/api/mobile","/wechatSendRecords","/wechat_new_competition","/chaowan.apk",
                    "/user/api/intent_proxy","/user/api/clickRate","/saveIntentInfo","/wechatDiamondRebate","/withdrawalsRecord",
                    "/binding_wechat","/user/api/club_invite","/callBackLogin","/join_club",
                    "/user/api/mahjong_rules","/spreadCallBack","/jump_page","/share_download"
            };
            for (String s : urls) {
                if (StringUtils.endsWith(url, s)){
                    return true;
                }
            }

            resp.sendRedirect("/login");
            return false;

        }

        return true;
    }

}
