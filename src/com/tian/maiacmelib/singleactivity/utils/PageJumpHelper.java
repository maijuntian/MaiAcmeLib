package com.tian.maiacmelib.singleactivity.utils;

import com.tian.maiacmelib.singleactivity.domain.HtmlModel;
import com.tian.maiacmelib.singleactivity.domain.PageData;
import com.tian.maiacmelib.singleactivity.domain.PageData.PageActionType;
import com.tian.maiacmelib.singleactivity.grobal.GrobalResource;

import android.content.Context;
import android.content.Intent;

/**
 * 页面跳转帮助类
 * @author maijuntian
 *
 */
public class PageJumpHelper {
	
	private static String HTML_ON_OPEN = "onOpen";  //html页面跳转的方法
	private static String HTML_ON_RESUME = "onResume";  //html页面回显的方法
	
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
	
	/**
	 * 原生打开html界面
	 * @param context 上下文
	 * @param pagePath  页面路径
	 */
	public static void OpenHtmlJump(Context context, String pagePath){
		OpenHtmlJump(context, pagePath, HTML_ON_OPEN, null);
	}
	
	/**
	 * 原生打开html界面
	 * @param context 上下文
	 * @param pagePath  页面路径
	 * @param params  js方法参数
	 */
	public static void OpenHtmlJump(Context context, String pagePath, String params){
		OpenHtmlJump(context, pagePath, HTML_ON_OPEN, params);
	}
	
	
	/**
	 * 原生打开html界面
	 * @param context 上下文
	 * @param pagePath  页面路径
	 * @param method  页面打开js方法
	 * @param params  js方法参数
	 */
	public static void OpenHtmlJump(Context context, String pagePath, String method, String params){
		if(pagePath == null || pagePath.equals("")){
			throw new RuntimeException("请指定具体的html页面"); 
		}
		PageData pageData = new PageData(PageActionType.OPEN_HTML);
		HtmlModel htmlModel = new HtmlModel();
		if(params == null || params.equals("")){
			htmlModel.setOnOpen("javascript:"+method+"('"+pagePath+"')");
		} else {
			htmlModel.setOnOpen("javascript:"+method+"('"+pagePath+"', '"+params+"')");
		}
		pageData.setData(htmlModel);
		sendBroadcast(context, pageData);
	}
	
	/**
	 * 原生返回html界面  + 不带结果返回
	 * @param context  上下文
	 */
	public static void ReturnHtmlJump(Context context){
		PageData pageData = new PageData(PageActionType.RETURN_HTML);
		sendBroadcast(context, pageData);
	}
	
	/**
	 * 原生返回html界面  + 带结果返回 + 默认js方法onResume
	 * @param context  上下文
	 * @param result  结果
	 */
	public static void ReturnHtmlJumpForResult(Context context, String result){
		ReturnHtmlJumpForResult(context, HTML_ON_RESUME, result);
	}
	
	/**
	 * 原生返回html界面  + 带结果返回
	 * @param context  上下文
	 * @param method  js回显方法
	 * @param result  结果
	 */
	public static void ReturnHtmlJumpForResult(Context context, String method, String result){
		PageData pageData = new PageData(PageActionType.RETURN_HTML);
		HtmlModel htmlModel = new HtmlModel();
		htmlModel.setOnResume("javascript:"+method+"("+result+"')");
		pageData.setResult(htmlModel);
		sendBroadcast(context, pageData);
	}
}
