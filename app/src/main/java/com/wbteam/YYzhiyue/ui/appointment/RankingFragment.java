package com.wbteam.YYzhiyue.ui.appointment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbteam.YYzhiyue.Entity.CaseEntity;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.appointment.Recommend01Adpater;
import com.wbteam.YYzhiyue.adapter.appointment.RecommendAdpater;
import com.wbteam.YYzhiyue.base.BaseFragment;
import com.wbteam.YYzhiyue.base.BaseFragment01;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankingFragment extends BaseFragment01  {
    //private List<Lis> data;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private Recommend01Adpater mAdapter;
    private RadioGroup rg_rank;


    private int pageNum = 1;
    private static final int PAGE_SIZE = 6;//为什么是6呢？
    private boolean isErr;
    private boolean mLoadMoreEndGone = false; //是否加载更多完毕
    private int mCurrentCounter = 0;
    int TOTAL_COUNTER = 0;

    @Override
    protected int setContentView() {
        return R.layout.fragment_ranking;
    }

    @Override
    protected void lazyLoad() {
        //initDate(1, false);
        initView();
    }
    private void initView() {
//        rg_rank= (RadioGroup) findViewById(R.id.rank_rgTools);
//        rg_rank.setOnCheckedChangeListener(this);
//        //列表
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.SwipeRefreshLayout);
//        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        initDate();
//        initAdapter();
    }

//    private void initDate() {
//        data = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            CaseEntity caseEntity = new CaseEntity();
//            caseEntity.setUrl(i + "");
//            data.add(caseEntity);
//        }
//    }

//    private void initAdapter() {
//        mAdapter = new RecommendAdpater(R.layout.recommend_gridview01_items, data);
//        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
//        // mAdapter.setPreLoadNumber(3);
//        mRecyclerView.setAdapter(mAdapter);
//        mCurrentCounter = mAdapter.getData().size();
//        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Toast.makeText(getActivity(), "onItemClick" + position, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    @Override
//    public void onRefresh() {
//        mAdapter.setEnableLoadMore(false);//禁止加载
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mAdapter.setNewData(data);
//                //  isErr = false;
//                isErr = true;
//                mCurrentCounter = PAGE_SIZE;
//                //mSwipeRefreshLayout.setRefreshing(false);
//                mAdapter.setEnableLoadMore(true);//启用加载
//            }
//        }, 2000);
//    }
//
//    @Override
//    public void onLoadMoreRequested() {
//        mSwipeRefreshLayout.setEnabled(false);
//        if (mAdapter.getData().size() < PAGE_SIZE) {
//            mAdapter.loadMoreEnd(true);
//        } else {
//            if (mCurrentCounter >= TOTAL_COUNTER) {
//                //pullToRefreshAdapter.loadMoreEnd();//default visible
//                mAdapter.loadMoreEnd(mLoadMoreEndGone);//true is gone,false is visible
//            } else {
//                if (isErr) {
//                    mAdapter.addData(data);
//                    mCurrentCounter = mAdapter.getData().size();
//                    mAdapter.loadMoreComplete();
//                } else {
//                    isErr = true;
//                    Toast.makeText(getContext(), "错误", Toast.LENGTH_LONG).show();
//                    mAdapter.loadMoreFail();
//                }
//            }
//            mSwipeRefreshLayout.setEnabled(true);
//        }
//    }

//    @Override
//    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//        switch (checkedId){
//            case R.id.bt_rank01:
//                break;
//            case R.id.bt_rank02:
//                break;
//            case R.id.bt_rank03:
//                break;
//            case R.id.bt_rank04:
//                break;
//        }
//    }
}
