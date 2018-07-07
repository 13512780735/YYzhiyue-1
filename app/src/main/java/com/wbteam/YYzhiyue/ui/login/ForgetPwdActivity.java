package com.wbteam.YYzhiyue.ui.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.util.MyActivityManager;
import com.wbteam.YYzhiyue.util.StringUtil;
import com.wbteam.YYzhiyue.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class ForgetPwdActivity extends BaseActivity {
    @BindView(R.id.forgetpwd_et_phone)
    EditText etPhone;
    @BindView(R.id.forgetpwd_et_pwd)
    EditText etPwd;
    @BindView(R.id.forgetpwd_et_pwd01)
    EditText etPwd01;
    @BindView(R.id.forgetpwd_et_code)
    EditText etCode;
    @BindView(R.id.send_code_btn)
    TextView tvSendCode;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    private String mobile;
    TimeCount time = new TimeCount(60000, 1000);
    private ForgetPwdActivity mContext;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        mContext = this;
        ButterKnife.bind(this);
        setBackView();
        setTitle("忘记密码");
    }

    @OnClick({R.id.send_code_btn, R.id.tv_confirm})
    public void Onclick(View view) {
        switch (view.getId()) {
            case R.id.send_code_btn:
                sendCode();
                break;
            case R.id.tv_confirm:
                reset();
                LoaddingShow();
                break;
        }
    }

    String paw01, paw02;

    private void reset() {
        paw01 = etPwd.getText().toString().trim();
        paw02 = etPwd01.getText().toString().trim();
        if (!paw01.equals(paw02)) {
            showProgress("两次输入密码不一样，请重新输入");
            etPwd.setText("");
            etPwd01.setText("");
            return;
        } else {
            code = etCode.getText().toString().trim();
            RetrofitUtil.getInstance().getResetpass(mobile, paw01, code, new Subscriber<BaseResponse<EmptyEntity>>() {
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
                        ToastUtil.showS(mContext,baseResponse.getMsg());
                        toActivityFinish(Login_RegisterActivity.class);
                    } else {
                        showProgress(baseResponse.getMsg());
                    }
                }
            });
        }
    }

    private void sendCode() {
        mobile = etPhone.getText().toString().trim();
        if (StringUtil.isBlank(mobile)) {
            ToastUtil.showS(mContext, "手机号不能为空");
            return;
        }
        if (!(StringUtil.isCellPhone(mobile))) {
            ToastUtil.showS(mContext, "请输入正确的手机号码");
            return;
        } else {
            VerificationCode();
            time.start();
            LoaddingShow();
        }
    }

    private void VerificationCode() {
        RetrofitUtil.getInstance().getVerifysms(mobile, "reset",
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
