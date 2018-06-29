package com.wbteam.YYzhiyue.ui.mine.MineCenter;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseFragment01;
import com.wbteam.YYzhiyue.ui.login.Login_RegisterActivity;
import com.wbteam.YYzhiyue.util.MyActivityManager;
import com.wbteam.YYzhiyue.util.UtilPreference;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineCenterFragment extends BaseFragment01 implements View.OnClickListener {
    private RelativeLayout rlmine01, rlmine02, rlmine03, rlmine04, rlmine05, rlmine06, rlmine07, rlmine08, rlmine09, rlmine10, rlmine11, rlmine12, rlmine13, rlmine14, rlmine15;
    private TextView tvExit;
    private RelativeLayout rlmine16;
    private Bundle bundle;
    private String android_permissions;


    @Override
    protected int setContentView() {
        return R.layout.fragment_mine_center;
    }

    @Override
    protected void lazyLoad() {
        android_permissions = UtilPreference.getStringValue(getActivity(), "android_permissions");
        initView();
    }

    private void initView() {

        rlmine01 = (RelativeLayout) findViewById(R.id.rl_mine01);//会员
        rlmine02 = (RelativeLayout) findViewById(R.id.rl_mine02);//访客
        rlmine03 = (RelativeLayout) findViewById(R.id.rl_mine03);//我的约会
        rlmine04 = (RelativeLayout) findViewById(R.id.rl_mine04);//我的礼物
        rlmine05 = (RelativeLayout) findViewById(R.id.rl_mine05);//我的悬赏
        rlmine06 = (RelativeLayout) findViewById(R.id.rl_mine06);//我的钱包
        rlmine07 = (RelativeLayout) findViewById(R.id.rl_mine07);//我的红包
        rlmine08 = (RelativeLayout) findViewById(R.id.rl_mine08);//实名认证
        rlmine16 = (RelativeLayout) findViewById(R.id.rl_mine16);//视频认证
        rlmine09 = (RelativeLayout) findViewById(R.id.rl_mine09);//手机绑定
        rlmine10 = (RelativeLayout) findViewById(R.id.rl_mine10);//修改密码
        rlmine11 = (RelativeLayout) findViewById(R.id.rl_mine11);//专属服务
        rlmine12 = (RelativeLayout) findViewById(R.id.rl_mine12);//邀请好友
        rlmine13 = (RelativeLayout) findViewById(R.id.rl_mine13);//关于我们
        rlmine14 = (RelativeLayout) findViewById(R.id.rl_mine14);//意见反馈
        rlmine15 = (RelativeLayout) findViewById(R.id.rl_mine15);//黑名单
        rlmine15.setVisibility(View.GONE);
        tvExit = (TextView) findViewById(R.id.tv_exit);//退出登录
        rlmine01.setOnClickListener(this);
        rlmine02.setOnClickListener(this);
        rlmine03.setOnClickListener(this);
        rlmine04.setOnClickListener(this);
        rlmine05.setOnClickListener(this);
        rlmine06.setOnClickListener(this);
        rlmine07.setOnClickListener(this);
        rlmine08.setOnClickListener(this);
        rlmine09.setOnClickListener(this);
        rlmine10.setOnClickListener(this);
        rlmine11.setOnClickListener(this);
        rlmine12.setOnClickListener(this);
        rlmine13.setOnClickListener(this);
        rlmine14.setOnClickListener(this);
        rlmine15.setOnClickListener(this);
        rlmine16.setOnClickListener(this);
        tvExit.setOnClickListener(this);
        if ("0".equals(android_permissions)) {
            rlmine06.setVisibility(View.GONE);
            rlmine01.setVisibility(View.GONE);
            rlmine07.setVisibility(View.GONE);
        } else {
            rlmine06.setVisibility(View.VISIBLE);
            rlmine01.setVisibility(View.VISIBLE);
            rlmine07.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exit:
                //toActivityFinish(Login_RegisterActivity.class);
                logout();
                break;
            case R.id.rl_mine01:
                toActivity(Mine01Activity.class);
                break;
            case R.id.rl_mine02:
                toActivity(Mine02Activity.class);
                break;
            case R.id.rl_mine03:
                toActivity(Mine03Activity.class);
                break;
            case R.id.rl_mine04:
                toActivity(Mine04Activity.class);
                break;
            case R.id.rl_mine05:
                bundle = new Bundle();
                bundle.putString("keys", "2");
                toActivity(Mine05Activity.class, bundle);
                break;
            case R.id.rl_mine06:
                toActivity(Mine06Activity.class);
                break;
            case R.id.rl_mine07:
                bundle = new Bundle();
                bundle.putString("idKeys", "1");
                toActivity(Mine07Activity.class, bundle);
                break;
            case R.id.rl_mine08:
                toActivity(Mine08Activity.class);
                break;
            case R.id.rl_mine09:
                toActivity(Mine09Activity.class);
                break;
            case R.id.rl_mine10:
                toActivity(Mine10Activity.class);
                break;
            case R.id.rl_mine11:
                toActivity(Mine11Activity.class);
                break;
            case R.id.rl_mine12:
                toActivity(Mine12Activity.class);
                break;
            case R.id.rl_mine13:
                toActivity(Mine13Activity.class);
                break;
            case R.id.rl_mine14:
                toActivity(Mine14Activity.class);
                break;
            case R.id.rl_mine15:
                toActivity(Mine15Activity.class);
                break;
            case R.id.rl_mine16:
                //  bundle = new Bundle();
                // bundle.putString("keys", "2");
                toActivity(Mine17Activity.class);
                break;
        }
    }

    private void logout() {
        if (EMClient.getInstance().isLoggedInBefore()) {
            EMClient.getInstance().logout(true, new EMCallBack() {

                @Override
                public void onSuccess() {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            // show login screen
                            toActivityFinish(Login_RegisterActivity.class);
                            MyActivityManager.getInstance().finishAllActivity();

                        }
                    });
                }

                @Override
                public void onProgress(int progress, String status) {

                }

                @Override
                public void onError(int code, String message) {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            //Toast.makeText(mContext, "unbind devicetokens failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else {
            toActivityFinish(Login_RegisterActivity.class);
            MyActivityManager.getInstance().finishAllActivity();
        }
    }
}
