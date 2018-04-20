<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
<head>
    <title>潮尚玩|消息查看</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>

    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
</head>
<style>a:hover{text-decoration: none;}</style>
<body>

    <div style="padding: 20px;margin-bottom: 200px;margin-top: 20px;">
        <div style="text-align: left">
            <h4><label>消息新增</label></h4>
            <a href="/status_editor?page=1&limit=20&status=1" class="btn btn-primary" style="position:absolute;right:300px;margin-top: -42px;">
                返回
            </a>
        </div>
        <hr>
            <input type="hidden" value="${msgRecord.btn_show}" id="btnShow">
            <input type="hidden" value="${msgRecord.specific_user}" id="specificUser">
            <input type="hidden" value="${msgRecord.startTime}" id="startTime">
            <input type="hidden" value="${msgRecord.endTime}" id="endTime">

            <input type="hidden" value="${msgRecord.version}" id="ver">

            <form action="#" method="post" name="simulationForm">
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>展示类型：</label><br>
                            <select id="msg_type" class="form-control">
                                <option>
                                    <c:if test="${msgRecord.msg_type == 1}">文本消息</c:if>
                                    <c:if test="${msgRecord.msg_type == 2}">纯图消息</c:if>
                                </option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <label>标题：</label><br>
                            <input type="text" placeholder="请输入标题" class="form-control" id="title" value="${msgRecord.title}" maxlength="20"/><br>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-8">
                        <div class="form-group">
                            <label>消息内容：</label><br>
                            <textarea placeholder="建议输入112字以内" rows="5" cols="30" maxlength="120" class="form-control" id="content">${msgRecord.content}</textarea><br>
                        </div>
                    </div>
                </div>


                <c:if test="${msgRecord.msg_type == 2}">
                    <div id="pic_all">
                        <div class="row">
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label>图片路径地址：</label><br>
                                    <input type='text' class="form-control" id="path" value="${msgRecord.pic_url}" readonly/>
                                </div>
                                <br>
                            </div>
                        </div>


                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>图片功能：</label><br>
                                    <select id="pic_cmd" class="form-control" >
                                        <option>
                                            <c:if test="${msgRecord.pic_cmd == 0}">无（关闭弹窗）</c:if>
                                            <c:if test="${msgRecord.pic_cmd == 2}">图片跳转</c:if>
                                            <c:if test="${msgRecord.pic_cmd == 1}">图片网页</c:if>
                                            <c:if test="${msgRecord.pic_cmd == 4}">强制退出游戏</c:if>
                                        </option>
                                    </select><br>
                                </div>
                            </div>
                            <c:if test="${msgRecord.pic_cmd == 2}">
                                <div class="col-md-3" id="pic_jump">
                                    <div class="form-group">
                                        <label>图片跳转：</label><br>
                                        <select class="form-control" id="pic_to_page">
                                            <option>
                                                <c:if test="${msgRecord.pic_cmd == 161}">打开消息</c:if>
                                                <c:if test="${msgRecord.pic_cmd == 162}">打开任务</c:if>
                                                <c:if test="${msgRecord.pic_cmd == 2}">打开比赛列表</c:if>
                                                <c:if test="${msgRecord.pic_cmd == 163}">打开宝箱</c:if>
                                                <c:if test="${msgRecord.pic_cmd == 4}">打开充值界面</c:if>
                                            </option>
                                        </select><br>
                                    </div>
                                </div>
                            </c:if>
                        </div>

                        <c:if test="${msgRecord.pic_cmd == 1}">
                            <div class="row" id="pic_url">
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <label>图片打开网页：</label><br>
                                        <input type="text" class="form-control" id="pic_to_url" value="${msgRecord.pic_to_url}">
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </c:if>
                <br>

                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>展示按钮：</label><br>
                            <select id="btn_show" class="form-control" >
                                <option>
                                    <c:if test="${msgRecord.btn_show == 0}">无按钮</c:if>
                                    <c:if test="${msgRecord.btn_show == 1}">有按钮</c:if>
                                </option>
                            </select><br>
                        </div>
                    </div>
                </div>


                <c:if test="${msgRecord.btn_show == 1}">
                    <div id="btn_group">
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>按钮文字：</label><br>
                                    <input type="text" class="form-control" id="btn_title" maxlength="10" value="${msgRecord.btn_title}">
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>按钮功能：</label><br>
                                    <select class="form-control" id="btn_cmd">
                                        <option>
                                            <c:if test="${msgRecord.btn_cmd == 0}">无（关闭弹窗）</c:if>
                                            <c:if test="${msgRecord.btn_cmd == 3}">领取奖励</c:if>
                                            <c:if test="${msgRecord.btn_cmd == 2}">按钮跳转</c:if>
                                            <c:if test="${msgRecord.btn_cmd == 1}">打开网页</c:if>
                                            <c:if test="${msgRecord.btn_cmd == 4}">强制退出游戏</c:if>
                                        </option>
                                    </select><br>
                                </div>
                            </div>

                            <c:if test="${msgRecord.btn_cmd == 2}">
                                <div class="col-md-3" id="btn_open_page">
                                    <div class="form-group">
                                        <label>按钮跳转：</label><br>
                                        <select class="form-control" id="btn_to_page">
                                            <option>
                                                <c:if test="${msgRecord.show_again == 161}">打开消息</c:if>
                                                <c:if test="${msgRecord.show_again == 162}">打开任务</c:if>
                                                <c:if test="${msgRecord.show_again == 2}">打开比赛列表</c:if>
                                                <c:if test="${msgRecord.show_again == 163}">打开宝箱</c:if>
                                                <c:if test="${msgRecord.show_again == 4}">打开充值界面</c:if>
                                            </option>
                                        </select><br>
                                    </div>
                                </div>
                            </c:if>
                        </div>

                        <c:if test="${msgRecord.btn_cmd == 1}">
                            <div class="row" id="btn_open_url">
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <label>按钮打开网页：</label><br>
                                        <input type="text" class="form-control" id="btn_to_url" value="${msgRecord.btn_to_url}">
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>
                    <br>
                </c:if>

                <c:if test="${msgRecord.btn_cmd == 3}">
                    <div class="row" id="reward_show">
                        <div class="col-md-3">
                            <div class="form-group" >
                                <label>奖励：</label><br>
                                <select class="form-control" id="reward_type">
                                    <option value="1">钻石</option>
                                </select><br>
                            </div>
                        </div>
                        <div class="col-md-3" id="reward_type_show">
                            <div class="form-group">
                                <label>&nbsp;</label><br>
                                <input type="text" class="form-control" id="reward_num" value="${msgRecord.reward}">
                            </div>
                        </div>
                    </div>
                </c:if>


                <h4><label>设置发送选项</label></h4>
                <hr>

                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>及时性：</label><br>
                            <select class="form-control" id="immediate">
                                <option>
                                    <c:if test="${msgRecord.immediateness == true}">立即收到</c:if>
                                    <c:if test="${msgRecord.immediateness == false}">下次登录收到</c:if>
                                </option>
                            </select><br>
                        </div>
                    </div>


                    <div class="col-md-3">
                        <div class="form-group">
                            <label>弹出位置：</label><br>
                            <select class="form-control" id="show_place">
                                <option>
                                    <c:if test="${msgRecord.show_place == 0}">任何界面</c:if>
                                    <c:if test="${msgRecord.show_place == 1}">上游主界面</c:if>
                                    <c:if test="${msgRecord.show_place == 2}">比赛列表</c:if>
                                    <c:if test="${msgRecord.show_place == 4}">充值界面</c:if>
                                    <c:if test="${msgRecord.show_place == 6}">激K主界面</c:if>
                                    <c:if test="${msgRecord.show_place ==7}">鱼虾蟹主界面</c:if>
                                </option>
                            </select><br>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>跑马灯：</label><br>
                            <select class="form-control" id="need_scroller">
                                <option value="1">
                                    <c:if test="${msgRecord.need_scroller == true}">展示</c:if>
                                    <c:if test="${msgRecord.need_scroller == false}">不展示</c:if>
                                </option>
                            </select><br>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <label>再次弹出：</label><br>
                            <select class="form-control" id="show_again">
                                <option>
                                    <c:if test="${msgRecord.show_again == 1}">每次登陆弹出</c:if>
                                    <c:if test="${msgRecord.show_again == 2}">每次打开特定界面弹出</c:if>
                                </option>
                            </select><br>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <label>保存消息：</label><br>
                            <select class="form-control" id="need_save">
                                <option>
                                    <c:if test="${msgRecord.need_save == true}">保存到消息列表</c:if>
                                    <c:if test="${msgRecord.need_save == false}">不保存</c:if>
                                </option>
                            </select><br>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>展示次数：(默认3次)</label><br>
                            <input type="text" class="form-control" value="${msgRecord.show_times}" id="show_times"><br>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>发送平台：</label><br>
                            <select class="form-control" id="platform">
                                <option>
                                    <c:if test="${msgRecord.platfrom == 0}">全平台</c:if>
                                    <c:if test="${msgRecord.platfrom == 1}">IOS</c:if>
                                    <c:if test="${msgRecord.platfrom == 2}">Android</c:if>
                                </option>
                            </select><br>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>应用版本：</label><br>
                            <select class="form-control" id="version">
                                <option>
                                    <c:if test="${msgRecord.version == 0}">所有版本</c:if>
                                    <c:if test="${msgRecord.version != 0}">特定版本</c:if>
                                </option>
                            </select><br>
                        </div>
                    </div>
                    <div class="col-md-3" id="speciallyVersion" style="display: none;">
                        <div class="form-group">
                            <label>特定版本号：</label><br>
                            <input type="text" class="form-control" id="versionText" value="${msgRecord.version}"><br>
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
                            <input type="text" class="form-control" id="start_date" readonly />
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>结束时间：</label><br>
                            <input type="text" class="form-control" id="end_date" readonly />
                        </div>
                    </div>
                </div>
            </form>
        </div>

        <script type="text/javascript" src="/assets/layer/common/layui/layui.js"></script>
        <script type="text/javascript">
            layui.use(['jquery','layer','element','laypage'],function(){
                window.jQuery = window.$ = layui.jquery;
                window.layer = layui.layer;
            });

            $(function () {

                //回填处理
                var spUser = $("#specificUser").val();
                if(spUser != ""){
                    $("#speUser").attr("checked","true");
                    $("#allUser").removeAttr("checked");
                    $("#specific_user").removeAttr("disabled");
                    $("#specific_user").text(spUser);
                }else{
                    $("#allUser").attr("checked","true");
                    $("#speUser").removeAttr("checked");
                }

                var startTime = $("#startTime").val();
                var endTime = $("#endTime").val();
                $("#start_date").val(startTime);
                $("#end_date").val(endTime);

                var ver = $("#ver").val();
                if(ver != 0){
                    $("#speciallyVersion").show();

                }
                //回填处理
                //处理特定用户
                $("#allUser").click(function () {
                    $("#specific_user").attr("disabled","true");
                    $("#specific_user").val("");
                });
                $("#speUser").click(function () {
                    $("#specific_user").removeAttr("disabled");
                    $("#specific_user").focus();
                });

            });
        </script>
    </div>
</body>
</html>
