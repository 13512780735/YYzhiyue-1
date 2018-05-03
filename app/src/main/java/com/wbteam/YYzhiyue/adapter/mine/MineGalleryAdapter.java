package com.wbteam.YYzhiyue.adapter.mine;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.GalleryListModel;

import java.util.List;

/**
 * Created by admin on 2018/4/17.
 */

public class MineGalleryAdapter extends BaseQuickAdapter<GalleryListModel.ListBean, BaseViewHolder> {
    public MineGalleryAdapter(int layoutResId, List<GalleryListModel.ListBean> data) {
        super(R.layout.mine_gallery_list_view, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, GalleryListModel.ListBean listBean) {
        ImageLoader.getInstance().displayImage(listBean.getPic().get(0).getPath(), (ImageView) baseViewHolder.getView(R.id.iv_image));
        baseViewHolder.addOnClickListener(R.id.bt_del);
    }
}
