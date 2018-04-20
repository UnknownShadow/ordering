<%--
  Created by IntelliJ IDEA.
  User: juunew
  Date: 2017/6/21
  Time: 12:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>聚牛网络|公告</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <!-- VENDOR CSS -->
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/vendor/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/vendor/linearicons/style.css">
    <!-- MAIN CSS -->
    <link rel="stylesheet" href="assets/css/main.css">
    <!-- FOR DEMO PURPOSES ONLY. You should remove this in your project -->
    <link rel="stylesheet" href="assets/css/demo.css">

    <link rel="apple-touch-icon" sizes="76x76" href="assets/img/apple-icon.png">
    <link rel="icon" type="image/png" sizes="96x96" href="assets/img/favicon.png">
    <link rel="stylesheet" type="text/css" href="bs/dist/DateTimePicker.css" />
    <script src="bs/js/jquery-1.11.2.min.js"></script>
    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/scripts/ajaxfileupload.js" type="text/javascript" charset="utf-8"></script>
    <script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
    <script src="assets/scripts/klorofil-common.js"></script>
    <script type="bs/dist/DateTimePicker.js"></script>
    <script src="assets/DatePicker/WdatePicker.js" ></script>
    <script src="assets/DatePicker/config.js" ></script>
    <script src="assets/DatePicker/lin.js"></script>
    <script src="assets/DatePicker/config.js" ></script>

    <script src="assets/DatePicker/WdatePicker.js" ></script>
    <script src="assets/DatePicker/config.js" ></script>

    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/WdatePicker.css">
    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/whyGreen/datepicker.css">

