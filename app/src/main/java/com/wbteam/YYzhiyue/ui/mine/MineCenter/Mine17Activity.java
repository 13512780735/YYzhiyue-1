package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.mine.MyVideoAdapter;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.model.MyVideoModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.util.viedeo.VideoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class Mine17Activity extends BaseActivity {
    @BindView(R.id.radio_group)
    RadioGroup radio_group;
    @BindView(R.id.radio_01)
    RadioButton radio_01;
    @BindView(R.id.radio_02)
    RadioButton radio_02;
    @BindView(R.id.line_01)
    View line_01;//审核
    @BindView(R.id.line_02)
    View line_02;//未审核
    private String type;
    private MyVideoModel myVideoModel;
    private List<MyVideoModel.ListBean> list;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView02;
    private MyVideoAdapter mAdapter;
    private String number = "0";
    private String number1 = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine17);
        ButterKnife.bind(this);
        setBackView();
        setTitle("我的视频");
        setRightText("发布视频", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilPreference.saveString(mContext, "videoKey", "2");
                Intent intent = new Intent(mContext, VideoActivity.class);
                startActivity(intent);
             //  finish();
               // toActivity(VideoActivity.class);
            }
        });
        initView();
    }

    private List<MyVideoModel.ListBean> data = new ArrayList<>();

    private void initData(final String type) {
        RetrofitUtil.getInstance().UserMyvideolist(ukey, type, "1", new Subscriber<BaseResponse<MyVideoModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<MyVideoModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    myVideoModel = baseResponse.getData();
                    if ("1".equals(type)) {
                        number = myVideoModel.getTotal();
                        radio_01.setText("视频" + "(" + number + ")");

                    } else if ("0".equals(type)) {
                        number1 = myVideoModel.getTotal();
                        radio_02.setText("未发布" + "(" + number1 + ")");
                    }
                    list = myVideoModel.getList();
                    if (list != null && list.size() > 0) {
                        data.addAll(list);
                        mAdapter.setNewData(data);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void initView() {
        line_01.setVisibility(View.VISIBLE);
        radio_01.setText("视频" + "(" + number + ")");
        radio_02.setText("未发布" + "(" + number1 + ")");
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView01);//发布
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        initAdapter();
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radio_01:
                        type = "1";
                        data.clear();
                        mAdapter.notifyDataSetChanged();
                        initData(type);
                        LoaddingShow();
                        line_01.setVisibility(View.VISIBLE);
                        line_02.setVisibility(View.GONE);
                        break;
                    case R.id.radio_02:
                        type = "0";
                        data.clear();
                        initData(type);
                        mAdapter.notifyDataSetChanged();
                        LoaddingShow();
                        line_01.setVisibility(View.GONE);
                        line_02.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String id = data.get(position).getId();
                LoaddingShow();
                RetrofitUtil.getInstance().User_Removemyvideo(ukey, id, new Subscriber<BaseResponse<EmptyEntity>>() {
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
                            showProgress("删除成功");
                            initData(type);
                        } else {
                            showProgress(baseResponse.getMsg());
                        }
                    }
                });
            }
        });
    }

    private void initAdapter() {
        mAdapter = new MyVideoAdapter(R.layout.my_viedeo_listview_items, data);
        mRecyclerView.setAdapter(mAdapter);
        type = "1";
        initData(type);
        LoaddingShow();
    }


}
