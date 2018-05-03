package com.wbteam.YYzhiyue.ui.neaeby;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.DatingInfoModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryDetailsActivity extends BaseActivity {
    @BindView(R.id.text_num)
    TextView text_num;


    @BindView(R.id.viewpager)
    ViewPager mPager;

    private List<DatingInfoModel.PicsBean> dataPics;
    private int position1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_details);
        ButterKnife.bind(this);
        dataPics = (List<DatingInfoModel.PicsBean>) getIntent().getExtras().getSerializable("pics");
        position1=getIntent().getExtras().getInt("position");
        setTitle("详情");
        setBackView();
        initView();
    }

    private void initView() {
        //text_num.setText(pos + "/" + dataPics.size());
//        for (int i = 0; i < dataPics.size(); i++) {
//            viewContainter.add(LayoutInflater.from(this).inflate(R.layout.viewpager_page, null));
//        }

        mPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return dataPics.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                position=position1;
                PhotoView view = new PhotoView(GalleryDetailsActivity.this);
                view.enable();
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ImageLoader.getInstance().displayImage(dataPics.get(position).getPath(), view);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
    }



}
