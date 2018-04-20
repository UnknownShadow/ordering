<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>潮尚玩|个人中心</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
</head>

<body>
    <div style="padding: 30px;">
        <div class="row tb">
            <div class="col-md-12">
                <table class="table table-hover table-bordered">
                    <tr>
                        <th class="active">玩家昵称</th>
                        <td>${userList.nickname}</td>
                    </tr>
                    <tr>
                        <th class="active">玩家ID</th>
                        <td id="user_id">${userList.users_id}</td>
                    </tr>
                    <tr>
                        <th class="active">用户身份</th>
                        <td>
                            <c:if test="${userList.user_status==2}">代理</c:if>
                            <c:if test="${userList.user_status==3}">玩家</c:if>
                        </td>
                    </tr>
                    <tr>
                        <th class="active">后台权限</th>
                        <td>${permission.role}</td>
                    </tr>
                    <tr>
                        <th class="active">钻石数量</th>
                        <td id="diam">${userList.diamond}</td>
                    </tr>
                    <tr>
                        <th class="active">子代理数量</th>
                        <td> ${childproxy} </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
