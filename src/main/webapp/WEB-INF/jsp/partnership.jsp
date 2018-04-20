<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <title>潮尚玩|合伙人详情</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">

    <link type="text/css" rel="stylesheet" href="/assets/DatePicker/skin/WdatePicker.css">
    <link type="text/css" rel="stylesheet" href="/assets/DatePicker/skin/whyGreen/datepicker.css">
    <script src="/assets/DatePicker/WdatePicker.js" ></script>
    <script src="/assets/DatePicker/config.js" ></script>
    <script src="/assets/DatePicker/lin.js"></script>
</head>
<body>
    <div style="padding: 20px;margin-top: 30px;">

        <div class="col-sm-3">
            <input type="text" class="form-control" id="userId" value="${userId}" placeholder="请输入合伙人ID..." style="width: 150px;"/>
            <input type="text" class="form-control" id="date" onclick="WdatePicker({el:$dp.$('gz')})" value="${chooseDate}" placeholder="请选择时间" style="width: 150px;margin-left: 170px;margin-top: -34px;" readonly/>
            <input type="button" class="btn btn-primary" value="查询" id="query" style="margin-left: 340px;margin-top: -36px;" />
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

        <div class="table-responsive">
            <table class="table table-hover">
                <tr class="active success">
                    <th>总代理昵称（ID）</th>
                    <th>房间数</th>
                    <th>开房用钻</th>
                    <th>钻石销量</th>
                    <th>活跃人数</th>
                    <th>代理人数</th>
                    <th>玩家人数</th>
                </tr>
                <c:forEach items="${partnership}" var="zhi">
                    <tr>
                        <td>${zhi.nickname}（${zhi.userId}）</td>
                        <td>${zhi.gameNum}</td>
                        <td>${zhi.gameConsumeDiam}</td>
                        <td>${zhi.rechargeDiam}</td>
                        <td>${zhi.activeNum}</td>
                        <td>${zhi.agentNum}</td>
                        <td>${zhi.playerNum}</td>
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

        $("#query").click(function () {
            var userId = $("#userId").val();
            var limit = $("#y").val();
            var date = $("#date").val();
            window.location.href="/partnership?page=1&limit="+limit+"&userId="+userId+"&chooseDate="+date;
        });

        $("#limit").change(function () {
            var limit = $(this).val();
            var date = $("#date").val();
            var userId = $("#userId").val();
            window.location.href="/partnership?page=1&limit="+limit+"&userId="+userId+"&chooseDate="+date;
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
        var userId = document.getElementById("userId").value;
        var date = document.getElementById("date").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/partnership?page=1&limit="+limit+"&userId="+userId+"&chooseDate="+date;
    }
</script>
</html>
