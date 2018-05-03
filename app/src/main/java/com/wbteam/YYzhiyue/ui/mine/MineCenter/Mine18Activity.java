package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.util.UtilPreference;

import mabeijianxi.camera.util.Log;

public class Mine18Activity extends BaseActivity {

    private String idKeys;
    private TextView tv03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main18);
        idKeys = UtilPreference.getStringValue(this,"idKeys");//1.我的界面  2.悬赏
        tv03= (TextView) findViewById(R.id.tv03);
        Log.d("TAG4",idKeys);
        showToast(idKeys);
        tv03.setText(idKeys);
    }
}
