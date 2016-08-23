package com.seppia.android.project_seppia.task.frame;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import com.seppia.android.project_seppia.AppCommon;
import com.seppia.android.project_seppia.R;
import com.seppia.android.project_seppia.activity.IndexActivity;
import com.seppia.android.project_seppia.dto.JsonPack;
import com.seppia.android.project_seppia.utils.ActivityUtils;
import com.seppia.android.project_seppia.utils.DeviceUtils;
import com.seppia.android.project_seppia.utils.DialogUtils;

public abstract class SeppiaTask extends BaseTask<JsonPack> {

	private OnTaskFinishedListener mListener;
	private Context context;
	public abstract JsonPack getData();

	public SeppiaTask(Context context, boolean showLoading, String msg, OnTaskFinishedListener listener) {
		super(context, showLoading, msg);
		mListener = listener;
		this.context = context;
	}

	@Override
	protected void onPreTask() {
		if (!DeviceUtils.isNetworkEnabled(getContext())) {
			DialogUtils.showToastLong(getContext(), getContext().getString(R.string.info_net_unavailable));
			cancelTask();
			if (mListener != null) {
				JsonPack jsonPack = new JsonPack();
				jsonPack.setErr_msg(getContext().getString(R.string.info_net_unavailable));
				mListener.onTaskFail(jsonPack);
			}
		}
	}

	@Override
	protected JsonPack onInTask(Object... obj) {
		return getData();
	}

	@Override
	protected void onPostTask(JsonPack result) {
		if (result == null) {
			if (mListener != null) {
				mListener.onTaskFail(result);
			}
			return;
		}
		
		if (result.getCode() == AppCommon.CONST_RESPONSE_CODE_SERVER_TOKEN_NOT_FIND) {
			DialogUtils.showAlert(context, "", result.getErr_msg(), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ActivityUtils.jump(context, IndexActivity.class);
					try {
						((Activity)context).finish();
					} catch (Exception e) {
					}
				}
			});
			return;
		}
		
		if (result.getCode() != AppCommon.CONST_RESPONSE_CODE_SUCCESS) {
			if (mListener != null) {
				mListener.onTaskFail(result);
			}
			return;
		}
		if (mListener != null) {
			mListener.onTaskSuccess(result);
		}
	}
}
