package com.server.model;

public class SampleModel {
	
	private String ckid;
	private String accid;
	private String planid;
	private String grpid;
	private String query;
	private String kwd;
	private Object match_type;
	private Object qry_cls;
	private Object kwd_cls;
	private Object pid;
	private Object serv_tp;
	private Object city_region;
	private Object is_logo;
	private Object src_ctr;
	
	
	
	
	public String getAccid() {
		return accid;
	}
	public void setAccid(String accid) {
		this.accid = accid;
	}
	public String getPlanid() {
		return planid;
	}
	public void setPlanid(String planid) {
		this.planid = planid;
	}
	public String getGrpid() {
		return grpid;
	}
	public void setGrpid(String grpid) {
		this.grpid = grpid;
	}
	public String getCkid() {
		return ckid;
	}
	public void setCkid(String ckid) {
		this.ckid = ckid;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getKwd() {
		return kwd;
	}
	public void setKwd(String kwd) {
		this.kwd = kwd;
	}
	
	
	public Object getMatch_type() {
		return match_type;
	}
	public void setMatch_type(Object match_type) {
		this.match_type = match_type;
	}
	public Object getQry_cls() {
		return qry_cls;
	}
	public void setQry_cls(Object qry_cls) {
		this.qry_cls = qry_cls;
	}
	public Object getKwd_cls() {
		return kwd_cls;
	}
	public void setKwd_cls(Object kwd_cls) {
		this.kwd_cls = kwd_cls;
	}
	public Object getPid() {
		return pid;
	}
	public void setPid(Object pid) {
		this.pid = pid;
	}
	public Object getServ_tp() {
		return serv_tp;
	}
	public void setServ_tp(Object serv_tp) {
		this.serv_tp = serv_tp;
	}
	public Object getCity_region() {
		return city_region;
	}
	public void setCity_region(Object city_region) {
		this.city_region = city_region;
	}
	public Object getIs_logo() {
		return is_logo;
	}
	public void setIs_logo(Object is_logo) {
		this.is_logo = is_logo;
	}
	public Object getSrc_ctr() {
		return src_ctr;
	}
	public void setSrc_ctr(Object src_ctr) {
		this.src_ctr = src_ctr;
	}
	public SampleModel(String ckid, String accid, String planid, String grpid, String query, String kwd,
			Object match_type, Object qry_cls, Object kwd_cls, Object pid, Object serv_tp, Object city_region,
			Object is_logo, Object src_ctr) {
		super();
		this.ckid = ckid;
		this.accid = accid;
		this.planid = planid;
		this.grpid = grpid;
		this.query = query;
		this.kwd = kwd;
		this.match_type = match_type;
		this.qry_cls = qry_cls;
		this.kwd_cls = kwd_cls;
		this.pid = pid;
		this.serv_tp = serv_tp;
		this.city_region = city_region;
		this.is_logo = is_logo;
		this.src_ctr = src_ctr;
	}
	
	

}
