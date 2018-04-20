<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>运营后台登录</title>
    <link href="/bs/css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <script src="/bs/js/jquery-1.11.2.min.js"></script>
    <script src="/bs/js/gVerify.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <!-- -->
    <script>var __links = document.querySelectorAll('a');
    function __linkClick(e) {
        parent.window.postMessage(this.href, '*');
    };for (var i = 0, l = __links.length; i < l; i++) {
        if (__links[i].getAttribute('data-t') == '_blank') {
            __links[i].addEventListener('click', __linkClick, false);
        }
    }</script>
    <script>
        $(document).ready(function (c) {
            $('.alert-close').on('click', function (c) {
                $('.message').fadeOut('slow', function (c) {
                    $('.message').remove();
                });
            });
        });
    </script>
</head>
<body>
<!-- contact-form -->
<div class="message warning">
    <div class="inset">
        <div class="login-head">
            <h1>后台管理登陆</h1>
            <%--<div class="alert-close"></div>--%>
        </div>
        <form action="#" method="post" name="form1" onsubmit="return false;">
            <li>
                <input type="text" class="text" name="username" id="name" placeholder="用户名" value="">
            </li>
            <div class="clear"></div>
            <li>
                <input type="password" name="password" id="pass" placeholder="密码">
            </li>
            <div id="v_container" style="width: 200px;height: 50px;"></div>
            <li>
                <input type="text" id="code_input" placeholder="请输入验证码" maxlength="4"/>
            </li>
            <input type="hidden" value="${error}" id="hide"/>
            <input type="hidden" value="${username}" id="usern"/>
            <div class="error"></div>
            <div class="submit">
                <input type="submit" value="登陆" id="my_button">
                <div class="clear"></div>
            </div>
        </form>
    </div>
</div>
</div>
</body>
<script>

    $(function () {
        var verifyCode = new GVerify("v_container");

        $("#my_button").click(function () {
            var res = verifyCode.validate(document.getElementById("code_input").value);
            if (res===true) {
                form1.action = "/login";
                form1.submit();
            } else {
                alert("验证码错误");
                return;
            }
        });

        $(".error").append($("#hide").val());
        $("#name").focus(function () {
            $(".error").text("");
        });
        $("#pass").focus(function () {
            $(".error").text("");
        });

        $("#name").val($("#usern").val());
       /* $("#name").focus(function () {
            $(".clear").text("");
        });*/
    });

</script>
</html>