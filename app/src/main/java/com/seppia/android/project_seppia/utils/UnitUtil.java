package com.seppia.android.project_seppia.utils;

import android.content.Context;

import com.seppia.android.project_seppia.Settings;


/**
 * dip 与像素的相互转换
 */
public class UnitUtil {

	
	
	//下面两个带context的方法是为了向下兼容，不推荐使用	
	/**
	 * @deprecated
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {		
		return dip2px(dipValue);
	}
	/**
	 * @deprecated
	 * @param context
	 * @return
	 */	
	public static float px2dip(Context context, int pxValue) {
		return px2dip(pxValue);		
	}
	
	/**将dip转换为像素
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(float dipValue) {
		float scale = Settings.GLOBAL_CONTEXT.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将像素转换为dip
	 * @param pxvalue
	 * @return
	 */
	public static float px2dip(int pxvalue) {
		float scale = Settings.GLOBAL_CONTEXT.getResources().getDisplayMetrics().density;
		return pxvalue/scale;		
	}
	/**获取屏宽度像素数
	 * @return
	 */
	public static int getScreenWidthPixels() {
		return Settings.GLOBAL_CONTEXT.getResources().getDisplayMetrics().widthPixels;		
	}
	/**获取屏幕高度像素数
	 * @return
	 */
	public static int getScreenHeightPixels() {
		return Settings.GLOBAL_CONTEXT.getResources().getDisplayMetrics().heightPixels;		
	}

}
