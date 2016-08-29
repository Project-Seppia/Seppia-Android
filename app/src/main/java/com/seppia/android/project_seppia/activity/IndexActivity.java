package com.seppia.android.project_seppia.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;

import com.seppia.android.project_seppia.R;

public class IndexActivity extends BaseActivity {

	static final String TAG = "IndexActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initComponent();
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
		this.getTvTitle().setText("HOME");
		this.getBtnGoBack().setVisibility(View.INVISIBLE);
	}
}
