package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class ContentEntity {

    private int msgId;
    private String title;
    private String content;
    private int liveCode;   //房间号
    private int gameKind;   // 游戏类别
    private String name;   //邀请人的昵称
    private int msgType;    //消息类型  1：文本消息；2：纯图消息；3：图文消息
    private int showPlace;  //0 任何地方，其他定义与page相同        page定义: // 1：消息；2：任务；3：比赛列表 4：宝箱；5：充值界面；6：好友界面
    private int btnShow;    //是否展示按钮
    private String btnTitle;    //按钮标题
    private ActionEntity btnAction;  //按钮点击事件
    private int reward;        //奖励；0：无奖励, 其他奖励数量
    private int needScroller;   //是否需要跑马灯滚动， 跑动内容为content    true
    private int scrollerTime;   //跑马灯滚动次数
    private int competitionId;
    private MsgListInfoEntity msgListInfoEntity;



    public MsgListInfoEntity getMsgListInfoEntity() {
        return msgListInfoEntity;
    }

    public void setMsgListInfoEntity(MsgListInfoEntity msgListInfoEntity) {
        this.msgListInfoEntity = msgListInfoEntity;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLiveCode() {
        return liveCode;
    }

    public void setLiveCode(int liveCode) {
        this.liveCode = liveCode;
    }

    public int getGameKind() {
        return gameKind;
    }

    public void setGameKind(int gameKind) {
        this.gameKind = gameKind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getShowPlace() {
        return showPlace;
    }

    public void setShowPlace(int showPlace) {
        this.showPlace = showPlace;
    }

    public int getBtnShow() {
        return btnShow;
    }

    public void setBtnShow(int btnShow) {
        this.btnShow = btnShow;
    }

    public String getBtnTitle() {
        return btnTitle;
    }

    public void setBtnTitle(String btnTitle) {
        this.btnTitle = btnTitle;
    }

    public ActionEntity getBtnAction() {
        return btnAction;
    }

    public void setBtnAction(ActionEntity btnAction) {
        this.btnAction = btnAction;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getNeedScroller() {
        return needScroller;
    }

    public void setNeedScroller(int needScroller) {
        this.needScroller = needScroller;
    }

    public int getScrollerTime() {
        return scrollerTime;
    }

    public void setScrollerTime(int scrollerTime) {
        this.scrollerTime = scrollerTime;
    }

    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }
}
