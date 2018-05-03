package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.RechargeAdapter;
import com.wbteam.YYzhiyue.adapter.mine.TagAdapter;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.citypicker.model.RechargeModel;
import com.wbteam.YYzhiyue.view.MyGridView;
import com.wbteam.YYzhiyue.wxapi.PayActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RechargeActivity extends BaseActivity {
    @BindView(R.id.gridView)
    GridView mGridview;
    private String[] Price = {"10", "30", "50", "100", "200", "500", "1000", "2000", "5000", "10000", "20000", "50000"};
    private List<RechargeModel> dataList;
    private SimpleAdapter simpleAdapter;
    private RechargeAdapter mAdapter;
    private String price;
    int selectorPosition = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        setBackView();
        setTitle("充值");
        price = "10";
        initView();
    }

    private void initView() {
        dataList = new ArrayList<>();
        for (int i = 0; i < Price.length; i++) {
            RechargeModel rechareModel = new RechargeModel();
            rechareModel.setName(Price[i]);
            dataList.add(rechareModel);

        }

        mAdapter = new RechargeAdapter(mContext, dataList);
        mGridview.setAdapter(mAdapter);
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.changeState(position);
                selectorPosition = position;
                // Log.d("TAG",price);
                // mAdapter.notifyDataSetChanged();
                price = dataList.get(position).getName();
            }

        });
    }


    @OnClick({R.id.tv_pay})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pay:
                Bundle bundel = new Bundle();
                bundel.putString("flag","2");//充值
                bundel.putString("ordersn", "");
                bundel.putString("vipId", "");
                bundel.putString("price", price);
                toActivity(PayActivity.class, bundel);
                break;
        }
    }
}
