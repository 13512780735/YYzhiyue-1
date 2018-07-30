package com.wbteam.YYzhiyue.ui.mine.MineCenter.myReward;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.reward.AddRewardAdapter;
import com.wbteam.YYzhiyue.adapter.reward.PostRewardAdapter;
import com.wbteam.YYzhiyue.base.BaseFragment01;
import com.wbteam.YYzhiyue.network.api_service.model.AddRewardModel;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.model.PostRewardModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.login.Login_RegisterActivity;
import com.wbteam.YYzhiyue.ui.mine.GradeFragment;
import com.wbteam.YYzhiyue.util.MyActivityManager;
import com.wbteam.YYzhiyue.view.CustomDialog01;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddRewardFragment extends BaseFragment01 implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private int pageNum = 1;
    private static final int PAGE_SIZE = 1;//为什么是6呢？
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false; //是否加载更多完毕
    private int mCurrentCounter = 0;
    int TOTAL_COUNTER = 0;
    private AddRewardAdapter mAdapter;
    private AddRewardModel addRewardModel;
    private CustomDialog01 dialog;
    private String score;

    @Override
    protected int setContentView() {
        return R.layout.fragment_add_reward;
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
                String isbid = data.get(position).getIsbid();
                String iscomment = data.get(position).getIscomment();
                final String id = data.get(position).getId();
                final String rkey = data.get(position).getUser().getRkey();
                final String status = data.get(position).getStatus();
                switch (view.getId()) {
                    case R.id.tv_confirm:
                        if ("0".equals(isbid)) {
                            //取消悬赏
                            cancle(id);
                            LoaddingShow();
                        } else if ("1".equals(isbid)) {
                            //点击评价
                            if ("3".equals(status)) {
                                AppraiseFragment dialogAppraise = new AppraiseFragment();
                                dialogAppraise.show(getFragmentManager(), "GradeFragment");
                                dialogAppraise.setOnDialogListener(new AppraiseFragment.OnDialogListener() {
                                    @Override
                                    public void onDialogClick(String person, String remark) {
                                        Log.d("TAG", person);
                                        score = person;
                                        Log.d("TAG999", score);
                                        LoaddingShow();
                                        RetrofitUtil.getInstance().Eventcomment(ukey, id, rkey, score, remark, new Subscriber<BaseResponse<EmptyEntity>>() {
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
                                                    onRefresh();
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
                            } else if ("2".equals(status)) {
                                RetrofitUtil.getInstance().Wentto(ukey, id, new Subscriber<BaseResponse<EmptyEntity>>() {
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
                                            showProgress(baseResponse.getMsg());
                                        }
                                    }
                                });
                            }
                        }
                        break;
                }
            }
        });
    }

    private void cancle(String id) {
        RetrofitUtil.getInstance().Cancelattend(ukey, id, new Subscriber<BaseResponse<EmptyEntity>>() {
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
                    onRefresh();
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

    private List<AddRewardModel.ListBean> data = new ArrayList<>();

    private void initAdapter() {
        mAdapter = new AddRewardAdapter(R.layout.add_reward_listview, data);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        //mAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.disableLoadMoreIfNotFullPage();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        initDate(1, false);
        LoaddingShow();
        mCurrentCounter = mAdapter.getData().size();
    }

    private void initDate(int pageNum, final boolean isloadmore) {
        RetrofitUtil.getInstance().Myattendlist(ukey, String.valueOf(pageNum), new Subscriber<BaseResponse<AddRewardModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<AddRewardModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    addRewardModel = baseResponse.getData();
                    Log.d("TAG", addRewardModel.getTotal());
                    Log.d("TAG", addRewardModel.getList().get(0).getCreate_time());
                    List<AddRewardModel.ListBean> list = addRewardModel.getList();
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
        //onRefresh();
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);//下拉刷新的时候关闭上拉加载 之后再打开
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                initDate(1, false);
                isErr = true;
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
        TOTAL_COUNTER = Integer.valueOf(addRewardModel.getTotal());
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
