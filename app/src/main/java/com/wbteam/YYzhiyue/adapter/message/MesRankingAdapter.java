package com.wbteam.YYzhiyue.adapter.message;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.MyfollowModel;

import java.util.List;

/**
 * Created by Administrator on 2018/1/23.
 */

public class MesRankingAdapter extends BaseQuickAdapter<MyfollowModel.ListBean, BaseViewHolder> {
    public MesRankingAdapter(int layoutResId, List<MyfollowModel.ListBean> data) {
        super(R.layout.mesrank_listview_items, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MyfollowModel.ListBean listBean) {
        // baseViewHolder.setText(R.id.group_content, "160cm 50kg 活波可爱" + caseEntity.getUrl());
        ImageLoader.getInstance().displayImage(listBean.getHeadimg(), (ImageView) baseViewHolder.getView(R.id.group_avatar));
        baseViewHolder.setText(R.id.group_name, listBean.getNickname());
        Drawable country = mContext.getResources().getDrawable(R.mipmap.icon_boy);
        country.setBounds(0, 0, country.getMinimumWidth(), country.getMinimumHeight());
        Drawable country1 = mContext.getResources().getDrawable(R.mipmap.icon_girl);
        country1.setBounds(0, 0, country1.getMinimumWidth(), country1.getMinimumHeight());
        if ("1".equals(listBean.getSex())) {
            baseViewHolder.setText(R.id.tv_gender, listBean.getAge());
            ((TextView) baseViewHolder.getView(R.id.tv_gender)).setCompoundDrawables(country, null, null, null);
        } else {
            baseViewHolder.setText(R.id.tv_gender, listBean.getAge());
            ((TextView) baseViewHolder.getView(R.id.tv_gender)).setCompoundDrawables(country1, null, null, null);
        }
        baseViewHolder.setText(R.id.group_content, listBean.getHeight() + listBean.getWeight() + listBean.getDescription());
    }
}
