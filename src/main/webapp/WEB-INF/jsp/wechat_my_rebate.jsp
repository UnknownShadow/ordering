<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>我的积分</title>
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
</style>
<body>
    <div class="container">
        <input type="hidden" value="${user_status}" id="userStatus"/>
        <input type="hidden" value="${user_id}" id="userID"/>
        <div style="position:relative;margin-bottom: 10px;" >
            <a href="/wxLogin">
                <div style="position: absolute;left: 10px;top: 35%;">
                    <img src="assets/img/back.png" width="38%" alt="">
                </div>
            </a>
            <img src="assets/img/myrebate.jpg" width="100%" alt="">
        </div>

        <div style="margin-top: 40px;">

            <input type="hidden" value="${totalRebateNum}" id="totalrebateNum" />
            <input type="hidden" value="${withdrawalsIntegral}" id="withdrawalsIntegral" />

            <div style="position: absolute;width: 100%;height: 550px;background: white;">

                <div style="position: relative; margin-top: 20px;margin-left:5px;line-height:22px;font-size: 13px;">
                    <span style="font-size: 16px;font-weight: 800;">我的积分：${totalRebateNum}</span><br>
                    <span style="font-size: 16px;font-weight: 800;">冻结积分：${frozen_integral}</span><br>
                    <c:if test="${withdrawalsIntegral!=0}">
                        <span style="font-size: 14px;">提现积分：<span style="font-weight: 700;">${withdrawalsIntegral}</span>（提现中，成功后扣除）</span><br>
                    </c:if>
                    <br>

                    积分规则说明：<br>
                    1.根据子代理充值的钻石数量获得返还积分；<br>
                    2.积分额度根据子代理充值钻石额度变化，见下表；<br>
                    3.每1积分相当于人民币0.1元；<br>
                    4.每次满2000分以上即可申请提现；<br>
                    5.获得新积分自动冻结一周，一周后解冻才可提现；<br>


                   <%-- 积分规则说明：<br>
                    1.根据子代理充值的钻石数量获得返还积分；<br>
                    2.积分额度根据子代理充值钻石额度变化，见下表；<br>
                    3.每1积分相当于人民币0.1元；<br>
                    4.每次满2000分以上即可申请提现；<br>
                    5.申请提现时将冻结对应积分，提现成功后，将扣除对应积分；<br>
                    6.有任何问题请添加微信客服：csw-kefu<br>--%>

                    <div style="margin-top: 10px;">
                        <span style="font-weight: 800;">返积分比例如下：</span>
                        <table class="table table-hover">
                            <tr class="active">
                                <th>礼包</th>
                                <th>钻石</th>
                                <th>返利比例</th>
                                <th>返积分</th>
                            </tr>
                            <tr>
                                <td>极品至尊礼包</td>
                                <td>100000</td>
                                <td>10%</td>
                                <td>10000</td>
                            </tr>
                            <tr>
                                <td>至尊礼包</td>
                                <td>50000</td>
                                <td>10%</td>
                                <td>5000</td>
                            </tr>
                            <tr>
                                <td>高级特惠礼包</td>
                                <td>10000</td>
                                <td>30%</td>
                                <td>3000</td>
                            </tr>
                            <tr>
                                <td>高级礼包</td>
                                <td>5000</td>
                                <td>30%</td>
                                <td>1500</td>
                            </tr>
                            <tr>
                                <td>特惠礼包</td>
                                <td>2000</td>
                                <td>50%</td>
                                <td>1000</td>
                            </tr>
                            <tr>
                                <td>折扣礼包</td>
                                <td>1000</td>
                                <td>50%</td>
                                <td>500</td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>

        </div>
    </div>
</body>
</html>

