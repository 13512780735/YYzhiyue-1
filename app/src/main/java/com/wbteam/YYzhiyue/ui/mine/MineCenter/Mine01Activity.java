package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.Entity.UserInfoModel;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.CircleImageView;
import com.wbteam.YYzhiyue.view.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class Mine01Activity extends BaseActivity {
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_VIP)
    TextView tvVIP;
    @BindView(R.id.rl_viptime)
    RelativeLayout rl_viptime;
    @BindView(R.id.tv_vipTime)
    TextView tv_vipTime;
    @BindView(R.id.gridView)
    MyGridView mGridView;
    private String headimg;
    private int[] icon = {R.mipmap.icon_vip_01, R.mipmap.icon_vip_02,
            R.mipmap.icon_vip_03, R.mipmap.icon_vip_04, R.mipmap.icon_vip_05, R.mipmap.icon_vip_06, R.mipmap.icon_vip_07, R.mipmap.icon_vip_08};
    private String[] iconName = {"免费置顶", "专属标识", "互动评论", "免费下单", "高级搜索", "专属客服", "免费查看", "免费围观"};
    private List<Map<String, Object>> dataList;
    private SimpleAdapter simpleAdapter;
    private UserInfoModel mUserInfoModel;
    private String isVip="0";
    private String vipdeadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine01ctivity);
        ButterKnife.bind(this);
        headimg = UtilPreference.getStringValue(mContext, "headimg");
        setBackView();
        setTitle("会员中心");
        initData();

    }

    private void initData() {
        RetrofitUtil.getInstance().getUserGetmy(ukey, new Subscriber<BaseResponse<UserInfoModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
                //showProgress("数据加载失败！");
            }

            @Override
            public void onNext(BaseResponse<UserInfoModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    mUserInfoModel = baseResponse.getData();
                    //   mUserInfoModel= JSON.parseObject(baseResponse.getData().toString(),UserInfoModel.class);
                    isVip = mUserInfoModel.getInfo().getIsvip();
                    vipdeadline = mUserInfoModel.getInfo().getVipdeadline();
                } else {
                    if ("Ukey不合法".equals(baseResponse.getMsg())) {
                        showProgress01("您的帐号已在其他设备登录！");
                        return;
                    } else {
                        showProgress(baseResponse.getMsg());
                    }
                }
                initView();
            }
        });
    }

    private void initView() {
        if ("0".equals(isVip)) {
            rl_viptime.setVisibility(View.INVISIBLE);
            tvVIP.setText("会员申请");
        } else if ("1".equals(isVip)) {
            rl_viptime.setVisibility(View.VISIBLE);
            tv_vipTime.setText("会员有效期至" + vipdeadline);
            tvVIP.setText("会员续费");
        }
        Glide.with(this).load(headimg).into(ivAvatar);
        dataList = new ArrayList<Map<String, Object>>();
        getData();
        String[] from = {"img", "name"};
        final int[] to = {R.id.home_header_gridView_avatar, R.id.home_header_gridView_header};
        simpleAdapter = new SimpleAdapter(mContext, dataList, R.layout.layout_home_header_gridview_item, from, to);
        //配置适配器
        mGridView.setAdapter(simpleAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < icon.length; i++) {
            Log.d("TAG", "" + icon.length);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icon[i]);
            map.put("name", iconName[i]);
            dataList.add(map);
        }
        return dataList;
    }

    @OnClick({R.id.tv_VIP})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_VIP:
                toActivity(VIPRenewActivity.class);
                break;
        }
    }

}
