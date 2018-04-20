<html lang="en">
<head>
    <title>聚牛网络|运营</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <script type="text/javascript" src="bs/highcharts/highcharts.js"></script>
    <script src="bs/js/jquery-1.11.2.min.js"></script>
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">

    <script src="assets/DatePicker/WdatePicker.js" ></script>
    <script src="assets/DatePicker/config.js" ></script>
    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/WdatePicker.css">
    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/whyGreen/datepicker.css">
    <script src="assets/DatePicker/lin.js"></script>
    <script src="assets/DatePicker/config.js" ></script>
</head>
<body>
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">分时统计折线图</a>
            </div>
            <div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">iOS</a></li>
                    <li><a href="#">SVN</a></li>
                    <li><a href="#">SVN</a></li>
                    <li><a href="#">SVN</a></li>
                    <li><a href="#">SVN</a></li>
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
                //--------------------
            $("#set_date").click(function () {
                var date = $("#start_date").val();
                $.ajax({
                    type: 'post',
                    dataType:'json',
                    contentType:'application/x-www-form-urlencoded;charset=utf-8',
                    data:{"date":date},
                    url: '/testCases',
                    success: function(msg){
                        var jsons = eval(msg);
                        $.each(jsons,function(idx,item){
                            op.series[idx]=item;
                        });
                        chart = new Highcharts.Chart(op);
                    }
                });
            });
        });
    </script>

    <div id="div1" style="margin-top: 50px;"> </div>
    <div style="margin-top: 50px;border:1px gainsboro;">
        <!-- WdatePicker({el:$dp.$('gz')}) -->
        <input type="hidden" id="hide">
        <label class='btn btn-danger' for='set_date'>对比时段</label>
        <input type='text'  style='position:absolute;clip:rect(0 0 0 0);'  id='set_date' />

        <#--<input type="text" class="form-control" placeholder="请选择日期..." maxlength="8" id="start_date" onclick="WdatePicker({el:$dp.$('gz')});" style="width: 150px;" readonly>
        <a href="#" class="btn btn-primary" id="set_date">对比时段</a>-->
    </div>
    <hr>

    <input type="text" id="PriceDate"name="PriceDate" class="Wdate" onFocus="WdatePicker({ onpicking: cDayFunc})" />
    <script>
        function cDayFunc(dp) {//p.cal.getDateStr("yyyy-MM-dd");
            var date = dp.cal.getDateStr("yyyy-MM-dd");
            alert($("#PriceDate").val());
        }

        function testAsync(){
            var temp;
            $.ajax({
                async: false,
                type : "GET",
                url : 'tet.php',
                complete: function(msg){
                    alert('complete');
                },
                success : function(data) {
                    alert('success');
                    temp=data;
                }
            });
            alert(temp+'   end');
        }
    </script>
</body>
<script>

    $(function () {
        var time = "";

        $("#time").click(function () {
            WdatePicker({ eCont: 'MyData', onpicked: function (dp) {
                time = dp.cal.getDateStr("yyyy-MM-dd");
                alert(time);
                }
            });

        });
        /*$("#set_date").click(function () {
             WdatePicker({el:$dp.$('gz')});
             var date = $(this).val();
             $(this).val("对比时段");
            alert(date);
        });*/
    });
</script>
</html>