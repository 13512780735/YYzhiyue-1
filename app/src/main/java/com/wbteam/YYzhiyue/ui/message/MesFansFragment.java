package com.wbteam.YYzhiyue.ui.message;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbteam.YYzhiyue.Entity.CaseEntity;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.message.MesRankingAdapter;
import com.wbteam.YYzhiyue.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MesFansFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener{
    private MesRankingAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<CaseEntity> data;
    private static final int TOTAL_COUNTER = 18;
    private static final int PAGE_SIZE = 6;
    private static final int DELAY_MILLIS = 1000;
    private int mCurrentCounter = 0;

    private boolean isErr;
    private boolean mLoadMoreEndGone = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_mes_fans);
        initView();
        return getContentView();
    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.SwipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initDate();
        initAdapter();
    }

    private void initAdapter() {
       // mAdapter = new MesRankingAdapter(R.layout.mesrank_listview_items, data);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        //mAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(mAdapter);
        mCurrentCounter = mAdapter.getData().size();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), "onItemClick" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDate() {
        data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CaseEntity caseEntity = new CaseEntity();
            caseEntity.setUrl(i + "");
            data.add(caseEntity);
        }
    }

    @Override
    public void onRefresh() {
//        mAdapter.setEnableLoadMore(false);//禁止加载
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mAdapter.setNewData(data);
//                isErr = false;
//                mCurrentCounter = PAGE_SIZE;
//                mSwipeRefreshLayout.setRefreshing(false);
//                mAdapter.setEnableLoadMore(true);//启用加载
//            }
//        }, DELAY_MILLIS);
    }

    @Override
    public void onLoadMoreRequested() {
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
    }

}
