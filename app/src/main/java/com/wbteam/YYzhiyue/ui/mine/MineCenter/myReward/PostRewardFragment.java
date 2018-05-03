package com.wbteam.YYzhiyue.ui.mine.MineCenter.myReward;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.reward.PostRewardAdapter;
import com.wbteam.YYzhiyue.base.BaseFragment01;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.model.PostRewardModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.login.Login_RegisterActivity;
import com.wbteam.YYzhiyue.util.MyActivityManager;
import com.wbteam.YYzhiyue.view.CustomDialog01;
import com.wbteam.YYzhiyue.wxapi.PayActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostRewardFragment extends BaseFragment01 implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private int pageNum = 1;
    private static final int PAGE_SIZE = 6;//为什么是6呢？
    private boolean isErr;
    private boolean mLoadMoreEndGone = false; //是否加载更多完毕
    private int mCurrentCounter = 0;
    int TOTAL_COUNTER = 0;
    private PostRewardAdapter mAdapter;
    private PostRewardModel postRewardModel;
    private CustomDialog01 dialog;
    private Bundle bundle;

    @Override
    protected int setContentView() {
        return R.layout.fragment_post_reward;
    }

    @Override
    protected void lazyLoad() {
        initView();
    }


    private void initView() {
        mSwipeRefreshLayout = findViewById(R.id.SwipeRefreshLayout);
        mRecyclerView = findViewById(R.id.RecyclerView);

        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // initBanner();
        initAdapter();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String status = data.get(position).getStatus();
                String id = data.get(position).getId();
                String title = data.get(position).getTitle();
                String create_time = data.get(position).getCreate_time();
                String address = data.get(position).getAddress();
                String amount = data.get(position).getAmount();
                String sex = data.get(position).getSex();
                String attend_count = data.get(position).getAttend_count();
                String limitCount = data.get(position).getLimitCount();
                String tagstr = data.get(position).getTagstr();
                String ordersn = data.get(position).getOrdersn();
                String price = data.get(position).getOrderamount();
                switch (view.getId()) {
                    case R.id.tv_02:
                        if ("0".equals(status)) {
                            //  Log.d("TAG848", "ordersn:" + ordersn + "price" + price);
                            Bundle bundel = new Bundle();
                            bundel.putString("ordersn", ordersn);
                            bundel.putString("price", price);
                            bundel.putString("flag", "1");
                            bundel.putString("vipId", "");

                            toActivity(PayActivity.class, bundel);
                        }
                        if ("1".equals(status)) {
                            bundle = new Bundle();
                            bundle.putString("id", id);
                            bundle.putString("title", title);
                            bundle.putString("create_time", create_time);
                            bundle.putString("address", address);
                            bundle.putString("amount", amount);
                            bundle.putString("sex", sex);
                            bundle.putString("attend_count", attend_count);
                            bundle.putString("limitCount", limitCount);
                            bundle.putString("tagstr", tagstr);
                            bundle.putString("status", status);
                            toActivity(MineRewardDetailsActivity.class, bundle);//确定对象
                        }
                        if ("2".equals(status)) {
                            finish(id);
                        }
                        if ("3".equals(status)) {
                            bundle = new Bundle();
                            bundle.putString("id", id);
                            bundle.putString("title", title);
                            bundle.putString("create_time", create_time);
                            bundle.putString("address", address);
                            bundle.putString("amount", amount);
                            bundle.putString("sex", sex);
                            bundle.putString("attend_count", attend_count);
                            bundle.putString("limitCount", limitCount);
                            bundle.putString("tagstr", tagstr);
                            bundle.putString("status", status);
                            toActivity(ConfirmRewardActivity.class, bundle);//评价
                        }

                        break;
                    case R.id.tv_01:
                        break;
                }
            }
        });
    }

    private void finish(final String id) {
        dialog = new CustomDialog01(getActivity()).builder()
                .setGravity(Gravity.CENTER)//默认居中，可以不设置
                .setTitle("悬赏活动是否已结束？", getResources().getColor(R.color.sd_color_black))//可以不设置标题颜色，默认系统颜色
                .setCancelable(false)
                .setNegativeButton("取消", new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setPositiveButton("确认", getResources().getColor(R.color.sd_color_black), new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Log.d("TAG22", "22");
                        LoaddingShow();
                        RetrofitUtil.getInstance().GetConfirmfinish(ukey, id, new Subscriber<BaseResponse<EmptyEntity>>() {
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
                                Log.d("TAG22", "33");
                                if (baseResponse.ret == 200) {
                                    onRefresh();
//                                    initDate(1, false);
//                                    mSwipeRefreshLayout.setRefreshing(false);
//                                    mAdapter.setEnableLoadMore(true);
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
                });
        dialog.show();

    }

    private List<PostRewardModel.ListBean> data = new ArrayList<>();

    private void initAdapter() {
        mAdapter = new PostRewardAdapter(R.layout.post_reward_listview, data);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        //mAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        initDate(1, false);
        LoaddingShow();
        mCurrentCounter = mAdapter.getData().size();
    }

    private void initDate(int pageNum, final boolean isloadmore) {
        RetrofitUtil.getInstance().Myeventlist(ukey, String.valueOf(pageNum), new Subscriber<BaseResponse<PostRewardModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<PostRewardModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    postRewardModel = baseResponse.getData();
                    Log.d("TAG", postRewardModel.getTotal());
                    Log.d("TAG", postRewardModel.getList().get(0).getCreate_time());
                    List<PostRewardModel.ListBean> list = postRewardModel.getList();
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

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);//下拉刷新的时候关闭上拉加载 之后再打开
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                initDate(1, false);
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
        TOTAL_COUNTER = Integer.valueOf(postRewardModel.getTotal());
        if (mAdapter.getData().size() < PAGE_SIZE) {
            mAdapter.loadMoreEnd(true);
        } else {
            if (mCurrentCounter >= TOTAL_COUNTER) {
                mAdapter.loadMoreEnd(mLoadMoreEndGone);
            } else {
                if (isErr) {
                    pageNum += 1;
                    initDate(pageNum, true);
                    //    mAdapter.addData(data);
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

}
