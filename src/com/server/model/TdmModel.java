package com.server.model;

public class TdmModel {
	
	private String kwd;
	private double score;
	public String getKwd() {
		return kwd;
	}
	public void setKwd(String kwd) {
		this.kwd = kwd;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public TdmModel(String kwd, double score) {
		super();
		this.kwd = kwd;
		this.score = score;
	}
	
	
	

}
