<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>潮尚玩|关键词过滤</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
    <div style="padding: 20px;">
        <h4><label>关键词过滤</label></h4>
        <br>
        <div class="row">
            <div class="col-md-6">
                <input type="text" class="form-control" id="txt">
            </div>
            <div class="col-md-6">
                <input type="button" class="btn btn-primary" id="confir" value="添加">
            </div>
        </div>
        <br>
        <div class="row">
            <div class="col-md-6">
                <h4><label>关键词过滤列表</label></h4>
                <select id="ta" multiple class="form-control" style="height: 200px;">
                    <c:forEach items="${keywordsList}" var="zhi">
                        <option value="">${zhi.keywords}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
</body>
<script>
    $(function () {
        $("#confir").click(function () {
            var txt = $("#txt").val();
            if(txt==""){
                alert("请输入要过滤的关键词");
            }else {
                $("#ta").append("<option>"+txt+"</option>");
                window.location.href="/add_filtra?keywords="+txt;
            }
        });
    });
</script>
</html>
