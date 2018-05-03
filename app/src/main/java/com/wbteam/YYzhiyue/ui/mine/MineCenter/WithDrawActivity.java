package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.util.StringUtil;
import com.wbteam.YYzhiyue.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class WithDrawActivity extends BaseActivity {
    @BindView(R.id.withdraw_et_account)
    EditText et_account;
    @BindView(R.id.withdraw_et_name)
    EditText et_name;
    @BindView(R.id.withdraw_amount)
    EditText et_amount;
    @BindView(R.id.withdraw_et_amount01)
    TextView tv_amount01;
    private String withdraw;
    private String withdraw01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw);
        ButterKnife.bind(this);
        setBackView();
        setTitle("提现");
        withdraw = getIntent().getExtras().getString("withdraw");
        withdraw01 = withdraw.substring(0, withdraw.indexOf("."));
        initView();
    }

    private void initView() {
        tv_amount01.setText("¥ " + withdraw01);
    }

    String account, name, amount;

    @OnClick({R.id.tv_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                account = et_account.getText().toString().trim();
                name = et_name.getText().toString().trim();
                amount = et_amount.getText().toString().trim();
                if (StringUtil.isBlank(amount)) {
                    showProgress("提现金额不能为空");
                    return;
                }
                if (Integer.valueOf(amount) > Integer.valueOf(withdraw01)) {
                    showProgress("提现金额已超过可提现的最大金额");
                    return;
                } else if (Integer.valueOf(amount) < 200) {
                    showProgress("提现金额不能低于200");
                    return;
                }
                offer();
                LoaddingShow();
                break;
        }
    }

    private void offer() {
        RetrofitUtil.getInstance().User_Withdraw(ukey, account, name, "alipay", amount, new Subscriber<BaseResponse<EmptyEntity>>() {
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
}
