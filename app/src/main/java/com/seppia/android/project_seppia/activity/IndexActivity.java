package com.seppia.android.project_seppia.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;

import com.seppia.android.project_seppia.R;
import com.seppia.android.project_seppia.dto.JsonPack;
import com.seppia.android.project_seppia.http.HttpApi;
import com.seppia.android.project_seppia.utils.LogUtils;

public class IndexActivity extends BaseActivity {

	static final String TAG = "IndexActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initComponent();
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				LogUtils.logD(TAG, "fetchLocationsByLocation--------------");
				JsonPack jp = HttpApi.getInstance().fetchLocationsByLocation("37.4561922", "-122.1548878", "5000", "sports");
				LogUtils.logD(TAG, jp.toString());
//				DialogUtils.showAlert(ctx, "fetchLocationsByLocation", jp.toString(), null);
			}
		});
//		t.start();
		t.run();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initComponent(){
		LayoutInflater mInflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View contextView = mInflater.inflate(R.layout.activity_index, null);

		this.getMainLayout().addView(contextView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}
}
