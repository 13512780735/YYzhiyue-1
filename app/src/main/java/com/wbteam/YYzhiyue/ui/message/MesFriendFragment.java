package com.wbteam.YYzhiyue.ui.message;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MesFriendFragment extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_mes_friend);
        initView();
        return getContentView();
    }

    private void initView() {
    }

}
