package com.wbteam.YYzhiyue.im;

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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.DatingInfoAdapter;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.DatingInfoModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.neaeby.GalleryListActivity;
import com.wbteam.YYzhiyue.ui.neaeby.InformActivity;
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

public class UserInforActivity extends BaseActivity {
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
    @BindView(R.id.tv_signature)
    TextView tv_signature;
    @BindView(R.id.rbbad)
    RadioButton rbbad;
    @BindView(R.id.rbmedium)
    RadioButton rbmedium;
    @BindView(R.id.rbgood)
    RadioButton rbgood;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    private DatingInfoModel datingInfoModel;
    private List<DatingInfoModel.PicsBean> data01;
    private DatingInfoAdapter mAdapter;
    private HorizontalPageLayoutManager horizontalPageLayoutManager;

    private String easemobid;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);
        ButterKnife.bind(this);
        easemobid = getIntent().getStringExtra("username");
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        //获取屏幕的宽和高
        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        setBackView();
        //setTitle(nickname);
        setRightText("举报",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toActivity(InformActivity.class);
                    }
                });
        initData();
        LoaddingShow();
    }

    private void initData() {
        RetrofitUtil.getInstance().Getuserinfo(ukey, easemobid, new Subscriber<BaseResponse<DatingInfoModel>>() {
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
                    setTitle(datingInfoModel.getInfo().getNickname());
                    mTvAge.setText("年龄：" + datingInfoModel.getInfo().getAge());
                    mTvConstellation.setText("星座：" + datingInfoModel.getInfo().getAstro());
                    mTvHeight.setText("身高：" + datingInfoModel.getInfo().getHeight());
                    mTvAddress.setText("城市：" + datingInfoModel.getInfo().getCityname());
                    tv_signature.setText(datingInfoModel.getInfo().getDescription());
                    nickname = datingInfoModel.getInfo().getNickname();
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

        ivPhoto.setMinimumWidth(width);
        ivPhoto.setMaxHeight(width * (9 / 16));
        ivPhoto.setBackgroundResource(R.mipmap.icon_bg_white);
        if (datingInfoModel.getPics().size() == 0) {
            ImageLoader.getInstance().displayImage(datingInfoModel.getInfo().getHeadimg(), (ivPhoto));
            //  Glide.with(mContext).load(datingInfoModel.getInfo().getHeadimg()).into(ivPhoto);
        } else {
            ImageLoader.getInstance().displayImage(datingInfoModel.getPics().get(0).getPath(), (ivPhoto));
            //   Glide.with(mContext).load(datingInfoModel.getPics().get(0).getPath()).into(ivPhoto);
        }
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

    @OnClick({R.id.tv_lookAll})
    public void onClick(View v) {
        switch (v.getId()) {
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
