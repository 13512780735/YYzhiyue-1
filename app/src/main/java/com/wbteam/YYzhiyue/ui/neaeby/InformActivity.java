package com.wbteam.YYzhiyue.ui.neaeby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.util.StringUtil;
import com.wbteam.YYzhiyue.util.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mabeijianxi.camera.util.Log;
import rx.Subscriber;

public class InformActivity extends BaseActivity {

    @BindView(R.id.editText)
    EditText mEditText;
    @BindView(R.id.tv_confirm)
    TextView mTvConfirm;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    private String[] mVals = new String[]
            {"垃圾广告", "虚假盗用资料", " 信 息 ", "淫秽色情", "暴力有害信息", "微信QQ",
                    "中介骗子", " 其 他 "};
    @BindView(R.id.flowLayout)
    TagFlowLayout mTagFlowLayout;
    private LayoutInflater mInflater;
    private String title;
    private String content;
    private String titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inform);
        ButterKnife.bind(this);
        setTitle("举报");
        setBackView();
        initUI();
    }

    private void initUI() {
        mTvContent.setText("* 举报后可能被禁言或者封号" + "\n" + "* 禁止滥用举报功能");
        mInflater = LayoutInflater.from(this);
        mTagFlowLayout.setMaxSelectCount(3);
        mTagFlowLayout.setAdapter(new TagAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                CheckBox checkBox = (CheckBox) mInflater.inflate(R.layout.flowlayout_items,
                        mTagFlowLayout, false);
                checkBox.setText(s);

                // checkBox.setChecked(true);
                return checkBox;
            }
        });
        mTagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {

//                if (selectPosSet.size() > 0) {
//                    for (int i = 0; i < selectPosSet.toArray().length; i++) {
//                        title += mVals[Integer.parseInt(selectPosSet.toArray()[i].toString())];
//                    }
//                }
                //ToastUtil.showS(mContext, title);
            }
        });

//        mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
//            @Override
//            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                //   Toast.makeText(mContext, mVals[position], Toast.LENGTH_SHORT).show();
//                titles += mVals[position] + ",";
//                ToastUtil.showS(mContext, titles);
//                return true;
//            }
//        });
    }

    @OnClick({R.id.tv_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_confirm:
                if (mTagFlowLayout.getSelectedList().size() > 0) {
                    for (int i = 0; i < mTagFlowLayout.getSelectedList().size(); i++) {
                        title += mVals[(int) mTagFlowLayout.getSelectedList().toArray()[i]] + ",";
                    }

                    String titles1 = title.substring(0, title.length() - 1);
                    titles = titles1.replaceAll("null", "");
                  //  ToastUtil.showS(mContext, titles);
                } else {
                    return;
                }
                content = mEditText.getText().toString().trim();
                Log.d("TAG222", title + "content-->" + content);
                if (StringUtil.isBlank(content)) {
                    showProgress("请填写完资料");
                    return;
                } else {
                    ToastUtil.showS(mContext, "提交成功！");
                    Offer(titles, content);
                    finish();
                }


                break;
        }
    }

    private void Offer(String title, String content) {
        Log.d("TAG222", title + "content-->" + content);
        RetrofitUtil.getInstance().User_Informer(ukey, title, content, new Subscriber<BaseResponse<EmptyEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<EmptyEntity> baseResponse) {
                if (baseResponse.ret == 200) {
                    ToastUtil.showS(mContext, "提交成功！");
                    finish();
                } else {
                    showProgress(baseResponse.getMsg());
                }
            }
        });
    }

}
