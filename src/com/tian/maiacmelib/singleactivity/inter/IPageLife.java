package com.tian.maiacmelib.singleactivity.inter;


import com.tian.maiacmelib.singleactivity.domain.PageData.PageActionMode;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * 生命周期回调接口
 * @author maijuntian
 *
 */
public interface IPageLife{
	int bindViewId();  //绑定界面
	Class bindViewClass(); //自定义界面的class
	void bindActionMode(PageActionMode actionMode);  //设置启动模式
	PageActionMode getActionMode();  //获取启动模式
	void onCreate(Activity context, Object data);    //创建
	void onResume();  //重启
	void onFinish();  //删除
	void onPause();  //暂停
	void returnResult(Object result);  //界面跳转返回值
	void onBackPressed(); //返回键监听
}
