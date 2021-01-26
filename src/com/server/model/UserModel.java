package com.server.model;

import com.jfinal.plugin.activerecord.Model; 

public class UserModel {

	private Object username;
	private Object pwd;
	private Object nick;
	private Object user_agent;
	public Object getUsername() {
		return username;
	}
	public void setUsername(Object username) {
		this.username = username;
	}
	public Object getPwd() {
		return pwd;
	}
	public void setPwd(Object pwd) {
		this.pwd = pwd;
	}
	public Object getNick() {
		return nick;
	}
	public void setNick(Object nick) {
		this.nick = nick;
	}
	public Object getUser_agent() {
		return user_agent;
	}
	public void setUser_agent(Object user_agent) {
		this.user_agent = user_agent;
	}
	public UserModel(Object username, Object pwd, Object nick, Object user_agent) {
		super();
		this.username = username;
		this.pwd = pwd;
		this.nick = nick;
		this.user_agent = user_agent;
	}
	@Override
	public String toString() {
		return "{username:" + username + ", pwd:" + pwd + ", nick:" + nick
				+ ", user_agent:" + user_agent + "}";
	}
	
}
    