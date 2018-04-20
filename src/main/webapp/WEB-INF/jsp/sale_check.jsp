<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <title>聚牛网络|代理</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <!--修改密码样式-->
    <link rel="stylesheet" href="assets/css/common.css"/><!-- 基本样式 -->
    <link rel="stylesheet" href="assets/css/animate.css"/> <!-- 动画效果 -->

    <script src="assets/DatePicker/lin.js"></script>
    <script src="assets/DatePicker/config.js" ></script>
    <script src="assets/DatePicker/WdatePicker.js" ></script>
    <script src="assets/DatePicker/config.js" ></script>

    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/WdatePicker.css">
    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/whyGreen/datepicker.css">

</head>

<!-- 菜单js文件 -->
<script src="bs/pagejs/index.js"  type="text/javascript"  charset="UTF-8"></script>
<script>
    $(function() {
        menu('proxyLabel', 'Searche');
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
                        <div class="panel-heading table-responsive"  style="margin-bottom: 200px;">
                            <div class="row">
                                <form action="#" method="post">
                                    <div class="col-md-3">
                                        <span>
                                            <label>起始时间：</label>
                                            <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="proxy_date" value="${proxy_date}" readonly>
                                        </span>
                                    </div>

                                    <div class="col-md-3">
                                        <span>
                                            <label>结束时间：</label>
                                            <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="proxy_date_end" value="${proxy_date_end}" readonly>
                                        </span>
                                    </div>

                                    <div class="col-md-3">
                                        <span>
                                            <label>排序方式：</label>
                                            <select class="form-control" id="compositor">
                                                <option value="1" id="op1">子代理总览</option>
                                                <option value="2" id="op2">充值钻石数量排列</option>
                                                <option value="3" id="op3">发送钻石数量排列</option>
                                                <option value="4" id="op4">按昵称排序</option>
                                                <option value="5" id="op5">按ID排序</option>
                                            </select>
                                             <input type="hidden" value="${compositor}" id="hide"/>
                                        </span>
                                    </div>
                                </form>
                            </div>

                            <br>
                            <div class="row">
                                <div class="col-md-3 col-lg-offset-4">
                                    <a href="javascript:;" class=" btn btn-primary" id="searches">查询</a>
                                </div>
                            </div>
                            <br>
                            <br>

                            <label><h4>子代理销售查询</h4></label>
                            <table class="table table-hover">
                                <tr class="active">
                                    <th>子代理ID</th>
                                    <th>昵称</th>
                                    <th>当前状态</th>
                                    <th>发送钻石数量</th>
                                    <th>发送钻石笔数</th>
                                    <th>每笔平均数量</th>
                                    <th>收入钻石笔数</th>
                                    <th>收入钻石总数</th>
                                    <th>当前钻石数量</th>
                                </tr>
                                <c:forEach items="${lists}" var="zhi">
                                    <tr>
                                        <td>${zhi.users_id}</td>
                                        <td>${zhi.nickname}</td>
                                        <td>正常</td>
                                        <td>${zhi.out_num}</td>
                                        <td>${zhi.out_count}</td>
                                        <td>${zhi.avg}</td>
                                        <td>${zhi.in_count}</td>
                                        <td>${zhi.in_num}</td>
                                        <td>${zhi.diamond}</td>
                                    </tr>
                                </c:forEach>
                            </table>
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

    var val = $("#hide").val();

    if(val==1){
        $("#op1").attr("selected","true");
    }else if(val==2){
        $("#op2").attr("selected","true");
    }else if(val==3){
        $("#op3").attr("selected","true");
    }else if(val==4){
        $("#op4").attr("selected","true");
    }else if(val==5){
        $("#op5").attr("selected","true");
    }


    var compositor = $("#compositor").val();

    $("#compositor").change(function () {
        compositor = $("#compositor").val();
    });

    $("#searches").click(function () {
        var proxy_date = $("#proxy_date").val();
        var proxy_date_end = $("#proxy_date_end").val();

        window.location.href="/searches?proxy_date="+proxy_date+"&proxy_date_end="+proxy_date_end+"&compositor="+compositor;
      //  alert(proxy_date+""+proxy_date_end+""+compositor);
    });
});
</script>
</html>
