<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>潮尚玩</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <style>
        html,body{ height: 100%; }
        body{
            /*background: #fff url("assets/img/bg.png") center 0 no-repeat;*/
            background-color: white;
            background-size: cover;
            position: relative;
        }
        #weixin-tip{display:none; position: fixed; left:0; top:0; background: rgba(0,0,0,0.8); filter:alpha(opacity=80); width: 100%; height:100%; z-index: 100;}
        #weixin-tip p{text-align: center; margin-top: 10%; padding:0 5%; position: relative;}
        #weixin-tip .close{
            color: #fff;
            padding: 5px;
            font: bold 20px/20px simsun;
            text-shadow: 0 1px 0 #ddd;
            position: absolute;
            top: 0; left: 5%;
        }
    </style>

    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

<div style="width:100%;position: relative;">
    <div style="width: 0%;height: 50px;position: absolute;left: 0px;"></div>
    <div style="width: 100%;height: auto;position: absolute;">
        <div style="position: relative;">
            <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180327162219" width="100%">

            <%--ontouchstart="mouseDownDown()"  ontouchend="mouseUpDown()"--%>
            <div id="oldVer" style="margin-top: -110px;margin-bottom: 25px;text-align: center;display: none;">
                <img class="img-btn3" id="down-android" src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180125155119"  width="38%" alt="下载btn" style="position:relative;">
                <img class="img-btn1" id="down-break-ios" src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180125154904"  width="38%" alt="下载btn" style="position:relative;">
            </div>

            <div id="nowVer" style="margin-top: -110px;margin-bottom: 25px;text-align: center;">
                <img class="img-btn1" id="appStore" src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180124191434"  width="30%" alt="下载btn" style="position:relative;">
                <img  id="mahjong-android" src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180124191415" width="30%" alt="下载btn" style="position:relative;">
                <img  id="mahjong-break-ios" src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180125145541" width="30%" alt="下载btn" style="position:relative;">
            </div>

            <div style="color: #ed2f3f;text-align: center;font-size: 16px;margin-bottom: 20px;">
                <u id="down-provision">苹果版本下载后无法打开，请点此</u><br>
                <%-- <p style="margin-bottom: 20px;margin-top: 10px;color: #6e4a38;">微信客服：csw-kf</p>--%>
            </div>

        </div>
    </div>
    <div style="width: 0%;height: 100px;position: absolute;right: 0px;"></div>
</div>

<div id="weixin-tip"><p><img src="assets/img/live_weixin.png" width="100%" alt="微信打开"/><span id="close" title="关闭" class="close">×</span></p></div>


<script>
    $(function(){

        var is_weixin = (function() {
            var ua = navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) == "micromessenger") {
                return true;
            } else {
                return false;
            }
        })();
        var winHeight = typeof window.innerHeight != 'undefined' ? window.innerHeight : document.documentElement.clientHeight;
        var tip = document.getElementById('weixin-tip');

        if(is_weixin){
            $('#down-provision').click(function () {
                tip.style.height = winHeight + 'px';
                tip.style.display = 'block';
            });

            $('#close').click(function () {
                tip.style.display = 'none';
            });

            $('#mahjong-android').click(function(){
                tip.style.height = winHeight + 'px';
                tip.style.display = 'block';
            });


        } else {
            $('#down-provision').click(function () {
                location.href = 'https://chaowan.juunew.com/assets/ipa/juunewinhouse.mobileprovision';
            });

            $('#mahjong-android').click(function(){
                location.href = '${android_url}';
            });
        }

        /* $('#down-ios').click(function(){
         location.href = 'https://itunes.apple.com/app/id1216994291';
         });*/

        //AppStore下载链接
        $("#appStore").click(function () {
            location.href = 'https://itunes.apple.com/cn/app/id1337657500?mt=8';
        });

        $('#mahjong-break-ios').click(function(){
            <c:if test="${is_appstore}">
            location.href = '${ios_url}';
            </c:if>

            <c:if test="${!is_appstore}">
            location.href = '/download?channelId=${channelId}';
            </c:if>
        });
    });


</script>
</body>
</html>