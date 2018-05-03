package com.wbteam.YYzhiyue.ui.mine.MineCenter;

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
import com.wbteam.YYzhiyue.ui.login.ForgetPwdActivity;
import com.wbteam.YYzhiyue.util.StringUtil;
import com.wbteam.YYzhiyue.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class Mine09Activity extends BaseActivity {
    @BindView(R.id.forgetpwd_et_phone)
    EditText et_phone;
    @BindView(R.id.forgetpwd_et_pwd)
    EditText et_pwd;
    @BindView(R.id.forgetpwd_et_code)
    EditText et_code;
    @BindView(R.id.send_code_btn)
    TextView tvSendCode;
    private String mobile;
    TimeCount time = new TimeCount(60000, 1000);
    private String pwd, code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine09);
        ButterKnife.bind(this);
        setBackView();
        setTitle("手机绑定");
    }

    @OnClick({R.id.tv_confirm, R.id.send_code_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                bindPhone();
                LoaddingShow();
                break;
            case R.id.send_code_btn:
                sendCode();
                break;
        }
    }

    private void bindPhone() {
        pwd = et_pwd.getText().toString().trim();
        code = et_code.getText().toString().trim();
        RetrofitUtil.getInstance().User_Bindmobile(ukey, mobile, pwd, code, new Subscriber<BaseResponse<EmptyEntity>>() {
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
                    finish();
                } else {
                    showProgress(baseResponse.getMsg());
                }
            }
        });
    }

    private void sendCode() {
        mobile = et_phone.getText().toString().trim();
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
