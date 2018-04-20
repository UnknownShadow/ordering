package com.juunew.admin.entity;

import java.util.Date;

public class GameUserEntity {

	//用户身份，1：总代理，2：代理，3：玩家，4：合伙人
	public final static int First_Level_Agent = 1;
	public final static int Two_Level_Agent = 2;
	public final static int Player = 3;
	public final static int Partner = 4;


//1、高级管理员；2管理员；3、运营；4、财务；5、代理；6、无；8、客服；
	public final static int SeniorAdministratorRole = 1;
	public final static int AdministratorsRole = 2;
	public final static int OperateRole = 3;
	public final static int FinanceRole = 4;
	public final static int AgentRole = 5;
	public final static int NoneRole = 6;
	public final static int CustomerServiceRole = 8;



	private int id;
	private String nickname;		//玩家昵称
	private String proxyNickname;		//代理玩家昵称
	private String openid;
	private int anchor;
	private String token;
	private String created_at;		//创建时间
	private int money;			//用户金币数
	private int fans_count;  	//粉丝人数
	private int diamond;		//游戏钻石
	private String device_token;	//用于ios发送的设备token
	private int status;			//'用户状态 1. 未激活  2. 正常 3. 已禁用'
	private String jpush_id;	//推送id
	private int experience;		//经验
	private String slogan;		//个性签名
	private String phone;		//手机号码
	private String updated_at;	//更新时间
	private int user_status;		//用户身份
	private String userStatus;		//String类型用户身份
	private int users_id;
	private  String role;			//用户身份  数字标识
	private  int role_id;			//用户身份
	private int fatherproxy_id;		//父级代理id
	private String fatherproxy_date;	//父级代理创建的时间；
	private String fatherproxy_nickname;	//父级代理昵称；
	private String username;
	private String login_time;		//登录时间
	private String perm;		//用户身份	文字标识
	private int childproxytotal;		//子代理个数
	private int integral;		//总积分（单位：分）
	private int superior_proxy_status;		//有效上级代理
	private String adjuster;		//充值后赠送钻石比例设置者
	private double total_integral;		//总积分（单位：元）
	private int invite_code;		//邀请码，针队玩家有效

	private int user_source;		//用户来源 （是否为被推广用户）1：默认，2：被推广用户
	private double total_recharge;		//用户总充值金额（单位：元）


	private int superior_user_id;		//上级代理ID
	private String superior_user_nickname;		//上级代理昵称
	private String desc;		//用于描述
	private String gameDetails;		//什么游戏，打了几局


	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getGameDetails() {
		return gameDetails;
	}

	public void setGameDetails(String gameDetails) {
		this.gameDetails = gameDetails;
	}

	public int getSuperior_user_id() {
		return superior_user_id;
	}

	public void setSuperior_user_id(int superior_user_id) {
		this.superior_user_id = superior_user_id;
	}

	public String getSuperior_user_nickname() {
		return superior_user_nickname;
	}

	public void setSuperior_user_nickname(String superior_user_nickname) {
		this.superior_user_nickname = superior_user_nickname;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getInvite_code() {
		return invite_code;
	}

	public void setInvite_code(int invite_code) {
		this.invite_code = invite_code;
	}

	public double getTotal_recharge() {
		return total_recharge;
	}

	public void setTotal_recharge(double total_recharge) {
		this.total_recharge = total_recharge;
	}

	public int getUser_source() {
		return user_source;
	}

	public void setUser_source(int user_source) {
		this.user_source = user_source;
	}

	public double getTotal_integral() {
		return total_integral;
	}

	public void setTotal_integral(double total_integral) {
		this.total_integral = total_integral;
	}

	public int getSuperior_proxy_status() {
		return superior_proxy_status;
	}

	public void setSuperior_proxy_status(int superior_proxy_status) {
		this.superior_proxy_status = superior_proxy_status;
	}

	public String getFatherproxy_nickname() {
		return fatherproxy_nickname;
	}

	public void setFatherproxy_nickname(String fatherproxy_nickname) {
		this.fatherproxy_nickname = fatherproxy_nickname;
	}

	public String getAdjuster() {
		return adjuster;
	}

	public void setAdjuster(String adjuster) {
		this.adjuster = adjuster;
	}

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public String getProxyNickname() {
		return proxyNickname;
	}

	public void setProxyNickname(String proxyNickname) {
		this.proxyNickname = proxyNickname;
	}

	public int getChildproxytotal() {
		return childproxytotal;
	}

	public void setChildproxytotal(int childproxytotal) {
		this.childproxytotal = childproxytotal;
	}

	public String getPerm() {
		return perm;
	}

	public void setPerm(String perm) {
		this.perm = perm;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public int getAnchor() {
		return anchor;
	}

	public void setAnchor(int anchor) {
		this.anchor = anchor;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getFans_count() {
		return fans_count;
	}

	public void setFans_count(int fans_count) {
		this.fans_count = fans_count;
	}

	public int getDiamond() {
		return diamond;
	}

	public void setDiamond(int diamond) {
		this.diamond = diamond;
	}

	public String getDevice_token() {
		return device_token;
	}

	public void setDevice_token(String device_token) {
		this.device_token = device_token;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getJpush_id() {
		return jpush_id;
	}

	public void setJpush_id(String jpush_id) {
		this.jpush_id = jpush_id;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public int getUser_status() {
		return user_status;
	}

	public void setUser_status(int user_status) {
		this.user_status = user_status;
	}

	public int getUsers_id() {
		return users_id;
	}

	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getFatherproxy_id() {
		return fatherproxy_id;
	}

	public void setFatherproxy_id(int fatherproxy_id) {
		this.fatherproxy_id = fatherproxy_id;
	}

	public String getFatherproxy_date() {
		return fatherproxy_date;
	}

	public void setFatherproxy_date(String fatherproxy_date) {
		this.fatherproxy_date = fatherproxy_date;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String getLogin_time() {
		return login_time;
	}

	public void setLogin_time(String login_time) {
		this.login_time = login_time;
	}
}
