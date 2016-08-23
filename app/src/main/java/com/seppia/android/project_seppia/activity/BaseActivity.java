package com.seppia.android.project_seppia.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seppia.android.project_seppia.AppCommon;
import com.seppia.android.project_seppia.R;
import com.seppia.android.project_seppia.utils.ActivityUtils;
import com.seppia.android.project_seppia.utils.LogUtils;

/**
 * Frame Page
 * 
 * @author mr.z
 * 
 */
public class BaseActivity extends Activity {

	protected Context ctx;
	private final static String TAG = "BaseActivity";

	private Button btnGoBack;
	private TextView tvTitle;
	private Button btnOption;
	private LinearLayout mainLayout;
	private RelativeLayout titleLayout;
	private LinearLayout bottomLayout;
	private Button btnTitle;
	private ProgressDialog progressDialog;

	private ImageButton btBottomIndex;
	private ImageButton btSearchActivity;
	private ImageButton btBottomMy;
	private ImageButton btBottomSettings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_frame);

		ctx = this;

		initComponent();
		
		AppCommon.activityStack.add(this);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		
//		if(ctx.getClass() == IndexActivity.class){
//			this.getBtnIndex().setSelected(true);
//			this.getBtnActivity().setSelected(false);
//			this.getBtnMy().setSelected(false);
//			this.getBtnSettings().setSelected(false);
//			
//			this.getBottomLayout().setVisibility(View.VISIBLE);
//		}else if(ctx.getClass() == SearchActivity.class){
//			this.getBtnIndex().setSelected(false);
//			this.getBtnActivity().setSelected(true);
//			this.getBtnMy().setSelected(false);
//			this.getBtnSettings().setSelected(false);
//			
//			this.getBtnGoBack().setVisibility(View.INVISIBLE);
//			this.getBottomLayout().setVisibility(View.VISIBLE);
//		}else if(ctx.getClass() == UserCenter.class){
//			this.getBtnIndex().setSelected(false);
//			this.getBtnActivity().setSelected(false);
//			this.getBtnMy().setSelected(true);
//			this.getBtnSettings().setSelected(false);
//			
//			this.getBtnGoBack().setVisibility(View.INVISIBLE);
//			this.getBottomLayout().setVisibility(View.VISIBLE);
//		}
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	//
	@Override
	protected void onPause() {
		super.onPause();
		System.gc();
	}

	@Override
	public void onConfigurationChanged(Configuration config) {
		super.onConfigurationChanged(config);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		this.setResult(resultCode, data);
		this.finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void finish() {
		super.finish();
	}

	private void initComponent() {

		titleLayout = (RelativeLayout) findViewById(R.id.main_frame_titlelayout);
		bottomLayout = (LinearLayout)findViewById(R.id.main_frame_llBottom);
		btnGoBack = (Button) findViewById(R.id.main_frame_btnGoBack);
		tvTitle = (TextView) findViewById(R.id.main_frame_tvTitle);
		btnOption = (Button) findViewById(R.id.main_frame_btnOption);
		mainLayout = (LinearLayout) findViewById(R.id.main_frame_layout);
		btnTitle = (Button) findViewById(R.id.main_frame_btnTitle);
		
		btnGoBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		btBottomIndex = (ImageButton)findViewById(R.id.frame_button1);
		btBottomIndex.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(ctx.getClass() != IndexActivity.class){
					ActivityUtils.jump(ctx, IndexActivity.class, false);
				}
			}
		});

		btSearchActivity = (ImageButton)findViewById(R.id.frame_button2);
		btSearchActivity.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
//				if(ctx.getClass() != SearchActivity.class){
//					ActivityUtils.jump(ctx, SearchActivity.class, false);
//				}
//				DialogUtils.showAlert(ctx, "click", "go to Search page", null);
				LogUtils.logD(TAG, "go to search page...");
			}
		});

		btBottomMy = (ImageButton)findViewById(R.id.frame_button3);
		btBottomMy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
//				if(ctx.getClass() != UserCenter.class){
//					ActivityUtils.jump(ctx, UserCenter.class, false);
//				}
//				DialogUtils.showAlert(ctx, "click", "go to UserCenter", null);
				LogUtils.logD(TAG, "go to UserCenter");
			}
		});
		
	}

	public TextView getTvTitle() {
		return tvTitle;
	}

	public Button getBtnOption() {
		return btnOption;
	}

	public Button getBtnTitle() {
		return btnTitle;
	}

	public Button getBtnGoBack() {
		return btnGoBack;
	}
	public LinearLayout getMainLayout() {
		return mainLayout;
	}

	public RelativeLayout getTitleLayout(){
		return titleLayout;
	}
	
	public LinearLayout getBottomLayout(){
		return bottomLayout;
	}
	
	public ImageButton getBtnIndex(){
		return btBottomIndex;
	}
	public ImageButton getBtnActivity(){
		return btSearchActivity;
	}
	public ImageButton getBtnMy(){
		return btBottomMy;
	}
	public ImageButton getBtnSettings(){
		return btBottomSettings;
	}

	public void showProgressDialog(String msg) {
		try {
			if (progressDialog == null) {
				progressDialog = new ProgressDialog(this);
				progressDialog.setTitle("");
				progressDialog.setMessage(msg);
				progressDialog.setIndeterminate(true);
			}
			progressDialog.show();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void closeProgressDialog() {
		try {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			progressDialog = null;
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
