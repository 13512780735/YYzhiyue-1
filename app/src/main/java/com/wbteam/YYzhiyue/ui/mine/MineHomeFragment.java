package com.wbteam.YYzhiyue.ui.mine;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guoqi.actionsheet.ActionSheet;
import com.wbteam.YYzhiyue.Entity.UserInfoModel;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.mine.MineGalleryAdapter;
import com.wbteam.YYzhiyue.adapter.mine.MineOnlookersAdapter;
import com.wbteam.YYzhiyue.adapter.mine.MineTrendsAdapter;
import com.wbteam.YYzhiyue.base.BaseFragment01;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.DatingInfoModel;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.model.GalleryListModel;
import com.wbteam.YYzhiyue.network.api_service.model.GalleryModel;
import com.wbteam.YYzhiyue.network.api_service.model.TagModel;
import com.wbteam.YYzhiyue.network.api_service.model.WeiboListModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.neaeby.GalleryListActivity;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.util.photo.PhotoUtils;
import com.wbteam.YYzhiyue.view.LoadingDialog;
import com.wbteam.YYzhiyue.view.MyGridView;
import com.wbteam.YYzhiyue.view.custom_scrollview.HorizontalPageLayoutManager;
import com.wbteam.YYzhiyue.view.custom_scrollview.MyRecyclerView;
import com.wbteam.YYzhiyue.view.custom_scrollview.PagingScrollHelper;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
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
public class MineHomeFragment extends BaseFragment01 implements View.OnClickListener, ActionSheet.OnActionSheetSelected, EasyPermissions.PermissionCallbacks {

    PagingScrollHelper scrollHelper = new PagingScrollHelper();
    PagingScrollHelper scrollHelper01 = new PagingScrollHelper();
    private HorizontalPageLayoutManager horizontalPageLayoutManager = null;
    private MyGridView mGridView;
    private MineOnlookersAdapter mAdapter;
    private HorizontalPageLayoutManager horizontalPageLayoutManager01 = null;
    private RecyclerView mRecyclerView01;
    private ImageView ivHeader;
    private MineTrendsAdapter mAdapter01;
    private TextView tvScopeEdit;
    private TextView tvInformationEdit;
    private TextView tvScope;
    private List<TagModel.ListBean> dataTag;
    private UserInfoModel mUserInfoModel;
    private String mGrade = null;
    private String mNickName = null;
    private String mID = null;
    private String mBirthday = null;
    private String mAge = null;
    private String mPhone = null;
    private String mSign = null;
    private String mHeight = null;
    private String mWeight = null;
    private String mIncome = null;
    private String mAppearance = null;
    private String mEmotion = null;
    private String mAddress = null;
    private String mIntroduce = null;
    private String mJob = null;
    private String mSex = "1";
    private TextView tvConstellation, tvPricing, tvJob, tvDiaries, tvHeight, tvWeixin, tvPhone, tvSignature;
    private String astro = null;
    private String price = null;
    private String job = null;
    private String weixin = null;
    private String mStartTime;
    private String mEndTime;
    private LoadingDialog loading;
    private ImageView ivHeader01;
    private MyRecyclerView mRecyclerView02;
    private HorizontalPageLayoutManager horizontalPageLayoutManager02;
    private MineGalleryAdapter mAdapter02;
    private String invitation_code;
    private TextView tv_move_phot;


    @Override
    protected int setContentView() {
        return R.layout.fragment_mine_home;
    }

    @Override
    protected void lazyLoad() {

        dataTag = new ArrayList<>();

        initView();
        getInformation();
        initDate(1, false);//动态
        initDate1(1, false);//相册
        //initDate();
        LoaddingShow();
        PhotoUtils.getInstance().init(getActivity(), true, new PhotoUtils.OnSelectListener() {
            @Override
            public void onFinish(File outputFile, final Uri outputUri) {
                // 4、当拍照或从图库选取图片成功后回调
                // mTvPath.setText(outputFile.getAbsolutePath());
                uploadImage(outputFile.getAbsolutePath());
                LoaddingShow();
                Log.d("TAG666", outputFile.getAbsolutePath());
                //    CircleImageView ivAvatar = (CircleImageView) findViewById(R.id.iv_avatar01);
                // uri = outputUri;
//                Log.d("TAG212", outputUri + "");
                //  Glide.with(getActivity()).load(uri).into(ivAvatar);
            }
        });
    }

