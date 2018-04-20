<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>潮尚玩|钻石发送</title>
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
    .searchbox{ width: 40%; }
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
            var reg=/^[0-9]*$/;

            if(!reg.test(user_id)){
                alert("请正确输入ID号！");
            }else if(user_id!=""){
                formSer.action="/finduser?user_id="+user_id+"&status=1";
                formSer.submit();
            }else{
                alert("请输入用户ID！");
            }
        });
    });
</script>

<body>
    <div style="padding: 40px;">
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
                                <td>
                                    <c:if test="${user.user_status==2}">代理</c:if>
                                    <c:if test="${user.user_status==3}">玩家</c:if>
                                    <c:if test="${user.user_status==1}">总代理</c:if>
                                    <c:if test="${user.user_status==4}">合伙人</c:if>
                                    <c:if test="${user.user_status==6}">至尊2</c:if>
                                    <c:if test="${user.user_status==7}">至尊1</c:if>
                                    <c:if test="${user.user_status==8}">极品至尊</c:if>
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
                        <label>钻石数量：</label><br>
                        <span><input type="text" class="form-control"  id="diamond" maxlength="8" placeholder="请输入钻石数..." onkeyup="var reg = /^[0-9]*$/; if(!reg.test(this.value)) this.value='';" style="width: 200px;"/></span>*需要通过财务审核后才能到账<br><br>
                        <label>金额(RMB)：</label><br>
                        <span><input type="text" class="form-control"  id="RMB" style="width: 200px;" maxlength="8" placeholder="请输入金额值..." onkeyup="var regs=/^[0-9]*$/; if(!regs.test(this.value)){this.value = ''}"/><br>

                         <label>付款状态：</label><br>
                         <select id="pay_status" class="form-control" style="width: 200px;">
                            <option value="1">默认</option>
                            <option value="2">扶持计划</option>
                         </select>
                            <br><br>
                    <%--<label>金币数加：</label><br>
                        <span><input type="text" class="form-control" id="money" style="width:200px;"/>*超过20000需通过财务审核</span><br>--%>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <input type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" id="preview" value="预览">
                        </div>
                        <script>
                            $(function () {
                                $("#preview").click(function () {
                                    $("#diamond1").val($("#diamond").val());
                                    $("#Id").val($("#hide").val());
                                    $("#RMB1").val($("#RMB").val());
                                    var pay_status = $("#pay_status").val();

                                    if(pay_status=="1"){
                                        $("#one").attr("selected","true");
                                        $("#two").removeAttr("selected");
                                    }else if(pay_status=="2"){
                                        $("#two").attr("selected","true");
                                        $("#one").removeAttr("selected");
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
                                    <input type="text" class="form-control"  id="Id" style="width: 200px;"/>
                                </div>

                                <div class="form-group">
                                    <label>钻石数加：</label><br>
                                    <span><input type="text" class="form-control"  id="diamond1" style="width: 200px;"/></span>*超过200需通过财务审核<br><br>
                                    <label>金额(RMB)：</label><br>
                                    <span><input type="text" class="form-control"  id="RMB1" style="width: 200px;" maxlength="8" placeholder="请输入金额值..." /><br>

                                    <label>付款状态：</label><br>
                                    <select class="form-control" style="width: 200px;">
                                        <option id="one" >默认</option>
                                        <option id="two" >扶持计划</option>
                                    </select><br>
                                    <%--<label>金币数加：</label><br>
                                    <span><input type="text" class="form-control" id="money1" style="width:200px;"/>*超过20000需通过财务审核</span><br>--%>
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
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</body>
<script>
    $(function(){

        $("#userid").focus(function () {
            $("#errors").css("display","none");
            $("#hh").css("display","none");

        });

        var id = $("#hide").val();
        $("#btn").click(function () {
            var RMB = $("#RMB").val();
            if(id==""){
                alert("请先查询！！！");
            }else if(RMB==""){
                alert("请输入金额数");
            }else{
                var diamond = $("#diamond").val();
                var nickname = $("#nickname").text();
                var pay_status = $("#pay_status").val();

                $.ajax({
                    type:"post",
                    url:"/save",
                    data:{"id":id,"diamond":diamond,"nickname":nickname,"RMB":RMB,"pay_status":pay_status},
                    success:function(msg) {

                        if(msg==diamond){
                            alert("本次向玩家"+nickname+"发送钻石"+msg+"个，财务审核通过后对方即可收到。");
                        }else{
                            alert("发送失败！");
                        }
                        window.location.href="/finduser?user_id="+id+"&status=1";
                    }
                });
            }
        });

    });
</script>
</html>

