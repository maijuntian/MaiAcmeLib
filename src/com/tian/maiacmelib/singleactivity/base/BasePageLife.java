package com.tian.maiacmelib.singleactivity.base;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.tian.maiacmelib.singleactivity.domain.PageData.PageActionMode;
import com.tian.maiacmelib.singleactivity.inter.IPageLife;
import com.tian.maiacmelib.singleactivity.utils.PageJumpHelper;

public class BasePageLife implements IPageLife{
	
	private static final long serialVersionUID = -1L;
	
	private PageActionMode actionMode;
	protected Activity context;
	
	/**
	 * 获取上下文
	 * @return context
	 */
	protected Activity getBaseContext(){
		return context;
	}

	/**
	 * 绑定界面的id
	 */
	@Override
	public int bindViewId() {
		return 0;
	}

	/**
	 * 绑定自定义界面
	 */
	@Override
	public Class bindViewClass() {
		return null;
	}

	/**
	 * 创建界面的回调方法，
	 * 注意要super
	 * 不然不能获取上下文
	 */
	@Override
	public void onCreate(Activity context, Object data) {
		this.context = context;
	}

	/**
	 * 重启界面回调方法
	 */
	@Override
	public void onResume() {}

	/**
	 * 关闭界面回调方法
	 */
	@Override
	public void onFinish() {}

	/**
	 * 界面返回值回调
	 */
	@Override
	public void returnResult(Object result) {}

	/**
	 * 点击后退
	 */
	@Override
	public void onBackPressed() {
		PageJumpHelper.ReturnPageJump(getBaseContext());
	}

	/**
	 * 设置启动模式
	 */
	@Override
	public void bindActionMode(PageActionMode actionMode) {
		this.actionMode = actionMode;
	}

	@Override
	public PageActionMode getActionMode() {
		return actionMode;
	}

	@Override
	public void onPause() {
		
	}
	
	protected View findViewById(int id){
		return context.findViewById(id); 
	}

}
