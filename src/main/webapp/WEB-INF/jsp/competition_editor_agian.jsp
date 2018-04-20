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

                                <input type="hidden" value="${byRewardsNum }" id="rewardsNum"/>
                                <input type="hidden" value="${competitionTemplat.type }" id="type"/>
                                <input type="hidden" value="${competition_status }" id="competition_status"/>
                                <h4><label style="margin-bottom: 30px;">比赛新增与编辑</label></h4>
                                <div class="row">
                                    <div class="col-md-3">
                                        <span>
                                            <label>排序：</label>
                                            <select class="form-control" id="seq" disabled>
                                                <option>1</option>
                                                <option>2</option>
                                                <option>3</option>
                                                <option>4</option>
                                                <option>5</option>
                                            </select>
                                        </span>
                                    </div>

                                    <input type="hidden" id="competition_template_id" value="${competitionTemplat.id}"/>

                                    <div class="col-md-3">
                                        <span>
                                            <label>类型：</label>
                                            <select class="form-control" id="competition_type" disabled>
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
                                            <input type="text" placeholder="建议输入10字以内" maxlength="20" class="form-control" id="subheading" value="${competitionTemplat.name}"/>
                                        </span>
                                    </div>
                                </div>
                                <br>

                                <div class="row">
                                    <div class="col-md-3">
                                        <span>
                                            <label>报名费用：</label>
                                            <input type="text" placeholder="请输入数量，默认为钻石"  value="${competitionTemplat.num}" class="form-control" id="cost" maxlength="7" disabled/>
                                        </span>
                                    </div>

                                    <div class="col-md-3">
                                        <span>
                                            <label>费用类型</label>
                                            <select class="form-control" id="cost_type"  disabled>
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
                                            <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="registration_time" readonly  value="<fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>" disabled>
                                        </span>
                                    </div>
                                </div>
                                <br>

                                <div class="row">
                                    <div class="col-md-3">
                                        <span style="margin-left: 14px;">
                                            <label>开赛时间：</label>
                                            <fmt:parseDate value="${competitionTemplat.start_time}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                                            <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="start_date" readonly  value="<fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>" disabled>
                                        </span>
                                    </div>

                                    <div class="col-md-3">
                                        <span style="margin-left: 14px;">
                                            <label>结束时间：</label>
                                            <fmt:parseDate value="${competitionTemplat.end_time}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                                            <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="end_date" readonly  value="<fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>" disabled>
                                        </span>
                                    </div>
                                </div>
                                <br>

                                <div class="row">
                                    <div class="col-md-6">
                                            <span style="margin-left: 14px;">
                                                <label>比赛详情图片：</label>
                                                <br>
                                                <span style="margin-left: 14px;">
                                                    <label class='btn btn-danger' for='Fupload'>图片上传</label>
                                                    <input type='file' accept='image/png,image/jpeg,image/gif,image/jpg' style='position:absolute;clip:rect(0 0 0 0);' onchange='upload(this)'  name='file'  id='Fupload' />

                                                    <input type="button" style="margin-left: 15px" class="btn btn-primary" data-toggle="modal" data-target="#model" id="preview" value="图片上传预览">
                                                </span>
                                                <div class="form-group">
                                                    <label>图片路径地址：</label><br>
                                                    <input type='text' class="form-control path" value="${competitionTemplat.pic_url}" id="pic_url" readonly/>
                                                </div>
                                                <br>
                                            </span>
                                    </div>
                                </div>
                                <br>
                                <script>
                                    function upload(obj) {

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
                                                    alert("上传成功！");

                                                    var msgs = msg.replace(/<pre style=\"word-wrap: break-word; white-space: pre-wrap;\">/g,"");
                                                    msgs = msgs.replace(/<pre>/g,"");
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
                                <div class="row">
                                    <div class="col-md-3">
                                        <span style="margin-left: 14px;">
                                            <label>比赛密码：</label>
                                            <input type="text" class="form-control" placeholder="请输入密码" id="password" value="${competitionTemplat.password}" onkeyup="var reg = /^[0-9]*$/; if(!reg.test(this.value)) this.value='';" maxlength="6" disabled>
                                        </span>
                                    </div>
                                </div>

                                <br>
                                <br>

                                <div class="row">
                                    <div class="col-md-6">
                                        <label>比赛轮次：</label>（默认为 3）
                                        <input type="text" class="form-control" value="${competitionTemplat.totalPeriod}" id="round" maxlength="2"  onkeyup ="var reg = /^[0-9]*$/;if(!reg.test(this.value)) this.value='';" disabled>
                                    </div>
                                </div>


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
                                        <%--<input type="button" value="添加名次" class="btn btn-primary" id="addRank"/>--%>
                                        <%--<a href="javascript:;" class=" btn btn-primary" style="margin-left: 2%;" id="publish">修改</a>--%>
                                        <a href="javascript:;" class=" btn btn-primary" style="margin-left: 2%;" id="publish">修改</a>
                                        <a href="javascript:;" class=" btn btn-danger" id="stop" style="margin-left: 2%;" alt="${competitionTemplat.id}">立即终止比赛</a>
                                    </div>
                                </div>
                                <br>
                                <br>
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




    var i=1;
    var j=1;
    var r=1;
    $(function () {
        //---立即终止比赛---//
        $("#stop").click(function () {

            var id = $(this).attr("alt");

            var r=confirm("是否确定立即终止比赛?");
            if (r==true){
                window.location.href="/shopCompetition?id="+id;
            }else{
                return false;
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
                        +"<label>第<input type='text' maxlength='3' id='start"+i+"' value='"+itmes.rankone+"' style='width: 35px;height: 25px; margin-left: 10px;' disabled/><span style='margin: 8px;'>~</span><input type='text' id='end"+i+"' value='"+itmes.ranktwo+"' maxlength='3' style='width: 35px;height: 25px; margin-left: 10px;margin-right: 10px;' disabled/>名</label>"

                        +"<label style='margin-left: 50px;'>奖励"+i+"：</label>"
                        +"<select style='margin-top:0px;' id='type"+i+"' disabled>"
                        +"<option value='钻石' id='diam"+i+"'>钻石</option>"
                        +"<option value='金币' id='money"+i+"'>金币</option>"
                        +"<option value='实物' id='goods"+i+"'>实物</option>"
                        +"<option value='0'>无</option>"
                        +"</select>"

                        +"<input type='text' style='margin-left: 50px;' maxlength='20' id='numOrName"+i+"' placeholder='填写数量或实物名称' value='"+itmes.count+"'/>"
                        +"<label class='btn btn-danger' style='display: none;padding-top:3px;padding-bottom:3px; margin-left: 30px;margin-top: -2px; ' for='fileUpload"+i+"' id='upload"+i+"' alt='"+i+"'>实物图片上传</label>"
                        +"<input type='file' accept='image/png,image/jpeg,image/gif,image/jpg' style='position:absolute;clip:rect(0 0 0 0);' onchange='uploadFile(this)' name='file' alt='"+i+"'  id='fileUpload"+i+"' />"
                        +"<input type='text' style='margin-left: 20px; display: none;' id='number-data-id-"+i+"' name='"+i+"' readonly/>"
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


                        $("#number-data-id-"+i).show();
                        $("#number-data-id-"+i).val(itmes.url);
                    }


                    $("#type"+i).change(function () {
                        if($(this).val()=="实物"){
                            $(this).next().next().show();
                            $(this).next().next().next().next().show();
                        }else{
                            $(this).next().next().next().next().val("");
                            $(this).next().next().hide();
                            $(this).next().next().next().next().hide();
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





        $("#publish").click(function () {
            var title = $("#title").val();
            var subheading = $("#subheading").val();
            var rule = $("#rule").val();
            var template_id = $("#competition_template_id").val();
            var pic_url = $("#pic_url").val();


            var urls = "";
            for(var s=1;s<i;s++){
                var url = $("#number-data-id-"+s).val();
                if(url!=""){
                    urls=urls +","+ url;
                }
            }

            var rewardNames = "";
            for(var s=1;s<i;s++){
                var rewardName = $("#numOrName"+s).val();
                var start = $("#start"+s).val();
                var end = $("#end"+s).val();
                var type = $("#type"+s).val();
                var typed = "";
                if(type == "钻石"){typed=type;}
                if(rewardName!=""){
                    if(start==end){
                        rewardNames=rewardNames +","+"第"+start+"名 "+ rewardName + typed;
                    }else{
                        rewardNames=rewardNames +","+ "第"+start+"~"+end+"名 "+rewardName + typed;
                    }
                }
            }


            if(title==""){
                alert("请输入标题");
            }else if(subheading==""){
                alert("请输入副标题");
            }else if(pic_url==""){
                alert("请上传比赛详情图片");
            }else if(rule==""){
                alert("请输入游戏规则");
            }else{

                rule = rule.replace(/r|\n/g,"<br>");

                window.location.href="/competition_change?title="+title+"&subheading="+subheading+"&rule="+rule+"&template_id="+template_id+"&urls="+urls+"&rewardNames="+rewardNames+"&pic_url="+pic_url+"&strs=";

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
