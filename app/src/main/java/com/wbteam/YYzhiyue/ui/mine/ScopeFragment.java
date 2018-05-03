package com.wbteam.YYzhiyue.ui.mine;

import com.wbteam.YYzhiyue.network.api_service.model.BaseResponse;
import com.wbteam.YYzhiyue.network.api_service.model.EmptyEntity;
import com.wbteam.YYzhiyue.util.ToastUtil;

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
import android.widget.GridView;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.mine.TagAdapter;
import com.wbteam.YYzhiyue.network.api_service.model.TagModel;
import com.wbteam.YYzhiyue.network.api_service.util.RetrofitUtil;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScopeFragment extends DialogFragment {


    private GridView mGridView;
    private String ukey;
    private List<TagModel.ListBean> tagList = null;
    private TagAdapter mAdapter;
    private OnDialogListener mlistener;
    private TagAdapter mTagAdapter;
    private TextView tvSure;
    private String str2;
    private LoadingDialog loading;
    private String strName2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.fragment_scope, container, false);
        tagList = new ArrayList<>();
        initData();
        loading = new LoadingDialog(getActivity());
        loading.show();
        initView(view);
        return view;
    }

    public interface OnDialogListener {
        void onDialogClick(String person, String id);
    }

    public void setOnDialogListener(OnDialogListener dialogListener) {
        this.mlistener = dialogListener;
    }

    private void initData() {
        ukey = UtilPreference.getStringValue(getActivity(), "ukey");
        RetrofitUtil.getInstance().getGettaglist(ukey, new Subscriber<BaseResponse<TagModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<TagModel> baseResponse) {
                loading.dismiss();
                if (baseResponse.ret == 200) {
                    TagModel tagModel = baseResponse.getData();
                    for (int i = 0; i < tagModel.getList().size(); i++) {
                        TagModel.ListBean mListBean = new TagModel.ListBean();
                        mListBean.setId(tagModel.getList().get(i).getId());
                        mListBean.setTitle(tagModel.getList().get(i).getTitle());
                        tagList.add(mListBean);
                    }
                } else {
                    ToastUtil.showS(getActivity(),baseResponse.getMsg());
//                    if ("Ukey不合法".equals(baseResponse.getMsg())) {
//                        Intent intent=new Intent(getContext(),Login_RegisterActivity.class);
//                        startActivity(intent);
//                        getActivity().finish();
//                        MyActivityManager.getInstance().finishAllActivity();
//                    } else {
//                      showProgress(baseResponse.getMsg());
//                    }
                }
            }
        });
    }

    private void initView(View view) {
        tvSure = (TextView) view.findViewById(R.id.tv_sure);
        mGridView = (GridView) view.findViewById(R.id.tag_filter_items_GridView);
        mAdapter = new TagAdapter(getActivity(), tagList);
        mGridView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tagList.get(position).setChecked(!tagList.get(position).isChecked());
                str2 = tagList.get(position).getId();
                strName2 = tagList.get(position).getTitle();
                mlistener.onDialogClick(strName2, str2);


                for (int i = 0; i < tagList.size(); i++) {
                    //跳过已设置的选中的位置的状态
                    if (i == position) {
                        continue;
                    }
                }
                mAdapter.notifyDataSetChanged();

                mlistener.onDialogClick(strName2, str2);
                getDialog().dismiss();
            }
        });
//        tvSure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String str = "";
//                String strName = "";
//                for (int i = 0; i < tagList.size(); i++) {
//                    if (tagList.get(i).isChecked()) {
//                        str = str + tagList.get(i).getId() + ",";
//                        strName = strName + tagList.get(i).getTitle() + ",";
//                    }
//                }
//                if (str.length() > 0) {
//                    Log.d("TAG", "str2-->" + str.substring(0, str.length() - 1));
//                    str2 = str.substring(0, str.length() - 1);
//                    strName2 = strName.substring(0, strName.length() - 1);
//                    mlistener.onDialogClick(strName2, str2);
//                    offer(str2);
//                } else {
//                    strName2 = "";
//                    str2 = "";
//                    mlistener.onDialogClick(strName2, str2);
//                    getDialog().dismiss();
//                }
//
//            }
//        });

    }

    private void offer(String str2) {
        RetrofitUtil.getInstance().getSetusertag(ukey, str2, new Subscriber<BaseResponse<EmptyEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<EmptyEntity> baseResponse) {
                if (baseResponse.ret == 200) {
                    ToastUtil.showS(getActivity(), baseResponse.msg);
                    getDialog().dismiss();
                } else {
                    ToastUtil.showS(getActivity(),baseResponse.getMsg());
//                    if ("Ukey不合法".equals(baseResponse.getMsg())) {
//                        Intent intent = new Intent(getContext(), Login_RegisterActivity.class);
//                        startActivity(intent);
//                        getActivity().finish();
//                        MyActivityManager.getInstance().finishAllActivity();
//                    } else {
//                        //showProgress(baseResponse.getMsg());
//                    }
                }
            }
        });
    }

}