package com.wbteam.YYzhiyue.adapter.appointment;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.WeiboCommentModel;
import com.wbteam.YYzhiyue.view.CircleImageView;

import java.util.List;

/**
 * Created by admin on 2018/4/24.
 */

public class WeiboCommentAdapter extends BaseQuickAdapter<WeiboCommentModel.ListBean, BaseViewHolder> {
    public WeiboCommentAdapter(int layoutResId, List<WeiboCommentModel.ListBean> data) {
        super(R.layout.weibo_comment_list_view, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, WeiboCommentModel.ListBean listBean) {
        baseViewHolder.setText(R.id.group_content,listBean.getContent());
        baseViewHolder.setText(R.id.tv_time,listBean.getCreate_time());
        baseViewHolder.setText(R.id.group_name,listBean.getUser().getNickname());
        ImageLoader.getInstance().displayImage(listBean.getUser().getHeadimg(), (CircleImageView) baseViewHolder.getView(R.id.group_avatar));
        Drawable country = mContext.getResources().getDrawable(R.mipmap.icon_boy);
        country.setBounds(0, 0, country.getMinimumWidth(), country.getMinimumHeight());
        Drawable country1 = mContext.getResources().getDrawable(R.mipmap.icon_girl);
        country1.setBounds(0, 0, country1.getMinimumWidth(), country1.getMinimumHeight());
        if ("1".equals(listBean.getUser().getSex())) {
            baseViewHolder.setText(R.id.tv_gender, listBean.getUser().getAge());
            ((TextView) baseViewHolder.getView(R.id.tv_gender)).setCompoundDrawables(country, null, null, null);
        } else {
            baseViewHolder.setText(R.id.tv_gender, listBean.getUser().getAge());
            ((TextView) baseViewHolder.getView(R.id.tv_gender)).setCompoundDrawables(country1, null, null, null);
        }
    }
}
