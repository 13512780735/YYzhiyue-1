package com.wbteam.YYzhiyue.adapter.neaeby;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.MainListFriendModel;
import com.wbteam.YYzhiyue.util.UtilPreference;

import java.util.List;

/**
 * Created by Administrator on 2018/1/19.
 */

public class FriendHeaderAdapter extends BaseQuickAdapter<MainListFriendModel.ListBean, BaseViewHolder> {
    public FriendHeaderAdapter(int layoutResId, List<MainListFriendModel.ListBean> data) {
        super(R.layout.nearby_friend_gridview_items, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MainListFriendModel.ListBean listBean) {
        int height = UtilPreference.getIntValue(mContext, "height");
        int width = UtilPreference.getIntValue(mContext, "width");
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) baseViewHolder.getView(R.id.friend_avatar).getLayoutParams();
        params.width = height;
        params.height = height;
        baseViewHolder.getView(R.id.friend_avatar).setLayoutParams(params);
        ImageLoader.getInstance().displayImage(listBean.getHeadimg(), (ImageView) baseViewHolder.getView(R.id.friend_avatar));
        Glide.with(mContext).load(listBean.getHeadimg())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.icon_default_picture)
                .error(R.mipmap.icon_default_picture)
                .centerCrop().override(1090, 1090 * 3 / 4)
                .crossFade().into((ImageView) baseViewHolder.getView(R.id.friend_avatar));

        ImageLoader.getInstance().displayImage(listBean.getHeadimg(), (ImageView) baseViewHolder.getView(R.id.friend_avatar));
        baseViewHolder.setText(R.id.friend_name, listBean.getNickname());
        baseViewHolder.setText(R.id.friend_gender, listBean.getAge());
        if ("1".equals(listBean.getSex())) {
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.icon_boy);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((TextView) baseViewHolder.getView(R.id.friend_gender)).setCompoundDrawables(drawable, null, null, null);
        } else {
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.icon_girl);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            ((TextView) baseViewHolder.getView(R.id.friend_gender)).setCompoundDrawables(drawable, null, null, null);
        }
        if ("1".equals(listBean.getVideoauth())) {
            baseViewHolder.getView(R.id.tv_videoauth).setVisibility(View.VISIBLE);

        } else {
            baseViewHolder.getView(R.id.tv_videoauth).setVisibility(View.GONE);
        }
        baseViewHolder.setText(R.id.friend_photo_number, listBean.getPiccount() + " 张照片");
        baseViewHolder.setText(R.id.friend_sign, listBean.getAstro());
        baseViewHolder.setText(R.id.friend_characteristic, listBean.getHeight() + "/" + listBean.getWeight() + "/" + listBean.getCityname());
        baseViewHolder.setText(R.id.friend_distance, listBean.getCityname());


    }

//    @Override
//    public void onClick(View view) {
//        if (view.getId() == R.id.content_tv) {
//            Toast.makeText(Myapp.context, "Item中某个控件被点击了", Toast.LENGTH_SHORT).show();
//        }
//    }
}
