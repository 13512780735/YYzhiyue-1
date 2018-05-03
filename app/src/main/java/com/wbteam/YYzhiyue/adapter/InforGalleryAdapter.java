package com.wbteam.YYzhiyue.adapter;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.DatingInfoModel;
import com.wbteam.YYzhiyue.util.UtilPreference;

import java.util.List;

/**
 * Created by admin on 2018/4/17.
 */

public class InforGalleryAdapter extends BaseQuickAdapter<DatingInfoModel.PicsBean, BaseViewHolder> {

    public InforGalleryAdapter(int layoutResId, List<DatingInfoModel.PicsBean> data) {
        super(R.layout.item_pic_thumb, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, DatingInfoModel.PicsBean picsBean) {
        int width = UtilPreference.getIntValue(mContext, "width");
        int width01 = width / 4 - 50;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width01, width01);
        baseViewHolder.getView(R.id.thumb_iv).setLayoutParams(lp);
        ImageLoader.getInstance().displayImage(picsBean.getPath(), (ImageView) baseViewHolder.getView(R.id.thumb_iv));

    }
}
