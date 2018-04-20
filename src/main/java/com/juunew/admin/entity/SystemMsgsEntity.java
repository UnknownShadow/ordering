package com.juunew.admin.entity;

import java.util.Date;

/**
 * Created by juunew on 2017/6/20.
 */
public class SystemMsgsEntity {

    private int id;
    private String title;
    private String content;
    private String raw_content;     //
    private String pic_url;     //
    private int pic_to_page;     //
    private int pic_cmd;     //
    private String pic_to_url;     //
    private int btn_show;     //
    private String btn_title;     //
    private int btn_cmd;     //
    private int btn_to_page;     //
    private String btn_to_url;     //
    private int reward;
    private boolean need_exit;     //
    private boolean need_scroller;     //是否在跑马灯展示
    private int cmd;
    private boolean immediateness;     //及时性；0：不用及时发送（拉取）；1：需要及时发送（推送）
    private boolean need_save;     //是否需要保存在消息列表
    private int show_place;     //显示位置
    private int show_times;     //显示次数
    private int platfrom;
    private String version;
    private Date start_time;
    private Date end_time;
    private int show_again;     //是否再次弹出；0：不弹出；1：再次弹出
    private int msg_type;     //消息类型    1：文本消息；2：图片消息；3：图文消息
    private boolean send_all; //是否发送给全员
    private boolean created_at;
    private boolean updated_at;
    private String startTime;
    private String endTime;
    private String specific_user;
    private int hit_count;//消息点击按钮进入次数


    //--------------------------------------------------
    private int user_id;    //
    private int system_msgs_id;    //
    private int read_status;    //读取状态；0：未读；1：已读；2：已领取；



    @Override
    public String toString() {
        return "SystemMsgsEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", raw_content='" + raw_content + '\'' +
                ", pic_url='" + pic_url + '\'' +
                ", pic_to_page=" + pic_to_page +
                ", pic_cmd=" + pic_cmd +
                ", pic_to_url='" + pic_to_url + '\'' +
                ", btn_show=" + btn_show +
                ", btn_title='" + btn_title + '\'' +
                ", btn_cmd=" + btn_cmd +
                ", btn_to_page=" + btn_to_page +
                ", btn_to_url='" + btn_to_url + '\'' +
                ", reward=" + reward +
                ", need_exit=" + need_exit +
                ", need_scroller=" + need_scroller +
                ", cmd=" + cmd +
                ", immediateness=" + immediateness +
                ", need_save=" + need_save +
                ", show_place=" + show_place +
                ", show_times=" + show_times +
                ", platfrom=" + platfrom +
                ", version='" + version + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", show_again=" + show_again +
                ", msg_type=" + msg_type +
                ", send_all=" + send_all +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", specific_user='" + specific_user + '\'' +
                ", hit_count=" + hit_count +
                '}';
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSystem_msgs_id() {
        return system_msgs_id;
    }

    public void setSystem_msgs_id(int system_msgs_id) {
        this.system_msgs_id = system_msgs_id;
    }

    public int getRead_status() {
        return read_status;
    }

    public void setRead_status(int read_status) {
        this.read_status = read_status;
    }

    public int getHit_count() {
        return hit_count;
    }

    public void setHit_count(int hit_count) {
        this.hit_count = hit_count;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSpecific_user() {
        return specific_user;
    }

    public void setSpecific_user(String specific_user) {
        this.specific_user = specific_user;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public boolean isCreated_at() {
        return created_at;
    }

    public void setCreated_at(boolean created_at) {
        this.created_at = created_at;
    }

    public boolean isUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(boolean updated_at) {
        this.updated_at = updated_at;
    }

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

    public String getRaw_content() {
        return raw_content;
    }

    public void setRaw_content(String raw_content) {
        this.raw_content = raw_content;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public int getPic_to_page() {
        return pic_to_page;
    }

    public void setPic_to_page(int pic_to_page) {
        this.pic_to_page = pic_to_page;
    }

    public int getPic_cmd() {
        return pic_cmd;
    }

    public void setPic_cmd(int pic_cmd) {
        this.pic_cmd = pic_cmd;
    }

    public String getPic_to_url() {
        return pic_to_url;
    }

    public void setPic_to_url(String pic_to_url) {
        this.pic_to_url = pic_to_url;
    }

    public int getBtn_show() {
        return btn_show;
    }

    public void setBtn_show(int btn_show) {
        this.btn_show = btn_show;
    }

    public String getBtn_title() {
        return btn_title;
    }

    public void setBtn_title(String btn_title) {
        this.btn_title = btn_title;
    }

    public int getBtn_cmd() {
        return btn_cmd;
    }

    public void setBtn_cmd(int btn_cmd) {
        this.btn_cmd = btn_cmd;
    }

    public int getBtn_to_page() {
        return btn_to_page;
    }

    public void setBtn_to_page(int btn_to_page) {
        this.btn_to_page = btn_to_page;
    }

    public String getBtn_to_url() {
        return btn_to_url;
    }

    public void setBtn_to_url(String btn_to_url) {
        this.btn_to_url = btn_to_url;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public boolean isNeed_exit() {
        return need_exit;
    }

    public void setNeed_exit(boolean need_exit) {
        this.need_exit = need_exit;
    }

    public boolean isNeed_scroller() {
        return need_scroller;
    }

    public void setNeed_scroller(boolean need_scroller) {
        this.need_scroller = need_scroller;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public boolean isImmediateness() {
        return immediateness;
    }

    public void setImmediateness(boolean immediateness) {
        this.immediateness = immediateness;
    }

    public boolean isNeed_save() {
        return need_save;
    }

    public void setNeed_save(boolean need_save) {
        this.need_save = need_save;
    }

    public int getShow_place() {
        return show_place;
    }

    public void setShow_place(int show_place) {
        this.show_place = show_place;
    }

    public int getShow_times() {
        return show_times;
    }

    public void setShow_times(int show_times) {
        this.show_times = show_times;
    }

    public int getPlatfrom() {
        return platfrom;
    }

    public void setPlatfrom(int platfrom) {
        this.platfrom = platfrom;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public int getShow_again() {
        return show_again;
    }

    public void setShow_again(int show_again) {
        this.show_again = show_again;
    }

    public int getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(int msg_type) {
        this.msg_type = msg_type;
    }

    public boolean isSend_all() {
        return send_all;
    }

    public void setSend_all(boolean send_all) {
        this.send_all = send_all;
    }

    /**
     * 根据开始和结束时间来判断是否过期
     * @return
     */
    public boolean isExpired() {
        long now = System.currentTimeMillis();
        return now >= this.getStart_time().getTime() && now <= this.getEnd_time().getTime();
    }
}
