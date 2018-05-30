package com.wbteam.YYzhiyue.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.AppiontmentTabAdapter;
import com.wbteam.YYzhiyue.adapter.LoginRegisterTabAdapter;
import com.wbteam.YYzhiyue.base.BaseFragment;
import com.wbteam.YYzhiyue.base.BaseFragment01;
import com.wbteam.YYzhiyue.ui.appointment.Appointment01Fragment;
import com.wbteam.YYzhiyue.ui.appointment.OnlookersFragment;
import com.wbteam.YYzhiyue.ui.appointment.RankingFragment;
import com.wbteam.YYzhiyue.ui.appointment.RecommendFragment;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.NoScrollViewPager;
import com.wbteam.YYzhiyue.view.city.CityActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentFragment extends BaseFragment01 implements View.OnClickListener {

    private static final int REQUEST_REGION_PICK = 1;//城市返回标识

    private ImageView ivLeft;
    private LinearLayout llLeft;
    private TextView tvLeft;
    private ImageView ivRight;
    private String city;
    private LinearLayout llRight;
    private List<String> mDatas;

    NoScrollViewPager viewpager;
    TabLayout mTabLayout;


    @Override
    protected int setContentView() {
        return R.layout.fragment_appointment;
    }

    @Override
    protected void lazyLoad() {
        mDatas = new ArrayList<>(Arrays.asList("推荐", "动态", "围观"));
        city = UtilPreference.getStringValue(getActivity(), "city");
        initHeader();
        initView();
    }

    private void initHeader() {
        llLeft = (LinearLayout) findViewById(R.id.back_view);
        llRight = (LinearLayout) findViewById(R.id.right_view);
        llLeft.setVisibility(View.VISIBLE);
        llRight.setVisibility(View.VISIBLE);
        ivLeft = (ImageView) findViewById(R.id.toolbar_left_iv);
        tvLeft = (TextView) findViewById(R.id.toolbar_left_tv);
        ivRight = (ImageView) findViewById(R.id.toolbar_righ_iv);
        ivRight.setVisibility(View.VISIBLE);
        tvLeft.setText(city);
        ivLeft.setImageDrawable(this.getResources().getDrawable(R.drawable.icon_nav_adress));
        ivRight.setImageDrawable(this.getResources().getDrawable(R.drawable.icon_nav_filtrate));
        llLeft.setOnClickListener(this);
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        viewpager = (NoScrollViewPager) findViewById(R.id.viewpager);
        mTabLayout.setTabMode(TabLayout.GRAVITY_CENTER);
        mTabLayout.setupWithViewPager(viewpager);
        List<Fragment> mfragments = new ArrayList<Fragment>();
        mfragments.add(new RecommendFragment());
        mfragments.add(new Appointment01Fragment());
        mfragments.add(new OnlookersFragment());
        //mfragments.add(new RankingFragment());
        viewpager.setAdapter(new LoginRegisterTabAdapter(getChildFragmentManager(), mfragments, mDatas));
        viewpager.setCurrentItem(0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_view:
                Intent intent = new Intent(getActivity(), CityActivity.class);
                startActivityForResult(intent, REQUEST_REGION_PICK);
                break;
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
