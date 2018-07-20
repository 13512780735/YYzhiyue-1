package com.wbteam.YYzhiyue.adapter.appointment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.TypedValue;
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
import com.wbteam.YYzhiyue.network.api_service.model.DatingModel;
import com.wbteam.YYzhiyue.util.StringUtil;
import com.wbteam.YYzhiyue.util.UtilPreference;

import java.util.List;

/**
 * Created by Administrator on 2018/1/23.
 */

public class Recommend01Adpater extends BaseQuickAdapter<DatingModel.ListBean, BaseViewHolder> {
    public Recommend01Adpater(int layoutResId, List<DatingModel.ListBean> data) {
        super(R.layout.recommend_gridview01_items, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, DatingModel.ListBean listBean) {
        int height = UtilPreference.getIntValue(mContext, "height");
        int width = UtilPreference.getIntValue(mContext, "width");
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) baseViewHolder.getView(R.id.friend_avatar).getLayoutParams();
        params.width = height;
        params.height = height;
        baseViewHolder.getView(R.id.friend_avatar).setLayoutParams(params);
      //  ImageLoader.getInstance().displayImage(listBean.getHeadimg(), (ImageView) baseViewHolder.getView(R.id.friend_avatar));

        Glide.with(mContext).load(listBean.getHeadimg())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.icon_default_picture)
                .error(R.mipmap.icon_default_picture                                                                                                                                     )
                .centerCrop().override(1090, 1090 * 3 / 4)
                .crossFade().into((ImageView) baseViewHolder.getView(R.id.friend_avatar));
//

//        Matrix matrix = new Matrix();
//        int width2 = width1;
//        int height1 = width1;
//        matrix.postTranslate(width2, height1);
        //  baseViewHolder.getView(R.id.friend_avatar).setMinimumWidth(width1);
        // baseViewHolder.getView(R.id.friend_avatar).setMinimumHeight(width1);
        // ((ImageView) baseViewHolder.getView(R.id.friend_avatar)).setImageMatrix(matrix);
//
//        FrameLayout mFrameLayout = baseViewHolder.getView(R.id.fl__avatar);
//        mFrameLayout.setTop(height - 20);
        if (StringUtil.isBlank(listBean.getDescription())) {
            baseViewHolder.setText(R.id.friend_characteristic, "你不主动，我怎么知道你的想法");

        } else {
            baseViewHolder.setText(R.id.friend_characteristic, listBean.getDescription());
        }
        baseViewHolder.setText(R.id.friend_time, listBean.getCityname());
        baseViewHolder.setText(R.id.friend_distance, listBean.getPrice());
        baseViewHolder.setText(R.id.friend_name, listBean.getNickname());
        //Glide.with(mContext).load(listBean.getHeadimg()).apply(options).into((ImageView) baseViewHolder.getView(R.id.iv_avatar));
      // ImageLoader.getInstance().displayImage(listBean.getHeadimg(), (ImageView) baseViewHolder.getView(R.id.iv_avatar));
        Glide.with(mContext).load(listBean.getHeadimg())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.icon_default_picture)
                .error(R.mipmap.icon_default_picture)
                .centerCrop().override(1090, 1090 * 3 / 4)
                .crossFade().into((ImageView) baseViewHolder.getView(R.id.iv_avatar));
    }
}
