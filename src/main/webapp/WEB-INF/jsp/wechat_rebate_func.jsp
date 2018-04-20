<%--<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"
"http://www.wapforum.org/DTD/xhtml-mobile10.dtd" >--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>积分返利</title>
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
</style>
<body>
    <div class="container">
        <input type="hidden" value="${user_status}" id="userStatus"/>
        <input type="hidden" value="${user_id}" id="userID"/>
        <div style="position:relative;margin-bottom: 10px;" >
            <a href="/wechat_proxy_func">
                <div style="position: absolute;left: 10px;top: 35%;">
                    <img src="assets/img/back.png" width="38%" alt="">
                </div>
            </a>
            <img src="assets/img/rebate_func.jpg" width="100%" alt="">
        </div>

        <div style="margin-top: 40px;">

            <input type="hidden" value="${totalRebateNum}" id="totalrebateNum" />
            <input type="hidden" value="${withdrawalsIntegral}" id="withdrawalsIntegral" />


            <!-- 我的积分入口 -->
            <a href="/wechat_my_rebate" >
                <div style="position: absolute;width: 100%;height: 60px;background: white;">
                    <div style="position: absolute;right: 2px;top: 30%;">
                        <img src="assets/img/to.png" width="38%" alt="">
                    </div>
                    <img src="/assets/img/my_rebate.png" style="position: absolute;left: 10px;top: 28%;width: 26px;">
                    <p style="position: absolute;left: 40px;top: 39%;font-size:14px;font-family: Arial;color: #292929;">我的积分</p>
                </div>
            </a>
            <!-- 我的积分结束 -->

            <!-- 申请提现入口 -->
            <a href="javascript:;" id="withdrawalsCash">
                <div style="position: absolute;margin-top:70px;width: 100%;height: 60px;background: white;">
                    <div style="position: absolute;right: 2px;top: 30%;">
                        <img src="assets/img/to.png" width="38%" alt="">
                    </div>
                    <img src="/assets/img/cash.png" style="position: absolute;left: 10px;top: 28%;width: 26px;">
                    <p style="position: absolute;left: 40px;top: 39%;font-size:14px;font-family: Arial;color: #292929;">申请提现</p>
                </div>
            </a>
            <!-- 申请提现结束 -->


            <!-- 提现记录入口 -->
            <a href="/withdrawals_cash_record">
                <div style="position: absolute;margin-top:140px;width: 100%;height: 60px;background: white;" id="thre">
                    <div style="position: absolute;right: 2px;top: 35%;">
                        <img src="assets/img/to.png" width="38%" alt="">
                    </div>

                    <img src="/assets/img/record1123.png" style="position: absolute;left: 10px;top: 28%;width: 26px;">
                    <p style="position: absolute;left: 40px;top: 39%;font-size:14px;font-family: Arial;color: #292929;">提现记录</p>
                </div>
            </a>
            <!-- 提现记录结束 -->


            <!-- 积分返利记录入口：-->
            <a href="/wechat_diamond_rebate">

                <div style="position: absolute;margin-top:210px;width: 100%;height: 60px;background: white;">
                    <div style="position: absolute;right: 2px;top: 35%;">
                        <img src="assets/img/to.png" width="38%" alt="">
                    </div>

                    <img src="/assets/img/rebate_record.png" style="position: absolute;left: 10px;top: 28%;width: 26px;">
                    <p style="position: absolute;left: 39px;top: 39%;font-size:14px;font-family: Arial;color: #292929;">积分返利记录</p>
                </div>
            </a>
            <!-- 积分返利记录结束 -->

        </div>
    </div>
</body>
<script>
    $(function () {
        $("#withdrawalsCash").click(function () {
            var totalrebateNum = $("#totalrebateNum").val();
            var withdrawalsIntegral = $("#withdrawalsIntegral").val();
            if(totalrebateNum<2000){
                $('body').dialog({
                    titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',
                    title:'提示信息',discription:'您的积分不足，需满2000积分才可申请提现',
                    buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
                });
            }else if(withdrawalsIntegral!=0){
                $('body').dialog({
                    titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',
                    title:'提示信息',discription:'您已申请了提现，正在审核中，请耐心等待！',
                    buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
                });
            }else {
                window.location.href="/application_withdrawals";
            }
        });
    });
</script>
</html>

