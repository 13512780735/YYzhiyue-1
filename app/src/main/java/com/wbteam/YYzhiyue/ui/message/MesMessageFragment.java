package com.wbteam.YYzhiyue.ui.message;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.widget.EaseConversationList;
import com.hyphenate.util.NetUtils;
import com.wbteam.YYzhiyue.Entity.CaseEntity;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.message.MesMessageAdapter;
import com.wbteam.YYzhiyue.base.BaseFragment;
import com.wbteam.YYzhiyue.event.MessageEvent;
import com.wbteam.YYzhiyue.im.db.InviteMessgeDao;
import com.wbteam.YYzhiyue.im.domain.InviteMessage;
import com.wbteam.YYzhiyue.ui.chat.ChatActivity;
import com.wbteam.YYzhiyue.ui.mine.MineCenter.VIPRenewActivity;
import com.wbteam.YYzhiyue.ui.reward.CreatRewardActivity;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.CustomDialog01;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MesMessageFragment extends EaseConversationListFragment {
    private TextView errorText;
    private String isvip;
    private CustomDialog01 dialog;

    @Override
    protected void initView() {
        super.initView();
        View errorView = (LinearLayout) View.inflate(getActivity(), R.layout.fragment_message01, null);
        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        //处理逻辑
        refresh();
    }

    List<InviteMessage> msgs;

    @Override
    protected void setUpView() {
        super.setUpView();
        // register context menu

        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String userId = conversation.conversationId();
                if (userId.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                else {
                    isvip = UtilPreference.getStringValue(getActivity(), "isvip");

                    if ("0".equals(isvip)) {
                        dialog = new CustomDialog01(getActivity()).builder()
                                .setGravity(Gravity.CENTER)//默认居中，可以不设置
                                .setTitle("是否申请开通VIP服务", getResources().getColor(R.color.sd_color_black))//可以不设置标题颜色，默认系统颜色
                                .setCancelable(false)
                                .setNegativeButton("否", new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setPositiveButton("是", getResources().getColor(R.color.sd_color_black), new View.OnClickListener() {//可以选择设置颜色和不设置颜色两个方法
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        // UtilPreference.saveString(getActivity(), "paykey", "5");
                                        startActivity(new Intent(getActivity(), VIPRenewActivity.class));
                                    }
                                });
                        dialog.show();
                    } else {
//                        MessageEvent messageEvent = new MessageEvent("1");
//                        EventBus.getDefault().post(messageEvent);
                        // start chat acitivity
                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        intent.putExtra("userId", userId);
                        //intent.putExtra("to_nicheng", username);
                        startActivity(intent);
                    }


                }
            }
        });
        //red packet code : 红包回执消息在会话列表最后一条消息的展示

        super.

                setUpView();
        //end of red packet code
    }

    //    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        EventBus.getDefault().unregister(getActivity());
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMoonEvent(MessageEvent messageEvent) {
//        msgs.clear();
//        //DemoDBManager.getInstance().deleteMessage(msgs);
//    }
    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())) {
            errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
            errorText.setText(R.string.the_current_network);
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean deleteMessage = false;
        if (item.getItemId() == R.id.delete_message) {
            deleteMessage = true;
        } else if (item.getItemId() == R.id.delete_conversation) {
            deleteMessage = false;
        }
        EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
        if (tobeDeleteCons == null) {
            return true;
        }
        if (tobeDeleteCons.getType() == EMConversation.EMConversationType.GroupChat) {
            EaseAtMessageHelper.get().removeAtMeGroup(tobeDeleteCons.conversationId());
        }
        try {
            // delete conversation
            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.conversationId(), deleteMessage);
            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
            inviteMessgeDao.deleteMessage(tobeDeleteCons.conversationId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh();

        // update unread count
        //((MyfriendActivity) getActivity()).updateUnreadLabel();
        return true;
    }
//    private MesMessageAdapter mAdapter;
//    private SwipeRefreshLayout mSwipeRefreshLayout;
//    private RecyclerView mRecyclerView;
//    private List<CaseEntity> data;
//    private static final int TOTAL_COUNTER = 18;
//    private static final int PAGE_SIZE = 6;
//    private static final int DELAY_MILLIS = 1000;
//    private int mCurrentCounter = 0;
//
//    private boolean isErr;
//    private boolean mLoadMoreEndGone = false;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        setContentView(R.layout.fragment_message01);
//        initView();
//        return getContentView();
//    }
//
//    private void initView() {
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.SwipeRefreshLayout);
//        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        initDate();
//        initAdapter();
//    }
//
//    private void initAdapter() {
//        mAdapter = new MesMessageAdapter(R.layout.mes_message_listview_items, data);
//        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
//        //mAdapter.setPreLoadNumber(3);
//        mRecyclerView.setAdapter(mAdapter);
//        mCurrentCounter = mAdapter.getData().size();
//        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Toast.makeText(getActivity(), "onItemClick" + position, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void initDate() {
//        data = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            CaseEntity caseEntity = new CaseEntity();
//            caseEntity.setUrl(i + "");
//            data.add(caseEntity);
//        }
//    }
//
//    @Override
//    public void onRefresh() {
//        mAdapter.setEnableLoadMore(false);//禁止加载
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mAdapter.setNewData(data);
//                isErr = false;
//                mCurrentCounter = PAGE_SIZE;
//                mSwipeRefreshLayout.setRefreshing(false);
//                mAdapter.setEnableLoadMore(true);//启用加载
//            }
//        }, DELAY_MILLIS);
//    }
//
//    @Override
//    public void onLoadMoreRequested() {
//        mSwipeRefreshLayout.setEnabled(false);
//        if (mAdapter.getData().size() < PAGE_SIZE) {
//            mAdapter.loadMoreEnd(true);
//        } else {
//            if (mCurrentCounter >= TOTAL_COUNTER) {
//                //pullToRefreshAdapter.loadMoreEnd();//default visible
//                mAdapter.loadMoreEnd(mLoadMoreEndGone);//true is gone,false is visible
//            } else {
//                if (isErr) {
//                    mAdapter.addData(data);
//                    mCurrentCounter = mAdapter.getData().size();
//                    mAdapter.loadMoreComplete();
//                } else {
//                    isErr = true;
//                    Toast.makeText(getContext(), "错误", Toast.LENGTH_LONG).show();
//                    mAdapter.loadMoreFail();
//                }
//            }
//            mSwipeRefreshLayout.setEnabled(true);
//        }
//    }
}
