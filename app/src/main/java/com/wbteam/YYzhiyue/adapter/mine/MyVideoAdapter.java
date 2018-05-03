package com.wbteam.YYzhiyue.adapter.mine;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.MyVideoModel;
import com.wbteam.YYzhiyue.util.UtilPreference;

import java.util.List;

/**
 * Created by admin on 2018/3/30.
 */

public class MyVideoAdapter extends BaseQuickAdapter<MyVideoModel.ListBean, BaseViewHolder> {
    public MyVideoAdapter(int layoutResId, List<MyVideoModel.ListBean> data) {
        super(R.layout.my_viedeo_listview_items, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MyVideoModel.ListBean listBean) {
        int height = UtilPreference.getIntValue(mContext, "height");
        int width = UtilPreference.getIntValue(mContext, "width");
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) baseViewHolder.getView(R.id.friend_avatar).getLayoutParams();
        params.width = height;
        params.height = height;
        baseViewHolder.getView(R.id.friend_avatar).setLayoutParams(params);
        ImageLoader.getInstance().displayImage(listBean.getPhoto_url(), (ImageView) baseViewHolder.getView(R.id.friend_avatar));
        baseViewHolder.addOnClickListener(R.id.tv_del);
        baseViewHolder.setText(R.id.tv_onlooker,listBean.getView_count());
    }
}
