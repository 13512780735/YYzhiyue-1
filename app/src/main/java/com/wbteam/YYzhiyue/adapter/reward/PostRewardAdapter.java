package com.wbteam.YYzhiyue.adapter.reward;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.network.api_service.model.PostRewardModel;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.CustomDialog01;

import java.util.List;


/**
 * Created by admin on 2018/3/13.
 */

public class PostRewardAdapter extends BaseQuickAdapter<PostRewardModel.ListBean, BaseViewHolder> {
    private CustomDialog01 dialog;
    private String ukey;

    public PostRewardAdapter(int layoutResId, List<PostRewardModel.ListBean> data) {
        super(R.layout.post_reward_listview, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final PostRewardModel.ListBean listBean) {
        baseViewHolder.setText(R.id.tv_type, "类型：" + listBean.getTagstr());
        String status = listBean.getStatus();
        final String id = listBean.getId();
        ukey = UtilPreference.getStringValue(mContext, "ukey");
        if ("0".equals(status)) {
            baseViewHolder.setText(R.id.tv_status, "未付款");
            baseViewHolder.getView(R.id.tv_02).setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.tv_02, "去付款");
            baseViewHolder.addOnClickListener(R.id.tv_02);
        } else if ("1".equals(status)) {
            baseViewHolder.setText(R.id.tv_status, "待确认");
            baseViewHolder.getView(R.id.tv_01).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.tv_02).setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.tv_01, "取消悬赏");
            baseViewHolder.setText(R.id.tv_02, "确定对象");
            baseViewHolder.addOnClickListener(R.id.tv_01);
            baseViewHolder.addOnClickListener(R.id.tv_02);
//            baseViewHolder.setOnClickListener(R.id.tv_01, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    cancel();
//                }
//            });
//            baseViewHolder.setOnClickListener(R.id.tv_02, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    comfirm();
//                }
//            });


        } else if ("2".equals(status)) {
            baseViewHolder.setText(R.id.tv_status, "进行中");
            baseViewHolder.getView(R.id.tv_02).setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.tv_02, "确定结束");
            baseViewHolder.addOnClickListener(R.id.tv_02);
//            baseViewHolder.setOnClickListener(R.id.tv_02, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finish(ukey, id);
//                }
//            });

        } else if ("3".equals(status))

        {
            baseViewHolder.setText(R.id.tv_status, "待评价");
            baseViewHolder.getView(R.id.tv_01).setVisibility(View.VISIBLE);
            baseViewHolder.getView(R.id.tv_02).setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.tv_01, "删除悬赏");
            baseViewHolder.setText(R.id.tv_02, "去评价");
            baseViewHolder.addOnClickListener(R.id.tv_01);
            baseViewHolder.addOnClickListener(R.id.tv_02);


        } else if ("4".equals(status))

        {
            baseViewHolder.setText(R.id.tv_status, "已结束");
            baseViewHolder.getView(R.id.tv_01).setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.tv_01, "删除悬赏");
            baseViewHolder.addOnClickListener(R.id.tv_01);
//            baseViewHolder.setOnClickListener(R.id.tv_01, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    delReward();
//                }
//            });
        }

        baseViewHolder.setText(R.id.tv_price, "红包金额：" + listBean.getAmount());
        baseViewHolder.getView(R.id.tv_price).setVisibility(View.GONE);
        baseViewHolder.setText(R.id.tv_time, "时间：" + listBean.getCreate_time());
        baseViewHolder.setText(R.id.tv_number, "人数：" + listBean.getAttend_count() + " / " + listBean.getLimitCount());
        String sex = listBean.getSex();
        if ("0".

                equals(sex))

        {
            baseViewHolder.setText(R.id.tv_sex, "要求性别：" + "不限");
        } else if ("1".

                equals(sex))

        {
            baseViewHolder.setText(R.id.tv_sex, "要求性别：" + "男生");
        } else if ("2".

                equals(sex))

        {
            baseViewHolder.setText(R.id.tv_sex, "要求性别：" + "女生");
        }


    }

    /**
     * 评价
     */
    private void appraise() {
    }

    /**
     * 删除悬赏
     */
    private void delReward() {
    }

    /**
     * 结束悬赏
     *
     * @param id
     * @param ukey
     */
    private void finish(final String id, final String ukey) {

    }

    /**
     * 确定对象
     */
    private void comfirm() {
    }

    /**
     * 取消悬赏
     */
    private void cancel() {
    }
}
