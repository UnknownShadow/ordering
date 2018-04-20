<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>潮尚玩|特权用户详情</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
    <div style="padding: 20px;margin-bottom: 200px;">
        <span style="font-size: 22px;font-weight: 700;">特权用户完成详情</span> <br><br>


        <a href="/privileged_statistics?page=1&limit=20&user_id=" class="btn btn-primary" style="position: absolute;right: 30px;top: 20px;">
            返回
        </a>

        <div class="table-responsive" style="margin-top: 15px;">
            <table class="table table-hover">
                <tr class="active">
                    <th>用户ID</th>
                    <th>昵称</th>
                    <th>第几天</th>
                    <th>完成的进度</th>
                    <th>获得活动积分</th>
                </tr>
                <c:forEach items="${privilegedDetailsResp}" var="zhi">
                    <tr>
                        <td>${zhi.userId}</td>
                        <td>${zhi.nickname}</td>
                        <td>${zhi.day}</td>
                        <td>${zhi.task}</td>
                        <td>${zhi.reward}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</body>
</html>
