<%--<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"
"http://www.wapforum.org/DTD/xhtml-mobile10.dtd" >--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>安全验证</title>
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
    <div style="z-index:999;height:65px;width:100%;background-color:white;margin-bottom: 10px;position:fixed;" >
        <div style="position: relative;height: 60px;width: 100%;background-color: white; border-bottom: 1px solid #afafaf;">
            <a href="/wechat_proxy_func">
                <div style="position: absolute;left: 10px;top: 23px;">
                    <img src="assets/img/back.png" width="38%" alt="">
                </div>
            </a>
            <div style="margin-left: 38%;position: absolute;">
                <img src="/assets/img/mng.png" style="width: 15%;margin-top: 15px;">
                <span style="position:absolute;top:25px;font-size:14px;font-family: Arial;color: #292929;">安全验证</span>
            </div>
        </div>
        <br>

        <input type="hidden" value="${integral}" id="integral" />
    </div>
    <div style="margin-top: 30%;margin-left: 5%">
        <p>请输入您需要绑定提现的微信号：</p>
        <input type="text" class="form-control" id="wechatNum" style="margin-top:10px;width: 92%;"/>
        <p style="color:red;font-size: 13px;margin-right: 6%;text-align: right">（注意！不是昵称！）</p>

        <br><br>

        <p>请再次输入您需要绑定提现的微信号：</p>
        <input type="text" class="form-control" id="verifwechatNum" style="margin-top:10px;width: 92%;"/>
        <p style="color:red;font-size: 13px;margin-right: 6%;text-align: right">（注意！不是昵称！）</p>

        <p style="margin-top:20px;line-height: 20px;font-size: 12px;">
            请注意: <br>
            输入您的微信号不是昵称;<br>
            为了您的账户安全，绑定的微信号不可修改;<br>
            确认无误请点击下一步<br>
        </p>
        <center>
            <input type="button" class="btn btn-primary" id="confirmNext" style="margin-top:20px;padding-left: 40px;padding-right: 40px;"  value="下一步"/>
        </center>
    </div>

</div>
</body>
<script>
    $(function () {
        $("#confirmNext").click(function () {
            var wechatNum = $("#wechatNum").val();
            var verifwechatNum = $("#verifwechatNum").val();
            var integral = $("#integral").val();

            if(wechatNum != verifwechatNum){
                $('body').dialog({
                    titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',
                    title:'提示信息',discription:'两次输入的微信号不一致，请重新输入！',
                    buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
                });
            }else{
                window.location.href="/confirmation_submission?wechatNumber="+wechatNum+"&integral="+integral;
            }
        });
    });
</script>
</html>

