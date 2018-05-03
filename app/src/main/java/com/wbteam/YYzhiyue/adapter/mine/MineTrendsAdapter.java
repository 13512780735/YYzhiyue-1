package com.wbteam.YYzhiyue.adapter.mine;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.WeiboListModel;
import com.wbteam.YYzhiyue.view.RoundImageView;

import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 */

public class MineTrendsAdapter extends BaseQuickAdapter<WeiboListModel.ListBean, BaseViewHolder> {
    public MineTrendsAdapter(int layoutResId, List<WeiboListModel.ListBean> data) {
        super(R.layout.post_trends_image_items, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, WeiboListModel.ListBean listBean) {
        ImageLoader.getInstance().displayImage(listBean.getPic().get(0).getPath(), (RoundImageView) baseViewHolder.getView(R.id.iv_image));
        baseViewHolder.setText(R.id.tv_message, listBean.getComment_count());
        baseViewHolder.setText(R.id.tv_likes, listBean.getLike_count());
        baseViewHolder.setText(R.id.pic_number, listBean.getUser().getPiccount());
    }
}