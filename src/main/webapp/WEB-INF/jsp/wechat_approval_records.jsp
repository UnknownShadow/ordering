<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>聚牛网络|代理审批记录</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>

</head>

<body>
    <div style="padding: 20px;margin-bottom: 100px;">
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
        <div style="margin-left: 20px;margin-right: 20px;">
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-hover">
                        <tr class="active success">
                            <th>玩家ID</th>
                            <th>玩家昵称</th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th>来源</th>
                            <th>代理层级</th>
                            <th>地址</th>
                            <th>处理结果</th>
                            <th>处理人</th>
                        </tr>
                        <c:forEach items="${approvalAllDates }" var="zhi">
                            <tr>
                                <td>${zhi.user_id}</td>
                                <td>${zhi.nickname}</td>
                                <td>${zhi.name}</td>
                                <td>${zhi.phone}</td>
                                <td>
                                    <c:if test="${zhi.source==1}">
                                        <span>微信公众号添加</span>
                                    </c:if>
                                    <c:if test="${zhi.source==2}">
                                        <span>后台添加</span>
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${zhi.proxy_hierarchy==1}">
                                        <span>代理</span>
                                    </c:if>
                                    <c:if test="${zhi.proxy_hierarchy==2}">
                                        <span>至尊合伙人</span>
                                    </c:if>
                                </td>
                                <td>${zhi.address}</td>
                                <td>
                                    <c:if test="${zhi.dispose_result==1}">
                                        <span style="color: #8f1f00">通&nbsp;&nbsp;&nbsp;&nbsp;过</span>
                                    </c:if>
                                    <c:if test="${zhi.dispose_result==0}">
                                        <span style="color: #ff0b00">未通过</span>
                                    </c:if>
                                    <c:if test="${zhi.dispose_result==3}">
                                        <span style="color: #a50718">待审核</span>
                                    </c:if>
                                </td>
                                <td>${zhi.dispose_name}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
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
    </div>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</body>
<script>
    $(function () {
        $("#limit").change(function () {
            var limit = $(this).val();
            window.location.href="/wechat_approval_records?page=1&limit="+limit;
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
        window.location.href="/wechat_approval_records?page="+page+"&limit="+limit;
    }
</script>
</html>

