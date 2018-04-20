<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>潮尚玩|代理</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <!--修改密码样式-->
    <link rel="stylesheet" href="assets/css/common.css"/><!-- 基本样式 -->
    <link rel="stylesheet" href="assets/css/animate.css"/> <!-- 动画效果 -->
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>
    <script src="assets/scripts/jquery.hDialog.js"></script>
    <!-- VENDOR CSS -->
    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="bs/js/bootstrap-paginator.min.js"></script>

</head>
<body>
    <div style="padding: 20px;">
        <div class="box">
            <div class="demo">
                <a href="javascript:;" class="zoomIn dialog btn">修改密码</a>
                <span>*初始密码: ID+123456，为了您的账号安全，初次登陆后请立即修改！</span>
            </div>
            <!-- 注意：请将要放入弹框的内容放在比如id="HBox"的容器中，然后将box的值换成该ID即可，比如：$(element).hDialog({'box':'#HBox'}); -->
            <div id="HBox">
                <form action="" method="post" onsubmit="return false;">
                    <ul class="list">
                        <li class="form-group">
                            <label>新密码<span style="color:#ff0000">*</span></label>
                            <input type="text" id="pass" value="" class="form-control" placeholder="最多输入16位" maxlength="16" onkeyup="var reg = /^[^\u4e00-\u9fa5]{0,}$/;var regs=/^ +| +$/g; if(!reg.test(this.value))  this.value='';if(regs.test(this.value)) this.value=this.value.replace(/^ +| +$/g,'');"/>
                        </li>
                        <li class="form-group">
                            <label>确认密码<span style="color:#ff0000">*</span></label>
                            <input type="text" id="confirmpass" value="" class="form-control"  placeholder="最多输入16位" maxlength="16" onkeyup="var reg = /^[^\u4e00-\u9fa5]{0,}$/;var regs=/^ +| +$/g; if(!reg.test(this.value))  this.value='';if(regs.test(this.value)) this.value=this.value.replace(/^ +| +$/g,'');"/>
                        </li>
                        <li class="form-group">
                            <label>手机号<span style="color:#ff0000">*</span></label>
                            <div class="input-group">
                                <%--<input type="text" class="form-control" id="phone"  maxlength="11">--%>
                                <span class="input-group-btn"><input type="button" class="btn btn-primary" onclick="clock()" id="gets" value="获取绑定的手机短信验证码"></span>
                            </div>
                        </li>
                        <li class="form-group">
                            <label>请输入短信验证码<span style="color:#ff0000">*</span></label>
                            <input type="text" placeholder="请输入短信验证码" class="form-control" maxlength="4" id="verf"/>
                        </li>
                        <li><input type="submit" value="修改" class="submitBtn" /></li>
                    </ul>
                </form>
            </div><!-- HBox end -->
        </div><!-- box end -->

        <input type="hidden" value="${userList.user_status}" id="userStatus">
        <label>我的基本信息：</label>
        <div id="player">
            <div class="row tb">
                <div class="col-md-12">
                    <table class="table table-hover table-bordered">
                        <tr class="active">
                            <th>玩家昵称</th>
                            <th>玩家ID</th>
                            <th>用户身份</th>
                            <th>后台权限</th>
                            <th>钻石数量</th>
                            <th>子代理数量</th>
                            <%--<th id="tops">升级所需</th>--%>
                        </tr>
                        <tr id="ttr">
                            <td>${userInfo.nickname}</td>
                            <td id="user_id">${userInfo.users_id}</td>
                            <td>
                                <%--<c:if test="${userList.user_status==8}"> 极品至尊 </c:if>
                                <c:if test="${userList.user_status==7}"> 至尊1 </c:if>
                                <c:if test="${userList.user_status==6}"> 至尊2 </c:if>
                                <c:if test="${userList.user_status==5}"> 至尊3 </c:if>
                                <c:if test="${userList.user_status==4}"> 至尊4 </c:if>
                                <c:if test="${userList.user_status==1}"> 至尊5 </c:if>--%>
                                <c:if test="${userInfo.user_status==2}"> 代理 </c:if>
                                <c:if test="${userInfo.user_status==3}"> 玩家 </c:if>
                            </td>
                            <td>${userInfo.perm}</td>
                            <td id="diam">${userInfo.diamond}</td>
                            <td>${userInfo.childproxytotal}</td>
                            <%--<td id="topes" width="300px">再充<label style="margin-right: 5px;margin-left: 5px;">${condition}</label>钻，即可升级为<br>至尊合伙人，享受直接拿钻超低价</td>--%>
                        </tr>
                        <input type="hidden" value="${userList.users_id}" id="hide">
                    </table>
                </div>
            </div>
        </div>
    </div>
    <br>
            <%--<div id="honourable">
                <div class="row tb">
                    <div class="col-md-12">
                        <table class="table table-hover table-bordered">
                            <tr class="active">
                                <th>档次</th>
                                <th>房卡单价/10钻</th>
                                <th>晋级条件（按拿货金额）</th>
                                <th>单次最低拿货数量(钻)</th>
                                <th>单次最低房卡数量</th>
                                <th>原价</th>
                                <th>折扣(折)</th>
                                <th>拿货价格</th>
                            </tr>
                            <tr id="eight">
                                <td>极品至尊</td>
                                <td>¥1.0</td>
                                <td>累计充值5万（或500万钻）</td>
                                <td>100,000</td>
                                <td>10,000</td>
                                <td>¥50,000</td>
                                <td>2</td>
                                <td>¥10,000</td>
                            </tr>
                            <tr id="seven">
                                <td>至尊1</td>
                                <td>¥1.1</td>
                                <td>累计充值2.5万（或25万钻）</td>
                                <td>60,000</td>
                                <td>6,000</td>
                                <td>¥30,000</td>
                                <td>2.2</td>
                                <td>¥6,600</td>
                            </tr>
                            <tr id="six">
                                <td>至尊2</td>
                                <td>¥1.2</td>
                                <td>累计充值1.2万（或12万钻）</td>
                                <td>40,000</td>
                                <td>4,000</td>
                                <td>¥20,000</td>
                                <td>2.4</td>
                                <td>¥4,800</td>
                            </tr>
                            <tr id="five">
                                <td>至尊3</td>
                                <td>¥1.3</td>
                                <td>累计充值1万（或10万钻）</td>
                                <td>30,000</td>
                                <td>3,000</td>
                                <td>¥15,000</td>
                                <td>2.6</td>
                                <td>¥3,900</td>
                            </tr>
                            <tr id="four">
                                <td>至尊4</td>
                                <td>¥1.4</td>
                                <td>累计充值8千（或8万钻）</td>
                                <td>10,000</td>
                                <td>1,000</td>
                                <td>¥5,000</td>
                                <td>2.8</td>
                                <td>¥1,400</td>
                            </tr>
                            <tr id="one">
                                <td>至尊5</td>
                                <td>¥1.5</td>
                                <td>累计充值5千（或5万钻）</td>
                                <td>5,000</td>
                                <td>500</td>
                                <td>¥2,500</td>
                                <td>3</td>
                                <td>¥750</td>
                            </tr>
                            <tr>
                                <td>至尊</td>
                                <td>¥2.0</td>
                                <td>不足最低拿货数量时，散货价格</td>
                                <td>1000</td>
                                <td>100</td>
                                <td>¥500</td>
                                <td>4</td>
                                <td>¥200</td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>--%>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</body>
