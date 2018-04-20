<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>聚牛网络|财务日报</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>

    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/scripts/jquer_shijian.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="assets/css/shijian.css"/>
</head>

<body>
    <div style="padding: 20px;margin-bottom: 260px;">
        <div class="table-responsive" style="margin-top: 15px;">
            <table class="table table-hover">
                <tr class="active">
                    <th>日期</th>
                    <th>当日总营收</th>
                    <th>苹果充值</th>
                    <th>微信充值</th>
                    <th>平台充值</th>
                    <th>当日充钻总数量</th>
                    <th>当日钻石总消耗</th>
                    <th>当日钻石流动总数</th>
                </tr>
                <c:forEach items="${financialAll }" var="zhi">
                    <tr>
                        <td>${zhi.daily_date}</td>
                        <td>${zhi.totalRevenue}</td>
                        <td>${zhi.appleRecharge}</td>
                        <td>${zhi.wechatRecharge}</td>
                        <td>${zhi.platformRecharge}</td>
                        <td>${zhi.total_recharge}</td>
                        <td>${zhi.diamond_expend_total}</td>
                        <td>${zhi.diamond_flow_total}</td>
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
        window.location.href="/financial_daily?page="+page;
    }
</script>
</html>
