package com.server.model;

import java.util.List;

public class UnilmRespModel {

	private String ec;
	private String key;
	private List<UnilmModel> titles;

	public String getEc() {
		return ec;
	}

	public void setEc(String ec) {
		this.ec = ec;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<UnilmModel> getTitles() {
		return titles;
	}

	public void setTitles(List<UnilmModel> titles) {
		this.titles = titles;
	}

	public UnilmRespModel(String ec, String key, List<UnilmModel> titles) {
		this.ec = ec;
		this.key = key;
		this.titles = titles;
	}
}
