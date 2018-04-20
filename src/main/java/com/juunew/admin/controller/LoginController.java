package com.juunew.admin.controller;

import com.juunew.admin.dao.WechatInfoDao;
import com.juunew.admin.entity.GameUserEntity;
import com.juunew.admin.services.WechatApiService;
import com.juunew.admin.services.WechatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by juunew on 2017/8/25.
 */
@Controller
public class LoginController {

    @Autowired
    WechatApiService weixinUtil;

    @Value("${wechat.backUrl}")
    String backUrl;

    @Value("${wechat.appid}")
    String APPID;

    @Value("${wechat.appsecret}")
    String APPSECRET;

    @Autowired
    WechatInfoDao wechatInfoDao;

    @Autowired
    WechatService wechatService;


    private Logger logger = LoggerFactory.getLogger(getClass());


    @RequestMapping("wxLogin")
    public ModelAndView wxLogins(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException {
        String backUrls = backUrl + "/callBack";
        ModelAndView mv = wechatService.getModelAndViewByUser(session);
        int user_id = 0;
        if (mv != null) {

            //从mv中取值 （userID）
            Map model = mv.getModel();
            GameUserEntity userData = (GameUserEntity)model.get("userData");
            if(userData!=null){
                user_id = userData.getId();

                System.out.println("user_status::"+userData.getUser_status());
            }

            return mv;
        }

        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + APPID
                + "&redirect_uri=" + URLEncoder.encode(backUrls)
                + "&response_type=code"
                + "&scope=snsapi_userinfo&state=1#wechat_redirect";
        return new ModelAndView("redirect:"+url);
    }


}
