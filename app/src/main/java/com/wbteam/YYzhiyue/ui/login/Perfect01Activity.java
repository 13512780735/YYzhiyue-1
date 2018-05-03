package com.wbteam.YYzhiyue.ui.login;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.mine.InformationActivity;
import com.wbteam.YYzhiyue.util.StringUtil;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.city.CityActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class Perfect01Activity extends BaseActivity {
    private static final int REQUEST_REGION_PICK = 1;//城市返回标识
    @BindView(R.id.ed_ednickName)
    EditText ed_ednickName;
    @BindView(R.id.ed_edheight)
    EditText ed_edheight;
    @BindView(R.id.ed_edjob)
    EditText ed_edjob;
    @BindView(R.id.radioGroup_grade)
    RadioGroup radioGroup_grade;
    @BindView(R.id.tv_tvbirthday)
    TextView tv_tvbirthday;
    @BindView(R.id.tv_tvAddress)
    TextView tv_tvAddress;
    private String sex;

    /**
     * 日期
     */
    final int DATE_DIALOG = 1;
    int mYear, mMonth, mDay;
    private String city;
    private String time;
    private String cityId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect01);
        ButterKnife.bind(this);
        setBackView();
        setTitle("完善信息");
        sex = "1";
        initView();
    }

    private void initView() {
        radioGroup_grade.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton_grade02:
                        sex = "1";
                        break;
                    case R.id.radioButton_grade03:
                        sex = "2";
                        break;
                }
            }
        });
    }

    @OnClick({R.id.tv_confirm, R.id.tv_tvbirthday, R.id.rl_rlAddres})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                NextTo();
                break;
            case R.id.tv_tvbirthday:
                showDialog(DATE_DIALOG);
                break;
            case R.id.rl_rlAddres:
                Intent intent = new Intent(this, CityActivity.class);
                startActivityForResult(intent, REQUEST_REGION_PICK);
                break;
        }
    }

    private String mNickName, mheight, mjob;

    private void NextTo() {
        mNickName = ed_ednickName.getText().toString().trim();
        mheight = ed_edheight.getText().toString().trim();
        mjob = ed_edjob.getText().toString().trim();
        if (StringUtil.isBlank(mNickName) || StringUtil.isBlank(mheight) || StringUtil.isBlank(mjob)) {
            showProgress("请完善好资料");
            return;
        }
        Log.d("TAG999",sex+"");

        LoaddingShow();
        RetrofitUtil.getInstance().getUserEditinfo(ukey, mNickName, sex, time, mheight, "", "", mjob, "", "", cityId, "","",
                new Subscriber<BaseResponse<EmptyEntity>>() {
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
                            toActivity(Perfect02Activity.class);
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
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(Perfect01Activity.this, R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        display();
                    }
                }, 2017, 01, 01);
        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display() {
        time = String.valueOf(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" "));
        tv_tvbirthday.setText(time);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //   super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_REGION_PICK) {
            if (data != null) {
                city = data.getStringExtra("date");
                cityId = data.getStringExtra("id");
                tv_tvAddress.setText(city);
            }
        }
    }
}
