<html lang="en">
<head>
    <title>潮尚玩|版本配置</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="/assets/scripts/jquery-1.9.1.min.js"></script>
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
        <li class="layui-nav-item">
            <a href="/status_editor?page=1&limit=20">消息管理</a>
        </li>
        <li class="layui-nav-item">
            <a href="/competition_list?page=1&limit=20&proxy_date=&proxy_date_end=">比赛管理</a>
        </li>
        <li class="layui-nav-item">
            <a href="/reward_pending?page=1&start_date=&end_date=&user_id=">比赛发奖管理</a>
        </li>
        <li class="layui-nav-item"><a href="/log_output?page=1&limit=20&user_id=">玩家日志查询</a></li>
        <li class="layui-nav-item layui-this"><a href="/version_history?page=1">版本管理</a></li>
    </ul>

    <div style="padding: 20px;margin-bottom: 100px;">
        <div class="row" style="margin-left: 30px;">
            <div class="col-md-4">
                <div class="form-group">
                    <label>版本号：</label><br>
                    <input type="text" placeholder="请输入版本号" class="form-control" id="title" maxlength="20"/><br>
                </div>
                <div class="form-group">
                    <label>对应平台：</label><br>
                    <select id="platform" class="form-control">
                        <option value="1">IOS</option>
                        <option value="2">Android</option>
                    </select><br>
                </div>
                <div class="form-group">
                    <label>下载地址：</label><br>
                    <input type="text" class="form-control" placeholder="请输入下载地址" id="download" />
                </div>
                <br>
                <div class="form-group">
                    <label>是否强制更新：</label><br>
                    <select id="force" class="form-control">
                        <option value="1">强制更新</option>
                        <option value="0">不需要强制更新</option>
                    </select><br>
                </div>
                <br>
                <div class="form-group">
                    <label>更新说明：</label><br>
                    <textarea placeholder="请输入更新说明,字数不能高于80个字符" rows="5" cols="30" maxlength="80" class="form-control" id="content"></textarea><br>
                </div>
            </div>
        </div>
        <div class="row" style="padding-top: 20px;">
            <div class="col-md-3 col-lg-offset-3">
                <div class="form-group">
                    <input type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" id="yu" value="预览">
                </div>
            </div>
        </div>

        <script>
            $(function () {
                $("#yu").click(function () {
                    $("#title1").val($("#title").val());
                    $("#content1").val($("#content").val());
                    $("#download1").val($("#download").val());
                    var plat = $("#platform").val();
                    if(plat == 1){
                        $("#platform1").text("");
                        $("#platform1").append("<option>IOS</option>");
                    }else{
                        $("#platform1").text("");
                        $("#platform1").append("<option>Android</option>");
                    }
                    var force = $("#force").val();
                    if(force == 1){
                        $("#force1").text("");
                        $("#force1").append("<option>强制更新</option>");
                    }else{
                        $("#force1").text("");
                        $("#force1").append("<option>不需要强制更新</option>");
                    }
                });
            });
        </script>

        <#--预览-->
        <!-- Modal -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document" style="width: 700px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">预览</h4>
                    </div>
                    <div class="modal-body">

                        <fieldset disabled>
                            <div class="row" style="margin-left: 30px;">
                                <div class="col-md-11">
                                    <div class="form-group">
                                        <label>版本号：</label><br>
                                        <input type="text" placeholder="请输入版本号" class="form-control" id="title1" maxlength="20" /><br>
                                    </div>
                                    <div class="form-group">
                                        <label>对应平台：</label><br>
                                        <select id="platform1" class="form-control"></select><br>
                                    </div>
                                    <div class="form-group">
                                        <label>下载地址：</label><br>
                                        <input type="text" class="form-control" placeholder="请输入下载地址" id="download1"/>
                                    </div>
                                    <br>
                                    <div class="form-group">
                                        <label>是否强制更新：</label><br>
                                        <select id="force1" class="form-control"></select><br>
                                    </div>
                                    <br>
                                    <div class="form-group">
                                        <label>更新说明：</label><br>
                                        <textarea placeholder="请输入更新说明" rows="5" cols="30" maxlength="120" class="form-control" id="content1"></textarea><br>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                    <div class="modal-footer">
                        <a href="javascript:;" class="btn btn-primary publish" style="margin-left: 2%;" id="publish">发布</a>
                        <input type="button" class="btn btn-primary" data-dismiss="modal" value="再次编辑">
                    </div>
                </div>
            </div>
        </div>
    </div>

<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/assets/layer/common/layui/layui.js"></script>
<script type="text/javascript">
    layui.use(['jquery','layer','element','laypage'],function(){
        window.jQuery = window.$ = layui.jquery;
        window.layer = layui.layer;
    });


    $(".publish").click(function () {
        var version = $("#title").val();
        var platform = $("#platform").val();
        var download = $("#download").val();
        var content = $("#content").val();
        var force = $("#force").val();

        if(version==""){
            alert("请输入版本号");
        }else if(download==""){
            alert("请输入下载地址");
        }else if(content==""){
            alert("请输入更新说明");
        }else{
            window.location.href="/publish_version?version="+version+"&platform="+platform+"&download="+download+"&content="+content+"&force="+force;
        }
    });
</script>
</html>
