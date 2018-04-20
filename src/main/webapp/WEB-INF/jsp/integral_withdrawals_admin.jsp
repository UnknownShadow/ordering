<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>潮尚玩|积分提现审核</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</head>

<body>
    <div style="padding: 20px;">
        <table class="table table-hover"  id="list">
            <%--当前状态	操作--%>

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

            <tr class="active">
                <th width="10%">申请时间</th>
                <th width="2%">玩家ID</th>
                <th width="5%">昵称</th>
                <th width="5%">身份</th>
                <th width="10%">申请提现积分</th>
                <th width="5%">提现金额</th>
                <th width="10%">运营审批</th>
                <th width="10%">财务审批</th>
                <th width="10%">管理员审批</th>
                <th width="10%">当前状态</th>
                <th width="45%">操作</th>
            </tr>
            <c:forEach items="${WRecords}" var="zhi">
                <tr>
                    <td>
                        <fmt:parseDate value="${zhi.created_time}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                        <fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>
                    </td>
                    <td>${zhi.user_id}</td>
                    <td>${zhi.nickname}</td>
                    <td>${zhi.user_status}</td>
                    <td>${zhi.scoreNum}</td>
                    <td>${zhi.cash}</td>
                    <td>
                        <c:if test="${zhi.process_audit==2}">
                            <c:if test="${zhi.process_audit==2 && zhi.audit==1}">
                                <span>运营审核中</span>
                            </c:if>
                            <c:if test="${zhi.audit==3}">
                                <span>运营审核拒绝</span>
                            </c:if>
                        </c:if>
                        <c:if test="${zhi.process_audit>2}">
                            <span>运营审核通过</span>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${zhi.process_audit==3}">
                            <c:if test="${zhi.process_audit==3 && zhi.audit==1}">
                                <span>财务审核中</span>
                            </c:if>
                            <c:if test="${zhi.audit==3}">
                                <span>财务审核拒绝</span>
                            </c:if>
                        </c:if>
                        <c:if test="${zhi.process_audit>3}">
                            <span>财务审核通过</span>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${zhi.process_audit==4 && zhi.audit==1}">
                            <span>管理员审核中</span>
                        </c:if>
                        <c:if test="${zhi.process_audit==4 && zhi.audit==3}">
                            <span>管理员审核拒绝</span>
                        </c:if>
                        <c:if test="${zhi.process_audit==4 && zhi.audit==2}">
                            <span>管理员审核通过</span>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${zhi.process_audit==2 && zhi.audit==1}">
                            <span>运营审核中</span>
                        </c:if>
                        <c:if test="${zhi.process_audit==2 && zhi.audit==3}">
                            <span>运营审核拒绝</span>
                        </c:if>

                        <c:if test="${zhi.process_audit==3 && zhi.audit==1}">
                            <span>财务审核中</span>
                        </c:if>
                        <c:if test="${zhi.process_audit==3 && zhi.audit==3}">
                            <span>财务审核拒绝</span>
                        </c:if>

                        <c:if test="${zhi.process_audit==4 && zhi.audit==1}">
                            <span>管理员审核中</span>
                        </c:if>
                        <c:if test="${zhi.process_audit==4 && zhi.audit==3}">
                            <span>管理员审核拒绝</span>
                        </c:if>
                        <c:if test="${zhi.audit==2 && zhi.pay_status == 0}">
                            <span>审核通过，还未提款</span>
                        </c:if>
                        <c:if test="${zhi.audit==2 && zhi.pay_status == 1}">
                            <span>提款成功</span>
                        </c:if>
                        <c:if test="${zhi.audit==2 && zhi.pay_status == 2}">
                            <span>提款失败</span>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${zhi.process_audit==4 && zhi.audit==1}">
                            <input type="button" value="通过" id="${zhi.user_id}" alt="${zhi.trade_no}" onclick="passed(this)" class="btn btn-primary" style="margin:0px;padding-left: 10px;padding-right:10px;"/>
                            <input type="button" value="拒绝" id="${zhi.user_id}" alt="${zhi.trade_no}" onclick="refuse(this)" class="btn btn-danger" style="margin:0px;padding-left: 10px;padding-right:10px;"/>
                        </c:if>
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
                        <input type="hidden" value="${page}" id="page"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</body>
<script>
    $(function () {
        $("#limit").change(function () {
            var limit = $(this).val();
            window.location.href="/integral_withdrawals_admin?page=1&limit="+limit;
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
        window.location.href="/integral_withdrawals_admin?page="+page+"&limit="+limit;
    }

    function passed(c) {
        var limit = document.getElementById("y").value;
        var page = document.getElementById("page").value;
       window.location.href="/integral_audit?byWho=4&status=5&ID="+c.id+"&trade_no="+c.alt+"&page="+page+"&limit="+limit;
    }
    function refuse(r) {
        var limit = document.getElementById("y").value;
        var page = document.getElementById("page").value;
        window.location.href="/integral_audit?byWho=4&status=0&ID="+r.id+"&trade_no="+r.alt+"&page="+page+"&limit="+limit;
    }
    function arrival(r) {
        window.location.href="/integral_audit?byWho=4&status=2&ID="+r.id;
    }
</script>
</html>
