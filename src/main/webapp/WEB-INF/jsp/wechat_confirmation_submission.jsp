<%--<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"
"http://www.wapforum.org/DTD/xhtml-mobile10.dtd" >--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>提交确认</title>
    <script src="bs/js/holder.js"></script>
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
    <link rel="stylesheet" href="assets/dropload/dropload.css">
</head>
<body>
<div class="content">
    <div style="z-index:999;height:135px;width:100%;background-color:white;margin-bottom: 10px;position:fixed;" >
        <div style="position: relative;height: 60px;width: 100%;background-color: white; border-bottom: 1px solid #afafaf;">
            <a href="/wechat_proxy_func">
                <div style="position: absolute;left: 10px;top: 23px;">
                    <img src="assets/img/back.png" width="38%" alt="">
                </div>
            </a>
            <div style="margin-left: 38%;position: absolute;">
                <img src="/assets/img/mng.png" style="width: 15%;margin-top: 15px;">
                <span style="position:absolute;top:25px;font-size:14px;font-family: Arial;color: #292929;">提交确认</span>
            </div>
        </div>
        <br>
    </div>

        <div style="margin-top: 180px;">
            <center>
                <p>您将使用 <span style="font-weight: 800;">${integral}</span> 积分，</p><br>
                <p>
                    提现 <span style="font-weight: 800;">${integral/10}</span>
                    现金到微信<span style="font-weight: 800;">【${wechatNum}】。</span>
                </p><br>
                <input type="button" class="btn btn-primary" id="confirSub" style="margin-top:20px;padding-left: 40px;padding-right: 40px;" value="确认提交">
            </center>
        </div>

    <input type="hidden" value="${integral}" id="integral">
    <input type="hidden" value="${wechatNum}" id="wechatNum">

</div>
</body>
<script>
    $(function () {
        $("#confirSub").click(function () {

            var integral = $("#integral").val();
            var wechatNum = $("#wechatNum").val();
            window.location.href="/withdrawals_audit?integral="+integral+"&wechatNum="+wechatNum;
            $("#integral").val("");
            $("#wechatNum").val("");
        });
    });
</script>
</html>

