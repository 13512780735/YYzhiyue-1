package com.wbteam.YYzhiyue.ui.reward;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.model.RewardModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.view.CircleImageView;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class RewardDetailsActivity extends BaseActivity {
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private RewardModel.ListBean listBean;
    SimpleDateFormat sf = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        listBean = (RewardModel.ListBean) intent.getSerializableExtra("rewardModel");
        //  Log.d("TAG", listBean.getId());
        setBackView();
        setTitle("悬赏详情");
        initView();
    }

    private void initView() {

        tvTime.setText("时间：" + listBean.getS_time());
        ImageLoader.getInstance().displayImage(listBean.getUser().getHeadimg(), ivAvatar);
//        RequestOptions options = new RequestOptions();
//        options.placeholder(R.mipmap.banner);
//        Glide.with(mContext).load(listBean.getUser().getHeadimg()).apply(options).into(ivAvatar);
        tvName.setText("发起人：" + listBean.getUser().getNickname());
        tvType.setText("类型：" + listBean.getTagstr());
        tvAddress.setText("地点：" + listBean.getAddress());
        tvPrice.setText("金额：" + listBean.getAmount());
    }

    @OnClick(R.id.tv_confirm)
    public void onClick(View v) {
        RetrofitUtil.getInstance().GetAttendevent(ukey, listBean.getId(), new Subscriber<BaseResponse<EmptyEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<EmptyEntity> baseResponse) {
                if (baseResponse.ret == 200) {
                    // onBackPressed();
                    //showProgress("报名成功，请等待发起人确认名单！");
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
}
