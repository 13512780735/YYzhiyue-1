package com.wbteam.YYzhiyue.ui.appointment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbteam.YYzhiyue.Entity.CaseEntity;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.appointment.Recommend01Adpater;
import com.wbteam.YYzhiyue.adapter.appointment.RecommendAdpater;
import com.wbteam.YYzhiyue.base.BaseFragment;
import com.wbteam.YYzhiyue.base.BaseFragment01;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.DatingModel;
import com.wbteam.YYzhiyue.network.api_service.model.WeiboListModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.neaeby.InformationActivity;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.custom_scrollview.HorizontalPageLayoutManager;
import com.wbteam.YYzhiyue.view.custom_scrollview.PagingScrollHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends BaseFragment01 implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    private ImageView ivLeft, ivRight;
    private RecommendAdpater mAdapter;
    PagingScrollHelper scrollHelper = new PagingScrollHelper();
    private HorizontalPageLayoutManager horizontalPageLayoutManager = null;
    private RecyclerView recyclerView01;
    private Recommend01Adpater mAdapter01;

    private DatingModel datingModel;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DatingModel datingModel1;

    @Override
    protected int setContentView() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void lazyLoad() {
        //initDate(1, false);
        //initDate1();//推荐列表
        initDate(false);
        LoaddingShow();
        initView();
    }

    private int pageNum = 1;
    private static final int PAGE_SIZE = 6;//为什么是6呢？
    private boolean isErr;
    private boolean mLoadMoreEndGone = false; //是否加载更多完毕
    private int mCurrentCounter = 0;
    int TOTAL_COUNTER = 0;
    private List<DatingModel.ListBean> data1 = new ArrayList<>();

    private void initDate1(int pageNum, final boolean isloadmore) {
        LoaddingShow();
        RetrofitUtil.getInstance().DatingGetlist(ukey,"0" ,String.valueOf(pageNum), new Subscriber<BaseResponse<DatingModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<DatingModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    datingModel = baseResponse.getData();
                    List<DatingModel.ListBean> list = datingModel.getList();
                    //  initView();
                    if (list != null && list.size() > 0) {
                        if (!isloadmore) {
                            data1 = list;
                        } else {
                            data1.addAll(list);
                        }
                        mAdapter01.setNewData(data1);
                        mAdapter01.notifyDataSetChanged();
                    }
                } else {
                    {
                        if ("Ukey不合法".equals(baseResponse.getMsg())) {
                            showProgress01("您的帐号已在其他设备登录！");
                            return;
                        } else {
                            showProgress(baseResponse.getMsg());
                        }
                    }
                }
            }
        });
    }

    private void initView() {
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.recommend_headerview, null);
        ivLeft = (ImageView) findViewById(R.id.iv_left);
        ivRight = (ImageView) findViewById(R.id.iv_right);
//        ivLeft.setOnClickListener(this);
//        ivRight.setOnClickListener(this);
        horizontalPageLayoutManager = new HorizontalPageLayoutManager(1, 3);
        //滚动adapter
        mAdapter = new RecommendAdpater(R.layout.recommend_gridview_items, data);
        recyclerView = (RecyclerView) header.findViewById(R.id.RecyclerView);

        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        scrollHelper.setUpRecycleView(recyclerView);
        RecyclerView.LayoutManager layoutManager = null;
        layoutManager = horizontalPageLayoutManager;
        if (layoutManager != null) {
            recyclerView.setLayoutManager(layoutManager);
            scrollHelper.updateLayoutManger();
        }
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), "onItemClick" + position, Toast.LENGTH_SHORT).show();
            }
        });
        mSwipeRefreshLayout =  findViewById(R.id.SwipeRefreshLayout);
        recyclerView01 =  findViewById(R.id.RecyclerView01);

        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        recyclerView01.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        initAdapter();
    //   mAdapter01.addHeaderView(header);
        //列表


    }

    private void initAdapter() {
        mAdapter01 = new Recommend01Adpater(R.layout.recommend_gridview01_items, data1);
        mAdapter01.setOnLoadMoreListener(this, recyclerView01);
        //mAdapter.setPreLoadNumber(3);
        recyclerView01.setAdapter(mAdapter01);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        initDate1(1, false);
        LoaddingShow();
        mAdapter01.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                // Toast.makeText(getActivity(), "onItemClick" + position, Toast.LENGTH_SHORT).show();
                String rkey = data1.get(position).getRkey();
                String nickname = data1.get(position).getNickname();
                String easemob_id = data1.get(position).getEasemob_id();
                Bundle bundle = new Bundle();
                bundle.putString("rkey", rkey);
                bundle.putString("nickname", nickname);
                bundle.putString("easemob_id", easemob_id);
                toActivity(InformationActivity.class, bundle);
            }
        });
    }

    @Override
    public void onRefresh() {
        mAdapter01.setEnableLoadMore(false);//下拉刷新的时候关闭上拉加载 之后再打开
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                initDate1(1, false);
                initDate(false);
                isErr = false;
                mCurrentCounter = PAGE_SIZE;//这行不能删除
                pageNum = 1;//页数置为1 才能继续重新加载
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter01.setEnableLoadMore(true);
            }
        }, 2000);


    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        TOTAL_COUNTER = Integer.valueOf(datingModel.getTotal());
        if (mAdapter01.getData().size() < PAGE_SIZE) {
            mAdapter01.loadMoreEnd(true);
        } else {
            if (mCurrentCounter >= TOTAL_COUNTER) {
                mAdapter01.loadMoreEnd(mLoadMoreEndGone);
            } else {
                if (isErr) {
                    pageNum += 1;
                    initDate1(pageNum, true);
                    mCurrentCounter = mAdapter01.getData().size();
                    mAdapter01.loadMoreComplete();
                } else {
                    isErr = true;
                    // Toast.makeText(getContext(), "错误", Toast.LENGTH_LONG).show();
                    mAdapter01.loadMoreFail();
                }
            }
            mSwipeRefreshLayout.setEnabled(true);
        }
    }
    private List<DatingModel.ListBean> data= new ArrayList<>();
    private void initDate(final boolean isloadmore) {
        Log.d("TAG","11");
        RetrofitUtil.getInstance().DatingGetlist(ukey,"1" ,String.valueOf(pageNum), new Subscriber<BaseResponse<DatingModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<DatingModel> baseResponse) {      Log.d("TAG","22");

                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    datingModel1 = baseResponse.getData();
                    List<DatingModel.ListBean> list1 = datingModel1.getList();
                    //  initView();
                    if (list1 != null && list1.size() > 0) {
                        if (!isloadmore) {
                        data = list1;
                        } else {
                       data.addAll(list1);
                      }
                        Log.d("TAG",data.toString());
                        mAdapter.setNewData(data);
                        mAdapter.notifyDataSetChanged();
                  }
                } else {
                    {
                        if ("Ukey不合法".equals(baseResponse.getMsg())) {
                            showProgress01("您的帐号已在其他设备登录！");
                            return;
                        } else {
                            showProgress(baseResponse.getMsg());
                        }
                    }
                }
            }
        });
//        for (int i = 0; i < 9; i++) {
//            CaseEntity caseEntity = new CaseEntity();
//            caseEntity.setUrl(i + "");
//            data.add(caseEntity);
//        }
    }

}
