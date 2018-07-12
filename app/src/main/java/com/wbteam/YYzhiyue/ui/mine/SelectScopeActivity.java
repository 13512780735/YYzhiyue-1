package com.wbteam.YYzhiyue.ui.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.mine.TagAdapter;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.model.TagModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.login.Login_RegisterActivity;
import com.wbteam.YYzhiyue.util.MyActivityManager;
import com.wbteam.YYzhiyue.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class SelectScopeActivity extends BaseActivity {

    @BindView(R.id.tag_filter_items_GridView)
    GridView mGridView;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    private List<TagModel.ListBean> tagList = null;
    private String str2;
    private String strName2;
    private TagAdapter mAdapter;
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_scope);
        ButterKnife.bind(this);
        setBackView();
        setTitle("选择标签");
        tagList = new ArrayList<>();
        flag = getIntent().getStringExtra("flag");
        initData();
        LoaddingShow();

    }

    private void initData() {
        RetrofitUtil.getInstance().getGettaglist(ukey, new Subscriber<BaseResponse<TagModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<TagModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    TagModel tagModel = baseResponse.getData();
                    for (int i = 0; i < tagModel.getList().size(); i++) {
                        TagModel.ListBean mListBean = new TagModel.ListBean();
                        mListBean.setId(tagModel.getList().get(i).getId());
                        mListBean.setTitle(tagModel.getList().get(i).getTitle());
                        mListBean.setImage(tagModel.getList().get(i).getImage());
                        tagList.add(mListBean);
                    }
                    initView();
                }else{
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

    private void initView() {
        mAdapter = new TagAdapter(mContext, tagList);
        mGridView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        if ("1".equals(flag)) {
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    tagList.get(position).setChecked(!tagList.get(position).isChecked());
                    for (int i = 0; i < tagList.size(); i++) {
                        //跳过已设置的选中的位置的状态
                        if (i == position) {
                            continue;
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
            });
        } else {
            tvConfirm.setVisibility(View.GONE);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    tagList.get(position).setChecked(!tagList.get(position).isChecked());
                    for (int i = 0; i < tagList.size(); i++) {
                        //跳过已设置的选中的位置的状态
                        if (i == position) {
                            continue;
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                    str2 = tagList.get(position).getId();
                    strName2 = tagList.get(position).getTitle();
                    // offer(str2);
                    Log.d("TAG", str2 + strName2);
                    Intent intent = getIntent();
                    intent.putExtra("data", strName2);
                    intent.putExtra("dataid", str2);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            });
        }
    }

    @OnClick(R.id.tv_confirm)
    public void onClick(View v) {
        String str = "";
        String strName = "";
        for (int i = 0; i < tagList.size(); i++) {
            if (tagList.get(i).isChecked()) {
                str = str + tagList.get(i).getId() + ",";
                strName = strName + tagList.get(i).getTitle() + ",";
            }
        }
        if (str.length() > 0) {
            Log.d("TAG", "str2-->" + str.substring(0, str.length() - 1));
            str2 = str.substring(0, str.length() - 1);
            strName2 = strName.substring(0, strName.length() - 1);
            //mlistener.onDialogClick(strName2);
            offer(str2);
        } else {
            strName2 = "";
            //mlistener.onDialogClick(strName2);

        }
    }

    private void offer(String str2) {
        RetrofitUtil.getInstance().getSetusertag(ukey, str2, new Subscriber<BaseResponse<EmptyEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<EmptyEntity> baseResponse) {

                if (baseResponse.ret == 200) {
                    Intent intent = getIntent();
                    intent.putExtra("data", strName2);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
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
}
