package com.seppia.android.project_seppia.activity.search;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.seppia.android.project_seppia.R;
import com.seppia.android.project_seppia.activity.BaseActivity;
import com.seppia.android.project_seppia.dto.FetchLocationsDto;
import com.seppia.android.project_seppia.dto.JsonPack;
import com.seppia.android.project_seppia.dto.LocationDto;
import com.seppia.android.project_seppia.task.FetchLocationsByLocationTask;
import com.seppia.android.project_seppia.task.frame.OnTaskFinishedListener;
import com.seppia.android.project_seppia.utils.JsonUtils;
import com.seppia.android.project_seppia.utils.LogUtils;

/**
 * Created by zhanggd on 29.08.16.
 */
public class SearchActivity extends BaseActivity {

    private EditText edFecthLocation;
    private Button btnFetchLocations;
    private static final String TAG = "SearchActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        initCompent();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void initCompent(){
        // setting for header
        this.getBtnGoBack().setVisibility(View.VISIBLE);
        this.getTvTitle().setText("Search");
        this.getBottomLayout().setVisibility(View.VISIBLE);
        // content
        LayoutInflater mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contextView = mInflater.inflate(R.layout.activity_search, null);

        this.getMainLayout().addView(contextView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        edFecthLocation = (EditText)findViewById(R.id.editText);
        btnFetchLocations = (Button)findViewById(R.id.btnFetchLocations);
        btnFetchLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lat = "37.4561922";
                String lng = "-122.1548878";
                String radius = "5000";
                String keyword = "sports";
                LogUtils.logD(TAG, "click on SearchActivity");
//                String fetchLocationsByLocationStr = IOUtils.readStringFromAssets(Settings.GLOBAL_CONTEXT, "sample.json");
//                LogUtils.logD(TAG, "fetchLocationsByLocation: " + fetchLocationsByLocationStr);
//                JsonPack.toBean(fetchLocationsByLocationStr);
                FetchLocationsByLocationTask task = new FetchLocationsByLocationTask(ctx, true, "fetching data...",
                        lat, lng, radius, keyword, new OnTaskFinishedListener(){
                    @Override
                    public void onTaskSuccess(JsonPack jsonPack) {
                        LogUtils.logD(TAG, "result-ok:" + jsonPack.getResult().toString());
                        FetchLocationsDto locationsDto = JsonUtils.fromJson(jsonPack.getResult().toString(), FetchLocationsDto.class);
                        String tmp = "dto:";
                        for (LocationDto dto : locationsDto.getLocations()){
                            tmp += dto.getAddress()
                                    + "\n" + dto.getName()
                                    + "\n" + dto.getPhone()
                                    + "\n(" + dto.getGeometry().getLat() + "," + dto.getGeometry().getLng() + ")"
                                    + "\n" + dto.getRating();
                        }
                        edFecthLocation.setText(jsonPack.getTag() + "\n" + tmp);
                    }

                    @Override
                    public void onTaskFail(JsonPack jsonPack) {
                        LogUtils.logD(TAG, "result-fail:" + jsonPack.toString());
                        edFecthLocation.setText(jsonPack.getTag());
                    }
                });
                task.execute();
            }
        });
    }
}
