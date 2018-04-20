<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>潮尚玩|第二充半价</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</head>

<body>
    <div style="padding: 20px;margin-bottom: 260px;">
        <div class="table-responsive">
            <h4><label>等待审批列表</label></h4>
            <div class=" pull-right">
                <input type="button" class="btn btn-primary" id="passAll" value="批量通过"/>
                <input type="button" class="btn btn-danger" id="refuseAll" value="批量拒绝"/>
            </div>
            <table class="table table-hover"  id="list">
                <tr class="active">
                    <th>日期</th>
                    <th>参与用户数量</th>
                    <th>参与总次数</th>
                    <th>活动总金额</th>
                </tr>
                <c:forEach items="${auditList }" var="zhi">
                    <tr>
                        <td>${zhi.audit_id}</td>
                        <td>${zhi.send_id}</td>
                        <td>${zhi.send_name}</td>
                        <td>${zhi.receive_id}</td>
                        <td>${zhi.receive_name}</td>
                        <td>${zhi.diamond}</td>
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
    </div>
</body>
<script>
    $(function () {
        //全选或全不选
        $("#all").click(function(){
            if(this.checked){
                $("#list :checkbox").prop("checked", true);
            }else{
                $("#list :checkbox").prop("checked", false);
            }
        });

        //设置全选复选框
        $("#list :checkbox").click(function(){
            allchk();
        });

    });
</script>
</html>
