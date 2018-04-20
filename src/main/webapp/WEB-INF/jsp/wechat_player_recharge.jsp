<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>充值</title>
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
</style>
<body>

<div style="width:100%;position: relative;">
    <div style="width: 0%;height: 50px;position: absolute;left: 0px;"></div>
    <div style="width: 100%;height: auto;position: absolute;">
        <div style="position: relative;">
            <div style="margin: 0px;padding: 0px;position:fixed;top:0px;z-index: 999;" >
                <div style="margin: 0px;padding: 0px;">
                    <img src="assets/weChat/recharge/player_head.jpg" style="margin: 0px;padding: 0px; width: 100%;" class="img-responsive" >
                    <a href="javascript:;" onclick="window.history.go(-1);wondow.history.back();"><img src="assets/weChat/recharge/back.png" style="margin-left:12px;margin-top:-80px;position:absolute; width: 10px;" class="img-responsive" ></a>
                </div>
            </div>

            <input type="hidden" value="${dataVoucher}" id="dataVoucher"/>


            <div style="margin-top: 120px;margin-bottom: 90px;">
                <!-- 遍历商品信息 -->
                <c:forEach items="${productDatas}" var="zhi">
                    <div class="recharge" alt="${zhi.pay_price}" id="${zhi.diamond}" style="margin-top: 10px; margin-left:5%;padding: 0px; width: 90%;z-index: -999;height: 110px;background-color: white;">

                        <img src="${zhi.diamond_icon}" style="width: 86px;margin-top: 12px;">

                        <div style="position:absolute;margin-left: 26%;margin-top: -85px;">
                            <span style="font-weight: 800;">${zhi.title}</span><br>
                        </div>
                        <div style="position:absolute;margin-left: 26%;margin-top: -55px;">
                            <img src="bs/images/201710091655591.png" style="width: 35px;margin-top: -5px;" alt="">
                            <span style="font-weight: 800;color: #ff0090;font-size: 18px;">${zhi.show_diamond}</span><br>
                        </div>
                        <div style="position:absolute;margin-left: 26%;margin-top: -20px;">
                            <span style="text-indent:-25px;font-size: 12px;font-weight: 700;">
                                现价:<span style="color: #ff0000;">${zhi.pay_price/100}</span>元
                                <span>(${zhi.room_card_price})</span>
                            </span>
                        </div>

                        <div style="position:absolute;margin-left: 73%;margin-top: -5px;">
                            <span style="text-indent:-25px;font-size: 2px;font-weight: 700;">
                                <s style="color: #cfcfcf;">原价:${zhi.original_price/100}元 </s><br>
                            </span>
                        </div>

                        <div style="position:absolute;margin-left: 75%;margin-top: -98px;">
                            <span style="position: absolute;color: white;margin-left: 4px;margin-top: 10px;">${zhi.discount}</span>
                            <img src="bs/images/dis.png" style="width: 40px;">
                        </div>
                    </div>
                </c:forEach>
            </div>

            <input type="hidden" value="${user_status}" id="user_status"/>
            <input type="hidden" value="${openid}" id="openid"/>
            <input type="hidden" value="${userID}" id="userID"/>

            <div class="row" style="margin: 0px;padding: 0px;position:fixed;bottom:0; z-index: 999">
                <div class="col-md-12" style="margin: 0px;padding: 0px;">
                    <img src="assets/weChat/recharge/confirm_pay.jpg" id="pay" style="position:absolute;top:25px;right:10px;width: 24%;margin-bottom:-5px;z-index: 999;"/>
                    <h4><label style="position: absolute;top: 22px;left: 75px;" id="lab">￥0&nbsp;元</label></h4>
                    <c:if test="${user_status==3}">
                        <img src="assets/weChat/recharge/pay.jpg" style="margin-top: 10px;padding: 0px; width: 100%;margin-bottom:-5px;z-index: -999;"/>
                    </c:if>
                    <c:if test="${user_status!=3}">
                        <img src="assets/weChat/recharge/pay_proxy.jpg" style="margin-top: 10px;padding: 0px; width: 100%;margin-bottom:-5px;z-index: -999;"/>
                    </c:if>
                </div>
            </div>

        </div>
    </div>
    <div style="width: 0%;height: 100px;position: absolute;right: 0px;"></div>
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


    var data_voucher="";
    $(function () {

        var total_number = 0;
        var diamonds = 0;

        $(".recharge").click(function () {
            var pay_num = parseInt($(this).attr("alt"));
            var diamond = parseInt($(this).attr("id"));

            $(this).css("border","2px solid #50c93e");
            $(this).siblings(".recharge").css("border","none");

            total_number = pay_num;
            diamonds = diamond;

            $("#lab").text("￥"+total_number/100+" 元");
        });



        var dataVoucher = $("#dataVoucher").val();
        var userID = $("#userID").val();
        var openid= $("#openid").val();
        data_voucher=dataVoucher;


        $("#pay").click(function () {

            if(userID==""){
                alert("您当前还不是游戏玩家，无法进行支付");
            }else if(total_number==0){
                alert("请选择钻石套餐");
            }else{
                $.ajax({
                    type:"post",
                    url:"/jspay",
                    data:{openId:openid,"total_number":total_number,"userID":userID,"diamonds":diamonds,"dataVoucher":dataVoucher},
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
            }
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
                    window.location.href="/wechat_recharge_success";
                }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
                    alert("订单已取消");
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

