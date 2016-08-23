package com.seppia.android.project_seppia.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import com.seppia.android.project_seppia.R;
import com.seppia.android.project_seppia.utils.ActivityUtils;

/**
 * the splash page
 * @author mr.z
 */
public class SplashActivity extends Activity {

	private final int mSleepTime = 2000; // Splash stay time

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(mSleepTime);
					splashHandler.sendEmptyMessage(0);
					
				} catch (Exception e) {
					splashHandler.sendEmptyMessage(0);
				}
			}
		}).start();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ActivityUtils.exitApp(this);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private Handler splashHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			ActivityUtils.jump(SplashActivity.this, IndexActivity.class, null);
			finish();
			super.handleMessage(msg);
		}
	};
}
