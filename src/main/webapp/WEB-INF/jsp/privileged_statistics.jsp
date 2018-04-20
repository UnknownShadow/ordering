<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>潮尚玩|代理特权活动统计</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
    <div style="padding: 20px;margin-bottom: 200px;">
        <span style="font-size: 22px;font-weight: 700;">代理特权活动统计</span> <br><br>

        <div class="row">
            <div class="col-md-6">
                <form action="#" method="post" class="navbar-form navbar-left" name="formSer">
                    <div class="input-group">
                        <input type="text" id="userId" value="${userId}" class="form-control" placeholder="请输入用户ID..." maxlength="12">
                        <span class="input-group-btn"><input type="button" id="qurey" class="btn btn-primary" value="查询"></span>
                    </div>
                </form>
            </div>
        </div>

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


        <div class="table-responsive" style="margin-top: 15px;">
            <span style="font-weight: 600;font-size: 18px;">已激活人数：${activationNum} 人</span>
            <table class="table table-hover">
                <tr class="active">
                    <th>代理ID</th>
                    <th>昵称</th>
                    <th>激活码</th>
                    <th>特权码绑定时间</th>
                    <th>当前完成进度/需要完成进度</th>
                    <th>当前第几天</th>
                    <th>已获得活动积分</th>
                    <th>上级代理</th>
                    <th>详情</th>
                </tr>
                <c:forEach items="${agentPrivilegeResp}" var="zhi">
                    <tr>
                      <td>${zhi.userId}</td>
                      <td>${zhi.nickname}</td>
                      <td>${zhi.code}</td>
                      <td>${zhi.bindingTime}</td>
                      <td>${zhi.progress}/${zhi.task}</td>
                      <td>${zhi.today_progress}</td>
                      <td>${zhi.integral}</td>
                      <td>${zhi.superiorAgentNickname}（ID：${zhi.superiorAgentId}）</td>
                      <td>
                          <a href="javascript:;" class="btn btn-primary details" alt="${zhi.userId}">查看详情</a>
                      </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="col-md-4 col-md-offset-8" style="margin-top: 25px;">
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
<script>

    $(function () {

        //按回车键操作
        $("#userId").keydown(function (event) {
            var keyCode = event.keyCode;
            if(keyCode == 13){
                $("#qurey").click();
            }
        });
        $("#qurey").click(function () {
            var userId = $("#userId").val();
            var limit = $("#y").val();
            formSer.action="/privileged_statistics?page=1&limit="+limit+"&userId="+userId;
            formSer.submit();
        });


        $("#limit").change(function () {
            var limit = $(this).val();
            var userId = $("#userId").val();
            window.location.href="/privileged_statistics?page=1&limit="+limit+"&userId="+userId;
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


        $(".details").click(function () {
            var userId = $(this).attr("alt");
            window.location.href = "/privileged_details?userId="+userId;
        });
    });


    function mofen(page){
        var total = document.getElementById("hidd").value;
        var limit = document.getElementById("y").value;
        var userId = document.getElementById("userId").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/privileged_statistics?page="+page+"&limit="+limit+"&userId="+userId;
    }
</script>
</html>
