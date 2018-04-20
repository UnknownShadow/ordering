<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>聚牛网络|比赛列表</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>

    <script src="assets/DatePicker/WdatePicker.js" ></script>
    <script src="assets/DatePicker/config.js" ></script>
    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/WdatePicker.css">
    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/whyGreen/datepicker.css">

    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
</head>
<style>a:hover{text-decoration: none;}</style>
<body>
    <ul class="layui-nav" lay-filter="">
        <li class="layui-nav-item">
            <a href="/paid_records">付费玩家记录</a>
        </li>

        <li class="layui-nav-item"><a href="/integral_manage">玩家积分管理</a></li>

        <li class="layui-nav-item">
            <a href="/status_editor?page=1&limit=20">消息管理</a>
        </li>
        <li class="layui-nav-item layui-this">
            <a href="/competition_list?page=1&limit=20&proxy_date=&proxy_date_end=">比赛管理</a>
        </li>
        <li class="layui-nav-item">
            <a href="/reward_pending?page=1&start_date=&end_date=&user_id=">比赛发奖管理</a>
        </li>
        <li class="layui-nav-item"><a href="/log_output?page=1&limit=20&user_id=">玩家日志查询</a></li>
        <li class="layui-nav-item"><a href="/version_history?page=1">版本管理</a></li>
    </ul>
    <div style="padding: 20px;">
        <label>按比赛结束时间查询：</label>
        <br>
        <div class="row" style="margin-bottom: 50px;">
            <div class="col-md-3">
                <span style="margin-left: 14px;">
                    <label>起始时间：</label>
                    <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:' yyyy-MM-dd HH:mm:ss'})" id="proxy_date" value="${proxy_date}" readonly>
                </span>
            </div>

            <div class="col-md-3">
                <span style="margin-left: 14px;">
                    <label>结束时间：</label>
                    <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:' yyyy-MM-dd HH:mm:ss'})" id="proxy_date_end" value="${proxy_date_end}" readonly>
                </span>
            </div>

            <div class="col-md-2" style="margin-top: 25px;">
                <input type="button" class="btn btn-primary" id="competition_search" value="查询"/>
            </div>
        </div>


        <input type="hidden" value="${limit}" id="y">
        <div class="row">
            <div class="col-md-3 pull-right" style="margin-bottom: 15px;">
                <a href="/competition_editor" class="btn btn-primary" style="margin-left: -120px;margin-bottom: -50px;">新增比赛</a>
                <select class="form-control" id="limit">
                    <option value="10" id="ten">每页显示10条</option>
                    <option value="20" id="fifteen">每页显示20条</option>
                    <option value="50" id="twenty">每页显示50条</option>
                    <option value="100" id="twentyFive">每页显示100条</option>
                </select>
            </div>
        </div>

        <div class="table-responsive" >
            <table class="table table-bordered table-hover table-condensed" style="text-align: center;">

                <tr class="active" style="margin: 0px;padding: 0px;">
                    <th>编号</th>
                    <th width="7%">标题</th>
                    <th width="7%">副标题</th>
                    <th>比赛类型</th>
                    <th>报名费用</th>
                    <th>参赛人数</th>
                    <th>总报名人数</th>
                    <th>当前已比赛轮次</th>
                    <th>开赛时间</th>
                    <th>结束时间</th>
                    <th width="15%">奖励</th>
                    <th>状态</th>
                    <th>编辑</th>
                </tr>

                <c:forEach items="${competitionTempLists}" var="zhi">
                    <tr>
                        <td style="min-width:70px;margin: 0px;padding: 0px;">${zhi.id}</td>
                        <td style="width: 2%;">${zhi.title}</td>
                        <td>${zhi.name}</td>
                        <td>
                            <c:if test="${zhi.type==1}">
                                <span>大赛</span>
                            </c:if>
                            <c:if test="${zhi.type==2}">
                                <span>常规赛</span>
                            </c:if>
                        </td>
                        <td>${zhi.cost}</td>
                        <td>${zhi.user_limit}</td>
                        <td>${zhi.total_enrollment}</td>
                        <td>${zhi.round}</td>
                        <td>
                            <fmt:parseDate value="${zhi.start_time}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                            <fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>
                        </td>
                        <td>
                            <fmt:parseDate value="${zhi.end_time}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                            <fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>
                        </td>
                        <td>${zhi.rewards}</td>
                        <td>

                            <c:if test="${zhi.enable==0}">
                                <span class="competition_status${zhi.id}">已结束</span>
                            </c:if>
                            <c:if test="${zhi.enable==1}">
                                <c:choose>
                                    <c:when test="${nowDate<zhi.start_time}">
                                        <span class="competition_status${zhi.id}">未开始</span>
                                    </c:when>
                                    <c:when test="${nowDate <= zhi.end_time}">
                                        <c:if test="${nowDate >= zhi.start_time}">
                                            <span class="competition_status${zhi.id}">进行中</span>
                                        </c:if>
                                    </c:when>
                                    <c:when test="${nowDate>zhi.end_time}">
                                        <span class="competition_status${zhi.id}">已结束</span>
                                    </c:when>
                                </c:choose>
                            </c:if>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${zhi.enable==0}">
                                    <a href="javascript:;" class="btn btn-primary newEditor" alt="${zhi.id}">重新开赛</a>
                                </c:when>
                                <c:when test="${nowDate>zhi.end_time}">
                                    <a href="javascript:;" class="btn btn-primary newEditor" alt="${zhi.id}">重新开赛</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="javascript:;" class="btn btn-danger editor" alt="${zhi.id}">编辑</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <div class="col-md-4 col-md-offset-8" style="margin-top: 30px;">
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
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/assets/layer/common/layui/layui.js"></script>
<script type="text/javascript">
    layui.use(['jquery','layer','element','laypage'],function(){
        window.jQuery = window.$ = layui.jquery;
        window.layer = layui.layer;
    });

    function mofen(page){
        var total = document.getElementById("hidd").value;
        var proxy_date = document.getElementById("proxy_date").value;
        var proxy_date_end = document.getElementById("proxy_date_end").value;
        var limit = document.getElementById("y").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/competition_list?page="+page+"&proxy_date="+proxy_date+"&proxy_date_end="+proxy_date_end+"&limit="+limit;
    }


    $(function () {
        $(".editor").click(function () {
            var id = $(this).attr("alt");
            var competition_status = $(".competition_status"+id).text();
            window.location.href="/editor_agian?id="+id+"&competition_status="+competition_status;
        });
        $(".newEditor").click(function () {
            var id = $(this).attr("alt");
            var competition_status = $(".competition_status"+id).text();
            window.location.href="/new_editor?id="+id+"&competition_status="+competition_status;
        });

        $("#competition_search").click(function () {
            var proxy_date = $("#proxy_date").val();
            var proxy_date_end = $("#proxy_date_end").val();
            var limit = $("#y").val();

            if(proxy_date==""&&proxy_date_end!=""){
                alert("请输入开始时间");
            }else if(proxy_date!=""&&proxy_date_end==""){
                alert("请输入结束时间");
            }else{
                window.location.href="/competition_list?page=1&proxy_date="+proxy_date+"&proxy_date_end="+proxy_date_end+"&limit="+limit;
            }
        });



        $("#limit").change(function () {
            var limit = $(this).val();
            var proxy_date = $("#proxy_date").val();
            var proxy_date_end = $("#proxy_date_end").val();

            window.location.href="/competition_list?page=1&proxy_date="+proxy_date+"&proxy_date_end="+proxy_date_end+"&limit="+limit;
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
