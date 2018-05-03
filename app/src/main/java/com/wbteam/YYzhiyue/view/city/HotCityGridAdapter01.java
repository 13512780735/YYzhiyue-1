package com.wbteam.YYzhiyue.view.city;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.MyBaseAdapter;
import com.wbteam.YYzhiyue.view.city.widget.CityModel;

import java.util.List;

/**
 * author zaaach on 2016/1/26.
 */
public class HotCityGridAdapter01 extends MyBaseAdapter<CityModel> {


    public HotCityGridAdapter01(Context context, List<CityModel> list) {
        super(context, list);
    }
    @Override
    public View getItemView(int position, View view, ViewGroup parent) {
        HotCityViewHolder holder;
        if (view == null) {
            view =  getInflater().inflate(R.layout.item_hot_city_gridview, parent, false);
            holder = new HotCityViewHolder();
            holder.name = (TextView) view.findViewById(R.id.tv_hot_city_name);
            view.setTag(holder);
        } else {
            holder = (HotCityViewHolder) view.getTag();
        }
        CityModel cityModel=getItem(position);
        holder.name.setText(cityModel.getName());
        return view;
    }

    public static class HotCityViewHolder {
        TextView name;
    }
}
