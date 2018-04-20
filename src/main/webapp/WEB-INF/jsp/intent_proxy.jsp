<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>聚牛网络|意向代理列表</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
</head>
<style>a:hover{text-decoration: none;}</style>
<body>

    <ul class="layui-nav" lay-filter="">
        <li class="layui-nav-item"><a href="/proxy_overview">代理总览</a></li>
        <li class="layui-nav-item"><a href="/proxy_manage">添加删除代理</a></li>
        <li class="layui-nav-item layui-this"><a href="/intent_proxy?page=1">意向代理</a></li>
        <li class="layui-nav-item">
            <a href="/integral_withdrawals_operation?page=1&limit=20">积分提现审核</a>
        </li>
        <li class="layui-nav-item">
            <a href="/proxy_integral_record?page=1&limit=20&user_id=">代理总积分管理</a>
        </li>
        <li class="layui-nav-item">
            <a href="/proxy_log?page=1&limit=20&user_id=">代理日志查询</a>
        </li>
        <li class="layui-nav-item">
            <a href="/club_management?page=1&limit=20&queryId=&queryStatus=1">俱乐部管理</a>
        </li>
    </ul>

    <div style="padding: 20px;">
        <div style="margin-left: 20px;margin-right: 20px;">
            <div class="row">
                <div class="col-md-12" style="text-align: center;">
                    <table class="table table-hover">
                        <tr class="active success">
                            <th>称呼</th>
                            <th>手机号</th>
                            <th>时间</th>
                            <th>是否处理</th>
                            <th>操作</th>
                            <th>备注</th>
                            <th></th>
                        </tr>
                        <c:forEach items="${intentProxys }" var="zhi">
                            <tr>
                                <td>${zhi.name}</td>
                                <td>${zhi.phone}</td>
                                <td>
                                    <fmt:parseDate value="${zhi.created_time}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                                    <fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>
                                </td>
                                <td>
                                    <c:if test="${zhi.connection_status == 0 }"> 未处理 </c:if>
                                    <c:if test="${zhi.connection_status == 1 }"> 已处理 </c:if>
                                </td>
                                <td style="text-align: center;">
                                    <c:if test="${zhi.connection_status == 0 }">
                                        <input type="button" class="btn btn-primary contacted" alt="${zhi.id}" value="标记为已联系"/>
                                    </c:if>
                                    <c:if test="${zhi.connection_status == 1 }">- - -</c:if>
                                </td>
                                <td>
                                    <input type="text" class="form-control" id="remark${zhi.id}" value="${zhi.remark}" placeholder="此处可输入备注..." maxlength="20" disabled>
                                </td>
                                <td>
                                    <button class="btn editor" id="editor${zhi.id}" alt="${zhi.id}"><span style="padding: 10px;" class="glyphicon glyphicon-pencil"></span></button>
                                    <button class="btn save" id="save${zhi.id}" alt="${zhi.id}" style="display: none;"><span style="padding: 10px;" class="glyphicon glyphicon-ok"></span></button>
                                </td>
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
</body>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script>
    $(function () {
        $(".editor").click(function () {
            var id = $(this).attr("alt");
            $("#remark"+id).removeAttr("disabled");
            $("#remark"+id).focus();
            $("#editor"+id).hide();
            $("#save"+id).show();
        });
        $(".save").click(function () {
            var id = $(this).attr("alt");
            $("#remark"+id).attr("disabled","true");
            var remark = $("#remark"+id).val();

            $.ajax({
                type:"post",
                url:"/saveRemark",
                data:{"id":id,"remark":remark},
                success:function(msg) {
                    alert("添加成功！");
                }
            });
            $("#editor"+id).show();
            $("#save"+id).hide();
        });
        $(".contacted").click(function () {
            var id = $(this).attr("alt");
            window.location.href="/changeConnection?id="+id;
        });
    });


    function mofen(page){
        var total = document.getElementById("hidd").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/intent_proxy?page="+page;
    }
</script>

</html>

