package com.wbteam.YYzhiyue.adapter.appointment;

import android.content.Context;
import android.view.WindowManager;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.DatingInfoModel;
import com.wbteam.YYzhiyue.network.api_service.model.GetweiboinfoModel;
import com.wbteam.YYzhiyue.network.api_service.model.WeiboListModel;

import java.util.List;

/**
 * Created by admin on 2018/4/3.
 */

public class AppointmentInfoAdapter  extends BaseQuickAdapter<GetweiboinfoModel.InfoBean.PicBean, BaseViewHolder> {
    public AppointmentInfoAdapter(int layoutResId, List<GetweiboinfoModel.InfoBean.PicBean> data) {
        super(R.layout.appointment01_gridview_items, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, GetweiboinfoModel.InfoBean.PicBean picsBean) {
        //baseViewHolder.setTag(R.id.iv_avatar01, picBean.getPath());
        ImageLoader.getInstance().displayImage(picsBean.getPath(), (ImageView) baseViewHolder.getView(R.id.iv_avatar01));
//        RequestOptions options = new RequestOptions();
//        options.placeholder(R.mipmap.banner);
//        Glide.with(mContext).load(picBean.getPath()).apply(options).into((ImageView) baseViewHolder.getView(R.id.iv_avatar01));
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int width1 = width / 4 - 20;
        baseViewHolder.getView(R.id.iv_avatar01).setMinimumWidth(width1);
        baseViewHolder.getView(R.id.iv_avatar01).setMinimumHeight(width1);
    }
}