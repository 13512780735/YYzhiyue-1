package com.wbteam.YYzhiyue.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.util.AndroidWorkaround;
import com.wbteam.YYzhiyue.util.UtilPreference;


/**
 * Created by admin on 2018/3/30.
 */

public class ChatActivity extends BaseActivity {
    private String toChatUsername;
    private ChatFragment chatFragment;
    public static ChatActivity activityInstance;

    private String from_avatar;
    private String from_nicheng;
    //给扩展属性设置头像和昵称。
    EaseChatFragment.EaseChatFragmentHelper helper = new EaseChatFragment.EaseChatFragmentHelper() {
        @Override
        public void onSetMessageAttributes(EMMessage message) {
            // 附带扩展属性，头像和昵称他人的
            message.setAttribute(EaseConstant.TO_AVATER, to_avater);
            message.setAttribute(EaseConstant.TO_NICHENG, to_nicheng);
            from_avatar = UtilPreference.getStringValue(ChatActivity.this, "headimg");
            from_nicheng = UtilPreference.getStringValue(ChatActivity.this, "mNickName");
            //我的头像  存到自己的文件中
            message.setAttribute(EaseConstant.FROM_AVATER, from_avatar);
            message.setAttribute(EaseConstant.FROM_NICHENG, from_nicheng);
          //  Log.d("TAG859", to_avater + "-" + to_nicheng + "-" + from_avatar + "-" + from_nicheng);
        }

        @Override
        public void onEnterToChatDetails() {
        }

        @Override
        public void onAvatarClick(String username) {
        }

        @Override
        public void onAvatarLongClick(String username) {
        }

        @Override
        public boolean onMessageBubbleClick(EMMessage message) {
            return false;
        }

        @Override
        public void onMessageBubbleLongClick(EMMessage message) {
        }

        @Override
        public boolean onExtendMenuItemClick(int itemId, View view) {
            return false;
        }

        @Override
        public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
            return null;
        }
    };
    private String to_avater;
    private static String to_nicheng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
//        Window window = this.getWindow();
//        // 透明状态栏
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        // 透明导航栏
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
//            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
//        }
        activityInstance = this;
        toChatUsername = getIntent().getExtras().getString("userId");
//        to_avater = getIntent().getExtras().getString("to_avater");
        to_nicheng = getIntent().getExtras().getString("to_nicheng");
       //Log.d("TAG858", to_avater + "-" + to_nicheng + "-" + from_avatar + "-" + from_nicheng);
        //use EaseChatFratFragment
        chatFragment = new ChatFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
       // chatFragment.setChatFragmentHelper(helper);
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
    public static class ChatFragment extends EaseChatFragment implements EMMessageListener {
        /**
         * 设置聊天页面的title上面的昵称
         */
        @Override
        protected void setUpView() {
            super.setUpView();
            titleBar.setTitle(to_nicheng);

        }

    }
}
