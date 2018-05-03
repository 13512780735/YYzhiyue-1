package com.wbteam.YYzhiyue.adapter.mine;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.BonusModel;

import java.util.List;

/**
 * Created by admin on 2018/4/25.
 */

public class BounsAdapter extends BaseQuickAdapter<BonusModel.ListBean, BaseViewHolder> {
    public BounsAdapter(int layoutResId, List<BonusModel.ListBean> data) {
        super(R.layout.bouns_list_view, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, BonusModel.ListBean listBean) {
        baseViewHolder.getView(R.id.tv_status).setVisibility(View.GONE);
        baseViewHolder.getView(R.id.tv_type_title).setVisibility(View.GONE);
        baseViewHolder.getView(R.id.tv_create_time).setVisibility(View.GONE);
        String amount=listBean.getAmount();
        String amount1 = amount.substring(0,amount.indexOf("."));
        baseViewHolder.setText(R.id.tv_amount, amount1+"元");
        baseViewHolder.setText(R.id.tv_title, listBean.getTitle());
        baseViewHolder.setText(R.id.tv_deadline, "有效期"+listBean.getDeadline());
    }
}
