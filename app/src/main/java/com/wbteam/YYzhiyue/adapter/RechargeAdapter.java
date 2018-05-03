package com.wbteam.YYzhiyue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.mine.TagAdapter;
import com.wbteam.YYzhiyue.citypicker.model.RechargeModel;
import com.wbteam.YYzhiyue.network.api_service.model.TagModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/3/21.
 */

public class RechargeAdapter extends BaseAdapter{
    private int selectorPosition;
    private List<RechargeModel> mList = new ArrayList<>();

    private Context mContext;

    public RechargeAdapter(Context context, List<RechargeModel> list) {
        super();
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public RechargeModel getItem(int position) {
        return mList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ChildHolderOne holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.recharge_listview_items, parent, false);
            holder = new ChildHolderOne();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolderOne) convertView.getTag();
        }
        final RechargeModel data = mList.get(position);

        if (selectorPosition == position) {
            holder.tvTitle.setBackgroundResource(R.drawable.shape_round_group_btn_bg05);
        } else {
            //其他的恢复原来的状态
            holder.tvTitle.setBackgroundResource(R.drawable.shape_round_group_btn_bg10);
        }

        holder.tvTitle.setText("¥ "+data.getName());
        return convertView;
    }
    public void changeState(int pos) {
        selectorPosition = pos;
        notifyDataSetChanged();

    }
    class ChildHolderOne {
        TextView tvTitle;
    }
}
