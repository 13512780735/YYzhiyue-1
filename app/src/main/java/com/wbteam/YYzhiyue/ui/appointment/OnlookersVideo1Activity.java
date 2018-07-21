package com.wbteam.YYzhiyue.ui.appointment;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dl7.player.media.IjkPlayerView;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;

import butterknife.BindView;
import tv.danmaku.ijk.media.player.IMediaPlayer;


public class OnlookersVideo1Activity extends BaseActivity {
    private IjkPlayerView mPlayerView;
    Button playBtn;
    private String url;
    private String imgUrl;
    private LinearLayout back_view;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlookers_video);
        // ButterKnife.bind(this);
        // setBackView();
        //  setTitle("视频预览");
        url = getIntent().getStringExtra("data");
        imgUrl = getIntent().getStringExtra("imgUrl");
        initUI();
    }

    private void initUI() {
        mPlayerView = (IjkPlayerView) findViewById(R.id.player_view);
        playBtn = (Button) findViewById(R.id.playBtn);
        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setVisibility(View.VISIBLE);
        back_view = (LinearLayout) findViewById(R.id.back_view);
        back_view.setVisibility(View.VISIBLE);
        mTitle.setText("视频预览");
        mPlayerView.init()
                // .setDanmakuSource(getResources().openRawResource(R.raw.bili))
                .setVideoPath(url).start();
        mPlayerView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                playBtn.setVisibility(View.VISIBLE);
            }
        });
        back_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayerView.switchVideoPath(url).start();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayerView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerView.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mPlayerView.configurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mPlayerView.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (mPlayerView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
