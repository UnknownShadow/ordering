<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>潮尚玩|代理</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <!--修改密码样式-->
    <link rel="stylesheet" href="assets/css/common.css"/><!-- 基本样式 -->
    <link rel="stylesheet" href="assets/css/animate.css"/> <!-- 动画效果 -->
    <script src="assets/scripts/jquery.hDialog.js"></script>

    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>

    <script src="bs/js/bootstrap-paginator.min.js"></script>
</head>

<body>
    <div style="padding: 20px;">
        <label>我的子代理：</label>
        <div class="row">
            <div class="col-md-12 table-responsive" >

                <table class='table table-hover'>
                    <tr class="active">
                        <th>玩家昵称</th>
                        <th>子代理ID</th>
                        <th>注册日期</th>
                        <th>钻石数量</th>
                        <th>累计分发钻石数</th>
                        <th>为他充值钻石数</th>
                    </tr>
                    <tbody class='table table-hover' id="dataSource"></tbody>
                </table>
            </div>
        </div>
    <!--ajax分页按钮-->
        <div class="span9">
            <div class="row">
                <div class="col-md-12">
                    <ul class="examlpe"></ul>
                </div>
            </div>
        </div>
    </div>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</body>
<script>
    $(function () {
        ///------------分页查询子代理数据------------////
            var carId=1;
            var pageCount;
            var currentPage;
            $.ajax({
                type:"post",
                contentType:"application/json;charset=UTf-8",
                dataType:"json",
                url:"/ajaxmfen",
                data:{"carId":carId},
                success:function(result){
                    if(result.code == 0){
                        var json = eval(result.data);
                        $.each(json,function(index,items){
                            var obj ="<tr>"
                                +"<td width='12%'>"+items.name+"</td>"
                                +"<td width='8%'>"+items.id+"</td>"
                                +"<td width='10%'>"+items.proxy_date+"</td>"
                                +"<td width='10%'>"+items.diamond+"</td>"
                                +"<td width='15%'>"+items.proxyNumTotal+"</td>"
                                +"<td width='15%'>"+items.fatherProxyNumTotal+"</td>"
                                +"</tr>"

                            $("#dataSource").append(obj);
                            pageCount = items.pageCount;		//总页数
                            currentPage = items.CurrentPage;	//得到当前页
                        });

                        var options = {
                            bootstrapMajorVersion:3,
                            currentPage:currentPage,
                            totalPages: pageCount,
                            size:"small",
                            numberOfPages: 4,
                            itemContainerClass:function(type, page, currentPage){
                                return (page==currentPage)?"active":"c";
                            },
                            itemTexts:function(type,page,current){
                                switch(type){
                                    case "first":
                                        return "首页";
                                    case "prev":
                                        return "上一页";
                                    case "next":
                                        return "下一页";
                                    case "last":
                                        return "末页";
                                    case "page":
                                        return page;

                                }
                            },	//点击事件
                            onPageClicked:function(event,originalEvent,type,page){
                                $("#dataSource").empty();
                                $.ajax({
                                    type:"post",
                                    url:"/ajaxOnclick",
                                    data:{"page":page},
                                    success:function(data1){
                                        if(data1!=null){
                                            var json = eval(data1);

                                            $.each(json,function(index,items){
                                                var obj ="<tr>"
                                                    +"<td width='12%'>"+items.name+"</td>"
                                                    +"<td width='8%'>"+items.id+"</td>"
                                                    +"<td width='10%'>"+items.proxy_date+"</td>"
                                                    +"<td width='10%'>"+items.diamond+"</td>"
                                                    +"<td width='15%'>"+items.proxyNumTotal+"</td>"
                                                    +"<td width='15%'>"+items.fatherProxyNumTotal+"</td>"
                                                    +"</tr>"
                                                $("#dataSource").append(obj);
                                            });
                                        }
                                    }
                                });
                            }
                        }
                        $(".examlpe").bootstrapPaginator(options);
                    }else{
                        alert(result.errMsg);
                    }
                }
            });
        });
</script>
</html>
