package com.wbteam.YYzhiyue.ui.neaeby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.util.StringUtil;
import com.wbteam.YYzhiyue.util.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    }

    @OnClick({R.id.tv_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_confirm:
                String content = mEditText.getText().toString().trim();
                if (StringUtil.isBlank(content)) {
                    showProgress("请填写完资料");
                    return;
                } else {
                    ToastUtil.showS(mContext, "提交成功！");
                    finish();
                }


                break;
        }
    }

}
