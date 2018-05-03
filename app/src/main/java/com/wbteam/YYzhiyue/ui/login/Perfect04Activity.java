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
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.MainActivity;
import com.wbteam.YYzhiyue.util.MyActivityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class Perfect04Activity extends BaseActivity {
    @BindView(R.id.RecyclerView)
    ListView mRecyclerView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter simpleAdapter;
    private String[] Name = {"婚姻关系", "激情关系", "恋爱关系", "长期关系", "玩伴关系", "红颜知己"};
    private String provide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect04);
        ButterKnife.bind(this);
        setBackView();
        setTitle("完善信息");
        provide = getIntent().getExtras().getString("provide");
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
                String hopeful = dataList.get(position).get("name").toString();
                upInfo(hopeful);
                LoaddingShow();
            }
        });

    }

    private void upInfo(String hopeful) {
        RetrofitUtil.getInstance().getDemand(ukey, provide, hopeful, new Subscriber<BaseResponse<EmptyEntity>>() {
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
                    Bundle bundle = new Bundle();
                    bundle.putString("keys", "2");
                    toActivity(MainActivity.class, bundle);
                    finish();
                    MyActivityManager.getInstance().finishAllActivity();
                } else {
                    showProgress(baseResponse.getMsg());
                }

            }
        });
    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < Name.length; i++) {
            Log.d("TAG", "" + Name.length);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", Name[i]);
            dataList.add(map);
        }
        return dataList;
    }
}
