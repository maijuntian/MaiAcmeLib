package com.tian.maiacmelib.singleactivity.utils;

import com.tian.maiacmelib.singleactivity.domain.PageData;
import com.tian.maiacmelib.singleactivity.domain.PageData.PageActionType;
import com.tian.maiacmelib.singleactivity.grobal.GrobalResource;
import com.tian.maiacmelib.singleactivity.inter.IPageLife;

import android.content.Context;
import android.content.Intent;

/**
 * 页面跳转帮助类
 * @author maijuntian
 *
 */
public class PageJumpHelper {
	
	private static void sendBroadcast(Context context, PageData pageData){
		Intent intent = new Intent(GrobalResource.IntentAction_Page_Single_Open_Action);
		intent.putExtra(PageData.class.getName(), pageData);
		context.sendBroadcast(intent);
	}
	
	/**
	 * 返回主界面Activity
	 * @param context
	 */
	public static void ReturnMainPageJump(Context context){
		PageData pageData = new PageData(PageActionType.RETURN_MAIN);
		sendBroadcast(context, pageData);
	}

	/**
	 * 不带返回值的返回 
	 * @param context
	 */
	public static void ReturnPageJump(Context context){
		ReturnPageJumpForResult(context, null);
	}
	
	/**
	 * 带返回值的返回
	 * @param context
	 * @param result
	 */
	public static void ReturnPageJumpForResult(Context context, Object result){
		PageData pageData = new PageData(PageActionType.RETURN);
		pageData.setResult(result);
		sendBroadcast(context, pageData);
	}
	
	
	/**
	 * 打开界面
	 * 带参数
	 * @param context
	 * @param viewId
	 */
	public static void OpenPageJumpByViewId(Context context, Class iPageLifeClass, Object data){
		if(iPageLifeClass == null){
			throw new RuntimeException("请指定对应界面class"); 
		}
		PageData pageData = new PageData(PageActionType.OPEN, iPageLifeClass, data);
		sendBroadcast(context, pageData);
	}
	
	/**
	 * 打开界面
	 * 不带参数
	 * @param context
	 * @param viewId
	 */
	public static void OpenPageJumpByViewId(Context context, Class iPageLifeClass){
		if(iPageLifeClass == null){
			throw new RuntimeException("请指定对应界面class"); 
		}
		PageData pageData = new PageData(PageActionType.OPEN, iPageLifeClass);
		sendBroadcast(context, pageData);
	}
	
}
