package com.wbteam.YYzhiyue.ui.appointment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.Onlookers.OnlookersAdapter;
import com.wbteam.YYzhiyue.base.BaseFragment01;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.OnLookerModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnlookersFragment extends BaseFragment01 implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private int pageNum = 1;
    private static final int PAGE_SIZE = 1;//为什么是6呢？
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false; //是否加载更多完毕
    private int mCurrentCounter = 0;
    int TOTAL_COUNTER = 0;

    private OnlookersAdapter mAdapter;
    private OnLookerModel onLookerModel;


    @Override
    protected int setContentView() {
        return R.layout.fragment_onlookers;
    }

    @Override
    protected void lazyLoad() {
        initView();
    }

    private void initView() {
        //列表
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.SwipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        //mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        initAdapter();
    }


    private void initAdapter() {
        mAdapter = new OnlookersAdapter(R.layout.onlookers_gridview_items, data);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.disableLoadMoreIfNotFullPage();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        initDate(1, false);
        LoaddingShow();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("TAG33", data.get(position).getVideo_url());
                Bundle bundel = new Bundle();
                bundel.putString("data", data.get(position).getVideo_url());
                bundel.putString("imgUrl", data.get(position).getPhoto_url());
                toActivity(OnlookersVideo1Activity.class, bundel);
            }
        });
    }

    private List<OnLookerModel.ListBean> data = new ArrayList<>();

    private void initDate(int pageNum, final boolean isloadmore) {
        RetrofitUtil.getInstance().VideoGetvideolist(ukey, String.valueOf(pageNum), new Subscriber<BaseResponse<OnLookerModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<OnLookerModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    onLookerModel = baseResponse.getData();
                    List<OnLookerModel.ListBean> list = onLookerModel.getList();
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
        TOTAL_COUNTER = Integer.valueOf(onLookerModel.getTotal());
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
}