package com.wbteam.YYzhiyue.ui.login;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.wbteam.YYzhiyue.ui.MainActivity;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseFragment;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.model.RegisterModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.util.StringUtil;
import com.wbteam.YYzhiyue.util.ToastUtil;
import com.wbteam.YYzhiyue.util.UtilPreference;

import rx.Subscriber;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BaseFragment implements View.OnClickListener {
    private String TAG = "RegisterFragment";
    private EditText etPhone, etPwd, etCode, etInvitationcode;
    private CheckBox checkBox;
    private TextView tvRegister;
    TimeCount time = new TimeCount(60000, 1000);
    private String mobile;
    private TextView tvSendCode;
    private TextView tvProtocol;
    private String password;
    private String passwd1;
    private String code;
    private String clientid="";

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_register);
        passwd1 = "eed92abc0da569ad37b6e07b1d639400";//环信密码
        clientid = UtilPreference.getStringValue(getActivity(), "clientid");
        initView();
        return getContentView();
    }

    private void initView() {
        etPhone = (EditText) findViewById(R.id.register_et_phone);
        etPwd = (EditText) findViewById(R.id.register_et_pwd);
        etCode = (EditText) findViewById(R.id.register_et_code);
        etInvitationcode = (EditText) findViewById(R.id.register_et_Invitationcode);
        checkBox = (CheckBox) findViewById(R.id.checkbox01);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        tvSendCode = (TextView) findViewById(R.id.send_code_btn);
        tvProtocol = (TextView) findViewById(R.id.protocol_tv);
        tvSendCode.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvProtocol.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                mobile = etPhone.getText().toString().trim();
                password = etPwd.getText().toString().trim();
                code = etCode.getText().toString().trim();
                clientid=etInvitationcode.getText().toString().trim();
                // SMSSDK.submitVerificationCode("86", mobile, code);
                if (!checkBox.isChecked()) {
                    showProgress("請同意條款");
                    return;
                }
                Register(password);
                LoaddingShow();
           //  toActivity(Perfect01Activity.class);
                break;
            case R.id.protocol_tv:
                toActivity(AgreementActivity.class);
                break;
            case R.id.send_code_btn:
                sendCode();
                break;
        }
    }

    private void singin() {
        EMClient.getInstance().login(mobile, passwd1, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.d("login", "登录聊天服务器成功！");
                LoaddingDismiss();
//                Bundle bundle = new Bundle();
//                bundle.putString("keys", "2");
                toActivity(Perfect01Activity.class);
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

    private void Register(String password) {
        RetrofitUtil.getInstance().getUsersRegister(mobile, password, code, clientid, new Subscriber<BaseResponse<RegisterModel>>() {
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
            public void onNext(BaseResponse<RegisterModel> listBaseResponse) {
                // LoaddingDismiss();
                if (listBaseResponse.ret == 200) {
                    Log.e(TAG, "rx成功:" + listBaseResponse.data.toString());
                    UtilPreference.saveString(getActivity(), "ukey", listBaseResponse.getData().getUkey());
                    UtilPreference.saveString(getActivity(), "easemob_id", listBaseResponse.getData().getEasemob_id());
                    UtilPreference.saveString(getActivity(), "isvip", listBaseResponse.getData().getIsvip());
                    singin();//登录环信
                } else {
                    LoaddingDismiss();
                    showProgress(listBaseResponse.getMsg());
                }
            }
        });

    }

    private void sendCode() {
        mobile = etPhone.getText().toString().trim();
        if (StringUtil.isBlank(mobile)) {
            ToastUtil.showS(getActivity(), "手机号不能为空");
            return;
        }
        if (!(StringUtil.isCellPhone(mobile))) {
            ToastUtil.showS(getActivity(), "请输入正确的手机号码");
            return;
        } else {
            // SMSSDK.getVerificationCode("86", mobile);
            VerificationCode();
            time.start();
            LoaddingShow();
        }
    }

    private void VerificationCode() {
        RetrofitUtil.getInstance().getVerifysms(mobile, "verify",
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
                            showProgress(baseResponse.getMsg());
                        } else {
                            showProgress(baseResponse.getMsg());
                        }
                    }
                });
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            tvSendCode.setText("获取验证码");
            tvSendCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            tvSendCode.setClickable(false);//防止重复点击
            tvSendCode.setText(millisUntilFinished / 1000 + "s");
        }
    }
}
