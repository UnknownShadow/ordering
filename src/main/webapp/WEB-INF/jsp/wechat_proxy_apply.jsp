<%--<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"
"http://www.wapforum.org/DTD/xhtml-mobile10.dtd" >--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>代理申请</title>
    <script src="bs/js/holder.js"></script>
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0" />
    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
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
    textarea{
        border:0;
        outline:none;
        background:rgba(0, 0, 0, 0);
    }
</style>
<body>
    <div class="container">
        <input type="hidden" value="${userID}" id="userID"/>
        <input type="hidden" value="${nickname}" id="nickname"/>

        <div class="row" style="margin: 0px;padding: 0px;">
            <div class="col-md-12" style="margin: 0px;padding: 0px;">
                <img src="assets/weChat/header.jpg" style="margin: 0px;padding: 0px; width: 100%;" class="img-responsive" >

                <div style="position: absolute;top: 20.5%;left: 60px;height: 10px;font-size: 16px;width: 260px;">
                   <label style="position: absolute;top: 12px;left: 25%;color: #6C6E88;font-size: 12px;">
                       <c:if test="${userID!=''}">${userID}</c:if>
                   </label>
                </div>

                <div style="position: absolute;top: 31.5%;left: 50px;height: 10px;font-size: 16px;width: 260px;">
                    <input type="text" maxlength="5" id="name" placeholder="请输入您的真实姓名" style="position: absolute;"/>
                </div>
                <div style="position: absolute;top: 42.5%;left: 50px;height: 10px;font-size: 16px;width: 260px;">
                    <input type="text" placeholder="请输入您的手机号" id="phone" maxlength="11" style="position: absolute;"/>
                </div>

                <div style="position: absolute;top: 53.5%;left: 80px;height: 10px;font-size: 16px;width: 160px;">
                    <input type="text" placeholder="请输入验证码" id="verification" style="position: absolute;" maxlength="4"/>
                </div>

                <div style="position: absolute;top: 53.5%;right:-20px;font-size: 16px;width: 160px;">
                    <input type="button" onclick="clock()" class="btn btn-warning" id="bttn" style="width:85px;height: 35px;border: 0px;outline: none;border-radius: 20px;padding-left:7px; padding-top: 7px;" value="获取验证码"/>
                </div>



                <div style="position: absolute;top: 66.5%;left: 125px;height: 23px;font-size: 16px;width: 260px;">
                    <textarea maxlength="40" placeholder="请输入通讯地址" id="addr" style="position: absolute;" cols="20" rows="3"></textarea>
                </div>

                <a href="javascript:;" id="sub"><img src="assets/weChat/submit.jpg" style="position: absolute; width: 90%;left: 5%;bottom: 10px;"></a>
                
                <img src="assets/weChat/body.jpg" style="margin-top: 10px;padding: 0px; width: 100%;z-index: -999;">
            </div>
        </div>
    </div>
</body>
<script>
    var verify=9281;

    $(function () {
        $("#sub").click(function () {
            var userID = $("#userID").val();
            var nickname = $("#nickname").val();
            var name = $("#name").val();
            var phone = $("#phone").val();
            var verification = $("#verification").val();
            var addr = $("#addr").val();


            if(name==""){
                alert("请填写姓名");
            }else if(phone==""){
                alert("请填写手机号");
            }else if(verification==""){
                alert("请填写验证码");
            }else if(addr==""){
                alert("请填写地址");
            }else if(verify!=verification){
                alert("验证码错误");
            }else{
                window.location.href="/proxy_apply?name="+name+"&phone="+phone+"&addr="+addr+"&userID="+userID+"&nickname="+nickname;
            }
        });
        //上部分是表单提交按钮
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
        $("#bttn").val((--i)+"s后重试");
        $("#bttn").attr("disabled","true");
        if (i == 0) {
            window.clearInterval(intv);
            $("#bttn").removeAttr("disabled");
            $("#bttn").val("获取验证码");
            i = 60;
        }
    }


    function clock() {
        var phone = $("#phone").val();
        var PhoneReg = /^0{0,1}(13[0-9]|15[0-9]|153|156|18[1-9])[0-9]{8}$/; //手机正则
        if(PhoneReg.test(phone)){
            intv=self.setInterval("dd()",1000);
            verify=GetRandomNum(1234,9876);
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
</html>

