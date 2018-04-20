<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %><html lang="en">
<meta name="format-detection" content="telephone=no">
<head>
    <title>俱乐部邀请</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <link rel="stylesheet" href="/assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="/assets/scripts/jquery-1.9.1.min.js"></script>
    <script src="/assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</head>
<style>
    body{ background-color: #14042b; }
</style>
<script>
    $(function () {
        var clubCode = $("#clubCode").val();
        var inviteNickname = $("#inviteNickname").val();
        var beInvitedUserId = $("#beInvitedUserId").val();
        var token = $("#token").val();

        //status: -1:不是游戏玩家，9:是游戏玩家但不是俱乐部成员，0：申请加入（审核中）1. 已成会员
        var status = $("#status").val();

        $("#joinClub").click(function () {
            if(status == 9){    //可以申请 加入俱乐部 状态
                $.ajax({
                    type: "post",
                    url: "/join_club",
                    data:{"token":token,"code":clubCode},
                    success: function(msg){
                        if(msg=="0"){
                            alert("恭喜，您的申请发送成功！请告知群主立即审批。");
                            window.location.href="/user/api/club_invite?clubCode="+clubCode;
                        }else{
                            alert("加入俱乐部失败！");
                        }
                    }
                });

            }else if(status == 0){
                alert("温馨提示：您已申请！请告知群主立即审批。");
            }else if(status == 1){
                alert("温馨提示：您已经是当前俱乐部成员，无须再次申请。");
            }else{
                var r=confirm("温馨提示：对不起，您还没有下载游戏，请先下载游戏。");
                if (r==true){
                    window.location.href="/game_download";
                }else{
                    return false;
                }
            }
        });
    });
</script>
<body>
    <div style="width:100%;position: relative;">
        <div style="width: 1%;height: 100px;position: absolute;left: 0px;"></div>
        <div style="width: 98%;height: 500px;position: absolute;left: 1%;">
            <input type="hidden" value="${clubCode}" id="clubCode" />
            <input type="hidden" value="${inviteNickname}" id="inviteNickname" />
            <input type="hidden" value="${beInvitedUserId}" id="beInvitedUserId" />
            <input type="hidden" value="${status}" id="status" />
            <input type="hidden" value="${token}" id="token" />

            <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180329114007" width="100%">
            
            <center>

                <div style="margin-top:-62%;margin-bottom:10px;text-align: center;">
                    <p style="color: #ceccfe;font-size: 19px;line-height: 35px;margin-top: -100px;">
                        【<span style="color: #dcc729;">${inviteNickname}</span>】 <br>
                        邀请您加入俱乐部 <br>
                        【<span style="color: #dcc729;">${clubName}</span>】 <br>
                        俱乐部ID：<span style="color: #dcc729;">${clubCode}</span> <br>
                    </p>

                    <div style="margin-top: 20px;margin-bottom: 10px;">
                        <a href="#" id="joinClub" style="text-decoration:none;border-radius:5px;padding: 10px;padding-right: 50px;padding-left: 50px;background-color:#dcc729;color:#494782;">加入俱乐部</a>
                        <%--<img class="img-btn1" id="down-break-ios" src="http://yxx.ufile.ucloud.com.cn/dev/reward/20171104100747" width="70%" alt="下载btn">--%>
                    </div>
                </div>
            </center>
            <br>
            
        </div>
        <div style="width: 1%;height: 100px;position: absolute;right: 0px;"></div>
    </div>
</body>
</html>
