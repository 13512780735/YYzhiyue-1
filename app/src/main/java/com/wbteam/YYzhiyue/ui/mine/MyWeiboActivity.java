package com.wbteam.YYzhiyue.ui.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;

import butterknife.ButterKnife;

public class MyWeiboActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_weibo);
        ButterKnife.bind(this);
        setBackView();
        setTitle("我的动态");
    }
}
