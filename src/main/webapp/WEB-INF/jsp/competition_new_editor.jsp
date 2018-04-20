<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <title>聚牛网络|比赛新增与编辑</title>
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

    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
    <script src="assets/scripts/klorofil-common.js"></script>
    <script src="assets/scripts/ajaxfileupload.js" type="text/javascript" charset="utf-8"></script>
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
                   /* +"<li><a href='/prize_exchange'>兑奖信息列表</a></li>"
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
                    +"<div id='users' class='collapse '>"
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
                    +"<div id='users'   class='collapse '>"
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
            <a href="index.html"><img src="assets/img/1.png" class="img-responsive" style="height: 60px;margin-left: 20px;padding-right:23px;padding-left:23px;"></a>
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
                        <div class="panel-heading" style="margin-bottom: 260px;margin-left: 3%;">
                            <div>

                                <input type="hidden" value="${competitionTemplat.seq }" id="seqs"/>
                                <input type="hidden" value="${byRewardsNum }" id="rewardsNum"/>
                                <input type="hidden" value="${competitionTemplat.type }" id="type"/>
                                <input type="hidden" value="${competition_status }" id="competition_status"/>
                                <h4><label style="margin-bottom: 30px;">比赛新增与编辑</label></h4>
                                <div class="row">
                                    <div class="col-md-3">
                                        <span>
                                            <label>排序：</label>
                                            <select class="form-control" id="seq">
                                                <option id="one">1</option>
                                                <option id="two">2</option>
                                                <option id="three">3</option>
                                                <option id="four">4</option>
                                                <option id="five">5</option>
                                                <option id="six">6</option>
                                                <option id="seven">7</option>
                                                <option id="eight">8</option>
                                                <option id="nine">9</option>
                                                <option id="ten">10</option>
                                            </select>
                                        </span>
                                    </div>
                                    <script>
                                        $(function () {
                                            var seqs = $("#seqs").val();
                                            if(seqs=="1"){
                                                $("#one").attr("selected","true");
                                                $("#two").removeAttr("selected");
                                                $("#three").removeAttr("selected");
                                                $("#four").removeAttr("selected");
                                                $("#five").removeAttr("selected");
                                                $("#six").removeAttr("selected");
                                                $("#seven").removeAttr("selected");
                                                $("#eight").removeAttr("selected");
                                                $("#nine").removeAttr("selected");
                                                $("#ten").removeAttr("selected");
                                            }else if(seqs=="2"){
                                                $("#two").attr("selected","true");
                                                $("#one").removeAttr("selected");
                                                $("#three").removeAttr("selected");
                                                $("#four").removeAttr("selected");
                                                $("#five").removeAttr("selected");
                                                $("#six").removeAttr("selected");
                                                $("#seven").removeAttr("selected");
                                                $("#eight").removeAttr("selected");
                                                $("#nine").removeAttr("selected");
                                                $("#ten").removeAttr("selected");
                                            }else if(seqs=="3"){
                                                $("#three").attr("selected","true");
                                                $("#one").removeAttr("selected");
                                                $("#two").removeAttr("selected");
                                                $("#four").removeAttr("selected");
                                                $("#five").removeAttr("selected");
                                                $("#six").removeAttr("selected");
                                                $("#seven").removeAttr("selected");
                                                $("#eight").removeAttr("selected");
                                                $("#nine").removeAttr("selected");
                                                $("#ten").removeAttr("selected");
                                            }else if(seqs=="4"){
                                                $("#four").attr("selected","true");
                                                $("#one").removeAttr("selected");
                                                $("#three").removeAttr("selected");
                                                $("#two").removeAttr("selected");
                                                $("#five").removeAttr("selected");
                                                $("#six").removeAttr("selected");
                                                $("#seven").removeAttr("selected");
                                                $("#eight").removeAttr("selected");
                                                $("#nine").removeAttr("selected");
                                                $("#ten").removeAttr("selected");
                                            }else if(seqs=="5"){
                                                $("#five").attr("selected","true");
                                                $("#one").removeAttr("selected");
                                                $("#three").removeAttr("selected");
                                                $("#four").removeAttr("selected");
                                                $("#two").removeAttr("selected");
                                                $("#six").removeAttr("selected");
                                                $("#seven").removeAttr("selected");
                                                $("#eight").removeAttr("selected");
                                                $("#nine").removeAttr("selected");
                                                $("#ten").removeAttr("selected");
                                            }else if(seqs=="6"){
                                                $("#six").attr("selected","true");
                                                $("#one").removeAttr("selected");
                                                $("#three").removeAttr("selected");
                                                $("#four").removeAttr("selected");
                                                $("#five").removeAttr("selected");
                                                $("#two").removeAttr("selected");
                                                $("#seven").removeAttr("selected");
                                                $("#eight").removeAttr("selected");
                                                $("#nine").removeAttr("selected");
                                                $("#ten").removeAttr("selected");
                                            }else if(seqs=="7"){
                                                $("#seven").attr("selected","true");
                                                $("#one").removeAttr("selected");
                                                $("#three").removeAttr("selected");
                                                $("#four").removeAttr("selected");
                                                $("#five").removeAttr("selected");
                                                $("#six").removeAttr("selected");
                                                $("#two").removeAttr("selected");
                                                $("#eight").removeAttr("selected");
                                                $("#nine").removeAttr("selected");
                                                $("#ten").removeAttr("selected");
                                            }else if(seqs=="8"){
                                                $("#eight").attr("selected","true");
                                                $("#one").removeAttr("selected");
                                                $("#three").removeAttr("selected");
                                                $("#four").removeAttr("selected");
                                                $("#five").removeAttr("selected");
                                                $("#six").removeAttr("selected");
                                                $("#seven").removeAttr("selected");
                                                $("#two").removeAttr("selected");
                                                $("#nine").removeAttr("selected");
                                                $("#ten").removeAttr("selected");
                                            }else if(seqs=="9"){
                                                $("#nine").attr("selected","true");
                                                $("#one").removeAttr("selected");
                                                $("#three").removeAttr("selected");
                                                $("#four").removeAttr("selected");
                                                $("#five").removeAttr("selected");
                                                $("#six").removeAttr("selected");
                                                $("#seven").removeAttr("selected");
                                                $("#eight").removeAttr("selected");
                                                $("#two").removeAttr("selected");
                                                $("#ten").removeAttr("selected");
                                            }else{
                                                $("#ten").attr("selected","true");
                                                $("#one").removeAttr("selected");
                                                $("#three").removeAttr("selected");
                                                $("#four").removeAttr("selected");
                                                $("#five").removeAttr("selected");
                                                $("#six").removeAttr("selected");
                                                $("#seven").removeAttr("selected");
                                                $("#eight").removeAttr("selected");
                                                $("#nine").removeAttr("selected");
                                                $("#two").removeAttr("selected");
                                            }
                                        });
                                    </script>

                                    <input type="hidden" id="competition_template_id" value="${competitionTemplat.id}"/>

                                    <div class="col-md-3">
                                        <span>
                                            <label>类型：</label>
                                            <select class="form-control" id="competition_type">
                                                <option value="1" id="single">单次比赛</option>
                                                <option value="2" id="loop">常规赛</option>
                                                <%--<option value="3">带海选大奖赛</option>--%>
                                            </select>
                                        </span>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-3">
                                        <span>
                                            <label>标题：</label>
                                            <input type="text" class="form-control" placeholder="建议输入10字以内" maxlength="20" id="title" value="${competitionTemplat.title}"/>
                                        </span>
                                    </div>
                                    <div class="col-md-3">
                                        <span>
                                            <label>副标题：</label>
                                            <input type="text" placeholder="建议输入10字以内" maxlength="20" class="form-control" id="subheading" value="${competitionTemplat.name}" />
                                        </span>
                                    </div>
                                </div>
                                <br>

                                <div class="row">
                                    <div class="col-md-3">
                                        <span>
                                            <label>报名费用：</label>
                                            <input type="text" placeholder="请输入数量，默认为钻石"  value="${competitionTemplat.num}" class="form-control" id="cost" maxlength="7"/>
                                        </span>
                                    </div>

                                    <div class="col-md-3">
                                        <span>
                                            <label>费用类型</label>
                                            <select class="form-control" id="cost_type">
                                                <option>钻石</option>
                                            </select>
                                        </span>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-3">
                                        <span style="margin-left: 14px;">
                                            <label>参赛人数：</label>
                                            <input type="text" class="form-control" placeholder="请输入参赛人数" value="${competitionTemplat.user_limit}" maxlength="7" id="participant_num">
                                        </span>
                                    </div>
                                    <div class="col-md-3">
                                        <span style="margin-left: 14px;">
                                            <label>平台：</label>
                                            <select class="form-control" id="platform"  onblur="var plat=document.getElementById('platform').value;if(plat=='全平台'){document.getElementById('alls').setAttribute('selected','true');document.getElementById('ioss').removeAttribute('selected');document.getElementById('androids').removeAttribute('selected');}if(plat=='IOS'){document.getElementById('ioss').setAttribute('selected','true');document.getElementById('alls').removeAttribute('selected');document.getElementById('androids').removeAttribute('selected');}if(plat=='Android'){document.getElementById('androids').setAttribute('selected','true');document.getElementById('ioss').removeAttribute('selected');document.getElementById('alls').removeAttribute('selected');}">
                                                <option>IOS</option>
                                                <option>Android</option>
                                                <option>全平台</option>
                                            </select>
                                        </span>
                                    </div>
                                </div>
                                <br>


                                <div class="row">
                                    <div class="col-md-3">
                                        <span style="margin-left: 14px;">
                                            <label>展示时间：</label>
                                            <fmt:parseDate value="${competitionTemplat.show_time}" pattern="yyyy-MM-dd HH:mm:ss" var="show_time"/>
                                            <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="show_time" value="<fmt:formatDate value='${show_time}' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly>
                                        </span>
                                    </div>
                                    <div class="col-md-3">
                                        <span style="margin-left: 14px;">
                                            <label>报名时间：</label>
                                            <fmt:parseDate value="${competitionTemplat.sign_start_time}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                                            <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="registration_time" readonly  value="<fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
                                        </span>
                                    </div>
                                </div>
                                <br>

                                <div class="row">
                                    <div class="col-md-3">
                                            <span style="margin-left: 14px;">
                                                <label>开赛时间：</label>
                                                <fmt:parseDate value="${competitionTemplat.start_time}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                                                <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="start_date" readonly  value="<fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
                                            </span>
                                    </div>

                                    <div class="col-md-3">
                                        <span style="margin-left: 14px;">
                                            <label>结束时间：</label>
                                            <fmt:parseDate value="${competitionTemplat.end_time}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                                            <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="end_date" readonly  value="<fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
                                        </span>
                                    </div>
                                    <script>
                                        $(function () {
                                            $("#yu").click(function () {
                                                $("#registration_time1").val($("#registration_time").val());
                                                $("#start_date1").val($("#start_date").val());
                                                $("#end_date1").val($("#end_date").val());
                                                $("#show_time1").val($("#show_time").val());
                                                $("#title1").val($("#title").val());
                                                $("#subheading1").val($("#subheading").val());
                                                $("#rule1").val($("#rule").val());
                                                $("#cost1").val($("#cost").val());
                                                $("#participant_num1").val($("#participant_num").val());
                                                $("#password1").val($("#password").val());
                                                $("#picU1").val($("#picU").val());
                                                $("#round1").val($("#round").val());


                                                if($("#seq").val()=="1"){
                                                    $("#s1").attr("selected","true");
                                                    $("#s2").removeAttr("selected");
                                                    $("#s3").removeAttr("selected");
                                                    $("#s4").removeAttr("selected");
                                                    $("#s5").removeAttr("selected");
                                                    $("#s6").removeAttr("selected");
                                                    $("#s7").removeAttr("selected");
                                                    $("#s8").removeAttr("selected");
                                                    $("#s9").removeAttr("selected");
                                                    $("#s10").removeAttr("selected");
                                                }else if($("#seq").val()=="2"){
                                                    $("#s2").attr("selected","true");
                                                    $("#s1").removeAttr("selected");
                                                    $("#s3").removeAttr("selected");
                                                    $("#s4").removeAttr("selected");
                                                    $("#s5").removeAttr("selected");
                                                    $("#s6").removeAttr("selected");
                                                    $("#s7").removeAttr("selected");
                                                    $("#s8").removeAttr("selected");
                                                    $("#s9").removeAttr("selected");
                                                    $("#s10").removeAttr("selected");
                                                }else if($("#seq").val()=="3"){
                                                    $("#s3").attr("selected","true");
                                                    $("#s2").removeAttr("selected");
                                                    $("#s1").removeAttr("selected");
                                                    $("#s4").removeAttr("selected");
                                                    $("#s5").removeAttr("selected");
                                                    $("#s6").removeAttr("selected");
                                                    $("#s7").removeAttr("selected");
                                                    $("#s8").removeAttr("selected");
                                                    $("#s9").removeAttr("selected");
                                                    $("#s10").removeAttr("selected");
                                                }else if($("#seq").val()=="4"){
                                                    $("#s4").attr("selected","true");
                                                    $("#s2").removeAttr("selected");
                                                    $("#s3").removeAttr("selected");
                                                    $("#s1").removeAttr("selected");
                                                    $("#s5").removeAttr("selected");
                                                    $("#s6").removeAttr("selected");
                                                    $("#s7").removeAttr("selected");
                                                    $("#s8").removeAttr("selected");
                                                    $("#s9").removeAttr("selected");
                                                    $("#s10").removeAttr("selected");
                                                }else if($("#seq").val()=="5"){
                                                    $("#s5").attr("selected","true");
                                                    $("#s2").removeAttr("selected");
                                                    $("#s3").removeAttr("selected");
                                                    $("#s4").removeAttr("selected");
                                                    $("#s1").removeAttr("selected");
                                                    $("#s6").removeAttr("selected");
                                                    $("#s7").removeAttr("selected");
                                                    $("#s8").removeAttr("selected");
                                                    $("#s9").removeAttr("selected");
                                                    $("#s10").removeAttr("selected");
                                                }else if($("#seq").val()=="6"){
                                                    $("#s6").attr("selected","true");
                                                    $("#s2").removeAttr("selected");
                                                    $("#s3").removeAttr("selected");
                                                    $("#s4").removeAttr("selected");
                                                    $("#s1").removeAttr("selected");
                                                    $("#s5").removeAttr("selected");
                                                    $("#s7").removeAttr("selected");
                                                    $("#s8").removeAttr("selected");
                                                    $("#s9").removeAttr("selected");
                                                    $("#s10").removeAttr("selected");
                                                }else if($("#seq").val()=="7"){
                                                    $("#s7").attr("selected","true");
                                                    $("#s2").removeAttr("selected");
                                                    $("#s3").removeAttr("selected");
                                                    $("#s4").removeAttr("selected");
                                                    $("#s1").removeAttr("selected");
                                                    $("#s6").removeAttr("selected");
                                                    $("#s5").removeAttr("selected");
                                                    $("#s8").removeAttr("selected");
                                                    $("#s9").removeAttr("selected");
                                                    $("#s10").removeAttr("selected");
                                                }else if($("#seq").val()=="8"){
                                                    $("#s8").attr("selected","true");
                                                    $("#s2").removeAttr("selected");
                                                    $("#s3").removeAttr("selected");
                                                    $("#s4").removeAttr("selected");
                                                    $("#s1").removeAttr("selected");
                                                    $("#s6").removeAttr("selected");
                                                    $("#s7").removeAttr("selected");
                                                    $("#s5").removeAttr("selected");
                                                    $("#s9").removeAttr("selected");
                                                    $("#s10").removeAttr("selected");
                                                }else if($("#seq").val()=="9"){
                                                    $("#s9").attr("selected","true");
                                                    $("#s2").removeAttr("selected");
                                                    $("#s3").removeAttr("selected");
                                                    $("#s4").removeAttr("selected");
                                                    $("#s1").removeAttr("selected");
                                                    $("#s6").removeAttr("selected");
                                                    $("#s7").removeAttr("selected");
                                                    $("#s8").removeAttr("selected");
                                                    $("#s5").removeAttr("selected");
                                                    $("#s10").removeAttr("selected");
                                                }else if($("#seq").val()=="10"){
                                                    $("#s10").attr("selected","true");
                                                    $("#s2").removeAttr("selected");
                                                    $("#s3").removeAttr("selected");
                                                    $("#s4").removeAttr("selected");
                                                    $("#s1").removeAttr("selected");
                                                    $("#s6").removeAttr("selected");
                                                    $("#s7").removeAttr("selected");
                                                    $("#s8").removeAttr("selected");
                                                    $("#s9").removeAttr("selected");
                                                    $("#s5").removeAttr("selected");
                                                }
                                            });
                                        });
                                    </script>
                                </div>

                                <br>
                                <div class="row">
                                    <div class="col-md-6">
                                            <span style="margin-left: 14px;">
                                                <label>比赛详情图片：</label>
                                                <br>
                                                <span style="margin-left: 14px;">
                                                    <label class='btn btn-danger' for='Fupload'>图片上传</label>
                                                    <input type='file' accept='image/png,image/jpeg,image/gif,image/jpg' style='position:absolute;clip:rect(0 0 0 0);' onchange='Fupload(this)'  name='file'  id='Fupload' />

                                                    <input type="button" style="margin-left: 15px" class="btn btn-primary" data-toggle="modal" data-target="#model" id="preview" value="图片上传预览">
                                                </span>
                                                <div class="form-group">
                                                    <label>图片路径地址：</label><br>
                                                    <input type='text' class="form-control path" id="picU" value="${competitionTemplat.pic_url}" readonly/>
                                                </div>
                                                <br>
                                            </span>
                                    </div>
                                </div>
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
                                                        <img src="" id="picPre" class="center-block" style="margin-left: 75px;margin-top: 360px;"/>
                                                    </div>

                                                    <img src="assets/img/comp.jpg" style="z-index: -999;" class="center-block"/>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <input type="button" class="btn btn-primary" data-dismiss="modal" value="退出预览">
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <br>
                                <input type="hidden" value="${competitionTemplat.password}" id="pas">
                                <div class="row">
                                    <div class="col-md-3">
                                            <span style="margin-left: 14px;">
                                                <label>比赛密码：</label>
                                                <select class="form-control" id="pass">
                                                    <option value="0" id="no">无密码</option>
                                                    <option value="1" id="yes">需要密码</option>
                                                </select>
                                            </span>
                                    </div>
                                    <div class="col-md-3">
                                            <span style="margin-left: 14px;">
                                                <label>&nbsp;</label>
                                                <input type="text" class="form-control" placeholder="请输入密码" id="password" value="${competitionTemplat.password}" onkeyup="var reg = /^[0-9]*$/; if(!reg.test(this.value)) this.value='';" maxlength="6" disabled>
                                            </span>
                                    </div>
                                </div>
                                <script>
                                    $(function () {
                                        var pas = $("#pas").val();
                                        if(pas!=""){
                                            $("#password").removeAttr("disabled");
                                            $("#yes").attr("selected","true");
                                        }

                                        $("#pass").change(function () {
                                            var pass = $("#pass").val();
                                            if(pass==1){
                                                $("#password").removeAttr("disabled");
                                                $("#password").focus();
                                            }else{
                                                $("#password").val("");
                                                $("#password").attr("disabled","true");
                                            }
                                        });
                                    });
                                </script>

                                <br>

                                <div id="compTwo">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <label>计分规则：</label>
                                            <select id="settle" class="form-control">
                                                <option value="1" id="sheet">按张计算</option>
                                                <option value="2" id="score">30分</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div id="compOne">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <label>比赛轮次：</label>（默认为 3）
                                            <input type="text" class="form-control" value="3" id="round" maxlength="2"  onkeyup ="var reg = /^[0-9]*$/;if(!reg.test(this.value)) this.value='';">
                                        </div>
                                    </div>
                                </div>
                                <input type="hidden" value="${competitionTemplat.totalPeriod}" id="totalPeriod">
                                <input type="hidden" value="${competitionTemplat.settleType}" id="settleType">
                                <script>
                                    $(function () {

                                        var settleType = $("#settleType").val();
                                        var totalPeriod = $("#totalPeriod").val();

                                        if(settleType==1){

                                            $("#round").val(totalPeriod);
                                            $("#sheet").attr("selected","true");
                                            $("#score").removeAttr("selected");
                                            $("#compOne").show(0);
                                        }

                                        $("#settle").change(function () {
                                            var settle = $(this).val();
                                            if(settle==1){
                                                $("#compOne").show(0);
                                            }else{
                                                $("#compOne").hide(0);
                                            }
                                        });
                                    });
                                </script>

                                <br>
                                <div class="row">
                                    <div class="col-md-6">
                                        <span style="margin-left: 14px;">
                                            <label>比赛规则：</label>
                                            <textarea rows="5" cols="75" class="form-control" id="rule" maxlength="300">${competitionTemplat.rule}</textarea>
                                        </span>
                                    </div>
                                </div>
                            </div>

                            <script>
                                function Fupload(obj) {

                                    $.ajaxFileUpload({
                                        type:"post",
                                        url:"/fileUpload",
                                        secureuri:false,// 一般设置为false
                                        fileElementId:"Fupload",// 文件上传表单的id <input type="file" id="fileUpload" name="file" />
                                        dataType:"text/html",
                                        data:{'id':'1'},
                                        success:function(msg){
                                            if(msg=='<pre style="word-wrap: break-word; white-space: pre-wrap;">0</pre>'){
                                                alert("上传错误");
                                            }else if(msg=='0'){
                                                alert("上传错误");
                                            }else{

                                                var msgs = msg.replace(/<pre style=\"word-wrap: break-word; white-space: pre-wrap;\">/g,"");
                                                msgs = msgs.replace(/<pre>/g,"");
                                                msgs = msgs.replace(/<\/pre>/g,"");

                                                if(obj.value!=""){
                                                    alert("上传成功！");
                                                    $(".path").val(msgs);
                                                }else{
                                                    alert("上传失败，请重新上传");
                                                    $(".path").val("");
                                                }
                                            }
                                        },
                                        error:function(data){
                                            console.log("服务器异常");
                                        }
                                    });
                                    return false;
                                }
                                $(function () {
                                    $("#preview").click(function () {
                                        var picUrl = $(".path").val();
                                        $("#picPre").attr("src",picUrl);
                                    });
                                });
                            </script>


                            <br>
                            <div style="margin-top: 50px;">
                                <div style="margin-top: 50px;">
                                    <h4><label>比赛奖励</label></h4>
                                    <br>
                                    <div class="row" id="appe">

                                    </div>

                                    <br>
                                </div>

                                <br>

                                <div class="row" style="margin-top: 40px;margin-left: -40px;">
                                    <div class="col-md-6 col-lg-offset-2">
                                        <input type="button" value="添加名次" class="btn btn-primary" id="addRank"/>
                                        <input type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" id="yu" value="预览"  style="margin-left: 30px;">
                                    </div>
                                </div>
                                <br>
                                <br>


                                <%--预览--%>
                                <!-- Modal -->
                                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                                    <div class="modal-dialog" role="document" style="width: 700px;">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                <h4 class="modal-title" id="myModalLabel">预览</h4>
                                            </div>
                                            <div class="modal-body">

                                                <br>

                                                <div style="width:1000px;margin-left: 100px;" id="cont2">
                                                    <fieldset disabled>
                                                        <div class="row">
                                                            <div class="col-md-3">
                                                            <span>
                                                                <label>排序：</label>
                                                                <select class="form-control" id="seq1">
                                                                    <option id="s1">1</option>
                                                                    <option id="s2">2</option>
                                                                    <option id="s3">3</option>
                                                                    <option id="s4">4</option>
                                                                    <option id="s5">5</option>
                                                                    <option id="s6">6</option>
                                                                    <option id="s7">7</option>
                                                                    <option id="s8">8</option>
                                                                    <option id="s9">9</option>
                                                                    <option id="s10">10</option>
                                                                </select>
                                                            </span>
                                                            </div>

                                                            <div class="col-md-3">
                                                            <span>
                                                                <label>类型：</label>
                                                                <select class="form-control" id="competition_type1">
                                                                    <option value="1" >单次比赛</option>
                                                                    <option value="2" >常规赛</option>
                                                                </select>
                                                            </span>
                                                            </div>
                                                        </div>
                                                        <br>
                                                        <div class="row">
                                                            <div class="col-md-3">
                                                            <span>
                                                                <label>标题：</label>
                                                                <input type="text" class="form-control" placeholder="限输入10字以内" maxlength="10" id="title1"/>
                                                            </span>
                                                            </div>
                                                            <div class="col-md-3">
                                                            <span>
                                                                <label>副标题：</label>
                                                                <input type="text" placeholder="限输入10字以内" maxlength="10" class="form-control" id="subheading1"/>
                                                            </span>
                                                            </div>
                                                        </div>
                                                        <br>


                                                        <div class="row">
                                                            <div class="col-md-3">
                                                            <span>
                                                                <label>报名费用：</label>
                                                                <input type="text" placeholder="请输入数量，默认为钻石" class="form-control" id="cost1" onkeyup="var reg=/^[0-9]*$/; if(!reg.test(this.value))  this.value=''" maxlength="7"/>
                                                            </span>
                                                            </div>

                                                            <div class="col-md-3">
                                                            <span>
                                                                <label>费用类型</label>
                                                                <select class="form-control" id="cost_type1">
                                                                    <option>钻石</option>
                                                                    <option>金币</option>
                                                                </select>
                                                            </span>
                                                            </div>
                                                        </div>
                                                        <br>

                                                        <div class="row">
                                                            <div class="col-md-3">
                                                            <span style="margin-left: 14px;">
                                                                <label>参赛人数：</label>
                                                                <input type="text" class="form-control" placeholder="请输入参赛人数" maxlength="7" id="participant_num1" onkeyup="var reg=/^[0-9]*$/; if(!reg.test(this.value))  this.value=''">
                                                            </span>
                                                            </div>

                                                            <div class="col-md-3">
                                                            <span style="margin-left: 14px;">
                                                                <label>平台：</label>
                                                                <select class="form-control" id="platform1">
                                                                    <option id="ioss">IOS</option>
                                                                    <option id="androids">Android</option>
                                                                    <option id="alls">全平台</option>
                                                                </select>
                                                            </span>
                                                            </div>
                                                        </div>
                                                        <br>

                                                        <div class="row">

                                                            <div class="col-md-3">
                                                            <span style="margin-left: 14px;">
                                                                <label>展示时间：</label>
                                                                <input type="text" class="form-control" placeholder="请选择日期..." maxlength="8" id="show_time1" readonly>
                                                            </span>
                                                            </div>

                                                            <div class="col-md-3">
                                                            <span style="margin-left: 14px;">
                                                                <label>报名时间：</label>
                                                                <input type="text" class="form-control" placeholder="请选择日期..." maxlength="8" id="registration_time1" readonly>
                                                            </span>
                                                            </div>
                                                        </div>
                                                        <br>

                                                        <div class="row">
                                                            <div class="col-md-3">
                                                            <span style="margin-left: 14px;">
                                                                <label>开赛时间：</label>
                                                                <input type="text" class="form-control" placeholder="请选择日期..." maxlength="8" id="start_date1" readonly>
                                                            </span>
                                                            </div>

                                                            <div class="col-md-3">
                                                            <span style="margin-left: 14px;">
                                                                <label>结束时间：</label>
                                                                <input type="text" class="form-control" placeholder="请选择日期..." maxlength="8" id="end_date1">
                                                            </span>
                                                            </div>
                                                        </div>
                                                        <br>

                                                        <div class="row">
                                                            <div class="col-md-6">
                                                            <span style="margin-left: 14px;">
                                                                <label>比赛详情图片路径地址：</label><br>
                                                                <input type='text' class="form-control path" id="picU1" readonly/>
                                                            </span>
                                                            </div>
                                                        </div>

                                                        <br>
                                                        <div class="row">
                                                            <div class="col-md-3">
                                                            <span style="margin-left: 14px;">
                                                                <label>比赛密码：</label>
                                                                <input type="text" class="form-control" placeholder="请输入密码" id="password1" onkeyup="var reg = /^[0-9]*$/; if(!reg.test(this.value)) this.value='';" maxlength="6" disabled>
                                                            </span>
                                                            </div>
                                                        </div>
                                                        <br>

                                                        <div class="row">
                                                            <div class="col-md-6">
                                                                <label>比赛轮次：</label>（默认为 3）
                                                                <input type="text" class="form-control" value="3" id="round1" maxlength="2"  onkeyup ="var reg = /^[0-9]*$/;if(!reg.test(this.value)) this.value='';">
                                                            </div>
                                                        </div>

                                                        <br>
                                                        <div class="row">
                                                            <div class="col-md-6">
                                                            <span style="margin-left: 14px;">
                                                                <label>比赛规则：</label>
                                                                <textarea rows="5" cols="75" class="form-control" id="rule1" maxlength="300"></textarea>
                                                            </span>
                                                            </div>
                                                        </div>
                                                    </fieldset>
                                                </div>

                                                <div style="width:700px;margin-left: 100px;margin-top: 40px;margin-bottom: 100px;" id="cont3">


                                                </div>

                                            </div>
                                            <div class="modal-footer">
                                                <a href="javascript:;" class=" btn btn-primary" style="margin-left: 2%;" id="publish">发布</a>
                                                <input type="button" class="btn btn-primary" data-dismiss="modal" value="再次编辑">
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

<%--预览--%>
<!-- Modal -->
<div id="previews">

</div>
<div class="modal fade" id="myModals" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width:900px;">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabels">上传图片效果图预览</h4>
            </div>
            <div class="modal-body">

                <div style="position: relative;">
                    <div style="position:absolute;margin-left: 185px;margin-top: 115px;width: 500px;height: 500px;">
                        <img src="" id="pic_preview" class="center-block"/>
                    </div>

                    <img src="assets/img/picpreview.jpg" style="z-index: -999; margin-left: 50px;"/>
                </div>
                <br>

            </div>
            <div class="modal-footer">
                <input type="button" class="btn btn-primary" data-dismiss="modal" value="退出预览">
            </div>
        </div>
    </div>
</div>

</body>
<script>




    var i=1;
    var j=1;
    var r=1;
    $(function () {

        $("#participant_num").change(function () {
            var person = $("#participant_num").val();
            var pers = parseInt(person);
            var s=0;
            var numb=4;
            var i=2;
            if(pers!=4){

                for(;i<50000;i*=2){
                    numb=numb*i;
                    if(numb!=pers){
                        s=s+i;
                    }else{
                        break;
                    }
                    numb=4;
                }
            }
            if(s>=50000){
                $("#participant_num").val("");
                alert("请输入正确的人数，大于等于4且是2的次幂");
            }
        });



        if($("#competition_status").val()=="已结束"){
            $("#stop").hide();
        }
        if($("#competition_status").val()=="进行中"){
            $("#stop").hide();
        }

        if($("#type").val()==1){
            $("#single").attr("selected","true");
        }else if($("#type").val()==2){
            $("#loop").attr("selected","true");
        }



//-------------------------比赛奖励----------------------------//
        var template_id = $("#competition_template_id").val();
        $.ajax({
            type:"post",
            url:"/competitionReward",
            data:{"id":template_id},
            success:function(msg) {
                var jsons = eval(msg);
                $.each(jsons,function(index,itmes) {
                    $("#appe").append("<div class='col-md-12' id='ele"+i+"'>"
                        +"<label>第<input type='text' maxlength='3' id='start"+i+"' value='"+itmes.rankone+"' style='width: 35px;height: 25px; margin-left: 10px;'/><span style='margin: 8px;'>~</span><input type='text' id='end"+i+"' value='"+itmes.ranktwo+"' maxlength='3' style='width: 35px;height: 25px; margin-left: 10px;margin-right: 10px;'/>名</label>"

                        +"<label style='margin-left: 50px;'>奖励"+i+"：</label>"
                        +"<select style='margin-top:0px;' id='type"+i+"'>"
                        +"<option value='钻石' id='diam"+i+"'>钻石</option>"
                        +"<option value='实物' id='goods"+i+"'>实物</option>"
                        +"</select>"

                        +"<input type='text' style='margin-left: 50px;' maxlength='20' id='numOrName"+i+"' placeholder='填写数量或实物名称' value='"+itmes.count+"'/>"
                        +"<label class='btn btn-danger' style='display: none;padding-top:3px;padding-bottom:3px; margin-left: 30px;margin-top: -2px; ' for='fileUpload"+i+"' id='upload"+i+"' alt='"+i+"'>实物图片上传</label>"
                        +"<input type='file' accept='image/png,image/jpeg,image/gif,image/jpg' style='position:absolute;clip:rect(0 0 0 0);' onchange='uploadFile(this)' name='file' alt='"+i+"'  id='fileUpload"+i+"' />"
                        +"<input type='text' style='margin-left: 20px; display: none;' id='number-data-id-"+i+"' name='"+i+"' readonly/>"
                        +"<input type='button' style='margin-left:20px;height: 29px; padding-top: 3px;padding-bottom: 3px;display:none;' id='picPreview"+i+"' data-toggle='modal' data-target='#myModals' class='btn btn-primary' value='图片预览'/>"
                        /*+"<input type='button' style='margin-left:20px;height: 29px; padding-top: 3px;padding-bottom: 3px' class='btn btn-danger' id='del"+i+"' value='删除'/>"*/
                        +"</div>"
                    );

                    $("#del"+i).click(function () {
                        if(i>2){
                            $("#ele"+(i-1)).remove();
                            $("#del"+(i-2)).show();
                            i--;
                            j--;
                        }
                        if(i==2){
                            $("#del"+(i-1)).hide();
                        }
                    });


                    $("#del"+(i-1)).hide();


                    if(itmes.type=="钻石"){
                        $("#diam"+i).attr("selected","true");
                    }else if(itmes.type=="金币"){
                        $("#money"+i).attr("selected","true");
                    }else if(itmes.type=="实物"){
                        $("#goods"+i).attr("selected","true");

                        $("#upload"+i).show();
                        $("#picPreview"+i).show();

                        $("#number-data-id-"+i).show();
                        $("#number-data-id-"+i).val(itmes.url);
                    }

                    //图片预览
                    $("#picPreview"+i).click(function () {
                        var imgUrl = $(this).prev().val();
                        $("#pic_preview").attr("src", imgUrl);
                    });


                    $("#type"+i).change(function () {
                        if($(this).val()=="实物"){
                            $(this).next().next().show();
                            $(this).next().next().next().next().show();
                            $(this).next().next().next().next().next().show();
                        }else{
                            $(this).next().next().next().next().val("");
                            $(this).next().next().hide();
                            $(this).next().next().next().next().hide();
                            $(this).next().next().next().next().next().hide();
                        }

                        if($(this).val()=="0"){
                            $(this).next().next().next().next().val("");
                            $(this).next().val("");
                            $(this).next().attr("disabled","disabled");
                        }else{
                            $(this).next().removeAttr("disabled","disabled");
                        }
                    });
                    j=i;
                    r++;
                    i++;
                });
            }
        });



        $("#addRank").click(function () {
            i=j;
            var start = $("#start"+i).val();
            var end = $("#end"+i).val();
            var numOrName = $("#numOrName"+i).val();
            var type = $("#type"+i).val();
            var url = $("#number-data-id-"+i).val();

            if(start=="" || end==""){
                alert("请完整输入名次");
            }else if(type!="0" && numOrName==""){
                alert("请输入输入(数量)/(实物名称)");
            }else if(type=="实物" && url==""){
                alert("请上传实物图片");
            }else{
                $("#del"+i).hide();

                ++i;
                ++j;
                $("#appe").append("<div class='col-md-12' id='ele"+i+"'>"
                    +"<label>第<input type='text' maxlength='3' id='start"+i+"' style='width: 35px;height: 25px; margin-left: 10px;'/><span style='margin: 8px;'>~</span><input type='text' id='end"+i+"' maxlength='3' style='width: 35px;height: 25px; margin-left: 10px;margin-right: 10px;'/>名</label>"

                    +"<label style='margin-left: 50px;'>奖励"+i+"：</label>"
                    +"<select style='margin-top:0px;' id='type"+i+"'>"
                    +"<option value='钻石' selected>钻石</option>"
                    +"<option value='实物'>实物</option>"
                    +"</select>"

                    +"<input type='text' style='margin-left: 50px;' maxlength='20' id='numOrName"+i+"' placeholder='填写数量或实物名称'/>"

                    +"<label class='btn btn-danger' style='display: none;padding-top:3px;padding-bottom:3px; margin-left: 30px;margin-top: -2px; ' for='fileUpload"+i+"'>实物图片上传</label>"
                    +"<input type='file' accept='image/png,image/jpeg,image/gif,image/jpg' style='position:absolute;clip:rect(0 0 0 0);' onchange='uploadFile(this)' id='fileUpload"+i+"' alt='"+i+"' name='file'/>"
                    +"<input type='text' style='margin-left: 20px; display: none;' id='number-data-id-"+i+"' readonly/>"
                    +"<input type='button' style='margin-left:20px;height: 29px; padding-top: 3px;padding-bottom: 3px;display:none;' id='picPreview"+i+"' data-toggle='modal' data-target='#myModals' class='btn btn-primary' value='图片预览'/>"
                    +"<input type='button' style='margin-left:20px;height: 29px; padding-top: 3px;padding-bottom: 3px' class='btn btn-danger' id='del"+i+"' value='删除'/>"
                    +"</div>"
                );
            }

            $("#start"+i).change(function () {
                if(parseInt($("#start"+i).val())<=parseInt($("#end"+(i-1)).val())){
                    this.value='';
                    alert("输入的名次应该大于前面的名次");
                }
            });
            $("#end"+i).change(function () {
                if(parseInt($("#start"+i).val())>parseInt($("#end"+i).val())){
                    this.value='';
                    alert("输入的名次应该大于前面的名次");
                }
            });

            $("#start"+i).keyup(function () {
                var reg = /^[0-9]*$/;
                if(!reg.test(this.value)) this.value='';
            });
            $("#end"+i).keyup(function () {
                var reg = /^[0-9]*$/;
                if(!reg.test(this.value)) this.value='';
            });



            $("#del"+i).click(function () {
                if(i!=1){
                    $("#ele"+i).remove();
                    $("#del"+(i-1)).show();
                    i--;
                    j--;
                }
            });


            //图片预览
            $("#picPreview"+i).click(function () {
                var imgUrl = $(this).prev().val();
                $("#pic_preview").attr("src", imgUrl);
            });


            $("#type"+i).change(function () {
                if($(this).val()=="实物"){
                    $(this).next().next().show();
                    $(this).next().next().next().next().show();
                    $(this).next().next().next().next().next().show();
                }else{
                    $(this).next().next().next().next().val("");
                    $(this).next().next().hide();
                    $(this).next().next().next().next().hide();
                    $(this).next().next().next().next().next().hide();
                }

                if($(this).val()=="0"){
                    $(this).next().next().next().next().val("");
                    $(this).next().val("");
                    $(this).next().attr("disabled","disabled");
                }else{
                    $(this).next().removeAttr("disabled","disabled");
                }
            });
        });

        //以上是比赛奖励

        $("#yu").click(function () {
            $("#cont3").html("");
            for(var sts=1;sts<=j;sts++){
                $("#cont3").append("<fieldset disabled><div class='col-md-12'>"
                    +"<label>第<input type='text' maxlength='3' id='starts"+sts+"' style='width: 35px;height: 25px; margin-left: 10px;'/><span style='margin: 8px;'>~</span><input type='text' id='ends"+sts+"'  maxlength='3' style='width: 35px;height: 25px; margin-left: 10px;margin-right: 10px;'/>名</label>"

                    +"<label style='margin-left: 20px;'>奖励1：</label>"
                    +"<select style='margin-top:0px;'>"
                    +"<option id='dia"+sts+"'>钻石</option>"
                    +"<option id='goods"+sts+"'>实物</option>"
                    +"</select>"

                    +"<input type='text' style='margin-left: 20px;width: 25%; ' maxlength='20' id='numOrNames"+sts+"' placeholder='填写数量或实物名称'/>"
                    +"<input type='text' style='margin-left: 20px; width: 68%; display: none;' class='form-control' id='number-data-ids-"+sts+"' readonly/>"
                    +"</div>"
                    +"</fieldset><br>"
                );
                //$("#cont3").append("<fieldset disabled>"+$("#appe").html()+"</fieldset>");
                $("#starts"+sts).val($("#start"+sts).val());
                $("#ends"+sts).val($("#end"+sts).val());
                $("#numOrNames"+sts).val($("#numOrName"+sts).val());
                if($("#type"+sts).val()=="钻石"){
                    $("#dia"+sts).attr("selected","true");
                    $("#goods"+sts).removeAttr("selected");
                    $("#number-data-ids-"+sts).hide();
                }else if($("#type"+sts).val()=="实物"){
                    $("#goods"+sts).attr("selected","true");
                    $("#dia"+sts).removeAttr("selected");
                    $("#number-data-ids-"+sts).show();
                    $("#number-data-ids-"+sts).val($("#number-data-id-"+sts).val());
                }
            }
        });



        $("#publish").click(function () {
            var seq = $("#seq").val();
            var participant_num = $("#participant_num").val();
            var competition_type = $("#competition_type").val();
            var title = $("#title").val();
            var subheading = $("#subheading").val();
            var cost = $("#cost").val();
            var cost_type = $("#cost_type").val();
            var registration_time = $("#registration_time").val();
            var start_date = $("#start_date").val();
            var end_date = $("#end_date").val();
            var rule = $("#rule").val();
            var show_time = $("#show_time").val();
            var compPicUrl = $(".path").val();
            var pass = $("#pass").val();
            var password = $("#password").val();
            var round = $("#round").val();
            var settle = $("#settle").val();

////---------------------------------------------------------------///

            var reg = /^[0-9]{6,}$/;

            var url = $("#pict").val();



            for(var s=1;s<=i;s++){
                var start = $("#start"+s).val();
                var end = $("#end"+s).val();
                var numOrName = $("#numOrName"+s).val();
                var type = $("#type"+s).val();
                var url = $("#number-data-id-"+s).val();

                if(start=="" || end==""){
                    i=s;
                    break;
                }else if(type!="0" && numOrName==""){
                    i=s;
                    break;
                }else if(type=="实物" && url==""){
                    i=s;
                    break;
                }
            }




            if(title==""){
                alert("请输入标题");
            }else if(subheading==""){
                alert("请输入副标题");
            }else if(cost==""){
                alert("请输入报名费用");
            }else if(participant_num==""){
                alert("请输入参赛人数");
            }else if(registration_time==""){
                alert("请选择报名时间");
            }else if(show_time==""){
                alert("请选择展示时间");
            }else if(show_time>registration_time){
                alert("展示实时间不能大于报名时间");
            }else if(start_date==""){
                alert("请选择开赛时间");
            }else if(end_date==""){
                alert("请选择结束时间");
            }else if(compPicUrl==""){
                alert("请上传比赛详情图片");
            }else if(pass==1 && password==""){
                alert("请设置比赛密码");
            }else if(pass==1 && !reg.test(password)){
                alert("密码不少于六位数字");
            }else if(settle==1 && round==""){
                alert("请输入比赛轮次");
            }else if(rule==""){
                alert("请输入游戏规则");
            }else{

                if(s>i){
                    var strs=new Array()
                    for(var k=1;k<=j;k++){
                        var start = $("#start"+k).val();
                        var end = $("#end"+k).val();
                        var numOrName = $("#numOrName"+k).val();
                        var type = $("#type"+k).val();
                        var url = $("#number-data-id-"+k).val();

                        var str = start+","+end+","+numOrName+","+type+","+url;
                        strs[k-1]=str;
                    }

                    rule = rule.replace(/r|\n/g,"<br>");


                    window.location.href="/competition_publish?seq="+seq+"&competition_type="+competition_type+"&title="+title+"&subheading="+subheading+"&cost="+cost+"&cost_type="+cost_type+"&registration_time="+registration_time+"&start_date="+start_date+"&end_date="+end_date+"&rule="+rule+"&participant_num="+participant_num
                        +"&strs="+strs+"&show_time="+show_time+"&compPicUrl="+compPicUrl+"&password="+password+"&round="+round+"&settle="+settle;

                    /* window.location.href="/competition_publish?seq="+seq+"&competition_type="+competition_type+"&title="+title+"&subheading="+subheading+"&cost="+cost+"&cost_type="+cost_type+"&registration_time="+registration_time+"&start_date="+start_date+"&end_date="+end_date+"&rule="+rule+"&participant_num="+participant_num
                     +"&strs="+strs;*/
                }else{
                    alert("请将比赛奖励数据填写完整");
                }
            }
        })
    });

    function uploadFile(obj) {
        var id = $(obj).attr('alt');

        $.ajaxFileUpload({
            type:"post",
            url:"/fileUpload",
            async:true,
            secureuri:false,
            fileElementId:$(obj).attr('id'),
            dataType:"text/html",
            data:{'id':id},
            success:function(msg){
                if(msg=='<pre style="word-wrap: break-word; white-space: pre-wrap;">0</pre>'){
                    alert("上传错误");
                }else{

                    var msgs = msg.replace(/<pre style=\"word-wrap: break-word; white-space: pre-wrap;\">/g,"");
                    msgs = msgs.replace(/<pre>/g,"");
                    msgs = msgs.replace(/<\/pre>/g,"");

                    if(obj.value!=""){
                        alert("上传成功！");
                        $("#number-data-id-"+id).val(msgs);
                    }else{
                        alert("上传失败，请重新上传");
                        $("#number-data-id-"+id).val("");
                    }

                }


            },
            error:function(data){
                console.log("服务器异常");
            }
        });
        return false;
    }

</script>
</html>
