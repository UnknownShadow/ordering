<html lang="en">
<head>
    <title>潮尚玩|马王活动配置</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="bs/ecalendar/Ecalendar.jquery.min.js" ></script>
    <link type="text/css" rel="stylesheet" href="/bs/ecalendar/style.css">
    <link rel="stylesheet" href="/assets/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
</head>
<style>a:hover{text-decoration: none;}</style>
<body>
    <div style="padding: 20px;margin-top: 60px;margin-bottom: 100px;width: 600px;margin-left: 80px;">
        <form action="#" method="post">
            <div style="width: 600px;">
                <div class="form-group calendarWarp">
                    <label>活动开始时间：</label><br>
                    <input type="text" class="form-control ECalendar" id="start_time" placeholder="请选择开始时间..." readonly />
                </div>
                <div class="form-group calendarWarp" style="position: absolute;margin-left: 20px;">
                    <label>活动结束时间：</label><br>
                    <input type="text" class="form-control ECalendar"  id="end_time" placeholder="请选择结束时间..." readonly />
                </div>
            </div>
            <br>
            <script>
                $("#start_time").ECalendar({
                    type:"time",   //模式，time: 带时间选择; date: 不带时间选择;
                    offset:[0,2],   //弹框手动偏移量;
                    step:1,   //选择时间分钟的精确度;
                    stamp : false,   //是否转成时间戳，默认true;
                    format:"yyyy-mm-dd hh:ii",   //时间格式 默认 yyyy-mm-dd hh:ii;
                    skin:3,   //皮肤颜色，默认随机，可选值：0-8,或者直接标注颜色值;
                });
                $("#end_time").ECalendar({
                    type:"time",   //模式，time: 带时间选择; date: 不带时间选择;
                    offset:[0,2],   //弹框手动偏移量;
                    step:1,   //选择时间分钟的精确度;
                    stamp : false,   //是否转成时间戳，默认true;
                    format:"yyyy-mm-dd hh:ii",   //时间格式 默认 yyyy-mm-dd hh:ii;
                    skin:3,   //皮肤颜色，默认随机，可选值：0-8,或者直接标注颜色值;
                });
            </script>
            <div class="form-group" style="width: 600px;">
                <label>活动说明：</label><br>
                <textarea placeholder="建议输入112字以内" rows="5" cols="30" maxlength="200" class="form-control" id="desc"></textarea><br>
            </div>

            <label style="font-size: 16px;font-weight: 600;">中奖配置</label><br>
            <br>

            <div class="input-group" style="width:240px;">
                <span class="input-group-addon">小于</span>
                <input type="text" id="min" class="form-control" aria-label="Amount (to the nearest dollar)" maxlength="1">
                <span class="input-group-addon">马，无奖励。</span>
            </div>
            <br><br>
            <label style="">
                中
                <input type='text' maxlength='3' id='startOne' style='width: 35px;height: 25px; margin-left: 10px;'/>
                <span style='margin: 8px;'>~</span>
                <input type='text' id='endOne' maxlength='3' style='width: 35px;height: 25px; margin-left: 10px;margin-right: 10px;'/>
                马，
                金额
                <input type='text' maxlength='3' id='cashStartOne' style='width: 35px;height: 25px; margin-left: 10px;'/>
                <span style='margin: 8px;'>~</span>
                <input type='text' id='cashEndOne' maxlength='3' style='width: 35px;height: 25px; margin-left: 10px;margin-right: 10px;'/>
                元。
            </label>
            <br><br>
            <label style="">
                中
                <input type='text' maxlength='3' id='startTwo' style='width: 35px;height: 25px; margin-left: 10px;'/>
                <span style='margin: 8px;'>~</span>
                <input type='text' id='endTwo' maxlength='3' style='width: 35px;height: 25px; margin-left: 10px;margin-right: 10px;'/>
                马，
                金额
                <input type='text' maxlength='3' id='cashStartTwo' style='width: 35px;height: 25px; margin-left: 10px;'/>
                <span style='margin: 8px;'>~</span>
                <input type='text' id='cashStartTwo' maxlength='3' style='width: 35px;height: 25px; margin-left: 10px;margin-right: 10px;'/>
                元。
            </label>


            <script>
                /*$(function () {
                    $("#startOne").change(function () {
                        if(parseInt($("#startOne").val())<=parseInt($("#endOne").val())){
                            this.value='';
                            alert("输入的名次应该大于前面的名次");
                        }
                    });
                    $("#endOne").change(function () {
                        if(parseInt($("#startOne").val())>parseInt($("#endOne").val())){
                            this.value='';
                            alert("输入的名次应该大于前面的名次");
                        }
                    });
                });*/
            </script>
            <br><br>
            <input type="button" id="save" class="btn btn-primary" value="保存"/>
        </form>
    </div>
</body>
<script>
    $(function () {
        $("#save").click(function () {
            var desc = $("#desc").val();
            var start_time = $("#start_time").val();
            var end_time = $("#end_time").val();
            var min = $("#min").val();

            if(start_time==""){
                alert("请选择开始时间");
            }else if(end_time==""){
                alert("请选择结束时间");
            }else if(start_time>end_time){
                alert("开始时间必须小于结束时间");
            }else if(desc == ""){
                desc="";
            }else if(min == ""){
                min="";
            }else{
                var startOne = $("#startOne");
                var endOne = $("#endOne");
                var startTwo = $("#startTwo");
                var endTwoOne = $("#endTwoOne");
                var startOne = $("#startOne");
                var startOne = $("#startOne");
                var startOne = $("#startOne");
                if(startOne==""){

                }

                alert("保存");
            }



        });
    });
</script>
</html>
