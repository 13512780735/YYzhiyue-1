package com.wbteam.YYzhiyue.wxapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2017\6\8 0008.
 */

public class AppRegister extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
        String WX_APPID = "wx5132fa74303fb155";
        // 将该app注册到微信
        msgApi.registerApp(WX_APPID);

    }
}
