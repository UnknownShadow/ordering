<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>聚牛网络|俱乐部详情</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>

    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
</head>
<style> a:hover{text-decoration: none;} </style>
<body>

    <ul class="layui-nav" lay-filter="">
        <li class="layui-nav-item"><a href="/proxy_overview">代理总览</a></li>
        <li class="layui-nav-item"><a href="/proxy_manage">添加删除代理</a></li>
        <li class="layui-nav-item"><a href="/intent_proxy?page=1">意向代理</a></li>
        <li class="layui-nav-item">
            <a href="/integral_withdrawals_operation?page=1&limit=20">积分提现审核</a>
        </li>
        <li class="layui-nav-item">
            <a href="/proxy_integral_record?page=1&limit=20&user_id=">代理总积分管理</a>
        </li>
        <li class="layui-nav-item">
            <a href="/proxy_log?page=1&limit=20&user_id=">代理日志查询</a>
        </li>
        <li class="layui-nav-item layui-this">
            <a href="/club_management?page=1&limit=20&queryId=&queryStatus=1">俱乐部管理</a>
        </li>
    </ul>
    <div style="padding: 20px;margin-top: 60px;">
        <div class="row" style="margin-left: 30px;margin-top: -90px;margin-bottom: 30px;">
            <div class="col-md-3">
                <div class="form-group">
                    <label>&nbsp;</label><br>
                    <label>&nbsp;</label><br>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label>&nbsp;</label><br>
                    <label>&nbsp;</label><br>
                </div>
            </div>
            <div class="col-md-3">
                <label>&nbsp;</label><br>
                <a href="/club_management?page=1&limit=20&queryId=&queryStatus=1" class="btn btn-primary">
                    返回
                </a>
            </div>
        </div>



        <input type="hidden" value="${clubStatus}" id="clubStatus">
        <input type="hidden" value="${club.id}" id="clubId">
        <div class="row" style="margin-left: 30px;">
            <div class="col-md-3">
                <div class="form-group">
                    <label>俱乐部ID：</label><br>
                    <input type="text" class="form-control" value="${club.code}" disabled/><br>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>俱乐部创建者：</label><br>
                    <input type="text" class="form-control" value="${club.nickname}（${club.creator_id}）" disabled/><br>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>创建时间：</label><br>
                    <input type="text" class="form-control" value="${club.created_at}" disabled/><br>
                </div>
            </div>
        </div>


        <div class="row" style="margin-left: 30px;">
            <div class="col-md-3">
                <div class="form-group">
                    <label>游戏类型：</label><br>
                    <input type="text" class="form-control" value="${club.game_kind}" disabled/><br>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>总人数：</label><br>
                    <input type="text" class="form-control" value="${club.members}" disabled/><br>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>总局数：</label><br>
                    <input type="text" class="form-control" value="${club.game_times}" disabled/><br>
                </div>
            </div>
           <%-- <div class="col-md-6">
                <div class="form-group">
                    <label>公告：</label><br>
                    <input type="text" class="form-control" id="announcement" value="${club.announcement}" maxlength="100" disabled/>
                    <br>
                </div>
            </div>--%>
            <%--<div class="col-md-3">
                <label>&nbsp;</label><br>
                <input type="button" class="btn btn-primary editor" alt="1" value="编辑"/>
            </div>--%>
        </div>

        <%--<div class="row" style="margin-left: 30px;">
            <div class="col-md-3">
                <div class="form-group">
                    <label>总人数：</label><br>
                    <input type="text" class="form-control" value="${club.members}" disabled/><br>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label>名称：</label><br>
                    <input type="text" class="form-control" id="name" value="${club.name}" maxlength="20" disabled/><br>
                </div>
            </div>
            <div class="col-md-3">
                <label>&nbsp;</label><br>
                <input type="button" class="btn btn-primary editor" alt="2" value="编辑"/>
            </div>
        </div>--%>

        <%--<div class="row"  style="margin-left: 30px;">
            <div class="col-md-3">
                <div class="form-group">
                    <label>消耗钻石数：</label><br>
                    <input type="text" class="form-control" disabled/><br>
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>总局数：</label><br>
                    <input type="text" class="form-control" value="${club.game_times}" disabled/><br>
                </div>
            </div>
        </div>--%>


        <div style="text-align: center;margin-left: -180px;">
            <c:if test="${clubStatus == 0}">
                <input type="button" class="btn btn-danger clubStatus" alt="1" value="封停俱乐部">
            </c:if>
            <c:if test="${clubStatus == 1}">
                <input type="button" class="btn btn-primary clubStatus" alt="0" value="解封俱乐部" style="margin-left: 10px;margin-right: 10px;">
            </c:if>
        </div>

        <div style="margin-left: 30px;">
            <h3 style="margin-bottom: 20px;margin-top: 40px;"><label>成员列表</label></h3>
        </div>

        <input type="hidden" value="${limit}" id="y">
        <div class="row " style="margin-top: -20px;">
            <div class="col-md-3 pull-right" style="margin-bottom: 15px;">
                <select class="form-control" id="limit">
                    <option value="10" id="ten">每页显示10条</option>
                    <option value="20" id="fifteen">每页显示20条</option>
                    <option value="50" id="twenty">每页显示50条</option>
                    <option value="100" id="twentyFive">每页显示100条</option>
                </select>
            </div>
        </div>

        <table class="table table-hover" >
            <tr class="active success">
                <th>ID</th>
                <th>昵称</th>
                <th>加入时间</th>
                <th>当日局数</th>
                <th>总局数</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${members}" var="zhi">
                <tr>
                    <td>${zhi.user_id}</td>
                    <td>${zhi.nickname}</td>
                    <td>
                        <fmt:parseDate value="${zhi.created_at}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                        <fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd HH:mm:ss'/>
                    </td>
                    <td>${zhi.today_game_times}</td>
                    <td>${zhi.game_times}</td>
                    <td>
                        <c:if test="${zhi.user_id != club.creator_id}">
                            <a href="javascript:;" class="btn btn-danger kick" alt="${zhi.id}">踢出</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <div class="col-md-4 col-md-offset-8" style="margin-top: 50px;">
            <table>
                <tr>
                    <td>
                        <a href="javaScript:mofen(1)"><img src="bs/images/img_firstpage.gif" width="16" height="16" /></a>&nbsp;
                        <a href="javaScript:mofen(${page-1 })"><img src="bs/images/img_prevpage.gif" width="11" height="16" /></a>&nbsp;${page }/${total }&nbsp;
                    </td>
                    <td align="right">
                        <a href="javaScript:mofen(${page+1 })"><img src="bs/images/img_nextpage.gif" width="12" height="16" /></a>&nbsp;
                        <a href="javaScript:mofen(${total })"><img src="bs/images/img_lastpage.gif" width="16" height="16" /></a>
                        <input type="hidden" value="${total}" id="hidd"/>
                    </td>
                    <td>&nbsp;&nbsp;&nbsp;</td>
                    <td>共 ${count} 条记录</td>
                </tr>
            </table>
        </div>
    </div>

