package com.juunew.admin.entity;

/**
 * Created by juunew on 2017/6/20.
 */
public class GamesEntity {

    private int id;
    private int status;         //游戏状态，1. 未开始 2. 游戏中  3. 已结束或解散
    private int anchor_id;
    private int gamekinds_id;
    private int lives_id;
    private String created_at;
    private String end_at;
    private String odd;
    private String players;        //参与玩家
    private String result;         //游戏的战况信息
    private String start_time;
    private int sign_cost;  //报名费用
    private int cost_id;    //收费的ID
    private int quit;
    private int finished;   //是否打完退出，0 未打  1 开打了


    private String nickname;
    private String desc;   //游戏分数详情
    private String room_type;   //房间类型；分为私房和俱乐部
    private String code;   //房间号 lives表中的code
    private int num;   //房间人数

    private int times;   //每个游戏 的总次数




    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getAnchor_id() {
        return anchor_id;
    }

    public void setAnchor_id(int anchor_id) {
        this.anchor_id = anchor_id;
    }

    public int getGamekinds_id() {
        return gamekinds_id;
    }

    public void setGamekinds_id(int gamekinds_id) {
        this.gamekinds_id = gamekinds_id;
    }

    public int getLives_id() {
        return lives_id;
    }

    public void setLives_id(int lives_id) {
        this.lives_id = lives_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getEnd_at() {
        return end_at;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
    }

    public String getOdd() {
        return odd;
    }

    public void setOdd(String odd) {
        this.odd = odd;
    }

    public String getPlayers() {
        return players;
    }

    public void setPlayers(String players) {
        this.players = players;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public int getSign_cost() {
        return sign_cost;
    }

    public void setSign_cost(int sign_cost) {
        this.sign_cost = sign_cost;
    }

    public int getCost_id() {
        return cost_id;
    }

    public void setCost_id(int cost_id) {
        this.cost_id = cost_id;
    }

    public int getQuit() {
        return quit;
    }

    public void setQuit(int quit) {
        this.quit = quit;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
