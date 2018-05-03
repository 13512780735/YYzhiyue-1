package com.wbteam.YYzhiyue.adapter;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbteam.YYzhiyue.Entity.PoiAddressBean;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.util.UtilPreference;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by admin on 2018/3/15.
 */

public class CircumcityAdatper extends BaseQuickAdapter<PoiAddressBean, BaseViewHolder> {

    private double longitude;
    private double latitude;

    public CircumcityAdatper(int layoutResId, List<PoiAddressBean> data) {
        super(R.layout.circum_city_listview, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, PoiAddressBean poiAddressBean) {


        baseViewHolder.setText(R.id.tv_name, poiAddressBean.getDetailAddress());
        baseViewHolder.setText(R.id.tv_details, poiAddressBean.getText());

        baseViewHolder.addOnClickListener(R.id.ll_address);
        longitude = Double.valueOf(UtilPreference.getStringValue(mContext, "lon"));
        latitude = Double.valueOf(UtilPreference.getStringValue(mContext, "lat"));
        double lon = poiAddressBean.getLongitude();
        double lat = poiAddressBean.getLatitude();
        LatLng start = new LatLng(latitude, longitude);
        LatLng end = new LatLng(lat, lon);
        DecimalFormat df = new DecimalFormat(".00");
        double mile=AMapUtils.calculateLineDistance(start, end);
        if(mile<1000){
            baseViewHolder.setText(R.id.tv_distance, "0"+df.format(mile/1000) + " 千米");
        }else{
            baseViewHolder.setText(R.id.tv_distance, df.format(mile/1000) + " 千米");
        }

    }
}
