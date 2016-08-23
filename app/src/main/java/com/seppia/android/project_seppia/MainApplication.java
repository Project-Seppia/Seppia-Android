package com.seppia.android.project_seppia;

import android.app.Application;

/**
 */
public class MainApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		/** 初始化一些全局常量，eg：全局Context、debug状态、设备号 */
		Settings.GLOBAL_CONTEXT = this;
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}
}
