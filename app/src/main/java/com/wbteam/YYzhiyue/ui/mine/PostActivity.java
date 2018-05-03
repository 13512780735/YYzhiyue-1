package com.wbteam.YYzhiyue.ui.mine;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.guoqi.actionsheet.ActionSheet;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.BumModel;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.MainActivity;
import com.wbteam.YYzhiyue.util.MyActivityManager;
import com.wbteam.YYzhiyue.util.custom.GridViewAddImgesAdpter;
import com.wbteam.YYzhiyue.util.custom.GridViewAddImgesAdpter01;
import com.wbteam.YYzhiyue.util.photo.PhotoUtils;
import com.wbteam.YYzhiyue.view.city.CityActivity;

import net.bither.util.NativeUtil;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class PostActivity extends BaseActivity implements ActionSheet.OnActionSheetSelected, EasyPermissions.PermissionCallbacks {
    private static final int REQUEST_REGION_PICK = 3;//城市返回标识
    private static final int REQUEST_REGION = 4;//返回内容标识
    private static final String TAG = "PostActivity";
    @BindView(R.id.gw)
    GridView gw;//图片
    @BindView(R.id.gw01)
    GridView gw01;//视频
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.ed_explain)
    EditText edExplain;
    //性别选择
    @BindView(R.id.radioGroup)
    RadioGroup rgGrade;
    @BindView(R.id.radioButton)
    RadioButton rb01;
    @BindView(R.id.radioButton2)
    RadioButton rb02;
    @BindView(R.id.radioButton3)
    RadioButton rb03;
    //是否公开
    @BindView(R.id.radioGroup02)
    RadioGroup radioGroup02;
    @BindView(R.id.radioButton4)
    RadioButton rb04;
    @BindView(R.id.radioButton5)
    RadioButton rb05;
    /**
     * 图片
     */
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private List<Map<String, Object>> datas;
    private GridViewAddImgesAdpter gridViewAddImgesAdpter;

    private Dialog dialog;
    private final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
    private final String IMAGE_DIR = Environment.getExternalStorageDirectory() + "/gridview/";
    /* 头像名称 */
    private final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File file;
    private GridViewAddImgesAdpter01 gridViewAddImgesAdpter01;
    private List<Map<String, Object>> datas01;

    /**
     * 视频
     *
     * @param savedInstanceState
     */
    /**
     * 时间日期
     *
     * @param savedInstanceState
     */
    final int DATE_DIALOG = 1;
    int mYear, mMonth, mDay;
    private String mBirthday = null;

    private String city;
    private String cityId;
    private ScopeFragment dialog01;
    private String tagid;
    private String sex = "2";
    private String iscontact;
    private String Time01;
    private String postKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);
        postKey = getIntent().getExtras().getString("postKey");
        datas = new ArrayList<>();//图片
        datas01 = new ArrayList<>();//视频
        setBackView();
        setTitle("发布动态");
        initView();
        PhotoUtils.getInstance().init(this, true, new PhotoUtils.OnSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                // 4、当拍照或从图库选取图片成功后回调
                // mTvPath.setText(outputFile.getAbsolutePath());
                //uploadImage(outputFile.getAbsolutePath());
                photoPath(outputFile.getAbsolutePath());
                //Log.d("TAG21",outputFile.getAbsolutePath());
                //  LoaddingShow();
                //Glide.with(PostActivity.this).load(outputUri).into(ivAvatar);
            }
        });
    }

    private void initView() {
        /**
         * 图片
         */
        gridViewAddImgesAdpter = new GridViewAddImgesAdpter(datas, this);
        gw.setAdapter(gridViewAddImgesAdpter);
        gw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ActionSheet.showSheet(PostActivity.this, PostActivity.this, null);
            }
        });
        /**
         * 视频
         */
        gridViewAddImgesAdpter01 = new GridViewAddImgesAdpter01(datas01, this);
        gw01.setAdapter(gridViewAddImgesAdpter01);
        gw01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // showdialog();
            }
        });
        rgGrade.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton:
                        sex = "2";
                        break;
                    case R.id.radioButton2:
                        sex = "1";
                        break;
                    case R.id.radioButton3:
                        sex = "0";
                        break;
                }
            }
        });
        iscontact = "1";
        sex = "2";
        radioGroup02.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton4:
                        iscontact = "1";
                        break;
                    case R.id.radioButton5:
                        iscontact = "0";
                        break;
                }
            }
        });

    }

    @OnClick({R.id.tv_time, R.id.tv_address, R.id.tv_details, R.id.tv_clear, R.id.tv_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_time:
                showDialog(DATE_DIALOG);
                break;
            case R.id.tv_address:
                Intent intent = new Intent(this, CityActivity.class);
                startActivityForResult(intent, REQUEST_REGION_PICK);
                break;
            case R.id.tv_details:
                Intent intent1 = new Intent(mContext, SelectScopeActivity.class);
                intent1.putExtra("flag", "2");
                startActivityForResult(intent1, REQUEST_REGION);
                break;
            case R.id.tv_clear:
                edExplain.setText("");
                break;
            case R.id.tv_confirm:
                String remark = edExplain.getText().toString();
                //String videos = "";
                RetrofitUtil.getInstance().Publishweibo(ukey, Time01, cityId, sex, tagid, iscontact, remark, imgId, new Subscriber<BaseResponse<EmptyEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResponse<EmptyEntity> baseResponse) {
                        if (baseResponse.getRet() == 200) {
                            Log.d(TAG, baseResponse.getMsg());
                            finish();
                            if ("one".equals(postKey)) {
                                Bundle bundle = new Bundle();
                                bundle.putString("keys", "1");
                                toActivity(MainActivity.class, bundle);
                                MyActivityManager.getInstance().finishAllActivity();
                            } else if ("two".equals(postKey)) {
                                finish();
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

                break;
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(PostActivity.this, R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date02 = null;
        try {
            date02 = sdf.parse(mBirthday);
            Time01 = String.valueOf(date02.getTime() / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        tvTime.setText(mBirthday);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_REGION_PICK) {
                if (data != null) {
                    city = data.getStringExtra("date");
                    cityId = data.getStringExtra("id");
                    tvAddress.setText(city);
                }
            } else if (requestCode == REQUEST_REGION) {
                if (data != null) {
                    String tag = data.getStringExtra("data");
                    tagid = data.getStringExtra("dataid");
                    tvDetails.setText(tag);
                }
            }
        }
        PhotoUtils.getInstance().bindForResult(requestCode, resultCode, data);
    }

    public void photoPath(String path) {
        Map<String, Object> map = new HashMap<>();
        map.put("path", path);
        upload(path);
        datas.add(map);
        gridViewAddImgesAdpter.notifyDataSetChanged();
    }

    public static final String MULTIPART_FORM_DATA = "image/jpg";
    String imgId = "";

    private void upload(String path) {
        Log.e("TAG", "99" + path);
        File file = new File(path);
        RequestBody requestApiKey = RequestBody.create(MediaType.parse("multipart/form-data"), ukey);
        //RequestBody requestImgFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody requestFile =               // 根据文件格式封装文件
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        MultipartBody.Part requestImgPart =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RetrofitUtil.getInstance().getUploadalbum(requestApiKey, requestImgPart, new Subscriber<BaseResponse<BumModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
                Log.e(TAG, "6565" + e.toString());
            }

            @Override
            public void onNext(BaseResponse<BumModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    String imgid = baseResponse.getData().getPid();
                    Log.d(TAG, imgid);
                    imgId += imgid + ",";
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
