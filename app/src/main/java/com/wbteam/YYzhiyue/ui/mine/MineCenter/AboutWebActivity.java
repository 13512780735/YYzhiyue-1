package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.view.X5WebView;

public class AboutWebActivity extends BaseActivity {
    private String url;
    X5WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_web);
        url = "http://app.yun-nao.com/index.php?s=/People/index/index";
        // url = "https://www.baidu.com";
        setBackView();
        setTitle("关于影缘");
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
