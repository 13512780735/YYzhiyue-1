package com.wbteam.YYzhiyue.ui.reward;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.reward.RewardReAdapter;
import com.wbteam.YYzhiyue.base.BaseFragment01;
import com.wbteam.YYzhiyue.event.IsVipEvent;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.RewardModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.login.Login_RegisterActivity;
import com.wbteam.YYzhiyue.ui.mine.MineCenter.VIPRenewActivity;
import com.wbteam.YYzhiyue.ui.mine.SelectScopeActivity;
import com.wbteam.YYzhiyue.util.MyActivityManager;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.CustomDialog01;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReRewardFragment extends BaseFragment01 implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private static final int REQUEST_REGION = 4;//返回内容标识


    private RewardReAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private int pageNum = 1;
    private static final int PAGE_SIZE = 6;//为什么是6呢？
    private boolean isErr;
    private boolean mLoadMoreEndGone = false; //是否加载更多完毕
    private int mCurrentCounter = 0;
    int TOTAL_COUNTER = 0;
    private RewardModel rewardModel;
    private ImageView ivAdd;
    private RelativeLayout rlType;
    private TextView tvType;
    private String tagstr;
    private String tagid;
    private CustomDialog01 dialog;

    @Override
    protected int setContentView() {
        return R.layout.fragment_re_reward;
    }

    @Override
    protected void lazyLoad() {
        initView();
    }



    private void initView() {
        rlType = findViewById(R.id.rl_type);
        tvType = findViewById(R.id.tv_type);
        mSwipeRefreshLayout = findViewById(R.id.SwipeRefreshLayout);
        mRecyclerView = findViewById(R.id.RecyclerView);
        ivAdd = findViewById(R.id.iv_add);

        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        // initBanner();
        initAdapter();
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isvip=UtilPreference.getStringValue(getActivity(),"isvip");
                if ("0".equals(isvip)) {
                    dialog = new CustomDialog01(getActivity()).builder()
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
                                    // UtilPreference.saveString(getActivity(), "paykey", "5");
                                    toActivity(VIPRenewActivity.class);
                                }
                            });
                    dialog.show();
                } else {
                    toActivity(CreatRewardActivity.class);
                }
                //toActivity(CreatRewardActivity.class);
            }
        });
        rlType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getContext(), SelectScopeActivity.class);
                intent1.putExtra("flag", "2");
                startActivityForResult(intent1, REQUEST_REGION);
            }
        });

    }

    private List<RewardModel.ListBean> data = new ArrayList<>();

    private void initDate(int pageNum, final boolean isloadmore) {
        RetrofitUtil.getInstance().Geteventlist(ukey, String.valueOf(pageNum), new Subscriber<BaseResponse<RewardModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<RewardModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    rewardModel = baseResponse.getData();
                    Log.d("TAG", rewardModel.getTotal());
                    Log.d("TAG", rewardModel.getList().get(0).getCreate_time());
                    List<RewardModel.ListBean> list = rewardModel.getList();
                    if (list != null && list.size() > 0) {
                        if (!isloadmore) {
                            data = list;
                        } else {
                            data.addAll(list);
                        }
                        mAdapter.setNewData(data);
                        mAdapter.notifyDataSetChanged();
                    }
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

    private void initAdapter() {
        mAdapter = new RewardReAdapter(R.layout.reward_listview_items, data);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        //mAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(mAdapter);
        initDate(1, false);
        LoaddingShow();
        mCurrentCounter = mAdapter.getData().size();
    }

    @Override
    public void onResume() {
        super.onResume();
        //onRefresh();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);//禁止加载
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                //initDate(1, false);
                isErr = false;
                mCurrentCounter = PAGE_SIZE;//这行不能删除
                pageNum = 1;//页数置为1 才能继续重新加载
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.setEnableLoadMore(true);
            }
        }, 2000);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        TOTAL_COUNTER = Integer.valueOf(rewardModel.getTotal());
        if (mAdapter.getData().size() < PAGE_SIZE) {
            mAdapter.loadMoreEnd(true);
        } else {
            if (mCurrentCounter >= TOTAL_COUNTER) {
                mAdapter.loadMoreEnd(mLoadMoreEndGone);
            } else {
                if (isErr) {
                    pageNum += 1;
                    initDate(pageNum, true);
                    mCurrentCounter = mAdapter.getData().size();
                    mAdapter.loadMoreComplete();
                } else {
                    isErr = true;
                    // Toast.makeText(getContext(), "错误", Toast.LENGTH_LONG).show();
                    mAdapter.loadMoreFail();
                }
            }
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_REGION) {
            if (data != null) {
                tagstr = data.getStringExtra("data");//标签
                tagid = data.getStringExtra("dataid");
                tvType.setText(tagstr);
            }
        }
    }
}
