package com.wbteam.YYzhiyue.ui.fragment;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guoqi.actionsheet.ActionSheet;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.Entity.UserInfoModel;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.LoginRegisterTabAdapter;
import com.wbteam.YYzhiyue.base.BaseFragment01;
import com.wbteam.YYzhiyue.network.api_service.model.AvatarImageModel;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.mine.MineCenter.MineCenterFragment;
import com.wbteam.YYzhiyue.ui.mine.MineHomeFragment;
import com.wbteam.YYzhiyue.ui.mine.UploadAvatarActivity;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.CircleImageView;
import com.wbteam.YYzhiyue.view.CustomViewPager;
import com.wbteam.YYzhiyue.view.city.CityActivity;
import com.wbteam.YYzhiyue.util.photo.PhotoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class
MineFragment extends BaseFragment01 implements View.OnClickListener {
    private static final int REQUEST_REGION_PICK = 1;//城市返回标识

    private List<String> mDatas;
    private TabLayout mTabLayout;
    private CustomViewPager viewpager;
    private TextView tvLeft, tvRight, tvName, tvGender, tvAccount, tvVip, tvFans, tvGrade;
    private CircleImageView ivAvatar;
    private LinearLayout ll_vip, ll_fans, ll_grade;
    private String city;
    private UserInfoModel mUserInfoModel;
    private String mNickName, headimg;
    private ImageView iv_vip;//vip图标
    private TextView tvVideo;
    private Uri uri;
    private int flag;
    private String permissions;
    private String id;
    private LinearLayout ll_gender;


    @Override
    protected int setContentView() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void lazyLoad() {
        mDatas = new ArrayList<>(Arrays.asList("我的主页", "用户中心"));
        city = UtilPreference.getStringValue(getActivity(), "city");
        permissions = UtilPreference.getStringValue(getActivity(), "permissions");
        initHeaderView();
        initView();
        initData();
        Log.d("TAG12", "555");
//        PhotoUtils.getInstance().init(getActivity(), true, new PhotoUtils.OnSelectListener() {
//            @Override
//            public void onFinish(File outputFile, final Uri outputUri) {
//                    // 4、当拍照或从图库选取图片成功后回调
//                    // mTvPath.setText(outputFile.getAbsolutePath());
//                    uploadImage(outputFile.getAbsolutePath());
//                    Log.d("TAG656", outputFile.getAbsolutePath());
//                    //CircleImageView ivAvatar = (CircleImageView) findViewById(R.id.iv_avatar01);
//                    //uri = outputUri;
//                    LoaddingShow();
//                    //Log.d("TAG212", outputUri + "");
//                   // Glide.with(getActivity()).load(uri).into(ivAvatar);
//            }
//        });
    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
        Log.d("TAG22", "333");
    }

    private void initData() {
        RetrofitUtil.getInstance().getUserGetmy(ukey, new Subscriber<BaseResponse<UserInfoModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
                //showProgress("数据加载失败！");
            }

            @Override
            public void onNext(BaseResponse<UserInfoModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    mUserInfoModel = baseResponse.getData();
                    // mGrade = mUserInfoModel.getInfo().;
                    mNickName = mUserInfoModel.getInfo().getNickname();
                    // mID = mUserInfoModel.getInfo().ge;
                    //mSign = mUserInfoModel.getInfo().ge;
                    headimg = mUserInfoModel.getInfo().getHeadimg();
                    id = mUserInfoModel.getInfo().getUid();
                    UtilPreference.saveString(getActivity(), "headimg", headimg);
                    UtilPreference.saveString(getActivity(), "mNickName", mNickName);
                    //   mUserInfoModel= JSON.parseObject(baseResponse.getData().toString(),UserInfoModel.class);
                } else {
                    if ("Ukey不合法".equals(baseResponse.getMsg())) {
                        showProgress01("您的帐号已在其他设备登录！");
                        return;
                    } else {
                        showProgress(baseResponse.getMsg());
                    }
                }
                ll_gender.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(headimg, ivAvatar);
                tvName.setText(mNickName);
                tvAccount.setText("ID：" + id);
                tvGender.setText(mUserInfoModel.getInfo().getAge());
                Drawable country = getActivity().getResources().getDrawable(R.mipmap.icon_boy);
                country.setBounds(0, 0, country.getMinimumWidth(), country.getMinimumHeight());
                Drawable country1 = getActivity().getResources().getDrawable(R.mipmap.icon_girl);
                country1.setBounds(0, 0, country1.getMinimumWidth(), country1.getMinimumHeight());
                if ("1".equals(mUserInfoModel.getInfo().getSex())) {
                    tvGender.setCompoundDrawables(country, null, null, null);
                } else {
                    tvGender.setCompoundDrawables(country1, null, null, null);
                }
                if ("0".equals(mUserInfoModel.getInfo().getVideoauth())) {
                    tvVideo.setText("【视频未通过】");
                } else if ("1".equals(mUserInfoModel.getInfo().getVideoauth())) {
                    tvVideo.setText("【视频已通过】");
                } else if ("2".equals(mUserInfoModel.getInfo().getVideoauth())) {
                    tvVideo.setText("【视频待审核】");
                } else if ("-1".equals(mUserInfoModel.getInfo().getVideoauth())) {
                    tvVideo.setText("【视频未认证】");
                }
            }
        });
    }

    private void initHeaderView() {
        iv_vip = (ImageView) findViewById(R.id.iv_vip);
        ll_gender = (LinearLayout) findViewById(R.id.ll_gender);
        tvVideo = (TextView) findViewById(R.id.tv_video);
        tvLeft = (TextView) findViewById(R.id.toolbar_left_tv);//定位
        tvRight = (TextView) findViewById(R.id.toolbar_righ_tv);//分享
        ivAvatar = (CircleImageView) findViewById(R.id.iv_avatar01);//头像
        tvName = (TextView) findViewById(R.id.tv_name);//姓名
        tvGender = (TextView) findViewById(R.id.tv_gender);//性别年龄
        tvAccount = (TextView) findViewById(R.id.tv_account);//账号
        ll_vip = (LinearLayout) findViewById(R.id.ll_vip);//会员
        tvVip = (TextView) findViewById(R.id.tv_vip);//会员数
        ll_fans = (LinearLayout) findViewById(R.id.ll_fans);//粉丝
        tvFans = (TextView) findViewById(R.id.tv_fans);//粉丝
        ll_grade = (LinearLayout) findViewById(R.id.ll_grade);//等级
        tvGrade = (TextView) findViewById(R.id.tv_grade);//等级
        tvLeft.setText(city);
        tvName.setText("加载中");
        tvVip.setText("");
        tvFans.setText("");
        tvGrade.setText("");
        if ("1".equals(isvip)) {
            iv_vip.setVisibility(View.VISIBLE);
        } else {
            iv_vip.setVisibility(View.GONE);
        }
        initListener();

    }

    private void initListener() {
        tvLeft.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        ll_vip.setOnClickListener(this);
        ll_fans.setOnClickListener(this);
        ll_grade.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
    }

    private void initView() {

        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        viewpager = (CustomViewPager) findViewById(R.id.viewpager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(viewpager);
        List<Fragment> mfragments = new ArrayList<>();
        mfragments.add(new MineHomeFragment());
        mfragments.add(new MineCenterFragment());
        viewpager.setAdapter(new LoginRegisterTabAdapter(getChildFragmentManager(), mfragments, mDatas));
        viewpager.setCurrentItem(0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_tv:
                Intent intent = new Intent(getActivity(), CityActivity.class);
                startActivityForResult(intent, REQUEST_REGION_PICK);
                break;
            case R.id.toolbar_righ_tv:
                break;
            case R.id.ll_vip:
                break;
            case R.id.ll_fans:
                break;
            case R.id.ll_grade:
                break;
            case R.id.iv_avatar01:
//                flag = 1;
//                ActionSheet.showSheet(getActivity(), this, null);
                toActivityFinish(UploadAvatarActivity.class);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_REGION_PICK:
                    city = data.getStringExtra("date");
                    tvLeft.setText(city);
                    UtilPreference.saveString(getActivity(), "city", city);
                    break;
            }

        }
        //PhotoUtils.getInstance().bindForResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


}
