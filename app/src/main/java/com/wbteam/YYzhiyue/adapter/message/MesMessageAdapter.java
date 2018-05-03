package com.wbteam.YYzhiyue.adapter.message;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbteam.YYzhiyue.Entity.CaseEntity;
import com.wbteam.YYzhiyue.R;

import java.util.List;

/**
 * Created by Administrator on 2018/1/23.
 */

public class MesMessageAdapter extends BaseQuickAdapter<CaseEntity,BaseViewHolder>{
    public MesMessageAdapter(int layoutResId, List<CaseEntity> data) {
        super(R.layout.mes_message_listview_items, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CaseEntity caseEntity) {
        baseViewHolder.setText(R.id.tv_time,caseEntity.getUrl()+"小时");
    }
}
