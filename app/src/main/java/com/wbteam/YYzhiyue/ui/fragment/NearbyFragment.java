package com.wbteam.YYzhiyue.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.LoginRegisterTabAdapter;
import com.wbteam.YYzhiyue.base.BaseFragment01;
import com.wbteam.YYzhiyue.ui.mine.MineCenter.ViedeoAuthenticationActivity;
import com.wbteam.YYzhiyue.ui.neaeby.FriendFragment;
import com.wbteam.YYzhiyue.ui.neaeby.MovieFragment;
import com.wbteam.YYzhiyue.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyFragment extends BaseFragment01 {


    private List<String> mDatas;
    private TabLayout mTabLayout;
    private NoScrollViewPager viewpager;
    //private TextView tv_Video;
    private Bundle bundle;


    @Override
    protected int setContentView() {
        return R.layout.fragment_nearby;
    }

    @Override
    protected void lazyLoad() {
        mDatas = new ArrayList<>(Arrays.asList("影友", "电影"));
        initView();
    }

    private void initView() {
        setTitle("附近");
        //tv_Video = (TextView) findViewById(R.id.message_img);
        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        viewpager = (NoScrollViewPager) findViewById(R.id.viewpager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(viewpager);
        List<Fragment> mfragments = new ArrayList<Fragment>();
        mfragments.add(new FriendFragment());
        mfragments.add(new MovieFragment());
        viewpager.setAdapter(new LoginRegisterTabAdapter(getChildFragmentManager(), mfragments, mDatas));
        viewpager.setCurrentItem(0);
        setRightText("视频认证", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                // bundle.putString("keys", "2");
                toActivity(ViedeoAuthenticationActivity.class, bundle);
            }
        });
    }

}
