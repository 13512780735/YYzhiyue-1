package com.wbteam.YYzhiyue.adapter.mine;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.MyBonusModel;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by admin on 2018/4/25.
 */

public class MyBounsAdapter extends BaseQuickAdapter<MyBonusModel.ListBean, BaseViewHolder> {
    public MyBounsAdapter(int layoutResId, List<MyBonusModel.ListBean> data) {
        super(R.layout.bouns_list_view, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MyBonusModel.ListBean listBean) {
        String amount=listBean.getAmount();
        String amount1 = amount.substring(0,amount.indexOf("."));
        baseViewHolder.setText(R.id.tv_amount, amount1+"元");
        baseViewHolder.setText(R.id.tv_title, listBean.getTitle());
        baseViewHolder.setText(R.id.tv_type_title, listBean.getType_title());
        baseViewHolder.setText(R.id.tv_create_time, "奖励日期"+listBean.getCreate_time());
        baseViewHolder.setText(R.id.tv_deadline, "有效期"+listBean.getDeadline());
        if ("0".equals(listBean.getStatus())) {
            baseViewHolder.setText(R.id.tv_status, "已使用");
        } else if ("1".equals(listBean.getStatus())) {
            baseViewHolder.setText(R.id.tv_status, "未使用");
        }

    }
}
