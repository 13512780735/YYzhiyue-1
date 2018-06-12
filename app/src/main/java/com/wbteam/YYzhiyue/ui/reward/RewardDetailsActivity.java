package com.wbteam.YYzhiyue.ui.reward;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.Entity.UserInfoModel;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.model.RewardModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.mine.MineCenter.VIPRenewActivity;
import com.wbteam.YYzhiyue.ui.mine.MineCenter.ViedeoAuthenticationActivity;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.CircleImageView;
import com.wbteam.YYzhiyue.view.CustomDialog01;

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
    private String videoauth;
    private CustomDialog01 dialog;
    private CustomDialog01 dialog1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        listBean = (RewardModel.ListBean) intent.getSerializableExtra("rewardModel");
        //  Log.d("TAG", listBean.getId());
        setBackView();
        setTitle("约会详情");
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
        tvPrice.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_confirm)
    public void onClick(View v) {
        initUserInfo();
    }

    private void initUserInfo() {
        RetrofitUtil.getInstance().getUserGetmy(ukey, new Subscriber<BaseResponse<UserInfoModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<UserInfoModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    //Log.d("TAG21", baseResponse.getData().getInfo().getHeadimg());
                    //Log.e(TAG, baseResponse.getData().getInfo().getExist_parent());
                    UtilPreference.saveString(RewardDetailsActivity.this, "videoauth", baseResponse.getData().getInfo().getVideoauth());
                    UtilPreference.saveString(RewardDetailsActivity.this, "auth", baseResponse.getData().getInfo().getAuth());
                    UtilPreference.saveString(RewardDetailsActivity.this, "isvip", baseResponse.getData().getInfo().getIsvip());
                    //   mUserInfoModel= JSON.parseObject(baseResponse.getData().toString(),UserInfoModel.class);
                    toCreate();
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

    private void toCreate() {
        videoauth = UtilPreference.getStringValue(this, "videoauth");
        if ("1".equals(videoauth)) {
            isvip = UtilPreference.getStringValue(this, "isvip");
            if ("0".equals(isvip)) {
                dialog = new CustomDialog01(this).builder()
                        .setGravity(Gravity.CENTER)//默认居中，可以不设置
                        .setTitle("是否申请开通VIP服务", getResources().getColor(R.color.sd_color_black))//可以不设置标题颜色，默认系统颜色
                        .setCancelable(false)
                        .setNegativeButton("否", new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setPositiveButton("是", getResources().getColor(R.color.sd_color_black), new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                toActivity(VIPRenewActivity.class);
                            }
                        });
                dialog.show();
            } else {
                offer();
            }
        } else {
            dialog1 = new CustomDialog01(this).builder()
                    .setGravity(Gravity.CENTER)
                    .setTitle("是否视频认证", getResources().getColor(R.color.sd_color_black))
                    .setCancelable(false)
                    .setNegativeButton("否", new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setPositiveButton("是", getResources().getColor(R.color.sd_color_black), new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
                        @Override
                        public void onClick(View view) {
                            dialog1.dismiss();
                            // UtilPreference.saveString(getActivity(), "paykey", "5");
                            toActivity(ViedeoAuthenticationActivity.class);
                        }
                    });
            dialog1.show();

        }
    }

    private void offer() {
        RetrofitUtil.getInstance().

                GetAttendevent(ukey, listBean.getId(), new Subscriber<BaseResponse<EmptyEntity>>()

                {
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
