package com.wbteam.YYzhiyue.ui.mine.MineCenter.myReward;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.reward.AppraiseAdapter;
import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.CommentBean;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.ui.mine.GradeFragment;
import com.wbteam.YYzhiyue.util.LoaddingDialog;
import com.wbteam.YYzhiyue.util.ToastUtil;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppraiseFragment extends DialogFragment implements RadioGroup.OnCheckedChangeListener {
    private OnDialogListener mlistener;
    private RadioGroup rgTools;
    private String score;
    private TextView tv_confirm;
    private String remark;
    private String ukey;
    private LoaddingDialog dialog1;
    List<CommentBean.ListBean> data;
    private AppraiseAdapter mAdapter;
    private MyGridView mGridView;
    private int position1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appraise, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.BOTTOM); //可设置dialog的位置
        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        ukey = UtilPreference.getStringValue(getActivity(), "ukey");
        score = "1";
        data = new ArrayList<>();
        mAdapter = new AppraiseAdapter(getActivity(), data);
        initData(score);
        dialog1 = new LoaddingDialog(getActivity());
        dialog1.show();
        initView(view);
        return view;
    }

    private void initData(String score) {
        Log.d("TAG3333", "8888");
        RetrofitUtil.getInstance().Commentcategory(ukey, score, new Subscriber<BaseResponse<CommentBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<CommentBean> baseResponse) {
                dialog1.dismiss();
                if (baseResponse.ret == 200) {
                    CommentBean commentBean = baseResponse.getData();
                    data.addAll(commentBean.getList());
                    mAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showS(getActivity(), baseResponse.getMsg());
                }
            }
        });
    }

    private void initView(View view) {
        rgTools = (RadioGroup) view.findViewById(R.id.rgTools);
        rgTools.setOnCheckedChangeListener(this);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.onDialogClick(score, remark);
                getDialog().dismiss();
            }
        });
        mGridView = (MyGridView) view.findViewById(R.id.myGridView);
        mGridView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position1 = position;
                mAdapter.setSeclection(position1);
                remark = data.get(position).getTitle();
                // gridView中点击 item为选中状态(背景颜色)
//                for (int i = 0; i < parent.getCount(); i++) {
//                    View item = mGridView.getChildAt(i).findViewById(R.id.tv_name);
//                    if (position == i) {//当前选中的Item改变背景颜色
//                        item.setBackgroundResource(R.drawable.button_home_bg03);
//                    } else {
//                        item.setBackgroundResource(R.drawable.button_home_bg04);
//                    }
//                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rbbad:
                score = "3";
                if (data.size() != 0) {
                    data.clear();
                }
                initData(score);
                mAdapter.setSeclection(0);
                break;
            case R.id.rbmedium:
                score = "2";
                if (data.size() != 0) {
                    data.clear();
                }
                initData(score);
                mAdapter.setSeclection(0);
                break;
            case R.id.rbgood:
                score = "1";
                if (data.size() != 0) {
                    data.clear();
                }
                initData(score);
                mAdapter.setSeclection(0);
                break;
        }
    }

    public interface OnDialogListener {
        void onDialogClick(String person, String remark);
    }

    public void setOnDialogListener(OnDialogListener dialogListener) {
        this.mlistener = dialogListener;
    }

//        mlistener.onDialogClick(name);
//    getDialog().dismiss();
}
