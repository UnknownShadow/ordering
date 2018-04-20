<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>潮尚玩|达标俱乐部</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>

    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/DatePicker/WdatePicker.js" ></script>
    <script src="assets/DatePicker/config.js" ></script>
    <script src="assets/DatePicker/lin.js"></script>
    <script src="assets/DatePicker/config.js" ></script>

    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/WdatePicker.css">
    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/whyGreen/datepicker.css">
</head>
<body>
    <div style="padding: 20px;margin-bottom: 260px;">
        <div class="table-responsive" style="margin-top: 15px;">
            <table class="table table-hover">
                <tr class="active">
                    <th>达标俱乐部ID</th>
                    <th>俱乐部创建者昵称（ID）</th>
                    <th>达标时间</th>
                    <th>达标档次</th>
                    <th>可领积分</th>
                    <th>审核状态</th>
                </tr>
                <c:forEach items="${clubsActions}" var="zhi">
                    <tr>
                        <td>${zhi.club_code}</td>
                        <td>${zhi.nickname}（${zhi.creator_id}）</td>
                        <td>${zhi.created_time}</td>
                        <td>
                            <c:if test="${zhi.level==1}">钻石奖</c:if>
                            <c:if test="${zhi.level==2}">铂金奖</c:if>
                            <c:if test="${zhi.level==3}">黄金奖</c:if>
                            <c:if test="${zhi.level==4}">白银奖</c:if>
                        </td>
                        <td>${zhi.integral}</td>
                        <td>
                            <c:if test="${zhi.audit == 2}">
                                <input type="button" value="通过" id="${zhi.id}"  onclick="passed(this)" class="btn btn-primary" style="margin:0px;padding-left: 10px;padding-right:10px;"/>
                                <input type="button" value="拒绝" id="${zhi.id}"  onclick="refuse(this)" class="btn btn-danger" style="margin:0px;padding-left: 10px;padding-right:10px;"/>
                            </c:if>
                            <c:if test="${zhi.audit == 0}">审核拒绝 </c:if>
                            <c:if test="${zhi.audit == 1}">审核通过 </c:if>
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
    function mofen(page){
        var total = document.getElementById("hidd").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/clubs_action?page="+page+"&limit=20";
    }




    function passed(c) {
        var conf=confirm("是否确定通过该审核？");
        if (conf==true){
            auditAjax(1, c.id);
        }else{
            return false;
        }
    }

    function refuse(r) {
        var conf=confirm("是否确定拒绝该审核？");
        if (conf==true){
            auditAjax(0,r.id);
        }else{
            return false;
        }
    }

    function auditAjax(status,id) {
        $.ajax({
            type: 'post',
            url: '/clubs_action_audit',
            data: {"status": status, "ID": id},
            success: function (msg) {
                if (msg == "1") {
                    alert("操作成功！");
                }else if(msg == "0"){
                    alert("审核失败，无法根据ID 获取数据！");
                }else {
                    alert(msg);
                }
                window.location.href = "/clubs_action?page=1&limit=20";
            }
        });
    }
</script>
</html>
