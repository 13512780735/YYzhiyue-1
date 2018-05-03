package com.wbteam.YYzhiyue.view;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowAvatarFragment extends DialogFragment implements View.OnClickListener {


    private TextView tvPicture, tvCancel, tvTakePhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.fragment_show_avatar, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvPicture = (TextView) view.findViewById(R.id.tv_picture);
        tvTakePhoto = (TextView) view.findViewById(R.id.tv_takePhoto);
        tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        tvPicture.setOnClickListener(this);
        tvTakePhoto.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_picture:
                break;
            case R.id.tv_takePhoto:
                break;
            case R.id.tv_cancel:
                getDialog().dismiss();
                break;
        }
    }

}
