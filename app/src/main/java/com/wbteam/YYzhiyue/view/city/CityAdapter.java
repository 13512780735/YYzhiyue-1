package com.wbteam.YYzhiyue.view.city;

import android.view.View;
import android.widget.AbsListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.wbteam.YYzhiyue.R;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.widget.AdapterHolder;
import org.kymjs.kjframe.widget.KJAdapter;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by YoKey on 16/10/7.
 */
public class CityAdapter extends KJAdapter<CityEntity> implements SectionIndexer {
    private KJBitmap kjb = new KJBitmap();
    private ArrayList<CityEntity> datas;

    public CityAdapter(AbsListView view, ArrayList<CityEntity> mDatas) {
        super(view, mDatas, R.layout.item_list_contact);
        datas = mDatas;
        if (datas == null) {
            datas = new ArrayList<>();
        }
        Collections.sort(datas);
    }


    @Override
    public void convert(AdapterHolder helper, CityEntity item, boolean isScrolling) {

    }

    @Override
    public void convert(AdapterHolder holder, CityEntity item, boolean isScrolling, int position) {

        holder.setText(R.id.contact_title, item.getName());
 //       ImageView headImg = holder.getView(R.id.tv_item_city_listview_letter);
//        if (isScrolling) {
//            kjb.displayCacheOrDefult(headImg, item.getUrl(), R.drawable.default_head_rect);
//        } else {
//            kjb.displayWithLoadBitmap(headImg, item.getUrl(), R.drawable.default_head_rect);
//        }

        TextView tvLetter = holder.getView(R.id.contact_catalog);
        TextView tvLine = holder.getView(R.id.contact_line);

        //如果是第0个那么一定显示#号
        if (position == 0) {
            tvLetter.setVisibility(View.VISIBLE);
            tvLetter.setText("A");
            tvLine.setVisibility(View.VISIBLE);
        } else {

            //如果和上一个item的首字母不同，则认为是新分类的开始
            CityEntity prevData = datas.get(position - 1);
            if (item.getFirstChar() != prevData.getFirstChar()) {
                tvLetter.setVisibility(View.VISIBLE);
                tvLetter.setText("" + item.getFirstChar());
                tvLine.setVisibility(View.VISIBLE);
            } else {
                tvLetter.setVisibility(View.GONE);
                tvLine.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        CityEntity item = datas.get(position);
        return item.getFirstChar();
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            char firstChar = datas.get(i).getFirstChar();
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}
