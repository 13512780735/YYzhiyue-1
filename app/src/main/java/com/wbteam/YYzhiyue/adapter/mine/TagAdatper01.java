package com.wbteam.YYzhiyue.adapter.mine;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.MyBaseAdapter;
import com.wbteam.YYzhiyue.network.api_service.model.TagNameModel;

import java.util.List;

/**
 * Created by Administrator on 2018/2/26.
 */

public class TagAdatper01 extends MyBaseAdapter<TagNameModel> {
    public TagAdatper01(Context context, List<TagNameModel> tagNameModels) {
        super(context, tagNameModels);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(
                    R.layout.mine_gridview_items, parent, false);
            holder.tv_name = (TextView) convertView
                    .findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TagNameModel listBean = getItem(position);
        holder.tv_name.setText(listBean.getName());
        return convertView;
    }

    public class ViewHolder {
        private TextView tv_name;
    }
}
