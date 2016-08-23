package com.seppia.android.project_seppia.utils;

public class SdkVersion {
	/**
	 * Magic version number for a current development build, which has not yet
	 * turned into an official release.
	 */
	public static final int CUR_DEVELOPMENT = 10000;

	/**
	 * October 2008: The original, first, version of Android. Yay!
	 */
	public static final int BASE = 1;

	/**
	 * February 2009: First Android update, officially called 1.1.
	 */
	public static final int BASE_1_1 = 2;

	/**
	 * May 2009: Android 1.5.
	 */
	public static final int CUPCAKE = 3;

	/**
	 * September 2009: Android 1.6.
	 * <p>
	 * Applications targeting this or a later release will get these new changes
	 * in behavior:
	 * </p>
	 * <ul>
	 * <li>They must explicitly request the
	 * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE} permission to
	 * be able to modify the contents of the SD card. (Apps targeting earlier
	 * versions will always request the permission.)
	 * <li>They must explicitly request the
	 * {@link android.Manifest.permission#READ_PHONE_STATE} permission to be
	 * able to be able to retrieve phone state info. (Apps targeting earlier
	 * versions will always request the permission.)
	 * <li>They are assumed to support different screen densities and sizes.
	 * (Apps targeting earlier versions are assumed to only support medium
	 * density normal size screens unless otherwise indicated). They can still
	 * explicitly specify screen support either way with the supports-screens
	 * manifest tag.
	 * </ul>
	 */
	public static final int DONUT = 4;

	/**
	 * November 2009: Android 2.0
	 * <p>
	 * Applications targeting this or a later release will get these new changes
	 * in behavior:
	 * </p>
	 * <ul>
	 * <li>The {@link android.app.Service#onStartCommand Service.onStartCommand}
	 * function will return the new {@link android.app.Service#START_STICKY}
	 * behavior instead of the old compatibility
	 * {@link android.app.Service#START_STICKY_COMPATIBILITY}.
	 * <li>The {@link android.app.Activity} class will now execute back key
	 * presses on the key up instead of key down, to be able to detect canceled
	 * presses from virtual keys.
	 * <li>The {@link android.widget.TabWidget} class will use a new color
	 * scheme for tabs. In the new scheme, the foreground tab has a medium gray
	 * background the background tabs have a dark gray background.
	 * </ul>
	 */
	public static final int ECLAIR = 5;

	/**
	 * December 2009: Android 2.0.1
	 */
	public static final int ECLAIR_0_1 = 6;

	/**
	 * January 2010: Android 2.1
	 */
	public static final int ECLAIR_MR1 = 7;

	/**
	 * June 2010: Android 2.2
	 */
	public static final int FROYO = 8;

	/**
	 * November 2010: Android 2.3
	 */
	public static final int GINGERBREAD = 9;

	/**
	 * February 2011: Android 2.3.3.
	 */
	public static final int GINGERBREAD_MR1 = 10;

	/**
	 * February 2011: Android 3.0.
	 * <p>
	 * Applications targeting this or a later release will get these new changes
	 * in behavior:
	 * </p>
	 * <ul>
	 * <li>The default theme for applications is now dark holographic:
	 * {@link android.R.style#Theme_Holo}.
	 * <li>The activity lifecycle has changed slightly as per
	 * {@link android.app.Activity}.
	 * <li>When an application requires a permission to access one of its
	 * components (activity, receiver, service, provider), this permission is no
	 * longer enforced when the application wants to access its own component.
	 * This means it can require a permission on a component that it does not
	 * itself hold and still access that component.
	 * </ul>
	 */
	public static final int HONEYCOMB = 11;

	/**
	 * May 2011: Android 3.1.
	 */
	public static final int HONEYCOMB_MR1 = 12;

	/**
	 * June 2011: Android 3.2.
	 * <p>
	 * Update to Honeycomb MR1 to support 7 inch tablets, improve screen
	 * compatibility mode, etc.
	 * </p>
	 * <p>
	 * As of this version, applications that don't say whether they support
	 * XLARGE screens will be assumed to do so only if they target
	 * {@link #HONEYCOMB} or later; it had been {@link #GINGERBREAD} or later.
	 * Applications that don't support a screen size at least as large as the
	 * current screen will provide the user with a UI to switch them in to
	 * screen size compatibility mode.
	 * </p>
	 * <p>
	 * This version introduces new screen size resource qualifiers based on the
	 * screen size in dp: see
	 * {@link android.content.res.Configuration#screenWidthDp},
	 * {@link android.content.res.Configuration#screenHeightDp}, and
	 * {@link android.content.res.Configuration#smallestScreenWidthDp}.
	 * Supplying these in &lt;supports-screens&gt; as per
	 * {@link android.content.pm.ApplicationInfo#requiresSmallestWidthDp},
	 * {@link android.content.pm.ApplicationInfo#compatibleWidthLimitDp}, and
	 * {@link android.content.pm.ApplicationInfo#largestWidthLimitDp} is
	 * preferred over the older screen size buckets and for older devices the
	 * appropriate buckets will be inferred from them.
	 * </p>
	 * <p>
	 * New {@link android.content.pm.PackageManager#FEATURE_SCREEN_PORTRAIT} and
	 * {@link android.content.pm.PackageManager#FEATURE_SCREEN_LANDSCAPE}
	 * features are introduced in this release. Applications that target
	 * previous platform versions are assumed to require both portrait and
	 * landscape support in the device; when targeting Honeycomb MR1 or greater
	 * the application is responsible for specifying any specific orientation it
	 * requires.
	 * </p>
	 */
	public static final int HONEYCOMB_MR2 = 13;

	/**
	 * October 2011: Android 4.0.
	 * <p>
	 * Applications targeting this or a later release will get these new changes
	 * in behavior:
	 * </p>
	 * <ul>
	 * <li>For devices without a dedicated menu key, the software compatibility
	 * menu key will not be shown even on phones. By targeting Ice Cream
	 * Sandwich or later, your UI must always have its own menu UI affordance if
	 * needed, on both tablets and phones. The ActionBar will take care of this
	 * for you.
	 * <li>2d drawing hardware acceleration is now turned on by default. You can
	 * use {@link android.R.attr#hardwareAccelerated
	 * android:hardwareAccelerated} to turn it off if needed, although this is
	 * strongly discouraged since it will result in poor performance on larger
	 * screen devices.
	 * <li>The default theme for applications is now the "device default" theme:
	 * {@link android.R.style#Theme_DeviceDefault}. This may be the holo dark
	 * theme or a different dark theme defined by the specific device. The
	 * {@link android.R.style#Theme_Holo} family must not be modified for a
	 * device to be considered compatible. Applications that explicitly request
	 * a theme from the Holo family will be guaranteed that these themes will
	 * not change character within the same platform version. Applications that
	 * wish to blend in with the device should use a theme from the
	 * {@link android.R.style#Theme_DeviceDefault} family.
	 * <li>Managed cursors can now throw an exception if you directly close the
	 * cursor yourself without stopping the management of it; previously
	 * failures would be silently ignored.
	 * <li>The fadingEdge attribute on views will be ignored (fading edges is no
	 * longer a standard part of the UI). A new requiresFadingEdge attribute
	 * allows applications to still force fading edges on for special cases.
	 * </ul>
	 */
	public static final int ICE_CREAM_SANDWICH = 14;

	/**
	 * Android 4.0.3.
	 */
	public static final int ICE_CREAM_SANDWICH_MR1 = 15;
}
