package com.server.model;

public class ImgDetail{
	private String[] urls;
	private String[] tags;
	private String[] scores;
	private String[] mattypes;
	private String mat_type;
	private String ccid;
	private String ckid;
	public String[] getUrls() {
		return urls;
	}
	public void setUrls(String[] urls) {
		this.urls = urls;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	
	public String[] getScores() {
		return scores;
	}
	public void setScores(String[] scores) {
		this.scores = scores;
	}
	
	
	public String[] getMattypes() {
		return mattypes;
	}
	public void setMattypes(String[] mattypes) {
		this.mattypes = mattypes;
	}
	public String getMat_type() {
		return mat_type;
	}
	public void setMat_type(String mat_type) {
		this.mat_type = mat_type;
	}
	public String getCcid() {
		return ccid;
	}
	public void setCcid(String ccid) {
		this.ccid = ccid;
	}
	public String getCkid() {
		return ckid;
	}
	public void setCkid(String ckid) {
		this.ckid = ckid;
	}
	
	
	
	
	
}