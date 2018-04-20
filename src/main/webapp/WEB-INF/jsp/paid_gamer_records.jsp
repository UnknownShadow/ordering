<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>付费玩家记录</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" href="/assets/vendor/bootstrap/css/bootstrap.min.css">
    <!-- load css -->
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="/bs/datatables/css/jquery.dataTables.css">
    <script type="text/javascript" charset="utf8" src="/bs/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf8" src="/bs/datatables/js/jquery.dataTables.js"></script>
</head>
<style>
    a:hover{text-decoration : none;}
    .table tbody tr td{
        vertical-align: middle;
    }
</style>
<body>
<ul class="layui-nav" lay-filter="">
    <li class="layui-nav-item layui-this">
        <a href="/paid_records">付费玩家记录</a>
    </li>

    <li class="layui-nav-item"><a href="/integral_manage">玩家积分管理</a></li>

    <li class="layui-nav-item">
        <a href="/status_editor?page=1&limit=20">消息管理</a>
    </li>
    <li class="layui-nav-item">
        <a href="/competition_list?page=1&limit=20&proxy_date=&proxy_date_end=">比赛管理</a>
    </li>
    <li class="layui-nav-item">
        <a href="/reward_pending?page=1&start_date=&end_date=&user_id=">比赛发奖管理</a>
    </li>
    <li class="layui-nav-item"><a href="/log_output?page=1&limit=20&user_id=">玩家日志查询</a></li>
    <li class="layui-nav-item"><a href="/version_history?page=1">版本管理</a></li>
</ul>
<div style="padding: 20px;">
    <div style="margin-left: 20px;">
        <label>按ID查询：</label>
        <input type="text" id="user_id" class="form-control" style="width: 200px;" placeholder="请输入要查询的ID...">
    </div>
    <table id="table" class="display">
        <thead>
        <tr>
            <th>日期</th>
            <th>玩家ID</th>
            <th>玩家昵称</th>
            <th>充值渠道</th>
            <th>充值金额</th>
            <th>充值钻石数量</th>
            <th>当前钻石数量</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
    <%--<script type="text/javascript" src="js/constant.js"></script>--%>
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
                            lengthMenu: '<select class="form-control" style="position: absolute;width:200px;right: 20px;top: -45px;">' +
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
                        url: '/getCarrierByPage',
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
                    {
                        data: "created_at",//字段名
                    },
                    {
                        data: "user_id",//字段名
                    },
                    {
                        data : "nickname",//字段名
                        render : function(data,type, row, meta) {
                            return (data == null? "":data);
                        },
                        orderable: false
                    },
                    {
                        data : "desc",//字段名
                       /* render : function(data,type, row, meta) {
                            return (data == 1? "可以查发":data == 2?"可以链接":data == 3?"仅供查询":"不可用");
                        },*/
                    },
                    {
                        data : "doubPrice",//字段名
                    },
                    {
                        data: "recharge_diamond",//字段名
                        orderable : false//禁用排序
                    },
                    {
                        data: "diamond",//字段名
                        defaultContent:"",//无默认值
                        orderable : false//禁用排序
                    }
                ]
            })).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象

            //按回车键操作
            $("#user_id").keydown(function (event) {
                var keyCode = event.keyCode;
                if(keyCode == 13){
                    _table.ajax.reload();   //用于datatable 刷新 重新加载
                }
            });
        });

        var userManage = {
            getQueryCondition : function(data) {
                var param = {};
                //组装排序参数
                if (data.order&&data.order.length&&data.order[0]) {
                    switch (data.order[0].column) {
                        case 0:
                            param.orderColumn = "created_at";//数据库列名称
                            break;
                        case 1:
                            param.orderColumn = "user_id";//数据库列名称
                            break;
                        case 3:
                            param.orderColumn = "pay_type";//数据库列名称
                            break;
                        case 4:
                            param.orderColumn = "price";//数据库列名称
                            break;
                        default:
                            param.orderColumn = "created_at";//数据库列名称
                            break;
                    }
                    //排序方式asc或者desc
                    param.orderDir = data.order[0].dir;
                }
                //组装分页参数
               /* parseInt(data.start);    //50、100
                data.length;             //50、50   */
                param.user_id = $("#user_id").val();    //查询条件
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

