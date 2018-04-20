<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>潮尚玩|提现审核</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</head>

<body>
   <%-- <div style="padding: 20px;">
        <div class="table-responsive">

            <table class="table table-hover"  id="list">
                <tr class="active">
                    <th>申请人昵称（ID）</th>
                    <th>提交时间</th>
                    <th>申请人微信</th>
                    <th>单号</th>
                    <th>手机号</th>
                    <th>提现积分</th>
                    <th>总积分</th>
                    <th>提现金额</th>
                    <th>运营审批</th>
                    <th>财务审批</th>
                    <th>管理审批1</th>
                    <th>管理审批2</th>
                    <th>当前状态</th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${WRecords}" var="zhi">
                    <tr>
                        <td>${zhi.user_id}</td>
                        <td>
                            <fmt:parseDate value="${zhi.created_time}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                            <fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>
                        </td>
                        <td>${zhi.wechat_number}</td>
                        <td>${zhi.order_number}</td>
                        <td>${zhi.phone}</td>
                        <td>${zhi.withdrawal_limit}</td>
                        <td>${zhi.total_integral}</td>
                        <td>${zhi.cash}</td>
                        <td>
                            <c:if test="${zhi.verification_status==0}">
                                <span>审核拒绝</span>
                            </c:if>
                            <c:if test="${zhi.verification_status==2}">
                                <span>审核中</span>
                            </c:if>
                            <c:if test="${zhi.verification_status>2}">
                                <span>审核通过</span>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${zhi.verification_status==0}">
                                <span>审核拒绝</span>
                            </c:if>
                            <c:if test="${zhi.verification_status==3}">
                                <span>审核中</span>
                            </c:if>
                            <c:if test="${zhi.verification_status>3}">
                                <span>审核通过</span>
                            </c:if>
                        </td>
                        <td style=" ">
                            <c:if test="${zhi.verification_status==0}">
                                <span>审核拒绝</span>
                            </c:if>
                            <c:if test="${zhi.verification_status==4}">
                                <span>审核中</span>
                            </c:if>
                            <c:if test="${zhi.verification_status>4}">
                                <span>审核通过</span>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${zhi.verification_status==0}">
                                <span>审核拒绝</span>
                            </c:if>
                            <c:if test="${zhi.verification_status==5}">
                                <span>审核中</span>
                            </c:if>
                            <c:if test="${zhi.verification_status==1}">
                                <span>审核通过</span>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${zhi.verification_status==0}">
                                <span>审核拒绝</span>
                            </c:if>
                            <c:if test="${zhi.verification_status==1}">
                                <span>审核通过</span>
                            </c:if>
                            <c:if test="${zhi.verification_status==2}">
                                <span>运营审核中</span>
                            </c:if>
                            <c:if test="${zhi.verification_status==3}">
                                <span>待财务审核</span>
                            </c:if>
                            <c:if test="${zhi.verification_status==4}">
                                <span>待管理审核</span>
                            </c:if>
                            <c:if test="${zhi.verification_status==5}">
                                <span>待管理2审核</span>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${zhi.verification_status==3}">
                                <input type="button" value="通过" id="${zhi.id}" onclick="passed(this)" class="btn btn-primary" style="margin:0px;padding-left: 10px;padding-right:10px;"/>
                                <input type="button" value="拒绝" id="${zhi.id}" onclick="refuse(this)" class="btn btn-danger" style="margin:0px;padding-left: 10px;padding-right:10px;"/>
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
                    </td>
                </tr>
            </table>
        </div>
    </div>--%>
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
        window.location.href="/integral_withdrawals?page="+page;
    }

    function passed(c) {
       window.location.href="/integral_audit?byWho=3&status=4&ID="+c.id;
    }
    function refuse(r) {
        window.location.href="/integral_audit?byWho=3&status=0&ID="+r.id;
    }
   /* function arrival(r) {
        window.location.href="/integral_audit?status=2&ID="+r.id;
    }
*/
</script>
</html>
