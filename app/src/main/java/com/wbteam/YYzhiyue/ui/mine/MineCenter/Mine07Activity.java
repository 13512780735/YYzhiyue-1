package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.mine.BounsAdapter;
import com.wbteam.YYzhiyue.adapter.mine.MyBounsAdapter;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.BonusModel;
import com.wbteam.YYzhiyue.network.api_service.model.MyBonusModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class Mine07Activity extends BaseActivity {
    @BindView(R.id.RecyclerView01)
    RecyclerView mRecyclerView01;//我的界面
    @BindView(R.id.RecyclerView02)
    RecyclerView mRecyclerView02;//悬赏
    private String idKeys;
    private MyBounsAdapter adapter01;
    private LinearLayoutManager linearLayoutManager;
    private BounsAdapter adapter02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine07);
        ButterKnife.bind(this);
        idKeys = getIntent().getExtras().getString("idKeys");//1.我的界面  2.悬赏
        setBackView();
        setTitle("我的抵金券");
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        if ("1".equals(idKeys)) {
            initView01();
            initData1();

        } else if ("2".equals(idKeys)) {
            initView02();
            initData2();
        }

    }

    private void initView01() {
        mRecyclerView01.setVisibility(View.VISIBLE);
        mRecyclerView01.setLayoutManager(linearLayoutManager);
    }

    List<MyBonusModel.ListBean> data01 = new ArrayList<>();

    private void initData1() {
        Log.d("TAG1", "1");
        RetrofitUtil.getInstance().User_Mybonuslist(ukey, new Subscriber<BaseResponse<MyBonusModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<MyBonusModel> baseResponse) {
                if (baseResponse.ret == 200) {
                    List<MyBonusModel.ListBean> list = baseResponse.getData().getList();
                    Log.d("TAG2", "3");
                    data01.addAll(list);
                    adapter01 = new MyBounsAdapter(R.layout.bouns_list_view, data01);
                    adapter01.setNewData(data01);
                    mRecyclerView01.setAdapter(adapter01);
                    adapter01.notifyDataSetChanged();
                } else {
                    showProgress(baseResponse.getMsg());
                }
            }
        });
    }

    private void initView02() {
        setRightText("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bonusid ="0";
                String amount1 = "0";
                Intent intent = getIntent();
                intent.putExtra("bonusid", bonusid);
                intent.putExtra("amount", amount1);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        mRecyclerView02.setVisibility(View.VISIBLE);
        mRecyclerView02.setLayoutManager(linearLayoutManager);
        adapter02 = new BounsAdapter(R.layout.bouns_list_view, data02);
        adapter02.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String bonusid = data02.get(position).getId();
                String amount = data02.get(position).getAmount();
                String amount1 = amount.substring(0, amount.indexOf("."));
                Intent intent = getIntent();
                intent.putExtra("bonusid", bonusid);
                intent.putExtra("amount", amount1);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    List<BonusModel.ListBean> data02 = new ArrayList<>();

    private void initData2() {
        RetrofitUtil.getInstance().Event_Bonuslist(ukey, new Subscriber<BaseResponse<BonusModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<BonusModel> baseResponse) {
                if (baseResponse.ret == 200) {
                    List<BonusModel.ListBean> list = baseResponse.getData().getList();
//                    for(BonusModel.ListBean s:list){
//                        Log.d("TAG22",s+=);
//                    }

                    Log.d("TAG2", "3");
                    data02.addAll(list);

                    adapter02.setNewData(data02);
                    mRecyclerView02.setAdapter(adapter02);
                    adapter02.notifyDataSetChanged();
                } else {
                    showProgress(baseResponse.getMsg());
                }
            }
        });
    }
}
