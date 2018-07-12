package com.wbteam.YYzhiyue.ui.mine.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.WithdrawModel;

import java.util.List;

/**
 * Created by admin on 2018/7/12.
 */

public class WithdrawAdapter extends BaseQuickAdapter<WithdrawModel.ListBean, BaseViewHolder> {
    public WithdrawAdapter(int layoutResId, List<WithdrawModel.ListBean> data) {
        super(R.layout.withdrawlog_layout_items, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, WithdrawModel.ListBean item) {
        baseViewHolder.setText(R.id.title,item.getTitle());
        baseViewHolder.setText(R.id.apply_time,"申请时间："+item.getCreate_time());
        baseViewHolder.setText(R.id.grant_time,"打款时间："+item.getGranttime());
        baseViewHolder.setText(R.id.status,item.getStats());
        baseViewHolder.setText(R.id.amount,item.getAmount());
    }
}
