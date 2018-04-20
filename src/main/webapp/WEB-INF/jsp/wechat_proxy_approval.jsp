<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>聚牛网络|待审核代理</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
</head>

<!-- 菜单js文件 -->
<script src="bs/pagejs/index.js"  type="text/javascript"  charset="UTF-8"></script>
<script>
    $(function () {
        menu('seniorLabel','wechatProxyApproval');
    });
</script>

<body>
<!-- WRAPPER -->
<div id="wrapper">
    <!-- NAVBAR -->
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="brand" style="padding: 10px;">
            <a href="/idex"><img src="assets/img/1.png" class="img-responsive" style="height: 60px;margin-left: 20px;padding-right:23px;padding-left:23px;"></a>
        </div>
        <div class="container-fluid">
            <div class="navbar-btn">
                <button type="button" class="btn-toggle-fullwidth"><i class="lnr lnr-arrow-left-circle"></i></button>
            </div>

            <div class="navbar-btn navbar-btn-right">
                <a class="btn btn-success update-pro" href="/loginout" title="Upgrade to Pro"><i class="fa fa-rocket"></i> <span>退出登录</span></a>
            </div>

        </div>
    </nav>
    <!-- END NAVBAR -->
    <!-- LEFT SIDEBAR -->
    <div id="sidebar-nav" class="sidebar">
        <div class="sidebar-scroll">
            <nav>
                <ul class="nav">
                    <li><a href="/idex"><i class="lnr lnr-home"></i> <span>管理员首页</span></a></li>
                    <li id="admin"></li>
                    <li id="yunyin"></li>
                    <li id="proxys"></li>
                    <li id="finances"></li>
                    <li id="operation"></li>
                </ul>
            </nav>
        </div>
    </div>
    <!-- END LEFT SIDEBAR -->
    <!-- MAIN -->

    <div class="main">
        <!-- MAIN CONTENT -->
        <div class="main-content">
            <div class="container-fluid">
                <div class="row">
                    <div class="panel">
                        <div class="panel-heading" style="margin-bottom: 200px;">
                            <h4><label>待审核代理</label></h4>

                            <div style="margin-left: 20px;margin-right: 20px;">
                                <div class="row">
                                    <div class="col-md-12">
                                        <table class="table table-hover">
                                            <tr class="active success">
                                                <th>玩家ID</th>
                                                <th>玩家昵称</th>
                                                <th>姓名</th>
                                                <th>手机号</th>
                                                <th>地址</th>
                                                <th>申请时间</th>
                                                <th>处理</th>
                                            </tr>
                                            <c:forEach items="${approvalDates }" var="zhi">
                                                <tr>
                                                    <td>${zhi.user_id}</td>
                                                    <td>${zhi.nickname}</td>
                                                    <td>${zhi.name}</td>
                                                    <td>${zhi.phone}</td>
                                                    <td>${zhi.address}</td>
                                                    <td>
                                                        <fmt:parseDate value="${zhi.approve_date}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                                                        <fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>
                                                    </td>
                                                    <td>
                                                        通过
                                                        <%--<input type="button" class="btn btn-primary" onclick="pass(this)" alt="${zhi.id}" value="通过">
                                                        <input type="button" class="btn btn-danger" onclick="refuse(this)" alt="${zhi.id}" value="拒绝">--%>
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
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- END MAIN CONTENT -->
    </div>
</div>
<!-- END WRAPPER -->
<!-- Javascript -->
<script src="assets/vendor/jquery/jquery.min.js"></script>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script src="assets/scripts/klorofil-common.js"></script>
</body>
<script>

    function pass(p) {
        var id = p.alt;
        var cf = confirm("     你确定 同意 该玩家的代理申请操作吗？");
        if(cf){
            window.location.href="application?id="+id+"&status="+1;
        }else{
            return false;
        }

    }

    function refuse(r) {
        var id = r.alt;
        var cf = confirm("     你确定 拒绝 该玩家的代理申请操作吗？");
        if(cf){
            window.location.href="application?id="+id+"&status="+0;
        }else{
            return false;
        }
    }

    function mofen(page){
        var total = document.getElementById("hidd").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/wechat_proxy_approval?page="+page;
    }
</script>
</html>

