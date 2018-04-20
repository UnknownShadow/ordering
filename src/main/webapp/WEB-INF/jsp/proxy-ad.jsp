<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>潮尚玩|添加子代理</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <!--修改密码样式-->
    <link rel="stylesheet" href="assets/css/common.css"/><!-- 基本样式 -->
    <link rel="stylesheet" href="assets/css/animate.css"/> <!-- 动画效果 -->
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <script src="assets/scripts/jquery.hDialog.js"></script>

    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>

    <script src="bs/js/bootstrap-paginator.min.js"></script>
</head>

<body>
    <div style="padding: 20px;">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <form action="#" method="post" class="navbar-form navbar-left">
                    <div class="input-group">
                        <input type="text" id="u_id" class="form-control searchbox" placeholder="请输入用户ID..." maxlength="8">
                        <span class="input-group-btn"><input type="button" id="sear" class="btn btn-primary" value="查询"></span>
                    </div>
                </form>
            </div>
        </div>
        <!--效果--->

        <label>玩家信息：</label>
        <div class="row tb">
            <div class="col-md-12 table-responsive">
                <table class="table table-hover">
                    <tr class="active">
                        <th>玩家昵称</th>
                        <th>玩家ID</th>
                        <th>用户身份</th>
                        <th>手机号</th>
                        <th>钻石数量</th>
                        <th>子代理数量</th>
                    </tr>
                    <tr>
                        <td>${user.nickname}</td>
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
                        <td>${user.childproxytotal}</td>
                        <input type="hidden" value="${user.users_id}" id="proxy_id"/>
                        <input type="hidden" value="${user.user_status}" id="user_status"/>
                        <input type="hidden" value="${user.phone}" id="phone"/>
                    </tr>
                </table>
            </div>
        </div>
        <div class="form-group">
            <input type="button" class="btn btn-danger" value="添加为子代理" id="bn"/>
        </div>
        <div class="col-md-6" id="err" style="margin-top: -80px;margin-left: 300px;"><h4><label>${err}</label></h4></div>
    </div>

<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</body>
<script>
    $(function () {
        var user_id=0;
        <!--玩家搜索查询-->
        $("#sear").click(function(){

            user_id = $("#u_id").val();
            var reg=/^[0-9]*$/;

            if(!reg.test(user_id)){
                alert("请正确输入ID号！");
            }else if(user_id!=""){
                window.location.href="/proxyAdfinduser?user_id="+user_id+"&status=1";
            }else{
                alert("请输入用户ID！");
            }
        });


        var id = $("#hide").val();
        var proxy_id = $("#proxy_id").val();


        $("#bn").click(function(){
            var phone = $("#phone").val();
            var user_status = $("#user_status").val();


            if(proxy_id==""){
                alert("请先查询用户");
            }else if(phone==""){
                alert("未绑定手机号，请先绑定手机号!");
            }else if(user_status!=3){
                alert("必须是玩家身份的用户才能够添加为子代理！");
            }else{
                $.ajax({
                    type:"post",
                    url:"/addproxy",
                    data:{"proxy_id":proxy_id,"status":2},
                    success:function(msg) {

                        if(msg==1){
                            alert("你已成功添加子代理！");
                            window.location.href="/proxyAdfinduser?user_id="+proxy_id+"&status=1";
                        }else if(msg==0){
                            alert("该用户已经成为代理！");
                        }
                    }
                });
            }
        });
    });
</script>
</html>
