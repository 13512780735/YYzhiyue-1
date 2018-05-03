package com.wbteam.YYzhiyue.ui.reward;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.util.StringUtil;
import com.wbteam.YYzhiyue.view.AmountView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequireFragment extends DialogFragment implements View.OnClickListener {

    private RadioGroup radioGroupGrade;


    private AmountView mAmountView;
    private int number = 1;
    private String sex;
    private View tv_cancel;
    private View tv_save;
    private OnDialogListener mlistener;

    public RequireFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);
        View view = inflater.inflate(R.layout.fragment_require, container, false);
        sex = "0";
        initView(view);
        return view;
    }

    private void initView(View view) {
        tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_save = view.findViewById(R.id.tv_save);
        tv_cancel.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        radioGroupGrade= (RadioGroup) view.findViewById(R.id.radioGroup_grade);
        mAmountView = (AmountView) view.findViewById(R.id.amount_view);
        mAmountView.setGoods_storage(50);
        mAmountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                // Toast.makeText(getActivity(), "Amount=>  " + amount, Toast.LENGTH_SHORT).;
                number = amount;
                Log.d("TAG", "goods_num-->" + amount);
            }
        });
        radioGroupGrade.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton_grade01:
                        sex = "0";
                        break;
                    case R.id.radioButton_grade02:
                        sex = "1";
                        break;
                    case R.id.radioButton_grade03:
                        sex = "2";
                        break;
                }
            }
        });
    }

    public interface OnDialogListener {
        void onDialogClick(String sex, String number);
    }

    public void setOnDialogListener(OnDialogListener dialogListener) {
        this.mlistener = dialogListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                getDialog().dismiss();
                break;
            case R.id.tv_save:
                mlistener.onDialogClick(sex, String.valueOf(number));
                getDialog().dismiss();
                break;
        }
    }
}
