package com.wbteam.YYzhiyue.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.AppiontmentTabAdapter;
import com.wbteam.YYzhiyue.base.BaseFragment;
import com.wbteam.YYzhiyue.base.BaseFragment01;
import com.wbteam.YYzhiyue.ui.reward.ReRewardFragment;
import com.wbteam.YYzhiyue.ui.reward.ReShopFragment;
import com.wbteam.YYzhiyue.ui.reward.RewardRuleActivity;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.NoScrollViewPager;
import com.wbteam.YYzhiyue.view.city.CityActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RewardFragment extends BaseFragment01 implements View.OnClickListener {
    private static final int REQUEST_REGION_PICK = 1;//城市返回标识

    private List<String> mDatas;
    private TabLayout mTabLayout;
    private NoScrollViewPager viewpager;
    private TextView tvRight, tvLeft, tvAddress;
    private LinearLayout llBack;
    private int position;
    private String city;

    @Override
    protected int setContentView() {
        return R.layout.fragment_reward;
    }

    @Override
    protected void lazyLoad() {
        mDatas = new ArrayList<>(Arrays.asList("约会"));
        city = UtilPreference.getStringValue(getActivity(), "city");
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView() {
        tvRight = (TextView) findViewById(R.id.tv_head_right);
        tvAddress = (TextView) findViewById(R.id.toolbar_left_address);
        tvLeft = (TextView) findViewById(R.id.toolbar_left_tv);
        llBack = (LinearLayout) findViewById(R.id.back_view);
        tvAddress.setText(city);
        llBack.setOnClickListener(this);
        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        viewpager = (NoScrollViewPager) findViewById(R.id.viewpager);
        mTabLayout.setTabMode(TabLayout.GRAVITY_CENTER);
        mTabLayout.setupWithViewPager(viewpager);
        List<Fragment> mfragments = new ArrayList<Fragment>();
        mfragments.add(new ReRewardFragment());
      //  mfragments.add(new ReShopFragment());
        viewpager.setAdapter(new AppiontmentTabAdapter(getChildFragmentManager(), mfragments, mDatas));
        //viewpager.setCurrentItem(0);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                //viewpager.setCurrentItem(position);
                if (position == 0) {
                    tvLeft.setVisibility(View.GONE);
                    tvAddress.setVisibility(View.VISIBLE);
                    viewpager.setCurrentItem(0);
                    tvAddress.setText(city);
                } else if (position == 1) {
                    tvAddress.setVisibility(View.GONE);
                    tvLeft.setVisibility(View.VISIBLE);
                    viewpager.setCurrentItem(1);
                    tvLeft.setText("创建");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(RewardRuleActivity.class);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (position == 0) {
            Intent intent = new Intent(getActivity(), CityActivity.class);
            startActivityForResult(intent, REQUEST_REGION_PICK);
        } else if (position == 1) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //   super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_REGION_PICK) {
            if (data != null) {
                city = data.getStringExtra("date");
                tvLeft.setText(city);
                UtilPreference.saveString(getActivity(), "city", city);
                //initData(city);
                //tvAddress.setText(city);
            }
        }
    }
}
