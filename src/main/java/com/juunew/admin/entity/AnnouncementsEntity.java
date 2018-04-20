package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class AnnouncementsEntity {

    private int id;
    private int type;             //公告类型，1. 针对所有人
    private int status;             //1. 有效  2. 过期  0. 暂停
    private String content;
    private long start_time;
    private long end_time;
    private String updated_at;
    private String created_at;
    private int exit;               //弹框后是否要退出
    private String title;
    private int times;             //需要显示的次数
    private String version;
    private int platform;             //平台
    private int display_position;             //显示位置：（1：弹窗+跑马灯，2：仅跑马灯）
    private String jump_url;                       //跳转位置
    private String imgurl;                       //跳转位置
    private int msg_type;                       //公告类型
    private int page_type;                       //公告显示在哪个页面


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getExit() {
        return exit;
    }

    public void setExit(int exit) {
        this.exit = exit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public int getDisplay_position() {
        return display_position;
    }

    public void setDisplay_position(int display_position) {
        this.display_position = display_position;
    }

    public String getJump_url() {
        return jump_url;
    }

    public void setJump_url(String jump_url) {
        this.jump_url = jump_url;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(int msg_type) {
        this.msg_type = msg_type;
    }

    public int getPage_type() {
        return page_type;
    }

    public void setPage_type(int page_type) {
        this.page_type = page_type;
    }
}
