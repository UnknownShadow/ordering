<%--<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"
"http://www.wapforum.org/DTD/xhtml-mobile10.dtd" >--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>充值成功</title>
    <script src="bs/js/holder.js"></script>
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0" />
    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</head>
<style>
    *{
        margin: 0px;padding: 0px;
    }
    body{
        background-color: #f6f6f6;
        margin: 0px;padding: 0px;
    }
    .container{
        margin: 0px;
        padding: 0px;
        overflow:hidden;
    }
    input{
         border:0;
         outline:none;
         background:rgba(0, 0, 0, 0);

        width: 67%;height:40px;
        margin-left: 29%
    }
    textarea{
        border:0;
        outline:none;
        background:rgba(0, 0, 0, 0);
    }
</style>
<body>
    <div class="container">
        <div class="row" style="margin: 0px;padding: 0px;">
            <div class="col-md-12"  class="center-block" style="margin: 0px;padding: 0px;position: absolute;">

                <img src="assets/weChat/success.png" class="center-block" style="width: 100px;margin-top: 15%;">
                 <center>
                     <p style="font-size: 22px;margin-top: 5%;">支付成功</p>
                     <p style="font-size: 16px;margin-top: 5%;">成功充值：${diam}钻石</p>
                     <p style="font-size: 30px;margin-top: 5%;"><label>￥${money}</label></p>
                     <input type="hidden" value="${user_id}" id="userId"/>
                 </center>
                <a href="/wxLogin"><img src="assets/weChat/finish.png" style="padding-left: 5%;padding-right: 5%; width: 100%;margin-top: 15%;"/></a>

            </div>
        </div>
    </div>
</body>
</html>

