package com.wbteam.YYzhiyue.adapter.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.TagModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/26.
 */

/**
 * Created by HanHailong on 15/10/19.
 */
public class TagAdapter extends BaseAdapter {

    private List<TagModel.ListBean> mList = new ArrayList<>();

    private Context mContext;

    public TagAdapter(Context context, List<TagModel.ListBean> list) {
        super();
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public TagModel.ListBean getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tag_listview_view, parent, false);
            holder = new ChildHolderOne();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolderOne) convertView.getTag();
        }
        final TagModel.ListBean data = mList.get(position);
        if (data.isChecked() == true) {
            holder.tvTitle.setBackgroundResource(R.drawable.shape_round_group_btn_bg05);
        } else {
            holder.tvTitle.setBackgroundResource(R.drawable.shape_round_group_btn_bg10);
        }
        holder.tvTitle.setText(data.getTitle());
        return convertView;
    }

    class ChildHolderOne {
        TextView tvTitle;
    }
}