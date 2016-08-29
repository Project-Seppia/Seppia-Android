package com.seppia.android.project_seppia.http;

import com.seppia.android.project_seppia.Settings;
import com.seppia.android.project_seppia.dto.JsonPack;
import com.seppia.android.project_seppia.utils.IOUtils;
import com.seppia.android.project_seppia.utils.LogUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 调用的Http接口
 */
public class HttpApi {

	private static final String TAG = HttpApi.class.getName();

	private volatile static HttpApi mInstance;

	// get sms code
	private static final String URL_API_SMS_NEWCODE = "/api/sms/newCode";
	// get email code
	private static final String URL_API_EMAIL_NEWCODE = "/api/email/newCode";
	// fetch locations by location
//	private static final String URL_API_SEARCH_BY_LOCATION = "/fetch/fetchLocationsByLocation";
	private static final String URL_API_SEARCH_BY_LOCATION = "/sample.json";

	static {
//		System.loadLibrary("zip");
//        System.loadLibrary("chk");
		mInstance = new HttpApi();
	}
	
//	private native static String get(String ass);

	private HttpApi() {
		
	}

	public static HttpApi getInstance() { 
		return mInstance;
	}
	
	public JsonPack getSmsNewCode(
							String phoneNumber, // 手机号码
							String identifyCodeType
	) {
		try {
			List<NameValuePair> pairList = new ArrayList<NameValuePair>();
			pairList.add(new BasicNameValuePair("phoneNumber", phoneNumber));
			pairList.add(new BasicNameValuePair("identifyCodeType", identifyCodeType));
			return HttpUtils.doPost(URL_API_SMS_NEWCODE, pairList);
		} catch (Exception e) {
			logE(e);
			return new JsonPack();
		}
	}

	public JsonPack fetchLocationsByLocation(String lat, String lng, String radius, String keyword){
		String fetchLocationsByLocationStr = IOUtils.readStringFromAssets(Settings.GLOBAL_CONTEXT, "sample.min.json");
		LogUtils.logD(TAG, "fetchLocationsByLocation: " + fetchLocationsByLocationStr);
		return JsonPack.toBean(fetchLocationsByLocationStr);
//		try {
//			List<NameValuePair> pairList = new ArrayList<>();
//			pairList.add(new BasicNameValuePair("lat", lat));
//			pairList.add(new BasicNameValuePair("lng", lng));
//			pairList.add(new BasicNameValuePair("radius", radius));
//			pairList.add(new BasicNameValuePair("keyword", keyword));
//			return HttpUtils.doGet(URL_API_SEARCH_BY_LOCATION, pairList);
////			return HttpUtils.doGet(URL_API_SEARCH_BY_LOCATION, null);
//		} catch (Exception e) {
//			logE(e);
//			return new JsonPack();
//		}
	}

	private static void logE(Throwable tr) {
		LogUtils.logE(TAG, tr.getLocalizedMessage(), tr);
	}
}
