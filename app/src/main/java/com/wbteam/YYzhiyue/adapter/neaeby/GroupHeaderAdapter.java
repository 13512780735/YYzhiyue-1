package com.wbteam.YYzhiyue.adapter.neaeby;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbteam.YYzhiyue.Entity.CaseEntity;
import com.wbteam.YYzhiyue.R;

import java.util.List;

/**
 * Created by Administrator on 2018/1/19.
 */

public class GroupHeaderAdapter extends BaseQuickAdapter<CaseEntity,BaseViewHolder>{
    public GroupHeaderAdapter(int layoutResId, List<CaseEntity> data) {
        super(R.layout.nearby_group_listview_items, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CaseEntity caseEntity) {
        baseViewHolder.setText(R.id.group_number,caseEntity.getUrl()+"/50");

    }
}
