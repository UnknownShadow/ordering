<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <li class="layui-nav-item">
            <a href="/competition_list?page=1&limit=20&proxy_date=&proxy_date_end=">比赛管理</a>
        </li>
        <li class="layui-nav-item layui-this">
            <a href="/reward_pending?page=1&start_date=&end_date=&user_id=">比赛发奖管理</a>
        </li>
        <li class="layui-nav-item"><a href="/log_output?page=1&limit=20&user_id=">玩家日志查询</a></li>
        <li class="layui-nav-item"><a href="/version_history?page=1">版本管理</a></li>
    </ul>

    <div style="padding:20px;">
        <div class="row" style="margin-top: 20px;">
            <div class="col-md-3">

                <span style="margin-left: 14px;">
                    <label>起始时间：</label>
                    <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:' yyyy-MM-dd HH:mm:ss'})" id="start_date" value="${start_date}" readonly>
                </span>
            </div>

            <div class="col-md-3">
                <span style="margin-left: 14px;">
                    <label>结束时间：</label>
                    <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:' yyyy-MM-dd HH:mm:ss'})" id="end_date" value="${end_date}" readonly>
                </span>
            </div>

            <div class="col-md-3">
                <span style="margin-left: 14px;">
                    <label>ID：</label>
                    <input type="text" class="form-control" id="user_id" placeholder="请输入ID..." value="${user_id}"/>
                </span>
            </div>
        </div>

        <div class="row">
            <div class="col-md-2 col-lg-offset-4">
                <div class="col-md-2" style="margin-top: 25px;">
                    <input type="button" class="btn btn-primary" id="inquiry" value="查询"/>
                </div>
            </div>
        </div>
        <br>

        <div class="row">
            <div class="col-md-12">
                <div class=" pull-right">
                    <a href='/prize_details?page=1&proxy_date=&proxy_date_end=&user_id=&opt=1' class="btn btn-primary">发奖历史</a>
                    <input type="button" class="btn btn-primary" id="passAll" value="批量已发货"/>
                    <input type="button" class="btn btn-danger" id="refuseAll" value="批量已完成"/>
                </div>
            </div>
        </div>




        <div class="table-responsive" style="margin-top: 15px;">
            <table class="table table-hover table-bordered table-striped"  id="list">
                <tr class="active">
                    <th>日期时间</th>
                    <th>比赛标题</th>
                    <th>奖品名称</th>
                    <th>奖励类型</th>
                    <th>获奖人昵称</th>
                    <th>获奖人ID</th>
                    <th>获奖人电话</th>
                    <th>收件人</th>
                    <th>地址</th>
                    <th>当前状态</th>
                    <th><input type="checkbox" id="all"><label for="all" style="cursor: pointer">全选</label></th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${rewardRecordPagings }" var="zhi">
                    <tr>
                        <td>
                            <fmt:parseDate value=" ${zhi.created_at}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                            <fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>
                        </td>
                        <td>${zhi.title}</td>
                        <td>${zhi.gift_title}</td>
                        <td>
                            <c:if test="${zhi.get_type==1}">
                                <span>线上自动发</span>
                            </c:if>
                            <c:if test="${zhi.get_type==2}">
                                <span>手工发</span>
                            </c:if>
                        </td>
                        <td>${zhi.nickname}</td>
                        <td>${zhi.user_id}</td>
                        <td>${zhi.phone}</td>
                        <td>${zhi.name}</td>
                        <td>${zhi.address}</td>
                        <td>
                            <c:if test="${zhi.status==0}">
                                <span>待处理</span>
                            </c:if>
                            <c:if test="${zhi.status==1}">
                                <span>已发放</span>
                            </c:if>
                            <c:if test="${zhi.status==2}">
                                <span>未填写</span>
                            </c:if>
                            <c:if test="${zhi.status==3}">
                                <span>待发货</span>
                            </c:if>
                            <c:if test="${zhi.status==4}">
                                <span>已完成</span>
                            </c:if>
                            <c:if test="${zhi.status==5}">
                                <span>无</span>
                            </c:if>
                            <c:if test="${zhi.status==6}">
                                <span>未开赛</span>
                            </c:if>
                        </td>
                        <td class="text-center">
                            <input type="checkbox" value="${zhi.id}" name="items">
                        </td>
                        <td>
                            <c:if test="${zhi.status==0}">
                                <input type="button" class="btn btn-primary" value="已发货" alt="${zhi.id}" onclick="sendGoods(this)" style="padding-left: 10px;padding-right: 10px;"/>
                                <input type="button" class="btn btn-danger" value="已完成" alt="${zhi.id}" onclick="sendGoodsClose(this)" style="padding-left: 10px;padding-right: 10px;"/>
                            </c:if>
                            <c:if test="${zhi.status==3}">
                                <input type="button" class="btn btn-primary" value="已发货" alt="${zhi.id}" onclick="sendGoods(this)" style="padding-left: 10px;padding-right: 10px;"/>
                                <input type="button" class="btn btn-danger" value="已完成" alt="${zhi.id}" onclick="sendGoodsClose(this)" style="padding-left: 10px;padding-right: 10px;"/>
                            </c:if>
                        </td>
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
                        <input type="hidden" value="${total}" id="hidde"/>
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

    $(function () {
        //全选或全不选
        $("#all").click(function(){
            if(this.checked){
                $("#list :checkbox").prop("checked", true);
            }else{
                $("#list :checkbox").prop("checked", false);
            }
        });

        //设置全选复选框
        $("#list :checkbox").click(function(){
            allchk();
        });

    });
    function allchk(){
        var chknum = $("#list :checkbox").size();//选项总个数
        var chk = 0;
        $("#list :checkbox").each(function () {
            if($(this).prop("checked")==true){
                chk++;
            }
        });
        if(chknum==chk){//全选
            $("#all").prop("checked",true);
        }else{//不全选
            $("#all").prop("checked",false);
        }
    }
    //以上是全选操作


    function mofen(page){
        var total = document.getElementById("hidde").value;
        var start_date = document.getElementById("start_date").value;
        var end_date = document.getElementById("end_date").value;
        var user_id = document.getElementById("user_id").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/reward_pending?page="+page+"&start_date="+start_date+"&end_date="+end_date+"&user_id="+user_id;
    }

    function sendGoods(send) {
        $.ajax({
            type:"post",
            url:"/send_goods",
            data:{"status":"1","id":send.alt},
            success:function(msg) {
                if(msg=="1"){
                    alert(" \"已发货\"  成功！");
                    window.location.href="/reward_pending?page=1&start_date=&end_date=&user_id=";
                }
            }
        });
    }

    function sendGoodsClose(send) {
        $.ajax({
            type:"post",
            url:"/send_goods",
            data:{"status":"4","id":send.alt},
            success:function(msg) {
                if(msg=="1"){
                    alert(" \"已完成\"  成功！");
                    window.location.href="/reward_pending?page=1&start_date=&end_date=&user_id=";
                }
            }
        });
    }

    $(function () {

        //批量已发货
        $("#passAll").click(function(){

            var arrayObj = new Array();

            $("[name=items]:checkbox:checked").each(function(){
                var val = $(this).val();
                arrayObj.push(val);
            });

            window.location.href="/batch_processing?arrayObj="+arrayObj+"&status=1"
        });

        //批量已完成
        $("#refuseAll").click(function(){
            var arrayObj = new Array();
            $("[name=items]:checkbox:checked").each(function(){
                var val = $(this).val();
                arrayObj.push(val);
            });

            window.location.href="/batch_processing?arrayObj="+arrayObj+"&status=4"
        });




        $("#inquiry").click(function () {
            var start_date = $("#start_date").val();
            var end_date = $("#end_date").val();
            var user_id = $("#user_id").val();
            if(start_date=="" && end_date!=""){
                alert("请输入开始时间");
            }else if(start_date!="" && end_date==""){
                alert("请输入结束时间");
            }else{
                window.location.href="/reward_pending?page=1&start_date="+start_date+"&end_date="+end_date+"&user_id="+user_id;
            }
        });
    });
</script>
</html>
