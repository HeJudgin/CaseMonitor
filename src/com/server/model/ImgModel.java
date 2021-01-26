package com.server.model;

import java.util.HashMap;
import java.util.List;




public class ImgModel {
	
	
	private int req_num;
	private String query_grp;
	private List<ImgDetail> img_details;
	
	public String getQuery_grp() {
		return query_grp;
	}
	public void setQuery_grp(String query_grp) {
		this.query_grp = query_grp;
	}
	
	public int getReq_num() {
		return req_num;
	}
	public void setReq_num(int req_num) {
		this.req_num = req_num;
	}
	public List<ImgDetail> getImg_details() {
		return img_details;
	}
	public void setImg_details(List<ImgDetail> img_details) {
		this.img_details = img_details;
	}
	public ImgModel(int req_num, String query_grp,
			List<ImgDetail> img_details) {
		super();
		this.req_num = req_num;
		this.query_grp = query_grp;
		this.img_details = img_details;
	}
	
	
	
	
	
	
	
	
	

}
