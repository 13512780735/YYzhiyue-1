package com.wbteam.YYzhiyue.view.city;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.citypicker.utils.StringUtils;
import com.wbteam.YYzhiyue.citypicker.view.WrapHeightGridView;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.CityListModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.login.Login_RegisterActivity;
import com.wbteam.YYzhiyue.util.MyActivityManager;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.city.widget.CityModel;
import com.wbteam.YYzhiyue.view.city.widget.SideBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class CityActivity extends BaseActivity implements SideBar.OnTouchingLetterChangedListener {


    @BindView(R.id.city_listview)
    ListView mListView;
    private SideBar mSideBar;
    private TextView mDialog;
    private ArrayList<CityEntity> datas;
    private CityAdapter mAdapter;
    String TAG = "CityActivity";
    private TextView tvLocation;
    private WrapHeightGridView mGridView;
    private List<CityModel> mCities;
    private CityModel cityModel;
    private AMapLocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ButterKnife.bind(this);
        setTitle("选择城市");
        setBackView();
        datas = new ArrayList<>();
        mCities=new ArrayList<>();
        getData();
        initData();
        LoaddingShow();
        initView();
        initLocation();
    }

    private void initLocation() {
        mLocationClient = new AMapLocationClient(this);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        String city = aMapLocation.getCity();
                        String district = aMapLocation.getDistrict();
                        Log.e("onLocationChanged", "city: " + city);
                        Log.e("onLocationChanged", "district: " + district);
                        String location = StringUtils.extractLocation(city, district);
                      //  mCityAdapter.updateLocateState(LocateState.SUCCESS, location);
                        tvLocation.setText(location);
                    } else {
                        //定位失败
                       // mCityAdapter.updateLocateState(LocateState.FAILED, null);
                        tvLocation.setText("重新定位");
                    }
                }
            }
        });
        mLocationClient.startLocation();
    }
    private void getData() {
        cityModel = new CityModel();
        cityModel.setId("110100");
        cityModel.setName("北京");
        mCities.add(cityModel);
//        cityModel = new CityModel();
//        cityModel.setId("110100");
//        cityModel.setName("上海");
//        mCities.add(cityModel);
        cityModel = new CityModel();
        cityModel.setId("440100");
        cityModel.setName("广州");
        mCities.add(cityModel);
        cityModel = new CityModel();
        cityModel.setId("440300");
        cityModel.setName("深圳");
        mCities.add(cityModel);
        cityModel = new CityModel();
        cityModel.setId("442000");
        cityModel.setName("中山");
        mCities.add(cityModel);
    }

    private void initData() {
        Log.d("TAG566", datas.toString());
        RetrofitUtil.getInstance().GetcityDistrict(ukey, new Subscriber<BaseResponse<CityListModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<CityListModel> baseResponse) {
                LoaddingDismiss();
                Log.d("TAG88", datas.toString());
                if (baseResponse.ret == 200) {
                    CityListModel cityListModel = baseResponse.getData();
                    // datas = cityListModel.getList();
                    for (int i = 0; i < cityListModel.getList().size(); i++) {
                        CityEntity mCityEntity = new CityEntity();
                        mCityEntity.setId(cityListModel.getList().get(i).getId());
                        mCityEntity.setName(cityListModel.getList().get(i).getName());
                        mCityEntity.setPinyin(HanziToPinyin.getPinYin(mCityEntity.getName()));
                        datas.add(mCityEntity);
                    }
                    Log.d("TAG55", datas.toString());
                    mAdapter = new CityAdapter(mListView, datas);
                    mListView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
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
        View headView = getLayoutInflater().inflate(R.layout.city_header_view, null);
        tvLocation = (TextView) headView.findViewById(R.id.tv_located_city);
        mGridView = (WrapHeightGridView) headView.findViewById(R.id.gridview_hot_city);
        mListView.addHeaderView(headView);
        mSideBar = (SideBar) findViewById(R.id.school_friend_sidrbar);
        mDialog = (TextView) findViewById(R.id.school_friend_dialog);
        mSideBar.setTextView(mDialog);
        mSideBar.setOnTouchingLetterChangedListener(this);
        tvLocation.setText(UtilPreference.getStringValue(mContext, "city"));
        final HotCityGridAdapter01 hotCityGridAdapter = new HotCityGridAdapter01(mContext,mCities);
        mGridView.setAdapter(hotCityGridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Log.d(TAG, "11" + hotCityGridAdapter.getItem(position).toString());
                back(mCities.get(position).getName(),mCities.get(position).getId());
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             //  Log.d(TAG, "ID:" + datas.get(position-1).getId() + ""+"地址："+datas.get(position-1).getName());
                back(datas.get(position-1).getName(),mCities.get(position-1).getId());
            }
        });
        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLocation();
            }
        });

    }
    private void back(String city,String id){
        // ToastUtils.showToast(this, "点击的城市：" + city);

        Intent intent = new Intent();

        intent.putExtra("date",
                city);
        intent.putExtra("id",
                id);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private List<CityEntity> iniyHotCityDatas() {
        List<CityEntity> list = new ArrayList<>();
        list.add(new CityEntity());
        list.add(new CityEntity());
        list.add(new CityEntity());
        list.add(new CityEntity());
        return list;
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        int position = 0;
        // 该字母首次出现的位置
        if (mAdapter != null) {
            position = mAdapter.getPositionForSection(s.charAt(0));
        }
        if (position != -1) {
            mListView.setSelection(position);
        } else if (s.contains("#")) {
            mListView.setSelection(0);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();
    }
}