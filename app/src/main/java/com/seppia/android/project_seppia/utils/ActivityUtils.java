package com.seppia.android.project_seppia.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.seppia.android.project_seppia.R;
import com.seppia.android.project_seppia.Settings;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public final class ActivityUtils {
	private static final String TAG = ActivityUtils.class.getName();

	public static final int NO_ANIMATION = 0;

	// 以Activity的名称为键，存储Activity的代码
	private static final HashMap<String, Integer> mActivityCodeMap = new HashMap<String, Integer>();

	public static void jump(Context old, Class<?> cls) {
		jump(old, cls, new Bundle());
	}
	public static void jump(Context old, Class<?> cls, boolean setAnim) {
		if(setAnim){
			jump(old, cls, new Bundle());
		}else{
			jumpWithOutAnim(old, cls, new Bundle(), true);
		}
	}

	public static void jump(Context old, Class<?> cls, Bundle bundle) {
		jump(old, cls, bundle, false);
	}

	public static void jump(Context old, Class<?> cls, Bundle bundle,
			boolean clearTop) {
		jump(old, cls, bundle, clearTop, R.anim.frame_anim_from_right,
				R.anim.frame_anim_to_left);
	}

	public static void jumpWithOutAnim(Context old, Class<?> cls, Bundle bundle,
			boolean clearTop) {
		try {
			Intent intent = new Intent();
			intent.setClass(old, cls);
			if (bundle != null) {
				intent.putExtras(bundle);
			}
			if (old instanceof Activity) {
				Activity activity = (Activity) old;
				intent.putExtra(Settings.BUNDLE_LAST_ACTIVITY,
						getActivityCode(activity.getClass()));
			}
			if (clearTop) {
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			}
			old.startActivity(intent);
			
			if(old instanceof Activity){
				Activity activity = (Activity) old;
				if (DeviceUtils.getSdkVersion() > SdkVersion.DONUT) {
					overridePendingTransition(activity, 0, 0);
				}
			}
			
		} catch (Exception e) {
			log(e);
		}
	}
	
	public static void jump(Context old, Class<?> cls, Bundle bundle,
			boolean clearTop, int enterAnim, int exitAnim) {
		try {
			Intent intent = new Intent();
			intent.setClass(old, cls);
			if (bundle != null) {
				intent.putExtras(bundle);
			}
			if (old instanceof Activity) {
				Activity activity = (Activity) old;
				intent.putExtra(Settings.BUNDLE_LAST_ACTIVITY,
						getActivityCode(activity.getClass()));

//				if (DeviceUtils.getSdkVersion() > SdkVersion.DONUT) {
//					overridePendingTransition(activity, enterAnim, exitAnim);
//				}
			}
			if (clearTop) {
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			}
			old.startActivity(intent);
			
			if(old instanceof Activity){
				Activity activity = (Activity) old;
				if (DeviceUtils.getSdkVersion() > SdkVersion.DONUT) {
					overridePendingTransition(activity, enterAnim, exitAnim);
				}
			}
		} catch (Exception e) {
			log(e);
		}
	}

	public static void jump(Context old, Class<?> cls, Bundle bundle, int flag,
			int enterAnim, int exitAnim) {
		try {
			Intent intent = new Intent();
			intent.setClass(old, cls);
			if (bundle != null) {
				intent.putExtras(bundle);
			}
			if (old instanceof Activity) {
				Activity activity = (Activity) old;
				intent.putExtra(Settings.BUNDLE_LAST_ACTIVITY,
						getActivityCode(activity.getClass()));

//				if (DeviceUtils.getSdkVersion() > SdkVersion.DONUT) {
//					overridePendingTransition(activity, enterAnim, exitAnim);
//				}
			}
			intent.setFlags(flag);
			old.startActivity(intent);
			
			if(old instanceof Activity){
				Activity activity = (Activity) old;
				if (DeviceUtils.getSdkVersion() > SdkVersion.DONUT) {
					overridePendingTransition(activity, enterAnim, exitAnim);
				}
			}
		} catch (Exception e) {
			log(e);
		}
	}

	public static void jumpForResult(Context old, Class<?> cls) {
		jumpForResult(old, cls, getActivityCode(old.getClass()), new Bundle());
	}

	public static void jumpForResult(Context old, Class<?> cls, int requestCode) {
		jumpForResult(old, cls, requestCode, new Bundle());
	}

	public static void jumpForResult(Context old, Class<?> cls, Bundle bundle) {
		jumpForResult(old, cls, getActivityCode(old.getClass()), bundle);
	}

	public static void jumpForResult(Context old, Class<?> cls,
			int requestCode, Bundle bundle) {
		jumpForResult(old, cls, requestCode, bundle, false);
	}

	public static void jumpForResult(Context old, Class<?> cls,
			int requestCode, Bundle bundle, boolean clearTop) {
		jumpForResult(old, cls, requestCode, bundle, clearTop,
				R.anim.frame_anim_from_right, R.anim.frame_anim_to_left);
	}

	public static void jumpForResult(Context old, Class<?> cls,
			int requestCode, Bundle bundle, boolean clearTop, int enterAnim,
			int exitAnim) {
		try {
			if (old instanceof Activity) {
				Intent intent = new Intent();
				intent.setClass(old, cls);
				if (bundle != null) {
					intent.putExtras(bundle);
				}
				Activity activity = (Activity) old;
				intent.putExtra(Settings.BUNDLE_LAST_ACTIVITY,
						getActivityCode(activity.getClass()));
				if (clearTop) {
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				}
				activity.startActivityForResult(intent, requestCode);
				if (DeviceUtils.getSdkVersion() > SdkVersion.DONUT) {
					overridePendingTransition(activity, enterAnim, exitAnim);
				}
			}
		} catch (Exception e) {
			log(e);
		}
	}

	public static void exitApp(Activity activity) {
		exitApp(activity, true);
	}

	public static void exitApp(Activity activity, boolean isExit) {
		try {
			// TODO 图片加载这一块的封装还未完成，完成之后需要放开
			// ImageLoader.getInstance().stop();
			activity.finish();
			if (isExit) {
				System.exit(0);
			}
		} catch (Exception e) {
			log(e);
		}
	}

	public static void launchApp(String packageName, Bundle bundle, Activity activity) throws NullPointerException{
		if(activity == null){
			throw new NullPointerException("activity can't be null!");
		}
		if(TextUtils.isEmpty(packageName)){
			throw new NullPointerException("packageName can't be empty!");
		}
		
		try {
			Intent intent = activity.getPackageManager().getLaunchIntentForPackage(packageName);
			if(bundle != null) intent.putExtras(bundle);
			activity.startActivity(intent);
		} catch (Exception e) {
			LogUtils.logE(e);
		}
	}

	public static void launchApp(String packageName, Activity activity) throws NullPointerException{
		if(activity == null){
			throw new NullPointerException("activity can't be null!");
		}
		if(TextUtils.isEmpty(packageName)){
			throw new NullPointerException("packageName can't be empty!");
		}
		
		try {
			Intent intent = activity.getPackageManager().getLaunchIntentForPackage(packageName);
			activity.startActivity(intent);
		} catch (Exception e) {
			LogUtils.logE(e);
		}
	}

	public static void initActivityCode(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (info.activities != null && info.activities.length > 0) {
				for (ActivityInfo actInfo : info.activities) {
					mActivityCodeMap.put(actInfo.name, actInfo.name.hashCode());
				}
			}
		} catch (Exception e) {
			log(e);
		}
	}

	public static int getActivityCode(Class<?> cls) {
		try {
			if (!mActivityCodeMap.containsKey(cls.getName())) {
				return -1;
			}
			return mActivityCodeMap.get(cls.getName());
		} catch (Exception e) {
			log(e);
			return -1;
		}
	}

	public static String getVersionName(Context ctx) {
		PackageManager manager = ctx.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage(), e);
			return "";
		}
	}

	public static boolean isOnForeground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
		if (tasksInfo.size() > 0) {
			if (context.getPackageName().equals(
					tasksInfo.get(0).topActivity.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	public static int getVersionCode(Context ctx) {
		PackageManager manager = ctx.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
			int code = info.versionCode; // 版本号
			return code;
		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage(), e);
			return 0;
		}
	}

	public static void overridePendingTransition(Activity activity,
			int enterAnim, int exitAnim) {
		try {
			Method method = Activity.class.getMethod(
					"overridePendingTransition", int.class, int.class);
			method.invoke(activity, enterAnim, exitAnim);
		} catch (Exception e) {
			log(e);
		}
	}

	public static void jumbToWeb(Activity activity, String url) {
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			activity.startActivity(intent);
			ActivityUtils.overridePendingTransition(activity,
					R.anim.slide_in_right, R.anim.slide_out_right);
		} catch (Exception e) {
			e.printStackTrace();
			DialogUtils.showToastShort(activity, "抱歉，无法打开链接");
		}
	}

	public static void jumbToWeb(String url) {
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Settings.GLOBAL_CONTEXT.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			DialogUtils.showToastShort(Settings.GLOBAL_CONTEXT, "抱歉，无法打开链接");
		}
	}

	private static void log(Throwable tr) {
		LogUtils.logE(TAG, tr.getLocalizedMessage(), tr);
	}
}
