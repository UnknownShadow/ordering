<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>运营日报</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">

    <link rel="stylesheet" href="/assets/vendor/bootstrap/css/bootstrap.min.css">
    <!-- load css -->
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="/bs/datatables/css/jquery.dataTables.css">
    <script type="text/javascript" charset="utf8" src="/bs/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf8" src="/bs/datatables/js/jquery.dataTables.js"></script>
</head>
<style>a:hover{text-decoration: none;}</style>
<body>
    <ul class="layui-nav" lay-filter="">
        <li class="layui-nav-item layui-this"><a href="/operation_daily?page=1&limit=20">运营日报</a></li>
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
        <li class="layui-nav-item">
            <a href="javascript:;">房间统计</a>
            <dl class="layui-nav-child" style="margin-top: -20px;"> <!-- 二级菜单 -->
                <%--<dd style="padding-top: 5px;"><a href="#">房间统计</a></dd>--%>
                <dd style="padding-top: 5px;"><a href="/room_details?page=1&limit=20&chooseDate=">房间详情</a></dd>
            </dl>
        </li>
    </ul>

    <div style="padding: 20px;margin-top: 30px;">
        <table id="table" class="display">
            <thead>
            <tr>
                <th>日期</th>
                <th>当日新增(人数)</th>
                <th>当日新增(苹果人数)</th>
                <th>当日新增(安卓人数)</th>
                <th>当日活跃(人数)</th>
                <th>当日活跃(苹果人数)</th>
                <th>当日活跃(安卓人数)</th>
                <th>当日付费笔数</th>
                <th>当日付费人数</th>
                <th>当日付费金额</th>
                <th>连续3日活跃玩家人数</th>
                <th>连续5日活跃玩家人数</th>
                <th>上游开房次数</th>
                <!-- 以下为更改部分 -->
                <th>上游俱乐部开房数量</th>
                <%--<th>上游散客开房数量</th>--%>
                <th>麻将开房次数</th>
                <th>麻将俱乐部开房数量</th>
                <%--<th>麻将散客开房数量</th>--%>
                <th>激K开房次数</th>
                <th>激K俱乐部开房数量</th>
                <%--<th>激K散客开房数量</th>--%>
                <%--<th>鱼虾蟹开房次数</th>--%>
            </tr>
            </thead>
            <tbody></tbody>
        </table>

        <script type="text/javascript" src="/assets/layer/common/layui/layui.js"></script>
        <script type="text/javascript">
            layui.use(['jquery','layer','element','laypage'],function(){
                window.jQuery = window.$ = layui.jquery;
                window.layer = layui.layer;
            });

            $(function(){
                var CONSTANT = {
                    DATA_TABLES : {
                        DEFAULT_OPTION : { //DataTables初始化选项
                            language: {
                                lengthMenu: '<select class="form-control" style="position: absolute;width:200px;right: 20px;top: -40px;">' +
                                '<option value="10">每页显示10条</option>'+'<option value="20">每页显示20条</option>' +
                                '<option value="50">每页显示50条</option>' + '<option value="100">每页显示100条</option>' + '</select>',//左上角的分页大小显示。
                                "sProcessing":   "处理中...",
                                /*"sLengthMenu":   "每页显示 _MENU_ 条数据",*/
                                "sZeroRecords":  "没有匹配结果",
                                "sInfo":         "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 条记录。",
                                "sInfoEmpty":    "当前显示第 0 至 0 项，共 0 项",
                                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                                "sInfoPostFix":  "",
                                /* "sSearch":       "搜索:",*/
                                //"sUrl":          "",
                                "sEmptyTable":     "表中数据为空",
                                "sLoadingRecords": "载入中...",
                                "sInfoThousands":  ",",
                                "oPaginate": {
                                    "sFirst": "首页",
                                    "sPrevious": "上页",
                                    "sNext": "下页",
                                    "sLast": "末页",
                                }
                            },
                            autoWidth: true,   //禁用自动调整列宽
                            stripeClasses: ["odd", "even"],//为奇偶行加上样式，兼容不支持CSS伪类的场合
                            order: [],          //取消默认排序查询,否则复选框一列会出现小箭头
                            processing: false,  //隐藏加载提示,自行处理
                            serverSide: true,   //启用服务器端分页
                            searching: false,    //禁用原生搜索
                            //允许分页
                            paging: true,
                            pagingType: "full_numbers"
                        },
                        RENDER: {   //常用render可以抽取出来，如日期时间、头像等
                            ELLIPSIS: function (data, type, row, meta) {
                                data = data||"";
                                return '<span title="' + data + '">' + data + '</span>';
                            }
                        }
                    }
                };



                var $table = $("#table");
                var _table = $table.dataTable($.extend(true,{},CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
                    ajax : function(data, callback, settings) {
                        //封装请求参数
                        var param = userManage.getQueryCondition(data);
                        $.ajax({
                            type: "GET",
                            url: '/operationDailyByPage',
                            cache : false,  //禁用缓存
                            data: param,    //传入已封装的参数
                            dataType: "json",
                            success: function(result) {
                                //异常判断与处理
                                if (result.errorCode) {
                                    alert("查询失败");
                                    return;
                                }
                                //封装返回数据
                                var returnData = {};
                                returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                                returnData.recordsTotal = result.total;//总记录数
                                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                                returnData.data = result.pageData;



                                /*$('#table tbody').on('click','tr', function () {
                                    var dateT = $('td', this).eq(0).text();

                                    if($("tr td:nth-child(5)") === $('tr td', this).eq(0)){

                                        alert();
                                    }
                                    alert(dateT);

                                   /!*$("tr td:nth-child(5)").click(function (e) {
                                       alert(dateT);
                                   });*!/

                                   /!* $('#table tbody').on('click','tr td:nth-child(5)', function (e) {
                                        alert(dateT);
                                    });*!/
                                });*/

                                /*$('#table tbody').on('click','tr td:nth-child(5)', function (e) {
                                    //var dateT = $(this).eq(1).text();
                                    //var name = $(this).text();
                                    var name = $("tr td:nth-child(0)").eq(0).text();
                                    var next = $(this).siblings().eq(0).val();

                                   alert(next);
                                } );*/



                                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                                callback(returnData);
                            },
                            error: function(XMLHttpRequest, textStatus, errorThrown) {
                                alert("查询失败");
                            }
                        });
                    },

                    //绑定数据
                    columns: [
                        { data: "daily_date",}, //字段名
                        { data: "newly_added_num", },  //字段名
                        { data: "ios_new_size", },//字段名
                        { data: "android_new_size",},//字段名
                        {
                            data: "activity_num",
                           /* render: function (value, type, row) {
                                if(value !=null){
                                    return '<a href="#">'+value+'</a>';
                                }else{
                                    return "";
                                }
                            }*/
                        },
                        { data: "ios_activity_size", },//字段名
                        { data: "android_activity_size", },//字段名
                        { data: "pay_count", },//字段名
                        { data: "pay_num", },//字段名
                        { data: "pay_doubPrice", },//字段名
                        { data: "three_days", },//字段名
                        { data: "five_days", },//字段名
                        { data: "thirteen_count", },//字段名
                        { data: "thirteen_club_count", },//字段名

                        { data: "mahjong_count", },//字段名
                        { data: "mahjong_club_count", },//字段名

                        { data: "fivek_count",},//字段名
                        { data: "fivek_club_count", }//字段名
                    ]
                })).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象




            });

            var userManage = {
                getQueryCondition : function(data) {
                    var param = {};
                    //组装排序参数
                    if (data.order&&data.order.length&&data.order[0]) {
                        switch (data.order[0].column) {
                            case 0:
                                param.orderColumn = "daily_date";//数据库列名称
                                break;
                            case 1:
                                param.orderColumn = "newly_added_num";//数据库列名称
                                break;
                            case 2:
                                param.orderColumn = "ios_new_size";//数据库列名称
                                break;
                            case 3:
                                param.orderColumn = "android_new_size";//数据库列名称
                                break;
                            case 4:
                                param.orderColumn = "activity_num";//数据库列名称
                                break;
                            case 5:
                                param.orderColumn = "ios_activity_size";//数据库列名称
                                break;
                            case 6:
                                param.orderColumn = "android_activity_size";//数据库列名称
                                break;
                            case 7:
                                param.orderColumn = "pay_count";//数据库列名称
                                break;
                            case 8:
                                param.orderColumn = "pay_num";//数据库列名称
                                break;
                            case 9:
                                param.orderColumn = "pay_total";//数据库列名称
                                break;
                            case 10:
                                param.orderColumn = "three_days";//数据库列名称
                                break;
                            case 11:
                                param.orderColumn = "five_days";//数据库列名称
                                break;
                            case 12:
                                param.orderColumn = "thirteen_count";//数据库列名称
                                break;
                            case 13:
                                param.orderColumn = "thirteen_club_count";//数据库列名称
                                break;
                            case 14:
                                param.orderColumn = "mahjong_count";//数据库列名称
                                break;
                            case 15:
                                param.orderColumn = "mahjong_club_count";//数据库列名称
                                break;
                            case 16:
                                param.orderColumn = "fivek_count";//数据库列名称
                                break;
                            case 17:
                                param.orderColumn = "fivek_club_count";//数据库列名称
                                break;
                            default:
                                param.orderColumn = "daily_date";//数据库列名称
                                break;
                        }
                        //排序方式asc或者desc
                        param.orderDir = data.order[0].dir;
                    }

                    //组装分页参数
                    /* parseInt(data.start);    //50、100
                     data.length;             //50、50   */


                    param.startIndex = parseInt(data.start/data.length);
                    param.pageSize = data.length;
                    param.draw = data.draw;
                    return param;
                }
            };
        </script>
    </div>
</body>
</html>

