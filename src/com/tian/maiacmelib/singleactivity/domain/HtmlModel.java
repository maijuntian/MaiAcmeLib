package com.tian.maiacmelib.singleactivity.domain;

import java.io.Serializable;

/**
 * webview界面跳转的参数
 * @author maijuntian
 *
 */
public class HtmlModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	private String onResume;
	private String onOpen;
	
	public HtmlModel() {
	}

	public HtmlModel(String onResume, String onOpen) {
		super();
		this.onResume = onResume;
		this.onOpen = onOpen;
	}

	public String getOnResume() {
		return onResume;
	}

	public void setOnResume(String onResume) {
		this.onResume = onResume;
	}

	public String getOnOpen() {
		return onOpen;
	}

	public void setOnOpen(String onOpen) {
		this.onOpen = onOpen;
	}

}
