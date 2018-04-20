<%--
  Created by IntelliJ IDEA.
  User: juunew
  Date: 2017/6/21
  Time: 12:36
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>聚牛网络|消息奖励</title>
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
    <script src="bs/js/jquery-1.11.2.min.js"></script>
    <script src="assets/DatePicker/WdatePicker.js" ></script>
    <script src="assets/DatePicker/config.js" ></script>
    <script src="assets/DatePicker/lin.js"></script>
    <script src="assets/DatePicker/config.js" ></script>

    <script src="assets/DatePicker/WdatePicker.js" ></script>
    <script src="assets/DatePicker/config.js" ></script>

    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/WdatePicker.css">
    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/whyGreen/datepicker.css">

</head>

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

                            <input type="hidden" value="${oneDate.id}" id="ID"/>
                            <input type="hidden" value="${oneDate.display_position}" id="display_position_hide"/>
                            <input type="hidden" value="${oneDate.sign_out}" id="sign_out_hide"/>
                            <input type="hidden" value="${oneDate.reward_type}" id="type_hide"/>
                            <input type="hidden" value="${oneDate.reward_number}" id="nub"/>
                            <input type="hidden" value="${oneDate.platform}" id="platform_hide"/>
                            <div style="width:340px;margin-left: 100px;">
                                <form action="#" method="post">
                                    <div class="form-group">
                                        <label>标题：</label><br>
                                        <input type="text" placeholder="请输入标题" class="form-control" id="title" value="${oneDate.title}" maxlength="20"/><br>
                                    </div>
                                    <div class="form-group">
                                        <label>公告内容：</label><br>
                                        <textarea placeholder="请输入公告内容" rows="5" cols="30" class="form-control" id="content">${oneDate.content}</textarea><br>
                                    </div>
                                    <div class="form-group">
                                        <label>位置：</label><br>
                                        <select id="display_position" class="form-control">
                                            <option value="消息界面" id="ms">消息界面</option>
                                            <option value="弹窗" id="alet">弹窗</option>
                                        </select><br>
                                    </div>
                                    <div class="form-group">
                                        <label>是否要退出：</label><br>
                                        <select class="form-control" id="sign_out">
                                            <option value="0" id="notExit">不需退出游戏</option>
                                            <option value="1" id="exits">查看后需要强退</option>
                                        </select><br>
                                    </div>
                                    <div class="form-group">
                                        <label>奖励类型：</label><br>
                                        <select id="type" class="form-control">
                                            <option value="无" id="non">无</option>
                                            <option value="钻石" id="diamond">钻石</option>
                                            <option value="金币" id="money">金币</option>
                                        </select><br>
                                    </div>
                                    <div class="form-group" id="reward_num" style="display: none;">
                                        <label>奖励数量：</label><br>
                                        <input type="text" placeholder="请输入数量" class="form-control" id="number" maxlength="7"/><br>
                                    </div>
                                    <div class="form-group">
                                        <fmt:parseDate value="${oneDate.start_date}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                                        <label>开始时间：</label><br>
                                        <input type="text" class="form-control"  id="set_date" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly/>
                                    </div>
                                    <div class="form-group">
                                        <fmt:parseDate value="${oneDate.end_date}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                                        <label>结束时间：</label><br>
                                        <input type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="end_date" value="<fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly/>
                                    </div>

                                    <div class="form-group">
                                        <label>平台：</label><br>
                                        <select id="platform" class="form-control">
                                            <option id="all">全部</option>
                                            <option value="IOS" id="ios">IOS</option>
                                            <option value="Android" id="android">Android</option>
                                        </select><br>
                                    </div>
                                    <div class="form-group">
                                        <label>应用版本：</label><br>
                                        <input type="text" placeholder="请输入版本号" class="form-control" id="version" value="${oneDate.version}"/>
                                        <br>
                                    </div>

                                    <div>
                                        <label><input type="radio" name="push" id="pushUser1" checked/>全员推送</label>
                                        <div class="form-group">
                                            <label><input type="radio" name="push" id="pushUser"/>特定用户</label><br>
                                            <input type="text" placeholder="请输入用户ID,中间用英文下逗号隔开" class="form-control" value="${oneDate.specific_user}" id="specific_user" onkeyup="var reg = /^[^\u4e00-\u9fa5]{0,}$/;var regs=/^ +| +$/g; if(!reg.test(this.value))  this.value='';if(regs.test(this.value)) this.value=this.value.replace(/^ +| +$/g,'');" readonly/><br>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <input type="button" class="btn btn-danger" value="修改并发布" id="publishAndChange"/>
                                        <br>
                                    </div>
                                </form>

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
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script src="assets/scripts/klorofil-common.js"></script>
</body>
<script>
    $(function () {

        if($("#display_position_hide").val()=="1"){
            $("#ms").attr("selected","true");
        }else {
            $("#alet").attr("selected","true");
        }

        if($("#sign_out_hide").val()=="0"){
            $("#notExit").attr("selected","true");
        }else {
            $("#exits").attr("selected","true");
        }





        if($("#type_hide").val()=="0"){
            $("#non").attr("selected","true");
            $("#reward_num").hide();
            $("#number").val("");
        }else if($("#type_hide").val()=="1"){
            $("#diamond").attr("selected","true");
            $("#reward_num").show();
            $("#reward_num").show();
            $("#number").val($("#nub").val());
        }else if($("#type_hide").val()=="2"){
            $("#money").attr("selected","true");
            $("#reward_num").show();
            $("#number").val($("#nub").val());
        }


        $("#type").change(function () {
            if($(this).val()=="无"){
                $("#reward_num").hide();
                $("#number").val("");
            }else{
                $("#reward_num").show();
            }
        });

        if($("#platform_hide").val()=="0"){
            $("#all").attr("selected","true");
        }else if($("#platform_hide").val()=="1"){
            $("#ios").attr("selected","true");
        }else if($("#platform_hide").val()=="2"){
            $("#android").attr("selected","true");
        }


        var id = $("#ID").val();


        var specific_user_status=0;
        $("#pushUser").click(function () {
            $("#specific_user").removeAttr("readonly");
            specific_user_status=1;
        });
        $("#pushUser1").click(function () {
            $("#specific_user").attr("readonly","true");
            $("#specific_user").val("");
            specific_user_status=0;
        });


        var specific_user = $("#specific_user").val();
        if(specific_user!=""){
            $("#pushUser").attr("checked","true");
            $("#specific_user").removeAttr("readonly");
            specific_user_status=1;
        }else {
            specific_user_status=0;
        }



        <!--存为草稿-->


        $("#publishAndChange").click(function () {
            var title = $("#title").val();
            var content = $("#content").val();
            var number = $("#number").val();
            var end_date = $("#end_date").val();
            var specific_user = $("#specific_user").val();
            var start_date = $("#set_date").val();
            var display_position = $("#display_position").val();
            var sign_out = $("#sign_out").val();
            var version = $("#version").val();
            var type = $("#type").val();


            if(title==""){
                alert("请输入标题！");
            }else if(content==""){
                alert("请输入内容！");
            }else if(start_date==""){
                alert("请输入开始时间！");
            }else if(end_date==""){
                alert("请输入结束时间！");
            }else if(type!="无" && number==""){
                alert("请输入要发送的数量！");
            }else if(specific_user_status==1&&specific_user==""){
                alert("请输入特定用户");
            }else{
                if(number==""){
                    number="0";
                }
                //alert("是否退出："+sign_out+"，版本："+version+"，特定用户："+specific_user);
               window.location.href="/pulishandchange?status=1&title="+title+"&content="+content+"&type="+type+"&number="+number+"&start_date="+start_date+"&end_date="+end_date+"&platform="+platform+"&version="+version+"&specific_user="+specific_user
                   +"&display_position="+display_position+"&sign_out="+sign_out+"&id="+id;
               /* alert(title+">>>"+content+">>>"+type+">>>"+number+">>>"+start_date+",end:::::"+end_date);*/

            }

        });

    });

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

                var yunyin = "<a href='#subPages' data-toggle='collapse'  aria-expanded='true' class='active'><i class='lnr lnr-file-empty'></i> <span>运营后台</span> <i class='icon-submenu lnr lnr-chevron-left'></i></a>"
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
                    +"<div id='users' class='collapsed'>"
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


</html>
