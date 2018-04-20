<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>新增统计图</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">

    <!-- load css -->
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">


    <link type="text/css" rel="stylesheet" href="/assets/DatePicker/skin/WdatePicker.css">
    <link type="text/css" rel="stylesheet" href="/assets/DatePicker/skin/whyGreen/datepicker.css">
    <script src="/assets/DatePicker/WdatePicker.js" ></script>
    <script src="/assets/DatePicker/config.js" ></script>
    <script src="/assets/DatePicker/lin.js"></script>



    <script type="text/javascript" src="/bs/highcharts/highcharts.js"></script>

    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="bs/ecalendar/Ecalendar.jquery.min.js" ></script>
    <link type="text/css" rel="stylesheet" href="/bs/ecalendar/style.css">

    <!-- load css -->
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/bootstrap/css/bootstrap.css" media="all">
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


        <li class="layui-nav-item layui-this">
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

    <div style="padding: 20px;">
        <script>
            var chart;
            $(function(){
                var op = {
                    chart: {
                        renderTo: 'div1',
                        defaultSeriesType: 'line',
                        //margin: [50, 150, 60, 80]
                    },
                    credits:{
                        enabled:false
                    },
                    title: {
                        text: '新增数据详情对比',
                    },
                    subtitle: {
                        text: '数据来源: 潮尚玩',
                    },
                    xAxis: {
                        type:"datetime",
                        dateTimeLabelFormats: {
                            day: '%m/%d',
                        }
                    },
                    yAxis: [{ // 第一个 Y 轴，放置在左边（默认在坐标）
                        title: {
                            text: '用户新增人数/个',
                            style: {
                                color: '#3E576F'
                            }
                        },
                        labels: {
                            align: 'left',
                            x: 3,
                            y: 16,
                            format: '{value:.,0f}'
                        },
                        showFirstLabel: false
                    }, {    // 第二个坐标轴，放置在右边
                        linkedTo: 0,
                        gridLineWidth: 0,
                        opposite: true,  // 通过此参数设置坐标轴显示在对立面
                        title: {
                            text: null
                        },
                        labels: {
                            align: 'right',
                            x: -3,
                            y: 16,
                            format: '{value:.,0f}'
                        },
                        showFirstLabel: false
                    }],
                   /* legend: {
                         align: 'right',
                         verticalAlign: 'bottom',
                         y: 40,
                         floating: true,
                         borderWidth: 0
                     },*/
                    tooltip: {
                        formatter: function () {
                            return '<b>' + this.series.name + '</b><br/>' +
                                Highcharts.dateFormat('%Y-%m-%d', this.x) + '<br/>' +
                                Highcharts.numberFormat(this.y, 0)+'个';
                        }
                    },
                    /*tooltip: {
                     shared: true,
                     crosshairs: true,
                     // 时间格式化字符
                     // 默认会根据当前的数据点间隔取对应的值
                     // 当前图表中数据点间隔为 1天，所以配置 day 值即可
                     dateTimeLabelFormats: {
                     day: '%Y-%m-%d'
                     }
                     },*/
                    plotOptions: {
                        series: {
                            cursor: 'pointer',
//                            pointInterval: 1440000,
                            point: {
                                events: {
                                    // 数据点点击事件
                                    // 其中 e 变量为事件对象，this 为当前数据点对象
                                    /*click: function (e) {
                                     $('.message').html( Highcharts.dateFormat('%Y-%m-%d', this.x) + ':<br/>  访问量：' +this.y );
                                     }*/
                                }
                            },
                            marker: {
                                lineWidth: 1
                            }
                        }
                    },
                    series: []
                };
                //-----------ajax-----------------
                $.ajax({
                    type: 'post',
                    dataType:'json',
                    contentType:'application/x-www-form-urlencoded;charset=utf-8',
                    data:{"start_date":"","end_date":""},
                    url: '/add_statistics_line',
                    success: function(msg){
                        var jsons = eval(msg);
                        $.each(jsons,function(idx,item){
                            op.series[idx]=item;
                        });
                        chart = new Highcharts.Chart(op);
                    }
                });

                //---------------------日期选择--------------
                $("#search").click(function () {

                    var start_date = $("#startDate").val();
                    var end_date = $("#endDate").val();

                    if(start_date != "" && end_date != ""){
                        $.ajax({
                            type: 'post',
                            dataType: 'json',
                            contentType: 'application/x-www-form-urlencoded;charset=utf-8',
                            data: {"start_date": start_date, "end_date": end_date},
                            url: '/add_statistics_line',
                            success: function (msg) {
                                var jsons = eval(msg);
                                $.each(jsons, function (idx, item) {
                                    op.series[idx] = item;
                                });
                                chart = new Highcharts.Chart(op);
                            }
                        });
                    }else{
                        alert("请选择时间段！");
                    }
                });
            });
        </script>

        <div class="col-sm-3">
            <input type="text" class="form-control" name="startDate" id="startDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')}'})" placeholder="请选择开始时间" style="width: 150px;" readonly/>
            <input type="text" class="form-control" name="endDate" id="endDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})" placeholder="请选择结束时间" style="width: 150px;margin-left: 170px;margin-top: -34px;" readonly/>
            <input type="button" class="btn btn-primary" value="查询" id="search" style="margin-left: 340px;margin-top: -56px;" />
        </div>

        <br>
        <hr>

        <div id="div1"></div>

    </div>
</body>
<script type="text/javascript" src="/assets/layer/common/layui/layui.js"></script>
<script type="text/javascript">
    layui.use(['jquery','layer','element','laypage'],function(){
        window.jQuery = window.$ = layui.jquery;
        window.layer = layui.layer;
    });



    $(function () {
        $("#detail_search").click(function () {
            var chooseDate = $("#chooseDate").val();
            var y = $("#y").val();
            if(chooseDate == ""){
                alert("请选择日期后再查询！");
            }else{
                window.location.href="/add_details?page=1&limit="+y+"&chooseDate="+chooseDate;
            }
        });
    });
</script>
</html>

