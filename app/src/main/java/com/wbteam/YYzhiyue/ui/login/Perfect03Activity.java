package com.wbteam.YYzhiyue.ui.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Perfect03Activity extends BaseActivity {
    @BindView(R.id.RecyclerView)
    ListView mRecyclerView;
    private String[] Name = {"经济帮助", "浪漫旅行", "温馨约会", "情感补充", "恋爱婚姻", "人生指导"};
    private List<Map<String, Object>> dataList;
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect03);
        ButterKnife.bind(this);
        setBackView();
        setTitle("完善信息");
        initView();
    }

    private void initView() {
        dataList = new ArrayList<Map<String, Object>>();
        getData();
        String[] from = {"name"};
        final int[] to = {R.id.tv_name};
        simpleAdapter = new SimpleAdapter(mContext, dataList, R.layout.perfect_listview_items, from, to);
        mRecyclerView.setAdapter(simpleAdapter);
        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String provide=dataList.get(position).get("name").toString();
                //Log.d("TAG",provide);
               // toActivity(Perfect04Activity.class);
                Bundle bundle=new Bundle();
                bundle.putString("provide",provide);
                toActivity(Perfect04Activity.class,bundle);
            }
        });

    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < Name.length; i++) {
            //Log.d("TAG", "" + Name.length);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", Name[i]);
            dataList.add(map);
        }
        return dataList;
    }
}
