package com.server.model;

public class UnilmModel {

	private String style;
	private String title;

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UnilmModel(String style, String title) {
		this.style = style;
		this.title = title;
	}
}
