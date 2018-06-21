package com.wbteam.YYzhiyue.adapter.reward;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.MyBaseAdapter;
import com.wbteam.YYzhiyue.network.api_service.model.TagModel1;
import com.wbteam.YYzhiyue.view.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/6/21.
 */

public class RewardTagsAdapter extends BaseAdapter {

    private List<TagModel1.ListBean> mList = new ArrayList<>();

    private Context mContext;

    public RewardTagsAdapter(Context context, List<TagModel1.ListBean> list) {
        super();
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        if (mList.size() >= 10) {
            return 10;
        } else {
            return mList.size();
        }
    }

    @Override
    public TagModel1.ListBean getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.items_reward_tags_item, parent, false);
            holder = new ChildHolderOne();
            holder.ivPicture = (RoundImageView) convertView.findViewById(R.id.tag_picture);
            holder.tvName = (TextView) convertView.findViewById(R.id.tag_name);

            convertView.setTag(holder);
        } else {
            holder = (ChildHolderOne) convertView.getTag();
        }
        final TagModel1.ListBean data = mList.get(position);
        holder.tvName.setText(data.getTitle());
        ImageLoader.getInstance().displayImage(data.getImage(), holder.ivPicture);
        return convertView;
    }

    //    @Override
//    public View getItemView(int position, View convertView, ViewGroup parent) {
//        ChildHolderOne holder;
//        if (convertView == null) {
//            convertView = getInflater().inflate(
//                    R.layout.items_reward_tags_item, parent, false);
//            holder = new ChildHolderOne();
//            holder.ivPicture = (RoundImageView) convertView.findViewById(R.id.tag_picture);
//            holder.tvName = (TextView) convertView.findViewById(R.id.tag_name);
//
//            convertView.setTag(holder);
//        } else {
//            holder = (ChildHolderOne) convertView.getTag();
//        }
//        TagModel1.ListBean data = getItem(position);
//        holder.tvName.setText(data.getTitle());
//        ImageLoader.getInstance().displayImage(data.getImage(), holder.ivPicture);
//        return convertView;
//    }
//
    class ChildHolderOne {
        RoundImageView ivPicture;
        TextView tvName;
    }


}
