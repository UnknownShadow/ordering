<%--<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN"
"http://www.wapforum.org/DTD/xhtml-mobile10.dtd" >--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>钻石发送</title>
    <script src="bs/js/holder.js"></script>
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0" />
    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- dialog弹出框 -->
    <link rel="stylesheet" href="assets/dialog/css/global.css">
    <link rel="stylesheet" href="assets/dialog/css/animate.css">
    <link rel="stylesheet" href="assets/dialog/css/dialog.css">
    <script src="assets/dialog/js/dialog.js"></script>
</head>
<style>
    *{ margin: 0px;padding: 0px; }
    body{
        background-color: #f6f6f6;
        margin: 0px;padding: 0px;
    }
    .container{
        margin: 0px;
        padding: 0px;
        overflow:hidden;
    }
    input{
        border:0;
        outline:none;
        background:rgba(0, 0, 0, 0);
        width: 67%;height:40px;
        margin-left: 29%
    }
</style>
<body>
    <div class="container">
        <div style="position:relative;margin-bottom: 10px;" >
            <a href="/wechat_proxy_func">
                <div style="position: absolute;left: 10px;top: 35%;">
                    <img src="assets/img/back.png" width="38%" alt="">
                </div>
            </a>
            <img src="assets/img/sendDiamond.jpg" width="100%" alt="">
        </div>
        <input type="hidden" value="${user_id}" id="userID"/>

        <div style="position: absolute;width: 100%;">

            <div style="position: absolute;margin:0px;padding:0px;width: 100%;height: 60px;">
                <div style="position:absolute;opacity: 1;width: 53%;height: 100%;">
                    <input type="text" id="user_id" style="left:20%;position:absolute;top:15px;margin:0px;" class="form-control input-sm" onfocus="this.value=''" placeholder="请输入用户ID"/>
                </div>
                <div style="position:absolute;right:0px;opacity: 1;width: 30%;height: 100%;">
                    <button class="btn btn-danger form-control input-sm" id="findUser" style="position:absolute;top:15px;margin:0px;padding:0px;width: 55px;left: 0px;">查询</button>
                </div>
            </div>

            <div style="position: absolute;margin:0px;padding:0px;width: 100%;top:70px;">

                    <div style="position: relative;">
                        <div style="position: relative;">
                            <table class="table table-hover">
                                <th class="active" style=" text-align:center;">查询结果</th>
                                <tr >
                                    <td class="active">
                                        <div style="width: 80%;height:auto;margin-top: 5px;margin-left: 48px;">
                                            ID： <c:if test="${user.users_id==null}">无</c:if><c:if test="${user.users_id!=null}">${user.users_id}</c:if>
                                        </div>
                                    </td>
                                </tr>
                                <tr >
                                    <td class="active">
                                        <div style="width: 80%;height:auto;margin-top: 5px;margin-left: 32px;">
                                            昵称： <c:if test="${user.nickname==null}">无</c:if><c:if test="${user.nickname!=null}">${user.nickname}</c:if>
                                        </div>
                                    </td>
                                </tr>
                                <tr >
                                    <td class="active">
                                        <div style="width: 80%;height:auto;margin-top: 5px;margin-left: 32px;">
                                            身份：
                                            <c:if test="${user.user_status==null}">无</c:if><c:if test="${user.user_status!=null}">
                                                <c:if test="${user.user_status==2}"> 代理 </c:if>
                                                <c:if test="${user.user_status==3}"> 玩家 </c:if>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="active">
                                        <div style="width: 80%;height:auto;margin-top: 5px;">
                                            注册日期：
                                            <c:if test="${user.created_at==null}">无</c:if><c:if test="${user.created_at!=null}">
                                                <fmt:parseDate value="${user.created_at}" pattern="yyyy-MM-dd HH:mm:ss" var="joindate"/>
                                                <fmt:formatDate value='${joindate }' pattern='yyyy-MM-dd'/>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                                <tr >
                                    <td class="active">
                                        <div style="width: 80%;height:auto;margin-top: 5px;">
                                            钻石数量： <c:if test="${diamond==-1}">无</c:if><c:if test="${diamond!=-1}">${diamond}</c:if>
                                        </div>
                                    </td>
                                <tr >
                                    <td class="active">
                                        <div style="width: 80%;height:auto;margin-top: 5px;">
                                            绑定手机： <c:if test="${user.phone==null}">无</c:if><c:if test="${user.phone!=null}">${user.phone}</c:if>
                                        </div>
                                    </td>
                                </tr>
                                <tr >
                                    <td class="active">
                                        <span style="position:absolute;margin-top: 6px;">向他发送钻石数量：</span>
                                        <input type="text" id="diamNum" class="form-control" maxlength="8" style="margin-left:150px;width: 140px;" placeholder="输入钻石数量"/>
                                    </td>
                                </tr>
                                <tr >
                                    <td class="active" >
                                        <button class="dw-btn btn-danger has-hover" id="sendDiam" style="margin-left:10%;margin-right:10%;margin-top:25px;width: 80%;">发送</button>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <input type="hidden" value="${user.users_id}" id="ID">
                    <input type="hidden" value="${user.user_status}" id="user_status">
                    <input type="hidden" value="${user.nickname}" id="nick">

                    <!-- 登录者信息 -->
                    <input type="hidden" value="${currentUser.user_status}" id="userStatus">
                    <input type="hidden" value="${currentUser.diamond}" id="myDiamond">
                    <input type="hidden" value="${currentUser.users_id}" id="myId">
            </div>
        </div>
    </div>

