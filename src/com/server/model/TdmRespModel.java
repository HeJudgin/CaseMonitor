package com.server.model;

import java.util.List;

public class TdmRespModel {
	
	private String ec;
	private String key;
	private List<TdmModel> kwds;

	public List<TdmModel> getKwds() {
		return kwds;
	}

	public void setKwds(List<TdmModel> kwds) {
		this.kwds = kwds;
	}
	
	

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

	public TdmRespModel(String ec, String key, List<TdmModel> kwds) {
		super();
		this.ec = ec;
		this.key = key;
		this.kwds = kwds;
	}

	

	
	
	
	

}
