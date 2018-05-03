package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.ui.MainActivity;
import com.wbteam.YYzhiyue.util.AndroidWorkaround;
import com.wbteam.YYzhiyue.util.MyActivityManager;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.util.viedeo.VideoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        setBackView();
        setTitle("视频认证");
//        Window window = this.getWindow();
//        // 透明状态栏
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////        // 透明导航栏
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
//            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
//        }
//        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
//            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
//        }

       // keys = getIntent().getExtras().getString("keys");
        initView();
    }

    private void initView() {
        tvStatus.setText("未审核");
        title.setVisibility(View.VISIBLE);
//        backView.setVisibility(View.VISIBLE);
//        title.setText("视频认证");
//        backView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, MainActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("keys", "2");
//                intent.putExtras(bundle);
//                startActivity(intent);
//                MyActivityManager.getInstance().finishAllActivity();
//            }
//        });
    }

    @OnClick({R.id.tv_start})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start:
                UtilPreference.saveString(mContext,"videoKey","1");
                Intent intent = new Intent(mContext, VideoActivity.class);
                startActivity(intent);
                //finish();
                break;
        }
    }

}
