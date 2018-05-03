package com.wbteam.YYzhiyue.ui.appointment;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.util.FileUtils;
import com.wbteam.YYzhiyue.view.PlayView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class OnlookersVideoActivity extends BaseActivity {
    private static String savePath;
    private Bitmap bitmap;
    @BindView(R.id.playView)
    PlayView playView;
    @BindView(R.id.playBtn)
    Button playBtn;
    @BindView(R.id.activity_play)
    RelativeLayout activityPlay;
    @BindView(R.id.iv01)
    ImageView iv01;
    private long playPostion = -1;
    private long duration = -1;
    String uri;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlookers_video);
        ButterKnife.bind(this);
        setBackView();
        setTitle("视频预览");
        url = getIntent().getStringExtra("data");
        playView.setVideoURI(Uri.parse(url));
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        final int width = metrics.widthPixels;
        playView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //获取视频资源的宽度
                int videoWidth = mp.getVideoWidth();
                //获取视频资源的高度
                int videoHeight = mp.getVideoHeight();
                playView.setSizeH(width);
                playView.setSizeW(width);
                playView.requestLayout();
                duration = mp.getDuration();
                play();
            }
        });

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();//如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
        if (!isScreenOn) {
            pauseVideo();
        }
    }

    @OnClick(R.id.playBtn)
    public void onClick() {
        play();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (playPostion > 0) {
            pauseVideo();
        }
        playView.seekTo((int) ((playPostion > 0 && playPostion < duration) ? playPostion : 1));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playView.stopPlayback();
    }

    @Override
    protected void onPause() {
        super.onPause();
        playView.pause();
        playPostion = playView.getCurrentPosition();
        pauseVideo();

    }

    @Override
    public void onBackPressed() {
        FileUtils.deleteFile(uri);
        finish();
        overridePendingTransition(R.anim.fab_in, R.anim.fab_out);
    }


    private void pauseVideo() {
        playView.pause();
        playBtn.setText("播放");
    }

    private void startVideo() {
        playView.start();
        playBtn.setText("停止");
    }

    /**
     * 播放
     */
    private void play() {
        if (playView.isPlaying()) {
            pauseVideo();
        } else {
            if (playView.getCurrentPosition() == playView.getDuration()) {
                playView.seekTo(0);
            }
            startVideo();
        }
    }
}
