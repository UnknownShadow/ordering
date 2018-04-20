<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>潮尚玩|财务审核</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>

    <script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</head>

<body>
    <div style="padding: 20px;margin-bottom: 260px;">
        <div class="table-responsive">
            <h4><label>等待审批列表</label></h4>
            <div class=" pull-right">
                <input type="button" class="btn btn-primary" id="passAll" value="批量通过"/>
                <input type="button" class="btn btn-danger" id="refuseAll" value="批量拒绝"/>
            </div>
            <table class="table table-hover"  id="list">
                <tr class="active">
                    <th>流水号</th>
                    <th>发起人ID</th>
                    <th>发起人昵称</th>
                    <th>接收人ID</th>
                    <th>接收人昵称</th>
                    <th>钻石数量</th>
                    <th>金额数量</th>
                    <th>付款状态</th>
                    <th>状态</th>
                    <th><input type="checkbox" id="all"><label for="all" style="cursor: pointer">全选</label></th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach items="${auditList }" var="zhi">
                    <tr>
                        <td>${zhi.audit_id}</td>
                        <td>${zhi.send_id}</td>
                        <td>${zhi.send_name}</td>
                        <td>${zhi.receive_id}</td>
                        <td>${zhi.receive_name}</td>
                        <td>${zhi.diamond}</td>
                        <td>￥${zhi.money}</td>
                        <td>
                            <c:if test="${zhi.pay_status==0}">
                                <span>扶持计划</span>
                            </c:if>
                            <c:if test="${zhi.pay_status==1}">
                                <span>默认</span>
                            </c:if>
                        </td>
                        <td>待审批</td>
                        <td>
                            <input type="checkbox" value="${zhi.id}" name="items">
                        </td>
                        <td><input type="button" value="通过" id="${zhi.id}" name="0,${zhi.diamond}"  alt="${zhi.receive_id}" onclick="passed(this)" class="btn btn-primary" style="margin:0px;padding-left: 10px;padding-right:10px;"/></td>
                        <td><input type="button" value="拒绝" id="${zhi.id}"  onclick="refuse(this)" class="btn btn-danger" style="margin:0px;padding-left: 10px;padding-right:10px;"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
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
                </tr>
            </table>
        </div>
    </div>
</body>
<script>
    $(function () {
        //全选或全不选
        $("#all").click(function(){
            if(this.checked){
                $("#list :checkbox").prop("checked", true);
            }else{
                $("#list :checkbox").prop("checked", false);
            }
        });

        //设置全选复选框
        $("#list :checkbox").click(function(){
            allchk();
        });

    });
    function allchk(){
        var chknum = $("#list :checkbox").size();//选项总个数
        var chk = 0;
        $("#list :checkbox").each(function () {
            if($(this).prop("checked")==true){
                chk++;
            }
        });
        if(chknum==chk){//全选
            $("#all").prop("checked",true);
        }else{//不全选
            $("#all").prop("checked",false);
        }
    }
    //以上是全选操作


    function mofen(page){
        var total = document.getElementById("hidd").value;
        var page = parseInt(page);
        if(page<=1){
            page=1;
        }else if(page>=total){
            page=total;
        }
        window.location.href="/auditing?page="+page;
    }

   $(function(){
       $("#selectAll").click(function () {
           $("[name=items]:checkbox").attr("checked",this.checked);
       });
       $("[name=items]:checkbox").click(function(){
           var flag=true;
           $("[name=items]:checkbox").each(function(){
               if(!this.checked){
                   flag=false;
               }
           });
           $("#selectAll").attr("checked",flag);
       });



       //批量拒绝
       $("#refuseAll").click(function(){
           var arrayObj = new Array();
           $("[name=items]:checkbox:checked").each(function(){
               var val = $(this).val();
               arrayObj.push(val);
           });
           alert("该方法正在优化");
           //status,id,DiamondAndMoney,receive_id
           //auditAjax(0, c.id,c.name,0);
           //window.location.href="/audit?arrayObj="+arrayObj;
       });


        //批量通过
       /*$("#passAll").click(function(){

           var arrayObj = new Array();

           $("[name=items]:checkbox:checked").each(function(){
               var val = $(this).val();
               arrayObj.push(val);
           });
           window.location.href="/audit?status=1&arrayObj="+arrayObj+"&receive_id=0&DiamondAndMoney=";
       });*/

       //批量拒绝
       /*$("#refuseAll").click(function(){
           var arrayObj = new Array();
           $("[name=items]:checkbox:checked").each(function(){
               var val = $(this).val();
               arrayObj.push(val);
           });
           window.location.href="/audit?status=0&arrayObj="+arrayObj+"&receive_id=0";
       });*/
    });

    function passed(c) {
        auditAjax(1, c.id,c.name,c.alt);
    }

    function refuse(r) {
        auditAjax(0,r.id,0,0);
    }

    function auditAjax(status,id,DiamondAndMoney,receive_id) {
        $.ajax({
            type: 'post',
            url: '/audit',
            data: {"status": status, "id": id, "DiamondAndMoney": DiamondAndMoney, "receive_id": receive_id},
            success: function (msg) {
                if (msg == "1") {
                    alert("审批成功，钻石已发送！");
                }else if(msg == "0"){
                    alert("已拒绝向此用户发送当前审批钻石数量");
                }else {
                    alert(msg);
                }
                window.location.href = "/auditing?page=1";
            }
        });
    }
</script>
</html>
