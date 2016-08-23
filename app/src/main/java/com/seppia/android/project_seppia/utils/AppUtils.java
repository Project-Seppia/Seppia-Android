package com.seppia.android.project_seppia.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.webkit.WebSettings;

import com.seppia.android.project_seppia.Settings;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE;
import static android.content.pm.PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
import static android.content.pm.PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
import static android.content.pm.PackageManager.DONT_KILL_APP;
import static android.content.pm.PackageManager.GET_META_DATA;
import static android.content.pm.PackageManager.GET_SIGNATURES;
import static android.content.pm.PackageManager.SIGNATURE_MATCH;


/**
 * 应用工具类
 */
public final class AppUtils {

	private static final String TAG = "AppUtils";

	// 设备号类型
	private static String deviceNOType;

	private enum DeviceNoType {
		ANDROID_ID, IMEI, IMSI, RANDOM_UUID
	}

	public static boolean isDebuggable(Context ctx) {
		ApplicationInfo appInfo = ctx.getApplicationInfo();
		boolean debug = (appInfo.flags & FLAG_DEBUGGABLE) != 0;
		return debug;
	}

	public static String getVersionName(Context ctx) {
		String verName = "?";
		try {
			verName = ctx.getPackageManager().getPackageInfo(
					ctx.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			LogUtils.logW(e);
		}
		return verName;
	}

	public static int getVersionCode(Context ctx) {
		int verCode = 0;
		try {
			verCode = ctx.getPackageManager().getPackageInfo(
					ctx.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			LogUtils.logW(e);
		}
		return verCode;
	}

	//

	public static boolean isInstalled(Context ctx, String pkgName) {
		try {
			ctx.getPackageManager().getApplicationInfo(pkgName, GET_META_DATA);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

	public static void setComponentEnabled(Context ctx,
			Class<? extends Context> component, boolean enabled) {
		PackageManager pm = ctx.getPackageManager();
		ComponentName componentName = new ComponentName(ctx, component);
		int state = enabled ? COMPONENT_ENABLED_STATE_ENABLED
				: COMPONENT_ENABLED_STATE_DISABLED;
		pm.setComponentEnabledSetting(componentName, state, DONT_KILL_APP);
	}

	/**
	 * 获取设备号类型 获取设备号成功之后，才会产生设备号类型，也就是说，如果没创建设备号，设备号类型不存在，即为空
	 * 
	 * @return
	 */
	public static String getDeviceNOType() {
		if (deviceNOType == null) {
			return "";
		}
		return deviceNOType;
	}

	/**
	 * 获取设备号
	 * 
	 * @param ctx
	 * @return
	 */
	public static String createDeviceNo(Context ctx) {
		String deviceNO = "";
		try {
			deviceNO = Secure.getString(ctx.getContentResolver(),
					Secure.ANDROID_ID);

			// 很多山寨机器都使用此Android_id:"9774d56d682e549c",所以排除此种情况
			if (!TextUtils.isEmpty(deviceNO)
					&& !TextUtils.equals("9774d56d682e549c", deviceNO)) {
				deviceNOType = DeviceNoType.ANDROID_ID.toString();
				return deviceNO;
			}

			// IMEI
			deviceNO = DeviceUtils.getDeviceId(ctx);
			if (!TextUtils.isEmpty(deviceNO)) {
				deviceNOType = DeviceNoType.IMEI.toString();
				return deviceNO;
			}

			// IMSI
			deviceNO = DeviceUtils.getIMSI(ctx);
			if (!TextUtils.isEmpty(deviceNO)) {
				deviceNOType = DeviceNoType.IMSI.toString();
				return deviceNO;
			}

			// RANDOM_UUID
			deviceNO = UUID.randomUUID().toString();
			if (!TextUtils.isEmpty(deviceNO)) {
				deviceNOType = DeviceNoType.RANDOM_UUID.toString();
				return deviceNO;
			}
		} catch (Exception e) {
			// RANDOM
			deviceNO = UUID.randomUUID().toString();
			if (!TextUtils.isEmpty(deviceNO)) {
				deviceNOType = DeviceNoType.RANDOM_UUID.toString();
				return deviceNO;
			}
		}
		return deviceNO;
	}

	// 获取设备号
	public static String getDeviceNO(Context context) {
		String deviceNo = "";
		try {
			if (DeviceUtils.isSDCardMounted()) {
				// SD卡可用时操作SD卡, 检查SD卡中是否有deviceNo
				deviceNo = IOUtils.readStringFromSD(Settings.PREF_DEVICE_NO);
				deviceNOType = IOUtils
						.readStringFromSD(Settings.PREF_DEVICE_NO_TYPE);
				if (TextUtils.isEmpty(deviceNo)) {
					// SD卡中没有时检查缓存中是否有deviceNo，缓存中存在deviceNo的情况下使用缓存中的deviceNo
					deviceNo = SharedPrefUtils.getString(context,
							Settings.PREF_DEVICE_NO, "");
					deviceNOType = SharedPrefUtils.getString(context,
							Settings.PREF_DEVICE_NO_TYPE, "");
					if (TextUtils.isEmpty(deviceNo)) {
						// 缓存中也不存在deviceNo的情况下创建新的deviceNo
						deviceNo = createDeviceNo(context);
						// 存储设备号
						IOUtils.writeToSD(Settings.PREF_DEVICE_NO, deviceNo);
						SharedPrefUtils.putString(context,
								Settings.PREF_DEVICE_NO, "");

						// 存储设备类型
						IOUtils.writeToSD(Settings.PREF_DEVICE_NO_TYPE,
								getDeviceNOType());
						SharedPrefUtils
								.putString(context,
										Settings.PREF_DEVICE_NO_TYPE,
										getDeviceNOType());

					}
				}
			} else {
				// SD卡不可用时操作缓存
				deviceNo = SharedPrefUtils.getString(context,
						Settings.PREF_DEVICE_NO, "");
				deviceNOType = SharedPrefUtils.getString(context,
						Settings.PREF_DEVICE_NO_TYPE, "");
				if (TextUtils.isEmpty(deviceNo)) {
					// 缓存中不存在UUID的情况下创建新的UUID
					deviceNo = createDeviceNo(context);
					SharedPrefUtils.putString(context, Settings.PREF_DEVICE_NO,
							deviceNo);
					IOUtils.writeToSD(Settings.PREF_DEVICE_NO, deviceNo);

					// 存储设备类型
					IOUtils.writeToSD(Settings.PREF_DEVICE_NO_TYPE,
							getDeviceNOType());
					SharedPrefUtils.putString(context,
							Settings.PREF_DEVICE_NO_TYPE, getDeviceNOType());
				}
			}
		} catch (Exception e) {
			try {
				// 有异常时使用缓存
				deviceNo = SharedPrefUtils.getString(context,
						Settings.PREF_DEVICE_NO, "");
				deviceNOType = SharedPrefUtils.getString(context,
						Settings.PREF_DEVICE_NO_TYPE, "");

				if (TextUtils.isEmpty(deviceNo)) {
					deviceNo = createDeviceNo(context);
					SharedPrefUtils.putString(context, Settings.PREF_DEVICE_NO,
							deviceNo);
					IOUtils.writeToSD(Settings.PREF_DEVICE_NO, deviceNo);

					// 存储设备类型
					IOUtils.writeToSD(Settings.PREF_DEVICE_NO_TYPE,
							getDeviceNOType());
					SharedPrefUtils.putString(context,
							Settings.PREF_DEVICE_NO_TYPE, getDeviceNOType());
				}
			} catch (Exception e2) {
				// 仍然出现异常则不再处理
				log(e2);
			}
		}
		return deviceNo;
	}

	public static String getSignature(Context ctx, String pkgName)
			throws NameNotFoundException {
		PackageInfo pi = ctx.getPackageManager().getPackageInfo(pkgName,
				GET_SIGNATURES);
		String signature = pi.signatures[0].toCharsString();
		return signature;
	}

	public static boolean doSignaturesMatch(Context ctx, String pkg1,
			String pkg2) {
		boolean match = ctx.getPackageManager().checkSignatures(pkg1, pkg2) == SIGNATURE_MATCH;
		return match;
	}

	@SuppressWarnings("deprecation")
	public static boolean canInstallNonMarketApps(Context ctx) {
		return Secure.getInt(ctx.getContentResolver(),
				Secure.INSTALL_NON_MARKET_APPS, 0) != 0;
	}

	public static boolean isInstalledFromMarket(Context ctx, String pkgName)
			throws NameNotFoundException {
		String installerPkg = ctx.getPackageManager().getInstallerPackageName(
				pkgName);
		boolean installedFromMarket = "com.google.android.feedback"
				.equals(installerPkg);
		return installedFromMarket;
	}

	@SuppressWarnings("resource")
	public static long getClassesDexCrc(Context ctx) {
		ZipFile zf;
		try {
			zf = new ZipFile(ctx.getPackageCodePath());
		} catch (IOException e) {
			LogUtils.logE(e);
			return -1;
		}
		ZipEntry ze = zf.getEntry("classes.dex");
		long crc = ze.getCrc();
		return crc;
	}

	/**
	 * 获得严格的设备号，当获取不到DeviceId时采用UUID策略
	 * 
	 * @param context
	 * @return
	 */
	public static String getStrictDeviceId(Context context) {
		String id = "";
		try {
			id = DeviceUtils.getDeviceId(context);
			if (TextUtils.isEmpty(id)) {
				id = getUUID(context);
			}
			return id;
		} catch (Exception e) {
			log(e);
			return id;
		}
	}

	/**
	 * 获得UUID
	 * 
	 * @param context
	 * @return
	 */
	public static String getUUID(Context context) {
		String uuid = "";
		try {
			if (DeviceUtils.isSDCardMounted()) {
				// SD卡可用时操作SD卡, 检查SD卡中是否有UUID
				uuid = IOUtils.readStringFromSD(Settings.PREF_DEVICE_NO);
				if (TextUtils.isEmpty(uuid)) {
					// SD卡中没有时检查缓存中是否有UUID，缓存中存在UUID的情况下使用缓存中的UUID
					uuid = SharedPrefUtils.getString(context,
							Settings.PREF_DEVICE_NO, "");
					if (TextUtils.isEmpty(uuid)) {
						// 缓存中也不存在UUID的情况下创建新的UUID
						uuid = UUID.randomUUID().toString();
						IOUtils.writeToSD(Settings.PREF_DEVICE_NO, uuid);
					}
				}
			} else {
				// SD卡不可用时操作缓存
				uuid = SharedPrefUtils.getString(context,
						Settings.PREF_DEVICE_NO, "");
				if (TextUtils.isEmpty(uuid)) {
					// 缓存中不存在UUID的情况下创建新的UUID
					uuid = UUID.randomUUID().toString();
					SharedPrefUtils.putString(context, Settings.PREF_DEVICE_NO,
							uuid);
				}
			}
		} catch (Exception e) {
			try {
				// 有异常时使用缓存
				uuid = SharedPrefUtils.getString(context,
						Settings.PREF_DEVICE_NO, "");
				if (TextUtils.isEmpty(uuid)) {
					uuid = UUID.randomUUID().toString();
					SharedPrefUtils.putString(context, Settings.PREF_DEVICE_NO,
							uuid);
				}
			} catch (Exception e2) {
				// 仍然出现异常则不再处理
				log(e2);
			}
		}
		return uuid;
	}

	/**
	 * generate our own UserAgent
	 * 
	 * @param wvSettings
	 * @return
	 */
	public static String getUserAgent(WebSettings wvSettings) {
		StringBuilder ua = new StringBuilder();
		ua.append(wvSettings.getUserAgentString() + "\\")
				.append(DeviceUtils.getBrand() + "|")
				.append(DeviceUtils.getDevice() + "|")
				.append(DeviceUtils.getManufacturer() + "|")
				.append(DeviceUtils.getProduct() + "|")
				.append(DeviceUtils.getModel() + ",").append("Android,")
				.append(DeviceUtils.getReleaseVersion() + ",")
				.append(DeviceUtils.getVersionName(Settings.GLOBAL_CONTEXT));
		return ua.toString();
	}

	
	/**
     * 获取网落图片资源
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }
         
        return bitmap;
         
    }
    
	/**
	 * 记录错误
	 * 
	 * @param tr
	 */
	private static void log(Throwable tr) {
		LogUtils.logE(TAG, tr.getLocalizedMessage(), tr);
	}
}
