package com.wbteam.YYzhiyue.ui.reward;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RewardRuleActivity extends BaseActivity {
    @BindView(R.id.tv_mContentTv)
    TextView mContentTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_rule);
        ButterKnife.bind(this);
        setBackView();
        setTitle("约会分成");
        initView();
    }
    private void initView() {
        try {
            InputStream is = getAssets().open("reward.txt");
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String text = new String(buffer, "GB2312");

            mContentTv.setText(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
