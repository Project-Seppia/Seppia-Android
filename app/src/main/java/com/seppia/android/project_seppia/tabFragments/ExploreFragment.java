package com.seppia.android.project_seppia.tabFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seppia.android.project_seppia.R;

/**
 * Created by User on 2016/9/11.
 */
public class ExploreFragment extends BaseFragment {
    @Override
    public void fetchData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_explore_layout,container,false);
        return view;
    }
}
