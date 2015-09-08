package com.tian.maiacmelib.base;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;

/**
 * 用于处理退出程序时可以退出所有的activity，而编写的通用类
 * @author maijuntian
 *
 */
public class ActivityManager {
	private List<Activity> activityList = new LinkedList<Activity>();  
    private static ActivityManager instance;  
  
    private ActivityManager() {  
    }  
    // 单例模式中获取唯一的MyApplication实例   
    public static ActivityManager getInstance() {  
        if (null == instance) {  
            instance = new ActivityManager();  
        }  
        return instance;  
    }  
  
    // 添加Activity到容器中   
    public void addActivity(Activity activity) {  
        activityList.add(activity);  
    }  
    
    public void removeActivity(Activity activity){
    	activityList.remove(activity);
    }
  
    // 遍历所有Activity并finish   
    public void exit() {  
        for (Activity activity : activityList) {  
        	if(!activity.isFinishing()){
                activity.finish();  
        	}
        }  
        System.exit(0);  
    } 
    
    public void exitBefore() {  
    	for (int i = 0; i < activityList.size()-1; i++) {
    		Activity activity = activityList.get(i);
    		if(!activity.isFinishing()){
    			activity.finish();
    		}
		}
    } 
}
