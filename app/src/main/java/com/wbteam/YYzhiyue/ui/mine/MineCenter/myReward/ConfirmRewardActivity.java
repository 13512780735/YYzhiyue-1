package com.wbteam.YYzhiyue.ui.mine.MineCenter.myReward;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.reward.ConfirmRewardAdapter;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.ApplyPersonModel;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.mine.GradeFragment;
import com.wbteam.YYzhiyue.ui.neaeby.InformationActivity;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Subscriber;


public class ConfirmRewardActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ConfirmRewardAdapter mAdapter;

    private int pageNum = 1;
    private static final int PAGE_SIZE = 6;//为什么是6呢？
    private boolean isErr;
    private boolean mLoadMoreEndGone = false; //是否加载更多完毕
    private int mCurrentCounter = 0;
    int TOTAL_COUNTER = 0;
    private String id, status, title, create_time, address, amount, sex, attend_count, limitCount, tagstr;
    private ApplyPersonModel applyPersonModel;
    private CircleImageView ivAvatar;
    private String headimg, mNickName;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_reward);
        ButterKnife.bind(this);
        headimg = UtilPreference.getStringValue(this, "headimg");
        mNickName = UtilPreference.getStringValue(this, "mNickName");
        id = getIntent().getExtras().getString("id");

        status = getIntent().getExtras().getString("status");
        title = getIntent().getExtras().getString("title");
        create_time = getIntent().getExtras().getString("create_time");
        address = getIntent().getExtras().getString("address");
        amount = getIntent().getExtras().getString("amount");
        sex = getIntent().getExtras().getString("sex");
        attend_count = getIntent().getExtras().getString("attend_count");
        limitCount = getIntent().getExtras().getString("limitCount");
        tagstr = getIntent().getExtras().getString("tagstr");

        setTitle("约会详情");
        setBackView();
        initView();
    }

    TextView tv_status, tv_name, tv_type, tv_address, tv_price, tv_time, tv_number01;

    private void initView() {
        View header = LayoutInflater.from(this).inflate(R.layout.mine_reward_headerview, null);

        header.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ivAvatar = (CircleImageView) header.findViewById(R.id.iv_avatar);
        tv_name = (TextView) header.findViewById(R.id.tv_name);
        tv_status = (TextView) header.findViewById(R.id.tv_status);
        tv_type = (TextView) header.findViewById(R.id.tv_type);
        tv_address = (TextView) header.findViewById(R.id.tv_address);
        tv_price = (TextView) header.findViewById(R.id.tv_price);
        tv_time = (TextView) header.findViewById(R.id.tv_time);
        tv_number01 = (TextView) header.findViewById(R.id.tv_number01);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.SwipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        mAdapter.addHeaderView(header);
        Glide.with(mContext).load(headimg).into(ivAvatar);
        tv_name.setText("发起人：" + mNickName);
        tv_type.setText("类型：" + tagstr);
        tv_address.setText("地点：" + address);
        tv_price.setText("金额：" + amount);
        tv_time.setText("时间：" + create_time);
        tv_number01.setText(attend_count + "/" + limitCount + "人");
        if ("0".equals(status)) {
            tv_status.setText("未付款");
        }
        if ("1".equals(status)) {
            tv_status.setText("待确认");
        } else if ("2".equals(status)) {
            tv_status.setText("进行中");
        } else if ("3".equals(status)) {
            tv_status.setText("待评价");
        } else if ("4".equals(status)) {
            tv_status.setText("已结束");
        }
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            public String rkey;

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                rkey = data.get(position).getUser().getRkey();
                switch (view.getId()) {
                    case R.id.tv_confirm:
                        AppraiseFragment dialogAppraise = new AppraiseFragment();
                        dialogAppraise.show(getSupportFragmentManager(), "GradeFragment");
                        dialogAppraise.setOnDialogListener(new GradeFragment.OnDialogListener() {
                            @Override
                            public void onDialogClick(String person) {
                                Log.d("TAG", person);
                                score = person;
                                Log.d("TAG999", id + rkey + score);
                                LoaddingShow();
                                RetrofitUtil.getInstance().Eventcomment(ukey, id, rkey, score, new Subscriber<BaseResponse<EmptyEntity>>() {
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
                        break;
                    case R.id.group_avatar:
                        String rkey = data.get(position).getUser().getRkey();
                        String nickname = data.get(position).getUser().getNickname();
                        String easemob_id = data.get(position).getUser().getEasemob_id();
                        Bundle bundle = new Bundle();
                        bundle.putString("rkey", rkey);
                        bundle.putString("nickname", nickname);
                        bundle.putString("easemob_id", easemob_id);
                        toActivity(InformationActivity.class, bundle);
                        break;
                }

            }
        });
    }

    private void initAdapter() {
        mAdapter = new ConfirmRewardAdapter(R.layout.confirm_reward_listview, data);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        //mAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        initDate(1, false);
        LoaddingShow();
        mCurrentCounter = mAdapter.getData().size();
    }

    private List<ApplyPersonModel.ListBean> data = new ArrayList<>();

    private void initDate(int pageNum, final boolean isloadmore) {
        Log.d("TAG", "22222" + "id:" + id + "ukey:" + ukey);
        RetrofitUtil.getInstance().MyGetattendmember(ukey, id, String.valueOf(pageNum), new Subscriber<BaseResponse<ApplyPersonModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("TAG", "4444");
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<ApplyPersonModel> baseResponse) {
                Log.d("TAG", "3333");
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    applyPersonModel = baseResponse.getData();
                    Log.d("TAG6666", applyPersonModel.getList().get(0).getUser().getHeight());
                    List<ApplyPersonModel.ListBean> list = applyPersonModel.getList();
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
    public void onResume() {
        super.onResume();
        //onRefresh();
    }

    @Override
    public void onRefresh() {
        mAdapter.setEnableLoadMore(false);//禁止加载
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
        TOTAL_COUNTER = Integer.valueOf(applyPersonModel.getTotal());
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
