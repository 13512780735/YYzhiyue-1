package com.wbteam.YYzhiyue.ui.neaeby;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.neaeby.FriendHeaderAdapter;
import com.wbteam.YYzhiyue.base.BaseFragment01;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.MainAdModel;
import com.wbteam.YYzhiyue.network.api_service.model.MainListFriendModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends BaseFragment01 implements OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    ConvenientBanner mBanner;
    private FriendHeaderAdapter mAdapter;
    private List<MainAdModel> networkImage = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    String TAG = "FriendFragment";
    private MainListFriendModel mainListFriendModel;


    @Override
    protected int setContentView() {
        return R.layout.fragment_friend;
    }

    @Override
    protected void lazyLoad() {
        Log.d("TAG222", "9999");
        initBanner1();
        LoaddingShow();
        initView();
    }

    private void initView() {
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.home_header_banner, null);
        mBanner = (ConvenientBanner) header.findViewById(R.id.banner);
        mBanner.setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getActivity().getWindowManager().getDefaultDisplay().getHeight() / 4));

        mSwipeRefreshLayout = findViewById(R.id.SwipeRefreshLayout);
        mRecyclerView = findViewById(R.id.RecyclerView);

        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        initAdapter();
        mAdapter.addHeaderView(mBanner);


    }

    List<MainAdModel> AdList;

    private void initBanner1() {
        RetrofitUtil.getInstance().Main_AD(ukey, new Subscriber<BaseResponse<ArrayList<MainAdModel>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<ArrayList<MainAdModel>> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    AdList = baseResponse.getData();
                    Log.d("TAG11", AdList.get(0).getImg());
                    networkImage = AdList;
                } else {
                    showProgress(baseResponse.getMsg());
                }
                initBanner();
            }
        });
    }

    private int pageNum = 1;
    private static final int PAGE_SIZE = 6;//为什么是6呢？
    private boolean isErr;
    private boolean mLoadMoreEndGone = false; //是否加载更多完毕
    private int mCurrentCounter = 0;
    int TOTAL_COUNTER = 0;

    private List<MainListFriendModel.ListBean> data = new ArrayList<>();

    private void initDate(int pageNum, final boolean isloadmore) {
        Log.d(TAG, ukey);
        RetrofitUtil.getInstance().MainGetlist(ukey, String.valueOf(pageNum), new Subscriber<BaseResponse<MainListFriendModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<MainListFriendModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    mainListFriendModel = baseResponse.getData();
                    List<MainListFriendModel.ListBean> list = mainListFriendModel.getList();
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
        mAdapter = new FriendHeaderAdapter(R.layout.nearby_friend_gridview_items, data);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        //mAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        initDate(1, false);
        LoaddingShow();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                // Toast.makeText(getActivity(), "onItemClick" + position, Toast.LENGTH_SHORT).show();
                String rkey = data.get(position).getRkey();
                String nickname = data.get(position).getNickname();
                String easemob_id = data.get(position).getEasemob_id();
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
        TOTAL_COUNTER = Integer.valueOf(mainListFriendModel.getTotal());
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

    private void initBanner() {
        Log.d("TAG33", networkImage.get(0).getImg());
        //networkImage = Arrays.asList(images);
        mBanner.setPages(new CBViewHolderCreator<NetWorkImageHolderView>() {
            @Override
            public NetWorkImageHolderView createHolder() {
                return new NetWorkImageHolderView();
            }
        }, AdList)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageIndicator(new int[]{R.drawable.indicator_gray, R.drawable.indicator_red})
                .setOnItemClickListener(this)
                .setScrollDuration(1500);


    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "Banner:" + position, Toast.LENGTH_SHORT).show();
    }


    public class NetWorkImageHolderView implements Holder<MainAdModel> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.rv_header_img, null);
            imageView = (ImageView) view.findViewById(R.id.iv_head);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, MainAdModel data) {
            Log.d("TAG222", "UpdateUI: " + data.getImg());
            //Glide.with(getActivity()).load(data).into(imageView);
            ImageLoader.getInstance().displayImage(data.getImg(), imageView);
        }
    }
}
