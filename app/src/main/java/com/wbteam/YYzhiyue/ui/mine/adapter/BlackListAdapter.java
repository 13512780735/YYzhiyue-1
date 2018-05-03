package com.wbteam.YYzhiyue.ui.mine.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbteam.YYzhiyue.Entity.CaseEntity;
import com.wbteam.YYzhiyue.R;

import java.util.List;

/**
 * Created by Administrator on 2018/2/25.
 */

public class BlackListAdapter extends BaseQuickAdapter<CaseEntity,BaseViewHolder> {
    public BlackListAdapter(int layoutResId, List<CaseEntity> data) {
        super(R.layout.blacklist_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CaseEntity item) {
        helper.setText(R.id.group_name,"乱舞小天后");
    }
}
