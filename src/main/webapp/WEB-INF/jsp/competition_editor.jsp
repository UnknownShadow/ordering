<!DOCTYPE>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>潮尚玩|比赛新增与编辑</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>

    <script src="assets/scripts/ajaxfileupload.js" type="text/javascript" charset="utf-8"></script>
    <script src="assets/DatePicker/lin.js"></script>
    <script src="assets/DatePicker/WdatePicker.js" ></script>
    <script src="assets/DatePicker/config.js" ></script>
    <script src="bs/js/holder.js" ></script>
    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/WdatePicker.css">
    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/whyGreen/datepicker.css">
    <!-- load css -->
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/bootstrap/css/bootstrap.css" media="all">
</head>
<style>a:hover{text-decoration: none;}</style>
<body>

    <ul class="layui-nav" lay-filter="">
        <li class="layui-nav-item"><a href="/operation_daily?page=1&limit=20">运营日报</a></li>
        <li class="layui-nav-item">
            <a href="javascript:;">分时统计</a>
            <dl class="layui-nav-child" style="margin-top: -20px;"> <!-- 二级菜单 -->
                <dd style="padding-top: 5px;"><a href="/add_users">新增用户</a></dd>
                <dd style="padding-top: 5px;"><a href="/active_user">活跃用户数量</a></dd>
                <dd style="padding-top: 5px;"><a href="/paid_count">付费笔数</a></dd>
                <dd style="padding-top: 5px;"><a href="/pay_amount">付费金额</a></dd>
            </dl>
        </li>
        <li class="layui-nav-item">
            <a href="/paid_records">付费玩家记录</a>
        </li>
        <li class="layui-nav-item">
            <a href="/status_editor?page=1&limit=20">消息管理</a>
        </li>
        <li class="layui-nav-item layui-this">
            <a href="/competition_list?page=1&limit=20&proxy_date=&proxy_date_end=">比赛管理</a>
        </li>
        <li class="layui-nav-item">
            <a href="/reward_pending?page=1&start_date=&end_date=&user_id=">比赛发奖管理</a>
        </li>
        <li class="layui-nav-item"><a href="/log_output?page=1&limit=20&user_id=">玩家日志查询</a></li>
        <li class="layui-nav-item"><a href="/version_history?page=1">版本管理</a></li>
    </ul>

    <div style="padding: 50px;margin-bottom: 260px;">
        <form method="post" name="form1" action="#">
            <div id="module">
                <div class="row">
                    <div class="col-md-3">
                        <span>
                            <label>排序：</label>
                            <select class="form-control" id="seq">
                                <option>1</option>
                                <option>2</option>
                                <option>3</option>
                                <option>4</option>
                                <option>5</option>
                                <option>6</option>
                                <option>7</option>
                                <option>8</option>
                                <option>9</option>
                                <option selected>10</option>
                            </select>
                        </span>
                    </div>

                    <div class="col-md-3">
                        <span>
                            <label>类型：</label>
                            <select class="form-control" id="competition_type"  onblur="var comp_type=document.getElementById('competition_type').value;if(comp_type=='1'){document.getElementById('single').setAttribute('selected','true');document.getElementById('loop').removeAttribute('selected');document.getElementById('reward_comp').removeAttribute('selected');}if(comp_type=='2'){document.getElementById('loop').setAttribute('selected','true');document.getElementById('reward_comp').removeAttribute('selected');document.getElementById('single').removeAttribute('selected');}if(comp_type=='3'){document.getElementById('reward_comp').setAttribute('selected','true');document.getElementById('loop').removeAttribute('selected');document.getElementById('single').removeAttribute('selected');}">
                                <option value="1">单次比赛</option>
                                <option value="2">常规赛</option>
                                <option value="3">带海选大奖赛</option>
                            </select>
                        </span>
                        <a href="/competition_list?page=1&proxy_date=&proxy_date_end=&limit=20" class="btn btn-primary" style="margin-left: 150px;margin-top: -100px;">
                            返回
                        </a>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-md-3">
                        <span>
                            <label>标题：</label>
                            <input type="text" class="form-control" placeholder="建议输入10字以内" maxlength="20" id="title" onkeyup="document.getElementById('title1').value=this.value" onblur="document.getElementById('title1').value=this.value"/>
                        </span>
                    </div>
                    <div class="col-md-3">
                        <span>
                            <label>副标题：</label>
                            <input type="text" placeholder="建议输入10字以内" maxlength="20" class="form-control" id="subheading" onkeyup="document.getElementById('subheading1').value=this.value" onblur="document.getElementById('subheading1').value=this.value"/>
                        </span>
                    </div>
                </div>
                <br>

                <div class="row">
                    <div class="col-md-3">
                        <span>
                            <label>报名费用：</label>
                            <input type="text" placeholder="请输入数量，默认为钻石" class="form-control" id="cost" onkeyup="var reg=/^[0-9]*$/; if(!reg.test(this.value))  this.value=''" maxlength="7"  onkeyup="document.getElementById('cost1').value=this.value" onblur="document.getElementById('cost1').value=this.value"/>
                        </span>
                    </div>

                    <div class="col-md-3">
                        <span>
                            <label>费用类型</label>
                            <select class="form-control" id="cost_type">
                                <option>钻石</option>
                               <%-- <option>金币</option>--%>
                            </select>
                        </span>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-md-3">
                        <span style="margin-left: 14px;">
                            <label>参赛人数：</label>
                            <input type="text" class="form-control" placeholder="请输入参赛人数" maxlength="7" id="participant_num"  onkeyup="var reg=/^[0-9]*$/; if(!reg.test(this.value))  this.value=''"  onkeyup="document.getElementById('participant_num1').value=this.value" onblur="document.getElementById('participant_num1').value=this.value">
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

                            <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="show_time" readonly>
                        </span>
                    </div>
                    <div class="col-md-3">
                        <span style="margin-left: 14px;">
                            <label>报名时间：</label>

                            <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="registration_time" readonly>
                        </span>
                    </div>
                </div>
                <br>

                <div class="row">
                    <div class="col-md-3">
                        <span style="margin-left: 14px;">
                            <label>开赛时间：</label>
                            <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="start_date" readonly>
                        </span>
                    </div>

                    <div class="col-md-3">
                        <span style="margin-left: 14px;">
                            <label>结束时间：</label>
                            <input type="text" class="form-control" placeholder="请选择日期..."  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="end_date" onclick="document.getElementById('end_date1').value=this.value" onchange="document.getElementById('end_date1').value=this.value" readonly>
                        </span>
                    </div>
                    <script>
                        $(function () {
                            $("#yu").click(function () {
                                $("#registration_time1").val($("#registration_time").val());
                                $("#start_date1").val($("#start_date").val());
                                $("#end_date1").val($("#end_date").val());
                                $("#show_time1").val($("#show_time").val());
                                $("#password1").val($("#password").val());
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
                                <input type='file' accept='image/png,image/jpeg,image/gif,image/jpg' style='position:absolute;clip:rect(0 0 0 0);' onchange='upload(this)'  name='file'  id='Fupload' />

                                <input type="button" style="margin-left: 15px" class="btn btn-primary" data-toggle="modal" data-target="#model" id="preview" value="图片上传预览">
                            </span>
                            <div class="form-group">
                                <label>图片路径地址：</label><br>
                                <input type='text' class="form-control path" readonly/>
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
                                    msgs = msgs.replace(/<\/pre>/g,"");
                                    msgs = msgs.replace(/<pre>/g,"");
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


                <div class="row">
                    <div class="col-md-3">
                        <span style="margin-left: 14px;">
                            <label>比赛密码：</label>
                            <select class="form-control" id="pass">
                                <option value="0">无密码</option>
                                <option value="1">需要密码</option>
                            </select>
                        </span>
                    </div>
                    <div class="col-md-3">
                        <span style="margin-left: 14px;">
                            <label>&nbsp;</label>
                            <input type="text" class="form-control" placeholder="请输入密码" id="password" onkeyup="var reg = /^[0-9]*$/; if(!reg.test(this.value)) this.value='';" maxlength="6" disabled>
                        </span>
                    </div>
                </div>
                <script>
                    $(function () {
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
                <br>

                <div id="compTwo">
                    <div class="row">
                        <div class="col-md-6">
                            <label>计分规则：</label>
                            <select id="settle" class="form-control">
                                <option value="1">按张计算</option>
                                <option value="2">30分</option>
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
                <script>
                    $(function () {
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
                <br>
                <div class="row">
                    <div class="col-md-6">
                        <span style="margin-left: 14px;">
                            <label>比赛规则：</label>
                            <textarea rows="5" cols="75" class="form-control" id="rule" maxlength="300"  onkeyup="document.getElementById('rule1').value=this.value" onblur="document.getElementById('rule1').value=this.value"></textarea>
                        </span>
                    </div>
                </div>
            </div>


            <br>
            <div id="module1">
                <div style="margin-top: 50px;">
                    <h4><label>比赛奖励</label></h4>

                    <br>

                    <div class="row" id="appe">

                    </div>
                    <br>
                </div>
            </div>
            <div class="row" style="margin-top: 40px;margin-left: -40px;">
                <div class="col-md-6 col-lg-offset-1">
                    <input type="button" value="添加名次" class="btn btn-primary" id="addRank"/>
                    <input type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" id="yu" value="预览"  style="margin-left: 30px;">
                </div>
            </div>
        </form>
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
                                                <option value="1" id="single">单次比赛</option>
                                                <option value="2" id="loop">常规赛</option>
                                                <option value="3" id="reward_comp">带海选大奖赛</option>
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
                                            <input type='text' class="form-control path" readonly/>
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
                                        <span style="margin-left: 14px;">
                                            <label>比赛轮次：</label>
                                            <input type="text" class="form-control" value="3" id="round1" maxlength="2">
                                        </span>
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
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>

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
<script type="text/javascript" src="/assets/layer/common/layui/layui.js"></script>
<script type="text/javascript">
    layui.use(['jquery','layer','element','laypage'],function(){
        window.jQuery = window.$ = layui.jquery;
        window.layer = layui.layer;
    });


    function uploadFile(obj) {
        var id = $(obj).attr('alt');

        $.ajaxFileUpload({
            type:"post",
            url:"/fileUpload",
            secureuri:false,
            fileElementId:$(obj).attr('id'),
            dataType:"text/html",
            data:{'id':id},
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

    var i=1;
    var j=1;



    $(function () {

        $("#appe").append("<div class='col-md-12'>"
            +"<label>第<input type='text' maxlength='3' id='start"+i+"' style='width: 35px;height: 25px; margin-left: 10px;'/><span style='margin: 8px;'>~</span><input type='text' id='end"+i+"' maxlength='3' style='width: 35px;height: 25px; margin-left: 10px;margin-right: 10px;'/>名</label>"

            +"<label style='margin-left: 50px;'>奖励1：</label>"
            +"<select style='margin-top:0px;' id='type"+i+"'>"
            +"<option value='钻石' selected>钻石</option>"
            /*+"<option value='金币'>金币</option>"*/
            +"<option value='实物'>实物</option>"
            /*+"<option value='0'>无</option>"*/
            +"</select>"

            +"<input type='text' style='margin-left: 50px;' maxlength='20' id='numOrName"+i+"' placeholder='填写数量或实物名称'/>"
                +"<label class='btn btn-danger' style='display: none;padding-top:3px;padding-bottom:3px; margin-left: 30px;margin-top: -2px; ' for='fileUpload"+i+"'>实物图片上传</label>"
                +"<input type='file' accept='image/png,image/jpeg,image/gif,image/jpg' style='position:absolute;clip:rect(0 0 0 0);' onchange='uploadFile(this)' alt='"+i+"' name='file'  id='fileUpload"+i+"' />"
                +"<input type='text' style='margin-left: 20px; display: none;' id='number-data-id-"+i+"' readonly/>"

                +"<input type='button' style='margin-left:20px;height: 29px; padding-top: 3px;padding-bottom: 3px;display:none;' data-toggle='modal' data-target='#myModals' id='picPreview"+i+"' class='btn btn-primary' value='图片预览'/>"
            //+"<input type='button' class='btn btn-primary' style='margin-left: 50px;height: 25px; padding-right: 10px;padding-left: 10px; display: none' id='btttn"+j+"' alt='"+j+"' maxlength='10' value='实物图片上传'/>"
            +"</div>"
            +"<br>"
        );
        //图片预览
        $("#picPreview"+i).click(function () {
            var imgUrl = $(this).prev().val();
            $("#pic_preview").attr("src", imgUrl);
        });





        $("#start"+i).keyup(function () {
            var reg = /^[0-9]*$/;
            if(!reg.test(this.value)) this.value='';
        });
        $("#end"+i).keyup(function () {
            var reg = /^[0-9]*$/;
            if(!reg.test(this.value)) this.value='';
        });

        $("#type"+i).change(function () {
            if($(this).val()=="实物"){
                $("#picPreview"+i).show();
                $(this).next().next().show();
                $(this).next().next().next().next().show();
            }else{
                $("#picPreview"+i).hide();
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


        ////////-------------------------------------///////


        
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
                $("#del"+(i-1)).hide();
                ++i;
                ++j;
                $("#appe").append("<div class='col-md-12' id='ele"+i+"'>"
                +"<label id='lab"+i+"'>第<input type='text' maxlength='3' id='start"+i+"' style='width: 35px;height: 25px; margin-left: 10px;'/><span style='margin: 8px;' id='sp'>~</span><input type='text' id='end"+i+"' maxlength='3' style='width: 35px;height: 25px; margin-left: 10px;margin-right: 10px;'/>名</label>"

                +"<label style='margin-left: 50px;' id='reward_name"+i+"'>奖励"+i+"：</label>"
                +"<select style='margin-top:0px;' id='type"+i+"'>"
                    +"<option value='钻石' selected>钻石</option>"
                    /*+"<option value='金币'>金币</option>"*/
                    +"<option value='实物'>实物</option>"
                    /*+"<option value='0'>无</option>"*/
                +"</select>"

                +"<input type='text' style='margin-left: 50px;' maxlength='20' id='numOrName"+i+"' placeholder='填写数量或实物名称'/>"

                    +"<label class='btn btn-danger' style='display: none;padding-top:3px;padding-bottom:3px; margin-left: 30px;margin-top: -2px; ' for='fileUpload"+i+"'>实物图片上传</label>"
                    +"<input type='file' accept='image/png,image/jpeg,image/gif,image/jpg' style='position:absolute;clip:rect(0 0 0 0);' onchange='uploadFile(this)' alt='"+i+"' id='fileUpload"+i+"'  name='file'/>"
                    +"<input type='text' style='margin-left: 20px; display: none;' id='number-data-id-"+i+"' readonly/>"
                    +"<input type='button' style='margin-left:20px;height: 29px; padding-top: 3px;padding-bottom: 3px;display:none;' id='picPreview"+i+"' data-toggle='modal' data-target='#myModals' class='btn btn-primary' value='图片预览'/>"
                    +"<input type='button' style='margin-left:20px;height: 29px; padding-top: 3px;padding-bottom: 3px' class='btn btn-danger' id='del"+i+"' value='删除'/>"
                    +"</div>"
                );

            }

            //图片预览
            $("#picPreview"+i).click(function () {
                var imgUrl = $(this).prev().val();
                $("#pic_preview").attr("src", imgUrl);
            });


            $("#del"+i).click(function () {
                if(i!=1){
                    $("#ele"+i).remove();
                    $("#del"+(i-1)).show();
                    i--;
                    j--;
                }
            });


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

            $("#type"+i).change(function () {
                if($(this).val()=="实物"){
                    $("#picPreview"+i).show();
                    $(this).next().next().show();
                    $(this).next().next().next().next().show();
                }else{
                    $("#picPreview"+i).hide();
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

            //i++;

        });

        //以上是比赛奖励


        
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

                    form1.action="/competition_publish?seq="+seq+"&competition_type="+competition_type+"&title="+title+"&subheading="+subheading+"&cost="+cost+"&cost_type="+cost_type+"&registration_time="+registration_time+"&start_date="+start_date+"&end_date="+end_date+"&rule="+rule+"&participant_num="+participant_num
                        +"&strs="+strs+"&show_time="+show_time+"&compPicUrl="+compPicUrl+"&password="+password+"&round="+round+"&settle="+settle;
                    form1.submit();


                }else{
                    alert("请将比赛奖励数据填写完整");
                }
            }
        })




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
    });
</script>
</html>
