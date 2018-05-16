package com.wbteam.YYzhiyue.adapter.reward;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.AddRewardModel;
import com.wbteam.YYzhiyue.view.CircleImageView;

import java.util.List;

/**
 * Created by admin on 2018/3/14.
 */

public class AddRewardAdapter extends BaseQuickAdapter<AddRewardModel.ListBean, BaseViewHolder> {
    public AddRewardAdapter(int layoutResId, List<AddRewardModel.ListBean> data) {
        super(R.layout.add_reward_listview, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, AddRewardModel.ListBean listBean) {
        String status = listBean.getStatus();
        String isbid = listBean.getIsbid();
        String iscomment = listBean.getIscomment();
        ImageLoader.getInstance().displayImage(listBean.getUser().getHeadimg(), (ImageView) baseViewHolder.getView(R.id.iv_avatar));
        baseViewHolder.setText(R.id.tv_name, "发起人：" + listBean.getUser().getNickname());
        baseViewHolder.setText(R.id.tv_time, "时间：" + listBean.getS_time());
        baseViewHolder.setText(R.id.tv_type, "类型：" + listBean.getTagstr());
        baseViewHolder.setText(R.id.tv_address, "地点：" + listBean.getAddress());
        baseViewHolder.setText(R.id.tv_price, "金额：" + listBean.getAmount());
        baseViewHolder.getView(R.id.tv_price).setVisibility(View.GONE);
        baseViewHolder.setText(R.id.tv_name, "金额：" + listBean.getAmount());
        baseViewHolder.getView(R.id.tv_name).setVisibility(View.GONE);
        if ("0".equals(isbid)) {
            baseViewHolder.getView(R.id.tv_confirm).setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.tv_status, "待确认");
            baseViewHolder.setText(R.id.tv_confirm, "取消参加");
        } else if ("1".equals(isbid)) {
            if ("3".equals(status)) {
                baseViewHolder.getView(R.id.tv_confirm).setVisibility(View.VISIBLE);
                if("0".equals(iscomment)){
                    baseViewHolder.getView(R.id.tv_confirm).setVisibility(View.VISIBLE);
                    baseViewHolder.setText(R.id.tv_status, "待评价");
                    baseViewHolder.setText(R.id.tv_confirm, "完成约会");
                }else{
                    baseViewHolder.setText(R.id.tv_status, "待对方评价");
                    baseViewHolder.getView(R.id.tv_confirm).setVisibility(View.GONE);
                }

                baseViewHolder.setText(R.id.tv_confirm, "完成约会");
            } else if ("4".equals(status)) {
                baseViewHolder.setText(R.id.tv_status, "已完成");
                baseViewHolder.getView(R.id.tv_confirm).setVisibility(View.GONE);
            }
        }
        baseViewHolder.addOnClickListener(R.id.tv_confirm);
    }
}
