<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>绑定游戏ID</title>
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
    input{
        border:0;
        outline:none;
        background:rgba(0, 0, 0, 0);
        width: 67%;height:40px;
        margin-left: 29%
    }
</style>
<body>
    <div class="container">
        <input type="hidden" value="${openid}" id="openid"/>
        <input type="hidden" value="${userData.id}" id="userID"/>
        <input type="hidden" value="${userData.nickname}" id="nickname"/>

        <div class="row" style="margin: 0px;padding: 0px;">
            <div class="col-md-12" style="margin: 0px;padding: 0px;position: relative;">
                <img src="${headimgurl}" style="width:90px;height:90px;border-radius: 50%;left:38%;top:19%;position: absolute;border: solid 2px #849d9c;">
                <div style="position: relative;" >
                    <span style="position:absolute;top: 170px;left: 12%;color: white;">玩家ID：${userData.id}</span>
                    <span style="position:absolute;top: 170px;right: 12%;color: white;">钻石数量:${userData.diamond}</span>
                </div>
                <div style="margin: 0px;padding: 0px; width: 100%;height: 200px;overflow: hidden;">
                    <img src="assets/weChat/logo.jpg" style="position: absolute;margin: 0px;padding: 0px; width:100%;z-index: -999" class="img-responsive" >
                    <center><label  style="margin-top:40%;position:relative;color: white;">${userData.nickname}</label></center>
                </div>
            </div>
        </div>
        <input type="hidden" value="${dispose_result}" id="disposeResult"/>
        <input type="hidden" value="${flag}" id="flg"/>
        <input type="hidden" value="${proxy_flag}" id="proxy_flag"/>

        <!-- 邀请入口 -->
        <a href="javascript:;" id="athree" onclick="var id=document.getElementById('userID').value;if(id!=''){window.location.href='https://admin.juunew.com/share?user_id='+id;}else{var cf=confirm('对不起,您还没有游戏ID,\n请先用当前微信号登陆游戏后获取游戏ID！\n是否现在前往下载游戏?');if(cf){window.location.href='https://admin.juunew.com/game_download'}else{return false;}}">
            <div style="position: absolute;margin-top:0px;width: 100%;height: 60px;background: white;" id="three">
                <div style="position: absolute;right: 2px;top: 35%;">
                    <img src="/assets/img/to.png" width="38%" alt="">
                </div>

                <img src="/assets/img/yaoqing.png" style="position: absolute;left: 10px;top: 28%;width: 26px;">

                <p style="position: absolute;left: 40px;top: 39%;font-size:14px;font-family: Arial;color: #292929;">邀请小伙伴来玩</p>
                <p style="position: absolute;left: 40px;margin-top: 43px;color: #8c8c8c;font-size: 10px;font-weight: 900;">好友组局，欢乐更多</p>
            </div>
        </a>
        <!-- 邀请结束 -->
        <div style="position:absolute;top: 59%;left:20%;font-size:17px;font-family: Arial;color: #292929;">
            恭喜，您的游戏ID绑定成功！
        </div>
    </div>
</body>
</html>

