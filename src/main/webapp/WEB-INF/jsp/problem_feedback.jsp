<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <title>潮尚玩|问题反馈</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
</head>
<body>
    <div style="padding: 20px;">
        <span style="font-size: 24px;font-weight: 700;">问题反馈</span>

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
                    <th>反馈用户昵称</th>
                    <th>反馈用户ID</th>
                    <th>问题描述</th>
                    <th>反馈手机号</th>
                    <th>反馈时间</th>
                    <th>是否处理</th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${feedbackInfoEntities}" var="zhi">
                    <tr>
                        <td>${zhi.nickname}</td>
                        <td>${zhi.user_id}</td>
                        <td>${zhi.desc}</td>
                        <td>${zhi.phone}</td>
                        <td>${zhi.created_time}</td>
                        <td>
                            <c:if test="${zhi.connection_status == 0 }"> 未处理 </c:if>
                            <c:if test="${zhi.connection_status == 1 }"> 已处理 </c:if>
                        </td>
                        <td>
                            <c:if test="${zhi.connection_status == 0 }">
                                <input type="button" class="btn btn-primary contacted" alt="${zhi.id}" value="标记为已联系"/>
                            </c:if>
                            <c:if test="${zhi.connection_status == 1 }">- - -</c:if>
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
    $(function () {

        $(".contacted").click(function () {
            var id = $(this).attr("alt");
            window.location.href="/contacted?id="+id+"&status=2";
        });

        $("#limit").change(function () {
            var limit = $(this).val();
            window.location.href="/problem_feedback?page=1&limit="+limit;
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
        window.location.href="/problem_feedback?page="+page+"&limit="+limit;
    }
</script>
</html>
