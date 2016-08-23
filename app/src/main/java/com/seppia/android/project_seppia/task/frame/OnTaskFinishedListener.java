package com.seppia.android.project_seppia.task.frame;

import com.seppia.android.project_seppia.dto.JsonPack;


public interface OnTaskFinishedListener {

	public void onTaskSuccess(JsonPack jsonPack);
	
	public void onTaskFail(JsonPack jsonPack);
}
