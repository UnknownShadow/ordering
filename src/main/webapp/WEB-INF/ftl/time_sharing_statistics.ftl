<html lang="en">
<head>
    <title>聚牛网络|新增用户统计</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <#include "highcharts_file.ftl">
    <#include "header.ftl">
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="bs/ecalendar/Ecalendar.jquery.min.js" ></script>
    <link type="text/css" rel="stylesheet" href="bs/ecalendar/style.css">
</head>

<!-- 菜单js文件 -->
<script src="/bs/pagejs/index.js"  type="text/javascript"  charset="UTF-8"></script>
<script>
    $(function () {
        menu('operationManagement','timeStatistics');
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

                    <div class="panel" >
                        <div class="panel-heading" style="margin-bottom: 100px;">
                            <nav class="navbar navbar-default" role="navigation" style="margin: 0px;padding: 0px;">
                                <div class="container-fluid">
                                    <div class="navbar-header">
                                        <a class="navbar-brand" href="#">分时折线图统计</a>
                                    </div>
                                    <div>
                                        <ul class="nav navbar-nav">
                                            <li class="active"><a href="/time_sharing_statistics">新增用户</a></li>
                                            <li><a href="/active_user">活跃用户数量</a></li>
                                            <li><a href="/paid_count">付费笔数</a></li>
                                            <li><a href="/pay_amount">付费金额</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </nav>
                            <script>
                                var chart;
                                $(function(){
                                    var op = {
                                        chart: {
                                            renderTo: 'div1',
                                            defaultSeriesType: 'line',
                                            //                    margin: [50, 150, 60, 80]
                                        },
                                        credits:{
                                            enabled:false
                                        },
                                        title: {
                                            text: '用户新增情况统计',
                                        },
                                        subtitle: {
                                            text: '数据来源: 潮尚玩',
                                        },
                                        xAxis: {
                                            type:"datetime",
                                            dateTimeLabelFormats: {
                                                day: '%H:%M'
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
                                        /*legend: {
                                            align: 'left',
                                            verticalAlign: 'bottom',
                                            y: 40,
                                            floating: true,
                                            borderWidth: 0
                                        },*/
                                        tooltip: {
                                            formatter: function () {
                                                return '<b>' + this.series.name + '</b><br/>' +
                                                        Highcharts.dateFormat('%H:%M:%S', this.x) + '<br/>' +
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
                                                pointInterval: 1440000,
                                                pointStart: Date.UTC(2018, 1,09,0,0,0),
                                                pointEnd:Date.UTC(2018,1,09,23,59,59),
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
                                        data:{"date":""},
                                        url: '/testCases',
                                        success: function(msg){
                                            var jsons = eval(msg);
                                            $.each(jsons,function(idx,item){
                                                op.series[idx]=item;
                                            });
                                            chart = new Highcharts.Chart(op);
                                        }
                                    });

                                    //---------------------日期选择--------------
                                    $("#ECalendar_date").ECalendar({
                                        type:"date",   //模式，time: 带时间选择; date: 不带时间选择;
                                        stamp : false,   //是否转成时间戳，默认true;
                                        format:"yyyy-mm-dd",   //时间格式 默认 yyyy-mm-dd hh:ii;
                                        skin:3,   //皮肤颜色，默认随机，可选值：0-8,或者直接标注颜色值;
                                        callback:function(v,e){
                                            if($(".ECalendarBox").css('display')=="none"){
                                                var mydate = new Date();
                                                var str = "" + mydate.getFullYear() + "-";
                                                str += (mydate.getMonth()+1) + "-";
                                                str += mydate.getDate();
                                                //alert("获取的js日期："+str);
                                                if(v!=str){
                                                    $.ajax({
                                                        type: 'post',
                                                        dataType:'json',
                                                        contentType:'application/x-www-form-urlencoded;charset=utf-8',
                                                        data:{"date":v},
                                                        url: '/testCases',
                                                        success: function(msg){
                                                            var jsons = eval(msg);
                                                            $.each(jsons,function(idx,item){
                                                                op.series[idx]=item;
                                                            });
                                                            chart = new Highcharts.Chart(op);
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });
                                });
                            </script>

                            <div id="div1" style="margin-top: 50px;"> </div>


                            <div style="width: 100%;text-align: center;">
                                <div class="calendarWarp">
                                    <label class='btn btn-primary' style="padding-left: 20px;padding-right: 20px;" for='ECalendar_date'>对比时段</label>
                                    <input type='text' style='position:absolute;clip:rect(0 0 0 0);' name="date" class='ECalendar' id="ECalendar_date" />
                                </div>
                            </div>
                            <hr>

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
</html>
