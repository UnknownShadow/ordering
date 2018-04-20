<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>潮尚玩|代理</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <!--修改密码样式-->
    <link rel="stylesheet" href="assets/css/common.css"/><!-- 基本样式 -->
    <link rel="stylesheet" href="assets/css/animate.css"/> <!-- 动画效果 -->

    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>

</head>

<body>
    <div style="padding: 20px;">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <form action="#" method="post" class="navbar-form navbar-left">
                    <div class="input-group">
                        <input type="text" id="u_id" class="form-control searchbox" placeholder="请输入用户ID..." maxlength="8" >
                        <span class="input-group-btn"><input type="button" id="sear" class="btn btn-primary" value="查询"></span>
                    </div>
                </form>
            </div>
        </div>


        <!--效果--->
        <label>玩家信息：</label>
        <div class="row tb">
            <div class="col-md-12  table-responsive">
                <table class="table table-hover">
                    <tr class="active">
                        <th>玩家昵称</th>
                        <th>玩家ID</th>
                        <th>用户身份</th>
                        <th>手机号</th>
                        <th>钻石数量</th>
                    </tr>
                    <tr>
                        <td id="nickname">${user.nickname}</td>
                        <td>${user.users_id}</td>
                        <td>
                            <c:if test="${user.user_status==1}">至尊5</c:if>

                            <c:if test="${user.user_status==2}">代理</c:if>

                            <c:if test="${user.user_status==3}">玩家</c:if>

                            <c:if test="${user.user_status==4}">至尊4</c:if>

                            <c:if test="${user.user_status==5}">至尊3</c:if>

                            <c:if test="${user.user_status==6}">至尊2</c:if>

                            <c:if test="${user.user_status==7}">至尊1</c:if>

                            <c:if test="${user.user_status==8}">极品至尊</c:if>
                        </td>
                        <td>${user.phone}</td>
                        <td>${user.diamond}</td>
                    </tr>
                </table>
                <input type="hidden" value="${user.phone}" id="phone"/>
                <input type="hidden" value="${user.user_status}" id="user_status"/>
                <input type="hidden" value="${user.users_id}" id="proxy_id"/>

                <input type="hidden" value="${currentUser.diamond}" id="diam"/>
                <input type="hidden" value="${currentUser.users_id}" id="myId"/>
            </div>
        </div>


        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label>钻石数量：</label><br>
                    <span><input type="text" class="form-control"  id="diamond" style="width: 200px;" maxlength="8" placeholder="请输入钻石数量..." onkeyup="var reg=/^[0-9]*$/; if(!reg.test(this.value)) this.value = ''"/></span>*不能超过自已已有的钻石<label>(${currentUser.diamond})</label><br><br>
                </div>

                <input type="button" value="发送" class="btn btn-danger" id="bttn"/>
            </div>
            <div class="col-md-6" id="err" style="margin-top: 80px;margin-left: -140px;"><h4><label>${err}</label></h4></div>
        </div>
    </div>

<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</body>
<script>
$(function () {

        var proxy_id = $("#proxy_id").val();

        var status = "4";
        $("#status").change(function(){
           status = $("#status").val();
        });

        $("#u_id").focus(function () {
            $("#err").text("");
        });
<!--玩家搜索查询-->
    $(function(){

        $("#sear").click(function(){

            var user_id = $("#u_id").val();
            var reg=/^[0-9]*$/;

            if(!reg.test(user_id)){
                alert("请正确输入ID号！");
            }else if(user_id!=""){
                window.location.href="/sendsFinduser?user_id="+user_id+"&status=0";
            }else{
                alert("请输入用户ID！");
            }
        });


        $("#bttn").click(function(){

            var diam = $("#diam").val();
            var diamond = $("#diamond").val();
            var nickname=$("#nickname").text();
            var myId=$("#myId").val();

            var a = parseInt(diam);
            var b = parseInt(diamond);

            var proxy_id = $("#proxy_id").val();
            var user_status = $("#user_status").val();

            if(proxy_id==""){
                alert("请先查询!");
            }else if(diamond==""){
                alert("请输入钻石数后再点击修改！");
            }else if(b>a){
                alert("钻石数量不能超过自己已有的数量");
            }else if(b==0){
                alert("数量不可以为0");
            }else if(proxy_id == myId){
                alert("不可以给自己发送钻石");
            }else if(user_status!=3){
                alert("只能给玩家发送钻石！");
            }else{

                var  obj="您确定发送："+diamond+"个钻石给玩家 \""+nickname+"\" 吗?"+"\n"+"注意：发送后不可退回，请核对信息无误再点击确定按钮！";
                var r=confirm(obj);
                if (r==true){
                    $.ajax({
                         type:"post",
                         url:"/change",
                         async: false,
                         data:{"proxy_id":proxy_id,"diamond":diamond},
                         success:function(msg) {
                             var jsons=eval(msg);

                             $.each(jsons, function (k, v) {
                                 //window.location.href="/sends";

                                 if(v.flag==1){
                                     alert("钻石发送成功！");
                                     window.location.href="/sendsFinduser?user_id="+proxy_id+"&status=0";
                                 }else if(msg==0){
                                     alert("钻石或金币数量不足");
                                 }
                             });

                         }
                     });

                }else{
                    return false;
                }

            }

        });
    });
});
</script>
</html>
