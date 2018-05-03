package com.wbteam.YYzhiyue.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.AppiontmentTabAdapter;
import com.wbteam.YYzhiyue.base.BaseFragment;
import com.wbteam.YYzhiyue.base.BaseFragment01;
import com.wbteam.YYzhiyue.ui.message.MesFansFragment;
import com.wbteam.YYzhiyue.ui.message.MesGroupFragment;
import com.wbteam.YYzhiyue.ui.message.MesMessageFragment;
import com.wbteam.YYzhiyue.ui.message.MesRankingFragment;
import com.wbteam.YYzhiyue.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment01 {


    private List<String> mDatas;
    private TabLayout mTabLayout;
    private NoScrollViewPager viewpager;

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        setContentView(R.layout.fragment_message);
//        mDatas = new ArrayList<>(Arrays.asList("消息", "好友", "群组", "排行", "粉丝"));
//        initView();
//        return getContentView();
//    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_message;
    }

    @Override
    protected void lazyLoad() {
        mDatas = new ArrayList<>(Arrays.asList("消息", "关注"));
        initView();
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        viewpager = (NoScrollViewPager) findViewById(R.id.viewpager);
        mTabLayout.setTabMode(TabLayout.GRAVITY_CENTER);
        mTabLayout.setupWithViewPager(viewpager);
        List<Fragment> mfragments = new ArrayList<Fragment>();
        mfragments.add(new MesMessageFragment());
        //mfragments.add(new MesMessageFragment());
       // mfragments.add(new MesFriendFragment());
        //mfragments.add(new MesGroupFragment());
      mfragments.add(new MesRankingFragment());
       // mfragments.add(new MesFansFragment());
        viewpager.setAdapter(new AppiontmentTabAdapter(getChildFragmentManager(), mfragments, mDatas));
        viewpager.setCurrentItem(0);


    }

}
