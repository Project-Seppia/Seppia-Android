package com.seppia.android.project_seppia;

import android.content.Context;

/**
 * 应用程度配置类
 * 
 */
public class Settings {

	// 全局的Context
	public static Context GLOBAL_CONTEXT;

	// 标志应用程序的debuggable状态
	public static boolean isDebuggable;

	// 获取存储在Preference文件中的设备号所需的Key
	public static final String PREF_DEVICE_NO = "PREF_DEVICE_NO";
	public static final String PREF_DEVICE_NO_TYPE = "PREF_DEVICE_NO_TYPE";

	public static final String PREF_IS_SERVICE_STARTED = "PREF_IS_SERVICE_STARTED";

	// 设备号
	public static String GLOBAL_DEVICE_ID;
	public static String GLOBAL_DEVICE_INFO;
	public static String GLOBAL_DEVICE_NO_TYPE;
	public static String PUSH_TOKEN;
	public static String GLOBAL_VERSION_NAME;
	

	/*************************** Bundle Key ****************************/
	public static final String BUNDLE_LAST_ACTIVITY = "BUNDLE_LAST_ACTIVITY";
	public static final String BUNDLE_URL = "BUNDLE_URL";
//	public static String KK = "5TwCSFGL";
	public static String KK = "e2f9c324138a0c541739507935a95555";
	
	public static String MAIN_PAGE_URL = "http://baidu.com";
	
	public static boolean isTest = false;
}	
