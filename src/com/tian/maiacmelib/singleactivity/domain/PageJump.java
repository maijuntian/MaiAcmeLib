package com.tian.maiacmelib.singleactivity.domain;

import android.view.View;

import com.tian.maiacmelib.singleactivity.domain.PageData.PageActionType;
import com.tian.maiacmelib.singleactivity.inter.IPageLife;

/**
 * 界面跳转bean
 * 旧版
 * @author maijuntian
 *
 */
public class PageJump {
	public enum PageActionType{
		OPEN,
		RETURN,
		FINISH
	}
	
	public enum PageActionMode{
		CLEAR_ALL,
		FINISH_BEFORE,
	}

	private PageActionType actionType;  //动作
	
	private Class pageClass;  //界面对应的类
	
	private int viewId; //界面对应的id
	
	private View view; //对应界面
	
	private IPageLife iPageLife; 
	
	private Object result;
	
	private Object data;  //带上参数
	
	public PageJump(){}
	
	public PageJump(PageActionType actionType) {
		this.actionType = actionType;
	}

	/**
	 * 自定义view
	 * @param actionType
	 * @param pageClass
	 */
	public PageJump(PageActionType actionType, Class pageClass) {
		this.actionType = actionType;
		this.pageClass = pageClass;
	}
	
	/**
	 * 自定义view
	 * + 生命周期
	 * @param actionType
	 * @param pageClass
	 */
	public PageJump(PageActionType actionType, Class pageClass, IPageLife iPageLife) {
		this.actionType = actionType;
		this.pageClass = pageClass;
		this.iPageLife = iPageLife;
		
	}
	
	/**
	 * viewId
	 * @param actionType
	 * @param viewId
	 */
	public PageJump(PageActionType actionType, int viewId) {
		this.actionType = actionType;
		this.viewId = viewId;
	} 
	
	/**
	 * viewId
	 * + 生命周期
	 * @param actionType
	 * @param viewId
	 * @param iPageLife
	 */
	public PageJump(PageActionType actionType, int viewId, IPageLife iPageLife) {
		this.actionType = actionType;
		this.viewId = viewId;
		this.iPageLife = iPageLife;
	} 

	public PageActionType getActionType() {
		return actionType;
	}

	public Class getPageClass() {
		return pageClass;
	}

	public int getViewId() {
		return viewId;
	}

	public IPageLife getIPageLife(){
		return iPageLife;
	}
	
	public void setView(View view){
		this.view = view;
	}
	
	public View getView(){
		return this.view;
	}
	
	public void setResult(Object result){
		this.result = result;
	}
	
	public Object getResult(){
		return result;
	}
}
