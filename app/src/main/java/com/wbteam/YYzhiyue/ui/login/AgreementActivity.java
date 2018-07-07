package com.wbteam.YYzhiyue.ui.login;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.view.X5WebView;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgreementActivity extends BaseActivity {
//    @BindView(R.id.tv_mContentTv)
//    TextView mContentTv;
    private String url;
    X5WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        url = "http://app.yun-nao.com/index.php?s=/People/index/protocol";
        setBackView();
        setTitle("注册协议");
        initHardwareAccelerate();
        initView();
    }
    /**
     * 启用硬件加速
     */
    private void initHardwareAccelerate() {
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                this.getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }
    }
    private void initView() {
        findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        try {
//            InputStream is = getAssets().open("agreement.txt");
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
        webView = (X5WebView) findViewById(R.id.x5_webview);
        webView.loadUrl(url);
        LoaddingShow();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoaddingDismiss();
            }
        }, 2000);

    }

    @Override
    public void onDestroy() {
        //释放资源
        if (webView != null)
            webView.destroy();
        super.onDestroy();
    }

}
