package com.seppia.android.project_seppia.tabFragments.tabView;

import com.seppia.android.project_seppia.tabFragments.BaseFragment;

/**
 * Created by User on 2016/9/11.
 */
public class TabItem {

    public int imageResId;

    public int lableResId;

    public Class<? extends BaseFragment>tagFragmentClz;

    public TabItem(int imageResId, int lableResId, Class<? extends BaseFragment> tagFragmentClz) {
        this.imageResId = imageResId;
        this.lableResId = lableResId;
        this.tagFragmentClz = tagFragmentClz;
    }
}
