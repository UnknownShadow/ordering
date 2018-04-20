<html lang="en">
<head>
    <title>聚牛网络|新增用户统计</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <script type="text/javascript" src="/bs/highcharts/highcharts.js"></script>
    <script src="/bs/js/jquery-1.11.2.min.js"></script>

    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="bs/ecalendar/Ecalendar.jquery.min.js" ></script>
    <link type="text/css" rel="stylesheet" href="/bs/ecalendar/style.css">

    <!-- load css -->
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/bootstrap/css/bootstrap.css" media="all">
</head>
<style> a:hover{text-decoration : none;} </style>
<body>
    <ul class="layui-nav" lay-filter="">
        <li class="layui-nav-item"><a href="/operation_daily?page=1&limit=20">运营日报</a></li>
        <li class="layui-nav-item layui-this">
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
                <dd style="padding-top: 5px;"><a href="#">活跃统计</a></dd>
                <dd style="padding-top: 5px;"><a href="/active_details?page=1&limit=20&chooseDate=">活跃详情</a></dd>
            </dl>
        </li>
        <li class="layui-nav-item">
            <a href="javascript:;">付费统计</a>
            <dl class="layui-nav-child" style="margin-top: -20px;"> <!-- 二级菜单 -->
                <#--<dd style="padding-top: 5px;"><a href="#">付费统计</a></dd>-->
                <dd style="padding-top: 5px;"><a href="/payment_details?page=1&limit=20&chooseDate=">付费详情</a></dd>
            </dl>
        </li>
        <li class="layui-nav-item">
            <a href="javascript:;">流失统计</a>
            <dl class="layui-nav-child" style="margin-top: -20px;"> <!-- 二级菜单 -->
                <#--<dd style="padding-top: 5px;"><a href="#">流失统计</a></dd>-->
                <dd style="padding-top: 5px;"><a href="/flow_away_details?page=1&limit=20&chooseDate=">流失详情</a></dd>
            </dl>
        </li>
        <li class="layui-nav-item">
            <a href="javascript:;">房间统计</a>
            <dl class="layui-nav-child" style="margin-top: -20px;"> <!-- 二级菜单 -->
                <#--<dd style="padding-top: 5px;"><a href="#">房间统计</a></dd>-->
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
</body>
<script type="text/javascript" src="/assets/layer/common/layui/layui.js"></script>
<script type="text/javascript">
    $(function () {
        layui.use(['jquery','layer','element','laypage'],function(){
            window.jQuery = window.$ = layui.jquery;
            window.layer = layui.layer;
        });
    });
</script>
</html>
