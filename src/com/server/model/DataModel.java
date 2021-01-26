package com.server.model;

import com.jfinal.plugin.activerecord.Record;

public class DataModel {
	
	private Object id;
	private Object query;
	private Object title;
	private Object qry_msg;
	private Object title_msg;
	private Object date;
	private Object ext;
	private Object ext_msg;
	private Object data_tag;
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public Object getQuery() {
		return query;
	}
	public void setQuery(Object query) {
		this.query = query;
	}
	public Object getTitle() {
		return title;
	}
	public void setTitle(Object title) {
		this.title = title;
	}
	public Object getQry_msg() {
		return qry_msg;
	}
	public void setQry_msg(Object qry_msg) {
		this.qry_msg = qry_msg;
	}
	public Object getTitle_msg() {
		return title_msg;
	}
	public void setTitle_msg(Object title_msg) {
		this.title_msg = title_msg;
	}
	public Object getDate() {
		return date;
	}
	public void setDate(Object date) {
		this.date = date;
	}
	public Object getExt() {
		return ext;
	}
	public void setExt(Object ext) {
		this.ext = ext;
	}
	public Object getExt_msg() {
		return ext_msg;
	}
	public void setExt_msg(Object ext_msg) {
		this.ext_msg = ext_msg;
	}
	public Object getData_tag() {
		return data_tag;
	}
	public void setData_tag(Object data_tag) {
		this.data_tag = data_tag;
	}
	public DataModel(Object id, Object query, Object title, Object qry_msg,
			Object title_msg, Object date, Object ext, Object ext_msg,
			Object data_tag) {
		super();
		this.id = id;
		this.query = query;
		this.title = title;
		this.qry_msg = qry_msg;
		this.title_msg = title_msg;
		this.date = date;
		this.ext = ext;
		this.ext_msg = ext_msg;
		this.data_tag = data_tag;
	}
	
	public DataModel(Record re){
		this.id = re.get("id");
		this.query = re.get("query");
		this.title = re.get("title");
		this.qry_msg = re.get("qry_msg");
		this.title_msg = re.get("title_msg");
		this.date = re.get("date");
		this.ext = re.get("ext");
		this.ext_msg = re.get("ext_msg");
		this.data_tag = re.get("data_tag");
	}
	
	
	

}
