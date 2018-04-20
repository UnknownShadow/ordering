<%--<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"
"http://www.wapforum.org/DTD/xhtml-mobile10.dtd" >--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>代理功能</title>
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0" />
    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- dialog弹出框 -->
    <link rel="stylesheet" href="assets/dialog/css/global.css">
    <link rel="stylesheet" href="assets/dialog/css/animate.css">
    <link rel="stylesheet" href="assets/dialog/css/dialog.css">
    <script src="assets/dialog/js/dialog.js"></script>
</head>
<style>
    *{ margin: 0px;padding: 0px; }
    body{
        background-color: #f6f6f6;
        margin: 0px;padding: 0px;
    }
    .container{
        margin: 0px;
        padding: 0px;
        overflow:hidden;
    }
</style>
<body>
    <div class="container">
        <input type="hidden" value="${user_status}" id="userStatus"/>
        <input type="hidden" value="${user_id}" id="userID"/>
        <div style="position:relative;margin-bottom: 10px;" >
            <a href="/wxLogin">
                <div style="position: absolute;left: 10px;top: 35%;">
                    <img src="assets/img/back.png" width="38%" alt="">
                </div>
            </a>
            <img src="assets/img/proxy_func.jpg" width="100%" alt="">
        </div>

        <div style="margin-top: 40px;">

            <!-- 发送钻石入口 -->
            <a href="javascript:;" onclick="var id=document.getElementById('userID').value;
            if(id!=''){window.location.href='/wechat_send_diamonds?user_id='+id;}else{alert('当前ID为空！');}">

                <div style="position: absolute;width: 100%;height: 60px;background: white;">
                    <div style="position: absolute;right: 2px;top: 30%;">
                        <img src="assets/img/to.png" width="38%" alt="">
                    </div>
                    <img src="/assets/img/sendDiam1123.png" style="position: absolute;left: 10px;top: 28%;width: 26px;">
                    <p style="position: absolute;left: 40px;top: 39%;font-size:14px;font-family: Arial;color: #292929;">发送钻石</p>
                </div>
            </a>
            <!-- 发送钻石结束 -->


            <!-- 添加代理入口：-->
            <a href="javascript:;" onclick="var id=document.getElementById('userID').value;
            if(id!=''){window.location.href='/wechat_add_proxy?user_id='+id;}else{alert('当前ID为空！');}">

                <div style="position: absolute;margin-top:140px;width: 100%;height: 60px;background: white;">
                    <div style="position: absolute;right: 2px;top: 35%;">
                        <img src="assets/img/to.png" width="38%" alt="">
                    </div>

                    <img src="/assets/img/addproxy1123.png" style="position: absolute;left: 10px;top: 28%;width: 26px;">
                    <p style="position: absolute;left: 39px;top: 39%;font-size:14px;font-family: Arial;color: #292929;">添加代理</p>
                </div>
            </a>
            <!-- 添加代理结束 -->


            <!-- 返利记录查询  -->
            <a href="/wechat_rebate_func">
                <div style="position: absolute;margin-top:210px;width: 100%;height: 60px;background: white;">
                    <div style="position: absolute;right: 2px;top: 35%;">
                        <img src="assets/img/to.png" width="38%" alt="">
                    </div>
                    <img src="/assets/img/chongzhi.png" style="position: absolute;left: 10px;top: 28%;width: 26px;">
                    <p style="position: absolute;left: 40px;top: 39%;font-size:14px;font-family: Arial;color: #292929;">积分返利</p>
                </div>
            </a>
            <!-- 返利记录查询-->


            <!-- 记录查询入口 -->
            <a href="/wechat_send_records">
                <div style="position: absolute;margin-top:70px;width: 100%;height: 60px;background: white;" id="thre">
                    <div style="position: absolute;right: 2px;top: 35%;">
                        <img src="assets/img/to.png" width="38%" alt="">
                    </div>

                    <img src="/assets/img/record1123.png" style="position: absolute;left: 10px;top: 28%;width: 26px;">
                    <p style="position: absolute;left: 40px;top: 39%;font-size:14px;font-family: Arial;color: #292929;">记录查询</p>
                </div>
            </a>
            <!-- 记录查询结束 -->

        </div>
    </div>
</body>
</html>

