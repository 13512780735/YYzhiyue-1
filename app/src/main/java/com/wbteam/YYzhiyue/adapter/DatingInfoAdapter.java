package com.wbteam.YYzhiyue.adapter;

import android.content.Context;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.DatingInfoModel;

import java.util.List;

/**
 * Created by admin on 2018/3/27.
 */

public class DatingInfoAdapter extends BaseQuickAdapter<DatingInfoModel.PicsBean, BaseViewHolder> {
    public DatingInfoAdapter(int layoutResId, List<DatingInfoModel.PicsBean> data) {
        super(R.layout.datinginfo_gridview_items, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, DatingInfoModel.PicsBean picBean) {
        ImageLoader.getInstance().displayImage(picBean.getPath(), (ImageView) baseViewHolder.getView(R.id.iv_avatar01));
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int width1 = width / 4 - 20;
        baseViewHolder.getView(R.id.iv_avatar01).setMinimumWidth(width1);
        baseViewHolder.getView(R.id.iv_avatar01).setMinimumHeight(width1);
    }
}
