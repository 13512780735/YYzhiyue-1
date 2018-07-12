package com.wbteam.YYzhiyue.adapter.message;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.NotifyModel;

import java.util.List;

/**
 * Created by admin on 2018/7/12.
 */

public class NotifyAdapter extends BaseQuickAdapter<NotifyModel.ListBean, BaseViewHolder> {
    public NotifyAdapter(int layoutResId, List<NotifyModel.ListBean> data) {
        super(R.layout.notify_listview_items, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, NotifyModel.ListBean item) {
        baseViewHolder.setText(R.id.tv_title, item.getTitle());
        baseViewHolder.setText(R.id.tv_content, item.getContent());
        baseViewHolder.setText(R.id.tv_time, item.getCreate_time());
    }
}
