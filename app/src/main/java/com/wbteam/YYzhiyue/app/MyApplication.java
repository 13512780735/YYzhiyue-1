package com.wbteam.YYzhiyue.app;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.util.Log;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.igexin.sdk.PushManager;
import com.mob.MobSDK;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.smtt.sdk.QbSdk;
import com.wbteam.YYzhiyue.R;

import java.io.File;


/**
 * Created by Administrator on 2018/1/17.
 */

public class MyApplication extends Application {

    public static String VIDEO_PATH = Environment.getExternalStorageDirectory().getPath();
    public static MyApplication mContext;
    private static MyApplication instance;
    public static Context applicationContext;

    public static Context getAppContext() {
        return instance == null ? null : instance.getApplicationContext();
    }

    public static MyApplication getInstance() {
        if (mContext == null) {
            return new MyApplication();
        } else {
            return mContext;
        }
    }

    @Override
    public void onCreate() {
        // MultiDex.install(this);
        super.onCreate();
        VIDEO_PATH += String.valueOf(System.currentTimeMillis());
        File file = new File(VIDEO_PATH);
        if (!file.exists()) file.mkdirs();
        //设置视频缓存路径
        //VCamera.setVideoCachePath(VIDEO_PATH);
//设置拍摄视频缓存路径
//        if (DeviceUtils.isZte()) {
//            if (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).exists()) {
//                VCamera.setVideoCachePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Camera/VCameraDemo/");
//            } else {
//                VCamera.setVideoCachePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath().replace("/sdcard/", "/sdcard-ext/") + "/Camera/VCameraDemo/");
//            }
//        } else {
//            VCamera.setVideoCachePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Camera/VCameraDemo/");
//        }
        // 开启log输出,ffmpeg输出到logcat
        //  VCamera.setDebugMode(true);

        // 初始化拍摄SDK，必须
        //VCamera.initialize(this);
        initImageLoad();
        mContext = this;
        instance = this;
        applicationContext = this;
        // DemoHelper.getInstance().init(mContext);
        initHuanXin();
        PushManager.getInstance().initialize(this.getApplicationContext(), com.wbteam.YYzhiyue.service.DemoPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), com.wbteam.YYzhiyue.service.DemoIntentService.class);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        PushManager.getInstance().turnOnPush(this);
        //  LeakCanary.install(this);
        initX5WebView();
        MobSDK.init(this);

    }

    public static MyApplication getInstance(Context appContext) {
        return instance;
    }

    public static String currentUserNick = "";

    private void initHuanXin() {
        EMOptions options = new EMOptions();
////// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // EaseUI.getInstance().init(mContext,options);
        // EMClient.getInstance().setDebugMode(true);
        // DemoHelper.getInstance().init(applicationContext);
        EaseUI.getInstance().init(mContext, options);
    }

    private void initX5WebView() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    private void initImageLoad() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.icon_default_picture)
                .showImageOnFail(R.mipmap.icon_default_picture)
                .cacheInMemory(true).cacheOnDisc(true).build();
        // 图片加载工具配置
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .discCacheSize(50 * 1024 * 1024)//
                .discCacheFileCount(100)// 缓存一百张图片
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //  MultiDex.install(this);
    }
}
