<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改密码</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/bootstrap/css/bootstrap.css" media="all">
    <script src="/assets/scripts/jquery-1.9.1.min.js"></script>
    <script src="/assets/vendor/bootstrap/js/bootstrap.js"></script>
</head>
<body>
        <div style="padding: 50px;">
            <div class="form-group">
                <label>新密码：</label><br>
                <input type="password" placeholder="请输入新密码..." class="form-control" id="pass" maxlength="16" />
            </div>
            <div class="form-group">
                <label>确认密码：</label><br>
                <input type="text" class="form-control" id="confirmpass" maxlength="16" placeholder="再次确认..." />
            </div>

            <div class="form-group">
                <label>获取绑定的手机短信验证码：</label><br>
                <center>
                    <div class="input-group">
                        <%--<input type="text" class="form-control" id="phone"  maxlength="11">--%>
                        <input type="button" class="btn btn-primary" onclick="clock()" id="gets" value="获取验证码">
                    </div>
                </center>
            </div>
            <div class="form-group">
                <label>请输入短信验证码：</label><br>
                <input type="text" placeholder="请输入短信验证码" class="form-control" maxlength="4" id="verf"/>
            </div>
            <br>
            <center>
                <input type="button" class="btn btn-primary" id="submitBtn" style="padding-right: 80px;padding-left: 80px;" value="修改"/>
            </center>
        </div>
    <script>
        /*验证码的发送*/
        function GetRandomNum(Min,Max) {
            var Range = Max - Min;
            var Rand = Math.random();
            return(Min + Math.round(Rand * Range));
        }
        var i=60;
        var intv;
        function dd() {
            var val = $("#gets").val(""+(--i)+"s后重试");
            $("#gets").attr("disabled","true");
            if (i == 0) {
                window.clearInterval(intv);
                $("#gets").removeAttr("disabled","true");
                $("#gets").val("获取绑定的手机短信验证码");
                i = 60;
            }
        }
        var verify=0;
        function clock() {
            intv=self.setInterval("dd()",1000);
            verify=GetRandomNum(1234,9876);
            $.ajax({
                type:"post",
                url:"/smsVerification",
                data:{"code":verify},
                success:function(msg) {
                    if(msg==0){
                        alert("请先绑定手机号码");
                        return false;
                    }
                }
            });
        }
        /*验证码的发送*/
        $(function () {
            //提交并验证表单
            $('#submitBtn').click(function () {

                var verf = $("#verf").val();
                var confirmpass = $("#confirmpass").val();
                var pass = $("#pass").val();
                if (confirmpass == "" || pass == "") {
                    alert("密码为空,请输入密码！！！");
                } else if (confirmpass != pass) {
                    alert("两次密码不一致，请重新输入！！");
                } else if (verify != verf) {
                    alert("验证码错误！");
                } else if (verf == "") {
                    alert("请输入验证码");
                } else {
                    $.ajax({
                        type: "post",
                        url: "/modifyPassword",
                        data: {"pass": pass},
                        success: function (msg) {
                            if (msg == 1) {
                                alert("密码修改成功，请重新登陆，谢谢！！！");
                                window.location.href = "/loginout";
                            }else{
                                alert("修改失败！");
                            }
                        }
                    });
                }
            });
        });
    </script>
</body>
</html>


