package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class NoticeDraftEntity {

    private int id;
    private String content;
    private String start_time;
    private String end_time;
    private String updated_at;
    private String created_at;
    private int exit;               //弹框后是否要退出
    private String title;
    private int times;             //需要显示的次数
    private String version;
    private int platform;             //平台
    private int display_position;             //显示位置：（1：弹窗+跑马灯，2：仅跑马灯）
    private String jump_url;                       //跳转位置


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
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
}
