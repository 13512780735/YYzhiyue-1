package com.wbteam.YYzhiyue.adapter.reward;

import android.content.Context;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbteam.YYzhiyue.Entity.CaseEntity;
import com.wbteam.YYzhiyue.R;

import java.util.List;

/**
 * Created by Administrator on 2018/1/23.
 */

public class RewardShopAdapter extends BaseQuickAdapter<CaseEntity, BaseViewHolder> {
    public RewardShopAdapter(int layoutResId, List<CaseEntity> data) {
        super(R.layout.reward_shop_griditem_view, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CaseEntity caseEntity) {
        baseViewHolder.setText(R.id.tv_shop_price, caseEntity.getUrl());
        baseViewHolder.setText(R.id.tv_shop_name, "草莓");
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int width1 = width / 4 - 50;
        baseViewHolder.getView(R.id.ll_gridview).setMinimumWidth(width1);
        baseViewHolder.getView(R.id.ll_gridview).setMinimumHeight(width1+30);

    }
}
