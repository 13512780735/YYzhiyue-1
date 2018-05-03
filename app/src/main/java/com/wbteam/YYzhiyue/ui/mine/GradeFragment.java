package com.wbteam.YYzhiyue.ui.mine;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.wbteam.YYzhiyue.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class GradeFragment extends DialogFragment {

    private OnDialogListener mlistener;
    private String[] iconName = {"男", "女"};
    private ArrayList<Map<String, Object>> dataList;
    private SimpleAdapter simpleAdapter;
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.fragment_grade, container, false);
        dataList = new ArrayList<Map<String, Object>>();
        getData();
        initView(view);
        return view;
    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < iconName.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", iconName[i]);
            dataList.add(map);
        }
        return dataList;
    }

    public interface OnDialogListener {
        void onDialogClick(String person);
    }

    public void setOnDialogListener(OnDialogListener dialogListener) {
        this.mlistener = dialogListener;
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.ListView);
        String[] from = {"name"};
        int[] to = {R.id.tv_name};
        simpleAdapter = new SimpleAdapter(getActivity(), dataList, R.layout.base_dialog_string_view, from, to);
        mListView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name=dataList.get(position).get("name").toString();
                mlistener.onDialogClick(name);
                getDialog().dismiss();
            }
        });
    }

}
