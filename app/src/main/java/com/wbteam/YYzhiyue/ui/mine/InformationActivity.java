package com.wbteam.YYzhiyue.ui.mine;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.guoqi.actionsheet.ActionSheet;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.Entity.UserInfoModel;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.AstroModel;
import com.wbteam.YYzhiyue.network.api_service.model.AvatarImageModel;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.MainActivity;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.util.photo.PhotoUtils;
import com.wbteam.YYzhiyue.view.CircleImageView;
import com.wbteam.YYzhiyue.view.city.CityActivity;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class InformationActivity extends BaseActivity implements View.OnClickListener, ActionSheet.OnActionSheetSelected, EasyPermissions.PermissionCallbacks {
    private static final int REQUEST_REGION_PICK = 3;//城市返回标识
    private UserInfoModel mUserInfoModel = null;
    private String TAG = "InformationActivity";
    private CircleImageView ivAvatar;
    private TextView tvConfirm, tvGrade, tvID, tvBirthday, tvAge, tvSign, tvEmotion, tvAddress, tvSex;
    private EditText edNickName, edphone, edHeight, edWeight, edAppearance, edJob, tvIncome, edIntroduce;
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

    /**
     * 日期
     */
    final int DATE_DIALOG = 1;
    int mYear, mMonth, mDay;
    private String astro;
    private String city;
    private String cityId;
    private String headimg;
    private String Time01;
    private LinearLayout rl_rlinvitation;
    private EditText ed_edinvitation;
    private String invite = "";
    private String exist_parent;
    private TextView tvRoomLength;
    private LinearLayout back_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mContext = this;
        ButterKnife.bind(this);
     //  setBackView();
        initView();
        initData();
        LoaddingShow();
        PhotoUtils.getInstance().init(this, true, new PhotoUtils.OnSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                // 4、当拍照或从图库选取图片成功后回调
                // mTvPath.setText(outputFile.getAbsolutePath());
                uploadImage(outputFile.getAbsolutePath());
                Log.d("TAG555", outputFile.getAbsolutePath());
                LoaddingShow();
                Glide.with(InformationActivity.this).load(outputUri).into(ivAvatar);
            }
        });
    }


    private void initView() {

        tvConfirm = (TextView) findViewById(R.id.tv_confirm);//保存
        tvGrade = (TextView) findViewById(R.id.tv_tvGrade);//等级
        tvSex = (TextView) findViewById(R.id.tv_tvSex);//性别
        ivAvatar = (CircleImageView) findViewById(R.id.iv_avatar);//头像
        edNickName = (EditText) findViewById(R.id.ed_ednickName);//昵称
        tvID = (TextView) findViewById(R.id.tv_tvID);//ID
        tvBirthday = (TextView) findViewById(R.id.tv_tvbirthday);//生日
        tvAge = (TextView) findViewById(R.id.ed_edage);//年龄
        edphone = (EditText) findViewById(R.id.ed_edphone);//手机
        tvSign = (TextView) findViewById(R.id.tv_tvSign);//星座
        edHeight = (EditText) findViewById(R.id.ed_edheight);//身高
        edWeight = (EditText) findViewById(R.id.ed_edweight);//体重
        edAppearance = (EditText) findViewById(R.id.ed_edAppearance);//外貌
        edJob = (EditText) findViewById(R.id.ed_edjob);//职业
        tvIncome = (EditText) findViewById(R.id.ed_edincome);//收入
        tvEmotion = (TextView) findViewById(R.id.tv_tvEmotion);//情感
        tvAddress = (TextView) findViewById(R.id.tv_tvAddress);//出没城市
        edIntroduce = (EditText) findViewById(R.id.ed_edintroduce);//自我介绍
        rl_rlinvitation = (LinearLayout) findViewById(R.id.rl_rlinvitation);
        ed_edinvitation = (EditText) findViewById(R.id.ed_edinvitation);
        tvRoomLength = (TextView) findViewById(R.id.tv_room_length);
        back_view=(LinearLayout)findViewById(R.id.back_view);
        back_view.setVisibility(View.VISIBLE);
        initListener();

    }

    private void initListener() {
        tvConfirm.setOnClickListener(this);
        tvGrade.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
        tvBirthday.setOnClickListener(this);
        tvIncome.setOnClickListener(this);
        tvEmotion.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        tvSex.setOnClickListener(this);
        back_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("keys", "1");
                toActivity(MainActivity.class, bundle);
                finish();
            }
        });
        edIntroduce.addTextChangedListener(mTextWatcher);
    }
