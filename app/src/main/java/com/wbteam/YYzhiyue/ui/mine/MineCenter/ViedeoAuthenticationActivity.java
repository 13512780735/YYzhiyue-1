package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wbteam.YYzhiyue.Entity.UserInfoModel;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.event.IsVipEvent;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.MainActivity;
import com.wbteam.YYzhiyue.util.AndroidWorkaround;
import com.wbteam.YYzhiyue.util.MyActivityManager;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.util.viedeo.VideoActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class ViedeoAuthenticationActivity extends BaseActivity {
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.back_view)
    LinearLayout backView;
    @BindView(R.id.title)
    TextView title;
    private String keys;
    private ViedeoAuthenticationActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viedeo_authentication);
        ButterKnife.bind(this);
        //MyActivityManager.getInstance().addActivity(this);
        mContext = this;
        EventBus.getDefault().register(this);
        setBackView();
        setTitle("视频认证");
        initUser();
        initView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void XXX(IsVipEvent messageEvent) {
        Log.d("TAGS","Event收到");
        initUser();
    }

    private void initUser() {
        RetrofitUtil.getInstance().getUserGetmy(ukey, new Subscriber<BaseResponse<UserInfoModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<UserInfoModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    Log.d("TAG21", baseResponse.getData().getInfo().getHeadimg());
                    UtilPreference.saveString(mContext, "videoauth", baseResponse.getData().getInfo().getVideoauth());
                    UtilPreference.saveString(mContext, "auth", baseResponse.getData().getInfo().getAuth());
                    UtilPreference.saveString(mContext, "isvip", baseResponse.getData().getInfo().getIsvip());
                    UtilPreference.saveString(mContext, "android_permissions", baseResponse.getAndroid_permissions());
                    if ("0".equals(baseResponse.getData().getInfo().getVideoauth())) {
                        tvStatus.setText("视频未通过");
                    } else if ("1".equals(baseResponse.getData().getInfo().getVideoauth())) {
                        tvStatus.setText("视频已通过");
                    } else if ("2".equals(baseResponse.getData().getInfo().getVideoauth())) {
                        tvStatus.setText("视频待审核");
                    } else if ("-1".equals(baseResponse.getData().getInfo().getVideoauth())) {
                        tvStatus.setText("视频未认证");
                    }
                } else {
                    if ("Ukey不合法".equals(baseResponse.getMsg())) {
                        showProgress01("您的帐号已在其他设备登录！");
                        return;
                    } else {
                        showProgress(baseResponse.getMsg());
                    }
                }
            }
        });
    }

    private void initView() {
        // tvStatus.setText("未审核");
        title.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.tv_start})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start:
                UtilPreference.saveString(mContext, "videoKey", "1");
                Intent intent = new Intent(mContext, VideoActivity.class);
                startActivity(intent);
                //finish();
                break;
        }
    }

}
