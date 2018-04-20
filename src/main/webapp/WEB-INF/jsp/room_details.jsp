<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>开房数据详情</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">

    <link rel="stylesheet" href="/assets/vendor/bootstrap/css/bootstrap.min.css">
    <!-- load css -->
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
    <script type="text/javascript" charset="utf8" src="/bs/js/jquery-1.9.1.min.js"></script>


    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/WdatePicker.css">
    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/whyGreen/datepicker.css">
    <script src="assets/DatePicker/WdatePicker.js" ></script>
    <script src="assets/DatePicker/config.js" ></script>
    <script src="assets/DatePicker/lin.js"></script>
</head>
<style>a:hover{text-decoration: none;}</style>
<body>
    <ul class="layui-nav" lay-filter="">
        <li class="layui-nav-item"><a href="/operation_daily?page=1&limit=20">运营日报</a></li>
        <li class="layui-nav-item">
            <a href="javascript:;">分时统计</a>
            <dl class="layui-nav-child" style="margin-top: -20px;"> <!-- 二级菜单 -->
                <dd style="padding-top: 5px;"><a href="/add_users">新增用户</a></dd>
                <dd style="padding-top: 5px;"><a href="/active_user">活跃用户数量</a></dd>
                <dd style="padding-top: 5px;"><a href="/paid_count">付费笔数</a></dd>
                <dd style="padding-top: 5px;"><a href="/pay_amount">付费金额</a></dd>
            </dl>
        </li>


        <li class="layui-nav-item">
            <a href="javascript:;">新增统计</a>
            <dl class="layui-nav-child" style="margin-top: -20px;"> <!-- 二级菜单 -->
                <dd style="padding-top: 5px;"><a href="/add_statistics">新增统计</a></dd>
                <dd style="padding-top: 5px;"><a href="/add_details?page=1&limit=20&chooseDate=">新增详情</a></dd>
            </dl>
        </li>
        <li class="layui-nav-item">
            <a href="javascript:;">活跃统计</a>
            <dl class="layui-nav-child" style="margin-top: -20px;"> <!-- 二级菜单 -->
                <dd style="padding-top: 5px;"><a href="/active_statistics">活跃统计</a></dd>
                <dd style="padding-top: 5px;"><a href="/active_details?page=1&limit=20&chooseDate=">活跃详情</a></dd>
            </dl>
        </li>
        <li class="layui-nav-item">
            <a href="javascript:;">付费统计</a>
            <dl class="layui-nav-child" style="margin-top: -20px;"> <!-- 二级菜单 -->
                <%--<dd style="padding-top: 5px;"><a href="#">付费统计</a></dd>--%>
                <dd style="padding-top: 5px;"><a href="/payment_details?page=1&limit=20&chooseDate=">付费详情</a></dd>
            </dl>
        </li>
        <li class="layui-nav-item">
            <a href="javascript:;">流失统计</a>
            <dl class="layui-nav-child" style="margin-top: -20px;"> <!-- 二级菜单 -->
                <%--<dd style="padding-top: 5px;"><a href="#">流失统计</a></dd>--%>
                <dd style="padding-top: 5px;"><a href="/flow_away_details?page=1&limit=20&chooseDate=">流失详情</a></dd>
            </dl>
        </li>
        <li class="layui-nav-item layui-this">
            <a href="javascript:;">房间统计</a>
            <dl class="layui-nav-child" style="margin-top: -20px;"> <!-- 二级菜单 -->
                <%--<dd style="padding-top: 5px;"><a href="#">房间统计</a></dd>--%>
                <dd style="padding-top: 5px;"><a href="/room_details?page=1&limit=20&chooseDate=">房间详情</a></dd>
            </dl>
        </li>
    </ul>

    <div style="padding: 20px;margin-bottom: 100px;">

        <label style="font-size: 20px;">开房数据详情</label><br>

        <div class="row" style="margin-top: 30px;">
            <div class="col-md-3">
                <label>选择日期：</label>
                <input type="text" class="form-control" placeholder="请选择日期..." id="chooseDate" onclick="WdatePicker({el:$dp.$('gz')})" value="${date}" readonly>
            </div>

            <div class="col-md-3" style="margin-top: 26px;">
                <input type="button" class="btn btn-primary" value="查询" id="detail_search"/>
            </div>
        </div>

        <br>
        <br>

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

        <label>当日房间：</label>
        <div class="row">
            <div class="col-md-12 table-responsive">
                <table class="table table-hover">
                    <tr class="active">
                        <th>游戏创建时间</th>
                        <th>游戏结束时间</th>
                        <th>房间号</th>
                        <th>游戏</th>
                        <th>房主</th>
                        <th>房间类型</th>
                        <th>房间人数</th>
                        <th>房间状态</th>
                        <th>房间战绩</th>
                    </tr>
                    <c:if test="${error != ''}">
                        <tr>
                            <td colspan="9" style="text-align: center;">${error}</td>
                        </tr>
                    </c:if>
                    <c:forEach items="${gamesEntities}" var="zhi">
                        <tr>
                            <td>${zhi.created_at}</td>
                            <td>${zhi.end_at}</td>
                            <td>${zhi.code}</td>
                            <td>
                                <c:if test="${zhi.gamekinds_id == 2}">上游</c:if>
                                <c:if test="${zhi.gamekinds_id == 3}">激K</c:if>
                                <c:if test="${zhi.gamekinds_id == 4}">麻将</c:if>
                            </td>
                            <td>${zhi.nickname}（${zhi.anchor_id}）</td>
                            <td>${zhi.room_type}</td>
                            <td>${zhi.num}（人）</td>
                            <td>
                                <c:if test="${zhi.status==1}">未开始</c:if>
                                <c:if test="${zhi.status==2}">游戏中</c:if>
                                <c:if test="${zhi.status==3}">已结束</c:if>
                            </td>
                            <td>${zhi.desc}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div style="margin-left: 80%;margin-top: 10%;">
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
</body>
<script type="text/javascript" src="/assets/layer/common/layui/layui.js"></script>
<script type="text/javascript">
    layui.use(['jquery','layer','element','laypage'],function(){
        window.jQuery = window.$ = layui.jquery;
        window.layer = layui.layer;
    });


    function mofen(page){
        var total = document.getElementById("hidd").value;
        var chooseDate = document.getElementById("chooseDate").value;
        var y = document.getElementById("y").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/room_details?page="+page+"&limit="+y+"&chooseDate="+chooseDate;
    }

    $(function () {
        $("#detail_search").click(function () {
            var chooseDate = $("#chooseDate").val();
            var y = $("#y").val();
            if(chooseDate == ""){
                alert("请选择日期后再查询！");
            }else{
                window.location.href="/room_details?page=1&limit="+y+"&chooseDate="+chooseDate;
            }
        });

        $("#limit").change(function () {
            var limit = $(this).val();
            var chooseDate = $("#chooseDate").val();
            window.location.href="/room_details?page=1&limit="+limit+"&chooseDate="+chooseDate;
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
</script>
</html>

