<%--<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"
"http://www.wapforum.org/DTD/xhtml-mobile10.dtd" >--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>申请提现</title>
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
</style>
<body>
<div class="content">
    <div style="z-index:999;height:80px;width:100%;background-color:white;margin-bottom: 10px;position:fixed;" >
        <div style="position: relative;height: 60px;width: 100%;background-color: white; border-bottom: 1px solid #afafaf;">
            <a href="/wechat_proxy_func">
                <div style="position: absolute;left: 10px;top: 23px;">
                    <img src="assets/img/back.png" width="38%" alt="">
                </div>
            </a>
            <div style="margin-left: 38%;position: absolute;">
                <img src="/assets/img/mng.png" style="width: 15%;margin-top: 15px;">
                <span style="position:absolute;top:25px;font-size:14px;font-family: Arial;color: #292929;">申请提现</span>
            </div>
        </div>
        <br>
    </div>


    <input type="hidden" value="${integralNum}" id="integralNum">
    <div style="position: relative; margin-top: 85px;margin-left:5px;">
        <p>您当前可用积分为：${integralNum}</p>

        <div style="margin-left: 10px;margin-top: 20px;">
            <p>请填写您申请兑换的积分：</p>
            <input type="text" class="form-control" id="integral" style="margin-top:10px;width: 80%;"/>
        </div>

        <p style="line-height:22px;font-size: 12px;padding-left:10px;padding-right:10px;margin-top: 20px;">

            提现须知：<br>
            1.为保证您的账号安全，每次提现均需验证您绑定的手机号；<br>
            2.提现通过微信进行转账，首次提现需要绑定您的微信号；<br>
            3.为了您的账号安全，绑定的微信号无法更改；<br>
            4.提交提现申请后，一般1~2个工作日内即可到账；<br>
        </p>

        <div style="width: 100%;text-align: center;margin-top: 30px;">
            <input type="button" value="申请提现" id="application" style="padding-left: 40px;padding-right: 40px;" class="btn btn-primary"/>
        </div>
    </div>
</div>
<script>
   $(function () {

       $("#application").click(function () {
           var integral = $("#integral").val();
           var integralNum = $("#integralNum").val();

           //转换为数值；
           var integralVa = parseInt(integral);
           var integralNumVa = parseInt(integralNum);

           var reg = /^[0-9]*$/;

           if(integral == ""){
               $('body').dialog({
                   titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',
                   title:'提示信息',discription:'&nbsp;&nbsp;&nbsp;请输入要提现的积分值！',
                   buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
               });
           }else if(integralVa < 2000){
               $('body').dialog({
                   titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',
                   title:'提示信息',discription:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;积分不能小于2000',
                   buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
               });
           }else if(!reg.test(integral)){
               $('body').dialog({
                   titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',
                   title:'提示信息',discription:'&nbsp;&nbsp;&nbsp;&nbsp;请输入正确的积分值！',
                   buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
               },function(ret) {
                   if (ret.index === 0) {
                       $("#integral").val("");
                   }
               });
           }else if(integralVa>integralNumVa){
               $('body').dialog({
                   titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',
                   title:'提示信息',discription:'不能超过自己已有的积分值！',
                   buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
               });
           }else{
               window.location.href="/phone_verification?integral="+integral;
           }
       });
   });
</script>
</body>
</html>

