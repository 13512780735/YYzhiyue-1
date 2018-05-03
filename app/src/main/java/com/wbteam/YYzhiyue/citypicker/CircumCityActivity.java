package com.wbteam.YYzhiyue.citypicker;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wbteam.YYzhiyue.Entity.PoiAddressBean;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.CircumcityAdatper;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.util.AMapUtils;
import com.wbteam.YYzhiyue.util.LngLat;
import com.wbteam.YYzhiyue.util.ToastUtil;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.city.CityActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CircumCityActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener {
    private static final int REQUEST_REGION_PICK = 1;//城市返回标识

    @BindView(R.id.search_content_et)
    EditText edSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.RecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout SwipeRefreshLayout;


    private int pageNum = 1;
    private static final int PAGE_SIZE = 6;//为什么是6呢？
    private boolean isErr;
    private boolean mLoadMoreEndGone = false; //是否加载更多完毕
    private int mCurrentCounter = 0;
    int TOTAL_COUNTER = 0;
    private PoiSearch.Query query;
    private String keyWord;
    private int currentPage;
    private PoiSearch poiSearch;
    private String city;
    private PoiResult poiResult;
    private Double longitude;
    private Double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circum_city);
        ButterKnife.bind(this);
        setBackView();
        setTitle("约会地点");
        keyWord = getIntent().getExtras().getString("keyWord");
        city = UtilPreference.getStringValue(this, "city");
        setRightText("附近", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initView();
        doSearchQuery();
    }

    private void initView() {
        tvAddress.setText(city);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @OnClick({R.id.tv_search, R.id.tv_address})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                keyWord = edSearch.getText().toString().trim();
                doSearchQuery();
                break;
            case R.id.tv_address:
                Intent intent = new Intent(this, CityActivity.class);
                startActivityForResult(intent, REQUEST_REGION_PICK);
                break;
        }
    }

    protected void doSearchQuery() {
        currentPage = 0;
        //不输入城市名称有些地方搜索不到
        query = new PoiSearch.Query(keyWord, "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        //这里没有做分页加载了,默认给50条数据
        query.setPageSize(50);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {  // 搜索poi的结果
                if (result.getQuery().equals(query)) {  // 是否是同一条
                    poiResult = result;
                    Log.d("TAG", poiResult.toString());
                    final ArrayList<PoiAddressBean> data = new ArrayList<PoiAddressBean>();//自己创建的数据集合
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
//                    UtilPreference.saveString(mContext, "lat", String.valueOf(latitude));
//                    UtilPreference.saveString(mContext, "lon", String.valueOf(longitude));

                    for (PoiItem item : poiItems) {
                        //获取经纬度对象
                        LatLonPoint llp = item.getLatLonPoint();
                        double lon = llp.getLongitude();
                        double lat = llp.getLatitude();
//                        LngLat start = new LngLat(longitude, latitude);
//                        LngLat end = new LngLat(lon, lat);
                       //String distance = String.valueOf(AMapUtils.calculateLineDistance(start, end));
                      //  Log.d("TAG222",distance);
                        String title = item.getTitle();
                        String text = item.getSnippet();
                        String provinceName = item.getProvinceName();
                        String cityName = item.getCityName();
                        String adName = item.getAdName();
                         String distance = String.valueOf(item.getDistance());
//                        Log.d("TAG8788",distance);
//                        Log.d("TAG8733",lon+"*"+lat+"");
                        data.add(new PoiAddressBean(lon,lat, title, text, provinceName,
                                cityName, adName, distance));
                    }
                    CircumcityAdatper adapter = new CircumcityAdatper(R.layout.circum_city_listview, data);
                    mRecyclerView.setAdapter(adapter);
                    adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            switch (view.getId()) {
                                case R.id.ll_address:
                                    String city = data.get(position).getDetailAddress();
                                    Intent intent = new Intent();
                                    intent.putExtra("date",
                                            city);
                                    setResult(Activity.RESULT_OK, intent);
                                    finish();
                                    break;
                            }

                        }
                    });
                }
            } else {
                ToastUtil.showS(CircumCityActivity.this,
                        "222");
            }
        } else {
            // ToastUtil.showerror(this, rCode);
            ToastUtil.showS(this, rCode + "");
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int rCode) {

    }

    /**
     * 设置详情地址
     *
     * @param detailAddress
     */
    public void setDetailAddress(String detailAddress) {
        edSearch.setText(detailAddress);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //   super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_REGION_PICK) {
            if (data != null) {
                city = data.getStringExtra("date");
                tvAddress.setText(city);
                // UtilPreference.saveString(getActivity(), "city", city);
                //initData(city);
                //tvAddress.setText(city);
            }
        }
    }
}
