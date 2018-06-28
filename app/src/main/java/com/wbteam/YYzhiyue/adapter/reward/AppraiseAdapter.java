package com.wbteam.YYzhiyue.adapter.reward;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.MyBaseAdapter;
import com.wbteam.YYzhiyue.network.api_service.model.CommentBean;
import com.wbteam.YYzhiyue.view.city.HotCityGridAdapter01;
import com.wbteam.YYzhiyue.view.city.widget.CityModel;

import java.util.List;

/**
 * Created by admin on 2018/6/28.
 */

public class AppraiseAdapter extends MyBaseAdapter<CommentBean.ListBean> {

    private int location;
    public AppraiseAdapter(Context context, List<CommentBean.ListBean> list) {
        super(context, list);
    }
    public void setSeclection(int position) {
        location = position;
    }
    @Override
    public View getItemView(int position, View view, ViewGroup parent) {
        HotCityViewHolder holder;
        if (view == null) {
            view = getInflater().inflate(R.layout.appraise_items, parent, false);
            holder = new HotCityViewHolder();
            holder.name = (TextView) view.findViewById(R.id.tv_name);
            view.setTag(holder);
        } else {
            holder = (HotCityViewHolder) view.getTag();
        }
        if(location==position){
            holder.name.setTextColor(getContext().getResources().getColor(R.color.colorAccent));
            holder.name.setBackgroundResource(R.drawable.button_home_bg03);
        }else{
            holder.name.setTextColor(getContext().getResources().getColor(R.color.font_color));
            holder.name.setBackgroundResource(R.drawable.button_home_bg04);
        }
        CommentBean.ListBean listBean = getItem(position);
        holder.name.setText(listBean.getTitle());
        return view;
    }

    public static class HotCityViewHolder {
        TextView name;
    }
}
