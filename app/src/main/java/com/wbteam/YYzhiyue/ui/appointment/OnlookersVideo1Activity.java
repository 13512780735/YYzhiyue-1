package com.wbteam.YYzhiyue.ui.appointment;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.ui.login.Login_RegisterActivity;
import com.wbteam.YYzhiyue.ui.login.WelcomeGuideActivity;
import com.wbteam.YYzhiyue.view.PlayView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class OnlookersVideo1Activity extends BaseActivity {
    @BindView(R.id.buffer)
    VideoView vitamio_videoview;
    private String url;
    private Handler mHandler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlookers_video);
        ButterKnife.bind(this);
        setBackView();
        setTitle("视频预览");
        url = getIntent().getStringExtra("data");

        Vitamio.isInitialized(this);
        LoaddingShow();
        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                LoaddingDismiss();
                //从闪屏界面跳转到首界面
                initView();
            }
        }, 2000);
    }

    private void initView() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                vitamio_videoview.setVideoURI(Uri.parse(url));
//
//                //设置控制栏
//                vitamio_videoview.setMediaController(new MediaController(this));
//
//                //获取焦点
//                vitamio_videoview.requestFocus();
//
//                //准备播放监听
//                vitamio_videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mp) {
//                        mp.setPlaybackSpeed(1.0f);//设置播放速度
//                    }
//                });
//            }
//        }).
        //检测Vitamio是否解压解码包，一定要加
//        if (!io.vov.vitamio.L.checkVitamioLibs(this))
//            return;

        //初始化Vitamio包下的VideoView
        // vitamio_videoview = (VideoView) findViewById(R.id.vitamio_videoview);

        //放入网址
        vitamio_videoview.setVideoURI(Uri.parse(url));

        //设置控制栏
        vitamio_videoview.setMediaController(new MediaController(this));

        //获取焦点
        vitamio_videoview.requestFocus();

        //准备播放监听
        vitamio_videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setPlaybackSpeed(1.0f);//设置播放速度
            }
        });
    }
}
