package com.seppia.android.project_seppia.task;

import android.content.Context;

import com.seppia.android.project_seppia.dto.JsonPack;
import com.seppia.android.project_seppia.http.HttpApi;
import com.seppia.android.project_seppia.task.frame.OnTaskFinishedListener;
import com.seppia.android.project_seppia.task.frame.SeppiaTask;

/**
 * Created by zhanggd on 29.08.16.
 */
public class FetchLocationsByLocationTask extends SeppiaTask {
    private String lat;
    private String lng;
    private String radius;
    private String keyword;
    private static final String TAG = "FetchLocationsByLocationTask";

    public FetchLocationsByLocationTask(Context context, boolean showLoading, String msg,
                                        String lat, String lng, String radius, String keyword,
                                        OnTaskFinishedListener listener) {
        super(context, showLoading, msg, listener);
        this.lat = lat;
        this.lng = lng;
        this.radius = radius;
        this.keyword = keyword;
    }

    @Override
    public JsonPack getData() {
        return HttpApi.getInstance().fetchLocationsByLocation(lat, lng, radius, keyword);
    }
}