<script>
    /*验证码的发送*/

    function GetRandomNum(Min,Max)
    {
        var Range = Max - Min;
        var Rand = Math.random();
        return(Min + Math.round(Rand * Range));
    }

    var i=60;
    var intv;
    function dd() {
        var val = $("#gets").val(""+(--i)+"s后重试");
        $("#gets").attr("disabled","true");
        if (i == 0) {
            window.clearInterval(intv);
            $("#gets").removeAttr("disabled","true");
            $("#gets").val("获取绑定的手机短信验证码");
            i = 60;
        }
    }
    var verify=0;
    function clock() {
        //var phone = $("#phone").val();
        //var PhoneReg = /^0{0,1}(13[0-9]|15[0-9]|153|156|18[1-9])[0-9]{8}$/; //手机正则
        //if(PhoneReg.test(phone)){
            intv=self.setInterval("dd()",1000);
            verify=GetRandomNum(1234,9876);
            $.ajax({
                type:"post",
                url:"/smsVerification",
                data:{"code":verify},
                success:function(msg) {
                    if(msg==0){
                        alert("请先绑定手机号码");
                        return false;
                    }
                }
            });
      /*  }else {
            alert("请输入正确的手机号");
        }*/
    }
    /*验证码的发送*/



        $(function () {
            $("#honourable").hide();
            var user_status = $("#userStatus").val();

            if((user_status>=4 && user_status<=8) || user_status == 1){
                $("#honourable").show();
                $("#topes").hide();
                $("#tops").hide();

                if(user_status==8){
                    $("#eight").addClass("success");
                    $("#seven").removeClass("success");
                    $("#six").removeClass("success");
                    $("#five").removeClass("success");
                    $("#four").removeClass("success");
                    $("#one").removeClass("success");
                }else if(user_status==7){
                    $("#seven").addClass("success");
                    $("#eight").removeClass("success");
                    $("#six").removeClass("success");
                    $("#five").removeClass("success");
                    $("#four").removeClass("success");
                    $("#one").removeClass("success");
                }else if(user_status==6){
                    $("#six").addClass("success");
                    $("#seven").removeClass("success");
                    $("#eight").removeClass("success");
                    $("#five").removeClass("success");
                    $("#four").removeClass("success");
                    $("#one").removeClass("success");
                }else if(user_status==5){
                    $("#five").addClass("success");
                    $("#seven").removeClass("success");
                    $("#six").removeClass("success");
                    $("#eight").removeClass("success");
                    $("#four").removeClass("success");
                    $("#one").removeClass("success");
                }else if(user_status==4){
                    $("#four").addClass("success");
                    $("#seven").removeClass("success");
                    $("#six").removeClass("success");
                    $("#eight").removeClass("success");
                    $("#five").removeClass("success");
                    $("#one").removeClass("success");
                }else if(user_status==1){
                    $("#one").addClass("success");
                    $("#seven").removeClass("success");
                    $("#six").removeClass("success");
                    $("#eight").removeClass("success");
                    $("#four").removeClass("success");
                    $("#five").removeClass("success");
                }
            }if(user_status==3){
                $("#topes").hide();
                $("#tops").hide();
            }

            //以上是控制我的资料中 根据层级显示




            //----------------------
                $("#pass").change(function () {
                    $("#pass").replace(/^ +| +$/g,'');
                });
                $("#confirmpass").change(function () {
                    $("#confirmpass").replace(/^ +| +$/g,'');
                });

            //----------------------this.value=this.value.replace(/^ +| +$/g,'')


        var id = $("#hide").val();
        var proxy_id = $("#proxy_id").val();

        var status = "4";
        $("#status").change(function(){
           status = $("#status").val();
        });



        var $el = $('.dialog');
        $el.hDialog();

        //提交并验证表单
        $('.submitBtn').click(function() {
            var EmailReg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/; //邮件正则
            var PhoneReg = /^0{0,1}(13[0-9]|15[0-9]|153|156|18[7-9])[0-9]{8}$/ ; //手机正则
            var $nickname = $('.nickname');
            var $email = $('.email');
            var $phone = $('.phone');


            var verf = $("#verf").val();
            var confirmpass = $("#confirmpass").val();
            var pass = $("#pass").val();
            if(confirmpass==""||pass==""){
                alert("密码为空,请输入密码！！！");
            }else if(confirmpass!=pass){
                alert("两次密码不一致，请重新输入！！");
            }else if(verify!=verf){
                alert("验证码错误！");
            }else if(verf==""){
                alert("请输入验证码");
            }else{

                $.ajax({
                    type:"post",
                    url:"/modifyPassword",
                    data:{"pass":pass,"user_id":id},
                    success:function(msg) {

                        if(msg==1){
                            alert("密码修改成功，请重新登陆，谢谢！！！");
                            setTimeout(function(){
                                $el.hDialog('close',{box:'#HBox'});
                            },800);
                                window.location.href="/loginout";
                        };
                    }
                });
            }
        });


        ///------------分页查询子代理数据------------////
            var carId=1;
            var pageCount;
            var currentPage;
            $.ajax({
                type:"post",
                url:"/ajaxmfen",
                data:{"carId":carId,"user_id":id},
                success:function(data){
                    if(data!=null){
                        var json = eval(data);
                        $.each(json,function(index,items){
                            var obj ="<tr>"
                                +"<td width='200px'>"+items.name+"</td>"
                                +"<td width='150px'>"+items.id+"</td>"
                                +"<td width='150px'>"+items.proxy_date+"</td>"
                                +"<td width='150px'>"+items.diamond+"</td>"
                                +"<td width='140px'>"
                                +"<div class='input-group' style='width: 150px;'>"
                                +"<input type='text' class='form-control' placeholder='数量...' maxlength='5' id='number-data-id-"+items.id+"'/>"
                                +"<span class='input-group-btn'><input type='button' class='btn btn-primary' data-id='"+ items.id + "' onclick='send(this);' value='发送'></span>"
                                +"</div>"
                                +"</td>"
                                +"</tr>"

                            $("#tttr").append(obj);
                            pageCount = items.pageCount;		//总页数
                            currentPage = items.CurrentPage;	//得到当前页
                        });

                        var options = {
                            bootstrapMajorVersion:3,
                            currentPage:currentPage,
                            totalPages: pageCount,
                            size:"small",
                            numberOfPages: 4,
                            itemContainerClass:function(type, page, currentPage){
                                return (page==currentPage)?"active":"c";
                            },
                            itemTexts:function(type,page,current){
                                switch(type){
                                    case "first":
                                        return "首页";
                                    case "prev":
                                        return "上一页";
                                    case "next":
                                        return "下一页";
                                    case "last":
                                        return "末页";
                                    case "page":
                                        return page;

                                }
                            },	//点击事件
                            onPageClicked:function(event,originalEvent,type,page){
                                $("#tttr").empty();
                                $.ajax({
                                    type:"post",
                                    url:"/ajaxOnclick",
                                    data:{"page":page,"user_id":id},
                                    success:function(data1){
                                        if(data1!=null){
                                            var json = eval(data1);

                                            $.each(json,function(index,items){
                                                var obj ="<tr>"
                                                    +"<td width='200px' id='#number-name-"+items.name+"'>"+items.name+"</td>"
                                                    +"<td width='150px'>"+items.id+"</td>"
                                                    +"<td width='150px'>"+items.proxy_date+"</td>"
                                                    +"<td width='150px'>"+items.diamond+"</td>"
                                                    +"<td width='140px'>"
                                                    +"<div class='input-group' style='width: 150px;'>"
                                                    +"<input type='text' class='form-control' placeholder='数量...' maxlength='5' id='number-data-id-"+items.id+"'/>"
                                                    +"<span class='input-group-btn'><input type='button' class='btn btn-primary'  data-id='"+ items.id + "' onclick='send(this);' value='发送'></span>"
                                                    +"<input type='hidden' value='"+items.name+"' id='e'/>"
                                                    +"</div>"
                                                    +"</td>"
                                                    +"</tr>"
                                                $("#tttr").append(obj);
                                            });
                                        }
                                    }
                                });
                            }
                        }
                        $(".examlpe").bootstrapPaginator(options);
                    }
                }
            });

        });



</script>

</html>
