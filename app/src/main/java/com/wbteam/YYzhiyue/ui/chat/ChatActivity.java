package com.wbteam.YYzhiyue.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.im.BaseActivity;
import com.wbteam.YYzhiyue.im.ChatFragment;
import com.wbteam.YYzhiyue.util.AndroidWorkaround;
import com.wbteam.YYzhiyue.util.StatusBarUtil;


/**
 * Created by admin on 2018/3/30.
 */

public class ChatActivity extends BaseActivity {
    private String toChatUsername;
    private EaseChatFragment chatFragment;
    public static ChatActivity activityInstance;

    private String from_avatar;
    private String from_nicheng;
//    //给扩展属性设置头像和昵称。
//    EaseChatFragment.EaseChatFragmentHelper helper = new EaseChatFragment.EaseChatFragmentHelper() {
//        @Override
//        public void onSetMessageAttributes(EMMessage message) {
//            setUserInfoAttribute(message);
//        }
//
//        @Override
//        public void onEnterToChatDetails() {
//        }
//
//        @Override
//        public void onAvatarClick(String username) {
//        }
//
//        @Override
//        public void onAvatarLongClick(String username) {
//        }
//
//        @Override
//        public boolean onMessageBubbleClick(EMMessage message) {
//            return false;
//        }
//
//        @Override
//        public void onMessageBubbleLongClick(EMMessage message) {
//        }
//
//        @Override
//        public boolean onExtendMenuItemClick(int itemId, View view) {
//            return false;
//        }
//
//        @Override
//        public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
//            return null;
//        }
//    };
//    /**
//     * 设置用户的属性，
//     * 通过消息的扩展，传递客服系统用户的属性信息
//     * @param message
//     */
//    private void setUserInfoAttribute(EMMessage message) {
//        try {
//            message.setAttribute(SharePrefConstant.ChatUserId, PrefUtils.getUserChatId());
//            message.setAttribute(SharePrefConstant.ChatUserNick, PrefUtils.getUserName());
//            message.setAttribute(SharePrefConstant.ChatUserPic, PrefUtils.getUserPic()) ;//这里用是图片的完整链接地址，如果要取缩略图，需要服务端配合；
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private String to_avater;
    private static String to_nicheng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        activityInstance = this;
        // to_nicheng = getIntent().getExtras().getString("to_nicheng");
        toChatUsername = getIntent().getExtras().getString("userId");
        //use EaseChatFratFragment
        chatFragment = new ChatFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // enter to chat activity when click notification bar, here make sure only one chat activiy
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
    }

    public String getToChatUsername() {
        return toChatUsername;
    }

    /**
     * 继承环信的聊天页面
     * 实现消息监听和聊天页面的标题上显示的对方昵称
     */
//    public static class ChatFragment extends EaseChatFragment implements EMMessageListener {
//        /**
//         * 设置聊天页面的title上面的昵称
//         */
//        @Override
//        protected void setUpView() {
//            super.setUpView();
//            titleBar.setTitle(to_nicheng);
//
//        }
//
//    }
}
