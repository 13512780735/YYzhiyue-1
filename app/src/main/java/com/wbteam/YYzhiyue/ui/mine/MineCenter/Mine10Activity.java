package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.login.Login_RegisterActivity;
import com.wbteam.YYzhiyue.util.MyActivityManager;
import com.wbteam.YYzhiyue.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class Mine10Activity extends BaseActivity {
    @BindView(R.id.forgetpwd_et_phone)
    EditText et_phone;
    @BindView(R.id.orld_et_pwd01)
    EditText orld_et_pwd01;//旧密码
    @BindView(R.id.forgetpwd_et_pwd)
    EditText forgetpwd_et_pwd;//新密码
    @BindView(R.id.forgetpwd_et_pwd01)
    EditText forgetpwd_et_pwd01;//确认密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine10);
        ButterKnife.bind(this);
        setBackView();
        setTitle("修改密码");
    }

    String phone, ordPwd, newPwd, confirmPwd;

    @OnClick({R.id.tv_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                phone = et_phone.getText().toString();
                ordPwd = orld_et_pwd01.getText().toString();
                newPwd = forgetpwd_et_pwd.getText().toString();
                confirmPwd = forgetpwd_et_pwd01.getText().toString();
                if (!newPwd.equals(confirmPwd)) {
                    showProgress("两次密码输入不一致，请重新输入");
                    forgetpwd_et_pwd.setText("");
                    forgetpwd_et_pwd01.setText("");
                    return;
                } else {
                    offer();
                    LoaddingShow();
                }
                break;
        }
    }

    private void offer() {
        RetrofitUtil.getInstance().User_Changepass(ukey, phone, ordPwd, newPwd, new Subscriber<BaseResponse<EmptyEntity>>() {
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
                    toActivity(Login_RegisterActivity.class);
                    MyActivityManager.getInstance().finishAllActivity();
                } else {
                    showProgress(baseResponse.getMsg());
                }
            }
        });
    }
}
