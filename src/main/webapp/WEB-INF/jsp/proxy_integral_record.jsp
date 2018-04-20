<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>聚牛网络|代理积分记录</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">


    <!-- DataTables CSS -->
    <link rel="stylesheet" href="bs/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="http://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css">
    <script src="/assets/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf8" src="/assets/jquery.dataTables.js"></script>

    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
</head>
<style> a:hover{text-decoration: none;} </style>
<body>

    <ul class="layui-nav" lay-filter="">
        <li class="layui-nav-item"><a href="/proxy_overview">代理总览</a></li>
        <li class="layui-nav-item"><a href="/proxy_manage">添加删除代理</a></li>
        <li class="layui-nav-item"><a href="/intent_proxy?page=1">意向代理</a></li>
        <li class="layui-nav-item">
            <a href="/integral_withdrawals_operation?page=1&limit=20">积分提现审核</a>
        </li>
        <li class="layui-nav-item layui-this">
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
        <div class="row">
            <div class="col-md-6">
                <form action="#" method="post" class="navbar-form navbar-left" name="formSer">
                    <div class="input-group">
                        <input type="text" id="userId" class="form-control searchbox" placeholder="请输入用户ID..." maxlength="10" value="${user_id}" >
                        <span class="input-group-btn"><input type="button" id="findLog" class="btn btn-primary" value="查询"></span>
                    </div>
                </form>
            </div>
        </div>

        <input type="hidden" value="${limit}" id="y">
        <div class="row">
            <div class="col-md-3 pull-right" style="margin-bottom: 15px;">
                <select class="form-control" id="limit">
                    <option value="10" id="ten">每页显示10条</option>
                    <option value="20" id="fifteen">每页显示20条</option>
                    <option value="50" id="twenty">每页显示50条</option>
                    <option value="100" id="twentyFive">每页显示100条</option>
                </select>
            </div>
        </div>

        <div class="box-body">
            <div class="table-responsive">
                <table class="table table-hover order_table" id="export-csv-table">
                    <thead>
                        <tr class="active success">
                            <th>时间</th>
                            <th>充值昵称（ID）</th>
                            <th>充值钻石</th>
                            <th>返回积分</th>
                            <th>返还昵称（ID）</th>
                            <th>是否冻结</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${rebates}" var="zhi" >
                            <tr>
                                <td>
                                    <fmt:parseDate value="${zhi.created_time}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                                    <fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>
                                </td>
                                <td>${zhi.child_user_id_nickname}（${zhi.child_user_id}） </td>
                                <td>${zhi.recharge_number}</td>
                                <td>${zhi.rebate_num}</td>
                                <td>${zhi.user_id_nickname}（${zhi.user_id}）</td>
                                <td>
                                    <c:if test="${zhi.frozen_flag==0}"><span style="color: #6d3353;">未冻结</span></c:if>
                                    <c:if test="${zhi.frozen_flag==1}"><span style="color: red;">已冻结</span></c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
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
<script type="text/javascript" src="/assets/layer/common/layui/layui.js"></script>
<script type="text/javascript">
    layui.use(['jquery','layer','element','laypage'],function(){
        window.jQuery = window.$ = layui.jquery;
        window.layer = layui.layer;
    });

    $(function () {
       /* $('.order_table').DataTable({
            bFilter : true,// 搜索栏
            serverSide: false,   //开启服务端分页传输数据
            autoWidth: false, //禁用自动调整列宽
            stripeClasses: ["odd", "even"], //为奇偶行加上样式，兼容不支持CSS伪类的场合
            processing: false, //隐藏加载提示,自行处理
            searching: false, //禁用原生搜索
            orderMulti: true, //启用多列排序
            order: [], //取消默认排序查询,否则复选框一列会出现小箭头
            renderer: "bootstrap", //渲染样式：Bootstrap和jquery-ui
            pagingType: "full_numbers", //分页样式：simple,simple_numbers,full,full_numbers

            //开启搜索框
            "searching": true,
            //允许分页
            "paging": true,
            //左下角信息 showing 1 to 7 of 7entries
            "info":true,
            //支持国际化，将search转为中文
            language: {
                lengthMenu: '<select class="form-control">' +
                '<option value="10">每页显示10条</option>'+'<option value="20">每页显示20条</option>' +
                '<option value="50">每页显示50条</option>' + '<option value="100">每页显示100条</option>' + '</select>',//左上角的分页大小显示。

                //<span class="label label-success">搜索：</span>
                "search": "在表格中搜索:",
                "sInfoEmpty": "当前显示第 0 至 0 项，共 0 项",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页",
                },
            },
            "columnDefs": [
                {
                    //targets指定列禁止排序功能
                    "orderable": false,
                    //"targets": [0,1,2]
                    "targets": [1]
                }
            ]
        });
*/


        //按回车键操作
        $("#userId").keydown(function (event) {
            var keyCode = event.keyCode;
            if(keyCode == 13){
                $("#findLog").click();
            }
        });
        $("#findLog").click(function () {
            var user_id = $("#userId").val();
            var limit = $("#y").val();
            var reg = /^[0-9]*$/;

            if(!reg.test(user_id)){
                alert("请输入正确的ID");
                $("#userId").val("");
            }else{
                formSer.action="/proxy_integral_record?page=1&user_id="+user_id+"&limit="+limit;
                formSer.submit();
            }
        });


        $("#limit").change(function () {
            var limit = $(this).val();
            var userId = $("#userId").val();
            window.location.href="/proxy_integral_record?page=1&user_id="+userId+"&limit="+limit;
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


    function mofen(page){
        var total = document.getElementById("hidd").value;
        var user_id = document.getElementById("userId").value;
        var limit = document.getElementById("y").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/proxy_integral_record?page="+page+"&user_id="+user_id+"&limit="+limit;
    }
</script>
</html>