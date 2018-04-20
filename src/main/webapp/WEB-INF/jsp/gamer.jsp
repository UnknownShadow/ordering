<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>潮尚玩|后台权限管理</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="bs/js/jquery-1.11.2.min.js"></script>
</head>

<style>
    .tb{
        width:80%;
        margin-top: 30px;
    }
    .searchbox{
        width: 40%;
    }
</style>
<body>
    <div style="padding:30px;">
        <div>
            <div class="row">
                <div class="col-md-6">
                    <form action="#" method="post" class="navbar-form navbar-left" name="formSer">
                        <div class="input-group">
                            <input type="text" id="userid" class="form-control searchbox" placeholder="请输入用户ID..." maxlength="8">
                            <span class="input-group-btn"><input type="button" id="sear" class="btn btn-primary" value="查询"></span>
                        </div>
                    </form>
                </div>
            </div>

            <div class="row" >
                <div class="col-md-12">
                    <table class="table table-hover">
                        <tr class="active">
                            <th>玩家昵称</th>
                            <th>玩家ID</th>
                            <th>用户身份</th>
                            <th>后台权限</th>
                        </tr>
                            <tr id="ttr">
                                <td id="nickname">${user.nickname}</td>
                                <td>${user.users_id}</td>
                                <td>
                                    <c:if test="${user.user_status==8}"> 极品至尊 </c:if>
                                    <c:if test="${user.user_status==7}"> 至尊1 </c:if>
                                    <c:if test="${user.user_status==6}"> 至尊2 </c:if>
                                    <c:if test="${user.user_status==5}"> 至尊3 </c:if>
                                    <c:if test="${user.user_status==4}"> 合伙人 </c:if>
                                    <c:if test="${user.user_status==1}"> 总代理 </c:if>
                                    <c:if test="${user.user_status==2}"> 代理 </c:if>
                                    <c:if test="${user.user_status==3}"> 玩家 </c:if>
                                </td>
                                <td>${user.perm}</td>
                                <input type="hidden" value="${user.users_id}" id="hide">
                                <input type="hidden" value="${user.perm}" id="user_status">
                                <input type="hidden" value="${user.nickname}" id="nick">
                            </tr>
                    </table>
                    <div id="errors" style="">${err }</div>
                </div>
            </div>
            <div style="margin-top:16px;width: 400px">
                <form action="#" method="post">

                    <div class="form-group">
                        <label>后台管理权限：</label><br>
                        <select id="role" class="form-control" style="width: 180px;">
                            <option value="6" id="six">无</option>
                            <option value="1" id="one">高级管理员</option>
                            <option value="2" id="two">管理员</option>
                            <option value="3" id="three">运营</option>
                            <option value="4" id="four">财务</option>
                            <option value="5" id="five">代理</option>
                            <option value="8" id="customer">客服</option>
                        </select><br>
                    </div>


                    <div class="row">
                        <div class="col-md-6">
                            <input type="button" value="保存" class="btn btn-danger" id="btn"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</body>
<script>
    $(function(){

        //按回车键操作
        $("#userid").keydown(function (event) {
            var keyCode = event.keyCode;
            if(keyCode == 13){
                $("#sear").click();
            }
        });

        $("#sear").click(function(){

            var user_id = $("#userid").val();
            var reg=/^[0-9]*$/;

            if(!reg.test(user_id)){
                alert("请正确输入ID号！");
            }else if(user_id!=""){
                formSer.action="/gamerFinduser?user_id="+user_id+"&status=2";
                formSer.submit();
            }else{
                alert("请输入用户ID！");
            }
        });

        var user_status = $("#user_status").val();
        if(user_status=="高级管理员"){
            $("#one").attr("selected","true");
            $("#two").removeAttr("selected");
            $("#three").removeAttr("selected");
            $("#four").removeAttr("selected");
            $("#five").removeAttr("selected");
            $("#six").removeAttr("selected");
            $("#customer").removeAttr("selected");
        }else if(user_status=="管理员"){
            $("#two").attr("selected","true");
            $("#one").removeAttr("selected");
            $("#three").removeAttr("selected");
            $("#four").removeAttr("selected");
            $("#five").removeAttr("selected");
            $("#six").removeAttr("selected");
            $("#customer").removeAttr("selected");
        }else if(user_status=="运营"){
            $("#three").attr("selected","true");
            $("#two").removeAttr("selected");
            $("#one").removeAttr("selected");
            $("#four").removeAttr("selected");
            $("#five").removeAttr("selected");
            $("#six").removeAttr("selected");
            $("#customer").removeAttr("selected");
        }else if(user_status=="财务"){
            $("#four").attr("selected","true");
            $("#two").removeAttr("selected");
            $("#three").removeAttr("selected");
            $("#one").removeAttr("selected");
            $("#five").removeAttr("selected");
            $("#six").removeAttr("selected");
            $("#customer").removeAttr("selected");
        }else if(user_status=="代理"){
            $("#five").attr("selected","true");
            $("#two").removeAttr("selected");
            $("#three").removeAttr("selected");
            $("#four").removeAttr("selected");
            $("#one").removeAttr("selected");
            $("#six").removeAttr("selected");
            $("#customer").removeAttr("selected");
        }else if(user_status=="客服"){
            $("#customer").attr("selected","true");
            $("#two").removeAttr("selected");
            $("#three").removeAttr("selected");
            $("#four").removeAttr("selected");
            $("#five").removeAttr("selected");
            $("#one").removeAttr("selected");
            $("#six").removeAttr("selected");
        }else if(user_status=="无"){
            $("#six").attr("selected","true");
            $("#two").removeAttr("selected");
            $("#three").removeAttr("selected");
            $("#four").removeAttr("selected");
            $("#five").removeAttr("selected");
            $("#one").removeAttr("selected");
            $("#customer").removeAttr("selected");
        }



        $("#userid").focus(function () {
            $("#errors").css("display","none");
        });

        role=$("#role").val();

        $("#role").change(function(){
            role = $("#role").val();
        });

        var id = $("#hide").val();
        var nickname = $("#nick").val();
        $("#btn").click(function () {
            if(id==""){
                alert("请先查询！！！");
            }else{

                $.ajax({
                    type:"post",
                    url:"/change_role",
                    data:{"id":id,"role":role},
                    success:function(msg) {

                        if(msg=="1"){
                            alert("成功将玩家"+nickname+"改为：高级管理员");
                        }else if(msg=="2"){
                            alert("成功将玩家"+nickname+"改为：管理员");
                        }else if(msg=="3"){
                            alert("成功将玩家"+nickname+"改为：运营");
                        }else if(msg=="4"){
                            alert("成功将玩家"+nickname+"改为：财务");
                        }else if(msg=="5"){
                            alert("成功将玩家"+nickname+"改为：代理");
                        }else if(msg=="6"){
                            alert("成功将玩家"+nickname+"改为：玩家");
                        }else if(msg=="8"){
                            alert("成功将玩家"+nickname+"改为：客服");
                        }
                        window.location.href="/gamerFinduser?user_id="+id+"&status=2";
                    }
                });
            }
        });

    });
</script>
</html>

