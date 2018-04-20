<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>潮尚玩|消息新增</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <link rel="stylesheet" href="/assets/vendor/bootstrap/css/bootstrap.min.css">

    <link type="text/css" rel="stylesheet" href="/assets/DatePicker/skin/WdatePicker.css">
    <link type="text/css" rel="stylesheet" href="/assets/DatePicker/skin/whyGreen/datepicker.css">
    <script src="/bs/js/jquery-1.11.2.min.js"></script>
    <%--<script src="/assets/DatePicker/lin.js"></script>--%>
    <script src="/assets/DatePicker/WdatePicker.js" ></script>
    <script src="/assets/DatePicker/config.js" ></script>
    <script src="/assets/vendor/bootstrap/js/bootstrap.min.js"></script>

    <script src="/assets/scripts/ajaxfileupload.js" type="text/javascript" charset="utf-8"></script>

    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
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
        <li class="layui-nav-item layui-this">
            <a href="/status_editor?page=1&limit=20">消息管理</a>
        </li>
        <li class="layui-nav-item">
            <a href="/competition_list?page=1&limit=20&proxy_date=&proxy_date_end=">比赛管理</a>
        </li>
        <li class="layui-nav-item">
            <a href="/reward_pending?page=1&start_date=&end_date=&user_id=">比赛发奖管理</a>
        </li>
        <li class="layui-nav-item"><a href="/log_output?page=1&limit=20&user_id=">玩家日志查询</a></li>
        <li class="layui-nav-item"><a href="/version_history?page=1">版本管理</a></li>
    </ul>

    <div style="padding: 30px;">

        <div style="text-align: left">
            <h4><label>消息新增</label></h4>
            <a href="/status_editor?page=1&limit=20" class="btn btn-primary" style="position:absolute;right:300px;margin-top: -42px;">
                返回
            </a>
        </div>
        <hr>
            <form action="#" method="post" name="simulationForm">
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>展示类型：</label><br>
                            <select id="msg_type" class="form-control">
                                <option value="1">文本消息</option>
                                <option value="2">纯图消息</option>
                                <option value="3" disabled>图文消息</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <label>标题：</label><br>
                            <input type="text" placeholder="请输入标题" class="form-control" id="title" maxlength="20"/><br>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-8">
                        <div class="form-group">
                            <label>消息内容：</label><br>
                            <textarea placeholder="建议输入112字以内" rows="5" cols="30" maxlength="200" class="form-control" id="content"></textarea><br>
                        </div>
                    </div>
                </div>

                <div id="pic_all" style="display: none;">
                    <div class="row">
                        <div class="col-md-8">
                            <div class="form-group">
                                <label>图片上传：</label><br>
                                <label class='btn btn-danger' for='upfile'>图片上传</label>
                                <input type='file' accept='image/png,image/jpeg,image/gif,image/jpg' style='position:absolute;clip:rect(0 0 0 0);' onchange='uploadF(this)'  name='file'  id='upfile' />

                                <input type="button" style="margin-left: 15px" class="btn btn-primary" data-toggle="modal" data-target="#model" id="preview" value="图片上传预览">
                            </div>
                            <div class="form-group">
                                <label>图片路径地址：</label><br>
                                <input type='text' class="form-control" id="path" readonly/>
                            </div>
                            <br>
                        </div>
                    </div>


                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>图片功能：</label><br>
                                <select id="pic_cmd" class="form-control" >
                                    <option value="0">无（关闭弹窗）</option>
                                    <option value="2">图片跳转</option>
                                    <option value="1">图片网页</option>
                                    <option value="4">强制退出游戏</option>
                                </select><br>
                            </div>
                        </div>
                        <div class="col-md-3" id="pic_jump" style="display: none;">
                            <div class="form-group">
                                <label>图片跳转：</label><br>
                                <select class="form-control" id="pic_to_page">
                                   <%-- <option value="0" id="noPicJump">无</option>--%>
                                    <option value="161" id="msg" >打开消息</option>
                                    <option value="162" id="mission" >打开任务</option>
                                    <option value="2" id="compList" >打开比赛列表</option>
                                    <option value="163" id="treasure" >打开宝箱</option>
                                    <option value="4" id="recharge" >打开充值界面</option>
                                    <%--<option value="6" id="friend" >打开好友界面</option>--%>
                                </select><br>
                            </div>
                        </div>

                    </div>

                    <div class="row" id="pic_url" style="display: none;">
                        <div class="col-md-8">
                            <div class="form-group">
                                <label>图片打开网页：</label><br>
                                <input type="text" class="form-control" id="pic_to_url">
                            </div>
                        </div>
                    </div>
                </div>
                <br>

                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>展示按钮：</label><br>
                            <select id="btn_show" class="form-control" >
                                <option value="0">无按钮</option>
                                <option value="1">有按钮</option>
                            </select><br>
                        </div>
                    </div>
                </div>


                <div id="btn_group" style="display:none;">
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>按钮文字：</label><br>
                                <input type="text" class="form-control" id="btn_title" maxlength="10">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>按钮功能：</label><br>
                                <select class="form-control" id="btn_cmd">
                                    <option value="0">无（关闭弹窗）</option>
                                    <option value="3">领取奖励</option>
                                    <option value="2">按钮跳转</option>
                                    <option value="1">打开网页</option>
                                    <option value="4">强制退出游戏</option>
                                </select><br>
                            </div>
                        </div>


                        <div class="col-md-3" id="btn_open_page" style="display: none;">
                            <div class="form-group">
                                <label>按钮跳转：</label><br>
                                <select class="form-control" id="btn_to_page">
                                    <%--<option value="0">无（关闭弹窗）</option>--%>
                                    <option value="161">打开消息</option>
                                    <option value="162">打开任务</option>
                                    <option value="2">打开比赛列表</option>
                                    <option value="163">打开宝箱</option>
                                    <option value="4">打开充值界面</option>
                                   <%-- <option value="165">打开好友界面</option>--%>
                                </select><br>
                            </div>
                        </div>
                    </div>

                    <div class="row" id="btn_open_url" style="display: none;">
                        <div class="col-md-8">
                            <div class="form-group">
                                <label>按钮打开网页：</label><br>
                                <input type="text" class="form-control" id="btn_to_url">
                            </div>
                        </div>
                    </div>
                </div>
                <br>

                <div class="row" id="reward_show" style="display: none;">
                    <div class="col-md-3">
                        <div class="form-group" >
                            <label>奖励：</label><br>
                            <select class="form-control" id="reward_type">
                                <option value="1">钻石</option>
                            </select><br>
                        </div>
                    </div>
                    <div class="col-md-3" id="reward_type_show" style="display: none;">
                        <div class="form-group">
                            <label>&nbsp;</label><br>
                            <input type="text" class="form-control" id="reward_num" onkeyup="var reg = /^[0-9]*$/; if(!reg.test(this.value)) this.value='';" maxlength="3" >
                        </div>
                    </div>
                </div>

                <h4><label>设置发送选项</label></h4>
                <hr>

                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>及时性：</label><br>
                            <select class="form-control" id="immediate">
                                <option value="1">立即收到</option>
                                <option  value="0">下次登录收到</option>
                            </select><br>
                        </div>
                    </div>


                    <div class="col-md-3">
                        <div class="form-group">
                            <label>弹出位置：</label><br>
                            <select class="form-control" id="show_place">
                                <option value="0">任何界面</option>
                                <option value="1">上游主界面</option>
                                <option value="2">比赛列表</option>
                                <option value="4">充值界面</option>
                                <option value="6">激K主界面</option>
                                <option value="7">鱼虾蟹主界面</option>
                            </select><br>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>跑马灯：</label><br>
                            <select class="form-control" id="need_scroller">
                                <option value="1">展示</option>
                                <option value="0">不展示</option>
                            </select><br>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <label>再次弹出：</label><br>
                            <select class="form-control" id="show_again">
                                <option value="1">每次登陆弹出</option>
                                <option value="2">每次打开特定界面弹出</option>
                            </select><br>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <label>保存消息：</label><br>
                            <select class="form-control" id="need_save">
                                <option value="1">保存到消息列表</option>
                                <option value="0">不保存</option>
                            </select><br>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>展示次数：(默认3次)</label><br>
                            <input type="text" class="form-control" value="3" id="show_times"><br>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>发送平台：</label><br>
                            <select class="form-control" id="platform">
                                <option value="0">全平台</option>
                                <option value="1">IOS</option>
                                <option value="2">Android</option>
                            </select><br>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>应用版本：</label><br>
                            <select class="form-control" id="version">
                                <option value="0">所有版本</option>
                                <option value="1">特定版本</option>
                            </select><br>
                        </div>
                    </div>
                    <div class="col-md-3" id="speciallyVersion" style="display: none;">
                        <div class="form-group">
                            <label>特定版本号：</label><br>
                            <input type="text" class="form-control" id="versionText"><br>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-md-8">
                        <div class="form-group">
                            <label>用户：</label><br>
                            <label for="allUser" style="cursor:pointer;"><input type="radio" name="users" id="allUser" value="1" style="cursor:pointer;" checked>所有用户</label>
                            <br>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-8">
                        <div class="form-group"><%--回车区别用户ID--%>
                            <label for="speUser"  style="margin-top: -20px;cursor:pointer;"><input type="radio" name="users" id="speUser" value="0" style="cursor:pointer;">特定用户</label>
                            <textarea placeholder="用英文下的逗号隔开用户ID" rows="5" cols="2" id="specific_user" class="form-control" disabled></textarea><br>
                            <br>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>开始时间：</label><br>
                            <input type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="start_date" readonly />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>结束时间：</label><br>
                            <input type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="end_date" readonly />
                        </div>
                        <input type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" id="yu" value="预览"  style="padding:8px 50px 8px 50px;margin-top: 40px;">
                    </div><br>
                    <div class="col-md-3">
                    </div>
                </div>
            </form>
    </div>
    <script>
        $(function () {
            $("#preview").click(function () {
                var picUrl = $("#path").val();
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

                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-primary" data-dismiss="modal" value="退出预览">
                </div>
            </div>
        </div>
    </div>

    <script>
        $(function () {

            $("#msg_type").change(function(){
                var msg_type = $(this).val();
                if(msg_type==2){
                    $("#pic_all").show();
                }else{
                    $("#pic_all").hide();
                }
            });


            //是否显示按钮
            $("#btn_show").change(function(){
                var btn_show = $(this).val();
                if(btn_show==1){
                    $("#btn_group").show();
                }else{
                    $("#btn_to_url").val("");
                    $("#btn_group").hide();
                    $("#reward_show").hide();
                    $("#reward_show").val();
                }
            });

            $("#btn_cmd").change(function(){
                var btn_cmd = $(this).val();
                if(btn_cmd == 3){       //领取奖励
                    $("#btn_open_page").hide();
                    $("#reward_show").show();
                    $("#btn_open_url").hide();

                    $("#reward_type_show").show();
                    $("#reward_num").focus();

                }else if(btn_cmd == 2){     //按钮跳转
                    $("#reward_num").val("");
                    $("#btn_open_page").show();
                    $("#reward_show").hide();
                    $("#btn_open_url").hide();

                }else if(btn_cmd == 1){     //打开网页
                    $("#reward_num").val("");
                    $("#btn_open_url").show();
                    $("#btn_open_page").hide();
                    $("#reward_show").hide();

                }else{      //无（关闭弹窗）
                    $("#reward_num").val("");
                    $("#btn_open_page").hide();
                    $("#reward_show").hide();
                    $("#btn_open_url").hide();
                }

            });


            $("#version").change(function () {
                var version = $(this).val();
                if(version == 1){
                    $("#speciallyVersion").show();
                    $("#versionText").focus();
                }else{
                    $("#speciallyVersion").hide();
                    $("#versionText").val("");
                }
            });

            //处理特定用户
            $("#allUser").click(function () {
                $("#specific_user").attr("disabled","true");
                $("#specific_user").val("");
            });
            $("#speUser").click(function () {
                $("#specific_user").removeAttr("disabled");
                $("#specific_user").focus();
            });


            //图片动作处理
            $("#pic_cmd").change(function () {
                var pic_cmd = $(this).val();
                if(pic_cmd == 1){
                    $("#pic_url").show();
                    $("#pic_jump").hide();
                }else if(pic_cmd == 2){
                    $("#pic_to_url").val("");
                    $("#pic_jump").show();
                    $("#pic_url").hide();
                }else{
                    $("#pic_to_url").val("");
                    $("#pic_url").hide();
                    $("#pic_jump").hide();
                }

            });



            //预览处理
            $("#yu").click(function () {


                var msg_type = $("#msg_type").val();
                if(msg_type==1){
                    $("#pic_all1").hide();
                    $("#textMsg").attr("selected","true");
                    $("#picMsg").removeAttr("selected");
                }else{
                    $("#pic_all1").show();
                    $("#textMsg").removeAttr("selected");
                    $("#picMsg").attr("selected","true");
                }

                $("#title1").val( $("#title").val());
                $("#content1").val($("#content").val());

                var btn_show = $("#btn_show").val();
                if(btn_show==1){
                    $("#btn_h").removeAttr("selected");
                    $("#btn_n").attr("selected","true");
                    $("#btn_group1").show();
                    $("#btn_title1").val($("#btn_title").val());
                }else{
                    $("#btn_h").attr("selected","true");
                    $("#btn_n").removeAttr("selected");
                    $("#btn_group1").hide();
                }


                var btn_cmd = $("#btn_cmd").val();
                if(btn_cmd == 3){       //领取奖励
                    $("#btnG").attr("selected","true");
                    $("#btnO").removeAttr("selected");
                    $("#btnJ").removeAttr("selected");
                    $("#btnN").removeAttr("selected");

                    $("#btn_open_page1").hide();
                    $("#reward_show1").show();
                    $("#btn_open_url1").hide();

                    $("#reward_type_show1").show();
                    $("#reward_num1").focus();
                }else if(btn_cmd == 2){     //按钮跳转
                    $("#btnG").removeAttr("selected");
                    $("#btnO").removeAttr("selected");
                    $("#btnJ").attr("selected","true");
                    $("#btnN").removeAttr("selected");

                    $("#reward_num1").val("");
                    $("#btn_open_page1").show();
                    $("#reward_show1").hide();
                    $("#btn_open_url1").hide();

                }else if(btn_cmd == 1){     //打开网页
                    $("#btnG").removeAttr("selected");
                    $("#btnO").attr("selected","true");
                    $("#btnJ").removeAttr("selected");
                    $("#btnN").removeAttr("selected");

                    $("#reward_num1").val("");
                    $("#btn_open_url1").show();
                    $("#btn_open_page1").hide();
                    $("#reward_show1").hide();
                }else{      //无（关闭弹窗）
                    $("#btnG").removeAttr("selected");
                    $("#btnO").removeAttr("selected");
                    $("#btnJ").removeAttr("selected");
                    $("#btnN").attr("selected","true");

                    $("#reward_num1").val("");
                    $("#btn_open_page1").hide();
                    $("#reward_show1").hide();
                    $("#btn_open_url1").hide();
                }

                $("#reward_num1").val($("#reward_num").val());

                var btn_to_page = $("#btn_to_page").val();
                if(btn_to_page == 161){     //打开消息
                    $("#Omsg").attr("selected","true");
                    $("#Omiss").removeAttr("selected");
                    $("#Olist").removeAttr("selected");
                    $("#Otru").removeAttr("selected");
                    $("#Orech").removeAttr("selected");

                }else if(btn_to_page == 162){   //打开任务

                    $("#Omsg").removeAttr("selected");
                    $("#Omiss").attr("selected","true");
                    $("#Olist").removeAttr("selected");
                    $("#Otru").removeAttr("selected");
                    $("#Orech").removeAttr("selected");
                }else if(btn_to_page == 2){     //打开比赛列表

                    $("#Omsg").removeAttr("selected");
                    $("#Omiss").removeAttr("selected");
                    $("#Olist").attr("selected","true");
                    $("#Otru").removeAttr("selected");
                    $("#Orech").removeAttr("selected");
                }else if(btn_to_page == 163){   //打开宝箱

                    $("#Omsg").removeAttr("selected");
                    $("#Omiss").removeAttr("selected");
                    $("#Olist").removeAttr("selected");
                    $("#Otru").attr("selected","true");
                    $("#Orech").removeAttr("selected");
                }else if(btn_to_page == 4){     //打开充值界面

                    $("#Omsg").removeAttr("selected");
                    $("#Omiss").removeAttr("selected");
                    $("#Olist").removeAttr("selected");
                    $("#Otru").removeAttr("selected");
                    $("#Orech").attr("selected","true");
                }

                $("#path1").val($("#path").val());

                var pic_cmd1 = $("#pic_cmd").val();
                if(pic_cmd1 == 0){  //无（关闭弹窗）
                    $("#pic_cmd1").text("");
                    $("#pic_cmd1").append("<option>无（关闭弹窗）</option>");
                    $("#pic_jump1").hide();
                }else if(pic_cmd1 == 2){    //图片跳转
                    $("#pic_cmd1").text("");
                    $("#pic_cmd1").append("<option>图片跳转</option>");

                    $("#pic_jump1").show();
                    var pic_to_page = $("#pic_to_page").val();
                    if(pic_to_page == "161"){
                        $("#pic_to_page1").text("");
                        $("#pic_to_page1").append("<option>打开消息</option>");
                    }else if(pic_to_page == "162"){
                        $("#pic_to_page1").text("");
                        $("#pic_to_page1").append("<option>打开任务</option>");
                    }else if(pic_to_page == "2"){
                        $("#pic_to_page1").text("");
                        $("#pic_to_page1").append("<option>打开比赛列表</option>");
                    }else if(pic_to_page == "163"){
                        $("#pic_to_page1").text("");
                        $("#pic_to_page1").append("<option>打开宝箱</option>");
                    }else if(pic_to_page == "4"){
                        $("#pic_to_page1").text("");
                        $("#pic_to_page1").append("<option>打开充值界面</option>");
                    }
                }else if(pic_cmd1 == 1){    //图片网页
                    $("#pic_cmd1").text("");
                    $("#pic_cmd1").append("<option>图片网页</option>");
                    $("#pic_jump1").hide();
                }else{
                    $("#pic_cmd1").text("");
                    $("#pic_cmd1").append("<option>强制退出游戏</option>");
                    $("#pic_jump1").hide();
                }


                $("#start_date1").val($("#start_date").val());
                $("#end_date1").val($("#end_date").val());


                if($("#speUser").is(":checked")){
                    $("#speUser1").attr("checked","true");
                    $("#allUser1").removeAttr("checked");
                }else{
                    $("#allUser1").attr("checked","true");
                    $("#speUser1").removeAttr("checked");
                }

                $("#specific_user1").val($("#specific_user").val());
                $("#show_times1").val($("#show_times").val());

                var plat = $("#platform").val();
                if(plat == 0){  //全平台
                    $("#allPlat").attr("selected","true");
                    $("#IOSPlat").removeAttr("selected");
                    $("#AndroidPlat").removeAttr("selected");
                }else if(plat == 1){    //IOS
                    $("#allPlat").removeAttr("selected");
                    $("#IOSPlat").attr("selected","true");
                    $("#AndroidPlat").removeAttr("selected");
                }else{
                    $("#allPlat").removeAttr("selected");
                    $("#IOSPlat").removeAttr("selected");
                    $("#AndroidPlat").attr("selected","true");
                }

                var timely = $("#immediate").val();
                if(timely == 1){
                    $("#nowRe").attr("selected","true");
                    $("#nextRe").removeAttr("selected");
                }else{
                    $("#nextRe").attr("selected","true");
                    $("#nowRe").removeAttr("selected");
                }


                var showPlace = $("#show_place").val();
                if(showPlace==0){   //任何界面
                    $("#anyFace").attr("selected","true");
                    $("#thFace").removeAttr("selected");
                    $("#compeFace").removeAttr("selected");
                    $("#reFace").removeAttr("selected");
                    $("#kFace").removeAttr("selected");
                    $("#fishFace").removeAttr("selected");
                }else if(showPlace==5){     //上游主界面
                    $("#anyFace").removeAttr("selected");
                    $("#thFace").attr("selected","true");
                    $("#compeFace").removeAttr("selected");
                    $("#reFace").removeAttr("selected");
                    $("#kFace").removeAttr("selected");
                    $("#fishFace").removeAttr("selected");
                }else if(showPlace==2){     //比赛列表
                    $("#compeFace").attr("selected","true");
                    $("#thFace").removeAttr("selected");
                    $("#anyFace").removeAttr("selected");
                    $("#reFace").removeAttr("selected");
                    $("#kFace").removeAttr("selected");
                    $("#fishFace").removeAttr("selected");
                }else if(showPlace==4){
                    $("#reFace").attr("selected","true");
                    $("#thFace").removeAttr("selected");
                    $("#compeFace").removeAttr("selected");
                    $("#anyFace").removeAttr("selected");
                    $("#kFace").removeAttr("selected");
                    $("#fishFace").removeAttr("selected");
                }else if(showPlace==6){
                    $("#kFace").attr("selected","true");
                    $("#thFace").removeAttr("selected");
                    $("#compeFace").removeAttr("selected");
                    $("#reFace").removeAttr("selected");
                    $("#anyFace").removeAttr("selected");
                    $("#fishFace").removeAttr("selected");
                }else if(showPlace==7){
                    $("#fishFace").attr("selected","true");
                    $("#thFace").removeAttr("selected");
                    $("#compeFace").removeAttr("selected");
                    $("#reFace").removeAttr("selected");
                    $("#kFace").removeAttr("selected");
                    $("#anyFace").removeAttr("selected");
                }

                //跑马灯预览
                var need_scroller = $("#need_scroller").val();
                if(need_scroller =="1"){
                    $("#need_scroller1").text("");
                    $("#need_scroller1").append("<option>展示</option>");
                }else{
                    $("#need_scroller1").text("");
                    $("#need_scroller1").append("<option>不展示</option>");
                }

                //再次弹出预览
                var show_again = $("#show_again").val();
                if(show_again == "1"){
                    $("#show_again1").text("");
                    $("#show_again1").append("<option>每次登陆弹出</option>");
                }else{
                    $("#show_again1").text("");
                    $("#show_again1").append("<option>每次打开特定界面弹出</option>");
                }

                //保存到消息列表
                var need_save = $("#need_save").val();
                if(need_save == "1"){
                    $("#need_save1").text("");
                    $("#need_save1").append("<option>保存到消息列表</option>");
                }else{
                    $("#need_save1").text("");
                    $("#need_save1").append("<option>不保存</option>");
                }

            });
        });

        //图片上传
        function uploadF(obj) {

            $.ajaxFileUpload({
                type:"post",
                url:"/fileUpload",
                secureuri:false,// 一般设置为false
                fileElementId:"upfile",// 文件上传表单的id <input type="file" id="fileUpload" name="file" />
                dataType:"text/html",
                data:{'id':'1'},
                success:function(msg){
                    if(msg=='<pre style="word-wrap: break-word; white-space: pre-wrap;">0</pre>'){
                        alert("上传错误");
                    }else if(msg=='0'){
                        alert("上传错误");
                    }else{
                        var msgs = msg.replace(/<pre style=\"word-wrap: break-word; white-space: pre-wrap;\">/g,"");
                        msgs = msgs.replace(/<\/pre>/g,"");
                        msgs = msgs.replace(/<pre>/g,"");
                        if(obj.value!=""){
                            alert("上传成功！");
                            $("#path").val(msgs);
                        }else{
                            alert("上传失败，请重新上传");
                            $("#path").val("");
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


    <%--预览--%>
    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document"  style="width: 800px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">预览</h4>
                </div>
                <div class="modal-body" style="width: 800px;">
                    <fieldset disabled style="margin-left: 50px;margin-bottom: 100px;">

                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>展示类型：</label><br>
                                    <select id="msg_type1" class="form-control">
                                        <option value="1" id="textMsg">文本消息</option>
                                        <option value="2" id="picMsg">纯图消息</option>
                                        <option value="3"disabled>图文消息</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>标题：</label><br>
                                    <input type="text" placeholder="请输入标题" class="form-control" id="title1" maxlength="20"/><br>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label>消息内容：</label><br>
                                    <textarea placeholder="建议输入112字以内" rows="5" cols="30" maxlength="120" class="form-control" id="content1"></textarea><br>
                                </div>
                            </div>
                        </div>

                        <div id="pic_all1" style="display: none;">
                            <div class="row">
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <label>图片路径地址：</label><br>
                                        <input type='text' class="form-control" id="path1" readonly/>
                                    </div>
                                    <br>
                                </div>
                            </div>


                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label>图片功能：</label><br>
                                        <select id="pic_cmd1" class="form-control" >
                                            <option value="0" id="shutA">无（关闭弹窗）</option>
                                            <option value="2" id="picJu">图片跳转</option>
                                            <option value="1" id="picUr">图片网页</option>
                                        </select><br>
                                    </div>
                                </div>
                                <div class="col-md-3" id="pic_jump1" style="display: none;">
                                    <div class="form-group">
                                        <label>图片跳转：</label><br>
                                        <select class="form-control" id="pic_to_page1">
                                        </select><br>
                                    </div>
                                </div>
                            </div>

                            <div class="row" id="pic_url1" style="display: none;">
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <label>图片打开网页：</label><br>
                                        <input type="text" class="form-control" id="pic_to_url1">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <br>

                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>展示按钮：</label><br>
                                    <select id="btn_show1" class="form-control" >
                                        <option id="btn_h" value="0">无按钮</option>
                                        <option id="btn_n" value="1">有按钮</option>
                                    </select><br>
                                </div>
                            </div>
                        </div>


                        <div id="btn_group1" style="display:none;">
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label>按钮文字：</label><br>
                                        <input type="text" class="form-control" id="btn_title1" maxlength="10">
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label>按钮功能：</label><br>
                                        <select class="form-control" id="btn_cmd1">
                                            <option id="btnN">无（关闭弹窗）</option>
                                            <option id="btnG">领取奖励</option>
                                            <option id="btnJ">按钮跳转</option>
                                            <option id="btnO">打开网页</option>
                                            <option id="btnE">强制退出游戏</option>
                                        </select><br>
                                    </div>
                                </div>

                                <div class="col-md-3" id="btn_open_page1" style="display: none;">
                                    <div class="form-group">
                                        <label>按钮跳转：</label><br>
                                        <select class="form-control" id="btn_to_page1">
                                            <%--<option value="0">无（关闭弹窗）</option>--%>
                                            <option id="Omsg">打开消息</option>
                                            <option id="Omiss">打开任务</option>
                                            <option id="Olist">打开比赛列表</option>
                                            <option id="Otru">打开宝箱</option>
                                            <option id="Orech">打开充值界面</option>
                                            <%-- <option value="165">打开好友界面</option>--%>
                                        </select><br>
                                    </div>
                                </div>
                            </div>

                            <div class="row" id="btn_open_url1" style="display: none;">
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <label>按钮打开网页：</label><br>
                                        <input type="text" class="form-control" id="btn_to_url1">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <br>

                        <div class="row" id="reward_show1" style="display: none;">
                            <div class="col-md-3">
                                <div class="form-group" >
                                    <label>奖励：</label><br>
                                    <select class="form-control" id="reward_type1">
                                        <option value="1">钻石</option>
                                    </select><br>
                                </div>
                            </div>
                            <div class="col-md-3" id="reward_type_show1" style="display: none;">
                                <div class="form-group">
                                    <label>&nbsp;</label><br>
                                    <input type="text" class="form-control" id="reward_num1">
                                </div>
                            </div>
                        </div>

                        <h4><label>设置发送选项</label></h4>
                        <hr>

                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>及时性：</label><br>
                                    <select class="form-control" id="immediate1">
                                        <option value="1" id="nowRe">立即收到</option>
                                        <option  value="0" id="nextRe">下次登录收到</option>
                                    </select><br>
                                </div>
                            </div>


                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>弹出位置：</label><br>
                                    <select class="form-control" id="show_place1">
                                        <option value="0" id="anyFace">任何界面</option>
                                        <option value="5" id="thFace">上游主界面</option>
                                        <option value="2" id="compeFace">比赛列表</option>
                                        <option value="4" id="reFace">充值界面</option>
                                        <option value="6" id="kFace">激K主界面</option>
                                        <option value="7" id="fishFace">鱼虾蟹主界面</option>
                                        <%-- <option value="165">好友界面</option>--%>
                                    </select><br>
                                </div>
                            </div>
                        </div>


                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>跑马灯：</label><br>
                                    <select class="form-control" id="need_scroller1">
                                    </select><br>
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>再次弹出：</label><br>
                                    <select class="form-control" id="show_again1">
                                    </select><br>
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>保存消息：</label><br>
                                    <select class="form-control" id="need_save1">
                                    </select><br>
                                </div>
                            </div>
                        </div>


                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>展示次数：(默认3次)</label><br>
                                    <input type="text" class="form-control" value="3" id="show_times1"><br>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>发送平台：</label><br>
                                    <select class="form-control" id="platform1">
                                        <option value="0" id="allPlat">全平台</option>
                                        <option value="1" id="IOSPlat">IOS</option>
                                        <option value="2" id="AndroidPlat">Android</option>
                                    </select><br>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>应用版本：</label><br>
                                    <select class="form-control" id="version1">
                                        <option value="0">所有版本</option>
                                        <option value="1">特定版本</option>
                                    </select><br>
                                </div>
                            </div>
                            <div class="col-md-3" id="speciallyVersion1" style="display: none;">
                                <div class="form-group">
                                    <label>特定版本号：</label><br>
                                    <input type="text" class="form-control" id="versionText1"><br>
                                </div>
                            </div>
                        </div>


                        <div class="row">
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label>用户：</label><br>
                                    <label for="allUser" style="cursor:pointer;"><input type="radio" name="users" id="allUser1" value="1" style="cursor:pointer;" checked disabled>所有用户</label>
                                    <br>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-8">
                                <div class="form-group"><%--回车区别用户ID--%>
                                    <label for="speUser"  style="margin-top: -20px;cursor:pointer;"><input type="radio" name="users" id="speUser1" value="0" style="cursor:pointer;" disabled>特定用户</label>
                                    <textarea placeholder="用英文下的逗号隔开用户ID" rows="5" cols="2" id="specific_user1" class="form-control" disabled></textarea><br>
                                    <br>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>开始时间：</label><br>
                                    <input type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="start_date1" readonly />
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>结束时间：</label><br>
                                    <input type="text" class="form-control" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="end_date1" readonly />
                                </div>
                            </div>
                        </div>

                    </fieldset>
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-primary" id="saveMsg" value="发送">
                    <button type="button" class="btn btn-primary" data-dismiss="modal">再次编辑</button>
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

    $(function () {

        $("#saveMsg").click(function () {

            var msg_type = $("#msg_type").val();
            var title = $("#title").val();
            var content = $("#content").val();
            var pic_url = $("#path").val();
            var pic_cmd = $("#pic_cmd").val();
            if(pic_cmd!=1){
                $("#pic_to_url").val("");
            }
            var pic_to_url = $("#pic_to_url").val();

            var btn_title = $("#btn_title").val();

            var btn_show = $("#btn_show").val();            //是否显示按钮，需要添加

            var btn_to_url = $("#btn_to_url").val();

            var btn_cmd = $("#btn_cmd").val();
            var pic_to_page = $("#pic_to_page").val();        //图片跳转到页面；需要添加

            var btn_to_page = $("#btn_to_page").val();

            var reward_type = $("#reward_type").val();
            var reward_num = $("#reward_num").val();
            var immediate = $("#immediate").val();

            var need_exit = $("#need_exit").val();
            var show_place = $("#show_place").val();
            var need_scroller = $("#need_scroller").val();
            var show_again = $("#show_again").val();

            var show_times = $("#show_times").val();

            var specific_user = $("#specific_user").val();

            var send_all = 1; //是否全员发送，需要添加*/
            if($("#speUser").is(":checked")) {
                 send_all = 0;
            }

            var need_save = $("#need_save").val();        //是否 保存到消息列表  //需要添加到后台

            var end_date = $("#end_date").val();

            var start_date = $("#start_date").val();
            var version = "0";
            if($("#version").val()==1){
                version = $("#versionText").val();
            }

            var platform = $("#platform").val();
            var sign_out = $("#sign_out").val();


            if(title==""){
                alert("请输入标题！");
            }else if(content == ""){
                alert("请输入内容");
            }else if(btn_show == 1 && btn_title==""){
                alert("请输入按钮标题");
            }else if(msg_type == 2 && pic_url==""){
                alert("请上传图片");
            }else if(btn_cmd == 3 && reward_num==""){
                alert("请输入奖励数量");
            }else if($("#speUser").is(":checked") && specific_user==""){
                alert("请输入特定用户");
            }else if($("#versionText").val()=="" && $("#version").val()==1){
                alert("请输入特定版本号");
            }else if(start_date ==""){
                alert("请选择开始时间");
            }else if(end_date==""){
                alert("请选择结束时间");
            }else if(start_date>end_date){
                alert("开始时间不能大于结束时间");
            }else{

                var sort_title = encodeURIComponent(title);
                var sort_content = encodeURIComponent(content);


                simulationForm.action = "/publish_msg?title=" + sort_title + "&content=" + sort_content + "&immediate=" + immediate + "&need_exit=" + need_exit + "&btnShow=" + btn_show + "&btnUrl=" + btn_to_url +
                    "&btn_to_page=" + btn_to_page + "&btn_cmd=" + btn_cmd + "&show_place=" + show_place + "&msg_type=" + msg_type + "&pic_url=" + pic_url + "&pic_cmd=" + pic_cmd +
                    "&pic_page=" + pic_to_page + "&pic_to_url=" + pic_to_url + "&reward_num=" + reward_num + "&btn_title=" + btn_title + "&start_time=" + start_date +
                    "&end_time=" + end_date + "&version=" + version + "&specific_user=" + specific_user + "&need_scroller=" + need_scroller + "&show_times=" + show_times +
                    "&platform=" + platform + "&send_all=" + send_all + "&reward_type=" + reward_type+"&need_save="+need_save+"&show_again="+show_again;
                simulationForm.submit();
            }

        });
    });
</script>
</html>
