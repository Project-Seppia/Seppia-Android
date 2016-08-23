package com.seppia.android.project_seppia;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class AppCommon {
	/* 网络请求返回的代码约定 */
	public static final int CONST_RESPONSE_CODE_SUCCESS = 200; // 调用成功
	public static final int CONST_RESPONSE_CODE_SERVER_TOKEN_NOT_FIND = 401; // 调用成功
	public static final int CONST_RESPONSE_CODE_SERVER_ERROR = 500; // 服务器错误
	public static final int CONST_RESPONSE_CODE_BUSINESS_ERROR = 404; // 服务器返回调用失败
	public static final int CONST_RESPONSE_CODE_ERROR = 400; // 其它情况的调用失败

	public static final String WEB_SERVICE_API = "http://192.168.8.2"; // testing
//	public static final String WEB_SERVICE_API = "http://seppiajava-1.fyxfn8ksis.us-east-1.elasticbeanstalk.com"; // product

	public static final String PREF_SMS_CODE = "pref_sms_code";
	public static final String PREF_USER_INFO = "pref_user_info";
	
	/** Experience staus */
	public static final String EXPERIENCE_STATUS_NO_OPENING = "0";
	public static final String EXPERIENCE_STATUS_RECRUITMENT = "1";
	public static final String EXPERIENCE_STATUS_IN_EXPERIENCE = "2";
	public static final String EXPERIENCE_STATUS_OTHER = "3";
	public static final String EXPERIENCE_STATUS_FINISH = "4";

	/** Bundle data key*/
	public static final String BUNDLE_KEY_ADV_URL = "BUNDLE_KEY_ADV_URL";
	
	
	public static String newPasswd;
	public static List<Activity> activityStack = new ArrayList<Activity>();
	
	public static final String IMAGE_FILE_NAME = "/faceImage.jpg";
}
