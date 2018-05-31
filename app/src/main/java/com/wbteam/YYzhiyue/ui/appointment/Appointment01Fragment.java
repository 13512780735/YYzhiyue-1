package com.wbteam.YYzhiyue.ui.appointment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.appointment.AppiontmentAdpater;
import com.wbteam.YYzhiyue.base.BaseFragment01;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.model.WeiboListModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.mine.PostActivity;
import com.wbteam.YYzhiyue.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class Appointment01Fragment extends BaseFragment01 implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private AppiontmentAdpater mAdapter;
    private int pageNum = 1;
    private static final int PAGE_SIZE = 6;//为什么是6呢？
    private boolean isErr;
    private boolean mLoadMoreEndGone = false; //是否加载更多完毕
    private int mCurrentCounter = 0;
    int TOTAL_COUNTER = 0;
    private WeiboListModel weiboListModel;
    private LoadingDialog loading;
    private ImageView iv_add;
    private List<WeiboListModel.ListBean> list;

    @Override
    protected int setContentView() {
        return R.layout.fragment_appointment01;
    }

    @Override
    protected void lazyLoad() {
        //initDate(1, false);
        initView();
    }


    private void initView() {
        iv_add = (ImageView) findViewById(R.id.iv_add);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.SwipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // initDate();
        initAdapter();
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("postKey", "two");
                toActivity(PostActivity.class, bundle);
            }
        });
    }

    private void initAdapter() {
        mAdapter = new AppiontmentAdpater(R.layout.appointment01_listview_items, data);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        // mAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        initDate(1, false);
        loading = new LoadingDialog(getActivity());
        loading.show();
      //  mCurrentCounter = mAdapter.getData().size();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                // Toast.makeText(getActivity(), "onItemClick" + position, Toast.LENGTH_SHORT).show();
                String rkey = list.get(position).getUser().getRkey();
                String nickname = list.get(position).getUser().getNickname();
                String wid = list.get(position).getId();
                String easemob_id = list.get(position).getUser().getEasemob_id();
                Bundle bundle = new Bundle();
                bundle.putString("wid", wid);
                bundle.putString("easemob_id", easemob_id);
//                        bundle.putString("nickname", nickname);
//                        bundle.putString("sex", list.get(position).getUser().getSex());
//                        bundle.putString("time", list.get(position).getSelect_time());
//                        bundle.putString("time01", list.get(position).getInterval_time());
//                        bundle.putString("like_count", list.get(position).getLike_count());
//                        bundle.putString("address", list.get(position).getCityname());
//                        bundle.putString("address01", list.get(position).getUser().getCityname());
                toActivity(AppointmentInforActivity.class, bundle);
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_praise:
                        LoaddingShow();
                        RetrofitUtil.getInstance().GetLike(ukey, list.get(position).getId(), new Subscriber<BaseResponse<EmptyEntity>>() {
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
                                    showProgress(baseResponse.getMsg());
                                    onRefresh();
                                } else {
                                    showProgress(baseResponse.getMsg());
                                }
                            }
                        });
                        break;
                }
            }
        });
    }

    private List<WeiboListModel.ListBean> data = new ArrayList<>();

    private void initDate(int pageNum, final boolean isloadmore) {
        RetrofitUtil.getInstance().Getweibolist(ukey, String.valueOf(pageNum), new Subscriber<BaseResponse<WeiboListModel>>() {
            @Override

            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                loading.dismiss();
            }

            @Override
            public void onNext(BaseResponse<WeiboListModel> baseResponse) {
                loading.dismiss();
                if (baseResponse.ret == 200) {
                    weiboListModel = baseResponse.getData();
                    list = weiboListModel.getList();
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



    @Override
    public void onRefresh() {

        mAdapter.setEnableLoadMore(false);//禁止加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // mAdapter.setNewData(data);
                initDate(1, false);
                isErr = false;
                mCurrentCounter = PAGE_SIZE;
                pageNum = 1;//页数置为1 才能继续重新加载
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.setEnableLoadMore(true);//启用加载
            }
        }, 2000);
    }

    @Override
    public void onLoadMoreRequested() {

        mSwipeRefreshLayout.setEnabled(false);
        TOTAL_COUNTER = Integer.valueOf(weiboListModel.getTotal());

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
