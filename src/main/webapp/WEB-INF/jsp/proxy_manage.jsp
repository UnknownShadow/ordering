<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>潮尚玩|添加代理</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
</head>
<style>
    a:hover{text-decoration: none;}
    .tb{
        width:80%;
        margin-top: 30px;
    }
    .searchbox{
        width: 40%;
    }
</style>
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
            var reg = /^[0-9]*$/;

            if (!reg.test(user_id)) {
                alert("请正确输入ID号！");
            } else if (user_id != "") {
                fromSer.action="/finduser?user_id=" + user_id + "&status=3";
                fromSer.submit();
            } else {
                alert("请输入用户ID！");
            }
        });
    });
</script>
<body>

    <ul class="layui-nav" lay-filter="">
        <li class="layui-nav-item"><a href="/proxy_overview">代理总览</a></li>
        <li class="layui-nav-item layui-this"><a href="/proxy_manage">添加删除代理</a></li>
        <li class="layui-nav-item"><a href="/intent_proxy?page=1">意向代理</a></li>
        <li class="layui-nav-item">
            <a href="/integral_withdrawals_operation?page=1&limit=20">积分提现审核</a>
        </li>
        <li class="layui-nav-item">
            <a href="/proxy_integral_record?page=1&limit=20&user_id=">代理总积分管理</a>
        </li>
        <li class="layui-nav-item">
            <a href="/proxy_log?page=1&limit=20&user_id=">代理日志查询</a>
        </li>
        <li class="layui-nav-item">
            <a href="/club_management?page=1&limit=20&queryId=&queryStatus=1">俱乐部管理</a>
        </li>
    </ul>

    <div style="padding: 20px;">
        <div style="margin-left: 100px;">
            <div class="row">
                <div class="col-md-6">
                    <form action="#" method="post" class="navbar-form navbar-left" name="fromSer">
                        <div class="input-group">
                            <input type="text" id="userid"  class="form-control searchbox" placeholder="请输入用户ID..." maxlength="8">
                            <span class="input-group-btn"><input type="submit" id="sear" class="btn btn-primary" value="查询" ></span>
                        </div>
                    </form>
                </div>
            </div>

            <div class="row tb">
                <div class="col-md-12">
                    <table class="table table-hover">
                        <tr class="active">
                            <th>玩家昵称</th>
                            <th>玩家ID</th>
                            <th>用户身份</th>
                            <th>后台权限</th>
                            <th>钻石数量</th>
                        </tr>
                            <tr id="ttr">
                                <td id="nickname">${user.nickname}</td>
                                <td>${user.users_id}</td>
                                <td id="card">
                                    <c:if test="${user.user_status==3}">玩家</c:if>
                                    <c:if test="${user.user_status==2}">代理</c:if>
                                    <c:if test="${user.user_status==1}">总代理</c:if>
                                    <c:if test="${user.user_status==4}">合伙人</c:if>
                                </td>
                                <td>${user.perm}</td>
                                <td>${user.diamond}</td>
                                <input type="hidden" value="${user.users_id}" id="hide">
                            </tr>
                            <input type="hidden" value="${user.user_status}" id="hidde"/>
                    </table>
                    <div id="errors" style="">${err }</div>
                </div>
            </div>
            <div style="margin-top:16px;width: 400px">
                <form action="#" method="post">
                    <div class="form-group">
                        <label>游戏用户身份：</label><br>
                        <select id="status" class="form-control" style="width: 180px;">
                            <option id="op3" value="3">玩家</option>
                            <option id="op2" value="2">代理</option>
                            <option id="op1" value="1">总代理</option>
                            <option id="op4" value="4">合伙人</option>
                        </select><br>
                    </div>

                    <div class="row">
                        <div class="col-md-6">
                            <input type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" id="preview" value="预览">
                        </div>
                        <script>
                            $(function () {
                                $("#preview").click(function () {
                                    $("#Id").val($("#hide").val());
                                    var stts="";
                                    if($("#hidde").val()==3){
                                        stts="玩家";
                                    }else if($("#hidde").val()==2){
                                        stts="代理";
                                    }else if($("#hidde").val()==1){
                                        stts="总代理";
                                    }else if($("#hidde").val()==4){
                                        stts="合伙人";
                                    }

                                    $("#startIDcard").val(stts);

                                    if($("#status").val()=="3"){
                                        $("#ops3").attr("selected","true");
                                        $("#ops2").removeAttr("selected");
                                        $("#ops1").removeAttr("selected");
                                        $("#ops4").removeAttr("selected");
                                    }else if($("#status").val()=="2"){
                                        $("#ops2").attr("selected","true");
                                        $("#ops3").removeAttr("selected");
                                        $("#ops1").removeAttr("selected");
                                        $("#ops4").removeAttr("selected");
                                    }else if($("#status").val()=="1"){
                                        $("#ops1").attr("selected","true");
                                        $("#ops3").removeAttr("selected");
                                        $("#ops2").removeAttr("selected");
                                        $("#ops4").removeAttr("selected");
                                    }else if($("#status").val()=="4"){
                                        $("#ops4").attr("selected","true");
                                        $("#ops3").removeAttr("selected");
                                        $("#ops1").removeAttr("selected");
                                        $("#ops2").removeAttr("selected");
                                    }
                                });
                            });
                        </script>
                    </div>
                </form>
            </div>
        </div>


        <%--预览--%>
        <!-- Modal -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document" style="width: 360px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">预览</h4>
                    </div>
                    <div class="modal-body">
                        <fieldset disabled>
                            <div style="margin-left: 20%;">
                                <div class="form-group">
                                    <label>玩家ID：</label><br>
                                    <input type="text" class="form-control"  id="Id" style="width: 200px;"/><br>
                                </div>
                                <div class="form-group">
                                    <label>游戏用户原始身份：</label><br>
                                    <input type="text" class="form-control"  id="startIDcard" style="width: 200px;"/><br>
                                </div>
                                <div class="form-group">
                                    <label>游戏用户调整后身份：</label><br>
                                    <select class="form-control" style="width: 180px;">
                                        <option id="ops3" value="3">玩家</option>
                                        <option id="ops2" value="2">代理</option>
                                        <option id="ops1" value="1">总代理</option>
                                        <option id="ops4" value="4">合伙人</option>
                                    </select><br>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                    <div class="modal-footer">
                        <input type="button" value="【确定】" class="btn btn-danger" id="btn"/>
                        <input type="button" class="btn btn-primary" data-dismiss="modal" value="【取消】"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/assets/layer/common/layui/layui.js"></script>
