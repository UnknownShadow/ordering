<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>潮尚玩下载</title>
    <meta http-equiv="cache-control" content="no-cache">
    <script src="/assets/scripts/jquery-1.9.1.min.js"></script>
    <link rel="stylesheet" href="/assets/vendor/bootstrap/css/bootstrap.min.css">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0"/>
    <script src="/assets/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="/assets/clipboard.js"></script>
</head>
<style>
    html, body {
        height: 100%;
        background: white;
        margin: 0px;
    }
</style>
<body>
    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180327162219" style="width: 100%;">

    <div style="position: relative;">
        <center>
            <div style="font-size:20px;font-weight:700;margin-top:-100px;position: relative;">
                请输入我的邀请码：<span id="target"></span>
                <button class="btn btn-primary" data-clipboard-action="copy" data-clipboard-target="#target" id="copy_btn"
                        style="border:0px;background-color: #00a0f0;border-radius:6px;padding: 3px;padding-right: 15px;padding-left: 15px;">
                    复制
                </button>
                <br>
            </div>

            <div style="font-size:12px;font-weight: 700;margin-top: 20px;">
                输入我的邀请码首绑立得4钻，且获得商城购钻价格优惠。<br>
                请进入游戏后点击左下角【邀请码】，即可在弹出框中输入。
            </div>
        </center>



        <div style="position: relative;margin-top: 20px;margin-bottom: 10px;">
            <a href="https://admin.juunew.com/game_download">
                <img src="/assets/img/down.png" style="margin-top:0px;width:43%;margin-left:28.5%;" class="img-responsive"/>
            </a>
        </div>

        <div style="margin-top: 0px;margin-bottom:40px;font-size:12px;text-align: center;width: 100%;">
            若您还未下载游戏，请先点击下载游戏按钮
        </div>
    </div>
</body>
<script type="text/javascript">
    var targetText = $("#target").text();
    var clipboard = new Clipboard('#copy_btn');
    clipboard.on('success', function (e) {
        console.info('Action:', e.action);
        console.info('Text:', e.text);
        console.info('Trigger:', e.trigger);

        alert("复制成功，请在游戏内进行绑定。");

        e.clearSelection();
    });

    //获取地址栏参数
    function UrlSearch() {
        var name, value;
        var str = location.href; //取得整个地址栏

        var num = str.indexOf("?")
        str = str.substr(num + 1); //取得所有参数   stringvar.substr(start [, length ]

        var arr = str.split("&"); //各个参数放到数组里
        for (var i = 0; i < arr.length; i++) {
            num = arr[i].indexOf("=");
            if (num > 0) {
                name = arr[i].substring(0, num);
                value = arr[i].substr(num + 1);
                this[name] = value;
                if(name == "inviteCode"){
                    $("#target").text(value);
                }
            }
        }
    }
    var Request = new UrlSearch(); //实例化
</script>
</html>
