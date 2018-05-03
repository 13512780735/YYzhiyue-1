package com.wbteam.YYzhiyue.ui.neaeby;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseFragment01;
import com.wbteam.YYzhiyue.view.X5WebView;

/**
 * A simple {@link MovieFragment} subclass.
 */
public class MovieFragment extends BaseFragment01 {


    private String url;
    X5WebView webView;

    @Override
    protected int setContentView() {
        return R.layout.fragment_movie;
    }

    @Override
    protected void lazyLoad() {
        Log.d("TAG222", "5555");
        url = "http://channel.h5.komovie.cn/?channel=284";
        // url = "https://www.baidu.com";
        initHardwareAccelerate();
        initView();
    }
    /**
     * 启用硬件加速
     */
    private void initHardwareAccelerate() {
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                getActivity().getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }
    }
    private void initView() {
        webView = findViewById(R.id.x5_webview);
        webView.loadUrl(url);
        LoaddingShow();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               LoaddingDismiss();
            }
        }, 2000);
//        webView.loadUrl(url);
//        LoaddingShow();
//        webView.getSettings().setJavaScriptEnabled(false);
//        webView.getSettings().setSupportZoom(true);
//        // 设置出现缩放工具
//        webView.getSettings().setBuiltInZoomControls(true);
//        // 为图片添加放大缩小功能
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.setInitialScale(70);
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                //Toast.makeText(MainActivity.this,"加载完毕",Toast.LENGTH_SHORT).show();
//                LoaddingDismiss();
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                // Toast.makeText(MainActivity.this,"开始加载",Toast.LENGTH_SHORT).show();
//            }
//        });


//        mContent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"触发点击",Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void onDestroy() {
        //释放资源
        if (webView != null)
            webView.destroy();
        super.onDestroy();
    }
}
