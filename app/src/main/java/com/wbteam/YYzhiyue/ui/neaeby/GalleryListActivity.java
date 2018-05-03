package com.wbteam.YYzhiyue.ui.neaeby;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.wbteam.YYzhiyue.R;
import com.wbteam.YYzhiyue.adapter.InforGalleryAdapter;
import com.wbteam.YYzhiyue.base.BaseActivity;
import com.wbteam.YYzhiyue.network.api_service.model.DatingInfoModel;
import com.wbteam.YYzhiyue.view.photoview.PreviewLayout;
import com.wbteam.YYzhiyue.view.photoview.ThumbViewInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class GalleryListActivity extends BaseActivity {

    private String nickname;
    private List<DatingInfoModel.PicsBean> dataPics;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private InforGalleryAdapter mAdapter;
    private PreviewLayout mPreviewLayout;
    private List<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>();

    private int mStatusBarHeight;
    private int[] mPadding = new int[4];
    private int mSolidWidth = 0;
    private int mSolidHeight = 0;
    private Rect mRVBounds = new Rect();
    private GridLayoutManager mGridLayoutManager;

    private LinearLayout.LayoutParams mLayoutParams;
    private ArrayList<String> urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_list);
        ButterKnife.bind(this);
        nickname = getIntent().getExtras().getString("nickname");
        dataPics = (List<DatingInfoModel.PicsBean>) getIntent().getExtras().getSerializable("pics");
        setTitle(nickname + "的相册");
        setBackView();
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        mStatusBarHeight = getResources().getDimensionPixelSize(resId);
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        mLayoutParams = new LinearLayout.LayoutParams(metrics.widthPixels / 3, metrics.widthPixels / 3);

        initView();
    }

    private FrameLayout mContentContainer;

    private void initView() {
        urls = new ArrayList<String>();
        for (int i = 0; i < dataPics.size(); i++) {
            urls.add(dataPics.get(i).getPath());
        }
        for (int i = 0; i < urls.size(); i++) {
            mThumbViewInfoList.add(new ThumbViewInfo(urls.get(i), i));
        }
        mContentContainer = (FrameLayout) findViewById(android.R.id.content);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        //   mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mAdapter = new InforGalleryAdapter(R.layout.item_pic_thumb, dataPics);
        mGridLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPreviewLayout = new PreviewLayout(GalleryListActivity.this);
                mPreviewLayout.setData(mThumbViewInfoList, position);
                mPreviewLayout.startScaleUpAnimation();
                mContentContainer.addView(mPreviewLayout);
            }
        });

        mRecyclerView.addOnScrollListener(new MyRecyclerOnScrollListener());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mRecyclerView.getGlobalVisibleRect(mRVBounds);

            assembleDataList();
        }
    }

    private class MyRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                assembleDataList();
            }
        }
    }

    private void assembleDataList() {
        computeBoundsForward(mGridLayoutManager.findFirstCompletelyVisibleItemPosition());

        computeBoundsBackward(mGridLayoutManager.findFirstCompletelyVisibleItemPosition());
    }

    /**
     * 从第一个完整可见item顺序遍历
     */
    private void computeBoundsForward(int firstCompletelyVisiblePos) {
        for (int i = firstCompletelyVisiblePos; i < mThumbViewInfoList.size(); i++) {
            View itemView = mGridLayoutManager.findViewByPosition(i);
            Rect bounds = new Rect();

            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView.findViewById(R.id.thumb_iv);

                thumbView.getGlobalVisibleRect(bounds);

                if (mSolidWidth * mSolidHeight == 0) {
                    mPadding[0] = thumbView.getPaddingLeft();
                    mPadding[1] = thumbView.getPaddingTop();
                    mPadding[2] = thumbView.getPaddingRight();
                    mPadding[3] = thumbView.getPaddingBottom();
                    mSolidWidth = bounds.width();
                    mSolidHeight = bounds.height();
                }

                bounds.left = bounds.left + mPadding[0];
                bounds.top = bounds.top + mPadding[1];
                bounds.right = bounds.left + mSolidWidth - mPadding[2];
                bounds.bottom = bounds.top + mSolidHeight - mPadding[3];
            } else {
                bounds.left = i % 3 * mSolidWidth + mPadding[0];
                bounds.top = mRVBounds.bottom + mPadding[1];
                bounds.right = bounds.left + mSolidWidth - mPadding[2];
                bounds.bottom = bounds.top + mSolidHeight - mPadding[3];
            }
            bounds.offset(0, -mStatusBarHeight);

            mThumbViewInfoList.get(i).setBounds(bounds);
        }
    }

    /**
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     */
    private void computeBoundsBackward(int firstCompletelyVisiblePos) {
        for (int i = firstCompletelyVisiblePos - 1; i >= 0; i--) {
            View itemView = mGridLayoutManager.findViewByPosition(i);
            Rect bounds = new Rect();

            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView.findViewById(R.id.thumb_iv);

                thumbView.getGlobalVisibleRect(bounds);

                bounds.left = bounds.left + mPadding[0];
                bounds.bottom = bounds.bottom - mPadding[3];
                bounds.right = bounds.left + mSolidWidth - mPadding[2];
                bounds.top = bounds.bottom - mSolidHeight + mPadding[1];
            } else {
                bounds.left = i % 3 * mSolidWidth + mPadding[0];
                bounds.bottom = mRVBounds.top - mPadding[3];
                bounds.right = bounds.left + mSolidWidth - mPadding[2];
                bounds.top = bounds.bottom - mSolidHeight + mPadding[1];
            }
            bounds.offset(0, -mStatusBarHeight);

            mThumbViewInfoList.get(i).setBounds(bounds);
        }
    }

    @Override
    public void onBackPressed() {
        if (mContentContainer.getChildAt(mContentContainer.getChildCount() - 1) instanceof PreviewLayout) {
            mPreviewLayout.startScaleDownAnimation();
            return;
        }

        super.onBackPressed();
    }
}
