package com.seppia.android.project_seppia;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.seppia.android.project_seppia.account.LoginActivity;
import com.seppia.android.project_seppia.tabFragments.BaseFragment;
import com.seppia.android.project_seppia.tabFragments.ExploreFragment;
import com.seppia.android.project_seppia.tabFragments.MainFragment;
import com.seppia.android.project_seppia.tabFragments.MeFragment;
import com.seppia.android.project_seppia.tabFragments.SearchFragment;
import com.seppia.android.project_seppia.tabFragments.tabView.TabItem;
import com.seppia.android.project_seppia.tabFragments.tabView.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabClickListener {

    TextView temp_display;
    private TabLayout tabLayout;
    BaseFragment fragment;
    ViewPager viewPager;
    ArrayList<TabItem> tabs;
    public ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String intentMsg = intent.getStringExtra(LoginActivity.TAG);
        //temp_display = (TextView) findViewById(R.id.textView_MA_temp);
        //temp_display.setText(intentMsg);

        initView();
        initData();
    }

    private void initView(){
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
    }
    private void initData(){
        tabs = new ArrayList<TabItem>();
        tabs.add(new TabItem(R.drawable.tab_main,R.string.tab_main,MainFragment.class));
        tabs.add(new TabItem(R.drawable.tab_search,R.string.tab_search, SearchFragment.class));
        tabs.add(new TabItem(R.drawable.tab_explore,R.string.tab_explore,ExploreFragment.class));
        tabs.add(new TabItem(R.drawable.tab_me,R.string.tab_me,MeFragment.class));

        tabLayout.initData(tabs, this);
        tabLayout.setCurrentTab(0);

        FragAdapter adapter = new FragAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
                //actionBar.setTitle(tabs.get(position).lableResId);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onTabClick(TabItem tabItem) {

        //actionBar.setTitle(tabItem.lableResId);
        viewPager.setCurrentItem(tabs.indexOf(tabItem));

    }

    public class FragAdapter extends FragmentPagerAdapter {


        public FragAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            try {
                return tabs.get(arg0).tagFragmentClz.newInstance();

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return tabs.size();
        }

    }
}
