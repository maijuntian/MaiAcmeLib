package com.tian.maiacmelib.singleactivity.domain;

import java.io.Serializable;

import com.tian.maiacmelib.singleactivity.inter.IPageLife;

import android.view.View;

/**
 * 页面跳转参数bean
 *   新版
 * @author maijuntian
 */
public class PageData implements Serializable{
	
	public enum PageActionType{
		OPEN,
		FINISH,
		RETURN,
		RETURN_MAIN
	}
	
	public enum PageActionMode{
		CLEAR_ALL,
		FINISH_BEFORE,
	}

	private PageActionType actionType;  //动作
	
	private Class iPageLifeClass; 
	
	private Object result;
	
	private Object data;  //带上参数
	
	public PageData(){}
	
	/**
	 * 自定义view
	 * @param actionType
	 * @param pageClass
	 */
	public PageData(PageActionType actionType) {
		this.actionType = actionType;
	}
	
	/**
	 * viewId
	 * + 生命周期
	 * @param actionType
	 * @param pageClass
	 */
	public PageData(PageActionType actionType, Class iPageLifeClass) {
		this.actionType = actionType;
		this.iPageLifeClass = iPageLifeClass;
	}
	
	/**
	 * viewId
	 * + 生命周期 + 参数
	 * @param actionType
	 * @param pageClass
	 */
	public PageData(PageActionType actionType, Class iPageLifeClass, Object data) {
		this.actionType = actionType;
		this.iPageLifeClass = iPageLifeClass;
		this.data = data;
	}
	
	
	public PageActionType getActionType() {
		return actionType;
	}

	
	public void setResult(Object result){
		this.result = result;
	}
	
	public Object getResult(){
		return result;
	}

	public Class getiPageLifeClass() {
		return iPageLifeClass;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
