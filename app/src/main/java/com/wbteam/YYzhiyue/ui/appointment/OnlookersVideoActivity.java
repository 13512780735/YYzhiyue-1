package com.wbteam.YYzhiyue.ui.appointment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

import static android.R.attr.width;

public class OnlookersVideoActivity extends BaseActivity {
    private static String savePath;
    private Bitmap bitmap;
    @BindView(R.id.playView)
    PlayView playView;
    @BindView(R.id.iv_Video)
    ImageView iv_Video;
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
    private Handler mHandler;
    private Runnable runnable;
    private String imgUrl;
    private int mCurrentPosition;
    private static final long HIDE_IMAGE_DELAY = 100;
    int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlookers_video);
        ButterKnife.bind(this);
        setBackView();
        setTitle("视频预览");
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        url = getIntent().getStringExtra("data");
        imgUrl = getIntent().getStringExtra("imgUrl");
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_Video.getLayoutParams();
        params.height = width;
        params.width = width;
        iv_Video.setLayoutParams(params);
        //  iv_Video.setVisibility(View.VISIBLE);
        Glide.with(mContext).load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.icon_default_picture)
                .error(R.mipmap.icon_default_picture)
                .centerCrop().override(1090, 1090 * 3 / 4)
                .crossFade().into(iv_Video);
        //  LoaddingShow();
        playView.setSizeH(width);
        playView.setSizeW(width);
        playView.requestLayout();
      //  LoaddingShow();
        initUI();
//        handler = new Handler();
//        handler.postDelayed(runnable = new Runnable() {
//            @Override
//            public void run() {
//                // iv_Video.setVisibility(View.GONE);
//                LoaddingDismiss();
//                //从闪屏界面跳转到首界面
//
//            }
//        }, 2000);


    }

    private void initUI() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();//如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
        if (!isScreenOn)

        {
            pauseVideo();
        }
        playView.setVideoURI(Uri.parse(url));

        playView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                duration = mp.getDuration();
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                            //iv_Video.setVisibility(View.VISIBLE);
//                            playView.setVisibility(View.INVISIBLE);
//                        } else {
//                           // iv_Video.setVisibility(View.GONE);
//                           // LoaddingDismiss();
                         //   playView.setVisibility(View.VISIBLE);
                            playView.start();
                        }
                        return true;
                    }
                });
//                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
//                    @Override
//                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
//                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
//                            playView.setBackgroundColor(Color.TRANSPARENT);
//                        iv_Video.setVisibility(View.GONE);
//                        return true;
//
//
//                    }
//                }}
            }
        });

        playView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()

        {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //播放结束后的动作
                pauseVideo();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //在onStop()方法中显示预览图片
        // playView.setVisibility(View.VISIBLE);
        //停止视频播放
        if (playView != null) {
            mCurrentPosition = playView.getCurrentPosition();
            playView.stopPlayback();
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

    //
    @Override
    public void onBackPressed() {
        FileUtils.deleteFile(uri);
        OnlookersVideoActivity.this.finish();
        overridePendingTransition(R.anim.fab_in, R.anim.fab_out);
    }

    //
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        FileUtils.deleteFile(uri);
        OnlookersVideoActivity.this.finish();
        overridePendingTransition(R.anim.fab_in, R.anim.fab_out);
        return super.onKeyDown(keyCode, event);
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
