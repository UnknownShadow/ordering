<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>聚牛网络|钻石消耗详情</title>
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
</head>

<body>
    <div style="padding: 20px;margin-bottom: 200px;">
        <div class="row" style="margin-top: 30px;">
            <div class="col-md-3">
                <label>起始时间：</label>
                <input type="text" class="form-control" placeholder="请选择日期..." maxlength="8" id="start_date" onclick="WdatePicker({el:$dp.$('gz')})" value="${start_date}" readonly>
            </div>

            <div class="col-md-3">
                <label>结束时间：</label>
                <input type="text" class="form-control" placeholder="请选择日期..." maxlength="8" id="end_date" onclick="WdatePicker({el:$dp.$('gz')})" value="${end_date}" readonly>
            </div>

            <div class="col-md-3" style="margin-top: 26px;">
                <input type="button" class="btn btn-primary" value="查询" id="searchess"/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 table-responsive">
                <table class="table table-hover" style="margin-top: 20px;">
                    <tr class="active" style="font-size: 13px;">
                        <th>日期</th>
                        <th>消耗总量</th>
                        <th>开房消耗总量</th>
                        <th>开宝箱消耗总量</th>
                        <th>参赛消耗总量</th>
                        <th>神秘宝箱开启次数</th>
                        <th>神秘宝箱钻石消耗总量</th>
                        <th>银宝箱开启次数</th>
                        <th>银宝箱消耗总量</th>
                        <th>金宝箱开启次数</th>
                        <th>金宝箱消耗总量</th>
                        <th>上游报名次数</th>
                        <th>激k报名次数</th>
                        <th>鱼虾蟹报名次数</th>
                        <th>竞技报名次数</th>
                    </tr>
                    <c:forEach items="${consumeLists }" var="zhi">
                        <tr>
                            <td>${zhi.consume_date}</td>
                            <td>${zhi.consume_total}</td>
                            <td>${zhi.room_consume}</td>
                            <td>${zhi.treasure_consume}</td>
                            <td>${zhi.competition_consume}</td>
                            <td>${zhi.mystery_treasures_count}</td>
                            <td>${zhi.mystery_treasures_consume}</td>
                            <td>${zhi.silver_count}</td>
                            <td>${zhi.silver_consume}</td>
                            <td>${zhi.gold_count}</td>
                            <td>${zhi.gold_consume}</td>
                            <td>${zhi.thirteen_count}</td>
                            <td>${zhi.fivek_count}</td>
                            <td>${zhi.fish_count}</td>
                            <td>${zhi.competition_count}</td>
                        </tr>
                    </c:forEach>
                    <tr class="active">
                        <td></td><td></td><td></td><td></td><td></td>
                        <td></td><td></td><td></td><td></td><td></td>
                        <td></td><td></td><td></td><td></td><td></td>
                    </tr>
                </table>
            </div>
        </div>

        <div class="row">
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
                    </tr>
                </table>
            </div>
        </div>
    </div>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</body>
<script>
    $(function () {
        $("#searchess").click(function () {
            var start_date = $("#start_date").val();
            var end_date = $("#end_date").val();

            if(start_date!=""&&end_date==""){
                alert("请输入结束日期");
            }else if(start_date==""&&end_date!=""){
                alert("请输入开始日期");
            }else{
                window.location.href="/consumption_details?page=1&start_date="+start_date+"&end_date="+end_date;
            }
        });
    });

    function mofen(page){
        var start_date = $("#start_date").val();
        var end_date = $("#end_date").val();
        var total = document.getElementById("hidd").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/consumption_details?page="+page+"&start_date="+start_date+"&end_date="+end_date;
    }
</script>
</html>