</body>
<script>
    $(function () {
        $("#findUser").click(function () {
            var user_id = $("#user_id").val();
            var selfId = $("#userID").val();
            var reg=/^[0-9]*$/;

            if(!reg.test(user_id)){
                $('body').dialog({
                    titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',title:'提示信息',discription:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请输入正确ID！',buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
                });
            }else if(user_id!=""){
                window.location.href="/gamerFinduser?user_id="+user_id+"&status=1&selfId="+selfId;
            }else{
                $('body').dialog({
                    titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',title:'提示信息',discription:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请输入用户ID！',buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
                });
            }
        });


        var cli=0;
        $("#sendDiam").click(function () {

            var diamNum = $("#diamNum").val();      //要发送的钻石数量
            var myDiamond = $("#myDiamond").val();      //我的钻石数量
            var userStatus = $("#userStatus").val();      //我的身份 如：《代理，至尊1...》
            var user_status = $("#user_status").val();      //要发送给对方的身份 如：《代理，至尊1...》
            var nickname = $("#nick").val();
            var myId = $("#myId").val();

            var send_id = $("#ID").val();

            var diam = parseInt(diamNum);
            var myDiam = parseInt(myDiamond);


            var reg=/^[0-9]*$/;


            if(send_id==""){
                $('body').dialog({
                    titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',title:'提示信息',discription:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请先查询ID！',buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
                });
            }else if(diam==0){
                $('body').dialog({
                    titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',title:'提示信息',discription:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;钻石数量不能为0！',buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
                });
            }else if(diam>myDiam){
                $('body').dialog({
                    titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',title:'提示信息',discription:'<center>发送的钻石数量不能大于自己已有的！</center>',buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
                });
            }else if(!reg.test(diamNum)){
                $('body').dialog({
                    titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',title:'提示信息',discription:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请输入正确的钻石数量！',buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
                });
            }else if(diamNum==""){
                $('body').dialog({
                    titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',title:'提示信息',discription:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;钻石数量不能为空！',buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
                });
            }else if(myId==send_id){    //不可以给自己发送钻石
                $('body').dialog({
                    titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',title:'提示信息',discription:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;不可以给自己发钻石！',buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
                });
            }else if(user_status!=3){    //代理 和 至尊 只能给玩家发送钻石
                $('body').dialog({
                    titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'warning',title:'提示信息',discription:'&nbsp;&nbsp;&nbsp;您只能给玩家发送钻石！',buttonsSameWidth:true,buttons:[{name: '确定',className: 'defalut'}]
                });
            }else{
                $('body').dialog({
                    titleFontSize:16,discriptionFontSize:14,buttonsFontSize:14,width:200,type:'danger',title:'确认发送',discription:'<center>您确认向 '+nickname+'（ID：'+send_id+'）发送'+diam+'钻&nbsp;</center>',buttonsSameWidth:true,buttons:[{name: '确定并发送',className: 'defalut'},{name: '取消',className: 'reverse'}]
                },function(ret) {
                    if(ret.index===0){

                        if(cli==0) {
                            cli=1;
                            $.ajax({
                                type: "post",
                                url: "/change",
                                async: false,
                                data: {"proxy_id": send_id, "diamond": diam},
                                success: function (msg) {

                                    var jsons = eval(msg);

                                    $.each(jsons, function (k, v) {

                                        if (v.flag == 1) {
                                            window.location.href = "/gamerFinduser?user_id=" + send_id + "&status=1";
                                        }
                                        if (v.flag == 0) {
                                            alert("钻石不能超出自己已有的!");
                                        }
                                    });
                                }
                            });
                        }

                    }
                });
            }
        });

    });
</script>
</html>

