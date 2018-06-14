package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Mine13Activity extends BaseActivity {
    @BindView(R.id.tv_version)
    TextView mTvVersion;
    @BindView(R.id.tv_about)
    TextView mTvAbout;

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

    @OnClick(R.id.tv_about)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_about:
                toActivity(AboutWebActivity.class);
                break;
        }
    }
}
