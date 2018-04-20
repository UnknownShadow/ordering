<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>潮尚玩下载</title>
    <meta http-equiv="cache-control" content="no-cache">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <script src="assets/scripts/demo.js"></script>
    <script src="assets/scripts/zepto.min.js"></script>
    <!-- <script src="assets/scripts/jweixin-1.0.0.js"></script>
    <link rel="stylesheet" type="text/css" href="styles.css">-->
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0" />
    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</head>
<style>
    html,body{
        height: 100%;
        background: white;
    }
</style>
<body>
 <%--   <%
        //签名
        String url = request.getScheme()+"://";
        url+=request.getHeader("host");
        url+="/share";//request.getRequestURI();
        if(request.getQueryString()!=null){
            url+="?"+request.getQueryString();
        }

        Map<String,String> sign = WxUtil.getSign(url);
        String timestamp = sign.get("timestamp");
        String nonceStr = sign.get("nonceStr");
        String jsapi_ticket = sign.get("jsapi_ticket");
        String signature = sign.get("signature");
        //String url = sign.get("url");
    %>--%>


    <img src="/assets/img/sharebkg.png" style="width: 100%;">

    <input type="hidden" value="${user_id}" id="userId"/>
    <input type="hidden" value="${timestamp}" id="timestamp"/>
    <input type="hidden" value="${nonceStr}" id="nonceStr"/>
    <input type="hidden" value="${jsapi_ticket}" id="jsapi_ticket"/>
    <input type="hidden" value="${signature}" id="signature"/>
    <input type="hidden" value="${APPID}" id="APPID"/>


    <a href="https://admin.juunew.com/game_download">
        <img src="/assets/img/down.png" style="margin-top:-100px;width:43%;margin-left:28.5%;" class="img-responsive"/>
    </a>
    <%--<div style="margin-top:-65px;color:#ffdd81;font-size:17px;margin-left:36%;" id="cli">邀请人ID：<span id="ID"></span></div>--%>

    <script>
        var link='https://admin.juunew.com/share?user_id=';
        var user_id="";

        $(function () {
            user_id = $("#userId").val();

            /*var name,value;
            var str=location.href; //取得整个地址栏

            var num=str.indexOf("?")
            str=str.substr(num+1); //取得所有参数   stringvar.substr(start [, length ]
            var arr=str.split("&"); //各个参数放到数组里
            for(var i=0;i < arr.length;i++){
                num=arr[i].indexOf("=");
                if(num>0){
                    name=arr[i].substring(0,num);
                    value=arr[i].substr(num+1);
                    this[name]=value;
                    if(name=="user_id"){
                        user_id=value;
                    }
                }
            }*/
            /*$("#ID").text(user_id);*/
            link = link+user_id;
        });
    </script>

</body>

<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>

<script type="text/javascript">

    var timestamp = document.getElementById("timestamp").value;
    var nonceStr = document.getElementById("nonceStr").value;
    var jsapi_ticket = document.getElementById("jsapi_ticket").value;
    var signature = document.getElementById("signature").value;
    var APPID = document.getElementById("APPID").value;

    var appid=APPID;


    wx.config({
        debug: false,
        appId: appid,
        timestamp: timestamp,
        nonceStr: nonceStr,
        signature: signature,
        jsApiList: [
            'checkJsApi',
            'onMenuShareTimeline',
            'onMenuShareAppMessage'
        ] // 功能列表，我们要使用JS-SDK的什么功能
    });

    wx.ready(function(){

        // 获取"分享到朋友圈"按钮点击状态及自定义分享内容接口
        wx.onMenuShareTimeline({
            title: '【潮尚玩】好友邀您一起扶风干牌领大奖', // 分享标题
            desc: '输入我的ID，100钻一秒领走', // 分享描述
            link: link,
            imgUrl: 'http://yxx.ufile.ucloud.com.cn/test/reward/20170918185609' // 分享图标

        });


        // 获取"分享给朋友"按钮点击状态及自定义分享内容接口
        wx.onMenuShareAppMessage({
            title: '【潮尚玩】好友邀您一起扶风干牌领大奖', // 分享标题
            desc: '输入我的ID，100钻一秒领走', // 分享描述
            link: link,
            imgUrl: 'http://yxx.ufile.ucloud.com.cn/test/reward/20170918185609', // 分享图标
            type: 'link', // 分享类型,music、video或link，不填默认为link
            success: function () {
                // 用户确认分享后执行的回调函数
                alert("分享给朋友成功");
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
                alert("分享给朋友失败");
            }
        });

    });

    /*function UrlSearch(){
        var name,value;
        var str=location.href; //取得整个地址栏

        var num=str.indexOf("?")
        str=str.substr(num+1); //取得所有参数   stringvar.substr(start [, length ]

        var arr=str.split("&"); //各个参数放到数组里
        for(var i=0;i < arr.length;i++){
            num=arr[i].indexOf("=");
            if(num>0){
                name=arr[i].substring(0,num);
                value=arr[i].substr(num+1);
                this[name]=value;
                alert(value);
            }
        }
    }
    var Request=new UrlSearch(); //实例化
    alert(Request.id);
    //alert(Request.id);*/
</script>
</html>