package com.server.model;

public class SimModel {
	
	private String pairs;
	private String score;
	public SimModel(String pairs, String score) {
		super();
		this.pairs = pairs;
		this.score = score;
	}
	public String getPairs() {
		return pairs;
	}
	public void setPairs(String pairs) {
		this.pairs = pairs;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
	

}
