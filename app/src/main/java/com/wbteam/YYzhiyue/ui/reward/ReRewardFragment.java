package com.wbteam.YYzhiyue.ui.reward;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbteam.YYzhiyue.Entity.UserInfoModel;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.reward.RewardReAdapter;
import com.wbteam.YYzhiyue.adapter.reward.RewardTagsAdapter;
import com.wbteam.YYzhiyue.base.BaseFragment01;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.RewardModel;
import com.wbteam.YYzhiyue.network.api_service.model.TagModel;
import com.wbteam.YYzhiyue.network.api_service.model.TagModel1;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.mine.MineCenter.VIPRenewActivity;
import com.wbteam.YYzhiyue.ui.mine.MineCenter.ViedeoAuthenticationActivity;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.CustomDialog01;
import com.wbteam.YYzhiyue.view.MyGridView;


import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReRewardFragment extends BaseFragment01 implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
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
    private String tagstr;
    private String tagid;
    private CustomDialog01 dialog;
    private String videoauth;
    private CustomDialog01 dialog1;
    private String permissions;
    private MyGridView mGridview;
    private RewardTagsAdapter mTagAdapter;
    private List<TagModel1.ListBean> tagList = null;
    private List<TagModel1.ListBean> tags;
    private int flag;  //1的时候点击标签过去创建，2的时候是直接点击创建按钮
    private String tag;

    @Override
    protected int setContentView() {
        return R.layout.fragment_re_reward;
    }

    @Override
    protected void lazyLoad() {
        permissions = UtilPreference.getStringValue(getActivity(), "permissions");
        initTags();
        LoaddingShow();

    }

    private void initTags() {
        RetrofitUtil.getInstance().getGettaglist1(ukey, new Subscriber<BaseResponse<TagModel1>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<TagModel1> baseResponse) {
                //  Log.d("TAG2215", baseResponse.getData().getList().size() + "");

                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    tags = baseResponse.getData().getList();
                    TagModel1 tagModel1 = baseResponse.getData();
                    initView();
                } else {
                    showProgress(baseResponse.getMsg());
                }

            }

        });
    }


    private void initView() {
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.items_reward_tags_gridview, null);
        mGridview = (MyGridView) header.findViewById(R.id.tag_gridview);

        mSwipeRefreshLayout = findViewById(R.id.SwipeRefreshLayout03);
        mRecyclerView = findViewById(R.id.RecyclerView);
        ivAdd = findViewById(R.id.iv_add);

        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // initBanner();
        initAdapter();

        mAdapter.addHeaderView(header);
        mTagAdapter = new RewardTagsAdapter(getActivity(), tags);
        mGridview.setAdapter(mTagAdapter);
        mTagAdapter.notifyDataSetChanged();
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                flag=1;
                tag=tags.get(position).getTitle();
                initUserInfo();
            }
        });
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=2;
                tag="";
                initUserInfo();

            }
        });
//        rlType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(getContext(), SelectScopeActivity.class);
//                intent1.putExtra("flag", "2");
//                startActivityForResult(intent1, REQUEST_REGION);
//            }
//        });
        if ("1".equals(permissions)) {
            ivAdd.setVisibility(View.GONE);
            mGridview.setVisibility(View.GONE);
        } else {
            ivAdd.setVisibility(View.VISIBLE);
            mGridview.setVisibility(View.VISIBLE);
        }
    }

    private void initUserInfo() {
        RetrofitUtil.getInstance().getUserGetmy(ukey, new Subscriber<BaseResponse<UserInfoModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<UserInfoModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    Log.d("TAG21", baseResponse.getData().getInfo().getHeadimg());
                    Log.e(TAG, baseResponse.getData().getInfo().getExist_parent());
                    UtilPreference.saveString(getActivity(), "videoauth", baseResponse.getData().getInfo().getVideoauth());
                    UtilPreference.saveString(getActivity(), "auth", baseResponse.getData().getInfo().getAuth());
                    UtilPreference.saveString(getActivity(), "isvip", baseResponse.getData().getInfo().getIsvip());
                    //   mUserInfoModel= JSON.parseObject(baseResponse.getData().toString(),UserInfoModel.class);
                    toCreate();
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

    private void toCreate() {
        videoauth = UtilPreference.getStringValue(getActivity(), "videoauth");
        if ("1".equals(videoauth)) {
            isvip = UtilPreference.getStringValue(getActivity(), "isvip");
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
                Bundle bundle=new Bundle();
                bundle.putString("tag",tag);
                toActivity(CreatRewardActivity.class,bundle);
            }
        } else {
            dialog1 = new CustomDialog01(getActivity()).builder()
                    .setGravity(Gravity.CENTER)
                    .setTitle("是否视频认证", getResources().getColor(R.color.sd_color_black))
                    .setCancelable(false)
                    .setNegativeButton("否", new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setPositiveButton("是", getResources().getColor(R.color.sd_color_black), new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
                        @Override
                        public void onClick(View view) {
                            dialog1.dismiss();
                            // UtilPreference.saveString(getActivity(), "paykey", "5");
                            toActivity(ViedeoAuthenticationActivity.class);
                        }
                    });
            dialog1.show();

        }
    }

    private List<RewardModel.ListBean> data = new ArrayList<>();

    private void initDate(int pageNum, final boolean isloadmore) {
        LoaddingShow();
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
        mSwipeRefreshLayout.setOnRefreshListener(this);
        // initDate(1, false);

        // mCurrentCounter = mAdapter.getData().size();

    }

    //
    @Override
    public void onResume() {
        super.onResume();
        //onRefresh();
        initDate(1, false);
        //onRefresh();
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);//下拉刷新的时候关闭上拉加载 之后再打开
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                //  initDate(1, false);
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
                //  tvType.setText(tagstr);
            }
        }
    }
}
