package com.wbteam.YYzhiyue.adapter.reward;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.RewardModel;
import com.wbteam.YYzhiyue.ui.reward.RewardDetailsActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/23.
 */

public class RewardReAdapter extends BaseQuickAdapter<RewardModel.ListBean, BaseViewHolder> {
    public RewardReAdapter(int layoutResId, List<RewardModel.ListBean> data) {
        super(R.layout.reward_listview_items, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final RewardModel.ListBean rewardModel) {
       // baseViewHolder.setText(R.id.tv_number, "人数：" + rewardModel.getAttend_count() + " / " + rewardModel.getLimitCount());
        baseViewHolder.setText(R.id.tv_name, "发起人：" + rewardModel.getUser().getNickname());
        baseViewHolder.setText(R.id.tv_redPacket, "红包金额：" + rewardModel.getAmount());
      //  baseViewHolder.getView(R.id.tv_redPacket).setVisibility(View.GONE);
        baseViewHolder.setText(R.id.tv_time, "时间：" + rewardModel.getCreate_time());
        String sex = rewardModel.getSex();
        if ("0".equals(sex)) {
            baseViewHolder.setText(R.id.tv_grade, "要求性别：不限");
        } else if ("1".equals(sex)) {
            baseViewHolder.setText(R.id.tv_grade, "要求性别：男");
        } else {
            baseViewHolder.setText(R.id.tv_grade, "要求性别：女");
        }
        baseViewHolder.setOnClickListener(R.id.tv_apply, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    ToastUtil.showS(mContext, "11");
                Intent intent = new Intent(mContext, RewardDetailsActivity.class);
                intent.putExtra("rewardModel", rewardModel);
                mContext.startActivity(intent);
            }
        });
        Glide.with(mContext).load(rewardModel.getUser().getHeadimg())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.icon_default_picture)
                .error(R.mipmap.icon_default_picture)
                .centerCrop().override(1090, 1090 * 3 / 4)
                .crossFade().into((ImageView) baseViewHolder.getView(R.id.iv_avatar));

    //    ImageLoader.getInstance().displayImage(rewardModel.getUser().getHeadimg(), (ImageView) baseViewHolder.getView(R.id.iv_avatar));
    }
}
