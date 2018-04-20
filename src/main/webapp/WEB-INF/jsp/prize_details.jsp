<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>潮尚玩|已完成奖品详情</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>

    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>

    <script src="assets/DatePicker/WdatePicker.js" ></script>
    <script src="assets/DatePicker/config.js" ></script>
    <script src="assets/DatePicker/lin.js"></script>
    <script src="assets/DatePicker/config.js" ></script>
    <script src="assets/DatePicker/WdatePicker.js" ></script>
    <script src="assets/DatePicker/config.js" ></script>
    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/WdatePicker.css">
    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/whyGreen/datepicker.css">

    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
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
            <a href="/paid_records">付费玩家记录</a>
        </li>
        <li class="layui-nav-item">
            <a href="/status_editor?page=1&limit=20">消息管理</a>
        </li>
        <li class="layui-nav-item">
            <a href="/competition_list?page=1&limit=20&proxy_date=&proxy_date_end=">比赛管理</a>
        </li>
        <li class="layui-nav-item layui-this">
            <a href="/reward_pending?page=1&start_date=&end_date=&user_id=">比赛发奖管理</a>
        </li>
        <li class="layui-nav-item"><a href="/log_output?page=1&limit=20&user_id=">玩家日志查询</a></li>
        <li class="layui-nav-item"><a href="/version_history?page=1">版本管理</a></li>
    </ul>


    <div style="padding: 20px;">
        <a href="/reward_pending?page=1&start_date=&end_date=&user_id=" class="btn btn-primary" style="position:absolute;right:280px;margin-top: 0px;">
            返回
        </a>

        <br/>
        <div class="row" style="margin-top: 20px;">
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

            <div class="col-md-3">
                <span style="margin-left: 14px;">
                    <label>ID：</label>
                    <input type="text" class="form-control" id="user_id" placeholder="请输入ID..." value="${user_id}"/>
                </span>
            </div>

            <div class="col-md-3">
                <span style="margin-left: 14px;">
                    <label>筛选：</label>
                    <select class="form-control" id="opt">
                        <option id="op1" value="1">全部</option>
                        <option id="op2" value="2">实物</option>
                        <option id="op3" value="3">钻石</option>
                    </select>
                </span>
            </div>

        </div>
        <div class="row" style="margin-top: 15px;">
            <div class="col-md-2 col-md-offset-4">
                <input type="button" class="btn btn-primary" id="inquiry" value="查询"/>
            </div>
        </div>

        <input type="hidden" value="${opt}" id="op"/>

        <div class="table-responsive" style="margin-top: 15px;">
            <table class="table table-hover">
                <tr class="active">
                    <th>日期时间</th>
                    <th>比赛标题</th>
                    <th>比赛轮次</th>
                    <th>奖励名称</th>
                    <th>奖励类型</th>
                    <th>获奖人昵称</th>
                    <th>获奖人ID</th>
                    <th>获奖人电话</th>
                    <th>收件人</th>
                    <th>地址</th>
                    <th>当前状态</th>
                    <th>确认人</th>
                </tr>
                <c:forEach items="${addressSendeds }" var="zhi">
                    <tr>
                        <td>
                            <fmt:parseDate value="${zhi.created_at}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                            <fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>
                        </td>
                        <td>${zhi.title}</td>
                        <td>${zhi.round}</td>
                        <td>${zhi.gift_title}</td>
                        <td>
                            <c:if test="${zhi.address!=''}">
                                实物
                            </c:if>
                            <c:if test="${zhi.address==''}">
                                钻石
                            </c:if>
                        </td>
                        <td>${zhi.nickname}</td>
                        <td>${zhi.user_id}</td>
                        <td>${zhi.phone}</td>
                        <td>${zhi.name}</td>
                        <td>${zhi.address}</td>
                        <td>
                            <c:if test="${zhi.status==1}">
                                <span style="color: darkred">已发货</span>
                            </c:if>
                            <c:if test="${zhi.status==4}">
                                <span style="color: #ff0b00">已关闭发货</span>
                            </c:if>
                        </td>
                        <td>${zhi.confirm_content}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
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
                        <input type="hidden" value="${total}" id="hidd"/>
                    </td>
                    <td>&nbsp;&nbsp;&nbsp;</td>
                    <td>共 ${count} 条记录</td>
                </tr>
            </table>
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
        var proxy_date = document.getElementById("proxy_date").value;
        var proxy_date_end = document.getElementById("proxy_date_end").value;
        var user_id = document.getElementById("user_id").value;
        var opt = document.getElementById("op").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/prize_details?page="+page+"&proxy_date="+proxy_date+"&proxy_date_end="+proxy_date_end+"&user_id="+user_id+"&opt="+opt;
    }

    $(function () {

        var op = $("#op").val();
        if(op=="1"){
            $("#op1").attr("selected","true");
        }else if(op=="2"){
            $("#op2").attr("selected","true");
        }else if(op=="3"){
            $("#op3").attr("selected","true");
        }

        $("#inquiry").click(function () {
            var proxy_date = $("#proxy_date").val();
            var proxy_date_end = $("#proxy_date_end").val();
            var user_id = $("#user_id").val();
            var opt = $("#opt").val();


            if(proxy_date!="" && proxy_date_end==""){
                alert("请选择结束时间！");
            }else if(proxy_date=="" && proxy_date_end!=""){
                alert("请选择开始时间！");
            }else{
                window.location.href="/prize_details?page=1&proxy_date="+proxy_date+"&proxy_date_end="+proxy_date_end+"&user_id="+user_id+"&opt="+opt;
            }
        });
    });

</script>

</html>