    public static final String MULTIPART_FORM_DATA = "image/jpg";

    private void uploadImage(String absolutePath) {
        File file = new File(absolutePath);
        RequestBody requestApiKey = RequestBody.create(MediaType.parse("multipart/form-data"), ukey);
        //RequestBody requestImgFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody requestFile =               // 根据文件格式封装文件
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        MultipartBody.Part requestImgPart =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RetrofitUtil.getInstance().Uploadalbum(requestApiKey, requestImgPart, new Subscriber<BaseResponse<GalleryModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
                Log.e(TAG, "6565" + e.toString());
            }

            @Override
            public void onNext(BaseResponse<GalleryModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    Log.d(TAG, "6565" + "222");
                    //Log.d("TAG434",baseResponse.getData().getUrl());
                    //  String imgid = baseResponse.getData().getPid();
                    //   Log.d(TAG, imgid);
                    // imgId += imgid + ",";
                    initDate1(1, false);
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

    private List<GalleryListModel.ListBean> data1 = new ArrayList<>();//相册
    List<DatingInfoModel.PicsBean> dataPics;

    private void initDate1(int pageNum, final boolean isloadmore) {
        RetrofitUtil.getInstance().Getgallerylist(ukey, new Subscriber<BaseResponse<GalleryListModel>>() {
            @Override

            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                // loading.dismiss();
            }

            @Override
            public void onNext(BaseResponse<GalleryListModel> baseResponse) {
                //loading.dismiss();
                Log.d("TAG", baseResponse.ret + "");
                if (baseResponse.ret == 200) {
                    GalleryListModel galleryListModel = baseResponse.getData();
                    List<GalleryListModel.ListBean> list1 = galleryListModel.getList();
                    //  Log.d("TAG", weiboListModel.getList().get(0).getUser().getHeadimg());
                    if (list1 != null && list1.size() > 0) {
                        if (!isloadmore) {
                            data1 = list1;
                        } else {
                            data1.addAll(list1);
                        }
                        dataPics = new ArrayList<>();
                        Log.d("TAG212", data1.get(0).getPic().get(0).getPath());
                        for (int i = 0; i < data1.size(); i++) {
                            Log.d("TAG2122", data1.get(i).getPic().get(0).getPath());
                            DatingInfoModel.PicsBean bean = new DatingInfoModel.PicsBean();
                            bean.setId(data1.get(i).getPic().get(0).getId());
                            bean.setPath(data1.get(i).getPic().get(0).getPath());
                            dataPics.add(bean);
                        }
                        mAdapter02.setNewData(data1);
                        mAdapter02.notifyDataSetChanged();
                    }
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

    @Override
    public void onResume() {
        super.onResume();
        if (dataTag.size() > 0) {
            dataTag.clear();
        }
        getTage();
        getInformation();
        //initDate1(1, false);
    }

    private List<WeiboListModel.ListBean> data = new ArrayList<>();

    private void initDate(int pageNum, final boolean isloadmore) {
        RetrofitUtil.getInstance().GetMyweibolist(ukey, new Subscriber<BaseResponse<WeiboListModel>>() {
            @Override

            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                // loading.dismiss();
            }

            @Override
            public void onNext(BaseResponse<WeiboListModel> baseResponse) {
                //loading.dismiss();
                Log.d("TAG", baseResponse.ret + "");
                if (baseResponse.ret == 200) {
                    WeiboListModel weiboListModel = baseResponse.getData();
                    List<WeiboListModel.ListBean> list = weiboListModel.getList();
                    Log.d("TAG", weiboListModel.getList().get(0).getUser().getHeadimg());
                    if (list != null && list.size() > 0) {
                        if (!isloadmore) {
                            data = list;
                        } else {
                            data.addAll(list);
                        }
                        mAdapter01.setNewData(data);
                        mAdapter01.notifyDataSetChanged();
                    }
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

    private void getInformation() {
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
                    mUserInfoModel = baseResponse.getData();
                    // mGrade = mUserInfoModel.getInfo().;
                    mNickName = mUserInfoModel.getInfo().getNickname();
                    // mID = mUserInfoModel.getInfo().ge;
                    mBirthday = mUserInfoModel.getInfo().getBirthday();
                    mAge = mUserInfoModel.getInfo().getAge();
                    price = mUserInfoModel.getInfo().getPrice();
                    job = mUserInfoModel.getInfo().getJob();
                    //  mPhone = mUserInfoModel.getInfo().ge;
                    //mSign = mUserInfoModel.getInfo().ge;
                    mHeight = mUserInfoModel.getInfo().getHeight();
                    mIncome = mUserInfoModel.getInfo().getWeight();
                    weixin = mUserInfoModel.getInfo().getWeixin();
                    mAppearance = mUserInfoModel.getInfo().getAppearance();
                    mEmotion = mUserInfoModel.getInfo().getEmotion();
                    // mAddress = mUserInfoModel.getInfo().ge;
                    mIntroduce = mUserInfoModel.getInfo().getDescription();
                    mStartTime = mUserInfoModel.getInfo().getStart_time();
                    mEndTime = mUserInfoModel.getInfo().getEnd_time();
                    mSex = mUserInfoModel.getInfo().getSex();
                    mPhone = mUserInfoModel.getInfo().getContact();
                    astro = mUserInfoModel.getInfo().getAstro();
                    invitation_code = mUserInfoModel.getInfo().getInvitation_code();
                    UtilPreference.saveString(getActivity(), "invitation_code", invitation_code);
                    //   mUserInfoModel= JSON.parseObject(baseResponse.getData().toString(),UserInfoModel.class);
                } else {
                    if ("Ukey不合法".equals(baseResponse.getMsg())) {
                        showProgress01("您的帐号已在其他设备登录！");
                        return;
                    } else {
                        showProgress(baseResponse.getMsg());
                    }
                }
                tvConstellation.setText(astro);
                tvPricing.setText(price);
                tvJob.setText(job);
                tvDiaries.setText(mStartTime + "-" + mEndTime);
                tvHeight.setText(mHeight);
                tvWeixin.setText(weixin);
                tvPhone.setText(mPhone);
                tvSignature.setText(mNickName);
            }
        });
    }

    private void getTage() {
        RetrofitUtil.getInstance().getSetusertag(ukey, new Subscriber<BaseResponse<TagModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<TagModel> baseResponse) {
                if (baseResponse.ret == 200) {
                    Log.d("TAG", baseResponse.getData().getList().toString());
                    TagModel tagModel = baseResponse.getData();
                    for (int i = 0; i < tagModel.getList().size(); i++) {
                        TagModel.ListBean mListBean = new TagModel.ListBean();
                        mListBean.setId(tagModel.getList().get(i).getId());
                        mListBean.setTitle(tagModel.getList().get(i).getTitle());
                        dataTag.add(mListBean);
                    }
                    mAdapter = new MineOnlookersAdapter(getActivity(), dataTag);
                    mGridView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
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

    private void initView() {
        tv_move_phot = findViewById(R.id.tv_move_photo);
        tvScopeEdit = (TextView) findViewById(R.id.tv_scopeEdit);//出租范围编辑
        tvInformationEdit = (TextView) findViewById(R.id.tv_informationEdit);//个人资料编辑
        tvConstellation = (TextView) findViewById(R.id.tv_constellation);// 星座
        tvPricing = (TextView) findViewById(R.id.tv_pricing);// 定价
        tvJob = (TextView) findViewById(R.id.tv_job);// 职业
        tvDiaries = (TextView) findViewById(R.id.tv_diaries);// 档期
        tvHeight = (TextView) findViewById(R.id.tv_height);// 身高
        tvWeixin = (TextView) findViewById(R.id.tv_weixin);// 微信号
        tvPhone = (TextView) findViewById(R.id.tv_phone);// 手机号
        tvSignature = (TextView) findViewById(R.id.tv_signature);// 签名

        horizontalPageLayoutManager = new HorizontalPageLayoutManager(1, 5);
        tvScopeEdit.setOnClickListener(this);
        tv_move_phot.setOnClickListener(this);
        tvInformationEdit.setOnClickListener(this);
        mGridView = (MyGridView) findViewById(R.id.RecyclerView);

        //  动态
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.mine_griview_header_view, null);
        ivHeader = (ImageView) header.findViewById(R.id.iv_avatar);
        mRecyclerView01 = (MyRecyclerView) findViewById(R.id.RecyclerView01);
        horizontalPageLayoutManager01 = new HorizontalPageLayoutManager(2, 3);
        mAdapter01 = new MineTrendsAdapter(R.layout.post_trends_image_items, data);

        mRecyclerView01.setAdapter(mAdapter01);
        scrollHelper.setUpRecycleView(mRecyclerView01);
        RecyclerView.LayoutManager layoutManager = null;
        layoutManager = horizontalPageLayoutManager01;
        if (layoutManager != null) {
            mRecyclerView01.setLayoutManager(layoutManager);
            scrollHelper.updateLayoutManger();
        }
        mAdapter01.addHeaderView(header);

        ivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("postKey", "one");
                toActivity(PostActivity.class, bundle);
            }
        });
        //相册
        View header01 = LayoutInflater.from(getActivity()).inflate(R.layout.mine_griview_add_photo_view, null);
        ivHeader01 = (ImageView) header01.findViewById(R.id.iv_avatar);
        mRecyclerView02 = (MyRecyclerView) findViewById(R.id.RecyclerView02);
        horizontalPageLayoutManager02 = new HorizontalPageLayoutManager(2, 3);
        mAdapter02 = new MineGalleryAdapter(R.layout.mine_gallery_list_view, data1);

        mRecyclerView02.setAdapter(mAdapter02);
        scrollHelper01.setUpRecycleView(mRecyclerView02);
        RecyclerView.LayoutManager layoutManager01 = null;
        layoutManager01 = horizontalPageLayoutManager02;
        if (layoutManager01 != null) {
            mRecyclerView02.setLayoutManager(layoutManager01);
            scrollHelper01.updateLayoutManger();
        }
        mAdapter02.addHeaderView(header01);

        ivHeader01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionSheet.showSheet(getActivity(), MineHomeFragment.this, null);
            }
        });
        mAdapter02.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.bt_del:
                        String pid = data1.get(position).getId();
                        data1.remove(position);
                        delPhoto(pid);
                        break;
                }
            }
        });
    }

    private void delPhoto(String pid) {
        RetrofitUtil.getInstance().Gallery_Delalbum(ukey, pid, new Subscriber<BaseResponse<EmptyEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<EmptyEntity> baseResponse) {
                if (baseResponse.ret == 200) {
                    showProgress("删除成功!");
                    //
                    onrefresh();
                } else {
                    showProgress(baseResponse.getMsg());
                }
            }
        });
    }

    private void onrefresh() {
        initDate1(1, false);
        mAdapter02.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_scopeEdit:
                toActivity(ScopeActivity.class);
                break;
            case R.id.tv_informationEdit:
                toActivity(InformationActivity.class);
                getActivity().finish();
                break;
            case R.id.tv_move_photo:
                if (data1 != null && data1.size() > 0) {
                    toGalleryList();
                } else {
                    return;
                }
                break;
        }
    }

    private void toGalleryList() {
        Bundle bundle = new Bundle();
        bundle.putString("nickname", mNickName);
        bundle.putSerializable("pics", (Serializable) dataPics);
        toActivity(GalleryListActivity.class, bundle);
    }

    String[] takePhotoPerms = {READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, CAMERA};
    String[] selectPhotoPerms = {READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};

    @Override
    public void onClick(int whichButton) {
        switch (whichButton) {
            case ActionSheet.CHOOSE_PICTURE:
                //相册
                checkPermission(selectPhotoPerms, 2);
                break;
            case ActionSheet.TAKE_PICTURE:
                //拍照
                checkPermission(takePhotoPerms, 1);
                break;
            case ActionSheet.CANCEL:
                //取消
                break;
        }
    }

    private void checkPermission(String[] perms, int requestCode) {
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {//已经有权限了
            switch (requestCode) {
                case 1:
                    PhotoUtils.getInstance().takePhoto();
                    break;
                case 2:
                    PhotoUtils.getInstance().selectPhoto();
                    break;
            }
        } else {//没有权限去请求
            EasyPermissions.requestPermissions(getActivity(), "权限", requestCode, perms);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoUtils.getInstance().bindForResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {//设置成功
        switch (requestCode) {
            case 1:
                PhotoUtils.getInstance().takePhoto();
                break;
            case 2:
                PhotoUtils.getInstance().selectPhoto();
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle("权限设置")
                    .setPositiveButton("设置")
                    .setRationale("当前应用缺少必要权限,可能会造成部分功能受影响！请点击\"设置\"-\"权限\"-打开所需权限。最后点击两次后退按钮，即可返回")
                    .build()
                    .show();
        }
    }
}
