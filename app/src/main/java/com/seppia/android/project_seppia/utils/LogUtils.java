package com.seppia.android.project_seppia.utils;

import android.util.Log;

import com.seppia.android.project_seppia.Settings;

/**
 * 日志工具类
 */
public final class LogUtils {

	// ------------------------------------------------------ Constants
	private static final String DEFAULT_TAG = "";
	private static final String DEFAULT_MSG = "";

	/**
	 * Priority constant for log
	 */
	public static final int VERBOSE = Log.VERBOSE;
	public static final int DEBUG = Log.DEBUG;
	public static final int INFO = Log.INFO;
	public static final int WARN = Log.WARN;
	public static final int ERROR = Log.ERROR;
	public static final int ASSERT = Log.ASSERT;
	private static final int DISABLE = 1024;

	private static final String LOG_FILE_NAME = "ct.log";
	// ------------------------------------------------------ Fields
	private static boolean isLoggable;
	private static boolean isDebuggable = Settings.isDebuggable;

	// ------------------------------------------------------ Public Methods

	// verbose
	public static void logV(String msg) {
		if (isDebuggable) {
			Log.v(DEFAULT_TAG, msg);
		}
	}

	public static void logV(String tag, String msg) {
		if (isDebuggable) {
			Log.v(tag, msg);
		}
	}

	public static void logV(Throwable tr) {
		if (isDebuggable) {
			Log.v(DEFAULT_TAG, DEFAULT_MSG, tr);
		}
	}

	public static void logV(String tag, String msg, Throwable tr) {
		if (isDebuggable) {
			Log.v(tag, msg, tr);
		}
	}

	// debug
	public static void logD(String msg) {
		if (isDebuggable) {
			Log.d(DEFAULT_TAG, msg);
		}
	}

	public static void logD(String tag, String msg) {
		if (isDebuggable) {
			Log.d(tag, msg);
		}
	}

	public static void logD(Throwable tr) {
		if (isDebuggable) {
			Log.d(DEFAULT_TAG, DEFAULT_MSG, tr);
		}
	}

	public static void logD(String tag, String msg, Throwable tr) {
		if (isDebuggable) {
			Log.d(tag, msg, tr);
		}
	}

	// info
	public static void logI(String msg) {
		if (isDebuggable) {
			Log.i(DEFAULT_TAG, msg);
		}
	}

	public static void logI(String tag, String msg) {
		if (isDebuggable) {
			Log.i(tag, msg);
		}
	}

	public static void logI(Throwable tr) {
		if (isDebuggable) {
			Log.i(DEFAULT_TAG, DEFAULT_MSG, tr);
		}
	}

	public static void logI(String tag, String msg, Throwable tr) {
		if (isDebuggable) {
			Log.i(tag, msg, tr);
		}
	}

	// warning
	public static void logW(String msg) {
		if (isDebuggable) {
			Log.w(DEFAULT_TAG, msg);
		}
	}

	public static void logW(String tag, String msg) {
		if (isDebuggable) {
			Log.w(tag, msg);
		}
	}

	public static void logW(Throwable tr) {
		if (isDebuggable) {
			Log.w(DEFAULT_TAG, DEFAULT_MSG, tr);
		}
	}

	public static void logW(String tag, String msg, Throwable tr) {
		if (isDebuggable) {
			Log.w(tag, msg, tr);
		}
	}

	// error
	public static void logE(String msg) {
		if (isDebuggable) {
			Log.e(DEFAULT_TAG, msg);
		}
	}

	public static void logE(String tag, String msg) {
		if (isDebuggable) {
			Log.e(tag, msg);
		}
	}

	public static void logE(Throwable tr) {
		if (isDebuggable) {
			Log.e(DEFAULT_TAG, DEFAULT_MSG, tr);
		}
	}

	public static void logE(String tag, Throwable tr) {
		if (isDebuggable) {
			Log.e(tag, DEFAULT_MSG, tr);
		}
	}

	public static void logE(String tag, String msg, Throwable tr) {
		if (isDebuggable) {
			Log.e(tag, msg, tr);
		}
	}
	
	// -------------------------------------------------------- Private Methods
	private boolean isLoggable() {
		return true;
	}

}
