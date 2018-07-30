package com.wbteam.YYzhiyue.ui.message;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.message.MesRankingAdapter;
import com.wbteam.YYzhiyue.base.BaseFragment01;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.MyfollowModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.neaeby.InformationActivity;
import com.wbteam.YYzhiyue.util.ToastUtil;
import com.wbteam.YYzhiyue.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MesRankingFragment extends BaseFragment01 implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private MesRankingAdapter mAdapter;
    private int pageNum = 1;
    private static final int PAGE_SIZE = 1;//为什么是6呢？
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false; //是否加载更多完毕
    private int mCurrentCounter = 0;
    int TOTAL_COUNTER = 0;
    //private LoadingDialog loading;
    private MyfollowModel myfollowModel;
    private List<MyfollowModel.ListBean> list;


    @Override
    protected int setContentView() {
        return R.layout.fragment_mes_ranking;
    }

    @Override
    protected void lazyLoad() {
        //initDate(1, false);
        initView();
    }

    private void initView() {
        mSwipeRefreshLayout = findViewById(R.id.SwipeRefreshLayout);
        mRecyclerView = findViewById(R.id.RecyclerView);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new MesRankingAdapter(R.layout.mesrank_listview_items, data);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        //mAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.disableLoadMoreIfNotFullPage();
        mSwipeRefreshLayout.setOnRefreshListener(this);


        //   mCurrentCounter = mAdapter.getData().size();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                // Toast.makeText(getActivity(), "onItemClick" + position, Toast.LENGTH_SHORT).show();
                String rkey = list.get(position).getRkey();
                String nickname = list.get(position).getNickname();
                String easemob_id = list.get(position).getEasemob_id();
                String headimg = list.get(position).getHeadimg();
                Bundle bundle = new Bundle();
                bundle.putString("rkey", rkey);
                bundle.putString("nickname", nickname);
                bundle.putString("easemob_id", easemob_id);
                bundle.putString("headimg", headimg);
                toActivity(InformationActivity.class, bundle);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
       // ToastUtil.showS(getActivity(), "1");
        //onRefresh();
        initDate(1, false);
      //  mAdapter.notifyDataSetChanged();
        // onRefresh();
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
        TOTAL_COUNTER = Integer.valueOf(myfollowModel.getTotal());
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

    private List<MyfollowModel.ListBean> data = new ArrayList<>();

    private void initDate(int pageNum, final boolean isloadmore) {
       /// loading = new LoadingDialog(getActivity());
      //  loading.show();
        LoaddingShow();
        RetrofitUtil.getInstance().UserMyfollowlist(ukey, String.valueOf(pageNum), new Subscriber<BaseResponse<MyfollowModel>>() {
            @Override

            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<MyfollowModel> baseResponse) {
              LoaddingDismiss();
                Log.d("TAG3333", baseResponse.ret + "");
                Log.d("TAG222", baseResponse.getData().getTotal() + "");
                if (baseResponse.ret == 200) {
                    if("0".equals(baseResponse.getData().getTotal())){
                        data.clear();
                        mAdapter.notifyDataSetChanged();
                    }
                    myfollowModel = baseResponse.getData();
                    list = myfollowModel.getList();
                    if (list != null && list.size() > 0) {
                        if (!isloadmore) {
                            data = list;
                        } else {
                            data.addAll(list);
                        }
                        mAdapter.setNewData(data);

                        mAdapter.notifyDataSetChanged();
                    }
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
}
