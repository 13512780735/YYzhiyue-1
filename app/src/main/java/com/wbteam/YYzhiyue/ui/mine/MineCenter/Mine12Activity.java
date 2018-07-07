package com.wbteam.YYzhiyue.ui.mine.MineCenter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.citypicker.utils.ToastUtils;
import com.wbteam.YYzhiyue.util.StringUtils;
import com.wbteam.YYzhiyue.util.UtilPreference;
import com.wbteam.YYzhiyue.view.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class Mine12Activity extends BaseActivity {
    @BindView(R.id.GridView)
    MyGridView mGridView;
    @BindView(R.id.tv_invitation_code)
    TextView tv_invitation_code;
    @BindView(R.id.mine12_scroll)
    ScrollView mine12_scroll;
    private int[] icon = {R.mipmap.icon_wechat, R.mipmap.icon_wechatmoment,
            R.mipmap.icon_qq, R.mipmap.icon_weibo01, R.mipmap.icon_qzone, R.mipmap.icon_duanxin};
    private String[] iconName = {"微信", "微信朋友圈", "腾讯QQ", "新浪微博", "QQ空间", "短信"};
    private List<Map<String, Object>> dataList;
    private SimpleAdapter simpleAdapter;
    private String invitation_code;
    private String url;
    private Bitmap bmp;
    private OnekeyShare oks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine12);
        ButterKnife.bind(this);
        invitation_code = UtilPreference.getStringValue(mContext, "invitation_code");
        setBackView();
        setTitle("邀请好友");
        dataList = new ArrayList<Map<String, Object>>();
        getData();
        initView();
    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icon[i]);
            map.put("name", iconName[i]);
            dataList.add(map);
        }
        return dataList;
    }

    private void initView() {
        mine12_scroll.smoothScrollTo(0,0);
        mine12_scroll.setFocusable(true);
        tv_invitation_code.setText(invitation_code);
        String[] from = {"img", "name"};
        int[] to = {R.id.sharesdk_avatar, R.id.sharesdk_name};
        simpleAdapter = new SimpleAdapter(this, dataList, R.layout.sharesdk_gridview_view, from, to);
        //配置适配器
        mGridView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Resources res = Mine12Activity.this.getResources();
                bmp = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);
                url = "http://app.yun-nao.com/sharepage/share.html?code=" + invitation_code;
                switch (position) {
                    case 0:
                   showShare(Wechat.NAME);


                        break;
                    case 1:
                       showShare(WechatMoments.NAME);
                        break;
                    case 2:
                      showShare(QQ.NAME);

                        break;
                    case 3:
                        showShare(SinaWeibo.NAME);

                        break;
                    case 4:
                   showShare(QZone.NAME);

                        break;
                    case 5:
                      // showShare(ShortMessage.NAME);
                        showProgress("功能暂未开通");
                        break;
                }
            }
        });
    }

    private void showShare(String platform) {
        Resources res = Mine12Activity.this.getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);
        url = "http://app.yun-nao.com/sharepage/share.html?code=" + invitation_code;
        final OnekeyShare oks = new OnekeyShare();
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        if (platform != null) {
            oks.setPlatform(platform);
        }
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setTitle("我推荐您使用“影缘之约”");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);//("http://www.sikmore.com/app/app.php");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我的影缘之约推荐码" + "\n" + invitation_code);
        // 分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageData(bmp);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);//("http://www.sikmore.com/app/app.php");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(mContext.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);//("http://www.sikmore.com/app/app.php");

        // 启动分享GUI
        oks.show(this);


// 启动分享GUI
        oks.show(this);
    }

    @OnClick({R.id.tv_copy})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_copy:
                ClipboardManager cmb = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(tv_invitation_code.getText().toString().trim()); //将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
                showProgress("复制文本成功");
                break;
        }
    }
}
