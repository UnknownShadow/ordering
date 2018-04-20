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

    <link rel="stylesheet" type="text/css" href="bs/dist/DateTimePicker.css" />
    <script src="assets/scripts/jquery.hDialog.js"></script>

    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="assets/scripts/jquery-1.9.1.min.js"></script>

    <script src="assets/DatePicker/lin.js"></script>
    <script src="assets/DatePicker/config.js" ></script>
    <script src="assets/DatePicker/WdatePicker.js" ></script>
    <script src="assets/DatePicker/config.js" ></script>

    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/WdatePicker.css">
    <link type="text/css" rel="stylesheet" href="assets/DatePicker/skin/whyGreen/datepicker.css">
</head>

<body>

    <div style="padding: 20px;margin-bottom: 100px;">
        <form action="#" method="post" class="navbar-form navbar-left">
            <div class="row" style="margin-left: 2px;">
                <div class="col-md-6">
                    <label>玩家ID：</label>
                    <input type="text" class="form-control" placeholder="请输入用户ID..." maxlength="8" id="user_id" value="${user_id}">
                </div>
                <div class="col-md-6">
                    <label>排序：</label>
                    <select class="form-control" id="compositor">
                        <option value="2" id="op2">按日期排序</option>
                        <option value="1" id="op1">按ID排序</option>
                        <option value="3" id="op3">按数量正序排序</option>
                        <option value="4" id="op4">按数量倒序排序</option>
                    </select>
                    <input type="hidden" value="${compositor}" id="hid"/>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-md-6">
                    <label>起始时间：</label>
                    <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="proxy_date" value="${proxy_date}" readonly>
                </div>
                <div class="col-md-6">
                    <label>结束时间：</label>
                    <input type="text" class="form-control" placeholder="请选择日期..." onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="proxy_date_end" value="${proxy_date_end}" readonly>

                </div>
            </div>
        </form>

        <div  class="row">
            <div class="col-md-12  col-md-offset-2" >
                <input type="button" class="btn btn-primary" value="查询" id="search_record">
            </div>
        </div>


        <label>玩家信息：</label>
        <div class="row">
            <div class="col-md-12 table-responsive">
                <table class="table table-hover">
                    <tr class="active">
                        <th>时间和日期</th>
                        <th>收(收入)支(发送)</th>
                        <th>对象ID</th>
                        <th>对象昵称</th>
                        <th>类型</th>
                        <th>数量</th>
                        <th>状态</th>
                    </tr>
                    <c:forEach items="${incomeList }" var="zhi">
                        <tr>
                            <td>${zhi.send_date}</td>
                            <td>${zhi.streaming_name}</td>
                            <td>${zhi.users_id}</td>
                            <td>${zhi.target_name}</td>
                            <td>${zhi.type}</td>
                            <td>${zhi.number}</td>
                            <td>
                                <c:if test="${zhi.status==1}">
                                    成功
                                </c:if>
                                <c:if test="${zhi.status==0}">
                                    失败
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>

                </table>
            </div>
            <div class="col-md-6 col-md-offset-8">
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
    </div>

<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
</body>
<script>
  $(function(){

      var val = $("#hid").val();

      if(val==1){
          $("#op1").attr("selected","true");
      }else if(val==2){
          $("#op2").attr("selected","true");
      }else if(val==3){
          $("#op3").attr("selected","true");
      }else if(val==4){
          $("#op4").attr("selected","true");
      }


      var compositor = $("#compositor").val();

      $("#compositor").change(function () {
         compositor = $("#compositor").val();
      });

      $("#search_record").click(function () {

          var user_id = $("#user_id").val();
          var proxy_date = $("#proxy_date").val();
          var proxy_date_end = $("#proxy_date_end").val();

          window.location.href="/search_record?page=1&user_id="+user_id+"&compositor="+compositor+"&proxy_date="+proxy_date+"&proxy_date_end="+proxy_date_end;
      });

  });

  function mofen(page){
      var user_id = document.getElementById("user_id").value;
      var proxy_date = document.getElementById("proxy_date").value;
      var proxy_date_end = document.getElementById("proxy_date_end").value;
      var compositor = document.getElementById("compositor").value;
      var total = document.getElementById("hidd").value;
      var page = parseInt(page);
      if(page<=1){
          page=1;
      }else if(page>=total){
          page=total;
      }
      window.location.href="/search_record?page="+page+"&user_id="+user_id+"&compositor="+compositor+"&proxy_date="+proxy_date+"&proxy_date_end="+proxy_date_end;
  }

  /*function UrlSearch(){
      var name,value;
      var str=location.href; //取得整个地址栏

      var num=str.indexOf("?")
      str=str.substr(num+1); //取得所有参数   stringvar.substr(start [, length ]

      var arr=str.split("&"); //各个参数放到数组里
      for(var i=0;i < arr.length;i++){
          num=arr[i].indexOf("=");
          if(num>0){
              name=arr[i].substring(0,num);
              value=arr[i].substr(num+1);
              this[name]=value;

              if(name=="page"){
                  alert(value);
              }
          }
      }
  }
  var Request=new UrlSearch(); //实例化*/
  /*alert(Request.id);*/
  //alert(Request.id);
</script>
</html>
