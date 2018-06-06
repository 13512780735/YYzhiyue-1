package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Mine13Activity extends BaseActivity {
    @BindView(R.id.tv_version)
    TextView mTvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine13);
        ButterKnife.bind(this);
        setBackView();
        setTitle("关于我们");
        initView();
    }

    private void initView() {
        mTvVersion.setText(getVersion());
//        try {
//            InputStream is = getAssets().open("about.txt");
//            int size = is.available();
//
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//
//            String text = new String(buffer, "GB2312");
//
//            mContentTv.setText(text);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return "V" + version;
        } catch (Exception e) {
            return "";
        }
    }
}
