package com.wbteam.YYzhiyue.ui.login;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.igexin.sdk.PushManager;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.LoginRegisterTabAdapter;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.util.ToastUtil;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

public class Login_RegisterActivity extends BaseActivity {


    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    @BindView(R.id.sliding_tabs)
    TabLayout mTabLayout;

    private List<String> mDatas;
    private Context mContext;
    private String ukeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__register);
        ButterKnife.bind(this);
        mContext = this;
        openPermission();
        mDatas = new ArrayList<>(Arrays.asList("登录", "注册"));
        getCid();
        initView();
    }

    public void getCid() {
        String cid = PushManager.getInstance().getClientid(this);
        UtilPreference.saveString(mContext, "clientid", cid);
       // Log.d("TAG95959", cid);
    }

    private void openPermission() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE + Manifest.permission.CAMERA + Manifest.permission.WRITE_EXTERNAL_STORAGE
                + Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(mContext,"请授予下面权限",Toast.LENGTH_SHORT).show();
            List<PermissionItem> permissions = new ArrayList<PermissionItem>();
            permissions.add(new PermissionItem(Manifest.permission.CALL_PHONE, "电话", R.drawable.permission_ic_phone));
            permissions.add(new PermissionItem(Manifest.permission.CAMERA, "照相", R.drawable.permission_ic_camera));
            permissions.add(new PermissionItem(Manifest.permission.ACCESS_FINE_LOCATION, "定位", R.drawable.permission_ic_location));
            permissions.add(new PermissionItem(Manifest.permission.ACCESS_COARSE_LOCATION, "定位", R.drawable.permission_ic_location));
            permissions.add(new PermissionItem(Manifest.permission.RECORD_AUDIO, "录音", R.drawable.permission_ic_micro_phone));
            //  permissions.add(new PermissionItem(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, "录音", R.drawable.permission_ic_micro_phone));
            permissions.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "储存空间", R.drawable.permission_ic_storage));
            HiPermission.create(mContext)
                    .permissions(permissions)
                    .msg("为了您正常使用影缘之约应用，需要以下权限")
                    .animStyle(R.style.PermissionAnimModal)
                    .checkMutiPermission(new PermissionCallback() {
                        @Override
                        public void onClose() {
                            Log.i(TAG, "onClose");
                            ToastUtil.showS(mContext, "权限被拒绝");
                        }

                        @Override
                        public void onFinish() {
                            //ToastUtil.showS(mContext,"权限已被开启");
                        }

                        @Override
                        public void onDeny(String permission, int position) {
                            Log.i(TAG, "onDeny");
                        }

                        @Override
                        public void onGuarantee(String permission, int position) {
                            Log.i(TAG, "onGuarantee");
                        }
                    });
            return;
        }
    }

    private void initView() {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(viewpager);
        List<Fragment> mfragments = new ArrayList<Fragment>();
        mfragments.add(new LoginFragment());
        mfragments.add(new RegisterFragment());
        viewpager.setAdapter(new LoginRegisterTabAdapter(getSupportFragmentManager(), mfragments, mDatas));
        viewpager.setCurrentItem(0);
    }
}
