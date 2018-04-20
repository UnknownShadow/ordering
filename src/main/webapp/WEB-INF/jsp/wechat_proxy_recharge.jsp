<%--<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"
"http://www.wapforum.org/DTD/xhtml-mobile10.dtd" >--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>代理充值</title>
    <script src="bs/js/holder.js"></script>
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0" />
    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/scripts/jweixin-1.0.0.js"></script>
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
    #gets{

    }
</style>
<body>
    <div class="container">
        <div class="row" style="margin: 0px;padding: 0px;position:fixed;top:0px;z-index: 999;" >
            <div class="col-md-12" style="margin: 0px;padding: 0px;">
                <img src="assets/weChat/recharge/player_head.jpg" style="margin: 0px;padding: 0px; width: 100%;" class="img-responsive" >
            </div>
        </div>

        <div class="row" style="margin: 0px;padding: 0px; margin-top: 110px; z-index: -999;margin-bottom: 80px;">
            <div class="col-md-12" style="margin: 0px;padding: 0px;">

                <img src="assets/weChat/recharge/50000.jpg" id="recharge1" alt="0" style="margin-top: 10px; margin-left:5%;padding: 0px; width: 90%;z-index: -999;"/>

                <img src="assets/weChat/recharge/30000.jpg" id="recharge2" alt="0" style="margin-top: 10px; margin-left:5%;padding: 0px; width: 90%;z-index: -999;"/>

                <img src="assets/weChat/recharge/15000.jpg" id="recharge3" alt="0" style="margin-top: 10px; margin-left:5%;padding: 0px; width: 90%;z-index: -999;"/>

                <img src="assets/weChat/recharge/10000.jpg" id="recharge4" alt="0" style="margin-top: 10px; margin-left:5%;padding: 0px; width: 90%;z-index: -999;"/>

                <img src="assets/weChat/recharge/5000.jpg" id="recharge5" alt="0" style="margin-top: 10px; margin-left:5%;padding: 0px; width: 90%;z-index: -999;"/>

                <img src="assets/weChat/recharge/2500.jpg" id="recharge6" alt="0" style="margin-top: 10px; margin-left:5%;padding: 0px; width: 90%;z-index: -999;"/>

            </div>
        </div>

        <input type="hidden" value="${openid}" id="openid"/>


        <div class="row" style="margin: 0px;padding: 0px;position:fixed;bottom:0; z-index: 999">
            <div class="col-md-12" style="margin: 0px;padding: 0px;">

                <img src="assets/weChat/recharge/confirm_pay.jpg" id="pay" style="position:absolute;top:25px;right:10px;width: 24%;margin-bottom:-5px;z-index: 999;"/>
                <h4><label style="position: absolute;top: 22px;left: 75px;" id="lab">￥0</label></h4>
                <img src="assets/weChat/recharge/pay.jpg" style="margin-top: 10px;padding: 0px; width: 100%;margin-bottom:-5px;z-index: -999;"/>
            </div>
        </div>


    </div>
</body>
<script>
    var prepay_id ;
    var paySign ;
    var appId ;
    var timeStamp ;
    var nonceStr ;
    var packageStr ;
    var signType ;

    $(function () {
        var flag1=1;var flag2=1;var flag3=1;
        var flag4=1;var flag5=1;var flag6=1;
        var total_number = 0;

        $("#recharge1").click(function () {
            $(this).attr('src', 'assets/weChat/recharge/checked/50000_back.jpg');
            total_number = total_number+2000;

            if(flag1%2==0){
                total_number = total_number-4000;
                $(this).attr('src', 'assets/weChat/recharge/50000.jpg');
                flag1=0;
            }
            $("#lab").text("￥"+total_number);
            flag1++;
        });
        $("#recharge2").click(function () {
            $(this).attr('src', 'assets/weChat/recharge/checked/30000_back.jpg');
            total_number = total_number+1260;

            if(flag2%2==0){
                total_number = total_number-2520;
                $(this).attr('src', 'assets/weChat/recharge/30000.jpg');
                flag2=0;
            }
            $("#lab").text("￥"+total_number);
            flag2++;
        });
        $("#recharge3").click(function () {
            $(this).attr('src', 'assets/weChat/recharge/checked/15000_back.jpg');
            total_number = total_number + 660;

            if(flag3%2==0){
                total_number = total_number - 1320;
                $(this).attr('src', 'assets/weChat/recharge/15000.jpg');
                flag3=0;
            }
            $("#lab").text("￥"+total_number);
            flag3++;
        });
        $("#recharge4").click(function () {
            $(this).attr('src', 'assets/weChat/recharge/checked/10000_back.jpg');
            total_number = total_number + 460;

            if(flag4%2==0){
                total_number = total_number - 920;
                $(this).attr('src', 'assets/weChat/recharge/10000.jpg');
                flag4=0;
            }
            $("#lab").text("￥"+total_number);
            flag4++;
        });
        $("#recharge5").click(function () {
            $(this).attr('src', 'assets/weChat/recharge/checked/5000_back.jpg');
            total_number = total_number + 240;

            if(flag5%2==0){
                total_number = total_number - 480;
                $(this).attr('src', 'assets/weChat/recharge/5000.jpg');
                flag5=0;
            }
            $("#lab").text("￥"+total_number);
            flag5++;
        });
        $("#recharge6").click(function () {
            $(this).attr('src', 'assets/weChat/recharge/checked/2500_back.jpg');
            total_number = total_number + 125;

            if(flag6%2==0){
                total_number = total_number - 250;
                $(this).attr('src', 'assets/weChat/recharge/2500.jpg');
                flag6=0;
            }
            $("#lab").text("￥"+total_number);
            flag6++;
        });

        var openid = "333333";// $("#openid").val();


        $("#pay").click(function () {
            $.ajax({
                type:"post",
                url:"/jspay",
                data:{openId:openid,"total_number":total_number,"userID":"45","diamonds":"25","dataVoucher":"123321147789"},
                success:function(data) {
                    if(data.resultCode == 'SUCCESS'){


                        appId = data.appId;
                        paySign = data.paySign;
                        timeStamp = data.timeStamp;
                        nonceStr = data.nonceStr;
                        packageStr = data.packageStr;
                        signType = data.signType;
                        callpay();

                    }else{
                        alert("统一下单失败");
                    }
                }
            });
        });

    })


    function onBridgeReady(){

        var map = {
            "appId":appId,     //公众号名称，由商户传入
            "paySign":paySign,         //微信签名
            "timeStamp":timeStamp, //时间戳，自1970年以来的秒数
            "nonceStr":nonceStr , //随机串
            "package":packageStr,  //预支付交易会话标识
            "signType":signType     //微信签名方式
        };

        //alert(JSON.stringify(map));

        WeixinJSBridge.invoke(
            'getBrandWCPayRequest', map,
            function(res){
                if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                    //window.location.replace("index.html");
                    alert('支付成功');
                }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
                    alert('支付取消');
                }else if(res.err_msg == "get_brand_wcpay_request:fail" ){

                    alert(res.err_code+res.err_desc+res.err_msg);
                } //使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
            });

    }

    function callpay(){
        if (typeof WeixinJSBridge == "undefined"){
            if( document.addEventListener ){
                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
            }else if (document.attachEvent){
                document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
            }
        }else{
            onBridgeReady();
        }
    }



</script>
</html>

