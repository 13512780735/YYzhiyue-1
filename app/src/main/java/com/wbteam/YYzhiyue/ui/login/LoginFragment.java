package com.wbteam.YYzhiyue.ui.login;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.wbteam.YYzhiyue.ui.MainActivity;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseFragment;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.LoginModel;
import com.wbteam.YYzhiyue.network.api_service.model.ThirdloginModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.util.UtilPreference;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener, PlatformActionListener, Handler.Callback {
    EditText etPhone;
    EditText etPwd;
    private TextView tvLogin;
    private ImageView login_wechat, login_qq, login_weibo;
    private TextView tv_forgetpwd;
    private String mobile;
    private String password;
    private String TAG = "LoginFragment";
    private String passwd1;
    private String currentUsername;
    private Handler handler;
    private String third_type;//第三方登录类型
    private String easemob_id;
    private String flag;
    private String figureurl_qq_1;
    private String nickname;
    private String uid;
    private String clientid;
    private String isvip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_login);
        passwd1 = "eed92abc0da569ad37b6e07b1d639400";//环信密码
        clientid = UtilPreference.getStringValue(getActivity(), "clientid");
        initView();
        mobile = UtilPreference.getStringValue(getActivity(), "mobile");
        password = UtilPreference.getStringValue(getActivity(), "password");
        etPhone.setText(mobile);
        etPwd.setText(password);
        handler = new Handler(this);

        return getContentView();
    }

    private void initView() {
        etPhone = (EditText) findViewById(R.id.login_et_phone);
        etPwd = (EditText) findViewById(R.id.login_et_pwd);
        tvLogin = (TextView) findViewById(R.id.tv_login);
        tv_forgetpwd = (TextView) findViewById(R.id.tv_forgetpwd);
        login_wechat = (ImageView) findViewById(R.id.login_wechat);
        login_qq = (ImageView) findViewById(R.id.login_qq);
        login_weibo = (ImageView) findViewById(R.id.login_weibo);
        tvLogin.setOnClickListener(this);
        tv_forgetpwd.setOnClickListener(this);
        login_wechat.setOnClickListener(this);
        login_qq.setOnClickListener(this);
        login_weibo.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                mobile = etPhone.getText().toString().trim();
                password = etPwd.getText().toString().trim();
                LoaddingShow();
                RetrofitUtil.getInstance().getUsersLogin(mobile, password, clientid, new Subscriber<BaseResponse<LoginModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LoaddingDismiss();
                        Log.e(TAG, "rx失败:" + e.getMessage());
                        showProgress("数据加载失败！");
                    }

                    @Override
                    public void onNext(BaseResponse<LoginModel> listBaseResponse) {
                        if (listBaseResponse.ret == 200) {
                            UtilPreference.saveString(getActivity(), "mobile", mobile);
                            UtilPreference.saveString(getActivity(), "password", password);
                            UtilPreference.saveString(getActivity(), "ukey", listBaseResponse.getData().getUkey());
                            UtilPreference.saveString(getActivity(), "easemob_id", listBaseResponse.getData().getEasemob_id());
                            UtilPreference.saveString(getActivity(), "isvip", listBaseResponse.getData().getIsvip());
                            Log.e(TAG, "rx成功:" + listBaseResponse.data.toString());
                            Log.e(TAG, "ukey:" + listBaseResponse.getData().getUkey());
                            flag = "2";
                            logout();//环信登录
                        } else {
                            LoaddingDismiss();
                            showProgress(listBaseResponse.getMsg());
                        }
                    }
                });
                break;
            case R.id.tv_forgetpwd:
                toActivity(ForgetPwdActivity.class);
                break;
            case R.id.login_wechat:
                LoaddingShow();
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                authorize(wechat);
                //toActivityFinish(MainActivity.class);
                break;
            case R.id.login_qq:
                LoaddingShow();
                Platform qzone = ShareSDK.getPlatform(QQ.NAME);
                authorize(qzone);
                // toActivityFinish(MainActivity.class);
                break;
            case R.id.login_weibo:
                LoaddingShow();
                Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
                authorize(sina);
                //toActivityFinish(MainActivity.class);
                break;
        }
    }

    private void logout() {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d("TAG", "EM退出成功");
                if ("1".equals(flag)) {
                    thirdlogin(third_type, nickname, uid, figureurl_qq_1);
                } else {
                    signin();
                }
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                LoaddingDismiss();
            }
        });
    }

    private void signin() {
        EMClient.getInstance().login(mobile, passwd1, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.d("login", "登录聊天服务器成功！");
                LoaddingDismiss();
                Bundle bundle = new Bundle();
                bundle.putString("keys", "2");
                toActivity(MainActivity.class, bundle);
                getActivity().finish();
            }

            @Override
            public void onError(int i, String s) {
                LoaddingDismiss();
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    // 执行授权,获取用户信息
    private void authorize(Platform plat) {

        plat.setPlatformActionListener(this);
        // 关闭SSO授权
        //plat.SSOSetting(true);
        plat.SSOSetting(false);
        // plat.authorize();
        plat.showUser(null);
    }

    private static final int MSG_AUTH_CANCEL = 1;
    private static final int MSG_AUTH_ERROR = 2;
    private static final int MSG_AUTH_COMPLETE = 3;

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
        if (action == Platform.ACTION_USER_INFOR) {
            Message msg = new Message();
            Log.d("TAG", "platform-->" + platform.toString());
            Log.d("数据：", res.toString());
            msg.what = MSG_AUTH_COMPLETE;
            // PlatformDb platDB = platform.getDb();//获取数平台数据DB
            msg.obj = new Object[]{platform.getName(), platform};
            handler.sendMessage(msg);
        }
    }

    @Override
    public void onError(Platform platform, int action, Throwable t) {
        if (action == Platform.ACTION_USER_INFOR) {
            handler.sendEmptyMessage(MSG_AUTH_ERROR);
            Log.d("TAG", t.toString());
        }
        t.printStackTrace();
    }

    @Override
    public void onCancel(Platform platform, int action) {
        if (action == Platform.ACTION_USER_INFOR) {
            handler.sendEmptyMessage(MSG_AUTH_CANCEL);
            Log.d("TAG", platform.toString());
        }
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_AUTH_CANCEL: {
                LoaddingDismiss();
                // 取消授权
                //  disShowProgress();
                //ToastUtil.showL(this, "取消授权");
                showProgress("取消授权");
                // ToastUtil.showL(this, getString(R.string.auth_cancel));
                Log.d("TAG", msg.toString());
            }
            break;
            case MSG_AUTH_ERROR: {
                LoaddingDismiss();
                // disShowProgress();
                // 授权失败
                Log.d("TAG", "授权失败");
                //ToastUtil.showL(this, "授权失败");
                showProgress("授权失败");
                // ToastUtil.showL(this, getString(R.string.auth_error));
                Log.d("TAG", msg.toString());
            }
            break;
            case MSG_AUTH_COMPLETE: {
                // disShowProgress();
                // 授权成功
                // ToastUtil.showL(this, "授权成功");
                Log.d("TAG", msg.toString());
                Log.d("TAG", "授权成功");
                // ToastUtil.showL(this, getString(R.string.auth_complete));
                Log.d("数据2：", msg.toString());
                Object[] objs = (Object[]) msg.obj;
                Log.d("数据2：", objs.toString());
                String platform = (String) objs[0];
                //   HashMap<String, Object> res1 = (HashMap<String, Object>) objs[1];
                Platform res = (Platform) objs[1];
                // Log.e("TAG", "授权返回的信息1：" + JSON.toJSONString(objs[1]));
                Log.e("TAG", QQ.NAME + "授权返回的信息2：" + platform);
//                res.getDb().getToken();
//                res.getDb().getUserGender();
//                res.getDb().getUserIcon();
//                res.getDb().getUserId();
//                res.getDb().getUserName();
                if (res != null) {
                    if (platform.equals(QQ.NAME)) {
                        // QQ认证回调
                        //  currentUsername=(String)res.get("nickname");\
                        third_type = "qq";
                        currentUsername = res.getDb().getUserId();
                        figureurl_qq_1 = res.getDb().getUserIcon();
                        nickname = res.getDb().getUserName();
                        uid = res.getDb().getUserId();
                        Log.d("TAG", "currentUsername-->" + currentUsername);
                        Log.d("TAG", "nickname-->" + nickname);
                        Log.d("TAG", "figureurl_qq_1-->" + figureurl_qq_1);
                        //showProgress("Loading...");
                        flag = "1";
                        logout();
                    } else if (platform.equals(SinaWeibo.NAME)) {
                        // 新浪微博认证回调
                        third_type = "weibo";
                        currentUsername = res.getDb().getUserId();
                        figureurl_qq_1 = res.getDb().getUserIcon();
                        nickname = res.getDb().getUserName();
                        uid = res.getDb().getUserId();
                        Log.d("TAG", "currentUsername-->" + currentUsername);
                        Log.d("TAG", "nickname-->" + nickname);
                        Log.d("TAG", "figureurl_qq_1-->" + figureurl_qq_1);
                        flag = "1";
                        logout();
                        // showProgress("Loading...");
                    } else if (platform.equals(Wechat.NAME)) {
                        // 微信认证回调
                        third_type = "weixin";
                        currentUsername = res.getDb().getUserId();
                        figureurl_qq_1 = res.getDb().getUserIcon();
                        nickname = res.getDb().getUserName();
                        uid = res.getDb().getUserId();
                        Log.d("TAG", "currentUsername-->" + currentUsername);
                        Log.d("TAG", "nickname-->" + nickname);
                        Log.d("TAG", "figureurl_qq_1-->" + figureurl_qq_1);
                        flag = "1";
                        logout();

                        // showProgress("Loading...");
                    }

                } else {
                    LoaddingDismiss();
                    showProgress("获取用户信息失败");
                }
            }
            break;
        }
        return false;
    }

    private void thirdlogin(String third_type, String nickname, String uid, String figureurl_qq_1) {

        RetrofitUtil.getInstance().getUsersLogin(uid, third_type, nickname, figureurl_qq_1, clientid, new Subscriber<BaseResponse<ThirdloginModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<ThirdloginModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    Log.d("TAG", baseResponse.getData().toString());
                    ukey = baseResponse.getData().getUkey();
                    easemob_id = baseResponse.getData().getEasemob_id();
                    isvip=baseResponse.getData().getIsvip();
                    UtilPreference.saveString(getActivity(), "ukey", ukey);
                    UtilPreference.saveString(getActivity(), "easemob_id", easemob_id);
                    UtilPreference.saveString(getActivity(), "isvip", isvip);
                    //  logout();
                    Bundle bundle = new Bundle();
                    bundle.putString("keys", "2");
                    toActivity(MainActivity.class, bundle);
                    getActivity().finish();
                } else {
                    showProgress(baseResponse.getMsg());
                }
            }
        });
    }


}
