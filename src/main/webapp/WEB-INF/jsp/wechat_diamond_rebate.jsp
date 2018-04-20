<%--<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"
"http://www.wapforum.org/DTD/xhtml-mobile10.dtd" >--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>积分返利记录</title>
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
<style>
    *{
        margin: 0;
        padding:0;
        -webkit-tap-highlight-color:rgba(0,0,0,0);
        -webkit-text-size-adjust:none;
    }
    html{
        font-size:10px;
    }
    body{
        background-color: #f6f6f6;
        font-size: 1.2em;
    }
    .tab{
        display: -webkit-box;
        display: -webkit-flex;
        display: -ms-flexbox;
        display: flex;
        height: 44px;
        line-height: 44px;
        border-bottom: 1px solid #ff3c3c;
        background-color: #eee;
    }
    .tab .item{
        display: block;
        -webkit-box-flex: 1;
        -webkit-flex: 1;
        -ms-flex: 1;
        flex: 1;
        width: 100%;
        font-size: 14px;
        text-align: center;
        color: #333;
        text-decoration: none;
    }
    .tab .cur{
        height: 42px;
        border-bottom: 2px solid #ff3c3c;
        color: #ff3c3c;
    }
    .content{
        background-color: #fff;
    }
    .content .item{
        display: -webkit-box;
        display: -webkit-flex;
        display: -ms-flexbox;
        display: flex;
        -ms-flex-align:center;
        -webkit-box-align:center;
        box-align:center;
        -webkit-align-items:center;
        align-items:center;
        padding:3.125%;
        border-bottom: 1px solid #ddd;
        color: #333;
        text-decoration: none;
    }
    .content .item img{
        display: block;
        width: 40px;
        height: 40px;
        border:1px solid #ddd;
    }
    .content .item h3{
        display: block;
        -webkit-box-flex: 1;
        -webkit-flex: 1;
        -ms-flex: 1;
        flex: 1;
        width: 100%;
        max-height: 40px;
        overflow: hidden;
        line-height: 20px;
        margin: 0 10px;
        font-size: 1.2rem;
    }
    .content .item .date{
        display: block;
        height: 20px;
        line-height: 20px;
        color: #999;
    }
    .opacity{
        -webkit-animation: opacity 0.3s linear;
        animation: opacity 0.3s linear;
    }
    @-webkit-keyframes opacity {
        0% {
            opacity:0;
        }
        100% {
            opacity:1;
        }
    }
    @keyframes opacity {
        0% {
            opacity:0;
        }
        100% {
            opacity:1;
        }
    }
</style>
<body>
<div class="content">
    <div style="z-index:999;height:70px;width:100%;background-color:white;margin-bottom: 10px;position:fixed;" >
        <div style="position: relative;height: 60px;width: 100%;background-color: white; border-bottom: 1px solid #afafaf;">
            <a href="/wechat_proxy_func">
                <div style="position: absolute;left: 10px;top: 23px;">
                    <img src="assets/img/back.png" width="38%" alt="">
                </div>
            </a>
            <div style="margin-left: 38%;position: absolute;">
                <img src="/assets/img/mng.png" style="width: 15%;margin-top: 15px;">
                <span style="position:absolute;top:25px;font-size:14px;font-family: Arial;color: #292929;">积分返利记录</span>
            </div>
        </div>
        <br>
    </div>

    <table class="table table-hover lists" style="margin-top: 25%;"></table>    <!-- style="margin-top: 30%;" -->
</div>

<script src="/assets/dropload/dropload.min.js"></script>
<script>
   $(function(){
       // 页数
       var page = 0;
       // 每页展示5个
       var size = 10;

       // dropload
       $('.content').dropload({
           scrollArea : window,
           loadDownFn : function(me){
               page++;
               // 拼接HTML
               var result = '';
               $.ajax({
                   type: 'GET',
                   url: '/wechatDiamondRebate?page='+page+'&size='+size,
                   dataType: 'json',
                   success: function(data){
                       var arrLen = data.length;

                       if(arrLen > 0){
                           for(var i=0; i<arrLen; i++){
                               result += "<tr>"
                                   +"<td style='font-size: 17px;'  rowspan='2'>"
                                   +"<span style='font-size: 13px;'>"+data[i].user_id+"，"+data[i].nickname+"</span>"
                               +"<br>"
                               +"<span style='font-size: 10px;'>"
                                   +data[i].dateTime
                                   +"</span>"
                                   +"<td rowspan='2' style='vertical-align: middle;font-size: 12px;font-family: 黑体;'>充值"+data[i].rechargeNum+"钻</td>"
                                   +"<td rowspan='2' style='vertical-align: middle;font-size: 14px;font-family: 黑体;'>返"+data[i].rebateNum+"积分</td>"
                               +"</tr>"
                               +"<tr></tr>";
                           }
                           // 如果没有数据
                       }else{
                           // 锁定
                           me.lock();
                           // 无数据
                           me.noData();
                       }
                       // 为了测试，延迟1秒加载
                       setTimeout(function(){
                           // 插入数据到页面，放到最后面
                           $('.lists').append(result);
                           // 每次数据插入，必须重置
                           me.resetload();
                       },1000);
                   },
                   error: function(xhr, type){
                       alert('加载失败');
                       // 即使加载出错，也得重置
                       me.resetload();
                   }
               });
           }
       });
   });

</script>
</body>
</html>

