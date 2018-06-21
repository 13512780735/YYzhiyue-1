package com.wbteam.YYzhiyue.ui.reward;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.citypicker.CircumCityActivity;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.BonusModel;
import com.wbteam.YYzhiyue.network.api_service.model.CreateRewardModel;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.login.Login_RegisterActivity;
import com.wbteam.YYzhiyue.ui.mine.MineCenter.Mine07Activity;
import com.wbteam.YYzhiyue.ui.mine.MineCenter.Mine12Activity;
import com.wbteam.YYzhiyue.ui.mine.SelectScopeActivity;
import com.wbteam.YYzhiyue.util.MyActivityManager;
import com.wbteam.YYzhiyue.util.StringUtil;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.city.CityActivity;
import com.wbteam.YYzhiyue.wxapi.PayActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

import static android.R.attr.tag;

public class CreatRewardActivity extends BaseActivity {
    private static final int REQUEST_REGION_PICK = 3;//城市返回标识
    private static final int REQUEST_REGION = 4;//返回内容标识
    private static final int REQUEST_BONUS = 5;//返回内容标识


    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_Time)
    TextView tvTime;
    @BindView(R.id.tv_duration)
    TextView tvDuration;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_require)
    TextView tvRequire;
    @BindView(R.id.tv_reward)
    TextView tvReward;
    @BindView(R.id.tv_volume)
    TextView tv_volume;
    @BindView(R.id.ll_volume)
    LinearLayout ll_volume;


    private TimePickerView pvStartTime, pvEndTime;
    private String startTime;
    private HeightFragment dialogHeight;
    private String amount;
    private SimpleDateFormat sdf;
    private String startTime01;
    private Date date01;
    private InputMethodManager imm;
    private String tagid;
    private String city;
    private DurationFragment dialogDuration;
    private String duration;
    private String sex01;
    private String number01;
    private String tagstr;
    private String ordersn;
    private String bonusid = "";
    private String amount01 = "0";
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_reward);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);
        setBackView();
        tagstr=getIntent().getExtras().getString("tag");
        initData();//抵金卷获取
        setTitle("创建约会");
        initCustomTimePicker();
        status = "1";   //1.显示 0.隐藏
        sex = "0"; // 性别 0.不限 1.男 2.女
        amount = "150";
        if(tagstr!=null){
            tvType.setText(tagstr);
        }
    }

    List<BonusModel.ListBean> list = new ArrayList<>();

    private void initData() {
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
                    list = baseResponse.getData().getList();
                } else {
                    showProgress(baseResponse.getMsg());
                }
                initView();
            }
        });
    }

    private void initView() {
        Log.d("TAG221", list.size() + "");
        if (list != null && list.size() > 0) {
            ll_volume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(mContext, Mine07Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("idKeys", "2");
                    intent1.putExtras(bundle);
                    startActivityForResult(intent1, REQUEST_BONUS);

                }
            });
            tv_volume.setHint("请选择抵金券");
        } else {
            ll_volume.setEnabled(false);
            tv_volume.setHint("暂无可用");
        }

    }


    @OnClick({R.id.tv_confirm, R.id.ll_time, R.id.ll_duration, R.id.ll_type, R.id.ll_address, R.id.ll_require, R.id.ll_reward})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                offer();
                LoaddingShow();
                break;
            case R.id.ll_type:
                Intent intent1 = new Intent(mContext, SelectScopeActivity.class);
                intent1.putExtra("flag", "2");
                startActivityForResult(intent1, REQUEST_REGION);
                break;
            case R.id.ll_time:
                pvStartTime.show();
                break;
            case R.id.ll_duration:
                dialogDuration = new DurationFragment();
                dialogDuration.show(getSupportFragmentManager(), "HeightFragment");
                dialogDuration.setOnDialogListener(new HeightFragment.OnDialogListener() {
                    @Override
                    public void onDialogClick(String person) {
                        duration = person;
                        tvDuration.setText(duration + "小时");
                    }
                });
                break;

            case R.id.ll_address:
                if (StringUtil.isBlank(tagstr)) {
                    showProgress("请选择类型");
                    return;
                }
                Intent intent = new Intent(this, CircumCityActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("keyWord", tagstr);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_REGION_PICK);
                break;
            case R.id.ll_require:
                RequireFragment dialogRequire = new RequireFragment();
                dialogRequire.show(getSupportFragmentManager(), "HeightFragment");
                dialogRequire.setOnDialogListener(new RequireFragment.OnDialogListener() {
                    @Override
                    public void onDialogClick(String sex, String number) {
                        sex01 = sex;
                        number01 = number;
                        if ("0".equals(sex01)) {
                            tvRequire.setText(" " + number01 + "人");
                        } else if ("1".equals(sex01)) {
                            tvRequire.setText("男生  " + number01 + "人");
                        } else if ("2".equals(sex01)) {
                            tvRequire.setText("女生  " + number01 + "人");
                        }

                    }
                });
                break;
            case R.id.ll_reward:
                dialogHeight = new HeightFragment();
                dialogHeight.show(getSupportFragmentManager(), "HeightFragment");
                dialogHeight.setOnDialogListener(new HeightFragment.OnDialogListener() {
                    @Override
                    public void onDialogClick(String person) {
                        amount = person;
                        // amount = "1";
                        tvReward.setText("每人" + amount + "元");
                    }
                });
                break;
        }
    }


    private String title, status, sex;

    private void offer() {
        Log.d("TAG", "startTime01:" + startTime01);
        Log.d("TAG222", amount + sex + startTime01 + number01 + tagstr + duration + title + bonusid);
        String lng = UtilPreference.getStringValue(mContext, "lon");
        String lat = UtilPreference.getStringValue(mContext, "lat");
        RetrofitUtil.getInstance().GetPublishevent(ukey, amount, sex, startTime01, "", "", "", "", "", "", number01, tagstr, duration, title, bonusid, lng, lat, new Subscriber<BaseResponse<CreateRewardModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoaddingDismiss();
            }

            @Override
            public void onNext(BaseResponse<CreateRewardModel> baseResponse) {
                Log.d("TAG11", amount01);
                LoaddingDismiss();
                if (baseResponse.ret == 200) {
                    // toActivity();
                    ordersn = baseResponse.getData().getOrdersn();
                    if ("0".equals(amount01)) {
                        price = String.valueOf(Integer.valueOf(number01) * Integer.valueOf(amount));
                    } else {
                        price = String.valueOf(Integer.valueOf(number01) * Integer.valueOf(amount) - Integer.valueOf(amount01));
                    }
                    Log.d("TAG848", "ordersn:" + ordersn + "price" + price);
                    Bundle bundel = new Bundle();
                    bundel.putString("ordersn", ordersn);
                    bundel.putString("price", price);
                    bundel.putString("flag", "1");
                    bundel.putString("vipId", "");
                    toActivity(PayActivity.class, bundel);
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

    private void initCustomTimePicker() {

        /**
         * @description
         *
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        final Calendar startDate = Calendar.getInstance();
        startDate.set(2017, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 2, 28);
        //时间选择器 ，自定义布局
        pvStartTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                startTime = getTime(date);
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                    date01 = sdf.parse(startTime);
                    startTime01 = String.valueOf(date01.getTime() / 1000);
                    Log.d("TAG", date01.getTime() + "startTime:" + startTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tvTime.setText(startTime);
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView tvCancel = (TextView) v.findViewById(R.id.tv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvStartTime.returnData();
                                pvStartTime.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvStartTime.dismiss();
                            }
                        });
                    }
                })
                .setContentSize(18)
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        // Log.d("TAG", date.toString());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_REGION) {
                if (data != null) {
                    tagstr = data.getStringExtra("data");//标签
                    tagid = data.getStringExtra("dataid");
                    tvType.setText(tagstr);
                }
            } else if (requestCode == REQUEST_REGION_PICK) {
                if (data != null) {
                    title = data.getStringExtra("date");//地点
                    // cityId = data.getStringExtra("id");
                    tvAddress.setText(title);
                }
            } else if (requestCode == REQUEST_BONUS) {
                Log.d("TAG", data.getStringExtra("amount") + "" );
                if (data != null) {
                    amount01 = data.getStringExtra("amount");//钱
                    bonusid = data.getStringExtra("bonusid");//id
                    if ("0".equals(amount01)) {
                        tv_volume.setText("");
                        tv_volume.setHint("请选择抵金券");
                    } else {
                        tv_volume.setText("抵金券" + amount01 + "元");
                    }
                }
            }
        }
    }
}
