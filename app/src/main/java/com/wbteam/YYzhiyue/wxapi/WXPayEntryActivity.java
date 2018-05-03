package com.wbteam.YYzhiyue.wxapi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.event.IsVipEvent;
import com.wbteam.YYzhiyue.ui.MainActivity;
import com.wbteam.YYzhiyue.ui.mine.InformationActivity;
import com.wbteam.YYzhiyue.ui.mine.MineCenter.Mine05Activity;
import com.wbteam.YYzhiyue.util.AndroidWorkaround;
import com.wbteam.YYzhiyue.util.MyActivityManager;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.CustomDialog;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;
    String payKey;
    private String key;
    private String weixinKey;
    private WXPayEntryActivity mContext;
    private CustomDialog dialog;
    private String paykey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        mContext=this;
        Window window = this.getWindow();
        // 透明状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }
        paykey = UtilPreference.getStringValue(mContext, "paykey");
        // String WX_APPID = "wx307bf5fa134ffacd";
        String WX_APPID = "wx5132fa74303fb155";
        weixinKey = UtilPreference.getStringValue(mContext, "weixinKey");
        MyActivityManager.getInstance().addActivity(this);
        api = WXAPIFactory.createWXAPI(this, WX_APPID);
        api.handleIntent(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Toast.makeText(getApplicationContext(), "onReq", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int code = resp.errCode;
            Log.d("TAg", code + "");
            Log.d("TAG", +resp.getType() + "");
            if (code == 0) {
                new AlertDialog.Builder(this).setMessage("支付订单成功！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if ("1".equals(weixinKey)) {
                            Intent intent = new Intent(WXPayEntryActivity.this, Mine05Activity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("keys", "5");
                            intent.putExtras(bundle);
                            startActivity(intent);
                            MyActivityManager.getInstance().finishAllActivity();
                        } else if ("2".equals(weixinKey)) {
                            //showProgress("充值成功");
                            if ("2".equals(paykey)) {
//                                Intent intent = new Intent(mContext, InformationActivity.class);
//                                startActivity(intent);
                                finish();
//                            }else if("5".equals(paykey)){
//                                Bundle bundle=new Bundle();
//                                bundle.putString("keys","3");
//                                Intent intent = new Intent(mContext, MainActivity.class);
//                                intent.putExtras(bundle);
//                                startActivity(intent);
//                                finish();
                            }
                          //  showProgress("充值成功");
                        }else if ("3".equals(weixinKey)) {
                            UtilPreference.saveString(mContext, "isvip", "1");
                            //showProgress("购买成功");
                            finish();
                        }
                    }
                }).setTitle("微信支付结果").setCancelable(false).show();

            } else if (code == -2) {
                // Toast.makeText(this, "取消支付", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(this).setMessage("取消支付").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        Intent intent = new Intent(WXPayEntryActivity.this, PayActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("status", "0");
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                        finish();

                        onBackPressed();
                    }
                }).setTitle("微信支付结果").setCancelable(false).show();

            } else {
                // Toast.makeText(this, "支付出错：" + resp.errStr, Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(this).setMessage("交易出错").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        Intent intent = new Intent(WXPayEntryActivity.this, PayActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("status", "0");
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                        finish();
                        onBackPressed();
                    }
                }).setTitle("微信支付结果").setCancelable(false).show();
            }

        }

    }
    /**
     * 显示字符串消息
     *
     * @param message
     */
    public void showProgress(String message) {
        // dialog = new CustomDialog(getActivity());
        dialog = new CustomDialog(this).builder()
                .setGravity(Gravity.CENTER).setTitle("提示", getResources().getColor(R.color.sd_color_black))//可以不设置标题颜色，默认系统颜色
                .setSubTitle(message);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1000);
    }

}
