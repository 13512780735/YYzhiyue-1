package com.wbteam.YYzhiyue.ui.mine.MineCenter.myReward;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.ui.mine.GradeFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppraiseFragment extends DialogFragment implements RadioGroup.OnCheckedChangeListener {
    private GradeFragment.OnDialogListener mlistener;
    private RadioGroup rgTools;
    private String score;
    private TextView tv_confirm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.fragment_appraise, container, false);
        score = "1";
        initView(view);
        return view;
    }

    private void initView(View view) {
        rgTools = (RadioGroup) view.findViewById(R.id.rgTools);
        rgTools.setOnCheckedChangeListener(this);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.onDialogClick(score);
                getDialog().dismiss();
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rbbad:
                score = "3";
                break;
            case R.id.rbmedium:
                score = "2";
                break;
            case R.id.rbgood:
                score = "1";
                break;
        }
    }

    public interface OnDialogListener {
        void onDialogClick(String person);
    }

    public void setOnDialogListener(GradeFragment.OnDialogListener dialogListener) {
        this.mlistener = dialogListener;
    }

//        mlistener.onDialogClick(name);
//    getDialog().dismiss();
}
