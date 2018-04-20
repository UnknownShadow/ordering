<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>潮尚玩管理后台</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">

    <link rel="stylesheet" href="assets/vendor/bootstrap/css/bootstrap.min.css">
    <script src="/assets/scripts/jquery-1.9.1.min.js"></script>
    <script src="/assets/vendor/bootstrap/js/bootstrap.js"></script>

    <!-- load css -->
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/layui/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="/assets/layer/common/global.css" media="all">
    <link rel="stylesheet" type="text/css" href="/assets/layer/css/adminstyle.css" media="all">
</head>
<body>
<div class="layui-layout layui-layout-admin" id="layui_layout">
    <!-- 顶部区域 -->
    <div class="layui-header header header-demo">
        <div class="layui-main">
            <!-- logo区域 -->
            <div class="admin-logo-box">
                <a class="logo" href="/" title="logo">潮尚玩管理后台</a>
                <div class="larry-side-menu">
                    <i class="fa fa-bars" aria-hidden="true"></i>
                </div>
            </div>
            <!-- 顶级菜单区域 -->
            <div class="layui-larry-menu">
                <ul class="layui-nav clearfix">
                    <li class="layui-nav-item layui-this">
                        <a href="javascirpt:;"><i class="iconfont icon-wangzhanguanli"></i>内容管理</a>
                    </li>
                    <%--<li class="layui-nav-item">
                        <a href="javascript:;" layadmin-event="refresh" title="刷新">
                            <i class="layui-icon layui-icon-refresh-3"></i>
                        </a>
                    </li>--%>
                </ul>
            </div>
            <!-- 右侧导航 -->
            <ul class="layui-nav larry-header-item">
                <li class="layui-nav-item">
                    <a href="javascript:;" id="info">
                        <i class="iconfont" style="margin-top: 5px;font-size: 13px;">&#xe63c;</i>
                        个人中心</a>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;" id="changePass">
                        <i class="iconfont icon-password"></i>
                        修改密码</a>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;" id="exit">
                    <i class="iconfont icon-exit"></i>
                    退出</a>
                </li>
            </ul>
        </div>
    </div>
    <script>
        $(function () {
            $("#exit").click(function () {
                $.ajax({
                    type:'post',
                    url:'/loginout',
                    success:function (msg) {
                        window.location.href="/login";
                    }
                });
            });

            $("#info").click(function () {
                layer.open({
                    type: 2,
                    title: '个人中心',
//                    closeBtn: 0, //不显示关闭按钮
                    shade: [0],
                    area: ['580px', '380px'],
                    offset: 'auto', //垂直居中
//                    time: 2000, //2秒后自动关闭
                    anim: 2,
                    content: ['/information_all', 'no'] //iframe的url，no代表不显示滚动条
                    /*end: function(){ //此处用于演示
                        layer.open({
                            type: 2,
                            title: '很多时候，我们想最大化看，比如像这个页面。',
                            shadeClose: true,
                            shade: false,
                            maxmin: true, //开启最大化最小化按钮
                            area: ['893px', '600px'],
                            content: '/information_all'
                        });
                    }*/
                });
            });
            $("#changePass").click(function () {
                layer.open({
                    type: 2,
                    title: '修改密码',
                    shade: [0],
                    area: ['380px', '500px'],
                    offset: 'auto', //垂直居中
                    anim: 2,
                    content: ['/change_password', 'no'] //iframe的url，no代表不显示滚动条
                });
            });
        });
    </script>
    <!-- 左侧侧边导航开始 -->
    <div class="layui-side layui-side-bg layui-larry-side" id="larry-side">
        <div class="layui-side-scroll" id="larry-nav-side" lay-filter="side">

            <!-- 左侧菜单 -->
            <ul class="layui-nav layui-nav-tree">
                <li class="layui-nav-item layui-this">
                    <a href="javascript:;" data-url="/main">
                        <i class="iconfont icon-home1" data-icon='icon-home1'></i>
                        <span>后台首页</span>
                    </a>
                </li>

                <c:if test="${role == 1}">
                    <!-- 管理后台 -->
                    <li class="layui-nav-item">
                        <a href="javascript:;">
                            <i class="iconfont icon-jiaoseguanli" ></i>
                            <span>管理后台</span>
                            <em class="layui-nav-more"></em>
                        </a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="javascript:;" data-url="total_preview?page=1&limit=20">
                                    <i class="iconfont icon-iconfuzhi01" data-icon='icon-iconfuzhi01'></i>
                                    <span>后台总览</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="games">
                                    <i class="iconfont icon-iconfontmokuai" data-icon='icon-piliangicon'></i>
                                    <span>后台权限管理</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="proxy_diamond_send">
                                    <i class="iconfont icon-pinglun1" data-icon='icon-piliangicon'></i>
                                    <span>钻石发送</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="integral_withdrawals_admin?page=1&limit=20">
                                    <i class="iconfont icon-pingmusuoding" data-icon='icon-piliangicon'></i>
                                    <span>积分提现审核</span>
                                </a>
                            </dd>
                            <%--<dd>
                                <a href="javascript:;" data-url="integral_withdrawals_supervisor?page=1">
                                    <i class="iconfont icon-pinglun01" data-icon='icon-piliangicon'></i>
                                    <span>待我审核</span>
                                </a>
                            </dd>--%>
                            <dd>
                                <a href="javascript:;" data-url="wechat_approval_records?page=1&limit=20">
                                    <i class="iconfont icon-piliangicon" data-icon='icon-piliangicon'></i>
                                    <span>代理审批记录</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="partner_details?date=">
                                    <i class="iconfont icon-piliangicon" data-icon='icon-piliangicon'></i>
                                    <span>合伙数据详情</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="partnership?page=1&limit=20&userId=&chooseDate=">
                                    <i class="iconfont icon-piliangicon" data-icon='icon-piliangicon'></i>
                                    <span>合伙人下级详情</span>
                                </a>
                            </dd>
                        </dl>
                    </li>
                </c:if>


                <c:if test="${role == 3}">
                    <!-- 管理后台 -->
                    <li class="layui-nav-item">
                        <a href="javascript:;">
                            <i class="iconfont icon-jiaoseguanli" ></i>
                            <span>管理后台</span>
                            <em class="layui-nav-more"></em>
                        </a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="javascript:;" data-url="proxy_diamond_send">
                                    <i class="iconfont icon-piliangicon" data-icon='icon-piliangicon'></i>
                                    <span>钻石发送</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="wechat_approval_records?page=1&limit=20">
                                    <i class="iconfont icon-piliangicon" data-icon='icon-piliangicon'></i>
                                    <span>代理审批记录</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="partner_details?date=">
                                    <i class="iconfont icon-piliangicon" data-icon='icon-piliangicon'></i>
                                    <span>合伙数据详情</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="partnership?page=1&limit=20&userId=&chooseDate=">
                                    <i class="iconfont icon-piliangicon" data-icon='icon-piliangicon'></i>
                                    <span>合伙人下级详情</span>
                                </a>
                            </dd>
                        </dl>
                    </li>
                </c:if>
                <c:if test="${role == 1 || role == 3}">
                    <!-- 运营后台 -->
                    <li class="layui-nav-item">
                        <a href="javascript:;">
                            <i class="iconfont icon-jiaoseguanli2" ></i>
                            <span>运营后台</span>
                            <em class="layui-nav-more"></em>
                        </a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="javascript:;" data-url="operation_daily?page=1&limit=20">
                                    <i class="iconfont icon-yonghu1" data-icon='icon-yonghu1'></i>
                                    <span>运营数据</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="paid_records">
                                    <i class="iconfont icon-yonghu1" data-icon='icon-yonghu1'></i>
                                    <span>运营管理</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;"  data-url="proxy_overview">
                                    <i class="iconfont icon-jiaoseguanli4" data-icon='icon-jiaoseguanli4'></i>
                                    <span>代理管理</span>
                                </a>
                            </dd>
                            <%--<dd>
                                <a href="javascript:;"  data-url="horse_config">
                                    <i class="iconfont icon-jiaoseguanli4" data-icon='icon-jiaoseguanli4'></i>
                                    <span>马王活动配置</span>
                                </a>
                            </dd>--%>
                             <dd>
                                <a href="javascript:;"  data-url="privileged_statistics?page=1&limit=20">
                                    <i class="iconfont icon-jiaoseguanli4" data-icon='icon-jiaoseguanli4'></i>
                                    <span>代理特权活动统计</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" class="aA" data-url="apply_agent?page=1&limit=20">
                                    <i class="iconfont icon-jiaoseguanli4" data-icon='icon-jiaoseguanli4'></i>
                                    <span>
                                        意向代理
                                        <c:if test="${applyAgent>0}">
                                            <span class="glyphicon glyphicon-info-sign applyAgent" style="position:absolute;top:6px;font-size: 12px;color: red;"></span>
                                        </c:if>
                                    </span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" class="pf" data-url="problem_feedback?page=1&limit=20">
                                    <i class="iconfont icon-jiaoseguanli4" data-icon='icon-jiaoseguanli4'></i>
                                    <span>
                                        问题反馈
                                        <c:if test="${problemFeedback>0}">
                                            <span class="glyphicon glyphicon-info-sign problemFeedback" style="position:absolute;top:6px;font-size: 12px;color: red;"></span>
                                        </c:if>
                                    </span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;"  data-url="authentication_record?page=1&limit=20">
                                    <i class="iconfont icon-jiaoseguanli4" data-icon='icon-jiaoseguanli4'></i>
                                    <span>实名认证</span>
                                </a>
                            </dd>
                            <%--<dd>
                                <a href="javascript:;"  data-url="clubs_action?page=1&limit=20">
                                    <i class="iconfont icon-jiaoseguanli4" data-icon='icon-jiaoseguanli4'></i>
                                    <span>二次充值半价</span>
                                </a>
                            </dd>--%>
                        </dl>
                    </li>
                </c:if>

                <c:if test="${role == 1 || role == 8}">
                    <!-- 客服可看 -->
                    <li class="layui-nav-item">
                        <a href="javascript:;">
                            <i class="iconfont icon-jiaoseguanli2" ></i>
                            <span>客服工具</span>
                            <em class="layui-nav-more"></em>
                        </a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="javascript:;" class="aA"  data-url="apply_agent?page=1&limit=20">
                                    <i class="iconfont icon-jiaoseguanli4" data-icon='icon-jiaoseguanli4'></i>
                                    <span>
                                        意向代理
                                        <c:if test="${applyAgent>0}">
                                            <span class="glyphicon glyphicon-info-sign applyAgent" style="position:absolute;top:6px;font-size: 12px;color: red;"></span>
                                        </c:if>
                                    </span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" class="pf" data-url="problem_feedback?page=1&limit=20">
                                    <i class="iconfont icon-jiaoseguanli4" data-icon='icon-jiaoseguanli4'></i>
                                    <span>
                                        问题反馈
                                        <c:if test="${problemFeedback>0}">
                                          <span class="glyphicon glyphicon-info-sign problemFeedback" style="position:absolute;top:6px;font-size: 12px;color: red;"></span>
                                        </c:if>
                                    </span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;"  data-url="log_output?page=1&limit=20&user_id=&status=1">
                                    <i class="iconfont icon-jiaoseguanli4" data-icon='icon-jiaoseguanli4'></i>
                                    <span>玩家日志查询</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;"  data-url="proxy_log?page=1&limit=20&user_id=&status=1">
                                    <i class="iconfont icon-jiaoseguanli4" data-icon='icon-jiaoseguanli4'></i>
                                    <span>代理日志查询</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;"  data-url="club_management?page=1&limit=20&queryId=&queryStatus=1&status=1">
                                    <i class="iconfont icon-jiaoseguanli4" data-icon='icon-jiaoseguanli4'></i>
                                    <span>俱乐部管理</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;"  data-url="integral_manage?status=1">
                                    <i class="iconfont icon-jiaoseguanli4" data-icon='icon-jiaoseguanli4'></i>
                                    <span>积分记录查询</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;"  data-url="status_editor?page=1&limit=20&status=1">
                                    <i class="iconfont icon-jiaoseguanli4" data-icon='icon-jiaoseguanli4'></i>
                                    <span>消息管理</span>
                                </a>
                            </dd>
                        </dl>
                    </li>
                </c:if>

                <script>
                    $(function () {
                        $(".problemFeedback").show();
                        $(".applyAgent").show();
                        $(".pf").click(function () {
                            $(".problemFeedback").hide();
                        });
                        $(".aA").click(function () {
                            $(".applyAgent").hide();
                        });
                    })
                </script>

                <c:if test="${role == 1 || role == 5}">
                    <!-- 代理后台 -->
                    <li class="layui-nav-item">
                        <a href="javascript:;">
                            <i class="iconfont icon-wenzhang1" ></i>
                            <span>代理后台</span>
                            <em class="layui-nav-more"></em>
                        </a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="javascript:;" data-url="information">
                                    <i class="iconfont icon-lanmuguanli" data-icon='icon-lanmuguanli'></i>
                                    <span>我的资料</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="child_proxy">
                                    <i class="iconfont icon-wenzhang2" data-icon='icon-wenzhang2'></i>
                                    <span>我的子代理</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="proxy">
                                    <i class="iconfont icon-icon1" data-icon='icon-icon1'></i>
                                    <span>添加子代理</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="sends">
                                    <i class="iconfont icon-word" data-icon='icon-word'></i>
                                    <span>发送钻石</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="search_record?page=1&user_id=&compositor=2&proxy_date=&proxy_date_end=">
                                    <i class="iconfont icon-word" data-icon='icon-word'></i>
                                    <span>记录查询</span>
                                </a>
                            </dd>
                        </dl>
                    </li>
                </c:if>
                <c:if test="${role == 3}">
                    <!-- 管理后台 -->
                    <li class="layui-nav-item">
                        <a href="javascript:;">
                            <i class="iconfont icon-jiaoseguanli" ></i>
                            <span>登录管理</span>
                            <em class="layui-nav-more"></em>
                        </a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="javascript:;" data-url="log_check?page=1">
                                    <i class="iconfont icon-piliangicon" data-icon='icon-piliangicon'></i>
                                    <span>日志查询</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="filtration">
                                    <i class="iconfont icon-piliangicon" data-icon='icon-piliangicon'></i>
                                    <span>关键词过滤</span>
                                </a>
                            </dd>
                        </dl>
                    </li>
                </c:if>

                <c:if test="${role == 1 || role == 4}">
                    <!-- 财务管理 -->
                    <li class="layui-nav-item">
                        <a href="javascript:;">
                            <i class="iconfont icon-m-members" ></i>
                            <span>财务管理</span>
                            <em class="layui-nav-more"></em>
                        </a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="javascript:;" data-url="integral_withdrawals?page=1&limit=20">
                                    <i class="iconfont icon-zhuti"  data-icon='icon-zhuti'></i>
                                    <span>积分提现财务审核</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="auditing?page=1">
                                    <i class="iconfont icon-zhandianguanli"  data-icon='icon-zhuti'></i>
                                    <span>财务审核</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="approval_record?page=1&proxy_date=&proxy_date_end=&send_id=">
                                    <i class="iconfont icon-zhandianpeizhi"  data-icon='icon-zhuti'></i>
                                    <span>审批记录</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="financial_daily?page=1">
                                    <i class="iconfont icon-zengjia"  data-icon='icon-zhuti'></i>
                                    <span>财务日报</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="consumption_details?page=1&start_date=&end_date=">
                                    <i class="iconfont icon-zhandian"  data-icon='icon-zhuti'></i>
                                    <span>钻石消耗详情</span>
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:;" data-url="diamond_production?page=1&start_date=&end_date=">
                                    <i class="iconfont icon-zhucechenggong"  data-icon='icon-zhuti'></i>
                                    <span>钻石产生详情</span>
                                </a>
                            </dd>
                        </dl>
                    </li>
                </c:if>

                <c:if test="${role == 999}">
                    <!-- 需求上去除的页面 -->
                    <li class="layui-nav-item">
                        <a href="javascript:;">
                            <i class="iconfont icon-m-members" ></i>
                            <span>去除的页面</span>
                            <em class="layui-nav-more"></em>
                        </a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="javascript:;" data-url="/integral_withdrawals_operation?page=1&limit=20">
                                    <i class="iconfont icon-zhuti"  data-icon='icon-zhuti'></i>
                                    <span>积分提现审核（去除）</span>
                                </a>
                            </dd>
                        </dl>
                        <dd>
                            <a href="javascript:;"  data-url="proxy_preview?page=1&limit=20&userId=">
                                <i class="iconfont icon-jiaoseguanli4" data-icon='icon-jiaoseguanli4'></i>
                                <span>代理管理（去除）</span>
                            </a>
                        </dd>
                        <dd>
                            <a href="javascript:;"  data-url="clubs_action?page=1&limit=20">
                                <i class="iconfont icon-jiaoseguanli4" data-icon='icon-jiaoseguanli4'></i>
                                <span>俱乐部活动（去除）</span>
                            </a>
                        </dd>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>

    <!-- 左侧侧边导航结束 -->
    <!-- 右侧主体内容 -->
    <div class="layui-body" id="larry-body" style="bottom: 0;border-left: solid 2px #2299ee;">
        <div class="layui-tab layui-tab-card larry-tab-box" id="larry-tab" lay-filter="demo" lay-allowclose="true">
            <div class="go-left key-press pressKey" id="titleLeft" title="滚动至最右侧"><i class="larry-icon larry-weibiaoti6-copy"></i> </div>
            <ul class="layui-tab-title">
                <li class="layui-this" id="admin-home"><i class="iconfont icon-diannao1"></i><em>后台首页</em></li>
            </ul>
            <div class="go-right key-press pressKey" id="titleRight" title="滚动至最左侧"><i class="larry-icon larry-right"></i></div>
            <ul class="layui-nav closeBox">
                <li class="layui-nav-item">
                    <a href="javascript:;"><i class="iconfont icon-caozuo"></i> 页面操作</a>
                    <dl class="layui-nav-child">
                        <%--<dd><a href="javascript:;" class="refresh refreshThis"><i class="layui-icon">&#x1002;</i> 刷新当前</a></dd>
                        <dd><a href="javascript:;" class="closePageOther"><i class="iconfont icon-prohibit"></i> 关闭其他</a></dd>
                        --%><dd><a href="javascript:;" id="sdf" class="closePageAll"><i class="iconfont icon-guanbi"></i> 关闭全部</a></dd>
                    </dl>
                </li>
            </ul>
            <div class="layui-tab-content" style="min-height: 150px; height: 100%;">
                <div class="layui-tab-item layui-show">
                    <iframe class="larry-iframe" data-id='0' src="main.html"></iframe>
                </div>
            </div>
        </div>
    </div>
    <script>
        $(function () {
            $(".closePageAll").click(function () {
                location.replace("/");
            });
        });
    </script>
    <!-- 底部区域 -->
    <div class="layui-footer layui-larry-foot" id="larry-footer">
        <div class="layui-mian">
            <p class="p-admin">
                欢迎进入潮尚玩运营管理后台
            </p>
        </div>
    </div>
</div>
<!-- 加载js文件-->
<script type="text/javascript" src="/assets/layer/common/layui/layui.js"></script>
<script type="text/javascript" src="/assets/layer/js/larry.js"></script>
<script type="text/javascript" src="/assets/layer/js/index.js"></script>
<!-- 锁屏 -->
<div class="lock-screen" style="display: none;">
    <div id="locker" class="lock-wrapper">
        <div id="time"></div>
        <div class="lock-box center">
            <%--<img src="images/userimg.jpg" alt="">--%>
            <h1>admin</h1>
            <duv class="form-group col-lg-12">
                <input type="password" placeholder='锁屏状态，请输入密码解锁' id="lock_password" class="form-control lock-input" autofocus name="lock_password">
                <button id="unlock" class="btn btn-lock">解锁</button>
            </duv>
        </div>
    </div>
</div>
</body>
</html>
</html>
