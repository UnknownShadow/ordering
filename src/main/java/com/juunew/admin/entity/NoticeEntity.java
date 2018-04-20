package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class NoticeEntity {

    private int id;         //主键id
    private String title;       //标题
    private String content;         //公告内容
    private String start_date;      //开始时间
    private String end_date;        //结束时间
    private int platform;        //平台
    private String version;         //应用版本
    private String specific_user;       //特定用户
    private int reward_type;                //奖励类型
    private int reward_number;         //奖励数量
    private int display_position;         //奖励数量
    private int sign_out;         //是否强制退出游戏
    private int is_all;         //是否全推
    private String liveCode;
    private int cmd;
    private int notice_id;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSpecific_user() {
        return specific_user;
    }

    public void setSpecific_user(String specific_user) {
        this.specific_user = specific_user;
    }

    public int getReward_type() {
        return reward_type;
    }

    public void setReward_type(int reward_type) {
        this.reward_type = reward_type;
    }

    public int getReward_number() {
        return reward_number;
    }

    public void setReward_number(int reward_number) {
        this.reward_number = reward_number;
    }

    public int getDisplay_position() {
        return display_position;
    }

    public void setDisplay_position(int display_position) {
        this.display_position = display_position;
    }

    public int getSign_out() {
        return sign_out;
    }

    public void setSign_out(int sign_out) {
        this.sign_out = sign_out;
    }

    public int getIs_all() {
        return is_all;
    }

    public void setIs_all(int is_all) {
        this.is_all = is_all;
    }

    public String getLiveCode() {
        return liveCode;
    }

    public void setLiveCode(String liveCode) {
        this.liveCode = liveCode;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public int getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(int notice_id) {
        this.notice_id = notice_id;
    }
}
