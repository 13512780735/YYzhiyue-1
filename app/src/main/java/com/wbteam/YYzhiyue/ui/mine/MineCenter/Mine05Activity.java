package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wbteam.YYzhiyue.ui.MainActivity;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.LoginRegisterTabAdapter;
import com.wbteam.YYzhiyue.ui.mine.MineCenter.myReward.AddRewardFragment;
import com.wbteam.YYzhiyue.ui.mine.MineCenter.myReward.PostRewardFragment;
import com.wbteam.YYzhiyue.util.AndroidWorkaround;
import com.wbteam.YYzhiyue.util.MyActivityManager;
import com.wbteam.YYzhiyue.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mabeijianxi.camera.util.Log;

public class Mine05Activity extends AppCompatActivity {
    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    @BindView(R.id.sliding_tabs)
    TabLayout mTabLayout;

    @BindView(R.id.back_view)
    LinearLayout backView;
    @BindView(R.id.title)
    TextView title;

    private List<String> mDatas;
    private String keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine05);
        MyActivityManager.getInstance().addActivity(this);
        Window window = this.getWindow();
        // 透明状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        // 透明导航栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }
        ButterKnife.bind(this);
        keys = getIntent().getExtras().getString("keys");
        Log.d("TAG23", keys);
        mDatas = new ArrayList<>(Arrays.asList("发布的约会", "参与的约会"));
        initView();

    }

    private void initView() {
        title.setVisibility(View.VISIBLE);
        backView.setVisibility(View.VISIBLE);
        title.setText("我的约会");
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(viewpager);
        List<Fragment> mfragments = new ArrayList<Fragment>();
        mfragments.add(new PostRewardFragment());
        mfragments.add(new AddRewardFragment());
        viewpager.setAdapter(new LoginRegisterTabAdapter(getSupportFragmentManager(), mfragments, mDatas));
        viewpager.setCurrentItem(0);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("3".equals(keys)){
                    finish();
                }else{
                Intent intent = new Intent(Mine05Activity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("keys", "1");
                intent.putExtras(bundle);
                startActivity(intent);
                MyActivityManager.getInstance().finishAllActivity();}
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if("3".equals(keys)){
            finish();
        }else {
        Intent intent = new Intent(Mine05Activity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("keys", "1");
        intent.putExtras(bundle);
        startActivity(intent);
        MyActivityManager.getInstance().finishAllActivity();
    }}
}
