package com.tian.maiacmelib.singleactivity.domain;

import android.view.View;

import com.tian.maiacmelib.singleactivity.inter.IPageLife;

/**
 * 后台界面记录bean
 * @author maijuntian
 *
 */
public class PageInfo {
	PageData pageData;  //数据
	IPageLife iPageLife; //生命周期
	View view;  //界面

	public PageData getPageData() {
		return pageData;
	}

	public void setPageData(PageData pageData) {
		this.pageData = pageData;
	}

	public IPageLife getIPageLife() {
		return iPageLife;
	}

	public void setIPageLife(IPageLife iPageLife) {
		this.iPageLife = iPageLife;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

}
