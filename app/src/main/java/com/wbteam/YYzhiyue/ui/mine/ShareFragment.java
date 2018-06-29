package com.wbteam.YYzhiyue.ui.mine;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.ui.mine.MineCenter.Mine12Activity;
import com.wbteam.YYzhiyue.util.ToastUtil;
import com.wbteam.YYzhiyue.view.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends DialogFragment {

    MyGridView mGridView;

    private int[] icon = {R.mipmap.icon_wechat, R.mipmap.icon_wechatmoment,
            R.mipmap.icon_qq, R.mipmap.icon_weibo01, R.mipmap.icon_qzone, R.mipmap.icon_duanxin};
    private String[] iconName = {"微信", "微信朋友圈", "腾讯QQ", "新浪微博", "QQ空间", "短信"};
    private List<Map<String, Object>> dataList;
    private SimpleAdapter simpleAdapter;
    private Bitmap bmp;
    private String url;
    private TextView tvCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.BOTTOM); //可设置dialog的位置
        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dataList = new ArrayList<Map<String, Object>>();
        getData();
        initView(view);
        return view;
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

    private void initView(View view) {
        mGridView = (MyGridView) view.findViewById(R.id.GridView);
        tvCancel= (TextView) view.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        String[] from = {"img", "name"};
        int[] to = {R.id.sharesdk_avatar, R.id.sharesdk_name};
        simpleAdapter = new SimpleAdapter(getActivity(), dataList, R.layout.sharesdk_gridview_view, from, to);
        //配置适配器
        mGridView.setAdapter(simpleAdapter);
        simpleAdapter.notifyDataSetChanged();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Resources res = getActivity().getResources();
                bmp = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);
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
                        ToastUtil.showS(getActivity(), "功能暂未开通");
                        break;
                }
            }
        });
    }

    private void showShare(String platform) {
        Resources res = getActivity().getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);
        url = "http://sj.qq.com/myapp/detail.htm?apkName=com.wbteam.YYzhiyue";
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
        oks.setText("【影缘】是以电影、社交为主题的文艺社交平台。");
        // 分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageData(bmp);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);//("http://www.sikmore.com/app/app.php");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getActivity().getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);//("http://www.sikmore.com/app/app.php");

        // 启动分享GUI
        oks.show(getActivity());


// 启动分享GUI
        oks.show(getActivity());
    }
}
