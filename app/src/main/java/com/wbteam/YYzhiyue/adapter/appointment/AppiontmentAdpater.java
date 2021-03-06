package com.wbteam.YYzhiyue.adapter.appointment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.DatingInfoModel;
import com.wbteam.YYzhiyue.network.api_service.model.WeiboListModel;
import com.wbteam.YYzhiyue.ui.neaeby.GalleryListActivity;
import com.wbteam.YYzhiyue.view.custom_scrollview.HorizontalPageLayoutManager;
import com.wbteam.YYzhiyue.view.custom_scrollview.PagingScrollHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/23.
 */

public class AppiontmentAdpater extends BaseQuickAdapter<WeiboListModel.ListBean, BaseViewHolder> {
    private AppiontmentAdpater01 mAdapter;
    private HorizontalPageLayoutManager horizontalPageLayoutManager;
    PagingScrollHelper scrollHelper = new PagingScrollHelper();
    private static List<WeiboListModel.ListBean.PicBean> data01 = new ArrayList<>();

//    static {
//        for (int i = 0; i < 4; i++) {
//            CaseEntity caseEntity = new CaseEntity();
//            caseEntity.setUrl(i + "");
//            data01.add(caseEntity);
//        }
//    }

    public AppiontmentAdpater(int layoutResId, List<WeiboListModel.ListBean> data) {
        super(R.layout.appointment01_listview_items, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final WeiboListModel.ListBean listBean) {
        // baseViewHolder.setTag(R.id.tv_praise, ListBean.getUrl());
        horizontalPageLayoutManager = new HorizontalPageLayoutManager(1, 4);
        data01 = listBean.getPic();
        mAdapter = new AppiontmentAdpater01(R.layout.appointment01_gridview_items, data01);
        RecyclerView mRecyclerView = baseViewHolder.getView(R.id.RecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        scrollHelper.setUpRecycleView(mRecyclerView);
        RecyclerView.LayoutManager layoutManager = null;
        layoutManager = horizontalPageLayoutManager;
        if (layoutManager != null) {
            mRecyclerView.setLayoutManager(layoutManager);
            scrollHelper.updateLayoutManger();
        }
        Glide.with(mContext).load(listBean.getUser().getHeadimg())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.icon_default_picture)
                .error(R.mipmap.icon_default_picture)
                .centerCrop().override(1090, 1090 * 3 / 4)
                .crossFade().into((ImageView) baseViewHolder.getView(R.id.iv_avatar));

        //ImageLoader.getInstance().displayImage(listBean.getUser().getHeadimg(), (ImageView) baseViewHolder.getView(R.id.iv_avatar));
//        RequestOptions options = new RequestOptions();
//        options.placeholder(R.mipmap.banner);
//        Glide.with(mContext).load(listBean.getUser().getHeadimg()).apply(options).into((ImageView) baseViewHolder.getView(R.id.iv_avatar));
        baseViewHolder.setText(R.id.friend_name, listBean.getUser().getNickname());
        Drawable country = mContext.getResources().getDrawable(R.mipmap.icon_boy);
        country.setBounds(0, 0, country.getMinimumWidth(), country.getMinimumHeight());
        Drawable country1 = mContext.getResources().getDrawable(R.mipmap.icon_girl);
        country1.setBounds(0, 0, country1.getMinimumWidth(), country1.getMinimumHeight());
        if ("1".equals(listBean.getUser().getSex())) {
            baseViewHolder.setText(R.id.friend_gender, listBean.getUser().getAge());
            ((TextView) baseViewHolder.getView(R.id.friend_gender)).setCompoundDrawables(country, null, null, null);
        } else {
            baseViewHolder.setText(R.id.friend_gender, listBean.getUser().getAge());
            ((TextView) baseViewHolder.getView(R.id.friend_gender)).setCompoundDrawables(country1, null, null, null);
        }
        baseViewHolder.setText(R.id.friend_sign, listBean.getUser().getAstro());
        baseViewHolder.setText(R.id.tv_time, listBean.getInterval_time());
        baseViewHolder.setText(R.id.tv_time01, listBean.getSelect_time());
        baseViewHolder.setText(R.id.tv_address, "在" + listBean.getCityname());
        if ("1".equals(listBean.getSex())) {
            baseViewHolder.setText(R.id.tv_number, "约一个 帅哥");
        } else if ("2".equals(listBean.getSex())) {
            baseViewHolder.setText(R.id.tv_number, "约一个 美女");
        } else if ("0".equals(listBean.getSex())) {
            baseViewHolder.setText(R.id.tv_number, "约一个 人");
        }
        baseViewHolder.setText(R.id.tv_content, listBean.getContent());
        baseViewHolder.setText(R.id.tv_address01, listBean.getUser().getCityname());
        baseViewHolder.setText(R.id.tv_message, listBean.getComment_count());
        baseViewHolder.setText(R.id.tv_praise, listBean.getLike_count());
        baseViewHolder.addOnClickListener(R.id.ll_praise);
        if ("0".equals(listBean.getIslike())) {
            ((ImageView) baseViewHolder.getView(R.id.iv_praise)).setBackground(mContext.getResources().getDrawable(R.mipmap.icon_unpraise));
        } else if ("1".equals(listBean.getIslike())) {
            ((ImageView) baseViewHolder.getView(R.id.iv_praise)).setBackground(mContext.getResources().getDrawable(R.mipmap.icon_praise));
        }
        final List<DatingInfoModel.PicsBean> dataPics = new ArrayList<>();
        for (int i = 0; i < listBean.getPic().size(); i++) {
            DatingInfoModel.PicsBean picsBean = new DatingInfoModel.PicsBean();
            picsBean.setId(listBean.getPic().get(i).getId());
            picsBean.setPath(listBean.getPic().get(i).getPath());
            dataPics.add(picsBean);
        }
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, GalleryListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nickname", listBean.getUser().getNickname());
                bundle.putSerializable("pics", (Serializable) dataPics);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }
}