//    protected void onPause() {
//        super.onPause();
//        overridePendingTransition(R.anim.in_from_left,
//               0);
//
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle bundle = new Bundle();
        bundle.putString("keys", "1");
        toActivity(MainActivity.class, bundle);
        finish();
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;
        int num=30;
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            temp = s;
            Editable editable = edIntroduce.getText();
            int len = editable.length();

            if (len > num) {
                showProgress("你输入的字数已经超过了限制！");
                int selEndIndex = Selection.getSelectionEnd(editable);
                String str = editable.toString();
                //截取新字符串
                String newStr = str.substring(0, num);
                edIntroduce.setText(newStr);
                editable = edIntroduce.getText();

                //新字符串的长度
                int newLen = editable.length();
                //旧光标位置超过字符串长度
                if (selEndIndex > newLen) {
                    selEndIndex = editable.length();
                }
                //设置新光标所在的位置
                Selection.setSelection(editable, selEndIndex);
            }

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
//          mTextView.setText(s);//将输入的内容实时显示
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
//            editStart = edIntroduce.getSelectionStart();
//            editEnd = edIntroduce.getSelectionEnd();
            tvRoomLength.setText("" + temp.length());
//            if (temp.length() > 30) {
////                Toast.makeText(mContext,
////                        "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT)
////                        .show();
//                showProgress("你输入的字数已经超过了限制！");
//                s.delete(editStart - 1, editEnd);
//                int tempSelection = editStart;
//                edIntroduce.setText(s);
//                edIntroduce.setSelection(tempSelection);
//            }
        }
    };

    private void initData() {
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
                    mUserInfoModel = baseResponse.getData();
                    // mGrade = mUserInfoModel.getInfo().;
                    mNickName = mUserInfoModel.getInfo().getNickname();
                    // mID = mUserInfoModel.getInfo().ge;
                    mBirthday = mUserInfoModel.getInfo().getBirthday();
                    Time01 = mBirthday;
                    mAge = mUserInfoModel.getInfo().getAge();
                    mPhone = mUserInfoModel.getInfo().getMobile();
                    mAddress = mUserInfoModel.getInfo().getCityname();
                    //mSign = mUserInfoModel.getInfo().ge;
                    headimg = mUserInfoModel.getInfo().getHeadimg();
                    mHeight = mUserInfoModel.getInfo().getHeight();
                    mWeight = mUserInfoModel.getInfo().getWeight();
                    mIncome = mUserInfoModel.getInfo().getIncome();
                    mAppearance = mUserInfoModel.getInfo().getAppearance();
                    astro = mUserInfoModel.getInfo().getAstro();
                    mEmotion = mUserInfoModel.getInfo().getEmotion();
                    // mAddress = mUserInfoModel.getInfo().ge;
                    mIntroduce = mUserInfoModel.getInfo().getDescription();
                    mSex = mUserInfoModel.getInfo().getSex();
                    cityId = mUserInfoModel.getInfo().getCityid();
                    mJob = mUserInfoModel.getInfo().getJob();
                    mSign = mUserInfoModel.getInfo().getAstro();
                    exist_parent = mUserInfoModel.getInfo().getExist_parent();
                    //   mUserInfoModel= JSON.parseObject(baseResponse.getData().toString(),UserInfoModel.class);
                } else {
                    if ("Ukey不合法".equals(baseResponse.getMsg())) {
                        showProgress01("您的帐号已在其他设备登录！");
                        return;
                    } else {
                        showProgress(baseResponse.getMsg());
                    }
                }
                setTitle(mNickName);
                tvGrade.setText(mGrade);
                tvID.setText(mID);
                tvBirthday.setText(mBirthday);
                tvAge.setText(mAge);
                tvSign.setText(mSign);
                tvIncome.setText(mIncome);
                tvAddress.setText(mAddress);
                tvEmotion.setText(mEmotion);
                edNickName.setText(mNickName);
                edphone.setText(mPhone);
                edHeight.setText(mHeight);
                edWeight.setText(mWeight);
                edAppearance.setText(mAppearance);
                edJob.setText(mJob);
                edIntroduce.setText(mIntroduce);
                ImageLoader.getInstance().displayImage(headimg, ivAvatar);
                if ("1".equals(mSex)) {
                    tvSex.setText("男");
                } else {
                    tvSex.setText("女");
                }
                if ("0".equals(exist_parent)) {
                    rl_rlinvitation.setVisibility(View.VISIBLE);
                } else {
                    rl_rlinvitation.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                mNickName = edNickName.getText().toString().trim();
                mPhone = edphone.getText().toString().trim();
                mHeight = edHeight.getText().toString().trim();
                mWeight = edWeight.getText().toString().trim();
                mAppearance = edAppearance.getText().toString().trim();
                mJob = edJob.getText().toString().trim();
                mIntroduce = edIntroduce.getText().toString().trim();
                mIncome = tvIncome.getText().toString().trim();
                if ("0".equals(exist_parent)) {
                    invite = ed_edinvitation.getText().toString().trim();
                } else {
                    invite = "";
                }
                LoaddingShow();
                save(invite);
                break;
            case R.id.tv_tvSex:
                GradeFragment dialogGrade = new GradeFragment();
                dialogGrade.show(getSupportFragmentManager(), "GradeFragment");
                dialogGrade.setOnDialogListener(new GradeFragment.OnDialogListener() {
                    @Override
                    public void onDialogClick(String person) {
                        Log.d("TAG", person);
                        if ("男".equals(person)) {
                            mSex = "1";
                        } else {
                            mSex = "2";
                        }
                        tvSex.setText(person);
                    }
                });
                break;
            case R.id.iv_avatar:
                ActionSheet.showSheet(this, this, null);
                break;
            case R.id.tv_tvbirthday:
                showDialog(DATE_DIALOG);
                break;
            case R.id.tv_tvEmotion:
                EmotionFragment dialogEmotion = new EmotionFragment();
                dialogEmotion.show(getSupportFragmentManager(), "EmotionFragment");
                dialogEmotion.setOnDialogListener(new EmotionFragment.OnDialogListener() {
                    @Override
                    public void onDialogClick(String person) {
                        Log.d("TAG", person);
                        mEmotion = person;
                        tvEmotion.setText(mEmotion);
                    }
                });
                break;
            case R.id.tv_tvAddress:
                Intent intent = new Intent(this, CityActivity.class);
                startActivityForResult(intent, REQUEST_REGION_PICK);
                break;
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(InformationActivity.this, R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        display();
                    }
                }, 2017, 01, 01);
        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */

    public void display() {
        mBirthday = String.valueOf(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" "));
        tvBirthday.setText(mBirthday);
        Time01 = mBirthday;
        RetrofitUtil.getInstance().GetGetastro(ukey, mBirthday, new Subscriber<BaseResponse<AstroModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<AstroModel> baseResponse) {
                if (baseResponse.ret == 200) {
                    tvAge.setText(baseResponse.getData().getAge());
                    tvSign.setText(baseResponse.getData().getAstro());
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

    //mID,mPhone, mSign,mGrade,
    private void save(String invite) {
        Log.d("TAG111", mSex + "");
        RetrofitUtil.getInstance().getUserEditinfo(ukey, mNickName, mSex, Time01, mHeight, mWeight, mAppearance, mJob, mIncome, mEmotion, cityId, mIntroduce, this.invite,
                new Subscriber<BaseResponse<EmptyEntity>>() {
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
                            //showProgress(baseResponse.getMsg());
                            Bundle bundle = new Bundle();
                            bundle.putString("keys", "1");
                            toActivity(MainActivity.class, bundle);
                            finish();
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


    public static final String MULTIPART_FORM_DATA = "image/jpg";

    private void uploadImage(String imagePath) {
        File file = new File(imagePath);
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
                Log.e(TAG, "6565" + e.toString());
            }

            @Override
            public void onNext(BaseResponse<AvatarImageModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    Log.d(TAG, baseResponse.toString());
                    Log.d(TAG, "232" + baseResponse.getData().getUrl());
                    ImageLoader.getInstance().displayImage(baseResponse.getData().getUrl(), ivAvatar);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case REQUEST_REGION_PICK:
                    city = data.getStringExtra("date");
                    cityId = data.getStringExtra("id");
                    tvAddress.setText(city);
                    UtilPreference.saveString(mContext, "city", city);
                    break;
            }
        }
        PhotoUtils.getInstance().bindForResult(requestCode, resultCode, data);

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
