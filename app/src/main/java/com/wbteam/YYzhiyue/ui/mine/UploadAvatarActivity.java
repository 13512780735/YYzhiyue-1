package com.wbteam.YYzhiyue.ui.mine;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guoqi.actionsheet.ActionSheet;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.AvatarImageModel;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.MainActivity;
import com.wbteam.YYzhiyue.ui.mine.MineCenter.Mine16Activity;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.util.photo.PhotoUtils;
import com.wbteam.YYzhiyue.view.CircleImageView;
import com.wbteam.YYzhiyue.view.CustomDialog;

import java.io.File;
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

public class UploadAvatarActivity extends BaseActivity implements ActionSheet.OnActionSheetSelected, EasyPermissions.PermissionCallbacks, View.OnClickListener {
    CircleImageView head_img;
    private TextView tv_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_avatar);
        tv_pic = (TextView) findViewById(R.id.tv_pic);
        head_img = (CircleImageView) findViewById(R.id.head_img);
        setBackView();
        setTitle("上传头像");
        PhotoUtils.getInstance().init(this, true, new PhotoUtils.OnSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                // 4、当拍照或从图库选取图片成功后回调
                // mTvPath.setText(outputFile.getAbsolutePath());
                uploadImage(outputFile.getAbsolutePath());
                Log.d("TAG21", outputFile.getAbsolutePath());
                LoaddingShow();
                Glide.with(UploadAvatarActivity.this).load(outputUri).into(head_img);
            }
        });
        tv_pic.setOnClickListener(this);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pic:
                ActionSheet.showSheet(this, this, null);
                break;
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoUtils.getInstance().bindForResult(requestCode, resultCode, data);

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
    public void onPermissionsGranted(int requestCode, List<String> perms) {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public static final String MULTIPART_FORM_DATA = "image/jpg";

    private void uploadImage(String imagePath) {
        final File file = new File(imagePath);
        RequestBody requestApiKey = RequestBody.create(MediaType.parse("multipart/form-data"), ukey);
        RequestBody requestFile =               // 根据文件格式封装文件
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        MultipartBody.Part requestImgPart =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RetrofitUtil.getInstance().getUploadhead(requestApiKey, requestImgPart, new Subscriber<BaseResponse<AvatarImageModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
                //Log.e(TAG, "6565" + e.toString());
            }

            @Override
            public void onNext(BaseResponse<AvatarImageModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    // showProgress("上传头像成功!");
                    // onBackPressed();
                    Bundle bundle = new Bundle();
                    bundle.putString("keys", "1");
                    toActivity(MainActivity.class, bundle);
                    finish();
//                    customDialog.setNegativeButton("取消", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                        }
//                    });
//                    customDialog.setPositiveButton("去视频认证", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            customDialog.dismiss();
//                            UtilPreference.saveString(UploadAvatarActivity.this, "videoKey", "3");
//                            toActivity(Mine16Activity.class);
//                            finish();
//                        }
//                    });
//                    customDialog.show();
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

}
