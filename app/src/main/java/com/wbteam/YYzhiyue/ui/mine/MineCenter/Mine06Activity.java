package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.Mywallet;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class Mine06Activity extends BaseActivity {
    @BindView(R.id.tv_pay)//充值
            TextView tv_pay;
    @BindView(R.id.tv_Income_yesterday)//昨日收益
            TextView tv_Income_yesterday;
    @BindView(R.id.tv_Income_total)//本周
            TextView tv_Income_week;

    @BindView(R.id.tv_total) //总余额
            TextView tv_total;
    @BindView(R.id.tv_withdraw)//可提现余额
            TextView tv_withdraw;
    @BindView(R.id.tv_confirm)//带确认金额
            TextView tv_confirm;
    @BindView(R.id.tv_voucher)//抵金卷
            TextView tv_voucher;
    private String total, withdraw, wait, coupon, yesterday, week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine06);
        ButterKnife.bind(this);
        setBackView();
        setTitle("我的钱包");
        setRightText("账单", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initData();
    }

    private void initData() {
        RetrofitUtil.getInstance().User_Mywallet(ukey, new Subscriber<BaseResponse<Mywallet>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<Mywallet> baseResponse) {
                if (baseResponse.ret == 200) {
                    total = baseResponse.getData().getTotal();
                    withdraw = baseResponse.getData().getWithdraw();
                    wait = baseResponse.getData().getWait();
                    coupon = baseResponse.getData().getCoupon();
                    yesterday = baseResponse.getData().getYesterday();
                    week = baseResponse.getData().getWeek();
                } else {
                    showProgress(baseResponse.getMsg());
                }
                initView();
            }
        });
    }

    private void initView() {
        tv_total.setText(total);
        tv_withdraw.setText(withdraw);
        tv_confirm.setText(wait);
        tv_voucher.setText(coupon);
        tv_Income_yesterday.setText(yesterday);
        tv_Income_week.setText(week);
    }

    @OnClick({R.id.tv_pay, R.id.tv_withdraw01})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pay:
                toActivity(RechargeActivity.class);
                break;
            case R.id.tv_withdraw01:
                Bundle bundle = new Bundle();
                bundle.putString("withdraw", withdraw);
                toActivity(WithDrawActivity.class, bundle);
                break;
        }
    }
}
