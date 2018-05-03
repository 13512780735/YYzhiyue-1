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
import com.wbteam.YYzhiyue.ui.chat.ChatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MesMessageFragment extends EaseConversationListFragment {
    private TextView errorText;

    @Override
    protected void initView() {
        super.initView();
        View errorView = (LinearLayout) View.inflate(getActivity(), R.layout.fragment_message01, null);
        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);
    }



    @Override
    protected void setUpView() {
        super.setUpView();
        // register context menu

        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.conversationId();
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                else {
                    // start chat acitivity
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
//                    if (conversation.isGroup()) {
//                        if (conversation.getType() == EMConversation.EMConversationType.ChatRoom) {
//                            // it's group chat
//                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
//                        } else {
//                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
//                        }
//
//                    }
                    // it's single chat
                    intent.putExtra("userId", username);
                    intent.putExtra("to_nicheng", username);
                    startActivity(intent);
                }
            }
        });
        //red packet code : 红包回执消息在会话列表最后一条消息的展示

        super.setUpView();
        //end of red packet code
    }

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
//            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
//            inviteMessgeDao.deleteMessage(tobeDeleteCons.conversationId());
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