</head>
<script>
    function fun() {
        $.ajax({
            type:"post",
            url:"/control",
            success:function(msg) {
                var finances = "<a href='#finance' data-toggle='collapse' class='collapsed'><i class='lnr lnr-file-empty'></i> <span>财务管理</span> <i class='icon-submenu lnr lnr-chevron-left'></i></a>"
                    +"<div id='finance' class='collapse '>"
                    +"<ul class='nav'>"
                    +"<li><a href='/information_all'><i class='lnr lnr-dice'></i> <span>我的资料</span></a></li>"
                    +"<li><a href='/integral_withdrawals?page=1'><i class='lnr lnr-dice'></i> <span>积分提现审核</span></a></li>"
                    +"<li><a href='/auditing?page=1'><i class='lnr lnr-text-format'></i> <span>财务审核</span></a></li>"
                    +"<li><a href='/approval_record?page=1&proxy_date=&proxy_date_end=&send_id='><i class='lnr lnr-dice'></i> <span>审批记录</span></a></li>"
                    +"<li><a href='/financial_daily?page=1'><i class='lnr lnr-dice'></i> <span>财务日报</span></a></li>"
                    +"<li><a href='/consumption_details?page=1&start_date=&end_date='><i class='lnr lnr-dice'></i> <span>钻石消耗详情</span></a></li>"
                    +"<li><a href='/diamond_production?page=1&start_date=&end_date='><i class='lnr lnr-dice'></i> <span>钻石产生详情</span></a></li>"
                 /* +"<li><a href='/proceeds'><i class='lnr lnr-dice'></i> <span>代理充值统计</span></a></li>"
                    +"<li><a href='/proceeds'><i class='lnr lnr-dice'></i> <span>代理分发统计</span></a></li>"*/
                    +"</ul>"
                    +"</div>";

                var yunyin = "<a href='#subPages' data-toggle='collapse' aria-expanded='true' class='active'><i class='lnr lnr-file-empty'></i> <span>运营后台</span> <i class='icon-submenu lnr lnr-chevron-left'></i></a>"
                    +"<div id='subPages' class='collapse in' aria-expanded='true'>"
                    +"<ul class='nav'>"
                    +"<li><a href='/operation_daily?page=1'>&nbsp;&nbsp;运营日报</a></li>"
                    +"<li><a href='/paid_gamer_records?page=1&user_id=&start_date=&end_date='>&nbsp;&nbsp;付费玩家记录</a></li>"
                    +"<li><a href='/integral_withdrawals_operation?page=1'>&nbsp;&nbsp;积分提现审核</a></li>"
                    +"<li><a href='/information_all'>&nbsp;&nbsp;我的资料</a></li>"
                    +"<li><a href='/version_history?page=1'>&nbsp;&nbsp;查看历史版本</a></li>"
                    +"<li><a href='/status_editor?page=1'>&nbsp;&nbsp;消息列表</a></li>"
                    +"<li><a href='/notice_list?page=1'>&nbsp;&nbsp;公告列表</a></li>"
                    +"<li><a href='/competition_list?page=1&proxy_date=&proxy_date_end='>&nbsp;&nbsp;比赛列表</a></li>"

                    /*+"<li><a href='/prize_exchange'>兑奖信息列表</a></li>"
                    +"<li><a href='/prize_editor'>兑奖信息编辑</a></li>"*/
                    +"<li><a href='/reward_pending?page=1&start_date=&end_date=&user_id='>【待发奖】列表</a></li>"
                    +"<li><a href='/prize_details?page=1&proxy_date=&proxy_date_end=&user_id=&opt=1'>&nbsp;&nbsp;发奖历史</a></li>"
                    +"<li><a href='/log_output?page=1&user_id='>&nbsp;&nbsp;玩家日志查询</a></li>"
                    +"<li><a href='/proxy_log?page=1&user_id='>&nbsp;&nbsp;代理日志查询</a></li>"
                    +"<li><a href='/intent_proxy?page=1'>&nbsp;&nbsp;意向代理列表</a></li>"
                    +"</ul>"
                    +"</div>";

                var proxys = "<a href='#pro' data-toggle='collapse' class='collapsed'><i class='lnr lnr-file-empty'></i> <span>代理后台</span> <i class='icon-submenu lnr lnr-chevron-left'></i></a>"
                    +"<div id='pro' class='collapse '>"
                    +"<ul class='nav'>"
                    +"<li><a href='/information'><i class='lnr lnr-dice'></i> <span>我的资料</span></a></li>"
                    +"<li><a href='/child_proxy'><i class='lnr lnr-dice'></i> <span>我的子代理</span></a></li>"
                    +"<li><a href='/searches?proxy_date=&proxy_date_end=&compositor=1'><i class='lnr lnr-dice'></i> <span>子代理销售查询</span></a></li>"
                    +"<li><a href='/proxy'><i class='lnr lnr-dice'></i> <span>添加子代理</span></a></li>"
                    +"<li><a href='/sends'><i class='lnr lnr-dice'></i> <span>发送钻石</span></a></li>"
                    +"<li><a href='/search_record?page=1&user_id=&compositor=2&proxy_date=&proxy_date_end='><i class='lnr lnr-dice'></i> <span>记录查询</span></a></li>"
                    +"</ul>"
                    +"</div>";



                var s="<li><a href='/wechat_proxy_approval?page=1'>待审核代理</a></li>";
                var json = eval(msg);
                $.each(json,function(index,itmes) {
                    if (itmes.approvalFlag==1) {
                        s = "<li><a href='/wechat_proxy_approval?page=1'>待审核代理<span class='glyphicon glyphicon-info-sign' style='position:absolute;margin-top:8px;color: red;font-size: 12px;' aria-hidden='true'></span></a></li>";
                    }
                })

                var senior= "<a href='#users' data-toggle='collapse' class='collapsed'><i class='lnr lnr-file-empty'></i> <span>管理后台</span> <i class='icon-submenu lnr lnr-chevron-left'></i></a>"
                    +"<div id='users' class='collapse ' >"
                    +"<ul class='nav'>"
                    +"<li><a href='/information_all'>我的资料</a></li>"
                    +"<li><a href='/total_preview?page=1'>后台总览</a></li>"
                    +"<li><a href='/games'>后台权限管理</a></li>"
                    +"<li><a href='/proxy_preview?page=1&userId='>代理总览</a></li>"
                    +"<li><a href='/proxy_manage'>添加代理</a></li>"
                    +"<li><a href='/proxy_diamond_send'>钻石发送</a></li>"
                    +"<li><a href='/integral_withdrawals_admin?page=1'>积分提现审核</a></li>"
                    +"<li><a href='/integral_withdrawals_supervisor?page=1'>待我审核</a></li>"
                    +s
                    +"<li><a href='/wechat_approval_records?page=1'>代理审批记录</a></li>"
                    +"</ul>"
                    +"</div>";

                var admin= "<a href='#users' data-toggle='collapse' class='collapsed'><i class='lnr lnr-file-empty'></i> <span>管理后台</span> <i class='icon-submenu lnr lnr-chevron-left'></i></a>"
                    +"<div id='users' class='collapse ' >"
                    +"<ul class='nav'>"
                    +"<li><a href='/information_all'>我的资料</a></li>"
                    +"<li><a href='/proxy_preview?page=1&userId='>代理总览</a></li>"
                    +"<li><a href='/proxy_manage'>添加代理</a></li>"
                    +"<li><a href='/proxy_diamond_send'>钻石发送</a></li>"
                    +s
                    +"<li><a href='/wechat_approval_records?page=1'>代理审批记录</a></li>"
                    +"</ul>"
                    +"</div>";

                var operation= "<a href='#operations' data-toggle='collapse' class='collapsed'><i class='lnr lnr-file-empty'></i> <span>登录管理</span> <i class='icon-submenu lnr lnr-chevron-left'></i></a>"
                    +"<div id='operations' class='collapse'>"
                    +"<ul class='nav'>"
                    +"<li><a href='/log_check?page=1'>日志查询</a></li>"
                    +"<li><a href='/filtration'>关键词过滤</a></li>"
                    +"</ul>"
                    +"</div>";

                var json = eval(msg);
                $.each(json,function(index,itmes) {
                    if(itmes.role=="4"){
                        $("#finances").append(finances);
                        if(itmes.userStatus==2){
                            $("#proxys").append(proxys);
                        }
                    }else if(itmes.role=="3"){
                        $("#operation").append(operation);
                        $("#yunyin").append(yunyin);
                        $("#admin").append(admin);
                        if(itmes.userStatus==2){
                            $("#proxys").append(proxys);
                        }
                    }else if(itmes.role=="5"){
                        if(itmes.userStatus==2){
                            $("#proxys").append(proxys);
                        }
                    }else if(itmes.role=="1"){
                        $("#admin").append(senior);
                        $("#finances").append(finances);
                        $("#yunyin").append(yunyin);
                        $("#proxys").append(proxys);
                    }else if(itmes.role=="2"){
                        $("#admin").append(admin);
                        $("#yunyin").append(yunyin);
                        $("#proxys").append(proxys);
                    }
                });

            }
        });
    }
