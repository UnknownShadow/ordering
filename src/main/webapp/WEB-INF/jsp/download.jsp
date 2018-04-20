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
        html,body{
            height: 100%;
        }
        body{
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

            <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20171106182832" width="100%">

            <div style="margin-top:0%;text-align: center;">
                <div style="margin-top: 10px;margin-bottom: 40px;">
                    <img class="img-btn1" id="down-break-ios" src="http://yxx.ufile.ucloud.com.cn/dev/reward/20171106183040" width="50%" alt="下载btn">
                </div>
            </div>
        </div>
    </div>
    <div style="width: 0%;height: 100px;position: absolute;right: 0px;"></div>
</div>

<div id="weixin-tip"><p><img src="assets/img/live_weixin.png" width="100%" alt="微信打开"/><span id="close" title="关闭" class="close">×</span></p></div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="/assets/share/jquery-2.1.1.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script>
    $(function(){
        $('#down-break-ios').click(function(){
            location.href = '${ios_url}';
        });
    });
</script>
</body>
</html>
