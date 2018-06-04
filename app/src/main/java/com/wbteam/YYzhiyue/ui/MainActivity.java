package com.wbteam.YYzhiyue.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.wbteam.YYzhiyue.Entity.UserInfoModel;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.HomeViewPagerAdapter;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.util.photo.PhotoUtils;
import com.wbteam.YYzhiyue.view.NoScrollViewPager;
import com.wbteam.YYzhiyue.view.tab.BottomTabBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;

public class MainActivity extends BaseActivity implements BottomTabBar.OnSelectListener, ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    private AMapLocationClientOption mLocationOption;
    private AMapLocationClient mLocationClient;
    public static NoScrollViewPager mViewPager;
    @BindView(R.id.rgTools)
    RadioGroup mRgTools;

    BottomTabBar tb;
    private Context mContext;
    String TAG = "MainActivity";
    private String keys;
    private HomeViewPagerAdapter adapter;


    /**
     * 头像获取
     */
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        mContext = this;
        ButterKnife.bind(this);
        keys = getIntent().getExtras().getString("keys");
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = width / 2 - 20;
        UtilPreference.saveInt(mContext, "width", width);
        UtilPreference.saveInt(mContext, "height", height);
        Log.d("TAG232", height + "+" + width);
        addGeoFence();
        initUserInfo();
        initView();
        if ("1".equals(keys)) {
            mViewPager.setCurrentItem(4);
        } else if ("2".equals(keys)) {
            mViewPager.setCurrentItem(0);
        } else if ("3".equals(keys)) {
            mViewPager.setCurrentItem(2);
        }

    }

    private void initUserInfo() {
        RetrofitUtil.getInstance().getUserGetmy(ukey, new Subscriber<BaseResponse<UserInfoModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<UserInfoModel> baseResponse) {
                LoaddingDismiss();


                if (baseResponse.ret == 200) {
                    Log.d("TAG21", baseResponse.getData().getInfo().getHeadimg());
                    Log.e(TAG, baseResponse.getData().getInfo().getExist_parent());
                    UtilPreference.saveString(MainActivity.this, "videoauth", baseResponse.getData().getInfo().getVideoauth());
                    //   mUserInfoModel= JSON.parseObject(baseResponse.getData().toString(),UserInfoModel.class);
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
    }

    public Uri getTitles() {
        return uri;
    }

    private void initView() {
        mViewPager = (NoScrollViewPager) findViewById(R.id.home_viewpager);
        adapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        mViewPager.setOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(2);
        mRgTools.setOnCheckedChangeListener(this);
    }

    private void addGeoFence() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。
                        double latitude = aMapLocation.getLatitude();//获取纬度
                        double longitude = aMapLocation.getLongitude();//获取经度

                        String city = aMapLocation.getCity();
                        Log.d(TAG, city);
                        UtilPreference.saveString(mContext, "lat", String.valueOf(latitude));
                        UtilPreference.saveString(mContext, "lon", String.valueOf(longitude));
                        UtilPreference.saveString(mContext, "city", city);
                        Log.d("TAG989", city + String.valueOf(longitude) + String.valueOf(latitude) + aMapLocation.getProvince());
                        upLoadLocation(latitude, longitude);
                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        UtilPreference.saveString(mContext, "city", "中山");
                        Log.e("AmapError", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        });
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(100000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private void upLoadLocation(double latitude, double longitude) {
        RetrofitUtil.getInstance().getUserUpdategps(ukey, String.valueOf(longitude), String.valueOf(latitude), new Subscriber<BaseResponse<EmptyEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<EmptyEntity> baseResponse) {
                if (baseResponse.ret == 200) {

                } else {
//                    if ("Ukey不合法".equals(baseResponse.getMsg())) {
//                        showProgress01("您的帐号已在其他设备登录！");
//                        return;
//                    } else {
                    showProgress(baseResponse.getMsg());
                }
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mRgTools.check(mRgTools.getChildAt(position).getId());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rbnearby:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.rbappointment:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.rbreward:
                mViewPager.setCurrentItem(2);
                //  badgeView.setVisibility(View.GONE);
                break;
            case R.id.rbMessage:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.rbMe:
                mViewPager.setCurrentItem(4);
                break;

        }
    }

    @Override
    public void onSelect(int position) {

    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                    return true;
                } else {
                    //MyActivityManager.getInstance().moveTaskToBack(mContext);// 不退出，后台运行
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoUtils.getInstance().bindForResult(requestCode, resultCode, data);
    }


}
