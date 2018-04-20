<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>潮尚玩|日志查询</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
    <div style="padding: 20px;">
        <h4><label>日志查看</label></h4>
        <div class="table-responsive">
            <table class="table table-hover">
                <tr class="active success">
                    <th>用户ID</th>
                    <th>昵称</th>
                    <th>时间</th>
                    <th>事件</th>
                    <th>ip</th>
                </tr>
                <c:forEach items="${datas}" var="zhi">
                    <tr>
                        <td>${zhi.users_id}</td>
                        <td>${zhi.nickname}</td>
                        <td>
                            <fmt:parseDate value="${zhi.create_date}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                            <fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>
                        </td>
                        <td>${zhi.content}</td>
                        <td>${zhi.ip_address}</td>
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
                        <input type="hidden" value="${total}" id="hidde"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</body>
<script>
    function mofen(page){
        var total = document.getElementById("hidde").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/log_check?page="+page;
    }
</script>
</html>
