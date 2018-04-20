<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %><html lang="en">
<meta name="format-detection" content="telephone=no">
<head>
    <title>麻将规则</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <!-- VENDOR CSS -->
    <link rel="stylesheet" href="/assets/vendor/bootstrap/css/bootstrap.min.css">

</head>
<style>
    body{ background-color: #F5E4D4; }
    .c{
        background-color: #D4B7A5;
        border-radius: 10px;
        padding: 18px;
        margin-bottom: 20px;
    }
    .c1{
        background-color: #D4B7A5;
        border-radius: 10px;
        padding: 18px;
    }
    .fonts{
        color: #795940;
        letter-spacing: 1px;
        font-size: 14px;
    }
    table tr td {
        border: 1px solid;
    }
</style>
<body>
    <div style="width:100%;position: relative;">
        <div style="width: 1%;height: 100px;position: absolute;left: 0px;"></div>
        <div style="width: 98%;position: absolute;left: 1%;">

            <div style="text-align: center;color: #795940;margin-top: 12px;margin-bottom: 12px;"><b>136张基础牌</b></div>
            <div class="c1">
                <span class="fonts" style="line-height: 20px;">
                    36张筒，36张索，36张万子，28张字牌（东、南、西、北、中、发、白），共136张基础牌。<br>
                    不带字模式下：去掉28张字牌，共108张。<br>
                    不带万字模式：去掉36张万字，共100张。<br>
                </span>
            </div>
            <br>


            <div style="margin-top: -15px;">
                <div style="text-align: center;color: #795940;margin-top: 12px;margin-bottom: 12px;"><b>能碰不能吃</b></div>
                <div class="c1">
                    <span class="fonts" style="line-height: 20px;">
                        只能碰牌，不能吃上家弃出的子。<br>
                    </span>
                </div>
            </div>

            <div style="text-align: center;color: #795940;margin-top: 22px;margin-bottom: 12px;"><b>过水不能胡</b></div>
            <div class="c1">
                <span class="fonts" style="line-height: 20px;">
                   “必胡”，为勾选项，即不管那家出牌，听牌者必须胡牌。<br>
                    过水不能胡，指必胡未勾选时，若有A玩家听牌，不管哪家出牌，
                    A玩家能胡选择不胡，则本轮其他玩家出牌时，A玩家本轮都无法再胡。<br>
                </span>
            </div>
<%--
            <div style="text-align: center;color: #795940;margin-top: 12px;margin-bottom: 12px;"><b>过水不能杠</b></div>
            <div class="c1">
                <span class="fonts" style="line-height: 20px;">
                    已经碰牌后，摸到第四张，当时没有明杠，则以后这一张就不能杠了。<br>
                </span>
            </div>--%>

            <div style="text-align: center;color: #795940;margin-top: 12px;margin-bottom: 12px;"><b>过水不能碰</b></div>
            <div class="c1">
                <span class="fonts" style="line-height: 20px;">
                    一圈之内，一方打出可碰牌未碰，则下圈才能再碰。
                    自己一对九条，下家打了九条没碰，则这一圈内，对家和上家再打出的九条就不能碰了。<br>
                </span>
            </div>

            <div style="text-align: center;color: #795940;margin-top: 12px;margin-bottom: 12px;"><b>胡牌牌型</b></div>
            <div class="c1">
                <span class="fonts" style="line-height: 20px;">
                    玩家起手共13张牌，庄家多一张，视为已经摸了一张，共14张。<br>
                    所有玩家胡牌按14张计算，包括碰杠吃的刻子和顺子，都算作自己的牌型。<br>


                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180118144657" style="width: 90%;margin-top: 10px;"><br><br>

                    正常胡牌模式为：3+3+3+3+2，3为刻子或顺子，2为对子。<br>
                    （特殊牌型七对：2+2+2+2+2+2+2）<br>
                    （特殊牌型十三幺：19万19条19筒东南西北中发白 ）<br>
                            刻子：指三张一样的牌型，如三个九万<br>
                    顺子：指筒条万同花色相连的三个数字，如三四五筒，六七八万；<br><br>

                    鸡胡1分（自摸2分）<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180118144914" style="width: 100%;"><br><br>
                    对对胡4分<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180118144939" style="width: 100%;"><br><br>
                     混一色4分<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180118145407" style="width: 100%;"><br><br>
                     清一色6分<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180118145500" style="width: 100%;"><br><br>
                     混对胡8分<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180118145014" style="width: 100%;"><br><br>
                    七小对6分<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180118145720" style="width: 100%;"><br><br>
                     清对胡10分<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180118150131" style="width: 100%;"><br><br>
                    字一色20分<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180118150228" style="width: 100%;"><br><br>
                    豪华七小对10分<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180118150245" style="width: 100%;"><br><br>
                    双豪华七小对20分<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180118150334" style="width: 100%;"><br><br>
                    三豪华七小对30分<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180118150353" style="width: 100%;"><br><br>
                    十八罗汉36分<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180118150406" style="width: 100%;"><br><br>
                    花幺九10分<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180118150535" style="width: 100%;"><br><br>
                    混幺九14分<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180122144554" style="width: 100%;"><br><br>
                    纯幺九20分<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180118150623" style="width: 100%;"><br><br>
                    十三幺26分<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180118150640" style="width: 100%;"><br><br>

                    混一色一条龙10分<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180122144821" style="width: 100%;"><br><br>
                    清一色一条龙12分<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180122145054" style="width: 100%;"><br><br>

                    天胡、地胡、人胡20分<br>
                    天胡: 只有庄家独有的权利，庄家起牌后即成胡牌牌型为天胡;<br>
                    地胡：起牌后，第1圈里闲家自摸的第1张牌胡牌为地胡;<br>
                    人胡：起牌后，庄家打出的第1张牌，闲家吃胡为人胡<br>
                    杠上开花X2<br>
                    海底捞月X2 <br>
                    杠上花和海底捞月不叠加，最多2倍  <br>
                </span>
            </div>



            <div style="text-align: center;color: #795940;margin-top: 12px;margin-bottom: 12px;"><b>马牌</b></div>
            <div class="c1">
                <span class="fonts" style="line-height: 20px;">
                    奖马 <br>
                    可选0~10张（0/2/5/8马）<br>
                    胡牌时预留的奖马数量的底牌，在胡牌后开出算马。底牌摸到预留的奖马前的最后一张，是海底牌。<br><br>

                    买马<br>
                    买0/1/2马<br>
                    所有庄家有买马，在开局前从牌堆中给予所有玩家各取0~2张盖牌当所有玩家买马。<br><br>

                    罚马<br>
                    罚0/1/2马<br>
                    只有庄家有罚马，在开局前从牌堆中取2张盖牌当庄家罚马。<br><br>


                    <table>
                        <tr>
                            <td>类型</td>
                            <td>可选数量</td>
                            <td>产生方法</td>
                            <td>计算方法</td>
                        </tr>
                        <tr>
                            <td>奖马	</td>
                            <td>0~10</td>
                            <td>胡牌后，从牌尾翻对应数量的牌（开局预留）</td>
                            <td>牌型分×中马数</td>
                        </tr>
                        <tr>
                            <td>买马</td>
                            <td>0~2</td>
                            <td>开局前所有人盖对应数量的牌当买马</td>
                            <td>牌型分×中马数</td>
                        </tr>
                        <tr>
                            <td>罚马</td>
                            <td>0~2</td>
                            <td>开局前庄家盖对应数量的牌当罚马</td>
                            <td>牌型分×中马数</td>
                        </tr>
                    </table>

                    <br>
                    中马规则<br>
                    庄家的马牌对应为: 1、5、9、东风，红中；<br>
                    庄家下家的马牌对应为: 2、6.南风、发财；<br>
                    庄家对家的马牌对应为: 3、7、西风、白板；<br>
                    庄家上家的马牌对应为:4、8、北风；<br>
                    一炮多响时从点炮玩家计算。<br>
                    A.当点炮胡时：<br>
                    a.非点炮玩家买中胡牌玩家的马牌,点炮玩家需支付其每张1倍胡牌分数。<br>
                    b.未胡牌玩家买中点炮玩家的马牌，需支付胡牌玩家每张1倍胡牌分数<br>
                    B当自摸时：<br>
                    a.自摸玩家买中自己的马，其他玩家需支付其每张1倍胡牌分数。<br>
                    b.未胡牌玩家买中胡牌玩家的马牌，其他未胡牌玩家需支付其每张1倍胡牌分数。<br>
                    c.未胡牌玩家买中非胡牌玩家的马牌，需支付自摸玩家每张1倍胡牌分数<br>
                    2.罚马:<br>
                    开局仅庄家从牌库拿取1或2张马牌，其他规则同买马。<br><br>
                    三人算马：<br>
                    庄家的马牌对应为: 1、4、7、东风、北风、白板；<br>
                    庄家下家的马牌对应为: 2、5、8、南风、红中；<br>
                    庄家上家的马牌对应为: 3、6、9、、西风、发财；<br>
                    二人算马：<br>
                    庄家的马牌对应为: 1、3、5、7、9、东风、西、红中、白板；<br>
                    庄家对家的马牌对应为: 2、4、6、8、南风、北风、发财；<br>
                </span>
            </div>


            <div style="text-align: center;color: #795940;margin-top: 12px;margin-bottom: 12px;"><b>杠</b></div>
            <div class="c1">
                <span class="fonts" style="line-height: 20px;">
                    明杠基础1分，暗杠基础2分<br>

                    <table>
                        <tr style="width: 260px;">
                            <td style="width: 260px;"></td>
                            <td style="width: 260px;">放杠人</td>
                            <td style="width: 260px;">其他玩家</td>
                        </tr>
                        <tr>
                            <td>明杠	</td>
                            <td>-3×杠数</td>
                            <td>无关系</td>
                        </tr>
                        <tr>
                            <td>补明杠</td>
                            <td>无放杠人</td>
                            <td>-1×杠数</td>
                        </tr>
                        <tr>
                             <td>暗杠</td>
                             <td>无放杠人</td>
                             <td>-2×杠数</td>
                        </tr>
                    </table>

                    <br>

                    玩家杠牌时，计算杠分；<br>
                    闲家中马时：<br>
                    买中赢家，一倍分数赢钱；<br>
                    买中输家，一倍分数输钱；<br><br>

                    马跟杠<br>
                    中马数量×杠×杠分<br>
                    A.当点杠时：<br>
                        &nbsp;&nbsp;&nbsp;&nbsp;1.非放杠玩家买中杠牌玩家的马牌，点杠玩家需支付其每张1倍杠分给该玩家。<br>
                        &nbsp;&nbsp;&nbsp;&nbsp;2.非杠牌玩家买中点杠玩家的马牌，需支付杠牌玩家每张1倍杠分给该玩家。<br>

                    B当明暗杠时：<br>
                        &nbsp;&nbsp;&nbsp;&nbsp;1.明暗杠玩家买中自己的马，其他玩家需支付其每张1倍杠分给该玩家。<br>
                        &nbsp;&nbsp;&nbsp;&nbsp;2.其他玩家买中明暗杠玩家的马牌，其他非该杠杠牌玩家需支付其每张1倍杠分给该玩家。<br>
                        &nbsp;&nbsp;&nbsp;&nbsp;3.其他玩家买中其他非该杠杠牌玩家的马牌，需支付明暗杠玩家每张1倍杠分给该玩家。<br><br>

                    流局杠算分<br>
                    当一局有杠且无人胡牌时，则流局时，本局内的杠牌，都需要算分。<br><br>
                    杠上花&吃杠杠爆全包<br>
                    玩家通过杠牌会后，自摸胡牌，视为杠上花，基础分翻倍；<br>
                    若该杠为吃杠，并杠上花杠爆，则放该杠的玩家，全包本桌其他玩家应该支付的基础分；<br>
                </span>
            </div>


            <div style="text-align: center;color: #795940;margin-top: 12px;margin-bottom: 12px;"><b>跟庄</b></div>
            <div class="c1">
                <span class="fonts" style="line-height: 20px;">
                    庄家打出的首张牌，其他闲家都跟他打出一张一样的牌，则算跟庄（庄家-1分给所有跟庄玩家）。<br>
                    如：<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;庄家首张打出东风，之后下家打东风，对家打东风，上家也打东风，则算跟庄成功。<br>
                    注：<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;跟庄只计算首轮第一张；<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;2人时不计算跟庄；<br>
                </span>
            </div>
            <div style="text-align: center;color: #795940;margin-top: 12px;margin-bottom: 12px;"><b>连庄</b></div>
            <div class="c1">
                <span class="fonts" style="line-height: 20px;">
                    指庄家连续当庄时，获得额外连庄分数。<br>
                        计算方法：<br>
                    在首轮胡牌为庄之后，庄家继续胡牌，则连庄成功，分数+1；<br>
                    第二次连庄，则分数+2，第三次连庄，分数+4，每次翻倍；<br>
                    连庄被打断后，重新计算。<br>
                </span>
            </div>
            <div style="text-align: center;color: #795940;margin-top: 12px;margin-bottom: 12px;"><b>一条龙</b></div>
            <div class="c1">
                <span class="fonts" style="line-height: 20px;">
                    混一色一条龙10分<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180122144821" style="width: 100%;"><br><br>
                    清一色一条龙12分<br>
                    <img src="http://yxx.ufile.ucloud.com.cn/dev/reward/20180122145054" style="width: 100%;"><br><br>
                </span>
            </div>
            <div style="text-align: center;color: #795940;margin-top: 12px;margin-bottom: 12px;"><b>10倍听牌免分</b></div>
            <div class="c1">
                <span class="fonts" style="line-height: 20px;">
                   当玩家听牌的牌型为：<br>
                    十三幺，三豪华七小对，十八罗汉，纯幺九，双豪华七小对等十倍及十倍以上分数时，
                    则在结算时，免扣和自己相关的基础分，马分，杠分。
                </span>
            </div>


            <div style="text-align: center;color: #795940;margin-top: 12px;margin-bottom: 12px;"><b>鬼牌</b></div>
            <div class="c1" style="margin-bottom: 16px;">
                <span class="fonts" style="line-height: 20px;">
                    鬼牌指将游戏中的一位麻将，当做可以替代游戏中的任何牌的存在。<br>
                    如：<br>
                    红中当鬼——指所有的红中，都可以当游戏中的任意牌来组合；<br>
                    白板当鬼——指所有的白板，都可以当游戏中的任意牌来组合；<br>
                    翻鬼和双鬼的玩法：<br>
                    翻鬼——指翻出指定的一张牌，来确定作为本局的鬼牌（每一小局翻一次）<br>
                    双鬼——指有两张鬼牌（供8张）；<br>
                    特殊玩法：<br>
                    四鬼胡牌——指手中同时有四张鬼，则可以算作胡牌（双鬼时，不可选四鬼胡牌）；<br>
                    无鬼翻倍——指手中胡牌时没有一张鬼牌，则得分翻倍；<br>
                </span>
            </div>

           <%-- <div style="text-align: center;color: #edf4c5;margin-top: 12px;margin-bottom: 12px;"><b>游戏问题</b></div>
            <div class="c">
                <span class="fonts">
                    您可以直接在有问题的界面中摇晃手机，在对应界面直接与我们反馈问题。
                </span>
            </div>--%>
        </div>
        <div style="width: 1%;height: 100px;position: absolute;right: 0px;"></div>
    </div>
</body>
</html>