<script type="text/javascript">
    layui.use(['jquery','layer','element','laypage'],function(){
        window.jQuery = window.$ = layui.jquery;
        window.layer = layui.layer;
    });


    $(function(){

        var op = $("#hidde").val();
        if(op==3){
            $("#op3").attr("selected","true");
        }else if(op==2){
            $("#op2").attr("selected","true");
        }else if(op==1){
            $("#op1").attr("selected","true");
        }else if(op==4){
            $("#op4").attr("selected","true");
        }

        $("#userid").focus(function () {
            $("#errors").css("display","none");
            $("#hh").css("display","none");
        });


        var id = $("#hide").val();
        $("#btn").click(function () {
            if(id==""){
                alert("请先查询！！！");
            }else{
                var nickname = $("#nickname").text();
                var status=$("#status").val();

                $.ajax({
                    type:"post",
                    url:"/add_proxy",
                    data:{"id":id,"status":status},
                    success:function(msg) {

                        var user_status="";
                        if(msg=="2"){
                            user_status="代理";
                        }else if(msg=="3"){
                            user_status="玩家";
                        }else if(msg=="1"){
                            user_status="总代理";
                        }else if(msg=="4"){
                            user_status="合伙人";
                        }

                        alert("已成功将玩家 \""+nickname+"\" 身份调整为:"+user_status+"。");

                        window.location.href="/finduser?user_id="+id+"&status=3";
                    }
                });
            }
        });
    });
</script>
</html>