</script>

<body onload="fun()">
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
                    <li id="admin">

                    </li>
                    <li id="yunyin">

                    </li>

                    <li id="proxys">

                    </li>

                    <li id="finances">

                    </li>
                    <li id="operation">

                    </li>
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
                    <div class="panel">
                        <div class="panel-heading">

                            <div style="width:340px;margin-left: 100px; margin-bottom: 120px;">
                                <form action="#" method="post">
                                    <div class="form-group">
                                        <label>公告类型：</label><br>
                                        <select id="notice_type" class="form-control" onblur="var noti=document.getElementById('notice_type').value; if(noti=='1') {document.getElementById('normal1').setAttribute('selected','true');document.getElementById('pic1').removeAttribute('selected')}if(noti=='2') {document.getElementById('pic1').setAttribute('selected','true');document.getElementById('normal1').removeAttribute('selected');}">
                                            <option id="normal" value="1">普通公告</option>
                                            <option id="pic" value="2">仅图片</option>
                                            <%--<option id="picAndWords" value="3">图片&文字</option>--%>
                                        </select>
                                        <br>
                                    </div>


                                    <div id="normalBlocke" style="width: 100%;">
                                        <div class="form-group">
                                            <label>标题：</label><br>
                                            <input type="text" placeholder="请输入标题" class="form-control" id="title" maxlength="20" onkeyup="document.getElementById('title1').value=this.value" onblur="document.getElementById('title1').value=this.value"/><br>
                                        </div>
                                        <div class="form-group">
                                            <label>公告内容：</label><br>
                                            <textarea placeholder="建议输入112字以内" rows="5" cols="30" maxlength="300" class="form-control" id="content" onkeyup="document.getElementById('content1').value=this.value" onblur="document.getElementById('content1').value=this.value"></textarea><br>
                                        </div>
                                    </div>

                                    <div id="picBlock" style="width: 100%;display: none;">
                                        <div class="form-group">
                                            <label>图片上传：</label><br>
                                            <label class='btn btn-danger' for='fileUpload'>图片上传</label>
                                            <input type='file' accept='image/png,image/jpeg,image/gif,image/jpg' style='position:absolute;clip:rect(0 0 0 0);' onchange='uploadFile(this)'  name='file'  id='fileUpload' />

                                            <input type="button" style="margin-left: 15px" class="btn btn-primary" data-toggle="modal" data-target="#model" id="preview" value="图片上传预览">
                                        </div>
                                        <div class="form-group">
                                            <label>图片路径地址：</label><br>
                                            <input type='text' class="form-control path" readonly/>
                                        </div>
                                        <br>
                                    </div>


                                    <div class="form-group" id="jum">
                                        <label>深度跳转：</label><br>
                                        <select id="web_jumping" class="form-control">
                                            <option id="nones">无</option>
                                            <option value="游戏内" id="o">游戏内</option>
                                            <option value="游戏外" id="i">游戏外</option>
                                        </select><br>
                                    </div>
                                    <div class="form-group" style="display: none;" id="inners">
                                        <label>游戏内跳转：</label><br>
                                        <select id="gameInner" class="form-control">
                                            <option value="1">任务</option>
                                            <option value="2">宝箱</option>
                                            <option value="3">签到</option>
                                            <option value="4">比赛</option>
                                            <option value="5">个人设置</option>
                                            <option value="6">系统设置</option>
                                            <option value="7">鱼虾蟹</option>
                                            <option value="8">消息</option>
                                            <option value="9">比赛记录</option>
                                        </select><br>
                                    </div>
                                    <div class="form-group" style="display: none;" id="outs">
                                        <label>游戏外跳转：</label><br>
                                        <input type="text" placeholder="请输入url" class="form-control" id="url" onkeyup="document.getElementById('url1').value=this.value" onblur="document.getElementById('url1').value=this.value"/>
                                    </div>
                                    <div class="form-group">
                                        <label>位置：</label><br>
                                        <select id="location"  class="form-control">
                                            <option value="1" id="both">弹窗+跑马灯</option>
                                            <option value="2" id="lights">仅跑马灯</option>
                                            <option value="3" id="alerts">仅弹窗</option>
                                        </select><br>
                                    </div>
                                    <div class="form-group">
                                        <label>公告弹窗位置：</label><br>
                                        <select id="page_type"  class="form-control">
                                            <option value="0">主界面</option>
                                            <option value="1">充值界面</option>
                                            <option value="2">比赛界面</option>
                                        </select><br>
                                    </div>
                                    <div class="form-group">
                                        <label>是否要退出：</label><br>
                                        <select class="form-control" id="sign_out"  onblur="var signs=document.getElementById('sign_out').value; if(signs=='1') {document.getElementById('exit').setAttribute('selected','true');document.getElementById('noExit').removeAttribute('selected')}if(signs=='0') {document.getElementById('noExit').setAttribute('selected','true');document.getElementById('exit').removeAttribute('selected')}">
                                            <option value="0">不需退出游戏</option>
                                            <option value="1">查看后需要强退</option>
                                        </select><br>
                                    </div>
                                    <div class="form-group">
                                        <label>开始时间：</label><br>
                                        <input type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="set_date" readonly />
                                    </div>
                                    <div class="form-group">
                                        <label>结束时间：</label><br>
                                        <input type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="end_date" readonly />
                                    </div>

                                    <script>


                                        $(function () {
                                            $("#yu").click(function () {
                                                $("#end_date1").val($("#end_date").val());
                                                $("#set_date1").val($("#set_date").val());

                                                var notic = $("#notice_type").val();

                                                var page_type = $("#page_type").val();

                                                if(page_type==0){
                                                    $("#main").attr("selected","true");
                                                    $("#recha").removeAttr("selected");
                                                    $("#comp").removeAttr("selected");
                                                }else if(page_type==1){
                                                    $("#recha").attr("selected","true");
                                                    $("#main").removeAttr("selected");
                                                    $("#comp").removeAttr("selected");
                                                }else if(page_type==2){
                                                    $("#comp").attr("selected","true");
                                                    $("#recha").removeAttr("selected");
                                                    $("#main").removeAttr("selected");
                                                }


                                                var local=$('#location').val();
                                                if(local=='1') {
                                                    $('#alertAndLight').attr('selected','true');
                                                    $('#light').removeAttr('selected');
                                                    $('#alertes').removeAttr('selected')}
                                                if(local=='2') {
                                                    $('#light').attr('selected','true');
                                                    $('#alertAndLight').removeAttr('selected');
                                                    $('#alertes').removeAttr('selected')}
                                                if(local=='3') {
                                                    $('#alertes').attr('selected','true');
                                                    $('#alertAndLight').removeAttr('selected');
                                                    $('#light').removeAttr('selected')
                                                }



                                                var jumping=$('#web_jumping').val();
                                                if(jumping=='无'){
                                                    $('#non').attr('selected','true');
                                                    $('#gamein').removeAttr('selected');
                                                    $('#gameout').removeAttr('selected');
                                                }else if(jumping=='游戏内'){
                                                    $('#gamein').attr('selected','true');
                                                    $('#non').removeAttr('selected');
                                                    $('#gameout').removeAttr('selected');
                                                }else if(jumping=='游戏外'){
                                                    $('#gameout').attr('selected','true');
                                                    $('#gamein').removeAttr('selected');
                                                    $('#non').removeAttr('selected');
                                                }



                                                if(notic==1){
                                                    $("#picurl").hide();
                                                    $("#tit").show();
                                                    $("#cont").show();
                                                    $("#url1").val();
                                                }else{
                                                    $("#picurl").show();
                                                    $("#tit").hide();
                                                    $("#cont").hide();
                                                    $("#url1").val($("#url").val());
                                                }

                                                $(".path1").val($(".path").val());

                                                if($("#sign_out").val()=="1"){
                                                    $("#times1").val("999");
                                                }else{
                                                    $("#times1").val($("#times").val())
                                                }

                                                if($("#web_jumping").val()=="游戏内"){
                                                    $("#inners1").show();
                                                    $("#outs1").hide();
                                                }else if($("#web_jumping").val()=="游戏外"){
                                                    $("#outs1").show();
                                                    $("#inners1").hide();
                                                }else{
                                                    $("#inners1").hide();
                                                    $("#outs1").hide();
                                                }

                                                if($("#gameInner").val()=="1"){
                                                    $("#one").attr("selected","true");
                                                    $("#two").removeAttr("selected");
                                                    $("#three").removeAttr("selected");
                                                    $("#four").removeAttr("selected");
                                                    $("#five").removeAttr("selected");
                                                    $("#six").removeAttr("selected");
                                                    $("#seven").removeAttr("selected");
                                                    $("#eight").removeAttr("selected");
                                                    $("#nine").removeAttr("selected");
                                                }else if($("#gameInner").val()=="2"){
                                                    $("#two").attr("selected","true");
                                                    $("#one").removeAttr("selected");
                                                    $("#three").removeAttr("selected");
                                                    $("#four").removeAttr("selected");
                                                    $("#five").removeAttr("selected");
                                                    $("#six").removeAttr("selected");
                                                    $("#seven").removeAttr("selected");
                                                    $("#eight").removeAttr("selected");
                                                    $("#nine").removeAttr("selected");
                                                }else if($("#gameInner").val()=="3"){
                                                    $("#three").attr("selected","true");
                                                    $("#two").removeAttr("selected");
                                                    $("#one").removeAttr("selected");
                                                    $("#four").removeAttr("selected");
                                                    $("#five").removeAttr("selected");
                                                    $("#six").removeAttr("selected");
                                                    $("#seven").removeAttr("selected");
                                                    $("#eight").removeAttr("selected");
                                                    $("#nine").removeAttr("selected");
                                                }else if($("#gameInner").val()=="4"){
                                                    $("#four").attr("selected","true");
                                                    $("#two").removeAttr("selected");
                                                    $("#three").removeAttr("selected");
                                                    $("#one").removeAttr("selected");
                                                    $("#five").removeAttr("selected");
                                                    $("#six").removeAttr("selected");
                                                    $("#seven").removeAttr("selected");
                                                    $("#eight").removeAttr("selected");
                                                    $("#nine").removeAttr("selected");
                                                }else if($("#gameInner").val()=="5"){
                                                    $("#five").attr("selected","true");
                                                    $("#two").removeAttr("selected");
                                                    $("#three").removeAttr("selected");
                                                    $("#four").removeAttr("selected");
                                                    $("#one").removeAttr("selected");
                                                    $("#six").removeAttr("selected");
                                                    $("#seven").removeAttr("selected");
                                                    $("#eight").removeAttr("selected");
                                                    $("#nine").removeAttr("selected");
                                                }else if($("#gameInner").val()=="6"){
                                                    $("#six").attr("selected","true");
                                                    $("#two").removeAttr("selected");
                                                    $("#three").removeAttr("selected");
                                                    $("#four").removeAttr("selected");
                                                    $("#five").removeAttr("selected");
                                                    $("#one").removeAttr("selected");
                                                    $("#seven").removeAttr("selected");
                                                    $("#eight").removeAttr("selected");
                                                    $("#nine").removeAttr("selected");
                                                }else if($("#gameInner").val()=="7"){
                                                    $("#seven").attr("selected","true");
                                                    $("#two").removeAttr("selected");
                                                    $("#three").removeAttr("selected");
                                                    $("#four").removeAttr("selected");
                                                    $("#five").removeAttr("selected");
                                                    $("#six").removeAttr("selected");
                                                    $("#one").removeAttr("selected");
                                                    $("#eight").removeAttr("selected");
                                                    $("#nine").removeAttr("selected");
                                                }else if($("#gameInner").val()=="8"){
                                                    $("#eight").attr("selected","true");
                                                    $("#two").removeAttr("selected");
                                                    $("#three").removeAttr("selected");
                                                    $("#four").removeAttr("selected");
                                                    $("#five").removeAttr("selected");
                                                    $("#six").removeAttr("selected");
                                                    $("#seven").removeAttr("selected");
                                                    $("#one").removeAttr("selected");
                                                    $("#nine").removeAttr("selected");
                                                }else if($("#gameInner").val()=="9"){
                                                    $("#nine").attr("selected","true");
                                                    $("#two").removeAttr("selected");
                                                    $("#three").removeAttr("selected");
                                                    $("#four").removeAttr("selected");
                                                    $("#five").removeAttr("selected");
                                                    $("#six").removeAttr("selected");
                                                    $("#seven").removeAttr("selected");
                                                    $("#eight").removeAttr("selected");
                                                    $("#one").removeAttr("selected");
                                                }
                                            });
                                        });
                                    </script>

                                    <div class="form-group">
                                        <label>平台：</label><br>
                                        <select id="platform" class="form-control" onblur="var plat=document.getElementById('platform').value;if(plat=='all'){document.getElementById('alls').setAttribute('selected','true');document.getElementById('ioss').removeAttribute('selected');document.getElementById('androids').removeAttribute('selected');}if(plat=='IOS'){document.getElementById('ioss').setAttribute('selected','true');document.getElementById('alls').removeAttribute('selected');document.getElementById('androids').removeAttribute('selected');}if(plat=='Android'){document.getElementById('androids').setAttribute('selected','true');document.getElementById('ioss').removeAttribute('selected');document.getElementById('alls').removeAttribute('selected');}">
                                            <option>IOS</option>
                                            <option>Android</option>
                                            <option>全部</option>
                                        </select><br>
                                    </div>
                                    <div class="form-group">
                                        <label>应用版本：</label><br>
                                        <select id="verType" class="form-control">
                                            <option value="1">所有版本</option>
                                            <option value="2">输入版本</option>
                                        </select>
                                    </div>
                                    <br>
                                    <div class="form-group" style="display:none;" id="inVer">
                                        <label>输入版本：</label><br>
                                        <input type="text" placeholder="请输入版本号" class="form-control" id="version" onkeyup="document.getElementById('version1').value=this.value" onblur="document.getElementById('version1').value=this.value"/><br>
                                    </div>
                                    <script>
                                        $(function () {
                                            $("#verType").change(function(){
                                                var verType = $(this).val();
                                                if(verType == 2){
                                                    $("#inVer").show();
                                                    $("#version").focus();
                                                }else{
                                                    $("#inVer").hide();
                                                    $("#version").val("");
                                                }
                                            });
                                        });
                                    </script>
                                    <div class="form-group">
                                        <label>显示次数：</label><br>
                                        <input type="text" placeholder="请输入需要显示的次数" id="times" class="form-control" maxlength="3" onkeyup="var reg = /^[0-9]*$/; if(!reg.test(this.value)) this.value='';" onkeyup="document.getElementById('times1').value=this.value" onblur="document.getElementById('times1').value=this.value"/><br>
                                    </div>
                                    <div class="form-group">
                                        <input type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" id="yu" value="预览">
                                        <br>
                                    </div>
                                </form>
                            </div>



                            <%--预览--%>


                            <!-- Modal -->
                            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title" id="myModalLabel">预览</h4>
                                        </div>
                                        <div class="modal-body">

                                            <fieldset disabled>
                                                <div class="row" style="margin-left: 10%;">
                                                    <div class="col-md-10">
                                                        <div class="form-group">
                                                            <label>公告类型：</label><br>
                                                            <select id="notice_type1" class="form-control">
                                                                <option id="normal1" value="1">普通公告</option>
                                                                <option id="pic1" value="2">仅图片</option>
                                                                <%--<option id="picAndWords" value="3">图片&文字</option>--%>
                                                            </select>
                                                            <br>
                                                        </div>
                                                        <div class="form-group" id="tit">
                                                            <label>标题：</label><br>
                                                            <input type="text" placeholder="请输入标题" class="form-control" id="title1" maxlength="20"/><br>
                                                        </div>
                                                        <div class="form-group" id="cont">
                                                            <label>公告内容：</label><br>
                                                            <textarea placeholder="请输入公告内容" rows="5" cols="30" maxlength="120" class="form-control" id="content1"></textarea><br>
                                                        </div>
                                                        <div class="form-group" style="display: none;" id="picurl">
                                                            <label>图片路径地址：</label><br>
                                                            <input type='text' class="form-control path1" readonly/><br>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>深度跳转：</label><br>
                                                            <select id="web_jumping1" class="form-control">
                                                                <option id="non">无</option>
                                                                <option value="游戏内" id="gamein">游戏内</option>
                                                                <option value="游戏外" id="gameout">游戏外</option>
                                                            </select><br>
                                                        </div>
                                                        <div class="form-group" style="display: none;" id="inners1">
                                                            <label>游戏内跳转：</label><br>
                                                            <select id="gameInner1" class="form-control">
                                                                <option value="1" id="one">任务</option>
                                                                <option value="2" id="two">宝箱</option>
                                                                <option value="3" id="three">签到</option>
                                                                <option value="4" id="four">比赛</option>
                                                                <option value="5" id="five">个人设置</option>
                                                                <option value="6" id="six">系统设置</option>
                                                                <option value="7" id="seven">鱼虾蟹</option>
                                                                <option value="8" id="eight">消息</option>
                                                                <option value="9" id="nine">比赛记录</option>
                                                            </select><br>
                                                        </div>
                                                        <div class="form-group" style="display: none;" id="outs1">
                                                            <label>游戏外跳转：</label><br>
                                                            <input type="text" placeholder="请输入url" class="form-control" id="url1"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>位置：</label><br>
                                                            <select id="location1"  class="form-control">
                                                                <option value="1" id="alertAndLight">弹窗+跑马灯</option>
                                                                <option value="2" id="light">仅跑马灯</option>
                                                                <option value="3" id="alertes">仅弹窗</option>
                                                            </select><br>
                                                            <input type="hidden" id="hid1"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>公告弹窗位置：</label><br>
                                                            <select id="page_type1"  class="form-control">
                                                                <option value="0" id="main">主界面</option>
                                                                <option value="1" id="recha">充值界面</option>
                                                                <option value="2" id="comp">比赛界面</option>
                                                            </select><br>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>是否要退出：</label><br>
                                                            <select class="form-control" id="sign_out1">
                                                                <option value="0" id="noExit">不需退出游戏</option>
                                                                <option value="1" id="exit">查看后需要强退</option>
                                                            </select><br>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>开始时间：</label><br>
                                                            <input type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="set_date1" readonly/>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>结束时间：</label><br>
                                                            <input type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="end_date1" readonly/>
                                                        </div>

                                                        <div class="form-group">
                                                            <label>平台：</label><br>
                                                            <select id="platform1" class="form-control">
                                                                <option id="ioss">IOS</option>
                                                                <option id="androids">Android</option>
                                                                <option id="alls">全部</option>
                                                            </select><br>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>应用版本：</label><br>
                                                            <input type="text" placeholder="请输入版本号" class="form-control" id="version1"/><br>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>显示次数：</label><br>
                                                            <input type="text" placeholder="请输入需要显示的次数" id="times1" class="form-control" maxlength="3" onkeyup="var reg = /^[0-9]*$/; if(!reg.test(this.value)) this.value='';" /><br>
                                                        </div>
                                                    </div>
                                                </div>
                                            </fieldset>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="button" class="btn btn-danger" value="发布" id="publish"/>
                                            <input type="button" class="btn btn-primary" data-dismiss="modal" value="再次编辑">
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <script>
                                $(function () {
                                    $("#preview").click(function () {
                                        var picUrl = $(".path").val();
                                        $("#picPre").attr("src",picUrl);
                                    });
                                });
                            </script>

                            <%--图片上传预览--%>
                            <!-- Modal -->
                            <div class="modal fade" id="model" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1">
                                <div class="modal-dialog" role="document" style="width: 850px;">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title" id="myModalLabel1">图片上传预览</h4>
                                        </div>
                                        <div class="modal-body">
                                            <label>注意：如果无法预览图片，请重新上传</label>

                                            <div style="position: relative;">
                                                <div style="position:absolute;">
                                                    <img src="" id="picPre" class="center-block" style="margin-left: 60px;margin-top: 120px;"/>
                                                </div>

                                                <img src="assets/img/recharge_back.jpg" style="z-index: -999;" class="center-block"/>
                                            </div>
                                            <%--<div style="position: relative;">
                                                <img src="assets/img/recharge_back.jpg" style="z-index: -999;">
                                                <img src="http://yxx.ufile.ucloud.com.cn/local/reward/20170913143829"  style="z-index: 999">
                                            </div>--%>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="button" class="btn btn-primary" data-dismiss="modal" value="退出预览">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

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


