package com.wbteam.YYzhiyue.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.mine.TagAdatper01;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.model.ScopeModel;
import com.wbteam.YYzhiyue.network.api_service.model.TagNameModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.login.Login_RegisterActivity;
import com.wbteam.YYzhiyue.util.MyActivityManager;
import com.wbteam.YYzhiyue.util.StringUtil;
import com.wbteam.YYzhiyue.view.MyGridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class ScopeActivity extends BaseActivity implements OnDateSetListener {
    private static final int REQUEST_REGION = 2;
    MyGridView mGridView;
    @BindView(R.id.ed_edPrice)
    EditText edPrice;
    @BindView(R.id.ed_edPhone)
    EditText edPhone;
    @BindView(R.id.ed_edWeiXin)
    EditText edWeiXin;
    @BindView(R.id.tv_startTime)
    TextView tvStartTime;
    @BindView(R.id.tv_EndTime)
    TextView tvEndTime;
    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.radioButton_yes)
    RadioButton radioButton_yes;
    @BindView(R.id.radioButton_no)
    RadioButton radioButton_no;

    private List<String> data;
    private TagAdatper01 mAdapter;
    TimePickerDialog mDialogHourMinute;
    private int timeId;
    SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
    private List<TagNameModel> dataTag;
    private String startTime;
    private String endTime;
    private String iscontact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scope);
        ButterKnife.bind(this);
        data = new ArrayList<>();
        //initDate();//模拟数据
        setBackView();
        setTitle("出租范围");
        initScope();
        LoaddingShow();
        initView();
    }

    private void initScope() {
        RetrofitUtil.getInstance().getGetleaserinfo(ukey, new Subscriber<BaseResponse<ScopeModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<ScopeModel> baseResponse) {
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    edPrice.setText(baseResponse.getData().getInfo().getPrice());
                    edPhone.setText(baseResponse.getData().getInfo().getContact());
                    edWeiXin.setText(baseResponse.getData().getInfo().getWeixin());
                    startTime = baseResponse.getData().getInfo().getStart_time();
                    endTime = baseResponse.getData().getInfo().getEnd_time();
                    tvStartTime.setText(startTime);
                    tvEndTime.setText(endTime);
                    iscontact = baseResponse.getData().getInfo().getIscontact();
                    Log.d("TAG", iscontact);
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

    private void initView() {
        mDialogHourMinute = new TimePickerDialog.Builder()
                .setType(Type.HOURS_MINS)
                .setThemeColor(getResources().getColor(R.color.login_btn))
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.login_btn))
                .setHourText("H")
                .setMinuteText("M")
                .setWheelItemTextSize(12)
                .setCallBack(this)
                .build();
        mGridView = (MyGridView) findViewById(R.id.RecyclerView);
        if ("1".equals(iscontact)) {
            radioButton_yes.setChecked(true);
            radioButton_no.setChecked(false);
        } else {
            radioButton_no.setChecked(true);
            radioButton_yes.setChecked(false);
        }
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton_yes:
                        iscontact = "1";
                        break;
                    case R.id.radioButton_no:
                        iscontact = "0";
                        break;
                }
                Log.d("TAG1", iscontact);
            }
        });
    }

    @OnClick({R.id.tv_startTime, R.id.tv_EndTime, R.id.tv_tvScope, R.id.tv_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_startTime:
                timeId = 1;
                mDialogHourMinute.show(getSupportFragmentManager(), "hour_minute");
                break;
            case R.id.tv_EndTime:
                timeId = 2;
                mDialogHourMinute.show(getSupportFragmentManager(), "hour_minute");
                break;
            case R.id.tv_tvScope:
                Intent intent = new Intent(mContext, SelectScopeActivity.class);
                intent.putExtra("flag", "1");
                startActivityForResult(intent, REQUEST_REGION);
                break;
            case R.id.tv_confirm:
                toConfirm();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_REGION) {
                if (data != null) {
                    String tag = data.getStringExtra("data");
                    Log.d("TAG", tag);
                    dataTag = new ArrayList<>();
                    String[] sourceStrArray = tag.split(",");
                    for (int i = 0; i < sourceStrArray.length; i++) {
                        System.out.println(sourceStrArray[i]);
                        TagNameModel tagNameModel = new TagNameModel();
                        tagNameModel.setName(sourceStrArray[i]);
                        dataTag.add(tagNameModel);
                    }
                    mAdapter = new TagAdatper01(ScopeActivity.this, dataTag);
                    mGridView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void toConfirm() {
        String price = edPrice.getText().toString().trim();
        String phone = edPhone.getText().toString().trim();
        String weixin = edWeiXin.getText().toString().trim();
        RetrofitUtil.getInstance().getSetleaserinfo(ukey, price, startTime, endTime, weixin, phone, iscontact, new Subscriber<BaseResponse<EmptyEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<EmptyEntity> baseResponse) {
                if (baseResponse.ret == 200) {
                    onBackPressed();
                    //showProgress(baseResponse.getMsg());
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


    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        String text = getDateToString(millseconds);
        if (timeId == 1) {
            tvStartTime.setText(text);
            startTime = text;
        } else if (timeId == 2) {
            tvEndTime.setText(text);
            endTime = text;
        }
    }

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }

}
