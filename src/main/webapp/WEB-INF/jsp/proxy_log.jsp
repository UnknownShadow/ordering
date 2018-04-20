<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <title>潮尚玩|运营-玩家日志查询</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">

</head>
<style> a:hover{text-decoration: none;} </style>
<body>

    <ul class="layui-nav" lay-filter="">
        <li class="layui-nav-item"><a href="/proxy_overview">代理总览</a></li>
        <li class="layui-nav-item"><a href="/proxy_manage">添加删除代理</a></li>
        <li class="layui-nav-item"><a href="/intent_proxy?page=1">意向代理</a></li>
        <li class="layui-nav-item">
            <a href="/integral_withdrawals_operation?page=1&limit=20">积分提现审核</a>
        </li>
        <li class="layui-nav-item">
            <a href="/proxy_integral_record?page=1&limit=20&user_id=">代理总积分管理</a>
        </li>
        <li class="layui-nav-item layui-this">
            <a href="/proxy_log?page=1&limit=20&user_id=">代理日志查询</a>
        </li>
        <li class="layui-nav-item">
            <a href="/club_management?page=1&limit=20&queryId=&queryStatus=1">俱乐部管理</a>
        </li>
    </ul>

    <div style="padding: 20px;">
        <div class="row">
            <div class="col-md-6">
                <form action="#" method="post" class="navbar-form navbar-left" name="formSer">
                    <div class="input-group">
                        <input type="text" id="userId" class="form-control searchbox" placeholder="请输入用户ID..." maxlength="8" value="${user_id}" >
                        <span class="input-group-btn"><input type="button" id="findLog" class="btn btn-primary" value="查询"></span>
                    </div>
                </form>
            </div>
        </div>

        <!-- 最近登录：游戏最近登录-->
        <table class="table table-hover">
            <tr class="active success">
                <th>昵称</th>
                <th>ID</th>
                <th>钻石数量</th>
                <th>积分数量</th>
                <th>手机号码</th>
                <th>上级代理</th>
                <th>子代理数</th>
                <th>最近登录游戏</th>
            </tr>
            <tr>
                <td>${userInfo.nickname}</td>
                <td>${userInfo.users_id}</td>
                <td>${userInfo.diamond}</td>
                <td>${userInfo.integral}</td>
                <td>${userInfo.phone}</td>
                <c:choose>
                    <c:when test="${userInfo.fatherproxy_id != 0 && userInfo.fatherproxy_id != 1 && userInfo.fatherproxy_id != null}">
                        <td>${userInfo.fatherproxy_nickname}（${userInfo.fatherproxy_id}）</td>
                    </c:when>
                    <c:when test="${userInfo.fatherproxy_id == 1}">
                        <td>系统代理</td>
                    </c:when>
                    <c:otherwise>
                        <td></td>
                    </c:otherwise>
                </c:choose>
                <td>${userInfo.childproxytotal}</td>
                <td>
                    <fmt:parseDate value="${userInfo.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                    <fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>
                </td>
            </tr>
        </table>
        <br>

        <input type="hidden" value="${limit}" id="y">
        <div class="row ">
            <div class="col-md-3 pull-right" style="margin-bottom: 15px;">
                <select class="form-control" id="limit">
                    <option value="10" id="ten">每页显示10条</option>
                    <option value="20" id="fifteen">每页显示20条</option>
                    <option value="50" id="twenty">每页显示50条</option>
                    <option value="100" id="twentyFive">每页显示100条</option>
                </select>
            </div>
        </div>

        <div class="table-responsive">
            <table class="table table-hover">
                <tr class="active success">
                    <th>发钻者昵称（ID）</th>
                    <th>收钻者昵称（ID）</th>
                    <th>钻石数</th>
                    <th>发钻时间</th>
                </tr>
                <c:forEach items="${sendDiamondsEntities}" var="zhi" >
                    <tr>
                        <td>${zhi.send_nickname}（${zhi.send_user_id}）</td>
                        <td>${zhi.receive_nickname}（${zhi.receive_user_id}）</td>
                        <td>${zhi.diamond}</td>
                        <td>
                            <fmt:parseDate value="${zhi.created_time}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                            <fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="col-md-4 col-md-offset-8" style="margin-top: 50px;">
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

</body>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/assets/layer/common/layui/layui.js"></script>
<script type="text/javascript">
    layui.use(['jquery','layer','element','laypage'],function(){
        window.jQuery = window.$ = layui.jquery;
        window.layer = layui.layer;
    });

    $(function () {

        //按回车键操作
        $("#userId").keydown(function (event) {
            var keyCode = event.keyCode;
            if(keyCode == 13){
                $("#findLog").click();
            }
        });
        $("#findLog").click(function () {
            var user_id = $("#userId").val();
            var limit = $("#y").val();
            var reg = /^[0-9]*$/;

            if(!reg.test(user_id)){
                alert("请输入正确的ID");
                $("#userId").val("");
            }else{
               formSer.action="/proxy_log?page=1&user_id="+user_id+"&limit="+limit;
               formSer.submit();
            }
        });

        $("#limit").change(function () {
            var limit = $(this).val();
            var userId = $("#userId").val();
            window.location.href="/proxy_log?page=1&user_id="+userId+"&limit="+limit;
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
        var user_id = document.getElementById("userId").value;
        var limit = document.getElementById("y").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/proxy_log?page="+page+"&user_id="+user_id+"&limit="+limit;
    }
</script>
</html>
