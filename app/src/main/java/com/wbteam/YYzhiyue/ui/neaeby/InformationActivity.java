package com.wbteam.YYzhiyue.ui.neaeby;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.DatingInfoAdapter;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.DatingInfoModel;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.chat.ChatActivity;
import com.wbteam.YYzhiyue.ui.mine.MineCenter.VIPRenewActivity;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.CustomDialog01;
import com.wbteam.YYzhiyue.view.custom_scrollview.HorizontalPageLayoutManager;
import com.wbteam.YYzhiyue.view.custom_scrollview.PagingScrollHelper;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class InformationActivity extends BaseActivity {
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.ll_photos)
    LinearLayout llPhotos;
    @BindView(R.id.tv_age)
    TextView mTvAge;
    @BindView(R.id.tv_constellation)
    TextView mTvConstellation;
    @BindView(R.id.tv_height)
    TextView mTvHeight;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    private int screenWidth;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.tv_signature)
    TextView tv_signature;
    @BindView(R.id.tv_attention)
    TextView tv_attention;
    @BindView(R.id.rbbad)
    RadioButton rbbad;
    @BindView(R.id.rbmedium)
    RadioButton rbmedium;
    @BindView(R.id.rbgood)
    RadioButton rbgood;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    private String rkey;
    private String nickname;
    private String easemob_id;
    private DatingInfoModel datingInfoModel;
    private List<DatingInfoModel.PicsBean> data01;
    private DatingInfoAdapter mAdapter;
    private HorizontalPageLayoutManager horizontalPageLayoutManager;
    private String username;
    private String to_nicheng;
    private String Myeasemob_id;
    private CustomDialog01 dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information2);
        ButterKnife.bind(this);
        Myeasemob_id = UtilPreference.getStringValue(mContext, "easemob_id");
        rkey = getIntent().getExtras().getString("rkey");
        nickname = getIntent().getExtras().getString("nickname");
        easemob_id = getIntent().getExtras().getString("easemob_id");
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        //获取屏幕的宽和高
        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        setBackView();
        setTitle(nickname);
        setRightText("举报", null);
        initData();
        LoaddingShow();
    }

    private void initData() {
        Log.d("TAG151", "222");

        RetrofitUtil.getInstance().DatingGetinfo(ukey, rkey, new Subscriber<BaseResponse<DatingInfoModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<DatingInfoModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    datingInfoModel = baseResponse.getData();
                    Log.d("TAG565", datingInfoModel.getInfo().getIsfollow());
//                    username = datingInfoModel.getInfo().getEasemob_id();
//                    to_nicheng = datingInfoModel.getInfo().getNickname();
                    if ("0".equals(datingInfoModel.getInfo().getIsfollow())) {
                        tv_attention.setText("关注");
                    } else if ("1".equals(datingInfoModel.getInfo().getIsfollow())) {
                        tv_attention.setText("取消关注");
                    }
                    mTvAge.setText("年龄："+datingInfoModel.getInfo().getAge());
                    mTvConstellation.setText("星座："+datingInfoModel.getInfo().getAstro());
                    mTvHeight.setText("身高："+datingInfoModel.getInfo().getHeight());
                    mTvAddress.setText("城市："+datingInfoModel.getInfo().getCityname());
                    initView();
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

    private void initView() {
        Log.d("TAG2", datingInfoModel.getInfo().getPositive());
        int height = UtilPreference.getIntValue(mContext, "height");
        int width = UtilPreference.getIntValue(mContext, "width");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(30, width * (9 / 16) - 60, 30, 0);
        LinearLayout.LayoutParams lp01 = new LinearLayout.LayoutParams(screenWidth, screenWidth);
        frameLayout.setLayoutParams(lp01);
        llPhotos.setLayoutParams(lp);

//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
//        params.width = width;
//        params.height = width * (9 / 16);
//        // params.setMargins(30, width * (9 / 16) - 100, 30, 0);
//        frameLayout.setLayoutParams(params);
//        ivPhoto.setMinimumWidth(width);
//        ivPhoto.setMinimumHeight(width * (9 / 16));
//        ivPhoto.setMaxHeight(width * (9 / 16));

        ivPhoto.setMinimumWidth(width);
        //ivPhoto.setMinimumHeight(screenWidth * (9 / 16));
        ivPhoto.setMaxHeight(width * (9 / 16));
        ivPhoto.setBackgroundResource(R.mipmap.icon_bg_white);
        Glide.with(mContext).load(datingInfoModel.getPics().get(0).getPath()).into(ivPhoto);
        tv_signature.setText(datingInfoModel.getInfo().getDescription());
        rbbad.setText("差评 " + datingInfoModel.getInfo().getNegative());
        rbmedium.setText("中评 " + datingInfoModel.getInfo().getNeutral());
        rbgood.setText("好评 " + datingInfoModel.getInfo().getPositive());
        horizontalPageLayoutManager = new HorizontalPageLayoutManager(1, 5);
        PagingScrollHelper scrollHelper = new PagingScrollHelper();
        data01 = datingInfoModel.getPics();
        mAdapter = new DatingInfoAdapter(R.layout.datinginfo_gridview_items, data01);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        scrollHelper.setUpRecycleView(mRecyclerView);
        RecyclerView.LayoutManager layoutManager = null;
        layoutManager = horizontalPageLayoutManager;
        if (layoutManager != null) {
            mRecyclerView.setLayoutManager(layoutManager);
            scrollHelper.updateLayoutManger();
        }

    }


    @OnClick({R.id.tv_send, R.id.tv_attention, R.id.tv_lookAll})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send:
                //String username = user.getUsername();
                // demo中直接进入聊天页面，实际一般是进入用户详情页
                //  Log.d("TAG221", datingInfoModel.getInfo().getEasemob_id());
                // String to_avater = datingInfoModel.getInfo().getHeadimg();
                //.putExtra("to_avater",to_avater).putExtra("to_nicheng",to_nicheng)
                isvip = UtilPreference.getStringValue(mContext, "isvip");
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
                                    UtilPreference.saveString(mContext, "paykey", "2");
                                    toActivityFinish(VIPRenewActivity.class);
                                }
                            });
                    dialog.show();
                } else {
                    if ("Myeasemob_id".equals(easemob_id)) {
                        showProgress("不能跟自己聊天");
                        return;
                    }
                    startActivity(new Intent(InformationActivity.this, ChatActivity.class).putExtra("userId", easemob_id).putExtra("to_nicheng", nickname));
                }
                // startActivity(new Intent(InformationActivity.this, ChatActivity.class).putExtra("userId", easemob_id).putExtra("to_nicheng", nickname));
                break;
            case R.id.tv_attention:
                if ("取消关注".equals(tv_attention.getText().toString())) {
                    tv_attention.setText("关注");
                } else if ("关注".equals(tv_attention.getText().toString())) {
                    tv_attention.setText("取消关注");
                }
                RetrofitUtil.getInstance().DatingFollow(ukey, rkey, new Subscriber<BaseResponse<EmptyEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResponse<EmptyEntity> baseResponse) {
                        if (baseResponse.ret == 200) {
                            showProgress(baseResponse.getMsg());
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
                break;
            case R.id.tv_lookAll:
                if (datingInfoModel.getPics() != null && datingInfoModel.getPics().size() > 0) {
                    List<DatingInfoModel.PicsBean> dataPics = datingInfoModel.getPics();
                    Bundle bundle = new Bundle();
                    bundle.putString("nickname", nickname);
                    bundle.putSerializable("pics", (Serializable) dataPics);
                    toActivity(GalleryListActivity.class, bundle);
                } else {
                    return;
                }
                break;
        }
    }
}
