<%--
  Created by IntelliJ IDEA.
  User: juunew
  Date: 2017/6/21
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <title>聚牛网络|收入</title>
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
</head>
<script>
    function fun() {
        $.ajax({
            type:"post",
            url:"/control",
            success:function(msg) {
                var finances = "<a href='#finance' data-toggle='collapse' aria-expanded='true' class='active'><i class='lnr lnr-file-empty'></i> <span>财务管理</span> <i class='icon-submenu lnr lnr-chevron-left'></i></a>"
                    +"<div id='finance' class='collapse in' aria-expanded='true'>"
                    +"<ul class='nav'>"
                    +"<li><a href='/information_all'><i class='lnr lnr-dice'></i> <span>我的资料</span></a></li>"
                    +"<li><a href='/integral_withdrawals?page=1'><i class='lnr lnr-dice'></i> <span>积分提现审核</span></a></li>"
                    +"<li><a href='/auditing?page=1'><i class='lnr lnr-text-format'></i> <span>财务审核</span></a></li>"
                    +"<li><a href='/approval_record?page=1&proxy_date=&proxy_date_end=&send_id='><i class='lnr lnr-dice'></i> <span>审批记录</span></a></li>"
                    +"<li><a href='/financial_daily?page=1'><i class='lnr lnr-dice'></i> <span>财务日报</span></a></li>"
                    +"<li><a href='/consumption_details?page=1&start_date=&end_date='><i class='lnr lnr-dice'></i> <span>钻石消耗详情</span></a></li>"
                    +"<li><a href='/diamond_production?page=1&start_date=&end_date='><i class='lnr lnr-dice'></i> <span>钻石产生详情</span></a></li>"
                    /* +"<li><a class='active' href='/proceeds'><i class='lnr lnr-dice'></i> <span>代理充值统计</span></a></li>"
                    +"<li><a href='/proceeds'><i class='lnr lnr-dice'></i> <span>代理分发统计</span></a></li>"*/
                    +"</ul>"
                    +"</div>";

                var yunyin = "<a href='#subPages' data-toggle='collapse' class='collapsed'><i class='lnr lnr-file-empty'></i> <span>运营后台</span> <i class='icon-submenu lnr lnr-chevron-left'></i></a>"
                    +"<div id='subPages' class='collapse '>"
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
                    <li><a href="/idex" class="active"><i class="lnr lnr-home"></i> <span>管理员首页</span></a></li>
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
                            <div class="row">
                                <div class="col-md-6">
                                    <form action="#" method="post" class="navbar-form navbar-left">
                                        <div class="input-group">
                                            <input type="text" id="userid" class="form-control searchbox" placeholder="请输入日期...">
                                            <span class="input-group-btn"><input type="button" id="sear" class="btn btn-primary" value="查询"></span>
                                        </div>
                                        <br>
                                        <br>
                                        <label>查找筛选：</label>
                                        <select type="checkbox" class="form-control">
                                            <option>今日数据</option>
                                            <option>一周数据</option>
                                            <option>本月数据</option>
                                        </select>
                                    </form>
                                </div>
                            </div>
                            <div class="row tb">
                                <div class="col-md-12">
                                    <table class="table table-hover">
                                        <tr class="active">
                                            <th>日期</th>
                                            <th>钻石流动数量</th>
                                            <th>钻石消耗数量</th>
                                            <th>成局房间数</th>
                                        </tr>
                                        <tr>
                                            <td>2017-5-5</td>
                                            <td>所有代理发送的钻石总量</td>
                                            <td>游戏中所有消耗钻石总量</td>
                                            <td>当天开局成功扣除钻石的房间总量</td>
                                        </tr>
                                    </table>
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
<script src="assets/vendor/jquery/jquery.min.js"></script>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script src="assets/scripts/klorofil-common.js"></script>
</body>

</html>
