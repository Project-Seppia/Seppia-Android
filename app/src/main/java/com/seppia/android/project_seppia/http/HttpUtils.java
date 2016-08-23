package com.seppia.android.project_seppia.http;

import android.os.Build;

import com.seppia.android.project_seppia.AppCommon;
import com.seppia.android.project_seppia.R;
import com.seppia.android.project_seppia.Settings;
import com.seppia.android.project_seppia.dto.JsonPack;
import com.seppia.android.project_seppia.utils.LogUtils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础Http调用方法
 *
 */
public class HttpUtils {

	private static final String TAG = HttpUtils.class.getName();

	public static JsonPack doGet(String funcType, List<NameValuePair> pairList) {
		JsonPack jp = new JsonPack();
		try {
			HttpHelper.setUseGzip(true);
			HttpGet request = HttpHelper.createGet(getFullUrl(funcType), addBaseParams(pairList));
			request.setHeader("Connection", "close");
			request.setHeader("accept", "application/json");
			request.setHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8");
			addBaseHeader(request);
			HttpResponse httpResponse = HttpHelper.doRequest(request);
			return getJsonPackFromResponse(httpResponse);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.logE(TAG, e);
			jp.setCode(400);
			jp.setErr_msg(Settings.GLOBAL_CONTEXT.getString(R.string.info_error_net));
			return jp;
		}
	}

	public static JsonPack doPost(String funcType, List<NameValuePair> pairList) {
		 List<NameValuePair> urlParamsList = new ArrayList<NameValuePair>(pairList);
		return doPost(funcType, pairList, urlParamsList);
	}

	public static JsonPack doPost(String funcType, List<NameValuePair> pairList, List<NameValuePair> urlParamsList) {
		JsonPack jp = new JsonPack();
		try {
			HttpHelper.setUseGzip(true);
			HttpPost request = HttpHelper.createJsonPostWithParams(getFullUrl(funcType), addBaseParams(pairList), addBaseParams(urlParamsList));
			request.setHeader("Connection", "close");
			addBaseHeader(request);
			HttpResponse httpResponse = HttpHelper.doRequest(request);
			return getJsonPackFromResponse(httpResponse);
		} catch (Exception e) {
			LogUtils.logE(TAG, e);
			jp.setCode(400);
			jp.setErr_msg(Settings.GLOBAL_CONTEXT.getString(R.string.info_error_net));
			return jp;
		}
	}

	public static JsonPack doPost(String funcType, List<NameValuePair> pairList, InputStream is) {
		JsonPack jp = new JsonPack();
		try {
			HttpHelper.setUseGzip(true);
			HttpPost request = HttpHelper.createPost(getFullUrl(funcType), addBaseParams(pairList), is);
			request.setHeader("Connection", "close");
			request.setHeader(HTTP.CONTENT_TYPE,"multipart/form-data;boundary=---------------------------7d33a816d302b6");
			addBaseHeader(request);
			HttpResponse httpResponse = HttpHelper.doRequest(request);
			return getJsonPackFromResponse(httpResponse);
		} catch (Exception e) {
			LogUtils.logE(TAG, e);
			jp.setCode(400);
			jp.setErr_msg(Settings.GLOBAL_CONTEXT.getString(R.string.info_error_net));
			return jp;
		}
	}
	
	public static JsonPack doPostSeparately(String funcType, List<NameValuePair> pairList, List<NameValuePair> urlParamsList) {
		JsonPack jp = new JsonPack();
		HttpClient client = null;
		try {
			HttpHelper.setUseGzip(true);
			HttpPost request = HttpHelper.createJsonPostWithParams(getFullUrl(funcType), addBaseParams(pairList), addBaseParams(urlParamsList));
			request.setHeader("Connection", "close");
			request.setHeader("accept", "application/json");
			request.setHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8");
			addBaseHeader(request);
			client = HttpHelper.createHttpClient();
			HttpResponse httpResponse = HttpHelper.doRequest(request, client);
			return getJsonPackFromResponse(httpResponse);
		} catch (Exception e) {
			LogUtils.logE(TAG, e);
			jp.setCode(400);
			jp.setErr_msg(Settings.GLOBAL_CONTEXT.getString(R.string.info_error_net));
			return jp;
		} finally {
			HttpHelper.shutdownClient(client);
		}
	}

	public static JsonPack getJsonPackFromResponse(HttpResponse httpResponse) {
		JsonPack jp = new JsonPack();
		try {
			if (httpResponse == null) {
				jp.setCode(AppCommon.CONST_RESPONSE_CODE_ERROR);
				jp.setErr_msg(Settings.GLOBAL_CONTEXT.getString(R.string.info_error_net));
				return jp;
			}
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			jp.setCode(statusCode);
			if (statusCode == AppCommon.CONST_RESPONSE_CODE_SUCCESS) {
				String strEntity = HttpHelper.dealWithGzipResponse(httpResponse);
				jp = JsonPack.toBean(strEntity);
				return jp;
			} else if(statusCode == AppCommon.CONST_RESPONSE_CODE_SERVER_TOKEN_NOT_FIND){
				jp.setCode(AppCommon.CONST_RESPONSE_CODE_SERVER_TOKEN_NOT_FIND);
				jp.setErr_msg(Settings.GLOBAL_CONTEXT.getString(R.string.info_error_server_token));
				return jp;
			} else {
				jp.setCode(AppCommon.CONST_RESPONSE_CODE_ERROR);
				jp.setErr_msg(Settings.GLOBAL_CONTEXT.getString(R.string.info_error_net));
				return jp;
			}
		} catch (Exception e) {
			LogUtils.logE(TAG, e);
			jp.setCode(400);
			jp.setErr_msg(Settings.GLOBAL_CONTEXT.getString(R.string.info_error_net));
			return jp;
		}
	}

	public static void addBaseHeader(HttpRequestBase request) {
	}

	public static List<NameValuePair> addBaseParams(List<NameValuePair> pairList) {
		try {
			if (pairList == null) {
				return pairList;
			}
			pairList.add(new BasicNameValuePair("version", Settings.GLOBAL_VERSION_NAME));
			pairList.add(new BasicNameValuePair("deviceNumber", Settings.GLOBAL_DEVICE_ID));
			pairList.add(new BasicNameValuePair("deviceType", Build.MODEL));
			return pairList;
		} catch (Exception e) {
			logE(e);
			return pairList;
		}
	}

	public static String getFullUrl(String url, String... args) {
		try {
			String fullUrl = AppCommon.WEB_SERVICE_API + url;
			for (int i = 0; i < args.length; i++) {
				fullUrl = fullUrl.replace("{" + i + "}", args[i]);
			}
			return fullUrl;
		} catch (Exception e) {
			logE(e);
			return url;
		}
	}

	public static void logE(Throwable tr) {
		LogUtils.logE(TAG, tr.getLocalizedMessage(), tr);
	}
}