</body>
<script>
    function uploadFile(obj) {

        $.ajaxFileUpload({
            type:"post",
            url:"/fileUpload",
            secureuri:false,// 一般设置为false
            fileElementId:"fileUpload",// 文件上传表单的id <input type="file" id="fileUpload" name="file" />
            dataType:"text/html",
            data:{'id':'1'},
            success:function(msg){
                if(msg=='<pre style="word-wrap: break-word; white-space: pre-wrap;">0</pre>'){
                    alert("上传错误");
                }else if(msg=='0'){
                    alert("上传错误");
                }else{
                    alert("上传成功！");

                    var msgs = msg.replace(/<pre style=\"word-wrap: break-word; white-space: pre-wrap;\">/g,"");
                    msgs = msgs.replace(/<\/pre>/g,"");
                    //$(".path").attr("src",msgs);
                    $(".path").val(msgs);
                }
            },
            error:function(data){
                console.log("服务器异常");
            }
        });
        return false;
    }



    var changeFlag=false;
    //标识文本框值是否改变，为true，标识已变
    $(document).ready(function(){
        //文本框值改变即触发
        $("#title").change(function(){
            changeFlag=true;
        });
        $("#content").change(function(){
            changeFlag=true;
        });
    });

    //离开页面时保存文档
    window.onbeforeunload = function() {
        if(changeFlag ==true){
            return "页面值已经修改，是否要保存?";
        }
    }




    $(function () {
        $("#notice_type").change(function () {
            var notice_type = $("#notice_type").val();
            if(notice_type=="1"){
                $("#normalBlocke").show();
                $("#picBlock").hide();
                $(".path").val("");

                $("#both").show();
                $("#lights").show();
                $("#alerts").show();

                $("#outs").hide();
                $("#url").val("");
                $("#nones").attr("selected","true");

                $("#both").attr("selected","true");
                $("#alerts").removeAttr("selected");
                $("#lights").removeAttr("selected");

            }else if(notice_type=="2"){
                $("#normalBlocke").hide();
                $("#picBlock").show();
                $("#content").val("");
                $("#title").val("");

                $("#i").attr("selected","true");
                $("#outs").show();
                $("#url").val("weixin://");

                $("#both").hide();
                $("#lights").hide();
                $("#alerts").attr("selected","true");
                $("#lights").removeAttr("selected");
                $("#both").removeAttr("selected");

            }/*else if(notice_type=="3"){
                $("#normalBlocke").show();
                $("#picBlock").show();
            }*/
        });




        var jump="无";
        var url="0";
        $("#web_jumping").change(function () {
            if($(this).val()=="游戏内"){
                $("#inners").css("display","block");
                $("#outs").css("display","none");
            }else  if($(this).val()=="游戏外"){
                $("#inners").css("display","none");
                $("#outs").css("display","block");
            }else{
                $("#inners").css("display","none");
                $("#outs").css("display","none");
            }
            jump = $(this).val();
        });


        $("#sign_out").change(function () {
            if($(this).val()==1){
                $("#times").val("999");
                $("#times").attr("readonly","readonly");
            }else if($(this).val()==0){
                $("#times").val("");
                $("#times").removeAttr("readonly");
            }
        })



       // var data = JSON.parse();

       // <!--存为草稿-->

        $("#manuscript").click(function (){
            var title = $("#title").val();
            var content = $("#content").val();
            var end_date = $("#end_date").val();
            var start_date = $("#set_date").val();
            var version = $("#version").val();
            var location = $("#location").val();
            var sign_out = $("#sign_out").val();
            var platform = $("#platform").val();
            var times = $("#times").val();
            url = $("#url").val();
            if(jump=="游戏内"){
                url = $("#gameInner").val();
            }

            var r=confirm("是否存为草稿?");
            if (r==true){
                /*window.location.href="/pub?status=0&title="+title+"&content="+content+"&start_date="+start_date+"&end_date="+end_date
                    +"&location="+location+"&platform="+platform+"&version="+version+"&sign_out="+sign_out+"&times="+times+"&url="+url;
            */}else{
                return false;
            }
        });

        $("#publish").click(function () {

            var title = $("#title").val();
            var content = $("#content").val();
            var end_date = $("#end_date").val();
            var start_date = $("#set_date").val();
            var version = $("#version").val();
            var location = $("#location").val();
            var sign_out = $("#sign_out").val();
            var platform = $("#platform").val();
            var times = $("#times").val();
            var page_type = $("#page_type").val();
            var verType = $("#verType").val();


            url = $("#url").val();

            if(jump=="游戏内"){
               url = $("#gameInner").val();
            }else if(url==""){
                url="0";
            }
            /*content = content.replace(/r|\n/g,"<b>");*/

            var notice_type = $("#notice_type").val();
            var imgurl = $(".path").val();

            if(notice_type==1 && title==""){
                alert("请输入标题！");
            }else if(notice_type==1 && content==""){
                alert("请输入内容！");
            }else if(notice_type==1 && jump=="游戏外" && url==""){
                alert("请输入游戏外跳转url！");
            }else if(notice_type==2 && imgurl==""){
                alert("请上传图片");
            }else if(start_date==""){
                alert("请输入开始时间！");
            }else if(end_date==""){
                alert("请输入结束时间！");
            }else if(version=="" && verType == 2){
                alert("请输入版本号");
            }else if(times==""){
                alert("请输入显示次数");
            }else{
                content = content.replace(/r|\n/g,"<b>");

               /* txt.value=txt.value.replace(/<br\/>/g,"\r\n搜索");*/

                var r=confirm("确定发布此公告?");
                if (r==true){

                    window.location.href="/pub?status=1&title="+title+"&content="+content+"&start_date="+start_date+"&end_date="+end_date
                        +"&location="+location+"&platform="+platform+"&version="+version+"&sign_out="+sign_out+"&times="+times+"&url="+url+"&imgurl="+imgurl+"&page_type="+page_type;
                }else{
                    return false;
                }

            }
        });

    });
</script>

</html>
