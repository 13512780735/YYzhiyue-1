package com.wbteam.YYzhiyue.adapter.appointment;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.DatingModel;
import com.wbteam.YYzhiyue.view.CircleImageView;

import java.util.List;

/**
 * Created by Administrator on 2018/1/23.
 */

public class RecommendAdpater extends BaseQuickAdapter<DatingModel.ListBean,BaseViewHolder> {
    public RecommendAdpater(int layoutResId, List<DatingModel.ListBean> data) {
        super(R.layout.recommend_gridview_items, data);
    }

//    @Override
//    public int getItemCount() {
//        return super.getItemCount();
//    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, DatingModel.ListBean listBean) {
        baseViewHolder.setText(R.id.tvName,listBean.getNickname());
       // baseViewHolder.setTag(R.id.tvName,"乱舞小天后"+listBean.getNickname());
     //   ImageLoader.getInstance().displayImage(listBean.getHeadimg(), (CircleImageView) baseViewHolder.getView(R.id.iv_avatar));
        Glide.with(mContext).load(listBean.getHeadimg())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.icon_default_picture)
                .error(R.mipmap.icon_default_picture)
                .centerCrop().override(1090, 1090 * 3 / 4)
                .crossFade().into((ImageView) baseViewHolder.getView(R.id.iv_avatar));
    }
}
