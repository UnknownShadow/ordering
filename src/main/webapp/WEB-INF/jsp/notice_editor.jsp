<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>聚牛网络|公告列表</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <!--样式文件-->
    <link rel="stylesheet" type="text/css" href="assets/css/jquery.dataTables.min.css">
    <!--DataTables 核心 js-->
    <script src="assets/scripts/jquery.dataTables.js"></script>
</head>

<!-- 菜单js文件 -->
<script src="bs/pagejs/index.js"  type="text/javascript"  charset="UTF-8"></script>
<script>
    $(function () {
        menu('operationLabel','noticeList');

        $("#againEditor").click(function () {

            alert($("#id").text());
        });
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

                        <%--<a href="javascript:window.location.href='/success';" class="btn btn-primary">删除</a>--%>
                        <div class="panel-heading" style="margin-bottom: 100px;">
                            <div style="text-align: center">
                                <h3><label>公告列表</label></h3>
                            </div>

                            <a href="/notices" class="btn btn-primary">【新增】公告</a>

                            <table class="table table-hover" id="example" class="display">
                                <tr class="active">
                                    <th>编号</th>
                                    <th>标题</th>
                                    <th>状态</th>
                                    <th>创建时间</th>
                                    <th>开始时间</th>
                                    <th>结束时间</th>
                                    <th>操作</th>
                                </tr>
                                <c:forEach items="${msgNoticePaging}" var="zhi">
                                    <tr>
                                        <td  id="number-data-id-"+${zhi.id}>${zhi.id}</td>
                                        <td>
                                            <c:if test="${zhi.title==''}">
                                                图片公告
                                            </c:if>
                                            <c:if test="${zhi.title!=''}">
                                                ${zhi.title}
                                            </c:if>
                                        </td>
                                        <td>
                                                <c:if test="${zhi.status=='0'}">
                                                    <span>草稿</span>
                                                </c:if>
                                                <c:if test="${zhi.status=='1'}">
                                                    <c:choose>
                                                        <c:when test="${nowDate<zhi.start_date}">
                                                            <span>未开始</span>
                                                        </c:when>
                                                        <c:when test="${nowDate <= zhi.end_date}">
                                                            <c:if test="${nowDate >= zhi.start_date}">
                                                                <span>进行中</span>
                                                            </c:if>
                                                        </c:when>
                                                        <c:when test="${nowDate>zhi.end_date}">
                                                            <span>已结束</span>
                                                        </c:when>
                                                    </c:choose>
                                                </c:if>
                                        </td>
                                        <td><c:out value="${zhi.create_date}"></c:out></td>
                                        <td><c:out value="${zhi.start_date}"></c:out></td>
                                        <td><c:out value="${zhi.end_date}"></c:out></td>
                                        <td>
                                            <a href="javascript:;"  data-id="${zhi.id}" onclick="send(this)" class="btn btn-primary">再次编辑</a>
                                            <a href="javascript:;"  data-id="${zhi.id}" onclick="delet(this)" class="btn btn-danger">删除</a>


                                            <%--<c:if test="${zhi.status=='1'}">
                                                <c:if test="${nowDate<zhi.start_date}">
                                                    <a href="javascript:;"  data-id="${zhi.id}" onclick="send(this)" class="btn btn-primary">再次编辑</a>
                                                    <a href="javascript:;"  data-id="${zhi.id}" onclick="delet(this)" class="btn btn-danger">删除</a>
                                                </c:if>
                                                <c:if test="${nowDate>zhi.end_date}">
                                                    <a href="javascript:;"  data-id="${zhi.id}" onclick="send(this)" class="btn btn-primary">再次编辑</a>
                                                    <a href="javascript:;"  data-id="${zhi.id}" onclick="delet(this)" class="btn btn-danger">删除</a>
                                                </c:if>
                                            </c:if>
                                           <c:if test="${zhi.status=='0'}">

                                            </c:if>--%>
                                        </td>
                                    </tr>
                                </c:forEach>

                            </table>

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
                                            <input type="hidden" value="${total}" id="hidde"/>
                                        </td>
                                    </tr>
                                </table>
                            </div>
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
        $('#example').DataTable({
            columns:[
                {data:"firstname"},
                {data:"lastname"},
                {data:"phone"}
            ]
        });
    });

    function mofen(page){
        var total = document.getElementById("hidde").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/notice_list?page="+page;
    }



    function send(e) {
        var id = $(e).attr('data-id');
        window.location.href="/againEditor?status=0&id="+id;

    }

    function delet(d) {
        var id = $(d).attr('data-id');

        var r=confirm("确定删除此条公告?");
        if (r==true){
            window.location.href="/deletByNotice?status=1&id="+id;
        }else{
            return false;
        }



    }
</script>


</html>
