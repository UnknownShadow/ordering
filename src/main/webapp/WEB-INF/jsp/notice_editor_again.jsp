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
                    +"<li><a href='/paid_records'>&nbsp;&nbsp;付费玩家记录</a></li>"
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

                            <input type="hidden" id="id_hide" value="${announcementsList.id}"/>
                            <input type="hidden" id="content_hide" value="${announcementsList.content}"/>
                            <input type="hidden" id="platform_hide" value="${announcementsList.platform}"/>
                            <input type="hidden" id="exit_hide" value="${announcementsList.exit}"/>
                            <input type="hidden" id="jump_hide" value="${announcementsList.jump_url}"/>
                            <input type="hidden" id="display_position_hide" value="${announcementsList.display_position}"/>
                            <div style="width:340px;margin-left: 100px;">
                                <form action="#" method="post">
                                    <div class="form-group">
                                        <label>标题：</label><br>
                                        <input type="text" placeholder="请输入标题" class="form-control" id="title" maxlength="20" value="${announcementsList.title}"/><br>
                                    </div>
                                    <div class="form-group">
                                        <label>公告内容：</label><br>
                                        <textarea placeholder="请输入公告内容" rows="5" cols="30" maxlength="120" class="form-control" id="content"></textarea><br>
                                        <script>
                                            var contents = $("#content_hide").val();
                                            contents = contents.replace("<br>","\n");
                                            $("#content").val(contents);
                                        </script>
                                    </div>
                                    <div class="form-group">
                                        <label>深度跳转：</label><br>
                                        <select id="web_jumping" class="form-control">
                                            <option id="non">无</option>
                                            <option value="游戏内" id="Inner">游戏内</option>
                                            <option value="游戏外" id="Outt">游戏外</option>
                                        </select><br>
                                    </div>
                                    <div class="form-group" style="display: none;" id="inners">
                                        <label>游戏内跳转：</label><br>
                                        <select id="gameInner" class="form-control">
                                            <option value="1" id="mission">任务</option>
                                            <option value="2" id="case">宝箱</option>
                                            <option value="3" id="sign">签到</option>
                                            <option value="4" id="competition">比赛</option>
                                            <option value="5" id="setting">个人设置</option>
                                            <option value="6" id="sys">系统设置</option>
                                            <option value="7" id="fish">鱼虾蟹</option>
                                            <option value="8" id="msg">消息</option>
                                            <option value="9" id="record">比赛记录</option>
                                        </select><br>
                                    </div>
                                    <div class="form-group" style="display: none;" id="outs">
                                        <label>游戏外跳转：</label><br>
                                        <input type="text" placeholder="请输入url" class="form-control" id="url"/>
                                    </div>
                                    <div class="form-group">
                                        <label>位置：</label><br>
                                        <select id="location"  class="form-control">
                                            <option value="1" id="alertAndLight">弹窗+跑马灯</option>
                                            <option value="2" id="light">仅跑马灯</option>
                                        </select><br>
                                        <input type="hidden" id="hid"/>
                                    </div>
                                    <div class="form-group">
                                        <label>是否要退出：</label><br>
                                        <select class="form-control" id="sign_out">
                                            <option value="0" id="noexit">不需退出游戏</option>
                                            <option value="1" id="exit">查看后需要强退</option>
                                        </select><br>
                                    </div>
                                    <div class="form-group">
                                        <label>开始时间：</label><br>
                                        <input type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="set_date" value="${start_date}" readonly/>
                                    </div>
                                    <div class="form-group">
                                        <label>结束时间：</label><br>
                                        <input type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="end_date" value="${end_date}" readonly/>
                                    </div>

                                    <div class="form-group">
                                        <label>平台：</label><br>
                                        <select id="platform" class="form-control">
                                            <option id="all">全部</option>
                                            <option id="ios">IOS</option>
                                            <option id="android">Android</option>
                                        </select><br>
                                    </div>
                                    <div class="form-group">
                                        <label>应用版本：</label><br>
                                        <input type="text" placeholder="请输入版本号" class="form-control" id="version" value="${announcementsList.version}"/><br>
                                    </div>
                                    <div class="form-group">
                                        <label>显示次数：</label><br>
                                        <input type="text" placeholder="请输入需要显示的次数" id="times" class="form-control" maxlength="3" value="${announcementsList.times}" onkeyup="var reg = /^[0-9]*$/; if(!reg.test(this.value)) this.value='';" /><br>
                                    </div>

                                    <div class="form-group">
                                        <input type="button" class="btn btn-danger" value="修改" id="publish"/>
                                        <br>
                                    </div>
                                </form>

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

        if($("#jump_hide").val()==""){
            $("#non").attr("selected","true");
        }else if($("#jump_hide").val()>="1" && $("#jump_hide").val()<="9"){
            $("#Inner").attr("selected","true");
            $("#inners").css("display","block");
            $("#outs").css("display","none");

            if($("#jump_hide").val()=="1"){
                $("#mission").attr("selected","true");
            }else if($("#jump_hide").val()=="2"){
                $("#case").attr("selected","true");
            }else if($("#jump_hide").val()=="3"){
                $("#sign").attr("selected","true");
            }else if($("#jump_hide").val()=="4"){
                $("#competition").attr("selected","true");
            }else if($("#jump_hide").val()=="5"){
                $("#setting").attr("selected","true");
            }else if($("#jump_hide").val()=="6"){
                $("#sys").attr("selected","true");
            }else if($("#jump_hide").val()=="7"){
                $("#fish").attr("selected","true");
            }else if($("#jump_hide").val()=="8"){
                $("#msg").attr("selected","true");
            }else if($("#jump_hide").val()=="9"){
                $("#record").attr("selected","true");
            }

        }else{
            $("#Outt").attr("selected","true");
            $("#inners").css("display","none");
            $("#outs").css("display","block");
            $("#url").val($("#jump_hide").val());
        }

        if($("#display_position_hide").val()=="1"){
            $("#alertAndLight").attr("selected","true");
        }else{
            $("#light").attr("selected","true");
        }

        if($("#exit_hide").val()=="0"){
            $("#noexit").attr("selected","true");
        }else{
            $("#exit").attr("selected","true");
        }

        if($("#platform_hide").val()=="0"){
            $("#all").attr("selected","true");
        }else if($("#platform_hide").val()=="1"){
            $("#ios").attr("selected","true");
        }else{
            $("#android").attr("selected","true");
        }



