package com.wbteam.YYzhiyue.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushManager;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.util.MyActivityManager;
import com.wbteam.YYzhiyue.util.UtilPreference;

import java.util.Timer;
import java.util.TimerTask;

public class GuideActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        final Intent intent = new Intent(this, Login_RegisterActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                toActivityFinish(Login_RegisterActivity.class);
            }
        };
        timer.schedule(task, 3000);// 此处的Delay可以是3*1000，代表三秒
    }


}
