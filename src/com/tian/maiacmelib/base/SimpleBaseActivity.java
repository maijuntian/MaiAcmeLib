package com.tian.maiacmelib.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 简单基类
 * @author maijuntian
 */
public abstract class SimpleBaseActivity extends Activity{
    private String LogTag = "MaiAcmeLib";
    
    private TextView tvBaseBack, tvBaseTitle, tvBaseRight; 
    private ProgressDialog pDialog;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		// 把activity推入历史栈，退出app后清除历史栈，避免造成内存溢出
		ActivityManager.getInstance().addActivity(this);
		
	}
	
	protected abstract void initView();
	protected abstract void initListener();
	
	/** 初始化左边按钮 */
	private void setBaseLeft(int leftViewId){
		tvBaseBack = (TextView) this.findViewById(leftViewId);
		tvBaseBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	/** 初始化标题按钮 */
	private void setBaseTitle(int titleViewId, String title){
		tvBaseTitle = (TextView) this.findViewById(titleViewId);
		tvBaseTitle.setText(title);
	}

	/** 初始化右边按钮 */
	private void setBaseRight(int rightViewId, Drawable drawable, String rightText, OnClickListener onClickListener){
		tvBaseRight = (TextView) this.findViewById(rightViewId);
		
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
		Log.e(LogTag, msg);
	}

	@Override
	public void finish() {
		ActivityManager.getInstance().removeActivity(this);
		super.finish();
	}
	
	protected void showToast(String msg){
		Toast.makeText(this, msg, 0).show();
	}
	protected void showToast(int resId){
		Toast.makeText(this, resId, 0).show();
	}
	
	protected void showProgressDialog(String msg){
		if(pDialog == null){
			pDialog =  new ProgressDialog(this);
			pDialog.setMessage(msg);
		}
		pDialog.show();
	}
	
	protected void dismissProgressDialog(){
		if(pDialog != null){
			if(pDialog.isShowing())
				pDialog.dismiss();
		}
	}
	
	protected void showProgressDialog(int resId){
		showProgressDialog(getString(resId));
	}

	protected void nextActivity(Class activityClass){
		this.startActivity(new Intent(this, activityClass));
	}
}
