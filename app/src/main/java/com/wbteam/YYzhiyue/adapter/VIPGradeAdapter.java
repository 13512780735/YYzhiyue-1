package com.wbteam.YYzhiyue.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.VipModel;

import java.util.List;

/**
 * Created by admin on 2018/3/22.
 */

public class VIPGradeAdapter extends BaseQuickAdapter<VipModel.ListBean, BaseViewHolder> {
    public VIPGradeAdapter(int layoutResId, List<VipModel.ListBean> data) {
        super(R.layout.vip_renew_listiview, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VipModel.ListBean item) {
        if ("1".equals(item.getId())) {
            helper.getView(R.id.tv_month).setBackgroundResource(R.drawable.shape_round_group_btn_bg05);
        }
        helper.setText(R.id.tv_month, item.getDeadline());
        helper.setText(R.id.tv_name, item.getTitle());
        helper.setText(R.id.tv_details, item.getDescription());
        helper.setText(R.id.tv_price, item.getAmount()+"元");
        helper.addOnClickListener(R.id.tv_price);
    }
}
