package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guoqi.actionsheet.ActionSheet;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.BumModel;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.mine.InformationActivity;
import com.wbteam.YYzhiyue.util.StringUtil;
import com.wbteam.YYzhiyue.util.ToastUtil;
import com.wbteam.YYzhiyue.util.photo.PhotoUtils;
import com.wbteam.YYzhiyue.view.RoundImageView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Mine14Activity extends BaseActivity implements ActionSheet.OnActionSheetSelected, EasyPermissions.PermissionCallbacks {
    @BindView(R.id.title01)
    EditText mTitle01;
    @BindView(R.id.editText)
    EditText mEditText;
    @BindView(R.id.iv_picture)
    RoundImageView mIvPicture;
    @BindView(R.id.tv_confirm)
    TextView mTvConfirm;
    private String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine14);
        ButterKnife.bind(this);
        setBackView();
        setTitle("意见反馈");
        PhotoUtils.getInstance().init(this, true, new PhotoUtils.OnSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                // 4、当拍照或从图库选取图片成功后回调
                // mTvPath.setText(outputFile.getAbsolutePath());
                //uploadImage(outputFile.getAbsolutePath());
                // photoPath(outputFile.getAbsolutePath());
                //Log.d("TAG21",outputFile.getAbsolutePath());
                //  LoaddingShow();
                //Glide.with(PostActivity.this).load(outputUri).into(ivAvatar);
                //  uploadImage(outputFile.getAbsolutePath());
                Glide.with(Mine14Activity.this).load(outputUri).into(mIvPicture);
                imgUrl = outputFile.getAbsolutePath();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoUtils.getInstance().bindForResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.tv_confirm, R.id.iv_picture})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_picture:
                ActionSheet.showSheet(this, this, null);
                break;
            case R.id.tv_confirm:
                //onBackPressed();
                String imgUrl1 = imgUrl;
                String title = mTitle01.getText().toString().trim();
                String content = mEditText.getText().toString().trim();
                if (StringUtil.isBlank(imgUrl1) || StringUtil.isBlank(title) || StringUtil.isBlank(content)) {
                    showProgress("请填写完资料");
                    return;
                } else {
                    uploadImage(imgUrl1, title, content);
                    LoaddingShow();
                }
                break;
        }
    }

    public static final String MULTIPART_FORM_DATA = "image/jpg";

    private void uploadImage(String imagePath, String title, String content) {
        Log.d("TAG54546",imagePath);
        File file = new File(imagePath);
        RequestBody requestApiKey = RequestBody.create(MediaType.parse("multipart/form-data"), ukey);
        RequestBody requestApiTitle = RequestBody.create(MediaType.parse("multipart/form-data"), title);
        RequestBody requestApiContent = RequestBody.create(MediaType.parse("multipart/form-data"), content);
        RequestBody requestFile =               // 根据文件格式封装文件
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        MultipartBody.Part requestImgPart =
                MultipartBody.Part.createFormData("fileimg", file.getName(), requestFile);
        RetrofitUtil.getInstance().UserFeedback(requestApiKey, requestApiTitle, requestApiContent, requestImgPart, new Subscriber<BaseResponse<EmptyEntity>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<EmptyEntity> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    ToastUtil.showS(mContext, baseResponse.getMsg());
                    onBackPressed();
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
        if (EasyPermissions.hasPermissions(this, perms)) {//已经有权限了
            switch (requestCode) {
                case 1:
                    PhotoUtils.getInstance().takePhoto();
                    break;
                case 2:
                    PhotoUtils.getInstance().selectPhoto();
                    break;
            }
        } else {//没有权限去请求
            EasyPermissions.requestPermissions(this, "权限", requestCode, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
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
