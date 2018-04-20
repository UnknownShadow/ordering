package com.juunew.admin.wechat;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by juunew on 2017/9/2.
 */
public class HttpReqUtil {

    /**
     * 获取客户端IP地址
     *
     * X-Forwarded-For 获取的ip有可能多个如： 118.249.19.107, 183.60.52.5
     * 那么取第一个即可
     *
     * @param request
     * @return
     */
    public static String getRemortIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isEmpty(ip)) {
            ip = request.getRemoteAddr();
        } else {
            if (StringUtils.contains(ip, ",")) {
                String[] ips = StringUtils.split(ip, ",");
                if (ips.length > 1) {
                    ip = ips[0];
                }
            }
        }
        return ip;
    }
}
