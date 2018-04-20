<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>聚牛网络|后台总览</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <link rel="stylesheet" href="/assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="/assets/scripts/jquery-1.9.1.min.js"></script>
    <script src="/assets/vendor/bootstrap/js/bootstrap.js"></script>
</head>
<body>
    <div style="padding: 10px;">
        <input type="hidden" value="${limit}" id="y">

        <div class="row ">
            <div class="col-md-3 pull-right" style="margin-bottom: 15px;margin-right: 20px;">
                <select class="form-control" id="limit">
                    <option value="10" id="ten">每页显示10条</option>
                    <option value="20" id="fifteen">每页显示20条</option>
                    <option value="50" id="twenty">每页显示50条</option>
                    <option value="100" id="twentyFive">每页显示100条</option>
                </select>
            </div>
        </div>

        <div style="margin-left: 20px;margin-right: 20px;">
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-hover">
                        <tr class="active">
                            <th>玩家ID</th>
                            <th>玩家昵称</th>
                            <th>后台权限</th>
                            <th>最近登录</th>
                            <th>密码修改</th>
                            <th>操作</th>
                        </tr>
                        <c:forEach items="${allRoleUser}" var="zhi">
                            <tr>
                                <td>${zhi.users_id}</td>
                                <td>${zhi.nickname}</td>
                                <td>
                                    <c:if test="${zhi.role_id==1}">
                                        <span>高级管理员</span>
                                    </c:if>
                                    <c:if test="${zhi.role_id==2}">
                                        <span>管理员</span>
                                    </c:if>
                                    <c:if test="${zhi.role_id==3}">
                                        <span>运营</span>
                                    </c:if>
                                    <c:if test="${zhi.role_id==4}">
                                        <span>财务</span>
                                    </c:if>
                                    <c:if test="${zhi.role_id==5}">
                                        <span>代理</span>
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${zhi.login_time=='未登录过'}">
                                        <span>未登录过</span>
                                    </c:if>
                                    <c:if test="${zhi.login_time!='未登录过'}">
                                        <fmt:parseDate value="${zhi.login_time}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                                        <fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>
                                    </c:if>
                                </td>
                                <td>
                                    <a href="javascript:;" class="btn btn-primary change"  data-toggle="modal" alt="${zhi.users_id}" data-target="#myModal">修改密码</a>
                                </td>
                                <td>
                                    <c:if test="${zhi.users_id!=user_id}">
                                        <input type="button" class="btn btn-danger cancelRole" alt="${zhi.users_id}" id="${zhi.nickname}" value="取消权限"/>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>


            <!-- Modal -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document"  style="width: 400px;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">密码修改</h4>
                        </div>
                        <div class="modal-body">
                            <div style="padding-left: 40px;padding-right: 40px;padding: 20px;">
                                <div class="form-group">
                                    <label>新密码：</label><br>
                                    <input type="password" placeholder="请输入新密码..." class="form-control" id="password" maxlength="32" />
                                </div>
                                <div class="form-group">
                                    <label>确认密码：</label><br>
                                    <input type="text" class="form-control" id="confirPassword" maxlength="32" placeholder="再次确认..." />
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input type="button" class="btn btn-primary" id="changePassword" value="保存"/>
                            <input type="button" class="btn btn-default" data-dismiss="modal" value="关闭"/>
                        </div>
                    </div>
                </div>
            </div>


            <div class="col-md-4 col-md-offset-8" style="margin-top: 50px;margin-bottom: 30px;">
                <table>
                    <tr>
                        <td>
                            <a href="javaScript:mofen(1)"><img src="bs/images/img_firstpage.gif" width="16" height="16" /></a>&nbsp;
                            <a href="javaScript:mofen(${page-1 })"><img src="bs/images/img_prevpage.gif" width="11" height="16" /></a>&nbsp;${page }/${total }&nbsp;
                        </td>
                        <td align="right">
                            <a href="javaScript:mofen(${page+1 })"><img src="bs/images/img_nextpage.gif" width="12" height="16" /></a>&nbsp;
                            <a href="javaScript:mofen(${total })"><img src="bs/images/img_lastpage.gif" width="16" height="16" /></a>
                            <input type="hidden" value="${total}" id="hidd"/>
                        </td>
                        <td>&nbsp;&nbsp;&nbsp;</td>
                        <td>共 ${count} 条记录</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</body>
<script>
    $(function () {
        var id;
        $(".change").click(function () {
            id = $(this).attr("alt");
        });
        $("#changePassword").click(function () {
            var password = $("#password").val();
            var confirPassword = $("#confirPassword").val();

            if(id==""){
                alert("ID无法获取，修改失败！");
            }if(password==""){
                alert("密码不能为空！");
            }else if(password != confirPassword){
                alert("两次输入的密码不一致，请重新输入！");
            }else{
                $.ajax({
                    type:"post",
                    url:"/changePassword",
                    data:{"id":id,"password":password},
                    success:function(msg) {
                        if(msg=="1"){
                            alert("修改成功！");
                        }else{
                            alert("修改失败！");
                        }
                        window.location.href="/total_preview?page=1&limit=20";
                    }
                });
            }
        });


        $(".cancelRole").click(function () {
            var user_id = $(this).attr("alt");
            var nickname = $(this).attr("id");

            var r=confirm("是否确定取消 \""+nickname+"\" 的后台操作权限?");
            if (r==true){
                window.location.href="/cancel_role?id="+user_id;
            }else{
                return false;
            }
        });



        $("#limit").change(function () {
            var limit = $(this).val();
            var userId = $("#userId").val();
            window.location.href="/total_preview?page=1&limit="+limit;
        });

        var y = $("#y").val();
        if(y == 20){
            $("#fifteen").attr("selected","true");
            $("#ten").removeAttr("selected");
            $("#twentyFive").removeAttr("selected");
            $("#twenty").removeAttr("selected");
        }else if(y == 50){
            $("#twenty").attr("selected","true");
            $("#fifteen").removeAttr("selected");
            $("#ten").removeAttr("selected");
            $("#twentyFive").removeAttr("selected");
        }else if(y == 100){
            $("#twentyFive").attr("selected","true");
            $("#twenty").removeAttr("selected");
            $("#fifteen").removeAttr("selected");
            $("#ten").removeAttr("selected");
        }else{
            $("#ten").attr("selected","true");
            $("#fifteen").removeAttr("selected");
            $("#twenty").removeAttr("selected");
            $("#twentyFive").removeAttr("selected");
        }
    });



    function mofen(page){
        var total = document.getElementById("hidd").value;
        var limit = document.getElementById("y").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/total_preview?page="+page+"&limit="+limit;
    }
</script>
</html>