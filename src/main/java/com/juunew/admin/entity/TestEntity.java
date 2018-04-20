package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class TestEntity {

    /**
     * json 格式：
     * 消息类型：
     *     1：文本消息（json：title、content）
     *     2：纯图消息（json：picUrl、picFunc、picToPage、picToUrl）
     *     3：图文消息（json:title、content、picUrl、picFunc、picToPage、picToUrl）
     *
     *     按钮需要展示时：  有按钮操作参数
     *     按钮不需要展示时：没有按钮操作参数
     */

    private int msgType;    //消息类型
    private String title;       //消息标题
    private String content;    //消息内容
    private String json;    //json字符串；
    private int btnShow;    //客户端是否展示按钮     1：展示；0：不展示

    private String btnJson;     //有按钮时
    private String picUrl;     //图片路径地址
    private int picFunc;    //图片功能     1：图片跳转；2：图片网页
    private int picToPage;    //图片跳转    1：打开消息；2：打开任务；3：打开比赛列表
			                            //4：打开宝箱；5：打开充值界面；6：打开好友界面
    private int picToUrl;       //图片打开的网址


    private int btnTitle;    //按钮文字
    private int btnFunc;    //按钮功能
    private int btnJump;    //按钮跳转
    private int btnToUrl;    //按钮打开网页

    private int reward;    //奖励；-1：无奖励；钻石数量：有奖励
    private int needExit;    //是否需要强退   1：强退；0：不强退
    private int light;      //跑马灯   1：展示；0：不展示
    private int startTime;      //开始时间





}
