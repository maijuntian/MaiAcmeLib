package com.tian.maiacmelib.singleactivity.base;

import java.util.Stack;

import com.mai.maiacmelib.R;
import com.tian.maiacmelib.base.ActivityManager;
import com.tian.maiacmelib.singleactivity.domain.PageData;
import com.tian.maiacmelib.singleactivity.domain.PageInfo;
import com.tian.maiacmelib.singleactivity.grobal.GrobalResource;
import com.tian.maiacmelib.singleactivity.inter.IPageLife;
import com.tian.maiacmelib.singleactivity.utils.PageJumpHelper;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 单一activity基类
 * @author maijuntian
 *
 */
public abstract class SingleActivity extends Activity{
    private String LogTag = "MaiAcmeLib";
    
    private WebView webView;  //记住webview
    private PageReciever reciever;
    
	Stack<PageInfo> pageStack = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		// 把activity推入历史栈，退出app后清除历史栈，避免造成内存溢出
		ActivityManager.getInstance().addActivity(this);
		
		pageStack = new Stack<PageInfo>();
	}

	@Override
	public void setContentView(int layoutResID) {
		View view = LayoutInflater.from(this).inflate(layoutResID, null);
		super.setContentView(view);
		PageInfo pageInfo = new PageInfo();
		pageInfo.setView(view);
		pageStack.add(pageInfo);
	}

	@Override
	public void setContentView(View view) {
		if(view != null){
			super.setContentView(view);	
		}
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		if(reciever == null){
			reciever = new PageReciever();
			registerReceiver(reciever, new IntentFilter(GrobalResource.IntentAction_Page_Single_Open_Action));
		}
		
		if(pageStack.size() > 0){ //重现状态
			if(pageStack.peek().getIPageLife() != null)
				pageStack.peek().getIPageLife().onResume();
		}
	}

	

	@Override
	protected void onPause() {
		super.onPause();
		if(reciever != null){
			unregisterReceiver(reciever);
			reciever = null;
		}
		
		if(pageStack.size() > 0){ //重现状态
			if(pageStack.peek().getIPageLife() != null)
				pageStack.peek().getIPageLife().onPause();
		}
	}

	@Override
	public void onBackPressed() {
		if(!getFragmentManager().popBackStackImmediate()){
			if(pageStack.peek().getIPageLife() != null){
				pageStack.peek().getIPageLife().onBackPressed();
			} else {
				PageJumpHelper.ReturnPageJump(SingleActivity.this);
			}
		}
	}
	
	/**
	 * 返回值
	 * @param data
	 */
	public void returnResult(Object data){
		
	}
	
	@Override
	public void finish() {
		ActivityManager.getInstance().removeActivity(this);
		super.finish();
	}
	
	private void popPage(){
		PageInfo finishPage = pageStack.pop();
		if(finishPage.getIPageLife() != null){
			finishPage.getIPageLife().onPause();
			finishPage.getIPageLife().onFinish();
		}
	}
	
	private void clearPage(){
		while (pageStack.size() > 0) {
			popPage();
		}
	}

	/**
	 * 页面跳转接收器
	 * @author maijuntian
	 *
	 */
	private class PageReciever extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			PageData pageData = (PageData) intent.getSerializableExtra(PageData.class.getName());
			log("页面跳转接收器------>对应动作：" + pageData.getActionType());
			switch (pageData.getActionType()) {
			case OPEN:
				open(context, pageData);
				break;
			case RETURN:  
				returnPage(pageData);
				break;
			case RETURN_MAIN:
				returnMain(pageData);
			case FINISH: 
				finish();
				break;
			default:
				finish();
				break;
			}
		}
		
	}
	
	/**
	 * 返回到主activity
	 * @param pageData
	 */
	private void returnMain(PageData pageData) {
		Animation anim_right_in = AnimationUtils.loadAnimation(SingleActivity.this.getBaseContext(), R.anim.page_right_in);
		Animation anim_left_out = AnimationUtils.loadAnimation(SingleActivity.this.getBaseContext(), R.anim.page_left_out);
		
		
		PageInfo curPage2 = pageStack.pop();
		if(curPage2.getIPageLife() != null){
			curPage2.getIPageLife().onPause();  //暂停生命周期
			curPage2.getIPageLife().onFinish(); //结束生命周期
		} 
		curPage2.getView().setAnimation(anim_left_out);
		if(pageStack.size() > 0){ //存在返回界面
			while(pageStack.size() != 1){
				pageStack.pop();
			}
			PageInfo backPage = pageStack.peek(); //返回界面	
			backPage.getView().setAnimation(anim_right_in);
			super.setContentView(backPage.getView());
			if(pageStack.size() == 1){  //调用activity中的重启生命周期
				onResume();
				if(pageData.getResult() != null){ //有返回值
					returnResult(pageData.getResult());
				}
				return;
			}
			if(backPage.getIPageLife() != null){
				backPage.getIPageLife().onResume(); //重启生命周期
				if(pageData.getResult() != null){ //有返回值
					backPage.getIPageLife().returnResult(pageData.getResult());
				}
			}
		} else {
			finish();
		}
	}
	
	private void returnPage (PageData pageData){
		Animation anim_right_out = AnimationUtils.loadAnimation(SingleActivity.this.getBaseContext(), R.anim.page_right_out);
		Animation anim_left_in = AnimationUtils.loadAnimation(SingleActivity.this.getBaseContext(), R.anim.page_left_in);
		
		PageInfo curPage = pageStack.pop();
		if(curPage.getIPageLife() != null){
			 curPage.getIPageLife().onPause();  //暂停生命周期
			 curPage.getIPageLife().onFinish(); //结束生命周期
		} 
		/** 加动画 */
		curPage.getView().setAnimation(anim_right_out);
		if(pageStack.size() > 0){ //存在返回界面
			PageInfo backPage = pageStack.peek(); //返回界面	
			backPage.getView().setAnimation(anim_left_in);
			setContentView(backPage.getView());
			if(pageStack.size() == 1){  //调用activity中的重启生命周期
				onResume();
				if(pageData.getResult() != null){ //有返回值
					returnResult(pageData.getResult());
				}
				return;
			}
			if(backPage.getIPageLife() != null){
				backPage.getIPageLife().onResume(); //重启生命周期
				if(pageData.getResult() != null){ //有返回值
					backPage.getIPageLife().returnResult(pageData.getResult());
				}
			}
		} else {
			finish();
		}
	}
	
	
	
	/**
	 * 打开界面
	 * @param pageData
	 */
	private void open(Context context, PageData pageData){
		Animation anim_right_in = AnimationUtils.loadAnimation(SingleActivity.this.getBaseContext(), R.anim.page_right_in);
		Animation anim_left_out = AnimationUtils.loadAnimation(SingleActivity.this.getBaseContext(), R.anim.page_left_out);
		
		
		/**
		 * 保存界面信息
		 */
		IPageLife iPageLife = null;
		try {
			iPageLife = (IPageLife) pageData.getiPageLifeClass().newInstance();
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
		PageInfo pageInfo = new PageInfo();
		pageInfo.setIPageLife(iPageLife);
		
		//=========================生成界面==================================
		View view = null;
		if(iPageLife.bindViewClass() != null){
			try {
				view = (View) iPageLife.bindViewClass().getConstructor(Context.class).newInstance(SingleActivity.this.getBaseContext());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			view = LayoutInflater.from(context).inflate(iPageLife.bindViewId(), null);
		}
		pageInfo.setView(view);  //记录当前界面
		/** 加动画 */
		pageStack.peek().getView().setAnimation(anim_left_out);
		view.setAnimation(anim_right_in);
		setContentView(view);
		//=====================================================================
		
		if(pageStack.peek().getIPageLife() != null)
			pageStack.peek().getIPageLife().onPause();
		iPageLife.onCreate(SingleActivity.this, pageData.getData());  //初始化界面
		iPageLife.onResume(); //显现
		
		//========================启动模式===================================
		if(iPageLife.getActionMode() != null){
			switch(iPageLife.getActionMode()){
			case FINISH_BEFORE:
				log("页面跳转接收器------>启动模式：FINISH_BEFORE");
				popPage();
				break;
			case CLEAR_ALL:
				log("页面跳转接收器------>启动模式：CLEAR_ALL");
				clearPage();
				break;
			}
		}
		//=================================================================
		
		pageStack.add(pageInfo);
		log("pageStack大小:" + pageStack.size());
	}
	
	/** 初始化左边按钮 */
	private void setBaseLeft(int leftViewId){
		TextView tvBaseBack = (TextView) this.findViewById(leftViewId);
		tvBaseBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	/** 初始化标题按钮 */
	private void setBaseTitle(int titleViewId, String title){
		TextView tvBaseTitle = (TextView) this.findViewById(titleViewId);
		tvBaseTitle.setText(title);
	}

	/** 初始化右边按钮 */
	private void setBaseRight(int rightViewId, Drawable drawable, String rightText, OnClickListener onClickListener){
		TextView tvBaseRight = (TextView) this.findViewById(rightViewId);
		
		if(onClickListener != null){
			tvBaseRight.setOnClickListener(onClickListener);
		}
		if(drawable != null){
			tvBaseRight.setText(rightText);
			drawable.setBounds(0, 0, drawable.getIntrinsicHeight(), drawable.getIntrinsicWidth());//必须设置图片大小，否则不显示
			tvBaseRight.setCompoundDrawables(drawable, null, null, null);
		}
	}
	
	protected void initBaseView(int leftViewId, int titleViewId, String title){
		setBaseLeft(leftViewId);
		setBaseTitle(titleViewId, title);
	}
	
	protected void initBaseView(int leftViewId, int titleViewId, int rightViewId, String title, String rightText, Drawable rightDrawable){
		setBaseLeft(leftViewId);
		setBaseTitle(titleViewId,title);
		setBaseRight(rightViewId, rightDrawable, rightText, null);
	}
	
	protected void initBaseView(int leftViewId, int titleViewId, int rightViewId, String title, String rightText, Drawable rightDrawable, OnClickListener listener){
		setBaseLeft(leftViewId);
		setBaseTitle(titleViewId,title);
		setBaseRight(rightViewId, rightDrawable, rightText, listener);
	}
	
	protected void log(String msg){
		Log.i(LogTag, msg);
	}
	
}
