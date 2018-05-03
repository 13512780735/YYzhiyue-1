package com.wbteam.YYzhiyue.adapter.reward;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.ApplyPersonModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/3/14.
 */

public class RewardDetailsAdapter extends BaseQuickAdapter<ApplyPersonModel.ListBean, BaseViewHolder> {
    public RewardDetailsAdapter(int layoutResId, List<ApplyPersonModel.ListBean> data) {
        super(R.layout.reward_details_listview, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ApplyPersonModel.ListBean listBean) {
        ImageLoader.getInstance().displayImage(listBean.getUser().getHeadimg(), (ImageView) baseViewHolder.getView(R.id.group_avatar));
        String isbid = listBean.getIsbid();
        String sex = listBean.getUser().getSex();
        baseViewHolder.setText(R.id.group_name, listBean.getUser().getNickname());
        if ("1".equals(sex)) {
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.icon_boy);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((TextView) baseViewHolder.getView(R.id.tv_gender)).setCompoundDrawables(drawable, null, null, null);
            baseViewHolder.setText(R.id.tv_gender, listBean.getUser().getAge());
        } else {
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.icon_girl);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((TextView) baseViewHolder.getView(R.id.tv_gender)).setCompoundDrawables(drawable, null, null, null);
            baseViewHolder.setText(R.id.tv_gender, listBean.getUser().getAge());
        }
        baseViewHolder.setText(R.id.group_content, listBean.getUser().getHeight() + listBean.getUser().getWeight() + listBean.getUser().getAppearance());
        if ("0".equals(isbid)) {
            baseViewHolder.getView(R.id.tv_confirm).setBackgroundResource(R.mipmap.icon_unconfirm);
            baseViewHolder.addOnClickListener(R.id.tv_confirm);
        } else {
            baseViewHolder.getView(R.id.tv_confirm).setBackgroundResource(R.mipmap.icon_confirm);
        }
    }
}
