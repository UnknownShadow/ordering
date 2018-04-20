<%--<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"
"http://www.wapforum.org/DTD/xhtml-mobile10.dtd" >--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>安全验证</title>
    <link rel="stylesheet" href="/assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="/assets/scripts/jquery-1.9.1.min.js"></script>
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
    .c{
        background-color: white;
        border-radius: 10px;
        padding: 18px;
        padding-bottom: 100px;
        padding-top: 100px;
        margin-bottom: 30px;
        margin-left: 10px;
        margin-right: 10px;
        box-shadow:5px 2px 6px #000 inset;
    }
</style>
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
                <span style="position:absolute;top:25px;font-size:14px;font-family: Arial;color: #292929;">安全验证</span>
            </div>
        </div>
        <br>

        <div style="margin-left: 10%;margin-top: 10%;">
            <label>请输入您游戏中绑定的手机号码：</label>
            <input type="text" id="phone" class="form-control" style="margin-top:10px;width:90%;"/>
        </div>

        <div style="width: 100%;text-align: center;">
            <input type="button" id="identifyingCode" value="获取验证码" style="margin-top:20px;padding-left: 30px;padding-right: 30px;" class="btn btn-primary"/>
        </div>

        <input type="hidden" value="${flag}" id="flag">
        <input type="hidden" value="${integral}" id="integral">

        <br>
        <div style="margin-left: 10%;margin-top: 5%;">
            <label>请输入验证码：</label>
            <input type="text" id="verification" class="form-control" maxlength="6" style="margin-top:10px;width: 90%"/>
        </div>

        <div style="width: 100%;text-align: center;">
            <input type="button" id="next" value="下一步" style="margin-top:20px;padding-left: 40px;padding-right: 40px;" class="btn btn-primary"/>
        </div>
    </div>
</div>
<script>

   var verify=091238;
   $(function () {

       $("#identifyingCode").click(function () {
           var phone = $("#phone").val();
           if(phone != ""){
               $.ajax({
                   type: 'post',
                   url: '/getPhone',
                   data: {'phone':phone},
                   success: function(data) {
                       if(data==0){
                           $('body').dialog({
                               titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',
                               title:'提示信息',discription:'对不起，您输入的不是游戏中绑定的手机号！',
                               buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
                           },function(ret) {
                               if (ret.index === 0) {
                                   $("#phone").val("");
                                   $("#phone").focus();
                               }
                           });
                       }else {
                           $("#identifyingCode").attr("disabled","true");
                           clock();
                       }
                   }
               });
           }else{
               $('body').dialog({
                   titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',
                   title:'提示信息',discription:'手机号码不能为空！',
                   buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
               });
           }
       });


       //点击下一步操作
       $("#next").click(function () {
           var flag = $("#flag").val();
           var verification = $("#verification").val();
           var integral = $("#integral").val();


           if(verification==""){
               $('body').dialog({
                   titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',
                   title:'提示信息',discription:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请填写验证码！',
                   buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
               });
           }else if(verify!=verification){
               $('body').dialog({
                   titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',
                   title:'提示信息',discription:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;验证码错误！',
                   buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
               });
           }else{
               if(flag==0){   //不是首次提现
                   window.location.href="/confirmation_submission?wechatNumber=&integral="+integral;
               }else{
                   window.location.href="/binding_wechat?integral="+integral;
               }
           }
       });
   });


   /*验证码的发送*/
   function GetRandomNum(Min,Max) {
       var Range = Max - Min;
       var Rand = Math.random();
       return(Min + Math.round(Rand * Range));
   }

   var i=60;
   var intv;
   function dd() {
       $("#identifyingCode").val((--i)+"s后重试");
       $("#identifyingCode").attr("disabled","true");
       if (i == 0) {
           window.clearInterval(intv);
           $("#identifyingCode").removeAttr("disabled");
           $("#identifyingCode").val("获取验证码");
           i = 60;
       }
   }

   function clock() {
       var phone = $("#phone").val();
       var PhoneReg = /^0{0,1}(13[0-9]|15[0-9]|153|156|18[1-9])[0-9]{8}$/; //手机正则
       if(PhoneReg.test(phone)){
           intv=self.setInterval("dd()",1000);
           verify=GetRandomNum(123456,987654);
           $.ajax({
               type:"post",
               url:"/weChatVerification",
               data:{"code":verify,"phone":phone},
               success:function(msg) {
                   if(msg==0){
                       alert("手机号码为空");
                       return false;
                   }
               }
           });
       }else {
           alert("请输入正确的手机号");
       }
   }
   /*验证码的发送*/
</script>
</body>
</html>

