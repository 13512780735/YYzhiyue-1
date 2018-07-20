package com.wbteam.YYzhiyue.adapter.Onlookers;

import android.content.Context;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.OnLookerModel;
import com.wbteam.YYzhiyue.util.UtilPreference;

import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 */

public class OnlookersAdapter extends BaseQuickAdapter<OnLookerModel.ListBean,BaseViewHolder>{
    public OnlookersAdapter(int layoutResId, List<OnLookerModel.ListBean> data) {
        super(R.layout.onlookers_gridview_items, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, OnLookerModel.ListBean listBean) {
          //  baseViewHolder.setTag(R.id.ll_likes,caseEntity.getUrl());


        int height = UtilPreference.getIntValue(mContext, "height");
        int width = UtilPreference.getIntValue(mContext, "width");
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) baseViewHolder.getView(R.id.framelayout).getLayoutParams();
        params.width = height;
        params.height = height;
        baseViewHolder.getView(R.id.framelayout).setLayoutParams(params);
//        ImageLoader.getInstance().displayImage(listBean.getPhoto_url(), (ImageView) baseViewHolder.getView(R.id.friend_avatar));
//        ImageLoader.getInstance().displayImage(listBean.getUser().getHeadimg(), (ImageView) baseViewHolder.getView(R.id.iv_avatar));

        Glide.with(mContext).load(listBean.getPhoto_url())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.icon_default_picture)
                .error(R.mipmap.icon_default_picture)
                .centerCrop().override(1090, 1090 * 3 / 4)
                .crossFade().into((ImageView) baseViewHolder.getView(R.id.friend_avatar));
        Glide.with(mContext).load(listBean.getUser().getHeadimg())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.icon_default_picture)
                .error(R.mipmap.icon_default_picture)
                .centerCrop().override(1090, 1090 * 3 / 4)
                .crossFade().into((ImageView) baseViewHolder.getView(R.id.iv_avatar));
//        WindowManager wm = (WindowManager) mContext
//                .getSystemService(Context.WINDOW_SERVICE);
//        int width = wm.getDefaultDisplay().getWidth();
//        int width1 = width / 2 - 20;
//        baseViewHolder.getView(R.id.friend_avatar).setMinimumWidth(width1);
//        baseViewHolder.getView(R.id.friend_avatar).setMinimumHeight(width1);
        baseViewHolder.setText(R.id.friend_name,listBean.getUser().getNickname());
        baseViewHolder.setText(R.id.friend_characteristic,listBean.getUser().getDescription());
        baseViewHolder.setText(R.id.tv_onlooker,listBean.getUser().getView_count());
        baseViewHolder.setText(R.id.tv_likes,listBean.getUser().getLike_count());

    }
}
