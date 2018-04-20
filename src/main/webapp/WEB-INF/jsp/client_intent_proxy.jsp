<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %><html lang="en">
<meta name="format-detection" content="telephone=no">
<head>
    <title>意向代理</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <!-- VENDOR CSS -->
    <link rel="stylesheet" href="/assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="/assets/scripts/jquery-1.9.1.min.js"></script>
    <script src="/assets/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="/assets/clipboard.js"></script>
</head>
<style>
    body{ background-color: #14042c; }
    .c{
        background-color: #220b45;
        border-radius: 10px;
        padding: 18px;
        padding-bottom: 30px;
        margin-bottom: 30px;
        color: white;
        font-size: 18px;
    }
</style>
<script>
    $(function () {
        var targetText=$("#target").text();
        var clipboard = new Clipboard('#copy_btn');
        clipboard.on('success', function(e) {
            console.info('Action:', e.action);
            console.info('Text:', e.text);
            console.info('Trigger:', e.trigger);

            e.clearSelection();
        });
    });
</script>
<body>
    <div style="width:100%;position: relative;">
        <div style="width: 0%;height: 100px;position: absolute;left: 0px;"></div>
        <div style="width: 100%;height: 500px;position: absolute;left: 0%;">

            <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20171201165056" width="100%"/>

            <div class="c">
                <center>
                    <p style="color: #ad85de;">
                        更多精彩活动，请联系我们贴心客服，在线为您详细解答
                    </p>

                    复制微信号，立即打开微信添加 <br>
                    客服微信：<span id="target" style="color: #ad85de;">csw-kefu</span>
                    <div style="text-align: center;margin-top:30px;">
                        <button class="btn btn-primary" data-toggle="modal" data-toggle="modal" data-target="#myModal" data-clipboard-action="copy" data-clipboard-target="#target" id="copy_btn" style="padding-left: 60px;padding-right:60px;padding-top:10px;padding-bottom:10px;
                        background-color: #7d4fdb;border-radius: 5px;color: white;border: 0px;">
                            复制
                        </button>
                    </div>
                </center>
            </div>

            <!-- Modal -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">操作成功</h4>
                        </div>
                        <div class="modal-body" style="">
                            <center>
                                复制成功，请打开微信粘贴后搜索！
                            </center>
                        </div>
                        <div class="modal-footer">
                            <center>
                                <button type="button" class="btn btn-default" data-dismiss="modal" style="padding-left: 50px;padding-right: 50px;">确定</button>
                            </center>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div style="width: 0%;height: 100px;position: absolute;right: 0px;"></div>
    </div>
</body>

<script>
    $(function () {
        $("#submt").click(function () {
            var PhoneReg = /^0{0,1}(13[0-9]|15[0-9]|153|156|18[7-9])[0-9]{8}$/;
            var name = $('#name').val();
            var phone =  $('#phone').val();
            name = name.replace(/(^\s*)|(\s*$)/g, "");

            if(name =='' || phone==''){
                alert('请将信息填写完整');
            }else if(!PhoneReg.test(phone)){
                alert('请输入正确的手机号！');
            }else{
                $.ajax({
                    type:'post',
                    url:'/saveIntentInfo',
                    data:{'name':name,'phone':phone},
                    success:function(msg) {
                        alert("提交成功！");
                        $('#name').val("");
                        $('#phone').val("");
                    }
                });
            }
        });
    });

</script>
</html>
