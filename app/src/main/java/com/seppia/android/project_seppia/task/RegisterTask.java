package com.seppia.android.project_seppia.task;

import android.content.Context;

import com.seppia.android.project_seppia.dto.JsonPack;
import com.seppia.android.project_seppia.http.HttpApi;
import com.seppia.android.project_seppia.task.frame.SeppiaTask;
import com.seppia.android.project_seppia.task.frame.OnTaskFinishedListener;

public class RegisterTask extends SeppiaTask {

	private String phone;
	private String password;
	private String smsCode;
	
	public RegisterTask(Context context, boolean showLoading, String msg,String phone, String password, String smsCode,
			OnTaskFinishedListener listener) {
		super(context, showLoading, msg, listener);
		this.phone = phone;
		this.password = password;
		this.smsCode = smsCode;
	}

	@Override
	public JsonPack getData() {
		return HttpApi.getInstance().getSmsNewCode(phone, password);
	}

}
