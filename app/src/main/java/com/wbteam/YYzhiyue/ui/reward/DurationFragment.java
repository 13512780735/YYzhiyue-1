package com.wbteam.YYzhiyue.ui.reward;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.wbteam.YYzhiyue.R;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DurationFragment extends DialogFragment implements View.OnClickListener {

    private View tv_cancel;
    private View tv_save;
    private NumberPickerView picker_height;
    private HeightFragment.OnDialogListener mlistener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);
        View view = inflater.inflate(R.layout.fragment_duration, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_save = view.findViewById(R.id.tv_save);
        picker_height = (NumberPickerView) view.findViewById(R.id.picker_height);
        tv_cancel.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        picker_height.setOnClickListener(this);
        setData(picker_height, 1, 5, 3);
    }

    private void setData(NumberPickerView picker, int minValue, int maxValue, int value) {
        picker.setMinValue(minValue);
        picker.setMaxValue(maxValue);
        picker.setValue(value);
    }

    public interface OnDialogListener {
        void onDialogClick(String person);
    }

    public void setOnDialogListener(HeightFragment.OnDialogListener dialogListener) {
        this.mlistener = dialogListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                // mlistener.onDialogClick("886");
                getDialog().dismiss();
                break;
            case R.id.tv_save:
                String h = picker_height.getContentByCurrValue();
                mlistener.onDialogClick(h);
                getDialog().dismiss();
                break;
        }
    }
}
