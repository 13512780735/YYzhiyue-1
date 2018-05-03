package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.PowerManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.VideoModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.MainActivity;
import com.wbteam.YYzhiyue.ui.login.Perfect02Activity;
import com.wbteam.YYzhiyue.util.FileUtils;
import com.wbteam.YYzhiyue.util.MyActivityManager;
import com.wbteam.YYzhiyue.util.ToastUtil;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.LoadingDialog;
import com.wbteam.YYzhiyue.view.PlayView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mabeijianxi.camera.MediaRecorderActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;

public class PlayActivity extends BaseActivity {
    public final static String DATA = "URL";
    private static String savePath;
    private Bitmap bitmap;
    @BindView(R.id.playView)
    PlayView playView;
    @BindView(R.id.back_view)
    LinearLayout back_view;
    @BindView(R.id.toolbar_righ_tv)
    TextView toolbar_righ_tv;
    @BindView(R.id.playBtn)
    Button playBtn;
    @BindView(R.id.activity_play)
    RelativeLayout activityPlay;
    @BindView(R.id.iv01)
    ImageView iv01;
    private long playPostion = -1;
    private long duration = -1;
    String uri;
    public static final String MULTIPART_FORM_DATA = "image/jpg";
    public static final String MULTIPART_FORM_DATA1 = "video/mp4";
    private RequestBody requestApiKey;
    private MultipartBody.Part requestVideoPart;
    private MultipartBody.Part requestImgPart;
    private RequestBody requestApiTitle;
    private static File filePic;
    private String videoKey;
    private LoadingDialog loadingDialog;
    private String ukey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        setTitle("视频预览");
        setBackView();

        loadingDialog = new LoadingDialog(this);
        videoKey = UtilPreference.getStringValue(this, "videoKey");
        ukey = UtilPreference.getStringValue(this, "ukey");
    //uri = getIntent().getStringExtra(DATA);
        uri = getIntent().getStringExtra(MediaRecorderActivity.VIDEO_URI);
        Log.d("TAG999", uri);
        playView.setVideoURI(Uri.parse(uri));
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
                mp.setLooping(false);
            }
        });

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();//如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
        if (!isScreenOn) {
            pauseVideo();
        }

        /**
         * 封面
         */
        MediaMetadataRetriever rev = new MediaMetadataRetriever();

        rev.setDataSource(this, Uri.parse(uri));    //这里第一个参数需要Context，传this指针

        bitmap = rev.getFrameAtTime(playView.getCurrentPosition() * 1000,

                MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        saveBitmap(this, bitmap);
        //File file01 = new File(savePath);
        RequestBody requestFile01 =               // 根据文件格式封装文件
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), filePic);
        requestImgPart =
                MultipartBody.Part.createFormData("fileimg", filePic.getName(), requestFile01);

        /**
         * 视频
         */
        File file = new File(uri);
        requestApiKey = RequestBody.create(MediaType.parse("multipart/form-data"), ukey);
        requestApiTitle = RequestBody.create(MediaType.parse("multipart/form-data"), "1111");
        RequestBody requestFile =               // 根据文件格式封装文件
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA1), file);
        requestVideoPart =
                MultipartBody.Part.createFormData("filevideo", file.getName(), requestFile);
        playView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playView.seekTo(1);
                startVideo();
            }
        });

        initView();
    }

    private void initView() {
        Log.d("TAG", bitmap + "");
        iv01.setImageBitmap(bitmap);
        back_view.setVisibility(View.VISIBLE);
        toolbar_righ_tv.setVisibility(View.VISIBLE);
        toolbar_righ_tv.setText("发布");
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
               // playView.pause();
            }
            startVideo();
        }
    }

    private static final String SD_PATH = "/sdcard/dskqxt/pic/";
    private static final String IN_PATH = "/dskqxt/pic/";

    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    public static String saveBitmap(Context context, Bitmap mBitmap) {

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + IN_PATH;
        }
        try {
            filePic = new File(savePath + generateFileName() + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }

    @OnClick({ R.id.right_view})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.right_view:
                loadingDialog.show();
                if ("1".equals(videoKey)) {
                    Log.d("TAG1111", "1");
                    RetrofitUtil.getInstance().Uploadvideoauth(requestApiKey, requestApiTitle, requestImgPart, requestVideoPart, new Subscriber<BaseResponse<VideoModel>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            loadingDialog.dismiss();
                        }

                        @Override
                        public void onNext(BaseResponse<VideoModel> baseResponse) {
                            loadingDialog.dismiss();
                            if (baseResponse.ret == 200) {
                                //Log.d("TAG888", baseResponse.getData().getPhoto_url() + "777:" + baseResponse.getData().getVideo_url());
                                Intent intent = new Intent(PlayActivity.this, ViedeoAuthenticationActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("keys", "5");
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                               // MyActivityManager.getInstance().finishAllActivity();
                            } else {
                                // ToastUtil.showS(PlayActivity.this,baseResponse.getMsg());
                                if ("Ukey不合法".equals(baseResponse.getMsg())) {
                                    showProgress01("您的帐号已在其他设备登录！");

                                    return;
                                } else {
                                    showProgress(baseResponse.getMsg());
                                }
                            }
                        }
                    });
                } else if ("2".equals(videoKey)) {
                    //Log.d("TAG222","2");
                    // Log.d("TAG456", "filePic->" + filePic + "requestApiKey->" + requestApiKey + "requestImgPart->" + requestImgPart + "requestVideoPart->" + requestVideoPart);
                    RetrofitUtil.getInstance().VideoUploadvideo(requestApiKey, requestApiTitle, requestImgPart, requestVideoPart, new Subscriber<BaseResponse<VideoModel>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            loadingDialog.dismiss();
                        }

                        @Override
                        public void onNext(BaseResponse<VideoModel> baseResponse) {
                            loadingDialog.dismiss();
                            //Log.d("TAG989",baseResponse.getMsg());
                            if (baseResponse.ret == 200) {
                                //Log.d("TAG888", baseResponse.getData().getPhoto_url() + "777:" + baseResponse.getData().getVideo_url());
                                Intent intent = new Intent(PlayActivity.this, Mine17Activity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("keys", "2");
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                               // MyActivityManager.getInstance().finishAllActivity();
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
                } else if ("3".equals(videoKey)) {
                    Log.d("TAG1111", "1");
                    RetrofitUtil.getInstance().Uploadvideoauth(requestApiKey, requestApiTitle, requestImgPart, requestVideoPart, new Subscriber<BaseResponse<VideoModel>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            loadingDialog.dismiss();
                        }

                        @Override
                        public void onNext(BaseResponse<VideoModel> baseResponse) {
                            loadingDialog.dismiss();
                            if (baseResponse.ret == 200) {
                                //Log.d("TAG888", baseResponse.getData().getPhoto_url() + "777:" + baseResponse.getData().getVideo_url());
                                Intent intent = new Intent(PlayActivity.this, Perfect02Activity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("keys", "5");
                                intent.putExtras(bundle);
                                startActivity(intent);
                                MyActivityManager.getInstance().finishAllActivity();
                            } else {
                                // ToastUtil.showS(PlayActivity.this,baseResponse.getMsg());
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
                break;
        }
    }
}