package com.server.model;

import com.jfinal.plugin.activerecord.Record;

public class LabHisModel {
	
	private Object res_id;
	private Object user_id;
	private Object rel_val;
	private Object kwds;
	private Object entitys;
	private Object exts;
	private Object date;
	private Object tab;
	public Object getRes_id() {
		return res_id;
	}
	public void setRes_id(Object res_id) {
		this.res_id = res_id;
	}
	public Object getUser_id() {
		return user_id;
	}
	public void setUser_id(Object user_id) {
		this.user_id = user_id;
	}
	public Object getRel_val() {
		return rel_val;
	}
	public void setRel_val(Object rel_val) {
		this.rel_val = rel_val;
	}
	public Object getKwds() {
		return kwds;
	}
	public void setKwds(Object kwds) {
		this.kwds = kwds;
	}
	public Object getEntitys() {
		return entitys;
	}
	public void setEntitys(Object entitys) {
		this.entitys = entitys;
	}
	public Object getExts() {
		return exts;
	}
	public void setExts(Object exts) {
		this.exts = exts;
	}
	public Object getDate() {
		return date;
	}
	public void setDate(Object date) {
		this.date = date;
	}
	public Object getTab() {
		return tab;
	}
	public void setTab(Object tab) {
		this.tab = tab;
	}
	public LabHisModel(Object res_id, Object user_id, Object rel_val,
			Object kwds, Object entitys, Object exts, Object date, Object tab) {
		super();
		this.res_id = res_id;
		this.user_id = user_id;
		this.rel_val = rel_val;
		this.kwds = kwds;
		this.entitys = entitys;
		this.exts = exts;
		this.date = date;
		this.tab = tab;
	}
	public LabHisModel(Record re) {
		super();
		this.res_id = re.get("res_id");
		this.user_id = re.get("user_id");
		this.rel_val = re.get("rel_val");
		this.kwds = re.get("kwds");
		this.entitys = re.get("entitys");
		this.exts = re.get("exts");
		this.date = re.get("date");
		this.tab = re.get("tab");
	}
	
	
	

}
