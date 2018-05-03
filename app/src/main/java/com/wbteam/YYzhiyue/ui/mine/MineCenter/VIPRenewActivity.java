package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.VIPGradeAdapter;
import com.wbteam.YYzhiyue.adapter.reward.RewardReAdapter;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.PostRewardModel;
import com.wbteam.YYzhiyue.network.api_service.model.RewardModel;
import com.wbteam.YYzhiyue.network.api_service.model.VideoModel;
import com.wbteam.YYzhiyue.network.api_service.model.VipModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.wxapi.PayActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class VIPRenewActivity extends BaseActivity {
    @BindView(R.id.RecyclerView)
    RecyclerView mRecyclerView;
    private VIPGradeAdapter mAdapter;
    private VipModel vipModel;
    private String price;
    private String vipId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viprenew);
        ButterKnife.bind(this);
        setBackView();
        setTitle("会员中心");
        setRightText("会员协议", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initView();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                price = data.get(position).getAmount();
                vipId=data.get(position).getId();
                switch (view.getId()) {
                    case R.id.tv_price:
                        Bundle bundel = new Bundle();
                        bundel.putString("flag", "3");//购买会员
                        bundel.putString("ordersn", "");
                        bundel.putString("vipId", vipId);
                        bundel.putString("price", price);
                        toActivity(PayActivity.class, bundel);
                        break;
                }
            }
        });

    }

    private List<VipModel.ListBean> data = new ArrayList<>();

    private void initAdapter() {
        mAdapter = new VIPGradeAdapter(R.layout.vip_renew_listiview, data);
        mRecyclerView.setAdapter(mAdapter);
        initData();
        LoaddingShow();
    }

    private void initData() {
        RetrofitUtil.getInstance().Viplist(ukey, new Subscriber<BaseResponse<VipModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<VipModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    vipModel = baseResponse.getData();
                    Log.d("TAG", vipModel.getList().toString());
                    List<VipModel.ListBean> list = vipModel.getList();
                    data = list;
                    //data.addAll(list);
                    mAdapter.setNewData(data);
                    mAdapter.notifyDataSetChanged();
                } else

                {
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