//-------------------------------------------------------------/

        var jump=  $("#web_jumping").val();
        var url="";
        $("#web_jumping").change(function () {
            if($(this).val()=="游戏内"){
                $("#inners").css("display","block");
                $("#outs").css("display","none");
                $("#url").val("");
            }else  if($(this).val()=="游戏外"){
                $("#inners").css("display","none");
                $("#outs").css("display","block");
            }else{
                $("#inners").css("display","none");
                $("#outs").css("display","none");
                $("#url").val("");
            }
            jump = $(this).val();
        });


        // var data = JSON.parse();


        <!--修改-->

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
            var id = $("#id_hide").val();
            url = $("#url").val();

            if(jump=="游戏内"){
                url = $("#gameInner").val();
            }

            if(title==""){
                alert("请输入标题！");
            }else if(content==""){
                alert("请输入内容！");
            }else if(jump=="游戏外" && url==""){
                alert("请输入游戏外跳转url！");
            }else if(start_date==""){
                alert("请输入开始时间！");
            }else if(end_date==""){
                alert("请输入结束时间！");
            }else if(version==""){
                alert("请输入版本号");
            }else if(times==""){
                alert("请输入显示次数");
            }else{
                content = content.replace(/r|\n/g,"<br>");


                var r=confirm("确定发布此公告?");
                if (r==true){
                    window.location.href="/pulishAndChangeMsg?title="+title+"&content="+content+"&start_date="+start_date+"&end_date="+end_date
                        +"&location="+location+"&platform="+platform+"&version="+version+"&sign_out="+sign_out+"&times="+times+"&url="+url+"&id="+id;
                }else{
                    return false;
                }

            }
        });

    });
</script>

</html>
