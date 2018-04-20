<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>潮尚玩|客服-消息列表</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
</head>
<style>a:hover{text-decoration: none;}</style>
<body>

    <div style="padding: 20px;">
        <input type="hidden" value="${limit}" id="y">

        <div class="row" style="margin-top: 20px;">
            <div class="col-md-3 pull-right" style="margin-bottom: 15px;">
                <select class="form-control" id="limit">
                    <option value="10" id="ten">每页显示10条</option>
                    <option value="20" id="fifteen">每页显示20条</option>
                    <option value="50" id="twenty">每页显示50条</option>
                    <option value="100" id="twentyFive">每页显示100条</option>
                </select>
                <a href="/message_system?status=1" style="margin-top: -35px;margin-left: -130px;" class="btn btn-primary">新增消息</a>
            </div>
        </div>

        <table class="table table-hover">
            <tr class="active">
                <th>编号</th>
                <th>标题</th>
                <th>内容</th>
                <%--<th>被展示次数</th>--%>
                <th>点击次数</th>
                <th>开始时间</th>
                <th>奖励</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${msgsList}" var="zhi">
                <tr>
                    <td id="number-data-id-"+${zhi.id}>${zhi.id}</td>
                    <td><c:out value="${zhi.title}"></c:out></td>
                    <td><c:out value="${zhi.content}"></c:out></td>
                    <%--<td>${zhi.show_times}</td>--%>
                    <td>${zhi.hit_count}</td>
                    <td> ${zhi.startTime}</td>
                    <td>
                        <c:if test="${zhi.reward==0}">
                            <span>无奖励</span>
                        </c:if>
                        <c:if test="${zhi.reward>0}">
                            <span>${zhi.reward}钻石</span>
                        </c:if>
                    </td>
                    <td>
                        <a href="javascript:;"  data-id="${zhi.id}" onclick="browse(this)" class="btn btn-primary">查看</a>
                        <a href="javascript:;"  data-id="${zhi.id}" onclick="delet(this)" class="btn btn-danger">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>


        <div class="col-md-6 col-md-offset-6">
            <table>
                <tr>
                    <td>
                        <a href="javaScript:mofen(1)"><img src="bs/images/img_firstpage.gif" width="16" height="16" /></a>&nbsp;
                        <a href="javaScript:mofen(${page-1 })"><img src="bs/images/img_prevpage.gif" width="11" height="16" /></a>&nbsp;${page }/${total }&nbsp;
                    </td>
                    <td align="right">
                        <a href="javaScript:mofen(${page+1 })"><img src="bs/images/img_nextpage.gif" width="12" height="16" /></a>&nbsp;
                        <a href="javaScript:mofen(${total })"><img src="bs/images/img_lastpage.gif" width="16" height="16" /></a>
                        <input type="hidden" value="${total}" id="hiddl"/>
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

    function mofen(page){
        var total = document.getElementById("hiddl").value;
        var limit = document.getElementById("y").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/status_editor?page="+page+"&limit="+limit+"&status=1";
    }

    //消息查看
    function browse(e) {
        var id = $(e).attr('data-id');
        window.location.href="/message_browse?msg_id="+id+"&status=1";
    }

    function delet(d) {
        var id = $(d).attr('data-id');
        var limit = $("#y").val();
        var r=confirm("确定删除此条消息?");
        if (r==true){
            window.location.href="/deletByNotice?status=0&id="+id+"&limit="+limit+"&flag=1";
        }else{
            return false;
        }
    }
    
    $(function () {
        $("#limit").change(function () {
            var limit = $(this).val();
            window.location.href="/status_editor?page=1&limit="+limit+"&status=1";
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
</script> 
</html>
