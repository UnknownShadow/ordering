<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <title>潮尚玩|赠送钻石比例调整</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
</head>
<style>a:hover{text-decoration: none;}</style>
<body>

    <ul class="layui-nav" lay-filter="">
        <li class="layui-nav-item layui-this"><a href="/operation_daily?page=1&limit=20">代理总览</a></li>
        <li class="layui-nav-item">
            <a href="/proxy_manage">添加删除代理</a>
        </li>
        <li class="layui-nav-item">
            <a href="/intent_proxy?page=1">意向代理</a>
        </li>
        <li class="layui-nav-item">
            <a href="/status_editor?page=1&limit=20">积分提现审核</a>
        </li>
        <li class="layui-nav-item">
            <a href="/proxy_integral_record?page=1&limit=20&user_id=">代理总积分管理</a>
        </li>
        <li class="layui-nav-item">
            <a href="/reward_pending?page=1&start_date=&end_date=&user_id=">代理日志查询</a>
        </li>
        <li class="layui-nav-item"><a href="/log_output?page=1&limit=20&user_id=">俱乐部管理</a></li>
    </ul>


    <div style="padding: 20px;">
        <h4 style="font-size: 20px;"><label>设置钻石赠送比例</label></h4>
        <label style="margin: 20px;">正在为（ID:${user_id}）的用户设置赠送钻石比例</label>

        <input type="hidden" value="${user_id}" id="userId">

        <a href="/proxy_preview?page=1&limit=20&userId=" class="btn btn-primary" style="position: absolute;right: 50px;">
            返回
        </a>
        <div style="margin-left: 20px;margin-right: 20px;">
            <div class="row">
                <div class="col-md-12" style="text-align: center;">

                    <table class="table table-hover" id="tab">
                        <tr class="active success">
                            <th>档次</th>
                            <th>房卡单价/10钻</th>
                            <th>购买钻石数量</th>
                            <th>充值金额</th>
                            <th>额外赠送比例</th>
                            <th>赠送钻石</th>
                        </tr>
                        <c:forEach items="${diamondRatios}" var="zhi">
                            <tr>
                                <td>${zhi.grade}</td>
                                <td id="roomCard${zhi.id}" alt="${zhi.money}">${zhi.room_card}</td>
                                <td class="rechargeDiam">${zhi.recharge_diamond}</td>
                                <td>${zhi.recharge_money}</td>
                                <td>
                                    <select class="form-control proportion" alt="${zhi.diamond}" id="${zhi.id}" style="width: 90px;">

                                        <c:choose>
                                            <c:when test="${zhi.diamond*1 == zhi.gift_diamond}">
                                                <option value="0" selected>0%</option>
                                                <option value="0.1">10%</option>
                                                <option value="0.2">20%</option>
                                                <option value="0.3">30%</option>
                                                <option value="0.4">40%</option>
                                                <option value="0.5">50%</option>
                                                <option value="0.6">60%</option>
                                                <option value="0.7">70%</option>
                                                <option value="0.8">80%</option>
                                                <option value="0.9">90%</option>
                                                <option value="1">100%</option>
                                            </c:when>
                                            <c:when test="${zhi.diamond*0.1 == zhi.gift_diamond}">
                                                <option value="0" >0%</option>
                                                <option value="0.1" selected>10%</option>
                                                <option value="0.2">20%</option>
                                                <option value="0.3">30%</option>
                                                <option value="0.4">40%</option>
                                                <option value="0.5">50%</option>
                                                <option value="0.6">60%</option>
                                                <option value="0.7">70%</option>
                                                <option value="0.8">80%</option>
                                                <option value="0.9">90%</option>
                                                <option value="1">100%</option>
                                            </c:when>
                                            <c:when test="${zhi.diamond*0.2 == zhi.gift_diamond}">
                                                <option value="0">0%</option>
                                                <option value="0.1">10%</option>
                                                <option value="0.2" selected>20%</option>
                                                <option value="0.3">30%</option>
                                                <option value="0.4">40%</option>
                                                <option value="0.5">50%</option>
                                                <option value="0.6">60%</option>
                                                <option value="0.7">70%</option>
                                                <option value="0.8">80%</option>
                                                <option value="0.9">90%</option>
                                                <option value="1">100%</option>
                                            </c:when>
                                            <c:when test="${zhi.diamond*0.3 == zhi.gift_diamond}">
                                                <option value="0">0%</option>
                                                <option value="0.1">10%</option>
                                                <option value="0.2">20%</option>
                                                <option value="0.3" selected>30%</option>
                                                <option value="0.4">40%</option>
                                                <option value="0.5">50%</option>
                                                <option value="0.6">60%</option>
                                                <option value="0.7">70%</option>
                                                <option value="0.8">80%</option>
                                                <option value="0.9">90%</option>
                                                <option value="1">100%</option>
                                            </c:when>
                                            <c:when test="${zhi.diamond*0.4 == zhi.gift_diamond}">
                                                <option value="0">0%</option>
                                                <option value="0.1">10%</option>
                                                <option value="0.2">20%</option>
                                                <option value="0.3">30%</option>
                                                <option value="0.4" selected>40%</option>
                                                <option value="0.5">50%</option>
                                                <option value="0.6">60%</option>
                                                <option value="0.7">70%</option>
                                                <option value="0.8">80%</option>
                                                <option value="0.9">90%</option>
                                                <option value="1">100%</option>
                                            </c:when>
                                            <c:when test="${zhi.diamond*0.5 == zhi.gift_diamond}">
                                                <option value="0">0%</option>
                                                <option value="0.1">10%</option>
                                                <option value="0.2">20%</option>
                                                <option value="0.3">30%</option>
                                                <option value="0.4">40%</option>
                                                <option value="0.5" selected>50%</option>
                                                <option value="0.6">60%</option>
                                                <option value="0.7">70%</option>
                                                <option value="0.8">80%</option>
                                                <option value="0.9">90%</option>
                                                <option value="1">100%</option>
                                            </c:when>
                                            <c:when test="${zhi.diamond*0.6 == zhi.gift_diamond}">
                                                <option value="0">0%</option>
                                                <option value="0.1">10%</option>
                                                <option value="0.2">20%</option>
                                                <option value="0.3">30%</option>
                                                <option value="0.4">40%</option>
                                                <option value="0.5">50%</option>
                                                <option value="0.6" selected>60%</option>
                                                <option value="0.7">70%</option>
                                                <option value="0.8">80%</option>
                                                <option value="0.9">90%</option>
                                                <option value="1">100%</option>
                                            </c:when>
                                            <c:when test="${zhi.diamond*0.7 == zhi.gift_diamond}">
                                                <option value="0">0%</option>
                                                <option value="0.1">10%</option>
                                                <option value="0.2">20%</option>
                                                <option value="0.3">30%</option>
                                                <option value="0.4">40%</option>
                                                <option value="0.5">50%</option>
                                                <option value="0.6">60%</option>
                                                <option value="0.7" selected>70%</option>
                                                <option value="0.8">80%</option>
                                                <option value="0.9">90%</option>
                                                <option value="1">100%</option>
                                            </c:when>
                                            <c:when test="${zhi.diamond*0.8 == zhi.gift_diamond}">
                                                <option value="0">0%</option>
                                                <option value="0.1">10%</option>
                                                <option value="0.2">20%</option>
                                                <option value="0.3">30%</option>
                                                <option value="0.4">40%</option>
                                                <option value="0.5">50%</option>
                                                <option value="0.6">60%</option>
                                                <option value="0.7">70%</option>
                                                <option value="0.8" selected>80%</option>
                                                <option value="0.9">90%</option>
                                                <option value="1">100%</option>
                                            </c:when>
                                            <c:when test="${zhi.diamond*0.9 == zhi.gift_diamond}">
                                                <option value="0">0%</option>
                                                <option value="0.1">10%</option>
                                                <option value="0.2">20%</option>
                                                <option value="0.3">30%</option>
                                                <option value="0.4">40%</option>
                                                <option value="0.5">50%</option>
                                                <option value="0.6">60%</option>
                                                <option value="0.7">70%</option>
                                                <option value="0.8">80%</option>
                                                <option value="0.9" selected>90%</option>
                                                <option value="1">100%</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="0" selected>0%</option>
                                                <option value="0.1">10%</option>
                                                <option value="0.2">20%</option>
                                                <option value="0.3">30%</option>
                                                <option value="0.4">40%</option>
                                                <option value="0.5">50%</option>
                                                <option value="0.6">60%</option>
                                                <option value="0.7">70%</option>
                                                <option value="0.8">80%</option>
                                                <option value="0.9">90%</option>
                                                <option value="1">100%</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </select>
                                </td>
                                <td alt="${zhi.id}" id="${zhi.diamond}">
                                    <span id="diam${zhi.id}" >${zhi.gift_diamond}</span>
                                    <input type="hidden" id="tableId" value="${zhi.id}">
                                </td>
                            </tr>
                        </c:forEach>
                    </table>

                    <input type="button" class="btn btn-primary" value="保存修改" id="saveChange" style="padding: 8px 50px 8px 50px;margin-top: 40px;"/>
                </div>
            </div>
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


    $(function () {

        $(".proportion").change(function () {
            var proportion = $(this).val();
            var rechargeDiam = $(this).attr("alt");
            var id = $(this).attr("id");

            var rechargeDiamond = parseInt(rechargeDiam);      //充值的钻石数量
            var result = rechargeDiamond*proportion;        //赠送比例

            var rechargeMoney = $("#roomCard"+id).attr("alt");  //充值的钱数
            var recharge_money = parseInt(rechargeMoney);

            $("#diam"+id).text(result);
            var roomCard = recharge_money/((rechargeDiamond+result)/10);
            roomCard = "￥"+roomCard.toFixed(2)
            $("#roomCard"+id).text(roomCard);
        });


        $("#saveChange").click(function () {
            var user_id = $("#userId").val();
            var diamond="";

            var i=0;var j=1;
            //获取表格中的 （赠送钻石比例的钻石数量）
            $("#tab").find("tr").each(function(){
                if(i!=0){
                    var tdArr = $(this).children();
                    tdArr.each(function () {
                        if(j%6==0){
                            var text = $(this).text();
                            var id = $(this).attr("alt");
                            var recharge_diamond = $(this).attr("id");
                            id = id.replace(/(^\s*)|(\s*$)/g, "");
                            text = text.replace(/(^\s*)|(\s*$)/g, "");
                            diamond = diamond+id+","+text+","+recharge_diamond+",";
                        }
                        j++;
                    })
                }
                i++;
            });

            if(user_id == ""){
                alert("无法获取需要设置钻石比例的 ID，请重新进入此页面！");
            }else if(diamond==""){
                alert("无法获取赠送的钻石数量，请重新进入此页面！");
            }else{
                diamond=diamond.substring(0,diamond.length-1);
                $.ajax({
                    type:'post',
                    url:'/setting_ratios',
                    data:{"id":user_id,"diamond":diamond},
                    success:function (msg) {
                        if(msg=="1"){
                            alert("成功设置该用户的赠送钻石比例。");
                        }else{
                            alert("设置失败，建议重新进入此页面。");
                        }
                        window.location.href="/bestowal_adjustment?user_id="+user_id;
                    }
                });
            }
        });

    });

</script>

</html>

