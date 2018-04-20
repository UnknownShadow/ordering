<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <title>聚牛网络|查看代理积分</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

</head>
<!-- 菜单js文件 -->
<script src="bs/pagejs/index.js"  type="text/javascript"  charset="UTF-8"></script>
<script>
    $(function () {
        menu('agencyManagement','proxyIntegralRecord');
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
                    <div class="panel" >
                        <div class="panel-heading" style="margin-bottom: 350px;">
                            <h4  style="margin-left: 30px;"><label>查看代理积分</label></h4>

                            <a href="/proxy_integral_record?page=1&limit=20&user_id=" class="btn btn-primary" style="position: absolute;right: 30px;top: 20px;">
                                返回
                            </a>

                            <%--<div class="row">
                                <div class="col-md-6">
                                    <form action="#" method="post" class="navbar-form navbar-left">
                                        <div class="input-group">
                                            <input type="text" id="userId" class="form-control searchbox" placeholder="请输入用户ID..." maxlength="10" value="${user_id}" >
                                            <span class="input-group-btn"><input type="button" id="findLog" class="btn btn-primary" value="查询"></span>
                                        </div>
                                    </form>
                                </div>
                            </div>--%>


                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <tr class="active success">
                                        <th>昵称（ID）</th>
                                        <th>推荐子代理数量</th>
                                        <th>子代理返利笔数</th>
                                        <th>总积分</th>
                                        <th>提现积分</th>
                                        <th>当前积分</th>
                                    </tr>
                                    <c:forEach items="${list}" var="zhi" >
                                        <tr>
                                            <td>${zhi.user_id}</td>
                                            <td>${zhi.rebate_child_total}</td>
                                            <td>${zhi.child_total_count}</td>
                                            <td>${zhi.total_rebate}</td>
                                            <td>${zhi.withdrawal_rebate}</td>
                                            <td>${zhi.present_rebate}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                            <%--<div class="col-md-4 col-md-offset-8" style="margin-top: 50px;">
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
                            </div>--%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- END MAIN CONTENT -->
    </div>
    <!-- END MAIN -->
</div>
<!-- END WRAPPER -->
<!-- Javascript -->

<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script src="assets/scripts/klorofil-common.js"></script>
</body>
<script>
    $(function () {
        $("#findLog").click(function () {
            var user_id = $("#userId").val();
            var reg = /^[0-9]*$/;

            if(!reg.test(user_id)){
                alert("请输入正确的ID");
                $("#userId").val("");
            }else{
                window.location.href="/proxy_integral_record?page=1&user_id="+user_id;
            }

        });
    });


    function mofen(page){
        var total = document.getElementById("hidd").value;
        var user_id = document.getElementById("userId").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/proxy_integral_record?page="+page+"&user_id="+user_id;
    }

</script>
</html>
