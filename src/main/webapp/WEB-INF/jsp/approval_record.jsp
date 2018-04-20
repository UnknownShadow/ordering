<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>聚牛网络|审批记录</title>
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
        <div class="row" style="margin-top: 20px;">
            <div class="col-md-3">
                <span style="margin-left: 14px;">
                    <label>起始时间：</label>
                    <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="proxy_date" value="${proxy_date}" readonly>
                </span>
            </div>

            <div class="col-md-3">
                <span style="margin-left: 14px;">
                    <label>结束时间：</label>
                    <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="proxy_date_end" value="${proxy_date_end}" readonly>
                </span>
            </div>

            <div class="col-md-3">
                <span style="margin-left: 14px;">
                    <label>发起人ID：</label>
                    <input type="text" class="form-control" id="send_id" placeholder="请输入ID号" maxlength="8" value="${send_id}"/>
                </span>
            </div>
        </div>
        <div class="row" style="margin-top: 15px;">
            <div class="col-md-2 col-md-offset-4">
                <input type="button" class="btn btn-primary" id="inquiry" value="查询"/>
            </div>
        </div>

        <div class="table-responsive" style="margin-top: 15px;">
            <table class="table table-hover">
                <tr class="active">
                    <th>流水号</th>
                    <th>发起人ID</th>
                    <th>发起人昵称</th>
                    <th>接收人ID</th>
                    <th>接收人昵称</th>
                    <th>钻石数量</th>
                    <th>金额数量</th>
                    <th>付款状态</th>
                    <th>状态</th>
                    <th>审批人</th>
                </tr>
                <c:forEach items="${auditDataList }" var="zhi">
                    <tr>
                        <td>${zhi.audit_id}</td>
                        <td>${zhi.send_id}</td>
                        <td>${zhi.send_name}</td>
                        <td>${zhi.receive_id}</td>
                        <td>${zhi.receive_name}</td>
                        <td>${zhi.diamond}</td>
                        <td>￥${zhi.money}</td>
                        <td>
                            <c:if test="${zhi.pay_status==0}">
                                <span>扶持计划</span>
                            </c:if>
                            <c:if test="${zhi.pay_status==1}">
                                <span>默认</span>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${zhi.audit_status==0}">
                                <span style="color: #ff0b00">审核拒绝</span>
                            </c:if>
                            <c:if test="${zhi.audit_status==1}">
                                <span style="color: #8f1f00">审核通过</span>
                            </c:if>
                            <c:if test="${zhi.audit_status==2}">
                                <span style="color: slateblue">待审核</span>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${zhi.auditor==''}">
                                暂无
                            </c:if>
                            <c:if test="${zhi.auditor!=''}">
                                ${zhi.auditor}
                            </c:if>
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
        var proxy_date = document.getElementById("proxy_date").value;
        var proxy_date_end = document.getElementById("proxy_date_end").value;
        var send_id = document.getElementById("send_id").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/approval_record?page="+page+"&proxy_date="+proxy_date+"&proxy_date_end="+proxy_date_end+"&send_id="+send_id;
    }

    $(function () {

        $("#inquiry").click(function () {
            var proxy_date = $("#proxy_date").val();
            var proxy_date_end = $("#proxy_date_end").val();
            var send_id = $("#send_id").val();

            if(proxy_date!="" && proxy_date_end=="" || proxy_date=="" && proxy_date_end!=""){
                alert("请选择开始时间或结束时间！");
            }else{
                window.location.href="/approval_record?page=1&proxy_date="+proxy_date+"&proxy_date_end="+proxy_date_end+"&send_id="+send_id;
            }
        });
    });
</script>
</html>
