<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>潮尚玩|数据详情</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>

    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/WdatePicker.css">
    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/whyGreen/datepicker.css">
    <script src="assets/DatePicker/WdatePicker.js" ></script>
    <script src="assets/DatePicker/lin.js"></script>
    <script src="assets/DatePicker/config.js" ></script>

    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
</head>
<style>
    a:hover{
        text-decoration: none;
        font-weight: 700;
    }
</style>
<body>
<div style="padding: 20px;">

    <div class="col-sm-3" style="margin-bottom: 20px;margin-top: 20px;margin-left: 5px;">
        <input type="text" class="form-control" id="chooseDate" onclick="WdatePicker({el:$dp.$('gz')})" placeholder="请选择时间" style="width: 160px;" value="${date}" readonly/>
        <input type="button" class="btn btn-primary" value="查询" id="search" style="margin-left: 180px;margin-top: -34px;" />
    </div>

    <div style="margin-left: 20px;margin-right: 20px;">
        <div class="row">
            <div class="col-md-12">
                <table class="table table-hover">
                    <tr class="active success">
                        <th>合伙人ID</th>
                        <th>合伙人昵称</th>
                        <th>日期</th>
                        <th>充值总数</th>
                        <th>销售钻石数</th>
                        <th>活跃人数</th>
                        <th>开房次数</th>
                        <th>开房用钻</th>
                    </tr>
                    <c:forEach items="${list}" var="zhi">
                        <tr>
                            <td>${zhi.userId}</td>
                            <td>${zhi.nickname}</td>
                            <td>${zhi.date}</td>
                            <td>${zhi.rechargeNum} </td>
                            <td>${zhi.rechargeDiam} </td>
                            <td>${zhi.activeNum}</td>
                            <td>${zhi.gamesNum}</td>
                            <td>${zhi.gameConsumeDiam}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
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

        $("#search").click(function () {
            var chooseDate = $("#chooseDate").val();
            window.location.href="/partner_details?date="+chooseDate;
        });



        $("#limit").change(function () {
            var limit = $(this).val();
            var userId = $("#userId").val();
            window.location.href="/proxy_preview?page=1&limit="+limit+"&userId="+userId;
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
        var userId = document.getElementById("userId").value;
        var y = document.getElementById("y").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/proxy_preview?limit="+y+"&page="+page+"&userId="+userId;
    }

</script>

</html>

