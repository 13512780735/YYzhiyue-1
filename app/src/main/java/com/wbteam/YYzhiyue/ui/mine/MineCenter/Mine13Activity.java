package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.CustomDialog01;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Mine13Activity extends BaseActivity {
    @BindView(R.id.tv_version)
    TextView mTvVersion;
    @BindView(R.id.tv_about)
    TextView mTvAbout;
    @BindView(R.id.message_switch)
    Switch aSwitch;
    private CustomDialog01 dialog;
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine13);
        ButterKnife.bind(this);
        setBackView();
        setTitle("设置");
        flag = UtilPreference.getStringValue(mContext, "flag");
        initView();
        // PushManager.getInstance().turnOffPush(this);
    }

    private void initView() {
        mTvVersion.setText(getVersion());
        if ("0".equals(flag)) {
            aSwitch.setChecked(false);
        } else if ("1".equals(flag)) {
            aSwitch.setChecked(true);
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //控制开关字体颜色
                if (b) {
                    //     Log.d("TAG444","开");
                    PushManager.getInstance().turnOnPush(Mine13Activity.this);
                    UtilPreference.saveString(mContext, "flag", "1");
                } else {
                    //   Log.d("TAG555","关");
                    dialog = new CustomDialog01(Mine13Activity.this).builder()
                            .setGravity(Gravity.CENTER)//默认居中，可以不设置
                            .setTitle("是否关闭消息推送", getResources().getColor(R.color.sd_color_black))//可以不设置标题颜色，默认系统颜色
                            .setCancelable(false)
                            .setNegativeButton("否", new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
                                @Override
                                public void onClick(View view) {
                                    aSwitch.setChecked(true);
                                    PushManager.getInstance().turnOnPush(Mine13Activity.this);
                                    UtilPreference.saveString(mContext, "flag", "1");
                                }
                            })
                            .setPositiveButton("是", getResources().getColor(R.color.sd_color_black), new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    // UtilPreference.saveString(getActivity(), "paykey", "5");
                                    PushManager.getInstance().turnOffPush(Mine13Activity.this);
                                    UtilPreference.saveString(mContext, "flag", "0");
                                }
                            });
                    dialog.show();
                }
            }
        });
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
