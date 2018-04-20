<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %><html lang="en">
<meta name="format-detection" content="telephone=no">
<head>
    <title>客服</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <!-- VENDOR CSS -->
    <link rel="stylesheet" href="/assets/vendor/bootstrap/css/bootstrap.min.css">

    <script type="text/javascript">
        function _webCopy(text) {
            if(typeof Android === "object"){
                Android.webCopy(text);
            } else {
                webCopy(text);
            }
        }
    </script>
</head>
<style>
    body{ background-color: #17162c; }
    .c{
        background-color: #211f3c;
        border-radius: 10px;
        padding: 18px;
        margin-bottom: 20px;
    }
    .c1{
        background-color: #211f3c;
        border-radius: 10px;
        padding: 18px;
    }
    .fonts{
        color: #ceccfe;
        letter-spacing: 1px;
        font-size: 14px;
    }
</style>
<body>
    <div style="width:100%;position: relative;">
        <div style="width: 1%;height: 100px;position: absolute;left: 0px;"></div>
        <div style="width: 98%;position: absolute;left: 1%;">

            <div style="text-align: center;color: #edf4c5;margin-top: 12px;margin-bottom: 12px;"><b>联系客服</b></div>
            <div class="c1">
                <center>
                    <span class="fonts" style="line-height: 28px;">
                        有任何问题请添加微信客服：<br>
                        <span id="target">csw-kefu</span>
                        <a href="#" onclick="_webCopy(document.getElementById('target').innerText)" style="color:white;text-decoration:none;border:0px;background-color: #00a0f0;border-radius:6px;padding-top: 5px;padding-bottom: 3px;padding-right: 15px;padding-left: 18px;">
                            复制
                        </a> <br>
          
                    </span>
                </center>
            </div>
            <br>

            <div style="text-align: center;color: #edf4c5;margin-top: 12px;margin-bottom: 12px;"><b>游戏问题</b></div>
            <div class="c">
                <span class="fonts">
                    您可以直接在有问题的界面中摇晃手机，在对应界面直接与我们反馈问题。
                </span>
            </div>
        </div>
        <div style="width: 1%;height: 100px;position: absolute;right: 0px;"></div>
    </div>
</body>
</html>
