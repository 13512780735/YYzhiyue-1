package com.wbteam.YYzhiyue.ui.appointment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.appointment.AppointmentInfoAdapter;
import com.wbteam.YYzhiyue.adapter.appointment.Recommend01Adpater;
import com.wbteam.YYzhiyue.adapter.appointment.WeiboCommentAdapter;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.DatingInfoModel;
import com.wbteam.YYzhiyue.network.api_service.model.DatingModel;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.model.GetweiboinfoModel;
import com.wbteam.YYzhiyue.network.api_service.model.WeiboCommentModel;
import com.wbteam.YYzhiyue.network.api_service.model.WeiboCommentModel01;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.util.StringUtil;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.CircleImageView;
import com.wbteam.YYzhiyue.view.custom_scrollview.HorizontalPageLayoutManager;
import com.wbteam.YYzhiyue.view.custom_scrollview.PagingScrollHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class AppointmentInforActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    @BindView(R.id.ed_input)
    EditText ed_input;
    @BindView(R.id.tv_send)
    TextView tv_send;
    CircleImageView ivAvatar;
    TextView tvFriend_name;
    TextView tvFriend_gender;
    TextView tvFriend_sign;
    TextView tv_time;
    TextView tv_time01;
    TextView tv_address;
    TextView tv_number;
    TextView tv_content;
    RecyclerView mRecyclerView;
    TextView tv_address01;
    TextView tv_message;
    TextView tv_praise;
    private ImageView iv_praise;
    private GetweiboinfoModel getweiboinfoModel;
    private String username;
    private String Myeasemob_id;
    private String rkey;
    private List<GetweiboinfoModel.InfoBean.PicBean> data01;
    private AppointmentInfoAdapter mAdapter;
    private String wid;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView01;
    private WeiboCommentAdapter mAdapter01;
    private WeiboCommentModel weiboCommentModel;
    private LinearLayout ll_praise;
    private String message = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_infor);
        setBackView();
        setTitle("约会详情");
        ButterKnife.bind(this);
        Myeasemob_id = UtilPreference.getStringValue(mContext, "easemob_id");
        wid = getIntent().getExtras().getString("wid");
        easemob_id = getIntent().getExtras().getString("easemob_id");
        initData();
        LoaddingShow();
    }

    private void initData() {
        RetrofitUtil.getInstance().Weibo_Getweiboinfo(ukey, wid, new Subscriber<BaseResponse<GetweiboinfoModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<GetweiboinfoModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    getweiboinfoModel = baseResponse.getData();
//                    username = datingInfoModel.getInfo().getEasemob_id();
//                    to_nicheng = datingInfoModel.getInfo().getNickname();

                } else {
                    if ("Ukey不合法".equals(baseResponse.getMsg())) {
                        showProgress01("您的帐号已在其他设备登录！");
                        return;
                    } else {
                        showProgress(baseResponse.getMsg());
                    }
                }
                initView();
            }
        });
    }

    private void initView() {
        tv_send.setOnClickListener(this);
        View header = LayoutInflater.from(this).inflate(R.layout.weibo_details_headview, null);
        ivAvatar = (CircleImageView) header.findViewById(R.id.iv_avatar);
        tvFriend_name = (TextView) header.findViewById(R.id.friend_name);
        tvFriend_gender = (TextView) header.findViewById(R.id.friend_gender);
        tvFriend_sign = (TextView) header.findViewById(R.id.friend_sign);
        tv_time = (TextView) header.findViewById(R.id.tv_time);
        tv_time01 = (TextView) header.findViewById(R.id.tv_time01);
        tv_address = (TextView) header.findViewById(R.id.tv_address);
        tv_number = (TextView) header.findViewById(R.id.tv_number);
        tv_content = (TextView) header.findViewById(R.id.tv_content);
        mRecyclerView = (RecyclerView) header.findViewById(R.id.RecyclerView);
        tv_address01 = (TextView) header.findViewById(R.id.tv_address01);
        tv_message = (TextView) header.findViewById(R.id.tv_message);
        tv_praise = (TextView) header.findViewById(R.id.tv_praise);
        iv_praise = (ImageView) header.findViewById(R.id.iv_praise);
        ll_praise = (LinearLayout) header.findViewById(R.id.ll_praise);
        ll_praise.setOnClickListener(this);
        HorizontalPageLayoutManager horizontalPageLayoutManager = new HorizontalPageLayoutManager(1, 4);
        PagingScrollHelper scrollHelper = new PagingScrollHelper();
        data01 = getweiboinfoModel.getInfo().getPic();
        mAdapter = new AppointmentInfoAdapter(R.layout.appointment01_gridview_items, data01);
        mRecyclerView.setAdapter(mAdapter);
        scrollHelper.setUpRecycleView(mRecyclerView);
        RecyclerView.LayoutManager layoutManager = null;
        layoutManager = horizontalPageLayoutManager;
        if (layoutManager != null) {
            mRecyclerView.setLayoutManager(layoutManager);
            scrollHelper.updateLayoutManger();
        }
        ImageLoader.getInstance().displayImage(getweiboinfoModel.getInfo().getUser().getHeadimg(), ivAvatar);
        tvFriend_name.setText(getweiboinfoModel.getInfo().getUser().getNickname());
        Drawable country = mContext.getResources().getDrawable(R.mipmap.icon_boy);
        country.setBounds(0, 0, country.getMinimumWidth(), country.getMinimumHeight());
        Drawable country1 = mContext.getResources().getDrawable(R.mipmap.icon_girl);
        country1.setBounds(0, 0, country1.getMinimumWidth(), country1.getMinimumHeight());
        if ("1".equals(getweiboinfoModel.getInfo().getUser().getSex())) {
            tvFriend_gender.setText(getweiboinfoModel.getInfo().getUser().getAge());
            tvFriend_gender.setCompoundDrawables(country, null, null, null);
        } else {
            tvFriend_gender.setText(getweiboinfoModel.getInfo().getUser().getAge());
            tvFriend_gender.setCompoundDrawables(country1, null, null, null);
        }
        tvFriend_sign.setText(getweiboinfoModel.getInfo().getUser().getAstro());
        tv_time.setText(getweiboinfoModel.getInfo().getInterval_time());
        tv_time01.setText(getweiboinfoModel.getInfo().getCreate_time());
        tv_address.setText("在" + getweiboinfoModel.getInfo().getCityname());
        if ("1".equals(getweiboinfoModel.getInfo().getUser().getSex())) {
            tv_number.setText("约一个 美女");
        } else if ("2".equals(getweiboinfoModel.getInfo().getUser().getSex())) {
            tv_number.setText("约一个 帅哥");
        }
        tv_content.setText(getweiboinfoModel.getInfo().getContent());
        tv_address01.setText(getweiboinfoModel.getInfo().getUser().getCityname());
        message = getweiboinfoModel.getInfo().getComment_count();
        tv_message.setText(message);
        tv_praise.setText(getweiboinfoModel.getInfo().getLike_count());
        if ("0".equals(getweiboinfoModel.getInfo().getIslike())) {
            iv_praise.setBackgroundResource(R.mipmap.icon_unpraise);
        } else if ("1".equals(getweiboinfoModel.getInfo().getIslike())) {
            iv_praise.setBackgroundResource(R.mipmap.icon_praise);
        }
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.SwipeRefreshLayout01);
        recyclerView01 = (RecyclerView) findViewById(R.id.RecyclerView01);

        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        recyclerView01.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();


        mAdapter01.addHeaderView(header);

    }

    private int pageNum = 1;
    private static final int PAGE_SIZE = 6;//为什么是6呢？
    private boolean isErr;
    private boolean mLoadMoreEndGone = false; //是否加载更多完毕
    private int mCurrentCounter = 0;
    int TOTAL_COUNTER = 0;


    private void initAdapter() {
        mAdapter01 = new WeiboCommentAdapter(R.layout.weibo_comment_list_view, data1);
        mAdapter01.setOnLoadMoreListener(this, recyclerView01);
        //mAdapter.setPreLoadNumber(3);
        recyclerView01.setAdapter(mAdapter01);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        initDate1(1, false);//获取评论列表
        LoaddingShow();
    }

    private List<WeiboCommentModel.ListBean> data1 = new ArrayList<>();

    private void initDate1(int pageNum, final boolean isloadmore) {
        RetrofitUtil.getInstance().Weibocommentlist(ukey, wid, String.valueOf(pageNum), new Subscriber<BaseResponse<WeiboCommentModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<WeiboCommentModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    weiboCommentModel = baseResponse.getData();
                    List<WeiboCommentModel.ListBean> list01 = weiboCommentModel.getList();
                    //  initView();
                    if (list01 != null && list01.size() > 0) {
                        if (!isloadmore) {
                            data1 = list01;
                        } else {
                            data1.addAll(list01);
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

    @Override
    public void onLoadMoreRequested() {

        mSwipeRefreshLayout.setEnabled(false);
        TOTAL_COUNTER = Integer.valueOf(weiboCommentModel.getTotal());
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

    @Override
    public void onRefresh() {
        mAdapter01.setEnableLoadMore(false);//下拉刷新的时候关闭上拉加载 之后再打开
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                initDate1(1, false);
                isErr = false;
                mCurrentCounter = PAGE_SIZE;//这行不能删除
                pageNum = 1;//页数置为1 才能继续重新加载
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter01.setEnableLoadMore(true);
            }
        }, 2000);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_praise:
                LoaddingShow();
                RetrofitUtil.getInstance().GetLike(ukey, wid, new Subscriber<BaseResponse<EmptyEntity>>() {
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

                            initData();
                        } else {
                            showProgress(baseResponse.getMsg());
                        }
                    }
                });
                break;
            case R.id.tv_send:
                final String content = ed_input.getText().toString().trim();
                if (StringUtil.isBlank(content)) {
                    showProgress("评论内容不能为空！");
                    return;
                }
                send(content);
                LoaddingShow();
                break;
        }
    }

    private void send(String content) {
        RetrofitUtil.getInstance().Weibocomment(ukey, wid, content, new Subscriber<BaseResponse<WeiboCommentModel01>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<WeiboCommentModel01> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    ed_input.getText().clear();

                    tv_message.setText(baseResponse.getData().getTotal());
                    onRefresh();
                } else {
                    showProgress(baseResponse.getMsg());
                }
            }
        });
    }

}