</body>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/assets/layer/common/layui/layui.js"></script>
<script type="text/javascript">
    layui.use(['jquery','layer','element','laypage'],function(){
        window.jQuery = window.$ = layui.jquery;
        window.layer = layui.layer;
    });

    function mofen(page){
        var total = document.getElementById("hidd").value;
        var id = document.getElementById("clubId").value;
        var limit = document.getElementById("y").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/club_details?page="+page+"&club_id="+id+"&limit="+limit;
    }

    $(function () {
        $(".kick").click(function () {
            var id = $(this).attr("alt");
            var clubId = $("#clubId").val();
            var limit = $("#y").val();

            var obj="您确定要将此成员踢出该俱乐部？";
            var r=confirm(obj);
            if (r==true){
                $.ajax({
                    type:'POST',
                    url:'/kick_member',
                    data:{"clubUserId":id},
                    success:function (msg) {
                        if(msg == "1") alert("成功踢出该成员！");
                        window.location.href="/club_details?page=1&club_id="+clubId+"&limit="+limit;
                    }
                });
            }else{
                return false;
            }
        });

        $(".clubStatus").click(function () {
            var status = $(this).attr("alt");
            var clubId = $("#clubId").val();
            var limit = $("#y").val();
            $.ajax({
                type:"post",
                url:"/changeClubStatus",
                data:{"clubStatus":status,"clubId":clubId},
                success:function(msg) {
                    if(msg == "1"){
                        alert("封停成功！");
                    }else if(msg == "2"){
                        alert("解封成功！");
                    }else{
                        alert("操作失败，无法确定停封或解封状态，或无法获取俱乐部ID！");
                    }
                    window.location.href="/club_details?page=1&club_id="+clubId+"&limit="+limit;
                }
            });
        });



        $("#limit").change(function () {
            var limit = $(this).val();
            var clubId = $("#clubId").val();
            window.location.href="/club_details?page=1&club_id="+clubId+"&limit="+limit;
        });
        var y = $("#y").val();
        if(y == 20){
            $("#fifteen").attr("selected","true");
            $("#ten").removeAttr("selected");
            $("#twentyFive").removeAttr("selected");
            $("#twenty").removeAttr("selected");
        }else if(y == 50){
            $("#twenty").attr("selected","true");
            $("#fifteen").removeAttr("selected");
            $("#ten").removeAttr("selected");
            $("#twentyFive").removeAttr("selected");
        }else if(y == 100){
            $("#twentyFive").attr("selected","true");
            $("#twenty").removeAttr("selected");
            $("#fifteen").removeAttr("selected");
            $("#ten").removeAttr("selected");
        }else{
            $("#ten").attr("selected","true");
            $("#fifteen").removeAttr("selected");
            $("#twenty").removeAttr("selected");
            $("#twentyFive").removeAttr("selected");
        }


        //公告和名称的编辑和保存
        /*var i = 1; var j=1;
        $(".editor").click(function () {
            var status = $(this).attr("alt");
            var clubId = $("#clubId").val();

            var announcement = $("#announcement").val();
            var name = $("#name").val();


            if(status == 1){
                if(i%2 != 0){
                    $("#announcement").removeAttr("disabled");
                    $(this).val("保存");
                }else{
                    save(status,clubId,announcement,name);
                    $("#announcement").attr("disabled","true");
                    $(this).val("编辑");
                }
                i++;
            }else if(status == 2){
                if(j%2 != 0){
                    $("#name").removeAttr("disabled");
                    $(this).val("保存");
                }else{
                    save(status,clubId,announcement,name);
                    $("#name").attr("disabled","true");
                    $(this).val("编辑");
                }
                j++;
            }
        });*/

    });

    function save(status,clubId,announcement,name) {
        if(clubId<=0){
            alert("俱乐部ID传入错误，请重新进入当前页面！");
        }else{
            $.ajax({
                type:"post",
                url:"/reviseClub",
                data:{"clubId":clubId,"status":status,"announcement":announcement,"name":name},
                success:function(msg) {
                    if(msg == 1){
                        alert("保存成功！");
                    }else{
                        alert("保存失败，无法获取需要修改俱乐部的参数！");
                    }
                }
            });
        }
    }
</script>
</html>
