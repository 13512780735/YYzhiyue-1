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
import android.widget.Toast;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.util.StringUtil;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeightFragment extends DialogFragment implements View.OnClickListener, NumberPickerView.OnScrollListener, NumberPickerView.OnValueChangeListener, NumberPickerView.OnValueChangeListenerInScrolling {


    private View tv_cancel;
    private View tv_save;
    private NumberPickerView picker_height;
    private OnDialogListener mlistener;
    private String h;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);
        View view = inflater.inflate(R.layout.fragment_height, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_save = view.findViewById(R.id.tv_save);
        picker_height = (NumberPickerView) view.findViewById(R.id.picker_height);
        tv_cancel.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        //  picker_height.setOnClickListener(this);
        //etData(picker_height, 0, 10, 3);
        picker_height.setOnScrollListener(this);
        picker_height.setOnValueChangedListener(this);
        picker_height.setOnValueChangeListenerInScrolling(this);
        String[] display_2 = getResources().getStringArray(R.array.height_display);
        picker_height.refreshByNewDisplayedValues(display_2);
    }

    private void setData(NumberPickerView picker, int minValue, int maxValue, int value) {
        picker.setMinValue(minValue);
        picker.setMaxValue(maxValue);
        picker.setValue(value);
    }

    @Override
    public void onScrollStateChange(NumberPickerView view, int scrollState) {

    }

    @Override
    public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
        String[] content = picker.getDisplayedValues();
        if (content != null)
            h = content[newVal - picker.getMinValue()];
//            Toast.makeText(getActivity(), "oldVal : " + oldVal + " newVal : " + newVal + "\n" + content[newVal - picker.getMinValue()], Toast.LENGTH_SHORT)
//                    .show();
    }

    @Override
    public void onValueChangeInScrolling(NumberPickerView picker, int oldVal, int newVal) {

    }

    public interface OnDialogListener {
        void onDialogClick(String person);
    }

    public void setOnDialogListener(OnDialogListener dialogListener) {
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
                // String h = picker_height.getContentByCurrValue();
                if (StringUtil.isBlank(h)) {
                    h = "400";
                }
                mlistener.onDialogClick(h);
                getDialog().dismiss();
                break;
        }
    }
}
