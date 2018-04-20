<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>聚牛网络|玩家日志查询</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
</head>
<style>a:hover{text-decoration: none;}</style>
<body>

    <ul class="layui-nav" lay-filter="">
        <li class="layui-nav-item">
            <a href="/paid_records">付费玩家记录</a>
        </li>

        <li class="layui-nav-item"><a href="/integral_manage">玩家积分管理</a></li>

        <li class="layui-nav-item">
            <a href="/status_editor?page=1&limit=20">消息管理</a>
        </li>
        <li class="layui-nav-item">
            <a href="/competition_list?page=1&limit=20&proxy_date=&proxy_date_end=">比赛管理</a>
        </li>
        <li class="layui-nav-item">
            <a href="/reward_pending?page=1&start_date=&end_date=&user_id=">比赛发奖管理</a>
        </li>
        <li class="layui-nav-item layui-this"><a href="/log_output?page=1&limit=20&user_id=">玩家日志查询</a></li>
        <li class="layui-nav-item"><a href="/version_history?page=1">版本管理</a></li>
    </ul>

    <div style="padding: 20px;">
        <div class="row">
            <div class="col-md-6">
                <form action="#" method="post" class="navbar-form navbar-left" name="formSer">
                    <div class="input-group">
                        <input type="text" id="userId" class="form-control searchbox" placeholder="请输入用户ID..." maxlength="20" value="${user_id}" >
                        <span class="input-group-btn"><input type="button" id="findLog" class="btn btn-primary" value="查询"></span>
                    </div>

                    <%--<div class="input-group">
                        <input type="text" id="pn" class="form-control searchbox" placeholder="请输入手机号或昵称..." value="${user_id}" >
                        <span class="input-group-btn"><input type="button" id="fin" class="btn btn-primary" value="查询"></span>
                    </div>--%>
                </form>
            </div>
        </div>



        <table class="table table-hover">
            <tr class="active success">
                <th>昵称</th>
                <th>当前钻石</th>
                <th>手机号</th>
                <th>积分</th>
                <th>上级代理</th>
                <th>子代理数</th>
                <th>最近登录游戏</th>
            </tr>
            <tr>
                <td>${user.nickname}</td>
                <td>${user.diamond}</td>
                <td>${user.phone}</td>
                <td>${user.integral}</td>
                <c:choose>
                    <c:when test="${user.fatherproxy_id != 0 && user.fatherproxy_id != 1 && user.fatherproxy_id != null}">
                        <td>${user.fatherproxy_nickname}（${user.fatherproxy_id}）</td>
                    </c:when>
                    <c:when test="${user.fatherproxy_id == 1}">
                        <td>系统代理</td>
                    </c:when>
                    <c:otherwise>
                        <td></td>
                    </c:otherwise>
                </c:choose>
                <td>${user.childproxytotal}</td>
                <td>
                    <fmt:parseDate value="${user.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
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
                    <th>用户ID</th>
                    <th>时间</th>
                    <th>事件</th>
                    <th>内容</th>
                    <th>变化前的钻石</th>
                    <th>变化后的钻石</th>
                </tr>
                <c:forEach items="${logFiles}" var="zhi" >
                    <tr>
                        <td>${zhi.user_id}</td>
                        <td>
                            <fmt:parseDate value="${zhi.created_at}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                            <fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>
                        </td>
                        <td>${zhi.purpose}</td>
                        <td>${zhi.change_val} 钻石</td>
                        <td>${zhi.old_val}</td>
                        <td>${zhi.new_val}</td>
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

           /* if(!reg.test(user_id)){
                alert("请输入正确的ID");
                $("#userId").val("");
            }else{*/
                formSer.action="/log_output?page=1&user_id="+user_id+"&limit="+limit;
                formSer.submit();
//            }

        });

        //根据手机号或昵称查询
       /* $("#fin").click(function () {
            var pn = $("#pn").val();
            var user_id = $("#userId").val();
            var limit = $("#y").val();
            var reg = /^[0-9]*$/;

            if(!reg.test(user_id)){
                alert("请输入正确的ID");
                $("#userId").val("");
            }else {
                window.location.href = "/log_output?page=1&user_id=" + user_id + "&limit=" + limit+"&pn="+pn;
            }
        });*/


        $("#limit").change(function () {
            var limit = $(this).val();
            var userId = $("#userId").val();
            window.location.href="/log_output?page=1&user_id="+userId+"&limit="+limit;
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
        window.location.href="/log_output?page="+page+"&user_id="+user_id+"&limit="+limit;
    }

</script>
</html>
