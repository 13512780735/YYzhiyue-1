package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.guoqi.actionsheet.ActionSheet;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.AuthModel;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.util.StringUtil;
import com.wbteam.YYzhiyue.util.photo.PhotoUtils;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

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

public class Mine08Activity extends BaseActivity implements ActionSheet.OnActionSheetSelected, EasyPermissions.PermissionCallbacks {
    @BindView(R.id.apply_et_name)
    EditText edNmae;
    @BindView(R.id.apply_et_id_card)
    EditText edCard;
    @BindView(R.id.iv_id_card_positive)
    ImageView ivCardPositive;
    @BindView(R.id.iv_id_card_contrary)
    ImageView ivIdCardContrary;
    @BindView(R.id.iv_id_hand_card)
    ImageView ivIdHandCard;

    private int staus;//选择上传图片 1、证明 2、反面 3、手持
    private String imageUrl01;
    private String imageUrl02;
    private String imageUrl03;
    private String name;
    private String idNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine08);
        ButterKnife.bind(this);
        setTitle("实名认证");
        setBackView();
        PhotoUtils.getInstance().init(this, true, new com.wbteam.YYzhiyue.util.photo.PhotoUtils.OnSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                // 4、当拍照或从图库选取图片成功后回调
                Log.d("TAG21", outputFile.getAbsolutePath());
                if (staus == 1) {
                    Glide.with(Mine08Activity.this).load(outputUri).into(ivCardPositive);
                    imageUrl01 = outputFile.getAbsolutePath();
                    //bitmap1=bitmap;
                } else if (staus == 2) {
                    Glide.with(Mine08Activity.this).load(outputUri).into(ivIdCardContrary);
                    imageUrl02 = outputFile.getAbsolutePath();
                } else if (staus == 3) {
                    Glide.with(Mine08Activity.this).load(outputUri).into(ivIdHandCard);
                    imageUrl03 = outputFile.getAbsolutePath();
                }
            }
        });
    }


    @OnClick({R.id.iv_id_card_positive, R.id.iv_id_card_contrary, R.id.iv_id_hand_card, R.id.tv_apply})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_id_card_positive:
                staus = 1;
                ActionSheet.showSheet(this, this, null);
                break;
            case R.id.iv_id_card_contrary:
                staus = 2;
                ActionSheet.showSheet(this, this, null);
                break;
            case R.id.iv_id_hand_card:
                staus = 3;
                ActionSheet.showSheet(this, this, null);
                break;
            case R.id.tv_apply:
                name = edNmae.getText().toString().trim();
                idNum = edCard.getText().toString().trim();
                if (StringUtil.isBlank(imageUrl01) || StringUtil.isBlank(imageUrl02) || StringUtil.isBlank(imageUrl03)) {
                    showProgress("请上传身份证！");
                    return;
                }
                send();
                LoaddingShow();
                break;

        }
    }

    public static final String MULTIPART_FORM_DATA = "image/jpg";

    private void send() {
        RequestBody requestApiKey = RequestBody.create(MediaType.parse("multipart/form-data"), ukey);
        RequestBody requestApiName = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody requestApiId_num = RequestBody.create(MediaType.parse("multipart/form-data"), idNum);
        File file01 = new File(imageUrl01);
        RequestBody requestFile =               // 根据文件格式封装文件
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file01);
        MultipartBody.Part requestImgPart =
                MultipartBody.Part.createFormData("fileimg", file01.getName(), requestFile);
        File file02 = new File(imageUrl02);
        RequestBody requestFile02 =               // 根据文件格式封装文件
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file02);
        MultipartBody.Part requestImgPart02 =
                MultipartBody.Part.createFormData("fileimg2", file02.getName(), requestFile02);
        File file03 = new File(imageUrl03);
        RequestBody requestFile03 =               // 根据文件格式封装文件
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file03);
        MultipartBody.Part requestImgPart03 =
                MultipartBody.Part.createFormData("fileimg3", file03.getName(), requestFile03);

        RetrofitUtil.getInstance().User_Uploadauth(requestApiKey, requestApiName, requestApiId_num, requestImgPart, requestImgPart02, requestImgPart03, new Subscriber<BaseResponse<AuthModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<AuthModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    showToast("上传成功，待审核");
                    onBackPressed();
                } else {
                    showProgress(baseResponse.getMsg());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoUtils.getInstance().bindForResult01(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
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
